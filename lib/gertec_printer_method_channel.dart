import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';
import 'package:gertec_printer/core/gertec_text.dart';

import 'gertec_printer_platform_interface.dart';

/// An implementation of [GertecPrinterPlatform] that uses method channels.
class MethodChannelGertecPrinter extends GertecPrinterPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('gertec_printer');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

  @override
  Future<String?> printText(GertecText textObject) async {
    final version = await methodChannel.invokeMethod<String>('printText', {'args': textObject.toMap()});
    return version;
  }
}
