/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqltests;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.*;
import dndbase.*;
import java.sql.*;
import securesql.*;

/**
 *
 * @author vibhusomeshwar
 */
public class SQLTests {
    
    public SQLTests() {
    }
    
    static DnDsql mydds;
    static Statement stmt;
    ResultSet rs;
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        mydds = new DnDsql();
        mydds.connection.setAutoCommit(false); //think about making connection var
        //private after testing
        stmt = mydds.connection.createStatement();
        
        HashMaster hm = new HashMaster();
        String[] SPHash = hm.SPhash("hellothere");
        String pwd = SPHash[0];
        String salt = SPHash[1];
        String uname = "TheLegend27";
        String fname = "Motherfucking";
        String lname = "Dunkey";
        
        stmt.executeUpdate("insert into DMs (firstName, lastName, uname, " + 
                "pwd, salt) values (\""+ fname + "\",\"" + lname + "\",\"" + uname + "\",\"" +
                pwd + "\",\"" + salt + "\");");
        
        SPHash = hm.SPhash("generalkenobi");
        pwd = SPHash[0];
        salt = SPHash[1];
        uname = "n00blord";
        fname = "Dunkmaster";
        lname = "Yi";
        
        stmt.executeUpdate("insert into Players (firstName, lastName, uname, " + 
                "pwd, salt) values (\"" + fname+"\",\""+lname+"\",\""+uname+"\",\""+pwd+"\",\""+salt+"\");");
        
        
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception {
        mydds.connection.rollback();
        mydds.connection.close();
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    @Ignore
    public void getTestDM() {
        
    }
    
    @Test
    @Ignore
    public void createCharAndCheckStats() {
        
    }
    
    @Test
    public void createPlayerandCheckInfo() throws Exception {
        mydds.createPlayer("tester", "tester", "Testy", "McTestface");
        rs = stmt.executeQuery("select * from Players where uname = 'tester'");
        while (rs.next()) {
            assertEquals(rs.getString("uname"), "tester");
            assertEquals(rs.getString("firstName"), "Testy");
            assertEquals(rs.getString("lastName"), "McTestface");
            assertTrue(rs.getString("salt") != null);
            assertTrue(rs.getString("pwd") != null);
        }
    }
   
    @Test
    @Ignore
    public void createAdventureAndCheckDM() throws Exception {
        mydds.createAdventure(TokenMaster.userToken("2017-12-12T01:02:03",
                "TheLegend27", "DM"), "Taric's Fabulous Adventure" , "Taric goes looking for gems.", "Gems");
        
        rs = stmt.executeQuery("select dmID from DMs where uname='TheLegend27';");
        int dmID=0;
        while (rs.next()){
            dmID = rs.getInt(1);
        }
        
        rs = stmt.executeQuery("select * from Adventures where creatorID="+dmID+";");
        while(rs.next()) {
            assertEquals("Taric's Fabulous Adventure", rs.getString("adventureName"));
            assertEquals("Taric goes looking for gems.", rs.getString("description"));
            assertEquals("Newly begun", rs.getString("advStatus"));
        }
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
