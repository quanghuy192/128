package vn.com.huy.a128.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import vn.com.huy.a128.R;

public class ResultView extends Activity implements OnClickListener {

	private TextView newHS, currentHS;
	private Button exit, retry;
	private SharedPreferences shared;
	private SharedPreferences.Editor edit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gameover);
		shared = getSharedPreferences("myHS", Context.MODE_PRIVATE);
		edit = shared.edit();

		Bundle bundle = getIntent().getExtras();
		int hscores = bundle.getInt("hscores");
		int oldHScores = shared.getInt("scores", -1);

		if (oldHScores < hscores) {
			edit.putInt("scores", hscores);
			Toast.makeText(getApplicationContext(), "New best",
					Toast.LENGTH_SHORT).show();
			oldHScores = hscores;
			edit.commit();
		} else {
		}

		newHS = (TextView) findViewById(R.id.high_scores_new);
		currentHS = (TextView) findViewById(R.id.high_scores_old);
		exit = (Button) findViewById(R.id.exit);
		retry = (Button) findViewById(R.id.back);

		newHS.setText(String.valueOf(oldHScores));
		currentHS.setText(String.valueOf(hscores));

		retry.setOnClickListener(this);
		exit.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.exit: {
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory( Intent.CATEGORY_HOME );
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			this.startActivity(intent);
			/*android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(1);*/
		}

			break;
		case R.id.back: {
			finish();
		}
			break;
		default:
			break;
		}
	}

}
