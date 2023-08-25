import 'package:gertec_printer/core/gertec_text.dart';

import 'gertec_printer_platform_interface.dart';

class GertecPrinter {
  Future<String?> getPlatformVersion() {
    return GertecPrinterPlatform.instance.getPlatformVersion();
  }

  Future<String?> printText(GertecText textObject) {
    return GertecPrinterPlatform.instance.printText(textObject);
  }
}
