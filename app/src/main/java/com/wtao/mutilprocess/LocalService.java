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
public class LocalService extends Service {

    LocalTool.Stub ibinder ;
    ServiceConnection conn ;
    class MyBinder extends LocalTool.Stub{
        @Override
        public void show() throws RemoteException {

        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        if(ibinder==null){
            ibinder = new MyBinder();
        }
        conn = new MyServiceConnection();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return ibinder;
    }


    class MyServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LocalService.this.bindService(new Intent(LocalService.this,RemoteService.class),conn,BIND_IMPORTANT|BIND_AUTO_CREATE);
            LocalService.this.startService(new Intent(LocalService.this,RemoteService.class));
        }
    };
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        bindService(new Intent(this,RemoteService.class),conn,BIND_IMPORTANT|BIND_AUTO_CREATE);
        startService(new Intent(LocalService.this,RemoteService.class));
        return START_STICKY;
    }
}
