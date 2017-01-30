package vn.com.huy.a128.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import vn.com.huy.a128.R;
import vn.com.huy.a128.utilities.MusicInGame;

public class MainActivity extends Activity implements View.OnClickListener {

    private ImageView play, sound;
    private boolean isSound = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_main);
        play = (ImageView) findViewById(R.id.play);
        sound = (ImageView) findViewById(R.id.sound);
        play.setOnClickListener(this);
        sound.setOnClickListener(this);

        // Banner
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.play: {
                if(isSound){
                    MusicInGame.init(getApplicationContext(), MusicInGame.Sound.BTN);
                    MusicInGame.playSound();
                }
                Intent switchIntent = new Intent(this, MainPlayView.class);
                switchIntent.putExtra("Sound_status",isSound);
                startActivity(switchIntent);
            }
            break;

            case R.id.sound: {
                if(isSound){
                    isSound = false;
                    sound.setImageDrawable(getResources().getDrawable(R.mipmap.volume_mute));
                }else{
                    isSound = true;
                    sound.setImageDrawable(getResources().getDrawable(R.mipmap.volume));
                }
            }
            break;
        }
    }
}
