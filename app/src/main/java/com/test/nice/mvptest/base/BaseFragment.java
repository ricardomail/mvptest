package com.test.nice.mvptest.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.test.nice.mvptest.base.presenter.BasePresenterImpl;


/**
 * Created by walter on 2017/2/28.
 */

public abstract class BaseFragment<V,T extends BasePresenterImpl> extends Fragment{
    public Context mContext;

    public Toast mToast;

    private Dialog mLoadingDialog;

    private int mRequestCode;

//    private IPermissionResultListener mIPermissionResultListener;

    private T prestener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(setFragmentLayoutID(), container, false);

        //  SetStatusBarColor(R.color.home_blue);
        initPresenter();
        initView(rootView);
        initData();
        return rootView;
    }

    public abstract int setFragmentLayoutID();

    protected abstract void initView(View rootView);

    protected abstract void initData();

    protected abstract void initPresenter();


    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor(int color) {
//        //清除黑字体状态栏

    }





    /**
     * 检查权限
     */
    private boolean checkPermission(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }

    /**
     * 申请权限
     */
    private void requestPermission(String msg, String[] permissions, int requestCode) {
        //先判断是否已经拒绝过了
        if (shouldShowRequestPermission(permissions)) {
            //弹出Dialog 给用户说明
            showRequestDialog(msg, permissions, requestCode);
        } else {
            ActivityCompat.requestPermissions(getActivity(), permissions, requestCode);
        }
    }

    /**
     * 再次申请时
     */
    private boolean shouldShowRequestPermission(String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 弹出的Dialog
     */
    private void showRequestDialog(String msg, final String[] permissions, final int requestCode) {
        new AlertDialog.Builder(getActivity())
                .setTitle("友好提醒")
                .setMessage(msg)
                .setPositiveButton("好，给你", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(getActivity(), permissions, requestCode);
                    }
                })
                .setNegativeButton("我拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }



    /**
     * 检查回调结果
     */
    private boolean checkRequestResult(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 显示Toast,页面中重复Toast不会重复实例化Toast对象
     *
     * @param str      String
     * @param duration 显示时间
     */
    public void showToast(String str, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(getActivity(), str, duration);
        } else {
            mToast.setText(str);
            mToast.setDuration(duration);
        }
        mToast.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(prestener != null){
            prestener.attach((V)this);
        }else{}

    }

    @Override
    public void onDestroy() {
        if (prestener != null){
            prestener.dettach();
        }else{}
        super.onDestroy();
    }
}
