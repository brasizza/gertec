// ignore_for_file: constant_identifier_names

enum BarCodeType {
  BARCODE_TYPE_UPCA(65),
  BARCODE_TYPE_UPCE(66),
  BARCODE_TYPE_JAN13(67),
  BARCODE_TYPE_JAN8(68),
  BARCODE_TYPE_CODE39(69),
  BARCODE_TYPE_ITF(70),
  BARCODE_TYPE_CODEBAR(71),
  BARCODE_TYPE_CODE93(72),
  BARCODE_TYPE_CODE128(73);

  const BarCodeType(this.value);
  final int value;
}

enum PrintAlign {
  LEFT(0),
  CENTER(1),
  RIGHT(2);

  final int value;
  const PrintAlign(this.value);
}

enum CutPaperType {
  HALF(1),
  FULL(0);

  final int value;
  const CutPaperType(this.value);
}

enum PrinterState {
  PRINTER_STATE_NORMAL(0),
  PRINTER_STATE_NOPAPER(1),
  PRINTER_STATE_HIGHTEMP(2),
  PRINTER_STATE_UNKNOWN(3),
  PRINTER_STATE_NOT_OPEN(4),
  PRINTER_STATE_DEV_ERROR(5),
  PRINTER_STATE_LOWVOL_ERROR(6),
  PRINTER_STATE_BUSY(7),
  PRINTER_STATE_CUT(8),
  PRINTER_STATE_OUT(9),
  PRINTER_PAPER_RUN_OUT(10),
  PRINTER_COVER_OPEN(11),
  PRINT_ERROR_PARAMETER(12);

  const PrinterState(this.value);
  final int value;
}

enum FontSize {
  SMALL(4),
  NORMAL(8),
  LARGE(16),
  XLARGE(24);

  final int value;
  const FontSize(this.value);
}

enum DecodeMode {
  MODE_DEFAULT(-1),
  MODE_SINGLE_SCAN_CODE(0),
  MODE_CONTINUE_SCAN_CODE(1);

  final int value;
  const DecodeMode(this.value);
}
