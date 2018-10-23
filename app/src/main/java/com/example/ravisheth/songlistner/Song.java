package com.example.ravisheth.songlistner;

public class Song {

    private String songName;

    public Song(String songName){
        this.songName = songName;
    }


    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongName() {
        return songName;
    }
}
