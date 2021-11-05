package com.example.chatapp.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatapp.R;
import com.example.chatapp.viewmodel.LoginRegisterViewModel;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;


public class RegisterFragment extends Fragment {
    private EditText emailReg, passReg, nameReg, passConfirmReg;
    private Button registerBtn;
    private CircleImageView profileImage;
    private LoginRegisterViewModel loginRegisterViewModel;
    private Uri imageUri;
    public static final int PICK_IMAGE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        loginRegisterViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(requireActivity().getApplication())).get(LoginRegisterViewModel.class);

        loginRegisterViewModel.getUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null){
                    Toast.makeText(requireActivity(), "User " + firebaseUser.getEmail() + " created", Toast.LENGTH_SHORT).show();
                    SignInFragment signInFragment = new SignInFragment();
                    FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.container_view, signInFragment);
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
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        nameReg = view.findViewById(R.id.register_name);
        emailReg =  view.findViewById(R.id.register_email);
        passReg =  view.findViewById(R.id.register_password);
        passConfirmReg = view.findViewById(R.id.register_confirm_password);
        profileImage = view.findViewById(R.id.profile_image);
        registerBtn =  view.findViewById(R.id.register);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailReg.getText().toString();
                String password = passReg.getText().toString();
                String name = nameReg.getText().toString();
                String cPassword = passConfirmReg.getText().toString();
                if (!email.isEmpty() && !password.isEmpty()){
                    loginRegisterViewModel.register(email, password, name, cPassword, imageUri);
                }
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select picture"), PICK_IMAGE);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE){
            if (data != null){
                imageUri = data.getData();
                profileImage.setImageURI(imageUri);
            }
        }
    }
}