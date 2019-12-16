package net.sahal.baking_app.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import net.sahal.baking_app.Activity.MainActivity;
import net.sahal.baking_app.R;
import net.sahal.baking_app.models.BakingList;
import net.sahal.baking_app.models.Ingredients;

import java.util.List;

public class BakingWidget extends AppWidgetProvider {

    private static final List<BakingList> List = MainActivity.List;
    private static int position;
    private static String result = "";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        position = BakingWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget);

        if (position > -1) {
            List<Ingredients> ingredient = List.get(position).getIngredients();

            for (int i = 0; i < ingredient.size(); i++) {
                result += " " + (i + 1) + " - " + ingredient.get(i).getQuantity() + " " +
                        ingredient.get(i).getMeasure() + " ( " + ingredient.get(i).getIngredient() + " )";
                if (i < ingredient.size() - 1) {
                    result += "\n";
                }
            }
            views.setTextViewText(R.id.appwidget_text, result);
        }
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            BakingWidgetConfigureActivity.deleteTitlePref(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}

