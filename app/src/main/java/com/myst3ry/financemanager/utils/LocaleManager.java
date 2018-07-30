package com.myst3ry.financemanager.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.preference.PreferenceManager;

import com.myst3ry.financemanager.R;

import java.util.Locale;

public final class LocaleManager {

    public static void setLocale(final Context context) {
        final String language = getLanguage(context);
        updateResources(context, language);
    }

    //todo change language in runtime
    @SuppressLint("unused")
    public static void setNewLocale(final Context context, final String language) {
        updateResources(context, language);
    }

    private static String getLanguage(final Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.key_language), Locale.getDefault().getLanguage());
    }

    //todo api 26 conflict
    private static void updateResources(final Context context, final String language) {
        final Locale locale = new Locale(language);
        Locale.setDefault(locale);

        final Resources res = context.getResources();
        final Configuration config = new Configuration(res.getConfiguration());
        config.setLocale(locale);
        res.updateConfiguration(config, res.getDisplayMetrics());
    }
}