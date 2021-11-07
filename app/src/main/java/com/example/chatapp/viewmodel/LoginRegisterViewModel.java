package com.example.chatapp.viewmodel;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.example.chatapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class LoginRegisterViewModel extends AndroidViewModel {


    private final FirebaseAuth firebaseAuth;
    private final FirebaseDatabase database;
    private final FirebaseStorage storage;
    private MutableLiveData<FirebaseUser> userMutableLiveData;
    private MutableLiveData<Boolean> userLoggedMutableLiveData;
    static Context mContext;

    public LoginRegisterViewModel(@NonNull Application application) {
        super(application);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        userMutableLiveData = new MutableLiveData<>();
        userLoggedMutableLiveData = new MutableLiveData<>();
//        if (firebaseAuth.getCurrentUser() != null) {
//            userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
//        }
    }

    public void init(Context context){
        mContext = context;
    }

    public void register(String email, String password, String name, String cPassword, Uri imageUri){

        if (!password.equals(cPassword)){
            Toast.makeText(getApplication(), "Password does not match", Toast.LENGTH_SHORT).show();
        } else {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                DatabaseReference databaseReference = database.getReference().child("users-reg").child(firebaseAuth.getUid());
                                StorageReference storageReference = storage.getReference().child("avatar").child(firebaseAuth.getUid());

                                if (imageUri != null) {
                                    storageReference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                            if (task.isSuccessful()){
                                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        User user = new User(firebaseAuth.getUid(), name, email, uri.toString());
                                                        databaseReference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()){
                                                                    Toast.makeText(getApplication(), "register success", Toast.LENGTH_SHORT).show();
                                                                    userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                                                                } else {
                                                                    Toast.makeText(getApplication(), "Fail to create user", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                        }
                                    });
                                } else {
                                    User user = new User(firebaseAuth.getUid(), name, email, "https://firebasestorage.googleapis.com/v0/b/chat-app-795a3.appspot.com/o/user.png?alt=media&token=40b655a1-9aac-4c78-b430-9582486883a9");
                                    databaseReference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(getApplication(), "register success", Toast.LENGTH_SHORT).show();
                                                userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                                            } else {
                                                Toast.makeText(getApplication(), "Fail to create user", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }


                            } else {
                                Toast.makeText(getApplication(), "Register fail! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }


    }

    public void login(String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                        } else {
                            Toast.makeText(getApplication(), "Login fail! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public MutableLiveData<Boolean> getUserLoggedMutableLiveData() {
        return userLoggedMutableLiveData;
    }

    public void signOut(){
       firebaseAuth.signOut();
       userLoggedMutableLiveData.postValue(true);
    }
}
