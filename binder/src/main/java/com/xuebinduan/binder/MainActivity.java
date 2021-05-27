package com.xuebinduan.binder;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * RPC : Across Processes Communication
 * Binder的作用：提供一个全局服务，所谓的全局就是系统中的所有应用程序都可以访问。
 *
 * Binder是可能意外死亡的，这往往是服务端的进程意外停止了。为此有两种解决方法：
 * 1 在ServiceConnection里面进行重连；
 * 2 给Binder设置DeathRecipient监听;
 * 3 还有一个方法就是通过Binder的方法isBinderAlive也可以判断Binder是否死亡。
 *
 * AIDL:
 * aidl文件转化为一个java类，在类文件中，同时重载了transact和onTransact()方法，统一了存入包裹和读取包裹参数，从而使设计者可以把注意力放到服务代码本身上。
 */
public class MainActivity extends AppCompatActivity {

    private IBookManager mRemoteBookManager;

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.e("TAG","DeathRecipient 当前线程："+Thread.currentThread());
            if (mRemoteBookManager == null){
                return;
            }
            mRemoteBookManager.asBinder().unlinkToDeath(mDeathRecipient,0);
            mRemoteBookManager = null;
            //todo 这里重新绑定远程Service
            Intent intent = new Intent(MainActivity.this,BookManagerService.class);
            bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
        }
    };

    //客户端binder
    private IOnNewBookArrivedListener mOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e("TAG","receive new book:"+newBook);
                }
            });
        }
    };

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //todo 这里ServiceConnection是运行在客户端UI线程中的
            Log.e("TAG","ServiceConnection 当前线程："+Thread.currentThread());

            mRemoteBookManager = IBookManager.Stub.asInterface(service);
            try {
                service.linkToDeath(mDeathRecipient,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            //todo 所以为了避免调用到服务端耗时方法导致ANR所以开个线程
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        List<Book> list = mRemoteBookManager.getBookList();
                        Log.e("TAG","query book list,list type:"+list.getClass().getCanonicalName());
                        Log.e("TAG","query book list:"+list.toString());
                        Book newBook = new Book(3,"Android 进阶");
                        mRemoteBookManager.addBook(newBook);
                        Log.e("TAG","add book:"+newBook);
                        List<Book> newList = mRemoteBookManager.getBookList();
                        Log.e("TAG","query book list:"+newList.toString());
                        mRemoteBookManager.registerListener(mOnNewBookArrivedListener);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this,BookManagerService.class);
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        if (mRemoteBookManager!=null && mRemoteBookManager.asBinder().isBinderAlive()){
            try{
                Log.e("TAG","unregister listener:"+mOnNewBookArrivedListener);
                mRemoteBookManager.unregisterListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mConnection);
        super.onDestroy();
    }
}
