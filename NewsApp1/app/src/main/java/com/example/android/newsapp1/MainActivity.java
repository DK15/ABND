package com.example.android.newsapp1;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<News>> {

    // Guardian API
    private static final String URL =
        "http://content.guardianapis.com/search?";

    // Constant value for the news loader ID.
    private static final int NEWS_LOADER_ID = 10;

    private NewsAdapter newsAdapter;

    private TextView emptyTextView;

    private String messageForUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_list);

        ListView newsListView = (ListView) findViewById(R.id.newsList);

        emptyTextView = (TextView) findViewById(R.id.defaultView);
        newsListView.setEmptyView(emptyTextView);

        newsAdapter = new NewsAdapter(this, new ArrayList<News>());

        newsListView.setAdapter(newsAdapter);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                News openNews = newsAdapter.getItem(position);

                assert openNews != null;
                Uri newsURI = Uri.parse(openNews.getNewsLink());

                Intent openNewsIntent = new Intent(Intent.ACTION_VIEW, newsURI);
                startActivity(openNewsIntent);
            }
        });

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connectivityMgr = (ConnectivityManager)
            getSystemService(Context.CONNECTIVITY_SERVICE);

        assert connectivityMgr != null;
        NetworkInfo networkInfo = connectivityMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader.
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        }
        View loadingIndicator = findViewById(R.id.loading_info);
        loadingIndicator.setVisibility(View.GONE);
    }

    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, Bundle args) {
        // Create a new loader for the given URL

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String searchNewsCategory = sharedPreferences.getString(
            getString(R.string.news_category_title),
            getString(R.string.news_all_title));

        // get news category from list

        String[] newsCategory = searchNewsCategory.split(" ");
        String newsCategoryCap = newsCategory[0];
        String news_category = newsCategoryCap.toLowerCase();

        // create Uri and parse guardian url

        Uri baseUri = Uri.parse(URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        if (!news_category.equals("all")) {
            uriBuilder.appendQueryParameter("section", news_category);
        }

        uriBuilder.appendQueryParameter("order-by", "newest");
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("api-key", "a97469a9-5f11-42c6-a29f-8d37cbbaf820");

        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<News>> loader, ArrayList<News> news) {

        View loadingIndicator = findViewById(R.id.loading_info);
        loadingIndicator.setVisibility(View.GONE);

        newsAdapter.clear();
        if (news != null && !news.isEmpty()) {
            newsAdapter.addAll(news);
        }
        if (news.isEmpty()) {
            messageForUser = (String) getText(R.string.no_internet_msg);
            warnMessage(messageForUser);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<News>> loader) {
        newsAdapter.clear();
    }

    private void warnMessage(String messageForUser) {
        View loadingIndicator = findViewById(R.id.loading_info);
        loadingIndicator.setVisibility(View.GONE);
        emptyTextView.setVisibility(View.VISIBLE);
        emptyTextView.setText(messageForUser);
    }

    @Override
    // This method initialize the contents of the Activity's options menu.
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the Options Menu we specified in XML
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




}
