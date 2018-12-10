package org.davenportschools.westhigh;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

public class LunchMenuFragment extends Fragment {
    private TextView todaysDateTextView, tomorrowsMenuTextView, todaysMenuTextView, dontKnowTextView, todaysMenuHeader, tomorrowsMenuHeader;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lunch_menu, container, false);

        todaysDateTextView = view.findViewById(R.id.todays_lunch_date_textView);
        todaysMenuTextView = view.findViewById(R.id.todays_menu_textView);
        tomorrowsMenuTextView = view.findViewById(R.id.tomorrows_menu_textView);
        dontKnowTextView = view.findViewById(R.id.dontknow_textView);
        todaysMenuHeader = view.findViewById(R.id.todays_menu_header);
        tomorrowsMenuHeader = view.findViewById(R.id.tomorrows_menu_header);

        if (isWeekend()) {
            setTextViewsVisible(false);
        } else if (isMonday()) {
            setTextViewsVisible(true);
            todaysMenuTextView.setText("Taco Nacho\nChicken Nuggets\nBosco Sticks\nCheeseburger\nGrilled Chicken Sandwich\n");
            tomorrowsMenuTextView.setText("Walking Taco\nChicken Chili Crispito\nSpicy/Popcorn Chicken\nFrench Bread Pizza\nBaked Pasta or Taco Bake\nSpicy/Chicken Fillet Sandwich\nMashed Potato Bowl\n");
        } else if (isTuesday()){
            setTextViewsVisible(true);
            todaysMenuTextView.setText("Walking Taco\nChicken Chili Crispito\nSpicy/Popcorn Chicken\nFrench Bread Pizza\nBaked Pasta or Taco Bake\nSpicy/Chicken Fillet Sandwich\nMashed Potato Bowl\n");
            tomorrowsMenuTextView.setText("Papa John's Pizza\nPork Nacho\nChicken Nuggets\nCheeseburger\nGrilled Chicken Sandwich\n");
        } else if (isWednesday()){
            setTextViewsVisible(true);
            todaysMenuTextView.setText("Papa John's Pizza\nPork Nacho\nChicken Nuggets\nCheeseburger\nGrilled Chicken Sandwich\n");
            tomorrowsMenuTextView.setText("Walking Taco\nChicken Enchilada\nPopcorn Chicken\nSpicy Popcorn Chicken\nFrench Bread Pizza\nChicken Filet Pizza\nSpicy Chicken Sandwich\nOrange Chicken\n");
        } else if (isThursday()){
            setTextViewsVisible(true);
            todaysMenuTextView.setText("Walking Taco\nChicken Enchilada\nPopcorn Chicken\nSpicy Popcorn Chicken\nFrench Bread Pizza\nChicken Filet Pizza\nSpicy Chicken Sandwich\nOrange Chicken\n");
            tomorrowsMenuTextView.setText("Taco Nacho\nBoneless Wings\nSpicy Wings\nBosco Sticks\nCheeseburger\nGrilled Chicken Sandwich\nFish Sandwich\nBaked Potato\n");
        } else if (isFriday()) {
            setTextViewsVisible(true);
            todaysMenuTextView.setText("Taco Nacho\nBoneless Wings\nSpicy Wings\nBosco Sticks\nCheeseburger\nGrilled Chicken Sandwich\nFish Sandwich\nBaked Potato\n");
            tomorrowsMenuTextView.setText("Taco Nacho\nChicken Nuggets\nBosco Sticks\nCheeseburger\nGrilled Chicken Sandwich\n");
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        insertCurrentTime();
    }

    private void setTextViewsVisible(boolean visible) {
        if (visible) {
            todaysMenuTextView.setVisibility(View.VISIBLE);
            tomorrowsMenuTextView.setVisibility(View.VISIBLE);
            dontKnowTextView.setVisibility(View.VISIBLE);
            todaysMenuHeader.setVisibility(View.VISIBLE);
            tomorrowsMenuHeader.setVisibility(View.VISIBLE);

        } else {
            todaysMenuTextView.setVisibility(View.GONE);
            tomorrowsMenuTextView.setVisibility(View.GONE);
            dontKnowTextView.setVisibility(View.GONE);
            todaysMenuHeader.setVisibility(View.GONE);
            tomorrowsMenuTextView.setVisibility(View.GONE);
        }
    }

    /**
     * Check to see if a {@link TextView} with the tag "current_time_textView" already exists,
     * if it does remove it.
     * Insert a TextView with how much time is left in the certain block of time.
     */
    private void insertCurrentTime() {
        // Set current date
        Calendar cal = Calendar.getInstance();
        String today = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US);
        String month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        todaysDateTextView.setText(String.format(Locale.US, "%s, %s %d, %d", today, month, day, year));

        // Insert current time into correct place
    }

    private boolean isWeekend() {
        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        return day == Calendar.SATURDAY || day == Calendar.SUNDAY;
    }
    private boolean isWednesday() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY;
    }
    private boolean isTuesday() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY;
    }
    private boolean isThursday() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY;
    }
    private boolean isFriday() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY;
    }
    private boolean isMonday() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY;
    }
}
