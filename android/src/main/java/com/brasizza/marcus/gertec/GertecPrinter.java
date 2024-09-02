package com.brasizza.marcus.gertec;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.RemoteException;
import android.util.Log;

import com.topwise.cloudpos.aidl.printer.AidlPrinter;
import com.topwise.cloudpos.aidl.printer.AidlPrinterListener;
import com.topwise.cloudpos.aidl.printer.PrintCuttingMode;
import com.topwise.cloudpos.aidl.printer.PrintItemObj;
import com.topwise.cloudpos.data.PrinterConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GertecPrinter extends Application {

    private Context context;
    private AidlPrinter printer;

    private AidlPrinterListener mListen = new AidlPrinterListener.Stub() { @Override
    public void onError(int i) throws RemoteException {

        Log.d("FLUTTER", "onError: " + i);
    }
        @Override
            public void onPrintFinish() throws RemoteException {

        } };

    public GertecPrinter(Context context) {
        this.context = context;
        printer = DeviceServiceManager.getInstance().getPrintManager(context);
    }
    public int wrapLine(int times) throws RemoteException {
         printer.goPaper(times);
         return 1;

    }
    public void printQRcode(String textQrcode, int widthQR, int heightQR) throws RemoteException {
        printer.addRuiQRCode(textQrcode, widthQR,heightQR );
        printer.printRuiQueue(mListen);
    }

    public void printImage(Bitmap image, int alignImage) throws RemoteException {
        printer.addRuiImage(image,alignImage);
        printer.printRuiQueue(mListen);
    }
    public void printBarCode(String textBC, int widthBC, int heightBC, int alignBC) throws RemoteException {

        printer.addRuiBarCode(textBC, widthBC,heightBC, alignBC );
        printer.printRuiQueue(mListen);

    }
    public int printRaw(byte[] data) throws RemoteException {
        return printer.printBuf(data);
    }
    public int cutPaper(int cut) throws RemoteException {

       return   printer.cuttingPaper(cut == 1 ? PrintCuttingMode.CUTTING_MODE_HALT : PrintCuttingMode.CUTTING_MODE_FULL);

    }
    public int getPrinterState() throws RemoteException {

       return printer.getPrinterState();

    }
    public void printText(HashMap map) throws RemoteException {
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

        printer.printText(printItems,mListen);
    }
}
