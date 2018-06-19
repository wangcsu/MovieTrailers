package com.android.gw.movietrailers.Activities;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.android.gw.movietrailers.BuildConfig;
import com.android.gw.movietrailers.Models.Movie;
import com.android.gw.movietrailers.R;
import com.android.gw.movietrailers.UIAdapters.RecyclerAdapter;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    private final static String API_KEY = BuildConfig.ApiKey;
    private List<Movie> movies;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final JSONObject[] jsonObject = {new JSONObject()};
        movies = new ArrayList<>();

        progressBar = findViewById(R.id.waiting);
        recyclerView = findViewById(R.id.movie_list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(dpToPx(7)));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.HORIZONTAL));

        mRequestQueue = Volley.newRequestQueue(this);
        String url = "https://api.themoviedb.org/3/movie/upcoming?api_key=" + API_KEY + "&language=en-US&page=1";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, jsonObject[0], new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("results");
                    // Log.d("results", results.toString());
                    for (int i = 0; i < results.length(); i++) {
                        String id = results.getJSONObject(i).getString("id");
                        String name = results.getJSONObject(i).getString("title");
                        String thumbnailUrl = "http://image.tmdb.org/t/p/w342" + results.getJSONObject(i).getString("poster_path");
                        String backdropUrl = results.getJSONObject(i).getString("backdrop_path");
                        Movie movie = new Movie(id, name, thumbnailUrl, backdropUrl);
                        movies.add(movie);
                    }
                    recyclerAdapter = new RecyclerAdapter(getApplicationContext(), movies);
                    recyclerView.setAdapter(recyclerAdapter);
                    recyclerAdapter.notifyDataSetChanged();
                    recyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Volley Error", error.getMessage());
            }
        });

        mRequestQueue.add(jsonObjectRequest);
    }

    public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {
        private final int verticalSpaceHeight;
        public VerticalSpaceItemDecoration(int verticalSpaceHeight) {
            this.verticalSpaceHeight = verticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.top = verticalSpaceHeight;
//            if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
//                outRect.bottom = verticalSpaceHeight;
//            }
            outRect.bottom = verticalSpaceHeight;
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
