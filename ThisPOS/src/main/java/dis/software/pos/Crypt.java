/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.software.pos;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Milton Cavazos
 */
public class Crypt
{
    
    private static final Logger logger = LogManager.getLogger(Crypt.class.getSimpleName());
    
    private static final String key = "Peanutsoftware28";
    
    /*public static void main(String[] args)
    {
        String value = "milton28";
        logger.info("Value: " + value);
        String salt = getSalt();
        logger.info("Salt: " + salt);
        logger.info("Value encrypted: " + encrypt(value, salt));
        logger.info("Value decrypted: " + decrypt(encrypt(value, salt), salt));
    }/**/
    
    public static String encrypt(String value, String salt)
    {
        try
        {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, Crypt.getSecretKey());
            byte[] aes = cipher.doFinal((salt + value).getBytes("utf-8"));
            return Base64.getEncoder().encodeToString(aes);
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
            | IllegalBlockSizeException | BadPaddingException ex)
        {
            logger.error("Error encrypting the password", ex);
        }
        catch (UnsupportedEncodingException ex)
        {
            logger.error("Error encoding the password", ex);
        }
        return null;
    }
    
    public static String decrypt(String value, String salt)
    {
        try
        {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, Crypt.getSecretKey());
            byte[] bytes = Base64.getDecoder().decode(value);
            byte[] decrypted = cipher.doFinal(bytes);
            return new String(decrypted, "utf-8").substring(salt.length());
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
            | IllegalBlockSizeException | BadPaddingException ex)
        {
            logger.error("Error decrypting the password", ex);
        } catch (UnsupportedEncodingException ex)
        {
            logger.error("Error decoding the password", ex);
        }
        return null;
    }
    
    public static String getSalt()
    {
        byte[] salt = null;
        try
        {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            salt = new byte[16];
            secureRandom.setSeed(new Date().getTime());
            secureRandom.nextBytes(salt);
        }
        catch (NoSuchAlgorithmException ex)
        {
            logger.error("Error getting salt", ex);
        }
        return Base64.getEncoder().encodeToString(salt);
    }
    
    private static Key getSecretKey()
    {
        Key secretKey = new SecretKeySpec(Crypt.key.getBytes(), "AES");
        return secretKey;
    }
    
}
