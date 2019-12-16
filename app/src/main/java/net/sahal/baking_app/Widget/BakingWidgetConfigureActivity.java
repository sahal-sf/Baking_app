package net.sahal.baking_app.Widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import net.sahal.baking_app.Activity.MainActivity;
import net.sahal.baking_app.R;
import net.sahal.baking_app.models.BakingList;

import java.util.List;

public class BakingWidgetConfigureActivity extends Activity {

    private static final String PREFS_NAME = "net.sahal.baking_app.Widget.BakingWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    private final List<BakingList> List = MainActivity.List;
    private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private RadioGroup radioGroup;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = BakingWidgetConfigureActivity.this;

            System.out.println(radioGroup.getCheckedRadioButtonId() + " --jdasdfjjnjasdfnjnj");

            saveTitlePref(context, mAppWidgetId, radioGroup.getCheckedRadioButtonId());

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            BakingWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        }
    };

    public BakingWidgetConfigureActivity() {
        super();
    }

    static void saveTitlePref(Context context, int appWidgetId, int position) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putInt(PREF_PREFIX_KEY + appWidgetId, position);
        prefs.apply();
    }

    static int loadTitlePref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        int position = prefs.getInt(PREF_PREFIX_KEY + appWidgetId, -1);
        return position;
    }

    static void deleteTitlePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setResult(RESULT_CANCELED);

        setContentView(R.layout.baking_widget_configure);

        radioGroup = findViewById(R.id.radio_group);
        for (int i = 0; i < List.size(); i++) {
            RadioButton radioBtn = new RadioButton(this);
            radioBtn.setId(i);
            radioBtn.setText(List.get(i).getName());
            radioGroup.addView(radioBtn);
        }
        radioGroup.check(0);

        findViewById(R.id.add_button).setOnClickListener(mOnClickListener);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }
    }
}

