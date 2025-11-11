package com.api.csm.utils;

import com.api.csm.config.properties.SpringProperties;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class AesUtil {
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final String ALGORITHM = "AES";
    private final SpringProperties springProperties;

    private SecretKey getSecretKey() {
        byte[] keyBytes = springProperties.getJwtKey().getBytes(StandardCharsets.UTF_8);
        int length = keyBytes.length;
        if (length != 16 && length != 24 && length != 32) {
            throw new IllegalArgumentException("Invalid AES key length: " + length +
                    " bytes. Must be 16, 24, or 32.");
        }
        return new SecretKeySpec(keyBytes, ALGORITHM);
    }

    public String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        byte[] iv = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        SecretKey key = getSecretKey();
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        byte[] combined = new byte[iv.length + encrypted.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);

        return Base64.getEncoder().encodeToString(combined);
    }

    public String decrypt(String encryptedData) {
        byte[] combined = Base64.getDecoder().decode(encryptedData);
        byte[] iv = Arrays.copyOfRange(combined, 0, 16);
        byte[] cipherBytes = Arrays.copyOfRange(combined, 16, combined.length);

        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            SecretKey key = getSecretKey();
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
            byte[] original = cipher.doFinal(cipherBytes);
            return new String(original, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
