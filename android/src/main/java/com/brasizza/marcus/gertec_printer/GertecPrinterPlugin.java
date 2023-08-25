package com.brasizza.marcus.gertec_printer;

import android.content.Context;
import android.os.RemoteException;

import androidx.annotation.NonNull;

import com.topwise.cloudpos.aidl.printer.AidlPrinterListener;
import com.topwise.cloudpos.aidl.printer.PrintItemObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/**
 * GertecPrinterPlugin
 */
public class GertecPrinterPlugin implements FlutterPlugin, MethodCallHandler {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private MethodChannel channel;
    private Context context;
    private AidlPrinterListener listener;
//  private AidlPrinter printer;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
//    printer = DeviceServiceManager.getInstance().getPrintManager();
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "gertec_printer");
        channel.setMethodCallHandler(this);
        context = flutterPluginBinding.getApplicationContext();
        PriceScanApplication.setContext(context);

    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {


        switch (call.method) {
            default:
                result.notImplemented();
            case "getPlatformVersion":
                result.success("Android " + android.os.Build.VERSION.RELEASE);
                break;

            case "bindPrinter":
                try {
                    DeviceServiceManager.getInstance().getPrintManager().getPrinterState();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                result.success("Android " + android.os.Build.VERSION.RELEASE);
                break;

            case "printText":

                HashMap map  = call.argument("args");
                String text = (String) map.get("text");
                List<PrintItemObj> printItems = new ArrayList<>();
                printItems.add(new PrintItemObj(text, 20));

                try {
                    DeviceServiceManager.getInstance().getPrintManager().printText(printItems, new AidlPrinterListener.Stub() {
                        @Override
                        public void onError(int i) throws RemoteException {

                        }

                        @Override
                        public void onPrintFinish() throws RemoteException {

                        }
                    });
                    ReturnObject returnObject = new ReturnObject("OK", "", true);
                    result.success(returnObject.toJson());
                } catch (RemoteException e) {
                    ReturnObject returnObject = new ReturnObject(e.getMessage(), "", false);
                    result.success(returnObject.toJson());
                }
                break;
        }
    }


    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }
}
