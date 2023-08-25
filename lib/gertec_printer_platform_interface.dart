import 'package:gertec_printer/core/gertec_text.dart';
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
}
