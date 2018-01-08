/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dndbase;
import java.io.*;
import java.sql.*;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.security.NoSuchAlgorithmException;
import securesql.*;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

/**
 *
 * @author vibhusomeshwar
 */
public class DnDsql {
    
    Statement stmt;
    public Connection connection;
    PreparedStatement prepstmt;
    ResultSet rs, rs2, rs3;
    HashMaster hm;
    int statusCode;
    
    final boolean DIRECTION_TODM=true;
    final boolean DIRECTION_TOPLAYER=false;
    
    final boolean REQUEST_APPROVED=true;
    final boolean REQUEST_PENDING=false;
    
    final boolean USER_BLOCKED=true;
    
    final int PRIVACY_GLOBAL=0;
    final int PRIVACY_PARTY=1;
    
    final int SUCCESS = 0;
    final int PERMISSION_ERROR = 1;
    final int CONNECTION_ERROR = 2;
    final int DATABASE_ERROR = 3;
    final int ILLEGAL_VALUE_ERROR = 4;
    final int TIMEOUT_ERROR = 5;
    final int UNKNOWN_ERROR = 1000;
    
    final boolean PLAYER_BOOL = true;
    final boolean DM_BOOL = false;
    /**
     *Initializes the database using stored credentials.
     * @throws IOException
     * Thrown if file not found
     * @throws ClassNotFoundException
     * Thrown if jbdc link is wrong
     * @throws SQLException
     * Thrown in event of SQL error
    */
    public DnDsql() throws IOException, ClassNotFoundException,
        SQLException
    {
        statusCode = UNKNOWN_ERROR;
        File sqlcreds = new File("sqlCredentials.txt");
        Scanner scan = new Scanner(sqlcreds);
        
        
        String sqlUser;
        String sqlPassword;
        String[][] credentialsArray = new String[2][2];
        
        Pattern pattern = Pattern.compile("\".*?\"");
        
        
        credentialsArray[0][0] = "username";
        credentialsArray[1][0] = "password";
        
        int i=0;
        while(scan.hasNextLine()) {
            String cred = scan.findInLine(pattern);
            if(cred != null) {
                if (i==0) {
                    cred = cred.replaceAll("\"", "");
                    credentialsArray[0][1] = cred.replaceAll("\"", "");
                }
                if (i==1) {
                    cred = cred.replaceAll("\"", "");
                    credentialsArray[1][1] = cred;
                }
                i++;
            }
            try {
                scan.nextLine();
            }
            
            catch (Exception e) {
            }
        }
        
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("Driver loaded");
        
        connection = DriverManager.getConnection("jdbc:mysql://"
        + "localhost:3306/dnd", credentialsArray[0][1], credentialsArray[1][1]);
        System.out.println("Database connected");
    }
    
    //Users ---------------------------------------------------
    
    /**
     * Creates Dungeon Master user.
     * @param username the chosen username
     * @param password the chosen password
     * @param fName DM's first name
     * @param lName DM's last name
     * @throws IOException
     * Thrown if error occurs with hash
     * @throws SQLException
     * @throws NoSuchAlgorithmException
    */
    public void createDM(String username,String password,
            String fName, String lName) throws IOException, SQLException,
            NoSuchAlgorithmException{
        prepstmt = connection.prepareStatement("select dmID from DMs where " +
                "uname=?");
        prepstmt.setString(1, username);
        rs = prepstmt.executeQuery();
        
        if (rs.next()) {
            System.out.println("Sorry, that username has already been taken." +
                    " Please choose another username and try again.");
            statusCode = ILLEGAL_VALUE_ERROR;
            return;
        }
        
        hm = new HashMaster();
        
        prepstmt = connection.prepareStatement("insert into DMs (firstName," +
        "lastName,uname,pwd,salt) values (?,?,?,?,?);");
        prepstmt.setString(1, fName);
        prepstmt.setString(2, lName);
        prepstmt.setString(3, username);
        String[] SandHash = hm.SPhash(password);
        prepstmt.setString(4,SandHash[0]);
        prepstmt.setString(5,SandHash[1]);
        prepstmt.executeUpdate();
    }
    
    /**
     * Creates player user.
     * @param username the chosen username
     * @param password the chosen password
     * @param fName the player's first name
     * @param lName the player's last name
     * @throws IOException
     * @throws SQLException
     * @throws NoSuchAlgorithmException 
     */
    public void createPlayer(String username, String password,
            String fName, String lName) throws IOException,
            SQLException, NoSuchAlgorithmException{
        prepstmt = connection.prepareStatement("select playerID from Players where " +
                "uname=?");
        prepstmt.setString(1, username);
        rs = prepstmt.executeQuery();
        
        if (rs.next()) {
            System.out.println("Sorry, that username has already been taken." +
                    " Please choose another username and try again.");
            statusCode = ILLEGAL_VALUE_ERROR;
            return;
        }
        
        hm = new HashMaster();
        
        prepstmt = connection.prepareStatement("insert into Players (firstName," +
        "lastName,uname,pwd,salt) values (?,?,?,?,?);");
        prepstmt.setString(1, fName);
        prepstmt.setString(2, lName);
        prepstmt.setString(3, username);
        String[] SandHash = hm.SPhash(password);
        prepstmt.setString(4,SandHash[0]);
        prepstmt.setString(5,SandHash[1]);
        prepstmt.executeUpdate();
    }
    
    public ArrayList<Integer> getAllCharsOfPlayer(String token) {
        ArrayList<Integer> charIDs = new ArrayList<>();
        
        return charIDs;
    }
    
        /**
     * Returns ID of user given username
     * @param uname
     * @param utype
     * @return
     * @throws SQLException 
     */
    public int getUser(String uname, boolean utype) throws SQLException {
        int uid = 0;
        if (utype == PLAYER_BOOL) {
            prepstmt = connection.prepareStatement("select playerID from " +
                    "Players where uname = ?");
            prepstmt.setString(1, uname);
            rs = prepstmt.executeQuery();
            if (rs.next()) {
                rs = prepstmt.executeQuery();
                while(rs.next()) {
                    uid = rs.getInt(1);
                }
            }
            else {
                statusCode = ILLEGAL_VALUE_ERROR;
            }
        }
        else {
            prepstmt = connection.prepareStatement("select dmID from DMs where " +
                    "uname = ?");
            prepstmt.setString(1, uname);
            rs = prepstmt.executeQuery();
            if (rs.next()) {
                rs = prepstmt.executeQuery();
                while(rs.next()) {
                    uid = rs.getInt(1);
                }
            }
            else {
                statusCode = ILLEGAL_VALUE_ERROR;
            }
        }
        return uid;
    }
    
    //Party/Adventure  ----------------------------------------------
    
    public ArrayList<Integer> getAllCharsOfParty(String token, int partyID) {
        ArrayList<Integer> charIDs = new ArrayList<>();
        return charIDs;
    }
    
    public void updateParty(String story, int partyID, String token) {
        
    }
    
    public void removeSelfFromParty(int charID, int partyID, String token) {
        
    }
    
    private void removeFromParty(int charID, int partyID, String token) {
        
    }
    
    public void addToParty(int charID, int partyID, String token) {
        
    }
    
    public ArrayList<Integer> getAllPartiesOfAdventure(String token, int AdvID) {
        ArrayList<Integer> partyIDs = new ArrayList<>();
        return partyIDs;
    }
    
    //Character ----------------------------------------------------
    public void deleteChar(int charID, String token) {
        
    }
}