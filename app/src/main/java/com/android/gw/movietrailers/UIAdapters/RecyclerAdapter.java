package com.android.gw.movietrailers.UIAdapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.gw.movietrailers.Activities.MovieDetails;
import com.android.gw.movietrailers.Models.Movie;
import com.android.gw.movietrailers.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
    private List<Movie> movieList;
    private Context context;

    public RecyclerAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        public TextView title;
        public ImageView thumbnail;
        public RelativeLayout playTrailerLayout;

        public ViewHolder(final View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.movie_title);
            thumbnail = itemView.findViewById(R.id.movie_thumbnail);
            playTrailerLayout = itemView.findViewById(R.id.play_trailer);
            playTrailerLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final int position = getAdapterPosition();
            Intent i = new Intent(context, MovieDetails.class);
            i.putExtra("movie", movieList.get(position));
            context.startActivity(i);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card, parent, false);

        final ViewHolder holder = new ViewHolder(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.title.setText(movieList.get(position).getName());
        Picasso.get().load(movieList.get(position).getThumbnailUrl()).into(holder.thumbnail);
//        holder.playTrailerLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Toast.makeText(context, "Clicked Movie: " + movieList.get(position).getId(), Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(context, MovieDetails.class);
//                i.putExtra("movie", movieList.get(position));
//                context.startActivity(i);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}
