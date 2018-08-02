package com.myst3ry.financemanager.ui.about;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.ui.base.BaseFragment;
import com.myst3ry.financemanager.utils.Utils;

import butterknife.OnClick;

public final class AboutFragment extends BaseFragment {

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @OnClick(R.id.about_developer)
    void onDeveloperClick() {
        final String subject = getString(R.string.mail_subject);
        final String body = getString(R.string.mail_body);
        final String mail = getString(R.string.mail_address);
        Intent contactIntent = Utils.createContactIntent(subject, body, mail);
        startActivity(Intent.createChooser(contactIntent, getString(R.string.mail_chooser_text)));
    }

    @Override
    public String getScreenTag() {
        return "BalanceFragment";
    }
}
