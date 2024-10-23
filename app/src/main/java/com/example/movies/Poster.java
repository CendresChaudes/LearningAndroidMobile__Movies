package com.example.movies;

import com.google.gson.annotations.SerializedName;

public class Poster {

    @SerializedName("url")
    private String url;

    public Poster(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    @Override
    public String toString() {
        return "Poster{" +
                "url='" + url + '\'' +
                '}';
    }
}
