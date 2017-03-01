package com.hao.common.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Package com.mk.lockdemo
 * @作 用:
 * @创 建 人: linguoding 邮箱：linggoudingg@gmail.com
 * @日 期: 2017年02月06日  16:13
 */


public class AESUtil {
    private final static String IV = "0102030405060708";

    private static byte[] Encrypt(byte[] text, byte[] key) throws Exception {
        SecretKeySpec aesKey = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(IV.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, aesKey, iv);
        return cipher.doFinal(text);
    }

    private static byte[] Decrypt(byte[] text, byte[] key) throws Exception {
        SecretKeySpec aesKey = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, aesKey, iv);
        return cipher.doFinal(text);
    }

    public static String EncodeAES(String password, String key) throws Exception {
        byte[] keybBytes = MD5STo16Byte.encrypt2MD5toByte16(key);
        byte[] passwdBytes = password.getBytes();
        byte[] aesBytyes = Encrypt(passwdBytes, keybBytes);
        return new String(Base64.encode(aesBytyes));
    }

    public static String DeCodeAES(String password, String key) throws Exception {
        byte[] keybBytes = MD5STo16Byte.encrypt2MD5toByte16(key);
        byte[] debase64Bytes = Base64.decode(password);
        return new String(Decrypt(debase64Bytes, keybBytes));
    }

    public static byte[] encrypotByte16(String key) {
        byte[] tempBytes = new byte[16];
        byte keyByte[] = key.getBytes();
        for (int i = 0; i < keyByte.length; i++) {
            tempBytes[i] = keyByte[i];
        }
        return tempBytes;
    }

}
