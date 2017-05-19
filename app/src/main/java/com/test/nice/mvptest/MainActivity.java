package com.test.nice.mvptest;

import android.os.Bundle;
import android.widget.Toast;

import com.test.nice.mvptest.base.BaseActivity;
import com.test.nice.mvptest.presenter.LoginPresenter;
import com.test.nice.mvptest.view.ILoginView;

public class MainActivity extends BaseActivity<ILoginView,LoginPresenter> implements ILoginView{

    @Override
    protected LoginPresenter initPresenter() {
        presenter = new LoginPresenter(this);
        return presenter;
    }

    @Override
    protected void initView(Bundle saveInstanceState) {

    }

    @Override
    protected void initData() {
        presenter.doLogin();
    }

    @Override
    protected void initConvetView(Bundle saveInstanceState) {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void onSuccess() {
        showToast("success", Toast.LENGTH_SHORT);
    }
}
