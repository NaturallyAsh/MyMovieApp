package com.ashleigh.mymovieapp.models;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Discover {

    private static final String TAG = Discover.class.getSimpleName();
    public static final float POSTER_ASPECT_RATIO = 1.5f;

    @SerializedName("id")
    private long mId;
    @SerializedName("original_title")
    private String mTitle;
    @SerializedName("overview")
    private String mOverview;
    @SerializedName("poster_path")
    private String mPoster;
    @SerializedName("backdrop_path")
    private String mBackdrop;
    @SerializedName("release_date")
    private String mReleaseDate;

    public String getmTitle() {
        return mTitle;
    }

    public long getmId() {
        return mId;
    }

    public String getmReleaseDate() {
        String inputPattern = "yyyy-MM-dd";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.US);
        if (mReleaseDate != null && !mReleaseDate.isEmpty()) {
            try {
                Date date = inputFormat.parse(mReleaseDate);
                return DateFormat.getDateInstance().format(date);
            } catch (ParseException e) {
                Log.i(TAG, "The Release data was not parsed successfully: " + mReleaseDate);
                // Return not formatted date
            }
        } else {
            mReleaseDate = "Release date missing";
        }

        return mReleaseDate;
    }

    public String getmBackdrop() {
        if (mBackdrop != null && !mBackdrop.isEmpty()) {
            return "http://image.tmdb.org/t/p/w300" + mBackdrop;
        }
        return null;
    }

    public String getmOverview() {
        return mOverview;
    }

    public String getmPoster() {
        if (mPoster != null && !mPoster.isEmpty()) {
            return "http://image.tmdb.org/t/p/w342" + mPoster;
        }
        return null;
    }
}
