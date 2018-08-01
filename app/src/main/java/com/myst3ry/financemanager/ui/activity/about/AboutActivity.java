package com.myst3ry.financemanager.ui.activity.about;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.TextView;

import com.myst3ry.financemanager.BuildConfig;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.ui.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public final class AboutActivity extends BaseActivity implements AboutView {

    @BindView(R.id.tv_about_app_version)
    TextView mAppVersionTextView;

    private AboutPresenter mAboutPresenter;

    public static Intent newIntent(final Context context) {
        return new Intent(context, AboutActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setupActionBar();
        setAboutInfo();

        initPresenter();
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

    private void initPresenter() {
        mAboutPresenter = new AboutPresenter();
        mAboutPresenter.attachView(this);
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

    @OnClick(R.id.tv_about_developer)
    void onDeveloperClick() {
        final String subject = getString(R.string.mail_subject);
        final String body = getString(R.string.mail_body);
        final String mail = getString(R.string.mail_address);
        mAboutPresenter.contactDeveloper(subject, body, mail);
    }

    public void contact(final Intent intent) {
        startActivity(Intent.createChooser(intent, getString(R.string.mail_chooser_text)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isDestroyed()) {
            mAboutPresenter.detachView();
        }
    }
}
