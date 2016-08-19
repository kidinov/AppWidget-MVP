package kidinov.org.ecosia.ui.random_player_widget;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import kidinov.org.ecosia.R;
import kidinov.org.ecosia.data.DataManager;
import kidinov.org.ecosia.util.AndroidUtil;

public class RandomMusicPlayerService extends Service implements RandomMusicPlayerView {
    private static final String TAG = RandomMusicPlayerService.class.getName();

    private static final String STOP_ACTION = "STOP_ACTION";
    private static final String START_ACTION = "START_ACTION";

    private RandomMusicPlayerPresenter presenter;
    private MediaPlayer mediaPlayer;


    public RandomMusicPlayerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        presenter = new RandomMusicPlayerPresenter(new DataManager(this));
        presenter.attachView(this);

        mediaPlayer = new MediaPlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopMusic();
        presenter.detachView();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        Log.d(TAG, String.format("onStartCommand action - %s", action));
        switch (action) {
            case START_ACTION:
                presenter.startMusic();
                break;
            case STOP_ACTION:
                presenter.stopMusic();
                stopSelf();
                break;
        }

        return START_REDELIVER_INTENT;
    }

    private void stopMusic() {
        mediaPlayer.release();
    }

    public static Intent getStartMusicIntent(Context ctx) {
        Log.d(TAG, "getStartMusicIntent");
        return AndroidUtil.buildIntentWithAction(ctx, RandomMusicPlayerService.class, START_ACTION);
    }

    public static Intent getStopMusicIntent(Context ctx) {
        return AndroidUtil.buildIntentWithAction(ctx, RandomMusicPlayerService.class, STOP_ACTION);
    }

    @Override
    public void showStartButton() {
        sendBroadcast(RandomMusicPlayerWidget.getShowStartButtonIntent(this));
    }

    @Override
    public void showStopButton() {
        sendBroadcast(RandomMusicPlayerWidget.getShowStopButtonIntent(this));
    }

    @Override
    public void showNoMusic() {
        Toast.makeText(this, R.string.no_music_files_found, Toast.LENGTH_LONG).show();
    }

    @Override
    public void playMusic(String name, String filePath) {
        try {
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();
            mediaPlayer.start();
            Toast.makeText(this, getString(R.string.file_playing, name), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e(TAG, String.format("can't play file - %s", filePath), e);
            Toast.makeText(this, R.string.cant_play_music, Toast.LENGTH_LONG).show();
        }
    }
}
