package com.example.android.newsapp1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lhu513 on 5/2/18.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Activity context, ArrayList<News> newsActivities) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.

        super(context, 0, newsActivities);
    }

    /**
     * Provides a view for an ListView
     *
     * @param position    The position in the list of data that should be displayed in the
     *                    list item view.
     * @param convertView The recycled view to populate.
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                R.layout.list_item, parent, false);
        }

        News news = getItem(position);

        TextView titleView = listItemView.findViewById(R.id.newsTitle);

        titleView.setText(news.getNewsTitle());

        TextView newsSectionView = listItemView.findViewById(R.id.newsSection);
        newsSectionView.setText(news.getNewsSection());

        TextView newsUrlView = listItemView.findViewById(R.id.newsUrl);
        newsUrlView.setText(news.getNewsLink());

        TextView authorView = listItemView.findViewById(R.id.newsAuthor);
        authorView.setText(news.getWebTitle());

        TextView dateView = listItemView.findViewById(R.id.newsDate);
        SimpleDateFormat dateFormatJSON = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);

        try {
            Date dateNews = dateFormatJSON.parse(news.getWebPublicationDate());

            String date = dateFormat2.format(dateNews);
            dateView.setText(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        dateView.setText(news.getWebPublicationDate());

        return listItemView;
    }

}
