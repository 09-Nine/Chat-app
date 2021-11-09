package com.example.chatapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class BaseViewModel extends ViewModel {
    protected final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    protected final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    protected final FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    protected MutableLiveData<Boolean> loggedOut = new MutableLiveData<>();
}
