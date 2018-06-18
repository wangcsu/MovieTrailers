package com.android.gw.movietrailers.UIAdapters;

import android.app.Activity;
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

import com.android.gw.movietrailers.Models.Trailer;
import com.android.gw.movietrailers.*;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.List;

public class TrailerRecyclerAdapter extends RecyclerView.Adapter<TrailerRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Trailer> trailers;

    public TrailerRecyclerAdapter(Context c, List<Trailer> t) {
        this.context = c;
        this.trailers = t;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // public VideoView videoView;
        public TextView textView;
        public YouTubeThumbnailView youTubeThumbnailView;
        public ImageView playButton;
        public RelativeLayout relativeLayoutOverYouTubeThumbnailView;

        public ViewHolder(final View itemView) {
            super(itemView);
            youTubeThumbnailView = itemView.findViewById(R.id.trailer_thumbnail);
            // videoView  = itemView.findViewById(R.id.trailer_player);
            textView = itemView.findViewById(R.id.trailer_name);
            playButton = itemView.findViewById(R.id.btnYoutube_player);
            relativeLayoutOverYouTubeThumbnailView = itemView.findViewById(R.id.relativeLayout_over_youtube_thumbnail);
            playButton.setOnClickListener(this);

            youTubeThumbnailView.initialize("AIzaSyDR7yPFBdKkIbJlMkQNNjKXDRfQpY_vpg8", new YouTubeThumbnailView.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
                    youTubeThumbnailLoader.setVideo(trailers.get(getAdapterPosition()).getKey());
                    youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                        @Override
                        public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                            // youTubeThumbnailView.setVisibility(View.VISIBLE);
                            // relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
                            youTubeThumbnailLoader.release();
                        }

                        @Override
                        public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

                        }
                    });
                }

                @Override
                public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

                }
            });
        }

        @Override
        public void onClick(View v) {
            Intent i = YouTubeStandalonePlayer.createVideoIntent((Activity) v.getContext(), "AIzaSyDR7yPFBdKkIbJlMkQNNjKXDRfQpY_vpg8",
                    trailers.get(getAdapterPosition()).getKey(), 100, false, true);
            context.startActivity(i);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_card, parent, false);

        final ViewHolder holder = new ViewHolder(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Trailer trailer = trailers.get(position);
        // holder.videoView.setVideoPath(trailer.getUrl());
//        final YouTubeThumbnailLoader.OnThumbnailLoadedListener onThumbnailLoadedListener =  new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
//
//            @Override
//            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
//                youTubeThumbnailView.setVisibility(View.VISIBLE);
//                holder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
//
//            }
//        };

//        holder.youTubeThumbnailView.initialize("AIzaSyDR7yPFBdKkIbJlMkQNNjKXDRfQpY_vpg8", new YouTubeThumbnailView.OnInitializedListener() {
//            @Override
//            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
//                youTubeThumbnailLoader.setVideo(trailer.getKey());
//                youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
//                    @Override
//                    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
//                        youTubeThumbnailView.setVisibility(View.VISIBLE);
//                        holder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
//                        youTubeThumbnailLoader.release();
//                    }
//
//                    @Override
//                    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
//
//                    }
//                });
//            }
//
//            @Override
//            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
//
//            }
//        });

        holder.textView.setText(trailer.getName());
        // holder.videoView.start();
//        holder.youTubePlayerView.initialize("AIzaSyDR7yPFBdKkIbJlMkQNNjKXDRfQpY_vpg8", new YouTubePlayer.OnInitializedListener() {
//            @Override
//            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
//                youTubePlayer.cueVideo(trailer.getKey());
//            }
//
//            @Override
//            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//                Log.e("Youtube error", "Something went wrong");
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }
}
