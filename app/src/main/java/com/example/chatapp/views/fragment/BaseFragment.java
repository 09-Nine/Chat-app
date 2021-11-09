package com.example.chatapp.views.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.chatapp.interfaces.OnActionCallBack;

public abstract class BaseFragment<K extends ViewDataBinding, V extends ViewModel> extends Fragment {
    protected Context mContext;
    protected View mRootView;
    protected OnActionCallBack callBack;

    protected K binding;
    protected V mViewModel;

    public void setCallBack(OnActionCallBack callBack) {
        this.callBack = callBack;
    }

    public final <T extends View> T findViewById(int id, View.OnClickListener event){
        T v = mRootView.findViewById(id);
        if (v != null && event != null) {
            v.setOnClickListener(event);
        }
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(getViewModelClass());
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        initViews();
        return binding.getRoot();
    }

    public void makeToast(String message){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    protected abstract Class<V> getViewModelClass();

    protected abstract int getLayoutId();

    protected abstract void initViews();
}
