package com.example.wia2007mad.AllModules.socialmarket;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.wia2007mad.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder>{

    private List<ToDoModel> todoList;
    private TodoList activity;
    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;
    private DocumentReference documentReference;

    public ToDoAdapter(TodoList mainActivity , List<ToDoModel> todoList){
        this.todoList = todoList;
        activity = mainActivity;
    }
    @androidx.annotation.NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        firebaseAuth= FirebaseAuth.getInstance();

        View view = LayoutInflater.from(activity).inflate(R.layout.each_task , parent , false);
        db = FirebaseFirestore.getInstance();
        documentReference=db.collection("users").document(firebaseAuth.getUid());

        return new MyViewHolder(view);
    }

    //delete task
    public void deleteTask(int position){
        ToDoModel toDoModel = todoList.get(position);
        documentReference.collection("task").document(toDoModel.TaskId).delete();
        todoList.remove(position);
        notifyItemRemoved(position);
    }

    //edit task
    public void editTask(int position){
        ToDoModel toDoModel = todoList.get(position);

        Bundle bundle = new Bundle();
        bundle.putString("task" , toDoModel.getTask());
        bundle.putString("due" , toDoModel.getDue());
        bundle.putString("id" , toDoModel.TaskId);

        AddNewTask addNewTask = new AddNewTask();
        addNewTask.setArguments(bundle);
        addNewTask.show(activity.getSupportFragmentManager() , addNewTask.getTag());
    }

    public Context getContext(){
        return activity;
    }
    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull MyViewHolder holder, int position) {
        ToDoModel toDoModel = todoList.get(position);
        holder.mCheckBox.setText(toDoModel.getTask());

        holder.mDueDateTv.setText("Due On " + toDoModel.getDue());

        holder.mCheckBox.setChecked(toBoolean(toDoModel.getStatus()));

        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    documentReference.collection("task").document(toDoModel.TaskId).update("status" , 1);
                }else{
                    documentReference.collection("task").document(toDoModel.TaskId).update("status" , 0);
                }
            }
        });

    }

    private boolean toBoolean(int status){
        return status != 0;
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mDueDateTv;
        CheckBox mCheckBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mDueDateTv = itemView.findViewById(R.id.due_date_tv);
            mCheckBox = itemView.findViewById(R.id.mcheckbox);

        }
    }

}
