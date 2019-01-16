package org.davenportschools.westhigh;

import android.util.Log;

public class ArticleModel {
    public String title;
    public String body;
    public String url;

    public ArticleModel(String title, String body, String url) {
        this.title = title;
        this.body = body;
        this.url = url;
        Log.d("ARTICLE", title);
        Log.d("ARTICLE", body);
        Log.d("ARTICLE", "----");
    }
}
