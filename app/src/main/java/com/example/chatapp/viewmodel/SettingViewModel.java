package com.example.chatapp.viewmodel;

import androidx.lifecycle.MutableLiveData;

public class SettingViewModel extends BaseViewModel{

    public void signOut(){
        firebaseAuth.signOut();
        loggedOut.postValue(true);
    }

    public MutableLiveData<Boolean> getLoggedOut() {
        return loggedOut;
    }
}
