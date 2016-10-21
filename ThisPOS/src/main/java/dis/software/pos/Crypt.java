/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.software.pos;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Milton Cavazos
 */
public class Crypt
{
    
    private static Logger logger = LogManager.getLogger(Crypt.class.getSimpleName());
    
    /*public static void main(String[] args)
    {
        try {
            logger.info(Base64.getEncoder().encodeToString(getSalt()));
            
            byte[] _byte = "MMkaz5VWpTOYEwKOjikaFA==".getBytes();
            logger.info(encrypt("milton28", _byte));
        } catch (NoSuchAlgorithmException | InvalidKeyException ex) {
            logger.error("Error encrypting the password", ex);
        }
    }/**/
    
    public static String encrypt(String value, byte[] salt) throws InvalidKeyException
    {
        String sha1 = null;
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(salt);
            byte[] bytes = md.digest(value.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i=0; i<bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            sha1 = sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            logger.error("Error encrypting the password", ex);
        }
        return sha1;
    }
    
    private static byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        secureRandom.setSeed(new Date().getTime());
        secureRandom.nextBytes(salt);
        return salt;
    }
    
}
