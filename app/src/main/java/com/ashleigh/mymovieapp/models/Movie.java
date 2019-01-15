package com.ashleigh.mymovieapp.models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.ashleigh.mymovieapp.R;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Movie implements Parcelable {

    private static final String TAG = Movie.class.getSimpleName();
    public static final float POSTER_ASPECT_RATIO = 1.5f;

    @SerializedName("id")
    private String mId;
    @SerializedName("original_title")
    private String mTitle;
    @SerializedName("poster_path")
    private String mPoster;
    @SerializedName("overview")
    private String mOverview;
    @SerializedName("vote_average")
    private String mPopularity;
    @SerializedName("release_date")
    private String mReleaseDate;
    @SerializedName("backdrop_path")
    private String mBackdrop;

    public String getmId() {
        return mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmPoster() {
        return mPoster;
    }

    public String getmOverview() {
        return mOverview;
    }

    public String getmBackdrop() {
        return mBackdrop;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public String getmPopularity() {
        return mPopularity;
    }
    public String getPosterUrl() {
        if (mPoster != null && !mPoster.isEmpty()) {
            return "http://image.tmdb.org/t/p/w342" + mPoster;
        }
        return null;
    }
    public String getBackdropUrl() {
        if (mBackdrop != null && !mBackdrop.isEmpty()) {
            return "http://image.tmdb.org/t/p/w500" + mBackdrop;
        }
        return null;
    }
    public String getReleaseDate(Context context) {
        String inputPattern = "yyyy-MM-dd";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.US);
        if (mReleaseDate != null && !mReleaseDate.isEmpty()) {
            try {
                Date date = inputFormat.parse(mReleaseDate);
                return DateFormat.getDateInstance().format(date);
            } catch (ParseException e) {
                Log.e(TAG, "The Release data was not parsed successfully: " + mReleaseDate);
                // Return not formatted date
            }
        } else {
            mReleaseDate = "Release date missing";
        }

        return mReleaseDate;
    }

    protected Movie(Parcel in) {
        mId = in.readString();
        mTitle = in.readString();
        mPoster = in.readString();
        mOverview = in.readString();
        mPopularity = in.readString();
        mReleaseDate = in.readString();
        mBackdrop = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mTitle);
        dest.writeString(mPoster);
        dest.writeString(mOverview);
        dest.writeString(mPopularity);
        dest.writeString(mReleaseDate);
        dest.writeString(mBackdrop);
    }
}
