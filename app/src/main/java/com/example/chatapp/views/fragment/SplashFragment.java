package com.example.chatapp.views.fragment;

import android.os.Handler;

import com.example.chatapp.Constants;
import com.example.chatapp.R;
import com.example.chatapp.databinding.SplashFragmentBinding;
import com.example.chatapp.viewmodel.SplashViewModel;

public class SplashFragment extends BaseFragment<SplashFragmentBinding, SplashViewModel> {
    @Override
    protected Class<SplashViewModel> getViewModelClass() {
        return SplashViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.splash_fragment;
    }

    @Override
    protected void initViews() {
        new Handler().postDelayed(this::gotoSignIn, 2000);

    }

    private void gotoSignIn(){
        callBack.callBack(Constants.KEY_SHOW_SIGN_IN, null);
    }
}
