/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securesql;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.time.*;
import java.util.regex.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
/**
 *
 * @author vibhusomeshwar
 */
public class TokenMaster {
    
    SecretKey sk;
    
    /**
     * Initializes tokenizer with AES key
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException 
     */
    public TokenMaster() throws IOException, NoSuchAlgorithmException,
            InvalidKeySpecException{
        Path path = Paths.get("aeskey.key");
        byte[] keybytes = Files.readAllBytes(path);
        sk = new SecretKeySpec(keybytes, "AES");
    }
    
    /**
     * Encrypts a user token
     * @param token
     * @return String representation of encrypted token
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException 
     */
    public String getEncryptedToken(String token) throws InvalidKeyException,
            NoSuchAlgorithmException,UnsupportedEncodingException,
            NoSuchPaddingException,IllegalBlockSizeException,
            BadPaddingException {
        byte[] cipherText = null;
        final Cipher cipherenc = Cipher.getInstance("AES");
        cipherenc.init(Cipher.ENCRYPT_MODE, sk);
        cipherText = cipherenc.doFinal(token.getBytes());

        return Base64.getEncoder().encodeToString(cipherText);
        //return Base64.getEncoder().encodeToString(cipherText);
    }
    
    /**
     * Decrypts an encrypted token
     * @param encryptedToken String representation of encrypted token
     * @return decrypted token String
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException 
     */
    public String DecryptToken(String encryptedToken) throws InvalidKeyException,
            NoSuchAlgorithmException,UnsupportedEncodingException,
            NoSuchPaddingException,IllegalBlockSizeException,
            BadPaddingException {
        final Cipher cipherdec = Cipher.getInstance("AES");
        cipherdec.init(Cipher.DECRYPT_MODE, sk);
        byte[] decryptedMessage = cipherdec.doFinal(Base64.getDecoder().decode(encryptedToken.getBytes()));
        String decry = new String(decryptedMessage);
        
        return decry;
    }
    
    /**
     * Creates a user token from username and expiry time
     * @param expireTime
     * @param username
     * @param userType player or DM
     * @return token
     */
    public static String userToken(String expireTime, String username, String userType) {
        String token = "user=\"" + username + "\"|expireTime=\"" + expireTime + "\"" +
                "|userType=\"" + userType + "\"";
        return token;
    }
    
    public static String timedToken(String username, String userType) {
        Duration dur = Duration.ofHours(1);
        String timePlusOneHour = LocalDateTime.now().plus(dur).toString();
        return userToken(timePlusOneHour, username, userType);
    }
    
    /**
     * Checks that token's expiry date has not yet elapsed
     * @param token
     * @return true if valid
     */
    public static boolean validateToken(String token) {
        Pattern pattern = Pattern.compile("\"(.*?)\"");
        String[] tokenSplit = token.split("\\|");
        Matcher m = pattern.matcher(tokenSplit[1]);
        if(m.find()) {
            try 
            {
            LocalDateTime dt = LocalDateTime.parse(m.group(1));
            Duration dur = Duration.between(LocalDateTime.now(), dt);
            return dur.compareTo(Duration.ZERO) != -1;
            }
            catch(Exception e) {
                return false;
            }
        }
        else {
            return false;
        }
    }
    
    /**
     * Returns username from token
     * @param token
     * @return username if valid, empty string if invalid
     */
    public static String userFromToken(String token) {
        Pattern pattern = Pattern.compile("\"(.*?)\"");
        String[] tokenSplit = token.split("\\|");
        Matcher m = pattern.matcher(tokenSplit[0]);
        if (m.find()) {
            return m.group(1);
        }
        else return "";
    }
    /**
     * Returns user type from token (player or DM)
     * @param token
     * @return user type if valid, empty string if invalid
     */
    public static String typeFromToken(String token) {
        Pattern pattern = Pattern.compile("\"(.*?)\"");
        String[] tokenSplit = token.split("\\|");
        Matcher m = pattern.matcher(tokenSplit[2]);
        if (m.find()) {
            return m.group(1);
        }
        else return "";
    }
    
}
