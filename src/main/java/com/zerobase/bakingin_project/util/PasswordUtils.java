package com.zerobase.bakingin_project.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordUtils {
    public static boolean equals(String plaintext, String hashed) {

        if (plaintext == null || plaintext.length() < 1) {
            return false;
        }

        if (hashed == null || hashed.length() < 1) {
            return false;
        }

        return BCrypt.checkpw(plaintext, hashed); // 비밀번호와 암호화된 비밀번호 비교 메서드
    }

    public static String encPassword(String plaintext) { // 비밀번호 암호화하는 메서드
        if (plaintext == null || plaintext.length() < 1) {
            return "";
        }
        return BCrypt.hashpw(plaintext, BCrypt.gensalt());
    }
}
