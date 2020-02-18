package com.bahaa.github.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class AppUtils {
    public static void setLanguage(String language, Activity from) {
        Resources activityRes = from.getResources();
        Configuration activityConf = activityRes.getConfiguration();
        Locale newLocale = new Locale(language);
        activityConf.setLocale(newLocale);
        activityRes.updateConfiguration(activityConf, activityRes.getDisplayMetrics());

        Resources applicationRes = from.getApplicationContext().getResources();
        Configuration applicationConf = applicationRes.getConfiguration();
        applicationConf.setLocale(newLocale);
        applicationRes.updateConfiguration(applicationConf,
                applicationRes.getDisplayMetrics());
    }
    public static void setLanguageWithoutReload(String language, Activity from, Class to) {
        Locale languageLocale = new Locale(language);
        Locale.setDefault(languageLocale);
        Configuration languageConfig = new Configuration();
        languageConfig.locale = languageLocale;
        from.getResources().updateConfiguration(languageConfig, from.getResources().getDisplayMetrics());
    }
    public static GridLayoutManager initVerticalRV(Context context, RecyclerView recyclerView, int spanCount, int space) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, spanCount, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(space, spanCount, true));
        recyclerView.setNestedScrollingEnabled(false);
        return gridLayoutManager;
    }
    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
    public static GridLayoutManager initHorizontalRV(Context context, RecyclerView recyclerView, int spanCount) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, spanCount, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(10, spanCount, false));
        recyclerView.setNestedScrollingEnabled(false);
        return gridLayoutManager;
    }
    public static LinearLayoutManager initVerticalRVLinear(Context context, RecyclerView recyclerView, int spanCount) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        // recyclerView.addItemDecoration(new SpacesItemDecoration(10, spanCount, false));
        recyclerView.setNestedScrollingEnabled(false);
        return linearLayoutManager;
    }
    public static void fillSpinner(Context context, List list, Spinner spinner) {
        ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }
    public static void hideKeyboard(Activity context) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (context.getCurrentFocus() != null)
                imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            Log.e("MultiBackStack", "Failed to add fragment to back stack", e);
        }
    }
}
