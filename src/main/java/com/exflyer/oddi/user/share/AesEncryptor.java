package com.exflyer.oddi.user.share;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class AesEncryptor {

  public static String alg = "AES/CBC/PKCS5Padding";
  private final String key = StringUtils.rightPad("0", 16, "oddiad");
  private final String iv = key.substring(0, 16); // 16byte

  public String encrypt(String text) {
    try {
      if (isEncrypted(text)) {
        return text;
      }


      Cipher cipher = Cipher.getInstance(alg);
      SecretKeySpec keySpec = new SecretKeySpec(iv.getBytes(), "AES");
      IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
      cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

      byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
      return Base64.getEncoder().encodeToString(encrypted);
    } catch (Exception e) {
      e.printStackTrace();
      return text;
    }
  }

  public String decrypt(String cipherText) {
    try {
      Cipher cipher = Cipher.getInstance(alg);
      SecretKeySpec keySpec = new SecretKeySpec(iv.getBytes(), "AES");
      IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
      cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

      byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
      byte[] decrypted = cipher.doFinal(decodedBytes);
      return new String(decrypted, "UTF-8");
    } catch (Exception e) {
      return cipherText;
    }
  }

  public boolean isEncrypted(String encryptedString){
    String decrypted = decrypt(encryptedString);
    if (StringUtils.equalsIgnoreCase(decrypted, encryptedString)) {
      return false;
    }
    return true;
  }

}
