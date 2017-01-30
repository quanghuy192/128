package vn.com.huy.a128.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vn.com.huy.a128.R;
import vn.com.huy.a128.control.ConfigurationLevel;
import vn.com.huy.a128.control.LoadingDataVer2;
import vn.com.huy.a128.model.Bomb;
import vn.com.huy.a128.model.Box;
import vn.com.huy.a128.model.Present;
import vn.com.huy.a128.utilities.MusicInGame;

/**
 * Created by Huy on 12/26/2014.
 */
public class MainPlayView extends Activity implements OnClickListener {

    private GridView grid;
    private int[][] values, copyOfValues;
    private int count = 0, maxScores = 0;
    private TextView highScores, level, currenSun;
    private ImageView next;
    private ConfigurationLevel config;
    private int currentLevel = 0;
    private ArrayAdapter<Box> adapter;
    private LoadingDataVer2 lData = null;
    private RelativeLayout layout;
    private int[] backgroundList = new int[]{R.color.b1, R.color.b2,
            R.color.b3, R.color.b4, R.color.b5, R.color.b6, R.color.b7,
            R.color.b8, R.color.b9, R.color.b10};
    private Random random = new Random();
    private Typeface custom_font;
    private int numberOfPresent = 4;
    private int numberOfBomb = 3;

    /*
    *
    *Interstitial Ads
    * */
    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_play);
        highScores = (TextView) findViewById(R.id.high_score);
        layout = (RelativeLayout) findViewById(R.id.background);
        initUI();

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });

        requestNewInterstitial();
    }

    @SuppressWarnings("deprecation")
    private void initUI() {
        layout.setBackgroundDrawable(getResources().getDrawable(
                backgroundList[random.nextInt(10) + 0]));
        custom_font = Typeface.createFromAsset(getAssets(), "distressed.ttf");
        config = new ConfigurationLevel();
        // lData = LoadingData.getInstance();
        lData = new LoadingDataVer2(numberOfPresent, numberOfBomb);
        lData.setLevel(config.setLevel(currentLevel));
        values = lData.getDataList();
        maxScores += lData.totalScoreInGame();
        copyOfValues = values.clone();
        //	currenSun       = (TextView) findViewById(R.id.count_sun);
        next = (ImageView) findViewById(R.id.next);
        level = (TextView) findViewById(R.id.level);
        grid = (GridView) findViewById(R.id.gridview);
        adapter = new BoxAdapter(getApplicationContext(), R.layout.item,
                getListBox());
        /*if (lData.get() == null) {
			Log.d("NULL>>>", "NULL>>>>");
		} else {
			currenSun.setText(String.valueOf(lData.get().getCountSun()));
		}*/
        next.setVisibility(View.INVISIBLE);
        next.setOnClickListener(this);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (getIntent().getBooleanExtra("Sound_status", true)) {
                    MusicInGame.playSound();
                }
                int i = position / 7;
                int j = position % 7;
                if (copyOfValues[i][j] == 0) {
                    ((ImageView) view.findViewById(R.id.image)).setMaxHeight(35);
                    ((ImageView) view.findViewById(R.id.image)).setMaxWidth(35);
                    ((ImageView) view.findViewById(R.id.image))
                            .setImageDrawable(getResources().getDrawable(
                                    R.mipmap.ic_bomb_item));
                    finish();
                    config.finishGame(getApplicationContext(), count);
                    if (getIntent().getBooleanExtra("Sound_status", true)) {
                        MusicInGame.init(getApplicationContext(), MusicInGame.Sound.BOMB);
                        MusicInGame.playSound();
                    }
                } else {
                    ((ImageView) view.findViewById(R.id.image))
                            .setImageDrawable(null);
                    ((TextView) view.findViewById(R.id.text)).setText(String
                            .valueOf(copyOfValues[i][j]));
                    count += copyOfValues[i][j];
                    ((TextView) view.findViewById(R.id.text)).setTypeface(custom_font);
                    highScores.setText(String.valueOf(count));
                    copyOfValues[i][j] = -1;

                    view.setOnClickListener(null);
                }
                if (config.testTheNextLevel(count, maxScores)) {
                    next.setVisibility(View.VISIBLE);
                }
            }

        });
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    private List<Box> getListBox() {
        List<Box> listBox = new ArrayList<Box>();

        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[1].length; j++) {
                Box box = new Box();
                box.setX(i);
                box.setY(j);
                box.setValue(copyOfValues[i][j]);

                listBox.add(box);
            }

        }
        return listBox;
    }

    @Override
    public void onClick(View view) {
        if (getIntent().getBooleanExtra("Sound_status", true)) {
            MusicInGame.playSound();
        }
        currentLevel++;
        // grid.setAdapter(adapter);
        // onCreate(null);
        initUI();
        level.setText(String.valueOf(currentLevel));
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    class ViewHolder {
        private ImageView img;
        private TextView text;

        public ViewHolder(View view) {
            this.img = (ImageView) view.findViewById(R.id.image);
            this.text = (TextView) view.findViewById(R.id.text);
        }
    }

    public class BoxAdapter extends ArrayAdapter<Box> {

        private Context context;
        // private List<Box> list;
        private ViewHolder viewHolder;

        public BoxAdapter(Context context, int resource, List<Box> objects) {
            super(context, resource, objects);
            // list = objects;
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;

            if (view == null) {
                LayoutInflater layoutInflate = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = layoutInflate.inflate(R.layout.item, parent, false);

                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            int i = position / 7;
            int j = position % 7;

            if (i != 0 && j != 0) {
                viewHolder.img.setImageDrawable(getResources().getDrawable(
                        R.mipmap.ic_box));
                List<Present> presentList = lData.getPresentList();
                for (Present p : presentList) {
                    if (i == p.getX() && j == p.getY()) {
                        viewHolder.img.setImageDrawable(getResources().getDrawable(
                                R.mipmap.ic_present));
                    }
                }
                List<Bomb> bomList = lData.getBombList();
                for (Bomb b : bomList) {
                    if (i == b.getX() && j == b.getY()) {
                        viewHolder.img.setImageDrawable(getResources().getDrawable(
                                R.mipmap.ic_skull));
                    }
                }
                // viewHolder.text.setText(String.valueOf(values[i][j]));
            } else if (i == 0 && j == 0) {
                //	viewHolder.img.setImageDrawable(getResources().getDrawable(
                //				R.mipmap.ic_bomb_item));
                viewHolder.text.setText("Bomb");
                viewHolder.text.setTextSize(15);
                view.setBackground(getResources().getDrawable(R.drawable.number_round_circle));
            } else {
                viewHolder.text.setTextColor(Color.WHITE);
                viewHolder.text.setTypeface(custom_font);
                viewHolder.text.setText(String.valueOf(values[i][j]));
                view.setBackground(getResources().getDrawable(R.drawable.number_round_circle));
                view.setOnClickListener(null);
            }
            return view;
        }

    }
}
