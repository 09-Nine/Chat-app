package com.example.chatapp.views.act;



import com.example.chatapp.Constants;
import com.example.chatapp.R;
import com.example.chatapp.databinding.ActivityMainBinding;
import com.example.chatapp.model.User;
import com.example.chatapp.viewmodel.ChatViewModel;
import com.example.chatapp.viewmodel.MainViewModel;
import com.example.chatapp.views.fragment.ChatFragment;
import com.example.chatapp.views.fragment.HomeFragment;
import com.example.chatapp.views.fragment.SettingFragment;
import com.example.chatapp.views.fragment.SignInFragment;
import com.example.chatapp.views.fragment.SignUpFragment;
import com.example.chatapp.views.fragment.SplashFragment;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {


    @Override
    protected Class<MainViewModel> getViewModelClass() {
        return MainViewModel.class;
    }

    @Override
    protected void initViews() {
        SplashFragment splashFragment = new SplashFragment();
        splashFragment.setCallBack(this);
        showFragment(R.id.container_view, splashFragment, false, R.anim.anim_start, R.anim.anim_end);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void callBack(String key, Object data) {
        switch (key) {
            case Constants.KEY_SHOW_SIGN_IN:
                SignInFragment signInFragment = new SignInFragment();
                signInFragment.setCallBack(this);
                showFragment(R.id.container_view, signInFragment, false, R.anim.anim_start, R.anim.anim_end);
                break;
            case Constants.KEY_SHOW_SIGN_UP:
                SignUpFragment signUpFragment = new SignUpFragment();
                signUpFragment.setCallBack(this);
                showFragment(R.id.container_view, signUpFragment, false, R.anim.anim_start, R.anim.anim_end);
                break;
            case Constants.KEY_SHOW_HOME:
                HomeFragment homeFragment = new HomeFragment();
                homeFragment.setCallBack(this);
                showFragment(R.id.container_view, homeFragment, false, R.anim.anim_start, R.anim.anim_end);
                break;
            case Constants.KEY_SHOW_SETTING:
                SettingFragment settingFragment = new SettingFragment();
                settingFragment.setCallBack(this);
                showFragment(R.id.container_view, settingFragment, false, R.anim.anim_start, R.anim.anim_end);
                break;
            case Constants.KEY_SHOW_CHAT:
                ChatFragment chatFragment = new ChatFragment();
                chatFragment.setCallBack(this);
                if (data != null){
                    if (data instanceof User){
                        chatFragment.getUser((User) data);
                    }
                }
                showFragment(R.id.container_view, chatFragment, false, R.anim.anim_start, R.anim.anim_end);
                break;
        }
    }
}