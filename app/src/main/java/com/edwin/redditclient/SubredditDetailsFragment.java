package com.edwin.redditclient;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

public class SubredditDetailsFragment extends Fragment {

    private ImageView bannerImageView;
    private ImageView iconImageView;
    private TextView titleTextView;
    private TextView displayNameTextView;
    private TextView descriptionTextView;


    public SubredditDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_subreddit_details, container, false);

        bannerImageView = (ImageView) rootView.findViewById(R.id.details_banner_imageview);
        iconImageView = (ImageView) rootView.findViewById(R.id.details_icon_imageview);
        titleTextView = (TextView) rootView.findViewById(R.id.details_title_textview);
        displayNameTextView = (TextView) rootView.findViewById(R.id.details_display_name_textview);
        descriptionTextView = (TextView) rootView.findViewById(R.id.details_description_textview);

        return rootView;
    }

    void updateDetails(final Context contextParam, Subreddit subredditParam) {

        if (!subredditParam.getBannerUrl().isEmpty())
            Glide.with(contextParam).load(subredditParam.getBannerUrl())
                .dontAnimate().fitCenter().placeholder(R.drawable.reddit_logo).error(R.drawable.reddit_logo).into(bannerImageView);
        else
            Glide.with(contextParam).load(subredditParam.getHeaderImageUrl())
                    .dontAnimate().fitCenter().placeholder(R.drawable.reddit_logo).error(R.drawable.reddit_logo).into(bannerImageView);

        Glide.with(contextParam).load(subredditParam.getIconUrl()).asBitmap()
                .placeholder(R.drawable.reddit_logo).error(R.drawable.reddit_logo).into(new BitmapImageViewTarget(iconImageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(contextParam.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                iconImageView.setImageDrawable(circularBitmapDrawable);
            }
        });

        titleTextView.setText(subredditParam.getTitle());
        displayNameTextView.setText(subredditParam.getDisplayName());
        descriptionTextView.setText(subredditParam.getDescription());
    }
}