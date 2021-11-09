package com.example.chatapp.views.fragment;

import android.content.Intent;
import android.net.Uri;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import android.view.View;

import com.example.chatapp.Constants;
import com.example.chatapp.R;
import com.example.chatapp.databinding.SignUpFragmentBinding;
import com.example.chatapp.viewmodel.SignUpViewModel;
import com.google.firebase.auth.FirebaseUser;


public class SignUpFragment extends BaseFragment<SignUpFragmentBinding, SignUpViewModel> {

    private Uri imageUri;
    public static final int PICK_IMAGE = 1;


    @Override
    protected Class<SignUpViewModel> getViewModelClass() {
        return SignUpViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.sign_up_fragment;
    }

    @Override
    protected void initViews() {
        binding.register.setOnClickListener(v -> signUpUser());

        binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select picture"), PICK_IMAGE);
            }
        });

        binding.signInLink.setOnClickListener(v -> gotoSignInFragment());

        mViewModel.getUserMutableLiveData().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null){
                    makeToast("User " +  firebaseUser.getEmail() + " has been created");
                    gotoSignInFragment();
                }
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE){
            if (data != null){
                imageUri = data.getData();
                binding.profileImage.setImageURI(imageUri);
            }
        }
    }

    private void gotoSignInFragment(){
        callBack.callBack(Constants.KEY_SHOW_SIGN_IN, null);
    }

    private void signUpUser(){
        String email = binding.registerEmail.getText().toString();
        String password = binding.registerPassword.getText().toString();
        String name = binding.registerName.getText().toString();
        String cPassword = binding.registerConfirmPassword.getText().toString();
        if (!password.equals(cPassword)){
            makeToast("Password and confirm password must be same");
        } else {
            mViewModel.signUp(email, password, name, imageUri);
        }
    }
}