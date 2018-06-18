package com.android.gw.movietrailers.Activities;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.gw.movietrailers.Models.Movie;
import com.android.gw.movietrailers.Models.Trailer;
import com.android.gw.movietrailers.R;
import com.android.gw.movietrailers.UIAdapters.RecyclerAdapter;
import com.android.gw.movietrailers.UIAdapters.TrailerRecyclerAdapter;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieDetails extends AppCompatActivity {

    RequestQueue requestQueue;
    List<Trailer> trailerList;
    RecyclerView recyclerView;
    TrailerRecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();

        Movie movie = i.getParcelableExtra("movie");

        setContentView(R.layout.activity_movie_details);
        Toolbar toolbar = findViewById(R.id.trailer_toobar);
        setSupportActionBar(toolbar);

        trailerList = new ArrayList<>();

        initCollapsingToolbar(movie);

        recyclerView = findViewById(R.id.trailers_recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        String requestUrl = "http://image.tmdb.org/t/p/original/" + movie.getBackdropUrl();

        requestQueue = Volley.newRequestQueue(this);

        String url = "https://api.themoviedb.org/3/movie/" + movie.getId() + "/videos?api_key=58b68d6af6a67e1fadcc2384cb198c11&language=en-US";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        String name = results.getJSONObject(i).getString("name");
                        String key = results.getJSONObject(i).getString("key");
                        String type = results.getJSONObject(i).getString("type");
                        String site = results.getJSONObject(i).getString("site");
                        if (site.equals("YouTube")) {
                            Trailer trailer = new Trailer(name, key, type, site);
                            trailer.setUrl();
                            trailerList.add(trailer);
                            // Log.d("name", trailer.getName());
                        }
                    }
                    recyclerAdapter = new TrailerRecyclerAdapter(getApplicationContext(), trailerList);
                    recyclerView.setAdapter(recyclerAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Volley Error:", error.getMessage());
            }
        });

        requestQueue.add(jsonObjectRequest);

//        for (Trailer t : trailerList) {
//            System.out.println("Name:" +  t.getName());
//        }
//        recyclerAdapter = new TrailerRecyclerAdapter(getApplicationContext(), trailerList);
//        recyclerView.setAdapter(recyclerAdapter);

        Picasso.get().load(requestUrl).into((ImageView) findViewById(R.id.backdrop));
    }

    private void initCollapsingToolbar(final Movie m) {
        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(" ");
        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(m.getName());
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }
}
