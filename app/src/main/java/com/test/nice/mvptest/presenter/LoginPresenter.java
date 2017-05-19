package com.test.nice.mvptest.presenter;

import com.test.nice.mvptest.base.presenter.BasePresenterImpl;
import com.test.nice.mvptest.view.ILoginView;

/**
 * Created by walter on 2017/5/19.
 */

public class LoginPresenter extends BasePresenterImpl<ILoginView>{
    public LoginPresenter(ILoginView loginView) {
        this.mView = loginView;
    }


    public void doLogin() {
        mView.onSuccess();
    }
}
