package com.edwin.redditclient;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edwin.redditclient.util.MyUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import hugo.weaving.DebugLog;

public class SubredditsListFragment extends Fragment {

    private RecyclerView subredditsRecyclerView;
    private View rootView;
    private List<Subreddit> subredditsList = new ArrayList<>();
    private ProgressDialog mProgressDialog;
    private boolean downloadInProgress;

    public SubredditsListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_subreddits_list, container, false);

        subredditsRecyclerView = (RecyclerView) rootView.findViewById(R.id.subreddits_recyclerview);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.update_subreddits_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchSubredditsFromServer();
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadProperSubredditsList();
    }

    @DebugLog
    private void loadProperSubredditsList() {
        subredditsList = querySubredditsInSugar();

        Log.d("EDWIN", "--->querySubredditsInSugar: " + subredditsList.size());

        if (subredditsList.isEmpty()) {
            fetchSubredditsFromServer();
        } else {
            Snackbar.make(rootView, R.string.using_offline_subreddits, Snackbar.LENGTH_SHORT).show();
            showSubredditsList();
        }
    }

    private List<Subreddit> querySubredditsInSugar() {
        return Subreddit.listAll(Subreddit.class);
    }

    private void fetchSubredditsFromServer() {
        if (!MyUtil.isConnectedToNetwork(getContext())) {
            Snackbar.make(rootView, R.string.no_network_available, Snackbar.LENGTH_SHORT).show();
            return;
        }

        AsyncHttpClient client = new AsyncHttpClient();
        client.setConnectTimeout(5000);

        client.get("https://www.reddit.com/reddits.json", new JsonHttpResponseHandler() {

            @DebugLog
            @Override
            public void onStart() {
                setDownloadInProgressUI(true);
            }

            @DebugLog
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                if (subredditsRecyclerView != null &&
                        response.optString("kind").equals("Listing") &&
                        response.optJSONObject("data") != null &&
                        response.optJSONObject("data").optJSONArray("children") != null) {

                    Log.d("EDWIN", "--->Populating");

                    List<Subreddit> subredditsListTemp = new ArrayList<>();

                    JSONArray postsJsonArray = response.optJSONObject("data").optJSONArray("children");

                    for (int i = 0; i < postsJsonArray.length(); i++) {

                        JSONObject currentPostJsonObject = postsJsonArray.optJSONObject(i);

                        if (currentPostJsonObject != null && currentPostJsonObject.optJSONObject("data") != null) {

                            Subreddit mSubreddit = new Subreddit();

                            mSubreddit.setRemoteId(currentPostJsonObject.optJSONObject("data").optString("id"));
                            mSubreddit.setDisplayName(currentPostJsonObject.optJSONObject("data").optString("display_name_prefixed"));
                            mSubreddit.setTitle(currentPostJsonObject.optJSONObject("data").optString("title"));
                            mSubreddit.setHeaderTitle(currentPostJsonObject.optJSONObject("data").optString("header_title"));
                            mSubreddit.setDescription(currentPostJsonObject.optJSONObject("data").optString("public_description"));
                            mSubreddit.setIconUrl(currentPostJsonObject.optJSONObject("data").optString("icon_img"));
                            mSubreddit.setHeaderImageUrl(currentPostJsonObject.optJSONObject("data").optString("header_img"));
                            mSubreddit.setBannerUrl(currentPostJsonObject.optJSONObject("data").optString("banner_img"));
                            subredditsListTemp.add(mSubreddit);
                        }
                    }

                    subredditsList = subredditsListTemp;
                    saveSubredditsToSugar();
                    showSubredditsList();
                    Snackbar.make(rootView, R.string.subreddits_updated, Snackbar.LENGTH_SHORT).show();

                } else
                    Log.w("EDWIN", "--->Not populating");

                setDownloadInProgressUI(false);
            }

            @DebugLog
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                setDownloadInProgressUI(false);
                Snackbar.make(rootView, R.string.error_updating, Snackbar.LENGTH_SHORT).show();
            }

            @DebugLog
            @Override
            public void onRetry(int retryNo) {
            }
        });
    }

    @DebugLog
    private void setDownloadInProgressUI(boolean downloadInProgressParam) {
        if ((downloadInProgress && downloadInProgressParam) || getActivity() == null || getActivity().isFinishing())
            return;

        downloadInProgress = downloadInProgressParam;

        if (downloadInProgress) {
            if (mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(getActivity());
                mProgressDialog.setMessage(getActivity().getString(R.string.please_wait));
                mProgressDialog.setTitle(getActivity().getString(R.string.updating_subreddits));
                mProgressDialog.setCancelable(false);
            }

            mProgressDialog.show();
        } else if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }

    }

    @DebugLog
    private void showSubredditsList() {
        SubredditsAdapter newAdapter = new SubredditsAdapter((MainActivity) getActivity(), subredditsList);
        subredditsRecyclerView.setAdapter(newAdapter);
        subredditsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @DebugLog
    private void saveSubredditsToSugar() {
        Subreddit.deleteAll(Subreddit.class);
        Subreddit.saveInTx(subredditsList);
    }
}