package com.example.zjl.videoplayerdemo.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.example.zjl.videoplayerdemo.IRemoteService;
import com.example.zjl.videoplayerdemo.bean.Entity;
import com.example.zjl.cluttertestdemo.R;
import com.jaydenxiao.common.base.BaseActivity;

public class MainActivity extends BaseActivity {

    protected static final String TAG = "MainActivity";
    private IRemoteService s;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //连接后拿到 Binder，转换成 AIDL，获得 aidl定义的接口持有类
            s = IRemoteService.Stub.asInterface(service);
            Log.e(TAG, "onServiceConnected client");
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.act_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

    }

    public void bindBtn(View v) {
        Intent mIntent = new Intent("android.zjl.MyService");
        bindService(mIntent, conn, BIND_AUTO_CREATE);
    }

    public void greetBtn(View v) {
        try {
            //addEntity
            Entity entity = new Entity();
            entity.setName("张家林客户端");
            s.addEntity(entity);
            Log.e(TAG, s.getEntity().get(0).getName());
            //doSomething
            s.doSomeThing(1,"随便写一点");
            Log.e(TAG, "doSomeThing OK");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void unbindBtn(View v) {
        unbindService(conn);

    }
}
