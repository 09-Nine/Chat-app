package com.example.chatapp.viewmodel;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.chatapp.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeViewModel extends BaseViewModel {
    private ArrayList<User> userArrayList;
    private MutableLiveData<ArrayList<User>> users;
    private User currentUser;
    private  DatabaseReference reference;

    public HomeViewModel() {
        userArrayList = new ArrayList<>();
        users = new MutableLiveData<>();
        reference = firebaseDatabase.getReference().child("users-reg");
    }

    public void loadUser(){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (firebaseAuth.getCurrentUser() != null) {
                    userArrayList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        User user = dataSnapshot.getValue(User.class);
                        if (user.getUid().equals(firebaseAuth.getCurrentUser().getUid())){
                            currentUser = user;
                        } else {
                            userArrayList.add(user);
                        }
                    }
                    users.postValue(userArrayList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void searchUser(String s){
        Query query = reference.orderByChild("userName")
                .startAt(s)
                .endAt(s+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (firebaseAuth.getCurrentUser() != null) {
                    userArrayList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        User user = dataSnapshot.getValue(User.class);
                        if (user.getUid().equals(firebaseAuth.getCurrentUser().getUid())){
                            currentUser = user;
                        } else {
                            userArrayList.add(user);
                        }
                    }
                    users.postValue(userArrayList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public MutableLiveData<ArrayList<User>> getUsers() {
        return users;
    }

    public ArrayList<User> getUserArrayList(){
        return  userArrayList;
    }

    public User getCurrentUser() {
        return currentUser;
    }

}
