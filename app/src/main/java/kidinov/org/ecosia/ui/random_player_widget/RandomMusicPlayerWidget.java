package kidinov.org.ecosia.ui.random_player_widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import kidinov.org.ecosia.R;
import kidinov.org.ecosia.util.AndroidUtil;

public class RandomMusicPlayerWidget extends AppWidgetProvider {
    private static final String TAG = RandomMusicPlayerService.class.getName();

    private static final String SHOW_START = "SHOW_START";
    private static final String SHOW_STOP = "SHOW_STOP";

    private VisualState currentVisualState = VisualState.START;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        Log.d(TAG, String.format("onReceive action - %s", action));
        switch (action) {
            case SHOW_STOP:
                currentVisualState = VisualState.STOP;
                break;
            case SHOW_START:
                currentVisualState = VisualState.START;
                break;
        }

        if (action.equals(SHOW_STOP) || action.equals(SHOW_START)) {
            updateAppWidget(context);
        } else {
            super.onReceive(context, intent);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(TAG, "onUpdate");
        updateAppWidget(context);
    }

    private void updateAppWidget(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.random_music_player_widget);

        setVisibilityState(views);

        views.setOnClickPendingIntent(R.id.start, buildStartPendingIntent(context));
        views.setOnClickPendingIntent(R.id.stop, buildStopPendingIntent(context));
        appWidgetManager.updateAppWidget(new ComponentName(context, getClass()), views);
    }

    private void setVisibilityState(RemoteViews views) {
        views.setViewVisibility(R.id.start, View.GONE);
        views.setViewVisibility(R.id.stop, View.GONE);
        views.setViewVisibility(R.id.no_music, View.GONE);

        Log.d(TAG, String.format("currentVisualState = %s", currentVisualState));
        switch (currentVisualState) {
            case START:
                views.setViewVisibility(R.id.start, View.VISIBLE);
                break;
            case STOP:
                views.setViewVisibility(R.id.stop, View.VISIBLE);
                break;
            case NO_MUSIC:
                views.setViewVisibility(R.id.no_music, View.VISIBLE);
                break;
        }
    }

    private PendingIntent buildStartPendingIntent(Context context) {
        return PendingIntent.getService(context, 0, RandomMusicPlayerService.getStartMusicIntent(context),
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private PendingIntent buildStopPendingIntent(Context context) {
        return PendingIntent.getService(context, 0, RandomMusicPlayerService.getStopMusicIntent(context),
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static Intent getShowStartButtonIntent(Context context) {
        return AndroidUtil.buildIntentWithAction(context, RandomMusicPlayerWidget.class, SHOW_START);
    }

    public static Intent getShowStopButtonIntent(Context context) {
        return AndroidUtil.buildIntentWithAction(context, RandomMusicPlayerWidget.class, SHOW_STOP);
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
        context.startService(RandomMusicPlayerService.getStopMusicIntent(context));
    }

    private enum VisualState {
        START, STOP, NO_MUSIC
    }
}

