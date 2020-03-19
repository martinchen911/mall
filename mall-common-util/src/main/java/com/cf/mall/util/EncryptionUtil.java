package com.cf.mall.util;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * 加密工具类
 * @Author chen
 * @Date 2020/3/19
 */
public class EncryptionUtil {


    public static String encrypt(String str) throws Exception {
        return encrypt(str,"MD5");
    }

    public static String encrypt(String str,String algorithm) throws Exception {
        return encrypt(str,algorithm,32);
    }
    public static String encrypt(String str,String algorithm,int radix) throws Exception {
        return encrypt(str,algorithm,radix,1);
    }

    /**
     * 字符串加密
     * @param str   原文
     * @param algorithm      算法名
     * @param radix     基数
     * @param signum    符号
     * @return
     * @throws Exception
     */
    public static String encrypt(String str,String algorithm,int radix,int signum) throws Exception {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        md.update(str.getBytes());
        return new BigInteger(signum,md.digest()).toString(radix);
    }
}
