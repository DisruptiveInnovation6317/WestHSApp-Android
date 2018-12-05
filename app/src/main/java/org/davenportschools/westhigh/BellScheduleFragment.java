package org.davenportschools.westhigh;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

public class BellScheduleFragment extends Fragment {
    private Block[] blocks = new Block[8];
    private TextView todayTextView;

    private boolean isWednesday;
    private boolean isWeekend;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bell_schedule, container, false);

        refresh(view);

        return view;
    }

    private void refresh(View suppliedView) {
        View view = suppliedView == null ? getView() : suppliedView;
        if (view == null) {
            throw new RuntimeException("THE VIEW IS NULL. WHAT?");
        }

        blocks = new Block[8];
        todayTextView = view.findViewById(R.id.todays_date_textView);
        TextView textViews[] = {
                view.findViewById(R.id.block_1_textView),
                view.findViewById(R.id.block_2_textView),
                view.findViewById(R.id.a_lunch_textView),
                view.findViewById(R.id.b_lunch_textView),
                view.findViewById(R.id.c_lunch_textView),
                view.findViewById(R.id.d_lunch_textView),
                view.findViewById(R.id.ff_textView),
                view.findViewById(R.id.block_4_textView)
        };

        isWeekend = isWeekend();
        isWednesday = isWednesday();

        String[] titles = {"Block 1", "Block 2", "A Lunch", "B Lunch", "C Lunch", "D Lunch", "Falcon Flex", "Block 4"};
        String[] times;

        if (isWednesday) {
            times = new String[]{"8:35 - 9:50", "9:55 - 11:10", "11:15 - 11:40",
                    "11:43 - 12:08", "12:11 - 12:36", "12:40 - 13:05", "NO FALCON FLEX",
                    "13:10 - 14:27"};
        } else { // This will create the array if it's a weekend, but we won't use the result if it is.
            times = new String[]{"8:35 - 9:55", "10:00 - 11:20", "11:25 - 11:50",
                    "11:55 - 12:20", "12:25 - 12:50", "12:55 - 13:20", "13:25 - 14:00",
                    "14:05 - 15:27"};
        }

        for (int i = 0; i < blocks.length; i++) {
            blocks[i] = new Block(textViews[i], titles[i], times[i]);
            blocks[i].updateTextView();
        }

        // Only show block text views if it is a weekday
        setTextViewsVisible(!isWeekend);

        insertCurrentTime();
    }

    /**
     * Change the visibility of all the text views showing block information.
     * This does not affect the TextView displaying the date, as that should always be visible.
     * @param visible Whether the block text views should be visible.
     */
    private void setTextViewsVisible(boolean visible) {
        for (Block block : blocks) {
            block.textView.setVisibility(visible ? View.VISIBLE : View.GONE);
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
        todayTextView.setText(String.format(Locale.US, "%s, %s %d, %d", today, month, day, year));

        if (isWeekend()) {
            return;
        }

        // TODO - Insert current time into correct place
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
    }

    private boolean isWeekend() {
        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        return day == Calendar.SATURDAY || day == Calendar.SUNDAY;
    }

    private boolean isWednesday() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY;
    }
}

class Block {
    int startHour, startMinute, endHour, endMinute;
    String title;
    TextView textView;

    Block(TextView textView, String title, String input) {
        this.textView = textView;
        this.title = title;
        String[] times = input.split(" - ");
        if (times.length != 2) {
            throw new IllegalArgumentException("Wrong input. Should be X:XX - X:XX");
        }

        String[] time1 = times[0].split(":");
        String[] time2 = times[1].split(":");

        int startHour = Integer.parseInt(time1[0]);
        int startMinute = Integer.parseInt(time1[1]);
        int endHour = Integer.parseInt(time2[0]);
        int endMinute = Integer.parseInt(time2[1]);

        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
    }

    void updateTextView() {
        textView.setText(toString());
    }

    @NonNull
    @Override
    public String toString() {
        return title + ": " + getFormatted();
    }

    private String getFormatted() {
        int startH = startHour;
        if (startH > 12) startH -= 12;

        String startM = String.valueOf(startMinute);
        if (startM.length() == 1) startM = "0" + startM;

        int endH = endHour;
        if (endH > 12) endH -= 12;

        String endM = String.valueOf(endMinute);
        if (endM.length() == 1) endM = "0" + endM;

        return String.format(Locale.US, "%d:%s - %d:%s", startH, startM, endH, endM);
    }
}