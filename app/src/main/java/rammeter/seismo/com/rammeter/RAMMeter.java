package rammeter.seismo.com.rammeter;

import android.app.ActivityManager;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.RemoteViews;

import java.util.IntSummaryStatistics;

/**
 * Implementation of App Widget functionality.
 */
public class RAMMeter extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        //Percentage can be calculated for API 16+
        Double percentAvail =  (mi.availMem / (double) mi.totalMem) * 100;
        // Construct the RemoteViews object
        String s = Integer.toString(100 - percentAvail.intValue()) + "%";
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.rammeter);
        views.setProgressBar(R.id.circular_progress_bar, 100, (100 - percentAvail.intValue()), false);
        views.setTextViewText(R.id.appwidget_text, s);

        // Instruct the widget manager to update the widget
        Log.d("ABC", "Ram Usage: " + s);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

