package com.myst3ry.model;

public class PeriodicAccount extends AccountBaseItem {
    private long total;
    private long active;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getActive() {
        return active;
    }

    public void setActive(long active) {
        this.active = active;
    }

    @Override
    public Object content() {
        return total + active;
    }
}
