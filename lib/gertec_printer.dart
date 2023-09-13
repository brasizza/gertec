import 'dart:typed_data';

import 'package:gertec/core/helpers/constants.dart';
import 'package:gertec/core/helpers/models/gertec_response.dart';
import 'package:gertec/core/helpers/models/gertec_text.dart';

import 'gertec_printer_platform_interface.dart';

class GertecPrinter {
  Future<GertecResponse> getPlatformVersion() async {
    final plat = await GertecPrinterPlatform.instance.getPlatformVersion();
    return GertecResponse.fromJson(plat ?? '{}');
  }

  Future<GertecResponse> printText(GertecText textObject) async {
    return GertecResponse.fromJson(
        await GertecPrinterPlatform.instance.printText(textObject) ?? '{}');
  }

  Future<GertecResponse> cutPaper(CutPaperType type) async {
    return GertecResponse.fromJson(
        await GertecPrinterPlatform.instance.cutPaper(type) ?? '{}');
  }

  Future<GertecResponse> printRaw(Uint8List data) async {
    return GertecResponse.fromJson(
        await GertecPrinterPlatform.instance.printRaw(data) ?? '{}');
  }

  Future<GertecResponse> readCamera() async {
    return GertecResponse.fromJson(
        await GertecPrinterPlatform.instance.readCamera() ?? '{}');
  }

  Future<GertecResponse> printBarCode(
      {int width = 200,
      int height = 60,
      required String text,
      PrintAlign align = PrintAlign.LEFT}) async {
    return GertecResponse.fromJson(await GertecPrinterPlatform.instance
            .printBarCode(
                width: width, height: height, text: text, align: align.value) ??
        '{}');
  }

  Future<GertecResponse> printQrcode(
      {int width = 100, int height = 100, required String text}) async {
    return GertecResponse.fromJson(await GertecPrinterPlatform.instance
            .printQrcode(width: width, height: height, text: text) ??
        '{}');
  }

  Future<GertecResponse> printImage(
      {required Uint8List image, PrintAlign align = PrintAlign.LEFT}) async {
    return GertecResponse.fromJson(
        await GertecPrinterPlatform.instance.printImage(image, align.value) ??
            '{}');
  }

  Future<GertecResponse> printerState() async {
    final state = GertecResponse.fromJson(
        await GertecPrinterPlatform.instance.printerState() ?? '{}');
    return state.copyWith(
        content: PrinterState.values
            .where((pState) => pState.index == (state.content as int))
            .first);
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
    await GertecPrinterPlatform.instance
        .printText(GertecText(text: List.filled(len, ch[0]).join()));
  }
}
