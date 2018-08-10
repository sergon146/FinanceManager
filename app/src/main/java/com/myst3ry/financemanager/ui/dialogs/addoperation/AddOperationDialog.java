package com.myst3ry.financemanager.ui.dialogs.addoperation;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.ui.base.dialog.BaseDialogMvpFragment;
import com.myst3ry.financemanager.ui.dialogs.SelectionDialogFragment;
import com.myst3ry.financemanager.ui.dialogs.template.TemplateDialog;
import com.myst3ry.financemanager.utils.Utils;
import com.myst3ry.financemanager.utils.formatter.balance.BalanceFormatterFactory;
import com.myst3ry.model.Account;
import com.myst3ry.model.CurrencyType;
import com.myst3ry.model.Operation;
import com.myst3ry.model.OperationType;
import com.myst3ry.model.PeriodicOperation;
import com.myst3ry.model.Template;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import icepick.State;

public class AddOperationDialog extends BaseDialogMvpFragment<AddOperationPresenter>
        implements AddOperationView {

    private static final String ACCOUNT_ID = "ACCOUNT_ID";
    private static final String OPERATION_ID = "OPERATION_ID";
    @Inject
    @InjectPresenter
    public AddOperationPresenter presenter;
    @BindView(R.id.account_type_title)
    TextView accountTypeTitle;
    @BindView(R.id.account_title)
    TextView accountTitle;
    @BindView(R.id.amount)
    TextView accountAmount;
    @BindView(R.id.operation_type)
    TextView operationTextView;
    @BindView(R.id.category_label)
    TextView categoryTextView;
    @BindView(R.id.title_edit)
    EditText titleEditText;
    @BindView(R.id.amount_edit)
    EditText amountEditText;
    @BindView(R.id.periodic_edit)
    EditText periodicEdit;
    @BindView(R.id.save_template)
    CheckBox saveTemplate;
    @BindView(R.id.periodic_group)
    Group periodic_group;

    @State
    OperationType currentOperationType;
    @State
    int operationIndex, currencyIndex, categoryIndex, accountIndex = 0;
    private long accountId;
    private BalanceFormatterFactory formatterFactory = new BalanceFormatterFactory();
    private ArrayList<String> operationTitles, categoryTitles, accountTitles = new ArrayList<>();
    private List<Account> accounts;
    private boolean isPeriodicToggle = false;

    public static AddOperationDialog newInstance(long accountId, long operaionId) {
        AddOperationDialog dialog = new AddOperationDialog();
        Bundle bundle = new Bundle();
        bundle.putLong(ACCOUNT_ID, accountId);
        bundle.putLong(OPERATION_ID, operaionId);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    @ProvidePresenter
    protected AddOperationPresenter providePresenter() {
        return presenter;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            accountId = getArguments().getLong(ACCOUNT_ID, -1);
            long operationId = getArguments().getLong(OPERATION_ID, -1);
            if (operationId != -1) {
                getPresenter().loadOperation(operationId);
            } else {
                getPresenter().loadData();
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTitlesLists();
        accountTypeTitle.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            int width = ViewGroup.LayoutParams.WRAP_CONTENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_add_operation, container, false);
    }

    private void initTitlesLists() {
        operationTitles = Utils.getOperationTitles(getContext());
        categoryTitles = new ArrayList<>(Arrays.asList(getResources()
                .getStringArray(R.array.arr_expense_categories)));
        setDefaultValues();
    }

    private void setDefaultValues() {
        operationTextView.setText(operationTitles.get(operationIndex));
        categoryTextView.setText(categoryTitles.get(categoryIndex));
        currentOperationType = OperationType.values()[0];
    }

    @OnClick( {R.id.operation_type_title, R.id.operation_type})
    void selectOperationType() {
        final SelectionDialogFragment dialog =
                SelectionDialogFragment.newInstance(operationTitles, operationIndex);
        dialog.setOnDialogSelectionListener(selectedIndex -> {
            currentOperationType = OperationType.values()[selectedIndex];
            final String type = operationTitles.get(selectedIndex);
            replaceCategoryList(type);
            operationTextView.setText(type);
            operationIndex = selectedIndex;
        });

        dialog.show(getActivity().getSupportFragmentManager(), null);
    }

    @OnClick( {R.id.account_type_title, R.id.account_title, R.id.amount})
    void selectAccount() {
        final SelectionDialogFragment dialog =
                SelectionDialogFragment.newInstance(accountTitles, accountIndex);
        dialog.setOnDialogSelectionListener(selectedIndex -> {
            Account account = accounts.get(selectedIndex);
            accountAmount.setText(formatterFactory.create(account.getCurrencyType())
                    .formatBalance(account.getBalance()));
            accountTitle.setText(account.getTitle());
            accountIndex = selectedIndex;
        });

        dialog.show(getActivity().getSupportFragmentManager(), null);
    }

    @OnClick( {R.id.category_title, R.id.category_label})
    void selectCategoryLabel() {
        final SelectionDialogFragment dialog =
                SelectionDialogFragment.newInstance(categoryTitles, categoryIndex);
        dialog.setOnDialogSelectionListener(selectedIndex -> {
            categoryTextView.setText(categoryTitles.get(selectedIndex));
            categoryIndex = selectedIndex;
        });

        dialog.show(getActivity().getSupportFragmentManager(), null);
    }

    private void replaceCategoryList(final String type) {
        if (type.equalsIgnoreCase(getString(R.string.expense))) {
            categoryTitles = new ArrayList<>(Arrays.asList(getResources()
                    .getStringArray(R.array.arr_expense_categories)));
        } else if (type.equalsIgnoreCase(getString(R.string.income))) {
            categoryTitles = new ArrayList<>(Arrays.asList(getResources()
                    .getStringArray(R.array.arr_income_categories)));
        }

        categoryIndex = 0;
        categoryTextView.setText(categoryTitles.get(categoryIndex));
    }

    @OnClick(R.id.add)
    void onAddClick() {
        createOperation();
    }

    @OnClick(R.id.cancel)
    void onCancelClick() {
        dismiss();
    }

    private void createOperation() {
        if (accountTitle.equals(getString(R.string.none))) {
            showToast(R.string.empty_account);
            return;
        }

        if (titleEditText.getText().toString().isEmpty()) {
            showToast(R.string.empty_title);
            return;
        }


        final String amount = amountEditText.getText().toString();
        if (amount.isEmpty()) {
            showToast(getString(R.string.err_empty_field));
            return;
        }

        if (BigDecimal.ZERO.equals(new BigDecimal(Float.valueOf(amount)))) {
            showToast(R.string.zero_amount);
            return;
        }

        long dayRepeat = 0L;
        if (isPeriodicToggle) {
            String repeat = periodicEdit.getText().toString();
            try {
                dayRepeat = Long.parseLong(repeat);
            } catch (NumberFormatException nfe) {
                showToast(R.string.invalid_value);
                return;
            }

            if (BigDecimal.ZERO.equals(new BigDecimal(dayRepeat))) {
                showToast(R.string.repeat_count_zero);
                return;
            } else {
                if (repeat.isEmpty()) {
                    showToast(R.string.repeat_count_empty);
                }
            }
        }

        PeriodicOperation periodic = new PeriodicOperation(dayRepeat);
        Operation.Builder builder = Operation.newBuilder()
                .setAmount(new BigDecimal(amount))
                .setTitle(titleEditText.getText().toString())
                .setCurrencyType(CurrencyType.values()[currencyIndex])
                .setOperationType(currentOperationType)
                .setCategory(categoryTextView.getText().toString());

        Account account;
        if (accounts != null && !accounts.isEmpty()) {
            account = accounts.get(accountIndex);
            builder.setAccountId(account.getId());
        } else {
            account = null;
        }

        Operation operation = builder.build();

        boolean isSaveToTemplate = saveTemplate.isChecked();
        getPresenter().performOperation(account, operation, periodic, isSaveToTemplate);

    }

    private void showAccountData(long accountId) {
        for (Account account : accounts) {
            if (account.getId() == accountId) {
                showAccountData(account);
                break;
            }
        }
    }

    @Override
    public void showAccountData(Account account) {
        accountTitle.setText(account.getTitle());
        accountAmount.setText(formatterFactory.create(account.getCurrencyType())
                .formatBalance(account.getBalance()));
    }

    @Override
    public void successPerform() {
        Toast toast = Toast.makeText(getContext(), R.string.operation_success, Toast.LENGTH_SHORT);
        TextView toastMessage = toast.getView().findViewById(android.R.id.message);
        toastMessage.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_vader, 0, 0, 0);
        toast.show();

        dismiss();
    }

    @Override
    public void showAccounts(List<Account> accounts) {
        this.accounts = accounts;
        accountTitles.clear();
        accountIndex = 0;
        for (int i = 0; i < accounts.size(); i++) {
            Account account = accounts.get(i);
            accountTitles.add(account.getTitle());
            if (account.getId() == accountId) {
                accountIndex = i;
                showAccountData(accountId);
                break;
            }
        }
    }

    @Override
    public void showOperation(Operation operation) {
        showAccountData(operation.getAccount());
        accountTypeTitle.setVisibility(View.GONE);
        accountTitle.setEnabled(false);
        accountAmount.setEnabled(false);

        operationTextView.setText(Utils.getOperationTypeTitle(getContext(), operation.getType()));
        categoryTextView.setText(operation.getCategory());
        amountEditText.setText(operation.getAmount().toPlainString());
        titleEditText.setText(operation.getTitle());
    }

    @OnCheckedChanged(R.id.periodic_switch)
    void onPeriodicToggle(boolean isToggle) {
        isPeriodicToggle = isToggle;
        if (isToggle) {
            periodic_group.setVisibility(View.VISIBLE);
        } else {
            periodic_group.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.template)
    void onTemplateClick() {
        TemplateDialog dialog = TemplateDialog.newInstance();
        dialog.setListener(template -> insertTemplate(template));
        dialog.show(getFragmentManager(), dialog.getTag());
    }

    private void insertTemplate(Template template) {
        amountEditText.setText(template.getAmount().toPlainString());
        titleEditText.setText(template.getTitle());

        for (int i = 0; i < operationTitles.size(); i++) {
            String operation = operationTitles.get(i);
            if (operation.equals(Utils.getOperationTypeTitle(getContext(), template.getType()))) {
                operationTextView.setText(operation);
                operationIndex = i;
            }
        }

        for (int i = 0; i < categoryTitles.size(); i++) {
            String category = categoryTitles.get(i);
            if (category.equals(template.getCategory())) {
                categoryIndex = i;
                categoryTextView.setText(category);
                break;
            }
        }
    }
}
