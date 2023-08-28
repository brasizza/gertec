import 'dart:typed_data';

import 'package:gertec/core/helpers/constants.dart';
import 'package:gertec/core/helpers/models/gertec_text.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'gertec_printer_method_channel.dart';

abstract class GertecPrinterPlatform extends PlatformInterface {
  /// Constructs a GertecPrinterPlatform.
  GertecPrinterPlatform() : super(token: _token);

  static final Object _token = Object();

  static GertecPrinterPlatform _instance = MethodChannelGertecPrinter();

  /// The default instance of [GertecPrinterPlatform] to use.
  ///
  /// Defaults to [MethodChannelGertecPrinter].
  static GertecPrinterPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [GertecPrinterPlatform] when
  /// they register themselves.
  static set instance(GertecPrinterPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future<String?> printText(GertecText textObject) {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future<void> wrapLine(int lines) {
    throw UnimplementedError('wrapLine() has not been implemented.');
  }

  Future<String?> cutPaper(CutPaperType type) {
    throw UnimplementedError('cutPaper() has not been implemented.');
  }

  Future<String?> printRaw(Uint8List data) {
    throw UnimplementedError('printRaw() has not been implemented.');
  }

  Future<String?> printBarCode(
      {required int width,
      required int height,
      required String text,
      required int align}) {
    throw UnimplementedError('printBarCode() has not been implemented.');
  }

  Future<String?> printQrcode(
      {required int width, required int height, required String text}) {
    throw UnimplementedError('printQrcode() has not been implemented.');
  }

  Future<String?> printImage(Uint8List image, int align) {
    throw UnimplementedError('printImage() has not been implemented.');
  }

  Future<String?> printerState() async {
    throw UnimplementedError('printerState() has not been implemented.');
  }
}
