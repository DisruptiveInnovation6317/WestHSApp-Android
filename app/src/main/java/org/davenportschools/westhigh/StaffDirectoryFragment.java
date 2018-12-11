package org.davenportschools.westhigh;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class StaffDirectoryFragment extends Fragment {
    public MyAdapter adapter;
    private String[] staff = new String[0];

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff_directory, container, false);

        adapter = new StaffDirectoryFragment.MyAdapter();
        ListView listView = view.findViewById(R.id.staff_directory_listView);
        listView.setAdapter(adapter);

        return view;
    }


    public class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return staff.length;
        }

        @Override
        public Object getItem(int position) {
            return staff[position];
    }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).
                        inflate(android.R.layout.simple_list_item_1, parent, false);
            }

            TextView textView = convertView.findViewById(android.R.id.text1);
            textView.setText((String)getItem(position));

            return convertView;
        }
    }
}