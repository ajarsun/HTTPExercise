package com.example.pablo.httpexercise;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class JobPostArrayAdapter extends ArrayAdapter<JobPost> {
    public JobPostArrayAdapter(Context context, List<JobPost> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View currentView;
        TextView titleTextView;
        TextView dateTextView;
        JobPost post = getItem(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            currentView = inflater.inflate(R.layout.post_text_view, parent, false);
        } else {
            currentView = convertView;
        }

        titleTextView = (TextView)currentView.findViewById(R.id.title_text_view);
        dateTextView = (TextView)currentView.findViewById(R.id.date_text_view);

        titleTextView.setText(post.getTitle());
        dateTextView.setText(post.getPostDate());

        return currentView;
    }
}
