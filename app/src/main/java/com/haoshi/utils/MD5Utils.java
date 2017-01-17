package com.haoshi.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author: HaoShi
 */
public class MD5Utils {

    private static String SALT = "1254mDMEnl";//加密的盐。增加破解难度

    //加密方法
    public static String getMD5(String content) {
        String encryptedContent = "";//加密内容
        String body = SALT + content + SALT;//加密主体，将内容和盐进行拼接
        //MD5加密
        try {
            StringBuffer sb = new StringBuffer();
            MessageDigest md5 = MessageDigest.getInstance("md5");
            byte[] encryptedBytes = md5.digest(body.getBytes());
            for (byte b : encryptedBytes) {
                int i = b & 0xff;
                String hexString = Integer.toHexString(i);
                if (hexString.length() == 1) {//如果只有一位的话附加一位
                    hexString = "0" + hexString;
                }
                sb.append(hexString);//添加到字符串中
            }
            encryptedContent = sb.toString();//将字符串数组转变成字符串

        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return encryptedContent;
    }
}
