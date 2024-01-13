package com.example.wia2007mad.AllModules.socialmarket;

import android.support.annotation.NonNull;

import com.google.firebase.database.Exclude;

public class TaskId {
    @Exclude
    public String TaskId;

    public  <T extends  TaskId> T withId(@NonNull final String id){
        this.TaskId = id;
        return  (T) this;
    }
}
