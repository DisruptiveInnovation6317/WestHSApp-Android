package org.davenportschools.westhigh;

import android.os.AsyncTask;
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
import android.widget.Toast;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class NewsFeedFragment extends Fragment {
    public MyAdapter adapter;
    public List<SyndEntry> articles = new LinkedList<>();
    public boolean shouldFetchNews = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_feed, container, false);

        adapter = new MyAdapter();
        ListView listView = view.findViewById(R.id.news_feed_listview);
        listView.setAdapter(adapter);

        if (shouldFetchNews) {
            new NewsFeedAsyncFetch().execute();
        }

        return view;
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return articles.size();
        }

        @Override
        public Object getItem(int position) {
            return articles.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).
                        inflate(android.R.layout.simple_list_item_2, parent, false);
            }

            SyndEntry entry = (SyndEntry)getItem(position);

            TextView titleTextView = convertView.findViewById(android.R.id.text1);
            titleTextView.setText(entry.getTitle());

            TextView subtitleTextView = convertView.findViewById(android.R.id.text2);
            subtitleTextView.setText(entry.getDescription().getValue());

            return convertView;
        }
    }

    private class NewsFeedAsyncFetch extends AsyncTask<Void, Void, SyndFeed> {
        @Override
        protected SyndFeed doInBackground(Void... voids) {
            try {
                String link = "https://davenportschools.org/west/feed";
                URL url = new URL(link);
                XmlReader reader = new XmlReader(url);
                SyndFeedInput input = new SyndFeedInput();
                SyndFeed feed = input.build(reader);

                return feed;
            } catch (FeedException | IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(SyndFeed s) {
            if (s == null) {
                Toast.makeText(getContext(), "Couldn't parse news, sorry. :(", Toast.LENGTH_LONG).show();;
            } else {
                articles = s.getEntries();
                adapter.notifyDataSetChanged();
                shouldFetchNews = false;
            }
        }
    }
}
