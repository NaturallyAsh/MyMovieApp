package com.ashleigh.mymovieapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ashleigh.mymovieapp.R;
import com.ashleigh.mymovieapp.models.Movie;
import com.ashleigh.mymovieapp.models.Trailer;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {

    private static final String TAG = TrailerAdapter.class.getSimpleName();

    private Context mContext;
    private ArrayList<Trailer> mTrailers;
    private OnTrailerClickedListener mListener;


    public interface OnTrailerClickedListener {
        void TrailerClicked(Trailer trailer, int position);
    }

    public TrailerAdapter(Context context, ArrayList<Trailer> trailerArrayList, OnTrailerClickedListener listener) {
        mContext = context;
        mTrailers = trailerArrayList;
        mListener = listener;
    }

    @NonNull
    @Override
    public TrailerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.movie_trailer_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final TrailerAdapter.ViewHolder holder, int position) {
        final Trailer currentTrailer = mTrailers.get(position);

        String thumbnail = "http://img.youtube.com/vi/" + currentTrailer.getmKey() + "/0.jpg";
        if (currentTrailer.getTrailerUrl() != null) {
            Glide.with(mContext)
                    .load(thumbnail)
                    .into(holder.trailerImage);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.TrailerClicked(currentTrailer, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTrailers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.trailer_IV)
        ImageView trailerImage;
        View mView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mView = itemView;
        }
    }

    public void addTrailer(List<Trailer> trailers) {
        mTrailers.clear();
        mTrailers.addAll(trailers);
        notifyDataSetChanged();
    }
}
