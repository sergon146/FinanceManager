package com.myst3ry.model;

public class PatternAccount extends AccountBaseItem {
    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public Object content() {
        return total;
    }
}
