package com.myst3ry.financemanager;

import android.support.annotation.NonNull;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.myst3ry.financemanager.ui.main.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.internal.util.Checks.checkNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class AddOperationTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void showEmptyAmountToast() {
        openCreateOperationCard();

        onView(withId(R.id.add)).perform(click());
        onView(withText(R.string.err_empty_field)).inRoot(withDecorView(
                not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

    @Test
    public void showZeroAmountToast() {
        openCreateOperationCard();

        onView(withId(R.id.amount_edit)).perform(replaceText("0.00"));
        onView(withId(R.id.add)).perform(click());
        onView(withText(R.string.zero_amount)).inRoot(withDecorView(
                not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

    @Test
    public void addOperation() {
        openCreateOperationCard();

        onView(withId(R.id.amount_edit)).perform(replaceText("100.0"));
        onView(withId(R.id.add)).perform(click());
        onView(withId(R.id.operation_recycler))
                .check(matches(hasDescendant(withText("-₽ 100.00"))));
    }

    @Test
    public void addPeriodicOperation() {
        openCreateOperationCard();

        onView(withId(R.id.amount_edit)).perform(replaceText("200.0"));
        onView(withId(R.id.periodic_switch)).perform(click());
        onView(withId(R.id.periodic_edit)).check(matches(isDisplayed()));
        onView(withId(R.id.periodic_edit)).perform(replaceText("6"));
        onView(withId(R.id.add)).perform(click());
        onView(withId(R.id.periodic_icon)).perform(click());
        onView(withId(R.id.operation_recycler))
                .check(matches(atPosition(0, hasDescendant(withText("6")))));
        onView(withId(R.id.operation_recycler))
                .check(matches(atPosition(0, hasDescendant(withText("-₽ 200.00")))));


    }

    private void openCreateOperationCard() {
        onView(withId(R.id.account_rv)).perform(RecyclerViewActions
                .actionOnItemAtPosition(0, click()));
        onView(withId(R.id.fab_add)).perform(click());
    }

    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    // has no item on such position
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }
}
