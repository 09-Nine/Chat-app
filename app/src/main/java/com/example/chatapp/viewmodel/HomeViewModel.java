package com.example.chatapp.viewmodel;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.chatapp.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {
    private final FirebaseDatabase database;
    private ArrayList<User> userArrayList;
    private MutableLiveData<ArrayList<User>> users;

    public HomeViewModel() {
        database = FirebaseDatabase.getInstance();
        userArrayList = new ArrayList<>();
        users = new MutableLiveData<>();
        DatabaseReference reference = database.getReference().child("users-reg");
        if (userArrayList.size() == 0){
            loadUser();
        }
    }

    //async error
    public void loadUser(){
        DatabaseReference reference = database.getReference().child("users-reg");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    userArrayList.add(user);
                }
                users.postValue(userArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public MutableLiveData<ArrayList<User>> getUsers() {
        return users;
    }
}
