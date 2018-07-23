package com.myst3ry.financemanager.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.TextView;

import com.myst3ry.financemanager.BuildConfig;
import com.myst3ry.financemanager.R;

import butterknife.BindView;

public final class AboutActivity extends BaseActivity {

    @BindView(R.id.tv_about_app_version)
    TextView mAppVersionTextView;
    @BindView(R.id.tv_about_developer)
    TextView mDeveloperTextView;

    public static Intent newIntent(final Context context) {
        return new Intent(context, AboutActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setupActionBar();
        setAboutInfo();
        setListeners();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupActionBar() {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setAboutInfo() {
        mAppVersionTextView.setText(String.format(getString(R.string.text_about_app_version), BuildConfig.VERSION_NAME));
    }

    //todo hided contact info
    private void setListeners() {
        mDeveloperTextView.setOnClickListener(v -> contactDeveloper());
    }

    private void contactDeveloper() {
        final String subject = String.format(getString(R.string.mail_subject), BuildConfig.VERSION_NAME);
        final String body = String.format(getString(R.string.mail_body), Build.MANUFACTURER,
                Build.MODEL, Build.DEVICE, Build.ID, Build.VERSION.RELEASE, Build.VERSION.SDK_INT);

        final Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
        final String uriString = "mailto:" + Uri.encode(getString(R.string.mail_address)) +
                "?subject=" + Uri.encode(subject) + "&body=" + Uri.encode(body);

        sendIntent.setData(Uri.parse(uriString));
        startActivity(Intent.createChooser(sendIntent, getString(R.string.mail_chooser_text)));
    }
}
