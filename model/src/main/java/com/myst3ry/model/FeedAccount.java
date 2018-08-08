package com.myst3ry.model;

import com.example.delegateadapter.delegate.diff.IComparableItem;

public class FeedAccount extends AccountBaseItem implements IComparableItem {
    private long totalCount;
    private Balance mainBalance;
    private Balance additionalBalance;

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public Balance getMainBalance() {
        return mainBalance;
    }

    public void setMainBalance(Balance mainBalance) {
        this.mainBalance = mainBalance;
    }

    public Balance getAdditionalBalance() {
        return additionalBalance;
    }

    public void setAdditionalBalance(Balance additionalBalance) {
        this.additionalBalance = additionalBalance;
    }

    @Override
    public Object id() {
        return totalCount;
    }

    @Override
    public Object content() {
        return mainBalance.getAmount().longValue() + totalCount;
    }
}
