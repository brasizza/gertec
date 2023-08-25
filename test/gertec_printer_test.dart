import 'package:flutter_test/flutter_test.dart';
import 'package:gertec_printer/gertec_printer.dart';
import 'package:gertec_printer/gertec_printer_platform_interface.dart';
import 'package:gertec_printer/gertec_printer_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockGertecPrinterPlatform
    with MockPlatformInterfaceMixin
    implements GertecPrinterPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final GertecPrinterPlatform initialPlatform = GertecPrinterPlatform.instance;

  test('$MethodChannelGertecPrinter is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelGertecPrinter>());
  });

  test('getPlatformVersion', () async {
    GertecPrinter gertecPrinterPlugin = GertecPrinter();
    MockGertecPrinterPlatform fakePlatform = MockGertecPrinterPlatform();
    GertecPrinterPlatform.instance = fakePlatform;

    expect(await gertecPrinterPlugin.getPlatformVersion(), '42');
  });
}
