package com.brasizza.marcus.gertec;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;

import com.topwise.cloudpos.aidl.camera.AidlCameraScanCode;
import com.topwise.cloudpos.aidl.camera.AidlDecodeCallBack;
import com.topwise.cloudpos.aidl.camera.DecodeMode;
import com.topwise.cloudpos.aidl.camera.DecodeParameter;
import com.topwise.cloudpos.aidl.printer.AidlPrinterListener;

public class GertecCamera extends Application {

    Handler handler = new Handler(Looper.getMainLooper());
    String result = "";
    private final Context context;
    private final AidlCameraScanCode camera;
    private DecodeParameter decodeParameter;
    private String decoded = "";
    private final AidlPrinterListener mListen = new AidlPrinterListener.Stub() {
        @Override
        public void onError(int i) throws RemoteException {

            Log.d("debug", "onError: " + i);
        }

        @Override
        public void onPrintFinish() throws RemoteException {
            Log.d("debug", "Printer Finished");
        }
    };


    public GertecCamera(Context context) {
        this.context = context;
        camera = DeviceServiceManager.getInstance().getCameraManager(context);

    }


    public void decode() {
        
        this.decoded = "";
        DecodeParameter decodeParameter = new DecodeParameter();
        decodeParameter.setDecodeMode(DecodeMode.MODE_SINGLE_SCAN_CODE).setDecodeIntervalTime(2000).setFlashLightTimeout(2000);
        try {
            camera.startDecode(decodeParameter, new AidlDecodeCallBack.Stub() {
            @Override
            public void onResult(String s) throws RemoteException {
                Log.d("debug", "onResult: " + s);
                setDecoded(s);
            }
            @Override
            public void onError(int i) throws RemoteException {
                Log.d("debug", "onError: " + i);

            }
        });
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.d("debug", "onError: " + e.getMessage());

            try {
                camera.stopDecode();
            } catch (RemoteException f) {
              f.printStackTrace();
                Log.d("debug", "onError: " + f.getMessage());

            }
        }
    }

    public void stopScan(){
        try {
            camera.stopDecode();
            camera.stopScan();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }



    public String getDecoded() {
        return decoded;
    }

    public void setDecoded(String decoded) {
        this.decoded = decoded;
    }
}