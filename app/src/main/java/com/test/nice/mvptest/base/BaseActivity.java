package com.test.nice.mvptest.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.test.nice.mvptest.R;
import com.test.nice.mvptest.base.presenter.BasePresenterImpl;


/**
 * Created by walter on 2017/2/28.
 */

public abstract class BaseActivity<V, T extends BasePresenterImpl> extends AppCompatActivity {
    private int mRequestCode;
    public static BaseActivity activity;
    public Toast mToast;
    //    private NetWorkBroadcastReceiver netWorkBroadcastReceiver;
    public T presenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        initConvetView(savedInstanceState);

        SetStatusBarColor(R.color.colorAccent);
        initPresenter();
        initView(savedInstanceState);
        initData();
//        initReceiver();
    }

    protected abstract T initPresenter();//初始化presenter

    protected abstract void initView(Bundle saveInstanceState);//视图初始化

    protected abstract void initData();//初始化数据

    protected abstract void initConvetView(Bundle saveInstanceState);

    /**
     * 初始化广播
     */
//    private void initReceiver(){
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//        netWorkBroadcastReceiver = new NetWorkBroadcastReceiver();
//        registerReceiver(netWorkBroadcastReceiver, intentFilter);
//    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor(int color) {
//        //清除黑字体状态栏


    }

    /**
     * 沉浸状态栏（4.4以上系统有效）
     */

//    protected void SetTranslanteBar() {
//        StatusBarCompat.translucentStatusBar(this);
//    }
//
//
//    protected void permissionRequest(String msg, String[] permissions, int requestCode, IPermissionResultListener iPermissionResultListener) {
//        if (permissions == null || permissions.length == 0) {
//            return;
//        }
//        mRequestCode = requestCode;
//        mIPermissionResultListener = iPermissionResultListener;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (checkPermission(permissions)) { //如果没有权限 需要申请
//                requestPermission(msg, permissions, requestCode);
//            } else {  //有权限
//                mIPermissionResultListener.onPermissionSuccess();
//            }
//        } else {
//            if (mIPermissionResultListener != null) {
//                mIPermissionResultListener.onPermissionSuccess();
//            }
//        }
//
//    }

    /**
     * 检查权限
     */
    private boolean checkPermission(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
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
            ActivityCompat.requestPermissions(BaseActivity.this, permissions, requestCode);
        }
    }

    /**
     * 再次申请时
     */
    private boolean shouldShowRequestPermission(String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 弹出的Dialog
     */
    private void showRequestDialog(String msg, final String[] permissions, final int requestCode) {
        new AlertDialog.Builder(this)
                .setTitle("友好提醒")
                .setMessage(msg)
                .setPositiveButton("好，给你", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(BaseActivity.this, permissions, requestCode);
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
            mToast = Toast.makeText(getApplicationContext(), str, duration);
        } else {
            mToast.setText(str);
            mToast.setDuration(duration);
        }
        mToast.show();
    }

    //隐藏软键盘
    public void hideKeyboard(IBinder windowToken) {
        if (windowToken != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 检查网络状态
     */
//    class NetWorkBroadcastReceiver extends BroadcastReceiver {
//
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            /**
//             * networkInfo.isAvailable();
//             * 是否有网络连接
//             */
//            if (NetWorkUtil.isNetWorkAvailable(context)) {
////                if (dialog != null && dialog.isShowing()) {
////                    dialog.dismiss();
////                }
//                // isNotNet = true;
//                //  Toast.makeText(context, "网络连接正常", Toast.LENGTH_SHORT).show();
//            } else {
//                //   isNotNet = false;
////                showDialog();
//                Toast.makeText(context, "暂无网络",
//                        Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.dettach();
        } else {
        }

        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.attach((V) this);
        } else {
        }

    }
}
