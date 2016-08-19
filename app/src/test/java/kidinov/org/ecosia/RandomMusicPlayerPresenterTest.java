package kidinov.org.ecosia;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kidinov.org.ecosia.data.DataManager;
import kidinov.org.ecosia.data.MusicFileMetaData;
import kidinov.org.ecosia.ui.random_player_widget.RandomMusicPlayerPresenter;
import kidinov.org.ecosia.ui.random_player_widget.RandomMusicPlayerView;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class RandomMusicPlayerPresenterTest {
    @Mock
    DataManager mockedDataManager;
    @Mock
    RandomMusicPlayerView mockedView;
    private RandomMusicPlayerPresenter presenter;

    @Before
    public void setUp() {
        presenter = new RandomMusicPlayerPresenter(mockedDataManager);
        presenter.attachView(mockedView);
    }

    @After
    public void detachView() {
        presenter.detachView();
    }

    @Test
    public void stopMusicCallShowStart() {
        presenter.stopMusic();
        verify(mockedView).showStartButton();
    }

    @Test
    public void startMusicCallShowNoMusicIfNoMusic() {
        doReturn(Collections.emptyList()).when(mockedDataManager).getMp3Files();
        presenter.startMusic();
        verify(mockedView).showNoMusic();
    }

    @Test
    public void startMusicCallShowStopMusicAndPlayMusicIfThereIsMusic() {
        List<MusicFileMetaData> files = new ArrayList<>();
        String fileName = "name";
        String filePath = "path";
        files.add(new MusicFileMetaData(fileName, filePath));

        doReturn(files).when(mockedDataManager).getMp3Files();
        presenter.startMusic();

        verify(mockedView).showStopButton();
        verify(mockedView).playMusic(fileName, filePath);
    }
}