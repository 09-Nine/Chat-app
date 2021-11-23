package com.example.chatapp.views.fragment;

import android.net.Uri;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.chatapp.Constants;
import com.example.chatapp.R;
import com.example.chatapp.adapters.ChatAdapter;
import com.example.chatapp.databinding.ChatFragmentBinding;
import com.example.chatapp.model.Message;
import com.example.chatapp.model.User;
import com.example.chatapp.viewmodel.ChatViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Date;

public class ChatFragment extends BaseFragment<ChatFragmentBinding, ChatViewModel> {
    private User receiverUser;
    private String senderUserUid;
    private ChatAdapter chatAdapter;

    @Override
    protected Class<ChatViewModel> getViewModelClass() {
        return ChatViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.chat_fragment;
    }

    @Override
    protected void initViews() {
        senderUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mViewModel.setChatRoom(senderUserUid, receiverUser.getUid());
        mViewModel.loadMessage();
        Glide.with(requireContext()).load(Uri.parse(receiverUser.getImageUri())).into(binding.receiveUserAvatar);
        binding.receiveUserName.setText(receiverUser.getUserName());
        binding.sendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messText = binding.sendingMess.getText().toString();
                if(!messText.equals("")){
                    mViewModel.sendMessage(senderUserUid, receiverUser.getUid(), messText, (new Date()).toString());
                    binding.sendingMess.setText("");
                }
            }
        });
        binding.messageRecycleView.setHasFixedSize(true);
        binding.messageRecycleView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.messageRecycleView.setNestedScrollingEnabled(false);
        chatAdapter = new ChatAdapter(mViewModel.getMessages(), receiverUser.getImageUri());
        binding.messageRecycleView.setAdapter(chatAdapter);

        mViewModel.getMessageLiveData().observe(this, new Observer<ArrayList<Message>>() {
            @Override
            public void onChanged(ArrayList<Message> messages) {
                chatAdapter.notifyDataSetChanged();
            }
        });
        binding.arrowBack.setOnClickListener(v -> gotoHomeFragment());
    }

    public void getUser(User user){
        this.receiverUser = user;
    }

    private void gotoHomeFragment(){
        callBack.callBack(Constants.KEY_SHOW_HOME, null);
    }
}
