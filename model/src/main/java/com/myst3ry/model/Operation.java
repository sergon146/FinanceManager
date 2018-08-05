package com.myst3ry.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.myst3ry.model.converter.BigDecimalConverter;
import com.myst3ry.model.converter.CurrencyTypeConverter;
import com.myst3ry.model.converter.OperationTypeConverter;

import java.math.BigDecimal;

@Entity(tableName = "operation")
public class Operation {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @TypeConverters(OperationTypeConverter.class)
    private OperationType type;
    @TypeConverters(CurrencyTypeConverter.class)
    private CurrencyType currencyType;
    @TypeConverters(BigDecimalConverter.class)
    private BigDecimal amount;
    private String category;
    private long accountId;

    public Operation(long id,
                     OperationType type,
                     CurrencyType currencyType,
                     BigDecimal amount,
                     String category,
                     long accountId) {
        this.id = id;
        this.type = type;
        this.currencyType = currencyType;
        this.amount = amount;
        this.category = category;
        this.accountId = accountId;
    }

    public Operation(final Builder builder) {
        this.type = builder.operationType;
        this.currencyType = builder.currencyType;
        this.amount = builder.amount;
        this.category = builder.category;
        this.accountId = builder.accountId;
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

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public static final class Builder {

        private OperationType operationType;
        private CurrencyType currencyType;
        private BigDecimal amount;
        private String category;
        private long accountId;

        private Builder() {
        }

        public Builder setOperationType(final OperationType OperationType) {
            this.operationType = OperationType;
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
