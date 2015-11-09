package com.example.pablo.httpexercise;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private JobPostArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<JobPost> content = new ArrayList<>();

        ListView jobPostsListView = (ListView) findViewById(R.id.list_view);

        arrayAdapter = new JobPostArrayAdapter(this, content);
        jobPostsListView.setAdapter(arrayAdapter);
    }

    public void connectToServer(View view) {
        NetworkOperationAsyncTask asyncTask = new NetworkOperationAsyncTask();

        asyncTask.execute();

    }

    private class NetworkOperationAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            // This is my URL: http://dipandroid-ucb.herokuapp.com/work_posts.json
            HttpURLConnection urlConnection = null; // HttpURLConnection Object
            BufferedReader reader = null; // A BufferedReader in order to read the data as a file
            Uri buildUri = Uri.parse("http://dipandroid-ucb.herokuapp.com").buildUpon() // Build the URL using the Uri class
                    .appendPath("work_posts.json").build();
            try {
                URL url = new URL(buildUri.toString()); // Create a new URL

                urlConnection = (HttpURLConnection) url.openConnection(); // Get a HTTP connection
                urlConnection.setRequestMethod("GET"); // I'm using GET to query the server
                urlConnection.addRequestProperty("Content-Type", "application/json"); // The MIME type is JSON
                urlConnection.connect(); // Connect!! to the cloud!!!

                // Methods in order to read a text file (In this case the query from the server)
                InputStream inputStream = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer buffer = new StringBuffer();

                // Save the data in a String
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }

                return buffer.toString();
            } catch (IOException ex) {
                Log.e(LOG_TAG, ex.getMessage());
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }

                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                } catch (IOException e) {
                    Log.e(LOG_TAG, e.getMessage());
                }

            }
            return "";
        }

        @Override
        protected void onPostExecute(String json) {
            try {
                JSONArray array = new JSONArray(json);
                arrayAdapter.clear();

                for (int i = 0; i < array.length(); i++) {
                    JSONObject jobPostJSON = array.getJSONObject(i);
                    JobPost jobPost = new JobPost();

                    jobPost.setId(jobPostJSON.getInt("id"));
                    jobPost.setTitle(jobPostJSON.getString("title"));
                    jobPost.setPostDate(jobPostJSON.getString("posted_date"));
                    jobPost.setDescription(jobPostJSON.getString("description"));

                    arrayAdapter.add(jobPost);
                }

            } catch (JSONException ex) {
                Log.e(LOG_TAG, ex.getMessage());
            }
        }
    }
}
