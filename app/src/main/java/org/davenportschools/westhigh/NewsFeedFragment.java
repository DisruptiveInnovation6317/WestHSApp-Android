package org.davenportschools.westhigh;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class NewsFeedFragment extends Fragment {
    public MyAdapter adapter;
    public List<ArticleModel> articles = new LinkedList<>();
    public boolean shouldFetchNews = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_feed, container, false);

        adapter = new MyAdapter();
        ListView listView = view.findViewById(R.id.news_feed_listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = articles.get(position).url;
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });

        if (shouldFetchNews) {
            new NewsFeedAsyncFetch().execute();
            Toast.makeText(getContext(), "Loading News...", Toast.LENGTH_LONG).show();
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

            ArticleModel entry = (ArticleModel)getItem(position);

            TextView titleTextView = convertView.findViewById(android.R.id.text1);
            titleTextView.setText(entry.title);

            TextView subtitleTextView = convertView.findViewById(android.R.id.text2);
            subtitleTextView.setLines(5);
            subtitleTextView.setText(entry.body);

            return convertView;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
            if (articles == null || articles.size() == 0) {
                Toast.makeText(getContext(), "Couldn't load news, sorry. :(", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class NewsFeedAsyncFetch extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document doc = Jsoup
                        .connect("https://www.davenportschools.org/west/")
                        .userAgent(System.getProperty("http.agent"))
                        .get();
                Elements posts = doc.getElementsByClass("post");
                for (int i = 0; i < posts.size(); i++) {
                    Element post = posts.get(i);
                    Element entry = post.getElementsByClass("entry").get(0);
                    System.out.println("ENTRY " + i);
                    System.out.println(entry.html());

                    StringBuilder sb = new StringBuilder();
                    String title = entry.select("h2").select("a").text();
                    String link = entry.select("h2").select("a").attr("href");
                    String body;
                    for (Element paragraph : entry.select("p")) {
                        sb.append(paragraph.text()).append("\n");
                    }
                    body = sb.toString();
                    articles.add(new ArticleModel(title, body, link));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void s) {
            adapter.notifyDataSetChanged();
            if (articles == null || articles.size() == 0) {
                shouldFetchNews = false;
            }
        }
    }
}