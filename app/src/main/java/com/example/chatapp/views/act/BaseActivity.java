package com.example.chatapp.views.act;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.chatapp.interfaces.OnActionCallBack;

public abstract class BaseActivity<BD extends ViewDataBinding, VM extends ViewModel> extends AppCompatActivity
        implements OnActionCallBack {

    protected  BD binding;
    protected  VM viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setUpAct();
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(getViewModelClass());
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        initViews();
    }

    protected abstract Class<VM> getViewModelClass();

    protected  abstract void initViews();
    protected abstract int getLayoutId();
    protected abstract void setUpAct();

    protected void showFragment(int layoutID, Fragment fragment, boolean addToBackStack,
                                int anim_start, int anim_end){
        if (!isFinishing() && !isDestroyed()) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (anim_start != 0 && anim_end != 0){
                ft.setCustomAnimations(anim_start, anim_end);
            }
            ft.replace(layoutID, fragment);
            if (addToBackStack){
                ft.addToBackStack("add");
            }
            ft.commit();
        }

    }
}
