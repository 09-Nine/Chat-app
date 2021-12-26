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
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.auth.FirebaseUser;


public class SignUpFragment extends BaseFragment<SignUpFragmentBinding, SignUpViewModel> {

    private Uri imageUri;


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
        binding.signUp.setOnClickListener(v -> signUpUser());

        binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(SignUpFragment.this)
                        .crop()
                        .start(Constants.PICK_IMAGE);
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

        mViewModel.getErrorMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                makeToast(s);
                binding.signUp.revertAnimation();
                binding.signUp.setBackgroundResource(R.drawable.background_button);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.PICK_IMAGE){
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
        binding.signUp.startAnimation();
        String email = binding.registerEmail.getText().toString().trim();
        String password = binding.registerPassword.getText().toString().trim();
        String name = binding.registerName.getText().toString().trim();
        String cPassword = binding.registerConfirmPassword.getText().toString().trim();
        mViewModel.signUp(email, password, name,cPassword, imageUri);
    }
}