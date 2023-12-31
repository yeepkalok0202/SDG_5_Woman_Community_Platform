package com.example.wia2007mad.AllModules;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ScholarshipModel implements Parcelable {
    private String scholarshipTitle, scholarshipDeadline, scholarshipDescription, scholarshipImageURL, scholarshipURL;

    public ScholarshipModel(){
    }
    public ScholarshipModel(String scholarshipTitle, String scholarshipDeadline, String scholarshipDescription, String scholarshipImageURL, String scholarshipURL) {
        this.scholarshipTitle = scholarshipTitle;
        this.scholarshipDeadline = scholarshipDeadline;
        this.scholarshipDescription = scholarshipDescription;
        this.scholarshipImageURL = scholarshipImageURL;
        this.scholarshipURL = scholarshipURL;
    }

    public String getScholarshipTitle() {
        return scholarshipTitle;
    }

    public void setScholarshipTitle(String scholarshipTitle) {
        this.scholarshipTitle = scholarshipTitle;
    }

    public String getScholarshipDeadline() {
        return scholarshipDeadline;
    }

    public void setScholarshipDeadline(String scholarshipDeadline) {
        this.scholarshipDeadline = scholarshipDeadline;
    }

    public String getScholarshipDescription() {
        return scholarshipDescription;
    }

    public void setScholarshipDescription(String scholarshipDescription) {
        this.scholarshipDescription = scholarshipDescription;
    }

    public String getScholarshipImageURL() {
        return scholarshipImageURL;
    }

    public void setScholarshipImageURL(String scholarshipImageURL) {
        this.scholarshipImageURL = scholarshipImageURL;
    }

    public String getScholarshipURL() {
        return scholarshipURL;
    }

    public void setScholarshipURL(String scholarshipURL) {
        this.scholarshipURL = scholarshipURL;
    }
    @Override
    public int describeContents() {
        return 0;
    }
    protected ScholarshipModel(Parcel in){
        this.scholarshipTitle =in.readString();
        this.scholarshipDeadline =in.readString();
        this.scholarshipDescription =in.readString();
        this.scholarshipImageURL =in.readString();
        this.scholarshipURL =in.readString();
    }
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(scholarshipTitle);
        dest.writeString(scholarshipDeadline);
        dest.writeString(scholarshipDescription);
        dest.writeString(scholarshipImageURL);
        dest.writeString(scholarshipURL);
    }

    //Parcelable.Creator

    public static final Creator<ScholarshipModel> CREATOR= new Creator<ScholarshipModel>(){

        @Override
        public ScholarshipModel createFromParcel(Parcel source) {
            return new ScholarshipModel(source);
        }

        @Override
        public ScholarshipModel[] newArray(int size) {
            return new ScholarshipModel[size];
        }
    };

}
