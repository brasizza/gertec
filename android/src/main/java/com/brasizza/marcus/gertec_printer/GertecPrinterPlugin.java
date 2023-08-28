package com.brasizza.marcus.gertec_printer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;

import com.topwise.cloudpos.aidl.printer.AidlPrinterListener;
import com.topwise.cloudpos.aidl.printer.PrintCuttingMode;
import com.topwise.cloudpos.aidl.printer.PrintItemObj;
import com.topwise.cloudpos.data.PrinterConstant;

import java.io.ByteArrayInputStream;
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


    AidlPrinterListener mListen = new AidlPrinterListener.Stub() { @Override
    public void onError(int i) throws RemoteException {

        Log.d("FLUTTER", "onError: " + i);
    }
        @Override
        public void onPrintFinish() throws RemoteException {

        } };


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
                ReturnObject<String> returnObject = new ReturnObject("OK", 1, true);
                result.success(new ReturnObject("OK", "Android " + android.os.Build.VERSION.RELEASE, true) .toJson());
                break;

            case "WRAP_LINE":
                int times = call.argument("lines");
                try {
                    DeviceServiceManager.getInstance().getPrintManager().goPaper(times);

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

                    DeviceServiceManager.getInstance().getPrintManager().addRuiQRCode(textQrcode, widthQR,heightQR );
                    DeviceServiceManager.getInstance().getPrintManager().printRuiQueue(mListen);

                    result.success(new ReturnObject("OK", 1, true).toJson());
                } catch (RemoteException e) {
                    result.success(new ReturnObject(e.getMessage(), "", false).toJson());
                    e.printStackTrace();
                }catch (NullPointerException e) {
                    Log.d("FLUTTER", "NullPointerException: " + e.getMessage());
                }

                break;



            case "PRINT_IMAGE":
                byte[]  dataImage = (byte[]) call.argument("data");
                int alignImage = call.argument("align");
                Bitmap image = byteArrayToBitmap(dataImage);
                try {
                    DeviceServiceManager.getInstance().getPrintManager().addRuiImage(image,alignImage);
                    DeviceServiceManager.getInstance().getPrintManager().printRuiQueue(mListen);
                    result.success(new ReturnObject("OK", 1, true).toJson());
                } catch (RemoteException e) {
                    e.printStackTrace();
                    result.success(new ReturnObject(e.getMessage(), "", false).toJson());
                }catch (NullPointerException e) {
                    Log.d("FLUTTER", "NullPointerException: " + e.getMessage());
                }

                break;


            case "PRINT_BARCODE":
                int widthBC = call.argument("width");
                int heightBC = call.argument("height");
                int alignBC = call.argument("align");
                String textBC = call.argument("text");
                try {
                    DeviceServiceManager.getInstance().getPrintManager().addRuiBarCode(textBC, widthBC,heightBC, 1  );
                    DeviceServiceManager.getInstance().getPrintManager().printRuiQueue(mListen);
                    result.success(new ReturnObject("OK", 1, true).toJson());
                } catch (RemoteException e) {
                    result.success(new ReturnObject(e.getMessage(), "", false).toJson());
                    e.printStackTrace();
                }catch (NullPointerException e) {
                    Log.d("FLUTTER", "NullPointerException: " + e.getMessage());
                }

                break;

            case "PRINT_RAW":
                byte[]  data = (byte[]) call.argument("data");
                Log.d("PRINT_RAW", String.valueOf(data));
                try {
                  int resultBuf =  DeviceServiceManager.getInstance().getPrintManager().printBuf(data);
                    result.success(new ReturnObject("OK", resultBuf, true).toJson());

                } catch (RemoteException e) {
                    result.success(new ReturnObject(e.getMessage(), "", false).toJson());
                    e.printStackTrace();
                }

                break;

            case "CUT_PAPER":
                int cut = call.argument("cut");
                try {
                   int resultCut = DeviceServiceManager.getInstance().getPrintManager().cuttingPaper(cut == 1 ? PrintCuttingMode.CUTTING_MODE_HALT : PrintCuttingMode.CUTTING_MODE_FULL);


                    result.success(new ReturnObject("OK", resultCut, true).toJson());
                } catch (RemoteException e) {
                    e.printStackTrace();
                    result.success(new ReturnObject(e.getMessage(), "", false).toJson());
                }
                break;

            case "PRINTER_STATE":
                int state = 0;
                try {
                   state =  DeviceServiceManager.getInstance().getPrintManager().getPrinterState();
                    result.success(new ReturnObject("OK", state, true).toJson());
                } catch (RemoteException e) {
                    result.success(new ReturnObject(e.getMessage(), "", false).toJson());
                    e.printStackTrace();
                }

                break;

            case "PRINT_TEXT":
                HashMap map = call.argument("args");
                String text = (String) map.get("text");
                Log.d("printText - input", map.toString());
                int fontSize = map.get("fontSize") != null ? (int) map.get("fontSize") : PrinterConstant.FontSize.NORMAL;
                boolean bold = map.get("bold") != null ? (boolean) map.get("bold") : false;
                boolean underline = map.get("underline") != null ? (boolean) map.get("underline") : false;
                boolean wordwrap = map.get("wordwrap") != null ? (boolean) map.get("wordwrap") : false;
                int letterSpacing = map.get("letterSpacing") != null ? (int) map.get("letterSpacing") : 0;
                int marginLeft = map.get("marginLeft") != null ? (int) map.get("marginLeft") : 0;
                int lineHeight = map.get("lineHeight") != null ? (int) map.get("lineHeight") : 29;

                List<PrintItemObj> printItems = new ArrayList<>();
                PrintItemObj printerObject = new PrintItemObj(text);
                PrintItemObj.ALIGN align = PrintItemObj.ALIGN.LEFT;
                if (map.get("align") != null) {
                    switch ((int) map.get("align")) {
                        case 0:
                            align = PrintItemObj.ALIGN.LEFT;
                            break;
                        case 1:
                            align = PrintItemObj.ALIGN.CENTER;
                            break;
                        case 2:
                            align = PrintItemObj.ALIGN.RIGHT;

                            break;
                    }
                }
                printerObject.setLetterSpacing(letterSpacing);
                printerObject.setLineHeight(lineHeight);
                printerObject.setMarginLeft(marginLeft);
                printerObject.setBold(bold);
                printerObject.setUnderline(underline);
                printerObject.setFontSize(fontSize);
                printerObject.setAlign(align);
                printerObject.setWordWrap(wordwrap);
                printItems.add(printerObject);
                try {
                    DeviceServiceManager.getInstance().getPrintManager().printText(printItems,mListen);
                    result.success(new ReturnObject("OK", "", true).toJson());
                } catch (RemoteException e) {
                    result.success(new ReturnObject(e.getMessage(), "", true).toJson());
                }
                break;
        }
    }


    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }


    public Bitmap byteArrayToBitmap(byte[] byteArray) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
        return BitmapFactory.decodeStream(inputStream);
    }
}
