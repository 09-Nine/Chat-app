package com.example.chatapp.views.fragment;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.example.chatapp.Constants;
import com.example.chatapp.R;
import com.example.chatapp.SharedPrefs;
import com.example.chatapp.databinding.SettingFragmentBinding;
import com.example.chatapp.model.User;
import com.example.chatapp.viewmodel.SettingViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class SettingFragment extends BaseFragment<SettingFragmentBinding, SettingViewModel> {
    private User currentUser;
    private SharedPrefs sharedPrefs;

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
        if (currentUser != null) {
            mViewModel.setCurrentUserLiveData(currentUser);
        }
        sharedPrefs = new SharedPrefs(requireActivity());

        binding.usernameTextView.setText(mViewModel.getCurrentUserLiveData().getValue().getUserName());
        Glide.with(requireContext()).load(Uri.parse(mViewModel.getCurrentUserLiveData().getValue().getImageUri())).into(binding.profileCircleImageView);

        binding.signOut.setOnClickListener(v -> handleSignOut());

        mViewModel.getLoggedOut().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    gotoSignInFragment();
                }
            }
        });

        binding.darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    sharedPrefs.setBooleanValue(Constants.MODE_KEY, true);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    sharedPrefs.setBooleanValue(Constants.MODE_KEY, false);
                }
            }
        });

        binding.darkModeSwitch.setChecked(sharedPrefs.getBooleanValue(Constants.MODE_KEY));

    }

    private void gotoSignInFragment(){
        callBack.callBack(Constants.KEY_SHOW_SIGN_IN, null);
    }

    private void handleSignOut(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog);
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        View v = layoutInflater.inflate(R.layout.sign_out_dialog, null);
        builder.setView(v);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        v.findViewById(R.id.cancel).setOnClickListener(v1 -> dialog.dismiss());

        v.findViewById(R.id.oke).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.setStatus(Constants.OFFLINE);
                mViewModel.signOut();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.setStatus(Constants.ONLINE);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            mViewModel.setStatus(Constants.OFFLINE);
        }
    }
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
