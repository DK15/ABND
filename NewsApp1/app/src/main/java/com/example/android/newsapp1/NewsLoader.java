package com.example.android.newsapp1;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by lhu513 on 5/3/18.
 */

public class NewsLoader extends AsyncTaskLoader<ArrayList<News>> {

    private String mUrl;

    /**
     * New NewsLoader.
     *
     * @param context of the activity
     * @param url     url for
     */
    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        ArrayList<News> News = null;
        try {
            News = Utils.getNewsData(mUrl);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return News;
    }
}
