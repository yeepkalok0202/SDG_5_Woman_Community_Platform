package com.example.wia2007mad.AllModules;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;


public class WorkshopModel implements Parcelable {
    String Author,Description,ThumbnailURL, Title,VideoURL;
    public WorkshopModel(){}


    public WorkshopModel(String author, String description, String thumbnailURL, String title, String videoURL) {
        Author = author;
        Description = description;
        ThumbnailURL = thumbnailURL;
        Title = title;
        VideoURL = videoURL;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getThumbnailURL() {
        return ThumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        ThumbnailURL = thumbnailURL;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getVideoURL() {
        return VideoURL;
    }

    public void setVideoURL(String videoURL) {
        VideoURL = videoURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected WorkshopModel(Parcel in){
        this.Author=in.readString();
        this.Description=in.readString();
        this.ThumbnailURL=in.readString();
        this.Title=in.readString();
        this.VideoURL=in.readString();
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(Author);
        dest.writeString(Description);
        dest.writeString(ThumbnailURL);
        dest.writeString(Title);
        dest.writeString(VideoURL);
    }


    public static final Creator<WorkshopModel> CREATOR=  new Creator<WorkshopModel>() {
        @Override
        public WorkshopModel createFromParcel(Parcel source) {
            return new WorkshopModel(source);
        }

        @Override
        public WorkshopModel[] newArray(int size) {
            return new WorkshopModel[size];
        }
    };


}
