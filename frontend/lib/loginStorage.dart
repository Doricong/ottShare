import 'dart:convert';

import 'package:frontend/models/user.dart';
import 'package:shared_preferences/shared_preferences.dart';

class LoginStorage {
  static SharedPreferences? _prefs;

  /// 초기화 메서드: SharedPreferences 인스턴스를 비동기적으로 불러옵니다.
  static Future<void> init() async {
    _prefs = await SharedPreferences.getInstance();
  }

  // /// 사용자 ID 가져오기: SharedPreferences에서 'userId' 키로 저장된 사용자 ID를 반환합니다.
  // static Future<int?> getUserId() async {
  //   return _prefs?.getInt('userId');
  // }
  //
  // /// 사용자 ID 저장하기: 주어진 사용자 ID를 'userId' 키에 저장합니다.
  // static Future<void> saveUserId(int userId) async {
  //   await _prefs?.setInt('userId', userId);
  // }

  static Future<void> saveUser(UserInfo userInfo) async {
    String jsonString = jsonEncode(userInfo.toJson());
    await _prefs?.setString('userInfo', jsonString);
  }

  /// UserInfo 객체 가져오기: SharedPreferences에서 'userInfo' 키로 저장된 JSON 문자열을 UserInfo 객체로 변환하여 반환합니다.
  static Future<UserInfo?> getUserInfo() async {
    String? jsonString = _prefs?.getString('userInfo');
    if (jsonString != null) {
      Map<String, dynamic> userMap = jsonDecode(jsonString);
      return UserInfo.fromJson(userMap);
    }
    return null;
  }

  /// 사용자 Token 저장하기: 주어진 사용자 userToken를 'userToken' 키에 저장합니다.
  static Future<void> saveUserToken(String userToken) async {
    await _prefs?.setString('userToken', userToken);
  }

  /// 사용자 Token 가져오기: SharedPreferences에서 'userToken' 키로 저장된 사용자 Token을 반환합니다.
  static Future<String?> getUserToken() async {
    return _prefs?.getString('userToken');
  }

  /// 로그아웃: 사용자 ID와 토큰을 SharedPreferences에서 삭제합니다.
  static Future<void> logout() async {
    // await _prefs?.remove('userId');
    await _prefs?.remove('userToken');  // 토큰 삭제, 이는 로그아웃 과정에서 사용자의 세션을 완전히 제거합니다.
    await _prefs?.remove('userInfo');
  }
}
