package com.example.wia2007mad.AllModules;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class JobModel implements Parcelable {
    private String jobTitle, jobDeadline, jobDescription, jobImageURL, jobURL;

    public JobModel(){
    }
    public JobModel(String jobTitle, String jobDeadline, String jobDescription, String jobImageURL, String jobURL) {
        this.jobTitle = jobTitle;
        this.jobDeadline = jobDeadline;
        this.jobDescription = jobDescription;
        this.jobImageURL = jobImageURL;
        this.jobURL = jobURL;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDeadline() {
        return jobDeadline;
    }

    public void setJobDeadline(String jobDeadline) {
        this.jobDeadline = jobDeadline;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobImageURL() {
        return jobImageURL;
    }

    public void setJobImageURL(String jobImageURL) {
        this.jobImageURL = jobImageURL;
    }

    public String getJobURL() {
        return jobURL;
    }

    public void setJobURL(String jobURL) {
        this.jobURL = jobURL;
    }
    @Override
    public int describeContents() {
        return 0;
    }
    protected JobModel(Parcel in){
        this.jobTitle =in.readString();
        this.jobDeadline =in.readString();
        this.jobDescription =in.readString();
        this.jobImageURL =in.readString();
        this.jobURL =in.readString();
    }
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(jobTitle);
        dest.writeString(jobDeadline);
        dest.writeString(jobDescription);
        dest.writeString(jobImageURL);
        dest.writeString(jobURL);
    }

    //Parcelable.Creator

    public static final Creator<JobModel> CREATOR= new Creator<JobModel>(){

        @Override
        public JobModel createFromParcel(Parcel source) {
            return new JobModel(source);
        }

        @Override
        public JobModel[] newArray(int size) {
            return new JobModel[size];
        }
    };

}