package com.myst3ry.model;

import android.arch.persistence.room.Ignore;

import com.example.delegateadapter.delegate.diff.IComparableItem;

public class AccountBaseItem implements IComparableItem {
    @Ignore
    private long id;
    @Ignore
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public Object id() {
        return id;
    }

    @Override
    public Object content() {
        return null;
    }
}
