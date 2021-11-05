package com.example.chatapp.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.example.chatapp.adapters.UsersAdapter;
import com.example.chatapp.model.User;
import com.example.chatapp.viewmodel.HomeViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private UsersAdapter adapter;
    private RecyclerView mainUserRecycleView;
    private HomeViewModel homeViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mainUserRecycleView = view.findViewById(R.id.main_user_recycle_view);
        mainUserRecycleView.setLayoutManager(new LinearLayoutManager(requireActivity()));


        homeViewModel.getUsers().observe(getViewLifecycleOwner(), new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> users) {
                adapter.notifyDataSetChanged();
            }
        });

        adapter = new UsersAdapter(homeViewModel.getUsers().getValue());
        mainUserRecycleView.setAdapter(adapter);

        return view;
    }
}
