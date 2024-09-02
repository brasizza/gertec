package com.brasizza.marcus.gertec;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.ByteArrayInputStream;
import java.util.HashMap;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    private GertecPrinter printer;
    private GertecCamera camera;


    @Override

    @SuppressWarnings("unchecked")
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "gertec_printer");
        channel.setMethodCallHandler(this);
        context = flutterPluginBinding.getApplicationContext();
        printer = new GertecPrinter(context);
        camera = new GertecCamera(context);

    }

    @Override
    @SuppressWarnings("unchecked")
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        switch (call.method) {
            default:
                result.notImplemented();
            case "getPlatformVersion":
                result.success(new ReturnObject("OK", "Android " + android.os.Build.VERSION.RELEASE, true).toJson());
                break;

            case "WRAP_LINE":
                int times = call.argument("lines");
                try {
                    printer.wrapLine(times);
                    result.success(new ReturnObject("OK", 1, true).toJson());
                } catch (RemoteException e) {
                    e.printStackTrace();
                    result.success(new ReturnObject(e.getMessage(), "", false).toJson());
                }
                break;


            case "PRINT_QRCODE":
                int widthQR = call.argument("width");
                int heightQR = call.argument("height");
                String textQrcode = call.argument("text");
                try {
                    printer.printQRcode(textQrcode, widthQR, heightQR);
                    result.success(new ReturnObject("OK", 1, true).toJson());
                } catch (RemoteException e) {
                    result.success(new ReturnObject(e.getMessage(), "", false).toJson());
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    Log.d("FLUTTER", "NullPointerException: " + e.getMessage());
                }

                break;


            case "PRINT_IMAGE":
                byte[] dataImage = (byte[]) call.argument("data");
                int alignImage = call.argument("align");
                Bitmap image = byteArrayToBitmap(dataImage);
                try {
                    printer.printImage(image, alignImage);
                    result.success(new ReturnObject("OK", 1, true).toJson());
                } catch (RemoteException e) {
                    e.printStackTrace();
                    result.success(new ReturnObject(e.getMessage(), "", false).toJson());
                } catch (NullPointerException e) {
                    Log.d("FLUTTER", "NullPointerException: " + e.getMessage());
                }

                break;


            case "PRINT_BARCODE":
                int widthBC = call.argument("width");
                int heightBC = call.argument("height");
                int alignBC = call.argument("align");
                String textBC = call.argument("text");
                try {
                    printer.printBarCode(textBC, widthBC, heightBC, alignBC);
                    result.success(new ReturnObject("OK", 1, true).toJson());
                } catch (RemoteException e) {
                    result.success(new ReturnObject(e.getMessage(), "", false).toJson());
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    Log.d("FLUTTER", "NullPointerException: " + e.getMessage());
                }

                break;

            case "PRINT_RAW":
                byte[] data = (byte[]) call.argument("data");
                Log.d("PRINT_RAW", String.valueOf(data));
                try {
                    int resultBuf = printer.printRaw(data);
                    result.success(new ReturnObject("OK", resultBuf, true).toJson());
                } catch (RemoteException e) {
                    result.success(new ReturnObject(e.getMessage(), "", false).toJson());
                    e.printStackTrace();
                }

                break;

            case "CUT_PAPER":
                int cut = call.argument("cut");
                try {

                    int resultCut = printer.cutPaper(cut);
                    result.success(new ReturnObject("OK", resultCut, true).toJson());
                } catch (RemoteException e) {
                    e.printStackTrace();
                    result.success(new ReturnObject(e.getMessage(), "", false).toJson());
                }
                break;

            case "PRINTER_STATE":
                int state = 0;
                try {
                    state = printer.getPrinterState();
                    result.success(new ReturnObject("OK", state, true).toJson());
                } catch (RemoteException e) {
                    result.success(new ReturnObject(e.getMessage(), "", false).toJson());
                    e.printStackTrace();
                }

                break;

                case "PRINT_TEXT":
                HashMap<String, Object> map = call.argument("args");
                try {
                    printer.printText(map);
                    result.success(new ReturnObject("OK", "", true).toJson());
                } catch (RemoteException e) {
                    result.success(new ReturnObject(e.getMessage(), "", true).toJson());
                }
                break;

                case "READ_CAMERA":
                camera.decode();
                ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
                scheduler.schedule(new Runnable() {
                    @Override
                    public void run() {
                        final String resultCamera = camera.getDecoded();
                        if (resultCamera == null || resultCamera.isEmpty()) {
                            result.success(new ReturnObject("", "", false).toJson());
                        } else {
                            result.success(new ReturnObject("OK", resultCamera, true).toJson());
                        }
                        camera.stopScan();
                    }
                }, 2, TimeUnit.SECONDS);
                break;
            

        }
    }


    @Override
    @SuppressWarnings("unchecked")
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }

    @SuppressWarnings("unchecked")
    public Bitmap byteArrayToBitmap(byte[] byteArray) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
        return BitmapFactory.decodeStream(inputStream);
    }
}
