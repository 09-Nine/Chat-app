package com.example.chatapp.views.fragment;

import android.view.View;

import androidx.lifecycle.Observer;

import com.example.chatapp.Constants;
import com.example.chatapp.R;
import com.example.chatapp.databinding.SettingFragmentBinding;
import com.example.chatapp.viewmodel.SettingViewModel;

public class SettingFragment extends BaseFragment<SettingFragmentBinding, SettingViewModel> {
    @Override
    protected Class<SettingViewModel> getViewModelClass() {
        return SettingViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.setting_fragment;
    }

    @Override
    protected void initViews() {
        binding.signOut.setOnClickListener(v -> mViewModel.signOut());

        mViewModel.getLoggedOut().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    gotoSignInFragment();
                }
            }
        });
    }

    private void gotoSignInFragment(){
        callBack.callBack(Constants.KEY_SHOW_SIGN_IN, null);
    }
}
