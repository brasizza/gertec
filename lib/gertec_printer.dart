import 'dart:typed_data';

import 'package:gertec_printer/core/helpers/constants.dart';
import 'package:gertec_printer/core/helpers/models/gertec_text.dart';

import 'gertec_printer_platform_interface.dart';

class GertecPrinter {
  Future<String?> getPlatformVersion() {
    return GertecPrinterPlatform.instance.getPlatformVersion();
  }

  Future<String?> printText(GertecText textObject) {
    return GertecPrinterPlatform.instance.printText(textObject);
  }

  Future<int> cutPaper(CutPaperType type) async {
    return await GertecPrinterPlatform.instance.cutPaper(type);
  }

  Future<int> printRaw(Uint8List data) async {
    return await GertecPrinterPlatform.instance.printRaw(data);
  }

  Future<int> printBarCode({int width = 200, int height = 60, required String text, PrintAlign align = PrintAlign.LEFT}) async {
    return await GertecPrinterPlatform.instance.printBarCode(width: width, height: height, text: text, align: align.value);
  }

  Future<int> printQrcode({int width = 100, int height = 100, required String text}) async {
    return await GertecPrinterPlatform.instance.printQrcode(width: width, height: height, text: text);
  }

  Future printImage({required Uint8List image, PrintAlign align = PrintAlign.LEFT}) async {
    return await GertecPrinterPlatform.instance.printImage(image, align.value);
  }

  Future<void> wrap({
    int len = 1,
  }) async {
    await GertecPrinterPlatform.instance.wrapLine(len);
  }

  Future<void> line({
    String ch = '-',
    int len = 31,
  }) async {
    await GertecPrinterPlatform.instance.printText(GertecText(text: List.filled(len, ch[0]).join()));
  }
}
