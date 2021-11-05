package com.example.chatapp.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.chatapp.R;
import com.example.chatapp.viewmodel.LoginRegisterViewModel;
import com.google.firebase.auth.FirebaseUser;

public class SignInFragment extends Fragment {
    private EditText emailEdit, passEdit;
    private Button loginBtn;
    private TextView registerBtn;
    private LoginRegisterViewModel loginRegisterViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginRegisterViewModel = new ViewModelProvider(requireActivity(), ViewModelProvider.AndroidViewModelFactory
                .getInstance(requireActivity().getApplication())).get(LoginRegisterViewModel.class);
        loginRegisterViewModel.getUserMutableLiveData().observe(requireActivity(), new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    HomeFragment homeFragment = new HomeFragment();
                    FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.container_view, homeFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        emailEdit = (EditText) view.findViewById(R.id.sign_in_email);
        passEdit = (EditText) view.findViewById(R.id.sign_in_password);
        loginBtn = (Button) view.findViewById(R.id.register_login);
        registerBtn = (TextView) view.findViewById(R.id.register_register);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFragment registerFragment = new RegisterFragment();
                FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container_view, registerFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEdit.getText().toString();
                String password = passEdit.getText().toString();
                if (!email.isEmpty() && !password.isEmpty()){
                    loginRegisterViewModel.login(email, password);
                }
            }
        });

        return view;
    }
}
