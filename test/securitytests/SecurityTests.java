/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securitytests;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import securesql.*;

/**
 *
 * @author vibhusomeshwar
 */
public class SecurityTests {
    
    public SecurityTests() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void generateAndCompareHash() throws Exception {
        HashMaster hm = new HashMaster();
        String[] saltPlusPassword = hm.SPhash("master");
        String salt = saltPlusPassword[1];
        String hashPassword = saltPlusPassword[0];
        
        assertNotEquals(hashPassword, "master");
        assertTrue(hm.checkPassword("master", salt, hashPassword));
    }
    
    @Test
    public void generateAndValidateToken() throws Exception {
        TokenMaster tm = new TokenMaster();
        String token = TokenMaster.userToken("2017-12-12T01:02:03", "scott", "dm");
        assertEquals(TokenMaster.userFromToken(token), "scott");
        assertTrue(TokenMaster.validateToken(token));
        assertEquals(TokenMaster.typeFromToken(token), "dm");
        
        String encryptedToken = tm.getEncryptedToken(token);
        assertNotEquals(token, encryptedToken);
        String decryptedToken = tm.DecryptToken(encryptedToken);
        assertEquals(token, decryptedToken);
        
        String falseToken = TokenMaster.userToken("blah", "blah", "blah");
        assertFalse(TokenMaster.validateToken(falseToken));
        
        String expiredToken = TokenMaster.userToken("2015-12-12T12:02:03", "scott", "player");
        assertFalse(TokenMaster.validateToken(expiredToken));
    }
}
