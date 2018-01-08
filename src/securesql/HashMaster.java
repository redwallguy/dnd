/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securesql;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;
import java.io.*;

/**
 *
 * @author vibhusomeshwar
 */
public class HashMaster {
    MessageDigest md;
    String pepper="";
    public HashMaster() throws FileNotFoundException, IOException {
        try(FileReader freader = new FileReader("pepper.txt");
        BufferedReader breader = new BufferedReader(freader);) {
            String message = "";
            String line;
            while ((line = breader.readLine()) != null) {
                message += line;
        }
        if (!message.equalsIgnoreCase("")) {
            pepper=message;
        }
    } //Pepper value for hashing
   }
    
    /**
     * Hashes a password using S&P method
     * @param password
     * @return Array containing salt and hashed password
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException 
     */
    public String[] SPhash(String password) throws NoSuchAlgorithmException,
            UnsupportedEncodingException{
        md = MessageDigest.getInstance("SHA-256");
        String salt = randString(16);
        
        String strToHash = salt + password + pepper;
        byte[] hashRes = md.digest(strToHash.getBytes());
        String encodedResult = Base64.getEncoder().encodeToString(hashRes);
        
        String[] results = {encodedResult, salt};
        return results;
    }
    
    /**
     * Checks to see if the input password and salt results in the correct hash value.
     * @param password
     * @param salt Random string prepended to password before hashing
     * @param hash Stored hash value
     * @return True if valid
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException 
     */
    public boolean checkPassword(String password, String salt, String hash) throws
            NoSuchAlgorithmException, UnsupportedEncodingException{
        md = MessageDigest.getInstance("SHA-256");
        
        String strToHash = salt + password + pepper;
        byte[] hashRes = md.digest(strToHash.getBytes());
        String encodedResult = Base64.getEncoder().encodeToString(hashRes);
        
        return (encodedResult.equals(hash));
    }
    
    /**
     * Creates string of random characters
     * @param length length of resulting string
     * @return Random string
     */
    private String randString(int length) {
        Random rand = new Random();
        char[] dict = {'a','b','c','d','e','f','g','h','i','j','k','l','m',
            'n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E',
            'F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V',
            'W','X','Y','Z','0','1','2','3','4','5','6','7','8','9'};
        String salt = "";
        for(int i=0; i<length;i++) {
            salt += Character.toString(dict[rand.nextInt(62)]);
        }
        return salt;
    }
}
