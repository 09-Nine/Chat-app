package com.example.chatapp.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.example.chatapp.model.User;

public class SettingViewModel extends BaseViewModel{
    private MutableLiveData<User> currentUserLiveData = new MutableLiveData<>();

    public void signOut(){
        firebaseAuth.signOut();
        loggedOut.postValue(true);
    }

    public void setCurrentUserLiveData(User currentUser) {
        currentUserLiveData.setValue(currentUser);
    }

    public MutableLiveData<User> getCurrentUserLiveData() {
        return currentUserLiveData;
    }

    public MutableLiveData<Boolean> getLoggedOut() {
        return loggedOut;
    }
}
