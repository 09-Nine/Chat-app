package com.example.chatapp.viewmodel;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.chatapp.Constants;
import com.example.chatapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SignUpViewModel extends BaseViewModel{

    private MutableLiveData<FirebaseUser> userMutableLiveData;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    public SignUpViewModel() {
        userMutableLiveData = new MutableLiveData<>();

    }

    public void signUp(String email, String password, String name, Uri imageUri){
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    databaseReference = firebaseDatabase.getReference().child("users-reg").child(firebaseAuth.getUid());
                    storageReference = firebaseStorage.getReference().child("avatar").child(firebaseAuth.getUid());
                    if (imageUri != null){
                        storageReference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                if (task.isSuccessful()){
                                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            upUserToDatabase(firebaseAuth.getUid(), name, email, uri.toString());
                                        }
                                    });
                                }
                            }
                        });
                    } else {
                        upUserToDatabase(firebaseAuth.getUid(), name, email, Constants.USER_IMAGE);
                    }
                } else {

                }
            }
        });
    }

    public void upUserToDatabase(String uid, String name, String email, String imageUri){
        User user = new User(uid, name, email, imageUri);
        databaseReference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                } else {

                }
            }
        });
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }
}
