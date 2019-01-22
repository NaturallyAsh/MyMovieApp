package com.ashleigh.mymovieapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashleigh.mymovieapp.R;
import com.ashleigh.mymovieapp.models.Cast;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {

    private static final String TAG = CastAdapter.class.getSimpleName();

    private ArrayList<Cast> castList;
    Context mContext;
    private OnCastClickedListener listener;

    public CastAdapter(Context context, ArrayList<Cast> castArrayList, OnCastClickedListener listener) {
        this.mContext = context;
        this.castList = castArrayList;
        this.listener = listener;
    }


    public interface OnCastClickedListener {
        void OnCastClicked(int position);
    }

    @NonNull
    @Override
    public CastAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.cast_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CastAdapter.ViewHolder holder, int position) {
        final Cast currentCast = castList.get(position);

        if (currentCast.getmProfile() != null) {
            Glide.with(mContext)
                    .load(currentCast.getmProfile())
                    .into(holder.mCastImage);
        }
        holder.mCharacter.setText(currentCast.getmCharacter());
        holder.mName.setText(currentCast.getmName());
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        View mView;
        @BindView(R.id.cast_character_tv)
        TextView mCharacter;
        @BindView(R.id.cast_image)
        ImageView mCastImage;
        @BindView(R.id.cast_name_tv)
        TextView mName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mView = itemView;
        }
    }

    public void add(List<Cast> casts) {
        castList.clear();
        castList.addAll(casts);
        notifyDataSetChanged();
    }

    public ArrayList<Cast> getCastList() {
        return castList;
    }
}
