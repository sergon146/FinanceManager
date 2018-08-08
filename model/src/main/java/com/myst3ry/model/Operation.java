package com.myst3ry.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.example.delegateadapter.delegate.diff.IComparableItem;
import com.myst3ry.model.converter.BigDecimalConverter;
import com.myst3ry.model.converter.CurrencyTypeConverter;
import com.myst3ry.model.converter.DateConverter;
import com.myst3ry.model.converter.OperationTypeConverter;

import java.math.BigDecimal;
import java.util.Date;

@Entity(tableName = "operation")
public class Operation implements IComparableItem {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    @TypeConverters(OperationTypeConverter.class)
    private OperationType type;
    @TypeConverters(CurrencyTypeConverter.class)
    private CurrencyType currencyType;
    @TypeConverters(BigDecimalConverter.class)
    private BigDecimal amount;
    private String category;
    @TypeConverters(DateConverter.class)
    private Date date;
    private long accountId;
    private boolean isActive = true;
    @Ignore
    private Account account;

    public Operation(long id,
                     String title,
                     OperationType type,
                     CurrencyType currencyType,
                     BigDecimal amount,
                     String category,
                     Date date,
                     long accountId,
                     boolean isActive) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.currencyType = currencyType;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.accountId = accountId;
        this.isActive = isActive;
    }

    public Operation(Operation operation) {
        this.type = operation.type;
        this.title = operation.getTitle();
        this.currencyType = operation.currencyType;
        this.amount = operation.amount;
        this.category = operation.category;
        this.accountId = operation.accountId;
    }

    public Operation(final Builder builder) {
        this.type = builder.operationType;
        this.title = builder.title;
        this.currencyType = builder.currencyType;
        this.amount = builder.amount;
        this.category = builder.category;
        this.accountId = builder.accountId;
        this.date = new Date();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public OperationType getType() {
        return type;
    }

    public void setType(final OperationType type) {
        this.type = type;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(final CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public Object id() {
        return id;
    }

    @Override
    public Object content() {
        return id + accountId + amount.toString();
    }

    public static final class Builder {

        private String title;
        private OperationType operationType;
        private CurrencyType currencyType;
        private BigDecimal amount;
        private String category;
        private long accountId;

        private Builder() {
        }

        public Builder setTitle(final String title) {
            this.title = title;
            return this;
        }

        public Builder setOperationType(final OperationType operationType) {
            this.operationType = operationType;
            return this;
        }

        public Builder setCurrencyType(final CurrencyType currencyType) {
            this.currencyType = currencyType;
            return this;
        }

        public Builder setAmount(final BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder setCategory(final String category) {
            this.category = category;
            return this;
        }

        public Builder setAccountId(final long accountId) {
            this.accountId = accountId;
            return this;
        }

        public Operation build() {
            return new Operation(this);
        }
    }
}
