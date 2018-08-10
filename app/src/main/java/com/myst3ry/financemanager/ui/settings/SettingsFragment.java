package com.myst3ry.financemanager.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.util.TypedValue;
import android.view.View;

import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.ui.base.BaseActivity;

import java.util.Locale;
import java.util.Objects;

public final class SettingsFragment extends PreferenceFragmentCompat
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String TAG = SettingsFragment.class.getSimpleName();

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences_main);

        final SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(Objects.requireNonNull(getActivity()));
        onSharedPreferenceChanged(prefs, getString(R.string.key_language));

        ((BaseActivity) getActivity()).setScreenTitle(R.string.tab_settings);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int topPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (int) getResources().getDimension(R.dimen.detail_top_padding),
                getResources().getDisplayMetrics());
        view.setPadding(view.getPaddingStart(), view.getPaddingTop() + topPadding,
                view.getPaddingEnd(), view.getPaddingBottom());
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        final Preference preference = findPreference(key);
        if (preference instanceof ListPreference) {
            final ListPreference listPreference = (ListPreference) preference;
            final String defLang = Locale.getDefault().getLanguage();

            final int index = listPreference
                    .findIndexOfValue(sharedPreferences.getString(key, defLang));
            preference.setSummary(index >= 0 ? listPreference.getEntries()[index] : null);
            listPreference.setValueIndex(index >= 0 ? index : 0);

        } else if (preference instanceof SwitchPreference) {
            final SwitchPreference switchPreference = (SwitchPreference) preference;
            preference.setSummary(switchPreference.isEnabled()
                    ? switchPreference.getSummaryOn()
                    : switchPreference.getSummaryOff());
        }
    }
}
