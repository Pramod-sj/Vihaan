package com.vesvihaan;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, OnLikeButtonClickListener, LikeHelper.OnLikeUnLikeListener, OnLikerClickListener {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    FeedAdapter feedAdapter;
    ArrayList<Feed> feeds = new ArrayList<>();
    TextView noFeed;
    LikeHelper likeHelper;

    DatabaseReference feedReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        feedReference=FirebaseDatabase.getInstance().getReference("Feeds");
        feedReference.keepSynced(true);
        noFeed = view.findViewById(R.id.noFeedTextView);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = view.findViewById(R.id.feedRecyclerView);
        feedAdapter = new FeedAdapter(getActivity(), feeds,this,this);
        recyclerView.setAdapter(feedAdapter);
        getFeedData();
        likeHelper=new LikeHelper(this);

        return view;
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        getFeedData();
    }


    public void getFeedData() {
        feedReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    feeds.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Feed feed = snapshot.getValue(Feed.class);
                        feed.setFeedId(snapshot.getKey());
                        if(FirebaseAuth.getInstance().getCurrentUser()!=null && feed.getFeedLikes()!=null && getUidFromLike(new ArrayList<User>(feed.getFeedLikes().values())).contains(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                            feed.setLiked(true);
                        }
                        else{
                            feed.setLiked(false);
                        }
                        feeds.add(feed);
                    }
                    noFeed.setVisibility(View.GONE);
                    feedAdapter.notifyDataSetChanged();
                } else {
                    noFeed.setVisibility(View.VISIBLE);
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onLikeButtonClick(String feedId,boolean isLiked) {
        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            MainActivity.showSnackBar("Please login first");

        }
        else {
            if (isLiked) {
                likeHelper.unlike(feedId);
            } else {
                likeHelper.like(feedId);
            }
        }
    }

    public ArrayList<String> getUidFromLike(ArrayList<User> likes){
        ArrayList<String> uids=new ArrayList<>();
        for(User user:likes){
            uids.add(user.getUid());
        }
        return uids;
    }

    @Override
    public void onLike() {
        getFeedData();
        //any other message
    }

    @Override
    public void onUnLike() {
        getFeedData();
        //any other message
    }

    @Override
    public void onClick(Feed feed) {
        LikerDialogFragment likerDialogFragment=new LikerDialogFragment();
        likerDialogFragment.setUsers(new ArrayList<User>(feed.getFeedLikes().values()));
        likerDialogFragment.show(getFragmentManager(),"LikerDialog");
    }
}
