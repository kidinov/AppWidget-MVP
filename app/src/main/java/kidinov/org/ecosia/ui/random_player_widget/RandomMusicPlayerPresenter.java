package kidinov.org.ecosia.ui.random_player_widget;

import java.util.List;
import java.util.Random;

import kidinov.org.ecosia.data.DataManager;
import kidinov.org.ecosia.data.MusicFileMetaData;
import kidinov.org.ecosia.ui.base.BasePresenter;


public class RandomMusicPlayerPresenter extends BasePresenter<RandomMusicPlayerView> {
    private DataManager dataManager;

    public RandomMusicPlayerPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(RandomMusicPlayerView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void startMusic() {
        checkViewAttached();
        List<MusicFileMetaData> mp3Files = dataManager.getMp3Files();
        if (mp3Files.isEmpty()) {
            getMvpView().showNoMusic();
            return;
        }

        getMvpView().showStopButton();
        int filePos = getRandomNumberFromZeroToNumber(mp3Files.size());
        MusicFileMetaData musicFileMetaData = mp3Files.get(filePos);
        getMvpView().playMusic(musicFileMetaData.getName(), musicFileMetaData.getPath());
    }

    public void stopMusic() {
        checkViewAttached();
        getMvpView().showStartButton();
    }

    private int getRandomNumberFromZeroToNumber(int num) {
        Random random = new Random();
        return random.nextInt(num);
    }

}
