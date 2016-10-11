package com.wtao.mutilprocess;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2016/7/27.
 */
public class RemoteService extends Service {
    LocalTool.Stub ibinder = new LocalTool.Stub() {
        @Override
        public void show() throws RemoteException {

        }
    };

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //startActivity(new Intent(RemoteService.this,LocalService.class));//这里写错了，调用的方法是startService才对而不是startActivity
            RemoteService.this.startService(new Intent(RemoteService.this,LocalService.class));
            RemoteService.this.bindService(new Intent(RemoteService.this, LocalService.class),conn,BIND_IMPORTANT);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        bindService(new Intent(this, LocalService.class), conn, BIND_IMPORTANT);

        return START_STICKY;//能自动复活

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return ibinder;
    }
}
