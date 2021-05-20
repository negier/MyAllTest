package com.xuebinduan.binder.server;

import android.os.Binder;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MusicPlayerService extends Binder {
    @Override
    protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
        switch (code){
            case 1000:
                //enforceInterface()是为了某种校验，它与客户端的writeInterfaceToken()对应
                data.enforceInterface("MusicPlayerService");
                String filePath = data.readString();
                start(filePath);
                break;
        }
        return super.onTransact(code, data, reply, flags);
    }
    public void start(String filePath){

    }
    public void stop(){

    }
}
