package com.test.nice.mvptest.base.presenter;


import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * 作者：Gemeni on 2016/12/20 0020 17:11
 */
public class BasePresenterImpl<T> implements BasePresenter {
    private CompositeSubscription mCompositeSubscription;
    public T mView;

    public void attach(T mView){
        this.mView = mView;
    }

    public void dettach(){
        mView = null;
    }

    /**
     * rxjava注册
     * @param subscription
     */
    protected void addSubscription(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    /**
     * rxjava解除注册
     */
    @Override
    public void unsubcrible() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }
}
