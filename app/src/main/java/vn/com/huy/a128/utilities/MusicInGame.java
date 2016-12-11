package vn.com.huy.a128.utilities;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import java.io.IOException;

import static vn.com.huy.a128.utilities.MusicInGame.Sound.BOMB;
import static vn.com.huy.a128.utilities.MusicInGame.Sound.BTN;

public class MusicInGame implements OnCompletionListener {

	private static MediaPlayer media;
	private static AssetFileDescriptor mp3Path;
	private static Context context;

	public static void init(Context context, Sound type) {
		try {
			if (type.equals(BTN)) {
				mp3Path = context.getAssets().openFd("button.mp3");
			} else if (type.equals(BOMB)) {
				mp3Path = context.getAssets().openFd("explosion.mp3");
		}
			MusicInGame.context = context;
			media = new MediaPlayer();
			media.reset();
			media.setAudioStreamType(AudioManager.STREAM_MUSIC);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private MusicInGame() {
	}

	public static void playSound() {

		try {
			media.reset();
			media.setDataSource(mp3Path.getFileDescriptor(), mp3Path.getStartOffset(), mp3Path.getDeclaredLength());
			media.prepare();
			media.start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void stop() {
		if (media != null) {
			if (media.isPlaying()) {
				media.pause();
				media.release();
				media = null;
			} else {
				// Nothing
			}
		} else {
			// Nothing
		}

	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		stop();
	}

	public enum Sound {
		BTN, BOMB
	}

}
