import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:gertec/core/helpers/constants.dart';
import 'package:gertec/core/helpers/models/gertec_text.dart';
import 'package:gertec/gertec_printer.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();

  await SystemChrome.setPreferredOrientations([DeviceOrientation.portraitUp]);
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  MyAppState createState() => MyAppState();
}

class MyAppState extends State<MyApp> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        title: 'Sunmi Printer',
        theme: ThemeData(
          primaryColor: Colors.black,
        ),
        debugShowCheckedModeBanner: false,
        home: const Home());
  }
}

class Home extends StatefulWidget {
  const Home({Key? key}) : super(key: key);

  @override
  HomeState createState() => HomeState();
}

class HomeState extends State<Home> {
  PrinterState printBinded = PrinterState.PRINTER_STATE_NORMAL;
  int paperSize = 0;
  String serialNumber = "";
  String printerVersion = "";
  final _gertecPrinterPlugin = GertecPrinter();
  String textScan = 'Text scanned';

  Future<Uint8List> readFileBytes(String path) async {
    ByteData fileData = await rootBundle.load(path);
    Uint8List fileUnit8List = fileData.buffer
        .asUint8List(fileData.offsetInBytes, fileData.lengthInBytes);
    return fileUnit8List;
  }

  Future<Uint8List> getImageFromAsset(String iconPath) async {
    return await readFileBytes(iconPath);
  }

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: const Text('GERTEC printer Example'),
        ),
        body: SingleChildScrollView(
          child: Column(
            children: [
              Padding(
                padding: const EdgeInsets.only(
                  top: 10,
                ),
                child: Text("Print binded: $printBinded"),
              ),
              ElevatedButton(
                  onPressed: () async {
                    final state = await _gertecPrinterPlugin.printerState();

                    setState(() {
                      try {
                        printBinded = state.content;
                      } catch (_) {
                        printBinded = PrinterState.PRINTER_STATE_NORMAL;
                      }
                    });
                  },
                  child: const Text('check state')),
              ElevatedButton(
                  onPressed: () async {
                    await _gertecPrinterPlugin.getPlatformVersion();
                  },
                  child: const Text('check state')),
              const Divider(),
              Padding(
                padding: const EdgeInsets.symmetric(horizontal: 20),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceAround,
                  children: [
                    ElevatedButton(
                        onPressed: () async {
                          await _gertecPrinterPlugin.printQrcode(
                              text: 'MARCUS BRASIZZA', height: 500, width: 500);
                        },
                        child: const Text('Print qrCode')),
                    ElevatedButton(
                        onPressed: () async {
                          await _gertecPrinterPlugin.printBarCode(
                              text: 'MARCUS BRASIZZA', width: 300);
                        },
                        child: const Text('Print barCode')),
                    ElevatedButton(
                        onPressed: () async {
                          await _gertecPrinterPlugin.line();
                        },
                        child: const Text('Print line')),
                    ElevatedButton(
                        onPressed: () async {
                          await _gertecPrinterPlugin.wrap(len: 10);
                        },
                        child: const Text('Wrap line')),
                  ],
                ),
              ),
              Padding(
                padding: const EdgeInsets.symmetric(horizontal: 20),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceAround,
                  children: [
                    ElevatedButton(
                        onPressed: () async {
                          await _gertecPrinterPlugin.printText(
                              GertecText(text: 'EU AMO FLUTTER', bold: true));
                        },
                        child: const Text('Bold Text')),
                    ElevatedButton(
                        onPressed: () async {
                          await _gertecPrinterPlugin.printText(GertecText(
                              text: 'EU AMO FLUTTER',
                              fontSize: FontSize.SMALL));
                        },
                        child: const Text('Small font')),
                    ElevatedButton(
                        onPressed: () async {
                          await _gertecPrinterPlugin.printText(GertecText(
                              text: 'EU AMO FLUTTER',
                              fontSize: FontSize.NORMAL));
                        },
                        child: const Text('Normal font')),
                  ],
                ),
              ),
              Padding(
                padding: const EdgeInsets.symmetric(horizontal: 20),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceAround,
                  children: [
                    ElevatedButton(
                        onPressed: () async {
                          await _gertecPrinterPlugin.printText(GertecText(
                              text: 'EU AMO FLUTTER',
                              fontSize: FontSize.LARGE));
                        },
                        child: const Text('Large font')),
                    ElevatedButton(
                        onPressed: () async {
                          await _gertecPrinterPlugin.printText(GertecText(
                              text: 'EU AMO FLUTTER',
                              fontSize: FontSize.XLARGE));
                        },
                        child: const Text('Very large font')),
                  ],
                ),
              ),
              Padding(
                padding: const EdgeInsets.symmetric(horizontal: 20),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceAround,
                  children: [
                    ElevatedButton(
                        onPressed: () async {
                          await _gertecPrinterPlugin.printText(GertecText(
                              text: 'EU AMO FLUTTER', algin: PrintAlign.LEFT));
                        },
                        child: const Text('Align right')),
                    ElevatedButton(
                        onPressed: () async {
                          await _gertecPrinterPlugin.printText(GertecText(
                              text: 'EU AMO FLUTTER', algin: PrintAlign.RIGHT));
                        },
                        child: const Text('Align left')),
                    ElevatedButton(
                      onPressed: () async {
                        await _gertecPrinterPlugin.printText(GertecText(
                            text: 'EU AMO FLUTTER', algin: PrintAlign.CENTER));
                      },
                      child: const Text('Align center'),
                    ),
                  ],
                ),
              ),
              Padding(
                padding: const EdgeInsets.symmetric(horizontal: 20),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceAround,
                  children: [
                    GestureDetector(
                      onTap: () async {
                        Uint8List byte =
                            await _getImageFromAsset('assets/images/dash.jpeg');

                        await _gertecPrinterPlugin.printImage(
                            image: byte, align: PrintAlign.LEFT);
                      },
                      child: Column(
                        children: [
                          Image.asset(
                            'assets/images/dash.jpeg',
                            width: 100,
                          ),
                          const Text('Print this image from asset!')
                        ],
                      ),
                    ),
                    GestureDetector(
                      onTap: () async {
                        String url =
                            'https://avatars.githubusercontent.com/u/14101776?s=100';
                        // convert image to Uint8List format
                        Uint8List byte =
                            (await NetworkAssetBundle(Uri.parse(url)).load(url))
                                .buffer
                                .asUint8List();
                        await _gertecPrinterPlugin.printImage(
                            image: byte, align: PrintAlign.LEFT);
                      },
                      child: Column(
                        children: [
                          Image.network(
                              'https://avatars.githubusercontent.com/u/14101776?s=100'),
                          const Text('Print this image from WEB!')
                        ],
                      ),
                    ),
                  ],
                ),
              ),
              Padding(
                padding: const EdgeInsets.symmetric(horizontal: 20),
                child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceAround,
                    children: [
                      ElevatedButton(
                          onPressed: () async {
                            await _gertecPrinterPlugin
                                .cutPaper(CutPaperType.FULL);
                          },
                          child: const Text('CUT PAPER')),
                    ]),
              ),
              const Divider(),
              const Text(
                "Scanner",
                style: TextStyle(fontWeight: FontWeight.bold, fontSize: 30),
              ),
              Text(
                "$textScan",
                style: TextStyle(fontSize: 20),
              ),
              Padding(
                padding: const EdgeInsets.symmetric(horizontal: 20),
                child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceAround,
                    children: [
                      ElevatedButton(
                          onPressed: () async {
                            final cameraData =
                                await _gertecPrinterPlugin.readCamera();
                            setState(() {
                              if (cameraData.success == true) {
                                textScan = (cameraData.content as String?) ??
                                    'Fail to read, try again';
                              } else {
                                textScan = 'Fail to read, try again';
                              }
                            });
                          },
                          child: const Text('Read barcode/qrcode')),
                    ]),
              ),
            ],
          ),
        ));
  }
}

Future<Uint8List> readFileBytes(String path) async {
  ByteData fileData = await rootBundle.load(path);
  Uint8List fileUnit8List = fileData.buffer
      .asUint8List(fileData.offsetInBytes, fileData.lengthInBytes);
  return fileUnit8List;
}

Future<Uint8List> _getImageFromAsset(String iconPath) async {
  return await readFileBytes(iconPath);
}
