package kidinov.org.ecosia.ui.random_player_widget;


import kidinov.org.ecosia.ui.base.MvpView;

public interface RandomMusicPlayerView extends MvpView {
    void showStartButton();

    void showStopButton();

    void showNoMusic();

    void playMusic(String name, String filePath);
}
