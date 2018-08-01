package com.myst3ry.financemanager.ui.activity.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import com.myst3ry.financemanager.BuildConfig;
import com.myst3ry.financemanager.ui.activity.BasePresenter;

public final class AboutPresenter extends BasePresenter<AboutView> {

    public void contactDeveloper(final String mailSubject, final String mailBody, final String mailAddress) {
        final String subject = String.format(mailSubject, BuildConfig.VERSION_NAME);
        final String body = String.format(mailBody, Build.MANUFACTURER,
                Build.MODEL, Build.DEVICE, Build.ID, Build.VERSION.RELEASE, Build.VERSION.SDK_INT);

        final Intent intent = new Intent(Intent.ACTION_SENDTO);
        final String uriString = "mailto:" + Uri.encode(mailAddress) +
                "?subject=" + Uri.encode(subject) + "&body=" + Uri.encode(body);

        intent.setData(Uri.parse(uriString));
        mView.contact(intent);
    }
}
