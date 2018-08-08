package com.udacity.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Step implements Parcelable {

    @JsonProperty("id")
    private int id;
    @JsonProperty("description")
    private String description;
    @JsonProperty("shortDescription")
    private String shortDescription;
    @JsonProperty("thumbnailURL")
    private String thumbnailUrl;
    @JsonProperty("videoURL")
    private String videoUrl;

    public Step() {
        this.id = 0;
        this.description = "";
        this.shortDescription = "";
        this.thumbnailUrl = "";
        this.videoUrl = "";
    }

    protected Step(Parcel in) {
        this.id = in.readInt();
        this.description = in.readString();
        this.shortDescription = in.readString();
        this.thumbnailUrl = in.readString();
        this.videoUrl = in.readString();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel destination, int flags) {
        destination.writeInt(this.id);
        destination.writeString(this.description);
        destination.writeString(this.shortDescription);
        destination.writeString(this.thumbnailUrl);
        destination.writeString(this.videoUrl);
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };
}