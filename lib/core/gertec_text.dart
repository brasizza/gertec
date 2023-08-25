// ignore_for_file: public_member_api_docs, sort_constructors_first
class GertecText {
  final String text;
  int fontSize;
  bool bold;
  bool underline;
  bool wordwrap;
  int lineHeight;
  int letterSpacing;
  int marginLeft;
  GertecText({
    required this.text,
    this.fontSize = 10,
    this.bold = false,
    this.underline = false,
    this.wordwrap = false,
    this.lineHeight = 0,
    this.letterSpacing = 0,
    this.marginLeft = 0,
  });

  Map<String, dynamic> toMap() {
    return <String, dynamic>{
      'text': text,
      'fontSize': fontSize,
      'bold': bold,
      'underline': underline,
      'wordwrap': wordwrap,
      'lineHeight': lineHeight,
      'letterSpacing': letterSpacing,
      'marginLeft': marginLeft,
    };
  }
}
