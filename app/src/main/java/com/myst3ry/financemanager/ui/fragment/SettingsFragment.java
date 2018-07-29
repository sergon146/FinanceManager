package com.myst3ry.financemanager.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;

import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.ui.dialog.LanguageDialogFragment;

import java.util.Locale;
import java.util.Objects;

public final class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String TAG = SettingsFragment.class.getSimpleName();

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences_main);

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(getActivity()));
        onSharedPreferenceChanged(prefs, getString(R.string.key_language));
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        final Preference preference = findPreference(key);
        if (preference instanceof ListPreference) {
            final ListPreference listPreference = (ListPreference) preference;
            final String defLang = Locale.getDefault().getLanguage();

            final int index = listPreference.findIndexOfValue(sharedPreferences.getString(key, defLang));
            preference.setSummary(index >= 0 ? listPreference.getEntries()[index] : null);
            listPreference.setValueIndex(index >= 0 ? index : 0);

            //todo fix dialog (not important)
            if (key.equals(getString(R.string.key_language))) {
                if (listPreference.getValue().equals(defLang))
                    //Need to reboot App Process for Runtime changes
                    //LocaleManager.setNewLocale(getActivity(), sharedPreferences.getString(key, defLang));
                    onLanguageChanged();
            }

        } else if (preference instanceof SwitchPreference) {
            final SwitchPreference switchPreference = (SwitchPreference) preference;
            preference.setSummary(switchPreference.isEnabled() ? switchPreference.getSummaryOn() : switchPreference.getSummaryOff());
        } else {
            preference.setSummary(sharedPreferences.getString(key, ""));
        }
    }

    private void onLanguageChanged() {
        new LanguageDialogFragment().show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), null);
    }
}
