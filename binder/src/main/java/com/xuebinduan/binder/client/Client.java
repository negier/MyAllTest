package com.xuebinduan.binder.client;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

public class Client {

    public void doSomething() throws RemoteException {
        IBinder mRemote = null;
        String filePath = "/sdcard/music/heal_the_world.mp3";
        int code = 1000;
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        data.writeInterfaceToken("MusicPlayerService");
        data.writeString(filePath);
        mRemote.transact(code,data,reply,0);
        IBinder binder = reply.readStrongBinder();
        reply.recycle();
        data.recycle();
    }

}
