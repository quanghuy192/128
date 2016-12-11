package vn.com.huy.a128.control;

import android.content.Context;
import android.content.Intent;

import vn.com.huy.a128.view.ResultView;

public class ConfigurationLevel {

	public boolean testTheNextLevel(int currentScores, int maxScoresValues) {

		if (currentScores == (maxScoresValues - 0))
			return true;
		else
			return false;
	}

	public void finishGame(Context context, int count) {
		Intent intent = new Intent(context, ResultView.class);
		intent.putExtra("hscores", count);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	public int setLevel(int level) {
		if (level < 8) {
			return 0;
		} else if (level < 16) {
			return 2;
		} else if (level < 32) {
			return 4;
		} else if (level < 64) {
			return 5;
		} else
			return 6;
	}

}
