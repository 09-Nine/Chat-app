package com.example.chatapp.views.fragment;


import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.chatapp.Constants;
import com.example.chatapp.R;
import com.example.chatapp.adapters.UsersAdapter;
import com.example.chatapp.databinding.HomeFragmentBinding;
import com.example.chatapp.interfaces.AdapterListener;
import com.example.chatapp.model.User;
import com.example.chatapp.viewmodel.HomeViewModel;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment<HomeFragmentBinding, HomeViewModel> {

    private UsersAdapter adapter;

    @Override
    protected Class<HomeViewModel> getViewModelClass() {
        return HomeViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_fragment;
    }

    @Override
    protected void initViews() {
        mViewModel.loadUser();
        binding.mainUserRecycleView.setHasFixedSize(true);
        binding.mainUserRecycleView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.currentUserAvatar.setOnClickListener(v -> gotoSettingFragment());

        adapter = new UsersAdapter(mViewModel.getUserArrayList());
        binding.mainUserRecycleView.setAdapter(adapter);
        adapter.setListener(new AdapterListener() {
            @Override
            public void onClick(User user) {
                gotoChatFragment(user);
            }
        });

        binding.userSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mViewModel.searchUser(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mViewModel.getUsers().observe(this, new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> users) {
                adapter.notifyDataSetChanged();
                if (mViewModel.getCurrentUserAvatar() != null){
                    Glide.with(requireContext()).load(Uri.parse(mViewModel.getCurrentUserAvatar())).into(binding.currentUserAvatar);
                }
            }
        });
    }

    private void gotoSettingFragment(){
        callBack.callBack(Constants.KEY_SHOW_SETTING, null);
    }

    private void gotoChatFragment(User user){
        callBack.callBack(Constants.KEY_SHOW_CHAT, user);
    }
}
