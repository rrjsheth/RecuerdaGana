package com.example.ravisheth.songlistner;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DisplaySongs extends Fragment {

    private RecyclerView recyclerView;
    private DisplaySongsAdapter adapter;
    private ArrayList<Song> songsArrayList;
    private DatabaseReference ref;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.display_songs, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        songsArrayList = new ArrayList<>();

        ref = FirebaseDatabase.getInstance().getReference().child("song");
        ValueEventListener songUpdateListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                refreshList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        ref.addValueEventListener(songUpdateListener);
        adapter = new DisplaySongsAdapter(this.getContext(), songsArrayList);
        recyclerView.setAdapter(adapter);

        printSongList();
        return view;
    }

    public void populateSongsList(Map<String,Object> songs){
        ArrayList<String> songsList = new ArrayList<>();
        if(songs == null) return;
        HashMap<String, Object> mp;
        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : songs.entrySet()){

            //Get user map
            Map singlesong = (Map) entry.getValue();
            /*
            //Get phone field and append to list
            mp = (HashMap<String, Object>) singlesong.get("songName");
            for( Map.Entry<String,Object> elem : mp.entrySet()){
                Log.d("notification123123","Key = " + elem.getKey() +
                        ", Value = " + elem.getValue());
            }
            */
            songsList.add((String) singlesong.get("songName"));
        }

        this.songsArrayList.clear();
        for( String elem : songsList) {
            this.songsArrayList.add(new Song(elem));
        }

        adapter.notifyDataSetChanged();

    }

    public void refreshList(){
        DatabaseReference songList = ref;
        songList.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                populateSongsList((Map<String,Object>) dataSnapshot.getValue());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void printSongList(){
        for( Song song: songsArrayList){
            Log.d("123321",song.getSongName());
        }
    }

}
