import 'dart:convert';

// ignore_for_file: public_member_api_docs, sort_constructors_first
class GertecResponse {
  final String message;

  final bool success;
  final dynamic content;
  GertecResponse({
    required this.message,
    required this.success,
    required this.content,
  });

  GertecResponse copyWith({
    String? message,
    bool? success,
    dynamic content,
  }) {
    return GertecResponse(
      message: message ?? this.message,
      success: success ?? this.success,
      content: content ?? this.content,
    );
  }

  Map<String, dynamic> toMap() {
    return <String, dynamic>{
      'message': message,
      'success': success,
      'content': content,
    };
  }

  factory GertecResponse.fromMap(Map<String, dynamic> map) {
    return GertecResponse(
      message: map['message'] as String,
      success: map['success'] as bool,
      content: map['content'] as dynamic,
    );
  }

  String toJson() => json.encode(toMap());

  factory GertecResponse.fromJson(String source) =>
      GertecResponse.fromMap(json.decode(source) as Map<String, dynamic>);

  @override
  String toString() =>
      'GertecResponse(message: $message, success: $success, content: $content)';

  @override
  bool operator ==(covariant GertecResponse other) {
    if (identical(this, other)) return true;

    return other.message == message &&
        other.success == success &&
        other.content == content;
  }

  @override
  int get hashCode => message.hashCode ^ success.hashCode ^ content.hashCode;
}
