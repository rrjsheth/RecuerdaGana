package com.example.ravisheth.songlistner;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplaySongsAdapter extends RecyclerView.Adapter<DisplaySongsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Song> songs;

    public DisplaySongsAdapter(Context context, ArrayList<Song> songs){
        this.context = context;
        this.songs = songs;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView songName;
        public ViewHolder(View viewItem){
            super(viewItem);
            songName = itemView.findViewById(R.id.songName);

        }

        public void setSongName(Song song){
            songName.setText(song.getSongName());
        }



    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.songs_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position){
        Song song = songs.get(position);
        viewHolder.setSongName(song);
    }

    @Override
    public int getItemCount(){
        return songs.size();
    }
}
