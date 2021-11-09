package com.example.chatapp.views.fragment;


import android.net.Uri;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.chatapp.Constants;
import com.example.chatapp.R;
import com.example.chatapp.adapters.UsersAdapter;
import com.example.chatapp.databinding.HomeFragmentBinding;
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
        binding.mainUserRecycleView.setHasFixedSize(true);
        binding.mainUserRecycleView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.currentUserAvatar.setOnClickListener(v -> gotoSettingFragment());

        adapter = new UsersAdapter(mViewModel.getUserArrayList());
        binding.mainUserRecycleView.setAdapter(adapter);



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
}
