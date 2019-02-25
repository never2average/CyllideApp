package com.example.kartikbhardwaj.bottom_navigation;

//import android.util.Base64;
import org.apache.commons.codec.binary.Base64;

import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static android.nfc.tech.MifareClassic.BLOCK_SIZE;

public class AdvancedEncryptionStandard
{
    private byte[] key;

    private static final String ALGORITHM = "AES";

    public AdvancedEncryptionStandard(byte[] key)
    {
        this.key = key;
    }

    /**
     * Encrypts the given plain text
     *
     * @param plainText The plain text to encrypt
     */
    public byte[] encrypt(byte[] plainText) throws Exception
    {
        SecretKeySpec secretKey = new SecretKeySpec(key, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        return cipher.doFinal(plainText);
    }

    public byte[] decrypt(byte[] cipherText) throws Exception
    {
        SecretKeySpec secretKey = new SecretKeySpec(key, ALGORITHM);
        final byte[] iv = Arrays.copyOfRange(cipherText, 0, BLOCK_SIZE);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, secretKey,ivParameterSpec);
        byte[] decode = Base64.decodeBase64(cipherText);



//        return cipher.doFinal(cipherText);
        byte[] decValue = cipher.doFinal(decode);
        return decValue;
//        String decryptedValue = new String(decValue);
//        String decoded=new String(Base64.decode(decryptedValue,Base64.NO_OPTIONS));
//        return decoded;
    }
}