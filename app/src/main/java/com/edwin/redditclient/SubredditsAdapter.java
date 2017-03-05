package com.edwin.redditclient;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.List;

public class SubredditsAdapter extends
        RecyclerView.Adapter<SubredditsAdapter.ViewHolder> {

    private List<Subreddit> subredditsList;
    private MainActivity parentActivity;

    public SubredditsAdapter(MainActivity parentActivityParam, List<Subreddit> subredditsListParam) {
        subredditsList = subredditsListParam;
        parentActivity = parentActivityParam;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View subredditView = inflater.inflate(R.layout.item_subreddit, parent, false);

        return new ViewHolder(subredditView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Subreddit mSubreddit = subredditsList.get(position);

        holder.displayNameTextView.setText(mSubreddit.getDisplayName());
        holder.headerTitleTextView.setText(mSubreddit.getHeaderTitle());

        Glide.with(parentActivity).load(mSubreddit.getIconUrl()).asBitmap()
                .placeholder(R.drawable.reddit_logo).error(R.drawable.reddit_logo).into(new BitmapImageViewTarget(holder.iconImageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(parentActivity.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                holder.iconImageView.setImageDrawable(circularBitmapDrawable);
            }
        });

        holder.wholeItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.moveToDetailsFragment(mSubreddit);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subredditsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView displayNameTextView;
        private TextView headerTitleTextView;
        private ImageView iconImageView;
        private RelativeLayout wholeItemLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            displayNameTextView = (TextView) itemView.findViewById(R.id.item_subreddit_display_name_textview);
            headerTitleTextView = (TextView) itemView.findViewById(R.id.item_subreddit_header_title_textview);
            iconImageView = (ImageView) itemView.findViewById(R.id.item_subreddit_icon_imageview);
            wholeItemLayout = (RelativeLayout) itemView.findViewById(R.id.item_subreddit_whole_layout);
        }


    }
}