package com.lakala.pos.erp.demo.utils.base;


import com.lakala.pos.erp.demo.utils.base.base64.Base64Utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author wanliang527 </br>
 * @date 2018/9/6 </br>
 */
public class RsaUtil {
    private final static String ALGORITHM_RSA = "RSA";

    private static final String RSA_PUBKEY_PREFIX = "-----BEGIN PUBLIC KEY-----";
    private static final String RSA_PUBKEY_SUFFIX = "-----END PUBLIC KEY-----";
    private static final String RSA_PRIKEY_PREFIX = "-----BEGIN PRIVATE KEY-----";
    private static final String RSA_PRIKEY_SUFFIX = "-----END PRIVATE KEY-----";

    /**
     * 获取RSA公钥对象
     *
     * @param pubKey 公钥数据
     * @return 公钥对象
     * @throws Exception
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PublicKey getPublicKey(String pubKey) {
        pubKey = pubKey.replace(RSA_PUBKEY_PREFIX, "").replace(RSA_PUBKEY_SUFFIX, "");
        X509EncodedKeySpec x509 = new X509EncodedKeySpec(Base64Utils.decode(pubKey));
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
            return keyFactory.generatePublic(x509);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取公钥 </br>
     *
     * @param fileIs 文件输入流
     * @return 公钥对象
     * @throws Exception
     */
    public static PublicKey getPublicKey(InputStream fileIs) throws Exception {
      InputStreamReader inputReader = new InputStreamReader(fileIs, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputReader);
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return getPublicKey(stringBuilder.toString());
    }

    /**
     * 获取私钥 </br>
     * Java只支持PKCS8格式的私钥，OpenSSL默认生成的是PKCS1格式的，需要进行转换
     *
     * @param priKey 私钥数据
     * @return 私钥对象
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String priKey) {
        priKey =priKey.replace(RSA_PRIKEY_PREFIX, "").replace(RSA_PRIKEY_SUFFIX, "");
        byte[] keyBytes = null;
        try {
            keyBytes = Base64Utils.decode(priKey);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            return privateKey;
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取私钥 </br>
     *
     * @param fileIs 文件输入流
     * @return 私钥对象
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(InputStream fileIs) throws Exception {
       InputStreamReader inputReader = new InputStreamReader(fileIs, StandardCharsets.UTF_8);
     BufferedReader bufferedReader = new BufferedReader(inputReader);
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return getPrivateKey(stringBuilder.toString());
    }

    /**
     * 私钥签名
     *
     * @param priKey    私钥对象
     * @param content   待签名数据
     * @param algorithm 签名算法{@link Signature}
     * @return 签名信息
     */
    public static byte[] signWithPrivateKey(PrivateKey priKey, byte[] content, String algorithm) {
        try {
            Signature signature = Signature.getInstance(algorithm);
            signature.initSign(priKey);
            signature.update(content);
            byte[] signData = signature.sign();
            return signData;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 私钥加密
     *
     * @param privateKey 私钥对象
     * @param algorithm  算法{@link Cipher}
     * @param data       明文
     * @return 密文
     */
    public static byte[] encryptByPrivateKey(PrivateKey privateKey, String algorithm, byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] result = cipher.doFinal(data);
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 私钥解密
     *
     * @param privateKey 私钥对象
     * @param algorithm  算法{@link Cipher}
     * @param cipherText 密文
     * @return 明文
     */
    public static byte[] decryptByPrivateKey(PrivateKey privateKey, String algorithm, byte[] cipherText) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] result = cipher.doFinal(cipherText);
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 公钥加密
     *
     * @param pubKey    公钥对象
     * @param algorithm 算法{@link Cipher}
     * @param data      明文
     * @return 密文
     */
    public static byte[] encryptByPublicKey(PublicKey pubKey, String algorithm, byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            byte[] result = cipher.doFinal(data);
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 公钥解密
     *
     * @param pubKey     公钥对象
     * @param algorithm  算法{@link Cipher}
     * @param cipherText 密文
     * @return 明文
     */
    public static byte[] decryptByPublicKey(PublicKey pubKey, String algorithm, byte[] cipherText) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, pubKey);
            byte[] result = cipher.doFinal(cipherText);
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 公钥验签
     *
     * @param pubKey    公钥对象
     * @param content   原始数据
     * @param sign      签名数据
     * @param algorithm 算法
     * @return 验证成功返回ture，否则返回false
     */
    public static boolean verifyByPublicKey(PublicKey pubKey, byte[] content, byte[] sign, String algorithm) {
        try {
            Signature signature = Signature.getInstance(algorithm);
            signature.initVerify(pubKey);
            signature.update(content);
            boolean result = signature.verify(sign);
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return false;
    }

}
