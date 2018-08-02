package com.myst3ry.financemanager.ui.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.myst3ry.financemanager.BuildConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class SelectionDialogFragment extends DialogFragment
        implements DialogInterface.OnClickListener {

    private static final String ARG_ITEMS_LIST =
            BuildConfig.APPLICATION_ID + "arg.ITEMS_LIST";
    private static final String ARG_SELECTED_INDEX =
            BuildConfig.APPLICATION_ID + "arg.SELECTED_INDEX";

    private List<String> items;
    private int selectedIndex;
    private OnDialogSelectionListener callback;

    public static SelectionDialogFragment newInstance(final ArrayList<String> items,
                                                      final int index) {
        final SelectionDialogFragment fragment = new SelectionDialogFragment();
        final Bundle args = new Bundle();
        args.putStringArrayList(ARG_ITEMS_LIST, items);
        args.putInt(ARG_SELECTED_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.items = getArguments().getStringArrayList(ARG_ITEMS_LIST);
            this.selectedIndex = getArguments().getInt(ARG_SELECTED_INDEX, 0);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder =
                new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        builder.setSingleChoiceItems(items.toArray(new String[items.size()]),
                selectedIndex, this);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        selectedIndex = which;
        callback.onSelectedItem(selectedIndex);
        this.dismiss();
    }

    public void setOnDialogSelectionListener(final OnDialogSelectionListener callback) {
        this.callback = callback;
    }

    public interface OnDialogSelectionListener {
        void onSelectedItem(final int selectedIndex);
    }
}
