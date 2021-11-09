package com.example.chatapp.views.fragment;

import android.view.View;

import androidx.lifecycle.Observer;

import com.example.chatapp.Constants;
import com.example.chatapp.R;
import com.example.chatapp.databinding.SignInFragmentBinding;
import com.example.chatapp.viewmodel.SignInViewModel;
import com.google.firebase.auth.FirebaseUser;

public class SignInFragment extends BaseFragment<SignInFragmentBinding, SignInViewModel> {

    @Override
    protected Class<SignInViewModel> getViewModelClass() {
        return SignInViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.sign_in_fragment;
    }

    @Override
    protected void initViews() {
        binding.createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoSignUpFragment();
            }
        });

        binding.login.setOnClickListener(v -> signInUser());

        mViewModel.getUserMutableLiveData().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    gotoHomeFragment();
                }
            }
        });
    }

    private void signInUser(){
        String email = binding.signInEmail.getText().toString();
        String password = binding.signInPassword.getText().toString();
        if (!email.trim().isEmpty() && !password.trim().isEmpty()){
            mViewModel.signIn(email, password);
        }
    }

    private void gotoSignUpFragment(){
        callBack.callBack(Constants.KEY_SHOW_SIGN_UP, null);
    }

    private void gotoHomeFragment(){
        callBack.callBack(Constants.KEY_SHOW_HOME, null);
    }
}
