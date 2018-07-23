package com.myst3ry.financemanager.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;

import com.myst3ry.financemanager.R;

import java.util.Objects;

public final class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String TAG = SettingsFragment.class.getSimpleName();

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences_main);

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(getActivity()));
        onSharedPreferenceChanged(sharedPreferences, getString(R.string.key_language));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        final Preference preference = findPreference(key);
        if (preference instanceof ListPreference) {
            final ListPreference listPreference = (ListPreference) preference;
            final int index = listPreference.findIndexOfValue(sharedPreferences.getString(key, ""));
            preference.setSummary(index >= 0 ? listPreference.getEntries()[index] : null);
        } else if (preference instanceof SwitchPreference) {
            final SwitchPreference switchPreference = (SwitchPreference) preference;
            preference.setSummary(switchPreference.isEnabled() ? switchPreference.getSummaryOn() : switchPreference.getSummaryOff());
        } else {
            preference.setSummary(sharedPreferences.getString(key, ""));
        }
    }
}
