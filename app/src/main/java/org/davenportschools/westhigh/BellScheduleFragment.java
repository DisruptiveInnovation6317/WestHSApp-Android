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
import java.util.Date;
import java.util.Locale;

public class BellScheduleFragment extends Fragment {
    private TextView todaysDateTextView, block1TextView, block2TextView,
            aLunchTextView, bLunchTextView, cLunchTextView, dLunchTextView,
            falconFlexTextView, block4TextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bell_schedule, container, false);

        todaysDateTextView = view.findViewById(R.id.todays_date_textView);
        block1TextView = view.findViewById(R.id.block_1_textView);
        block2TextView = view.findViewById(R.id.block_2_textView);
        aLunchTextView = view.findViewById(R.id.a_lunch_textView);
        bLunchTextView = view.findViewById(R.id.b_lunch_textView);
        cLunchTextView = view.findViewById(R.id.c_lunch_textView);
        dLunchTextView = view.findViewById(R.id.d_lunch_textView);
        falconFlexTextView = view.findViewById(R.id.ff_textView);
        block4TextView = view.findViewById(R.id.block_4_textView);

        if (isWeekend()) {
            setTextViewsVisible(false);
        } else if (isWednesday()) {
            setTextViewsVisible(true);
            block1TextView.setText("Block 1: 8:35 - 9:50");
            block2TextView.setText("Block 2: 9:55 - 11:10");
            aLunchTextView.setText("A Lunch: 11:15 - 11:40");
            bLunchTextView.setText("B Lunch: 11:43 - 12:08");
            cLunchTextView.setText("C Lunch: 12:11 - 12:36");
            dLunchTextView.setText("D Lunch: 12:40 - 1:05");
            falconFlexTextView.setVisibility(View.GONE);
            block4TextView.setText("Block 4: 1:10 - 2:27");
        } else {
            setTextViewsVisible(true);
            block1TextView.setText("Block 1: 8:35 - 9:55");
            block2TextView.setText("Block 2: 10:00 - 11:20");
            aLunchTextView.setText("A Lunch: 11:25 - 11:50");
            bLunchTextView.setText("B Lunch: 11:55 - 12:20");
            cLunchTextView.setText("C Lunch: 12:25 - 12:50");
            dLunchTextView.setText("D Lunch: 12:55 - 1:20");
            falconFlexTextView.setText("Falcon Flex: 1:25 - 2:00");
            block4TextView.setText("Block 4: 2:05 - 3:27");
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
            block1TextView.setVisibility(View.VISIBLE);
            block2TextView.setVisibility(View.VISIBLE);
            aLunchTextView.setVisibility(View.VISIBLE);
            bLunchTextView.setVisibility(View.VISIBLE);
            cLunchTextView.setVisibility(View.VISIBLE);
            dLunchTextView.setVisibility(View.VISIBLE);
            falconFlexTextView.setVisibility(View.VISIBLE);
            block4TextView.setVisibility(View.VISIBLE);
        } else {
            block1TextView.setVisibility(View.GONE);
            block2TextView.setVisibility(View.GONE);
            aLunchTextView.setVisibility(View.GONE);
            bLunchTextView.setVisibility(View.GONE);
            cLunchTextView.setVisibility(View.GONE);
            dLunchTextView.setVisibility(View.GONE);
            falconFlexTextView.setVisibility(View.GONE);
            block4TextView.setVisibility(View.GONE);
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
}
