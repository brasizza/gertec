// ignore_for_file: public_member_api_docs, sort_constructors_first
import 'package:gertec/core/helpers/constants.dart';

class GertecText {
  final String text;
  FontSize fontSize;
  bool? bold;
  bool? underline;
  bool? wordwrap;
  int? lineHeight;
  int? letterSpacing;
  int? marginLeft;
  PrintAlign algin;
  GertecText({
    required this.text,
    this.fontSize = FontSize.NORMAL,
    this.algin = PrintAlign.LEFT,
    this.bold,
    this.underline,
    this.wordwrap,
    this.lineHeight,
    this.letterSpacing,
    this.marginLeft,
  });

  Map<String, dynamic> toMap() {
    return <String, dynamic>{
      'text': text,
      'fontSize': fontSize.value,
      'bold': bold,
      'underline': underline,
      'wordwrap': wordwrap,
      'lineHeight': lineHeight,
      'letterSpacing': letterSpacing,
      'marginLeft': marginLeft,
      'align': algin.value,
    };
  }
}
