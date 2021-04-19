package com.bj58.ctf2021.privatefilesystem.common.utils.aes;


import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * AES加密128位CBC模式工具类
 *
 * @author me
 * @date 2021/3/24
 */
public class AesUtils {


    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    /**
     * 加密
     */
    public static String encrypt(String key, String iv, String content) {
        byte[] raw = key.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, ALGORITHM);
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(TRANSFORMATION);
            IvParameterSpec ips = new IvParameterSpec(getIv(iv));
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ips);
            byte[] encrypted = cipher.doFinal(content.getBytes());
            return Base64Utils.encodeToUrlSafeString(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密
     */
    public static String decrypt(String key, String iv, String content) {
        try {
            byte[] raw = key.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            IvParameterSpec ips = new IvParameterSpec(getIv(iv));
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ips);
            byte[] encrypted1 = Base64Utils.decodeFromUrlSafeString(content);
            try {
                byte[] original = cipher.doFinal(encrypted1);
                return new String(original);
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    private static byte[] getIv(String iv) {
        if (iv == null) {
            return new byte[16];
        }
        if (iv.length() > 16) {
            return StringUtils.right(iv, 16).getBytes();
        }
        return iv.getBytes();
    }

}