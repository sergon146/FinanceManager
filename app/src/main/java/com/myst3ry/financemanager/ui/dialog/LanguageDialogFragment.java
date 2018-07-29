package com.myst3ry.financemanager.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.myst3ry.financemanager.R;

import java.util.Objects;

public final class LanguageDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                .setTitle(R.string.dialog_lang_title)
                .setMessage(R.string.dialog_lang_message)
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
                .create();
    }
}