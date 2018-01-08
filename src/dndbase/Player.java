/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dndbase;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import securesql.HashMaster;
import securesql.TokenMaster;
import java.util.ArrayList;
import java.sql.*;

/**
 *
 * @author vibhusomeshwar
 */
public class Player extends User {
    
    public ArrayList<Integer> dndChars = new ArrayList<>();
    
    public Player(String token) throws IOException, ClassNotFoundException, SQLException {
        userToken = token;
    }
    /*
    Player information change methods
    */
    @Override
    public void changeUserPassword(String oldPassword, String newPassword,
        DnDsql ddsql)
        throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String uname = TokenMaster.userFromToken(userToken);
        Connection connection = ddsql.connection;
        HashMaster hm = new HashMaster();
        prepstmt = connection.prepareStatement("select pwd, salt from Players" +
                " where uname=?;");
        prepstmt.setString(1, uname);
        rs = prepstmt.executeQuery();
        while (rs.next()) {
            if (hm.checkPassword(oldPassword, rs.getString("salt"), rs.getString("pwd"))) {
                String[] newSPHash = hm.SPhash(newPassword);
                prepstmt = connection.prepareStatement("update Players set pwd=?,salt=? where" +
                        "uname=?");
                prepstmt.setString(1, newSPHash[0]);
                prepstmt.setString(2, newSPHash[1]);
                prepstmt.setString(3, uname);
                prepstmt.executeUpdate();
            }
            else {
                ddsql.statusCode = ddsql.PERMISSION_ERROR;
            }
        }
    }
    /*
    Party methods
    */
    @Override
    public void sendPartyRequest(int partyID, int charID, DnDsql ddsql) throws SQLException {
        
    }

    @Override
    public void respondPartyRequest(int partyID, boolean choice, DnDsql ddsql) 
            throws SQLException{
        
    }
    
    public void joinPartyWithPass(int partyID, int charID, String pwd, DnDsql ddsql) 
            throws SQLException{
        
    }
    
    /*
    Friend interaction methods
    */
    @Override
    public void sendFriendRequest(String friendName, boolean friendType, DnDsql ddsql) 
            throws SQLException {
        
    }
    
    @Override
    public void respondFriendRequest(String friendName, boolean friendType,
            boolean choice, DnDsql ddsql) throws SQLException {
        
    }
    
    @Override
    public void sendMessage(String friendName, boolean friendType, String message,
            DnDsql ddsql) throws SQLException {
        
    }
    
    /*
    Remove relationship methods
    */
    public void block(int blockedID, boolean blockedType, DnDsql ddsql) throws
            SQLException {
        blockUser(userID, blockedID,ddsql.PLAYER_BOOL,blockedType, ddsql);
    }
    
    public void removeFriend(int removeID, boolean removeType, DnDsql ddsql) 
            throws SQLException {
        
    }
    
    /**
     * Creates D&D character for the player user.
     * @param ddch the D&D character object
     * @param ddsql connection object
     * @param privacy the level of privacy
     * @throws SQLException 
     */
    public void createChar(DnDChar ddch, int privacy, DnDsql ddsql) 
            throws SQLException{
        
        Connection connection = ddsql.connection;
        String token = userToken;
        Statement stmt = connection.createStatement();
        prepstmt = connection.prepareStatement("select playerID from Players " + 
                "where uname = ?");
        String username = TokenMaster.userFromToken(token);
        prepstmt.setString(1,username);
        
        rs = prepstmt.executeQuery();
        
        if (!rs.next()) {
            System.out.println("Could not locate player with your username. " +
                    "Please contact the Dev$ support hotline.");
            ddsql.statusCode = ddsql.ILLEGAL_VALUE_ERROR;
        }
        
        else {
        
            rs = prepstmt.executeQuery();
            
            int playerID=0;
            while(rs.next()) {
                playerID = rs.getInt(1);
            }

            prepstmt = connection.prepareStatement("insert into dndCharacters (" +
                    "chName,level,race,hp,constitution,wisdom,intelligence," +
                    "dexterity,strength,charisma,story,playerID, dndClass, privacy) values (" +
                    "?,?,?,?,?,?,?,?,?,?,?,?);");
            prepstmt.setString(1, ddch.getName());
            prepstmt.setInt(2, ddch.getLevel());
            prepstmt.setString(3, ddch.getRace());
            prepstmt.setInt(4, ddch.getHp());
            prepstmt.setInt(5, ddch.getConstitution());
            prepstmt.setInt(6, ddch.getWisdom());
            prepstmt.setInt(7, ddch.getIntelligence());
            prepstmt.setInt(8, ddch.getDexterity());
            prepstmt.setInt(9, ddch.getStrength());
            prepstmt.setInt(10, ddch.getCharisma());
            prepstmt.setString(11, ddch.getStory());
            prepstmt.setInt(12, playerID);
            prepstmt.setString(13, ddch.getDndclass());
            prepstmt.setInt(14, privacy);
            
            prepstmt.executeUpdate();
            
            rs = stmt.executeQuery("select last_insert_id();");
            int charID = 0;
            while(rs.next()) {
                charID = rs.getInt(1);
            }
            
            dndChars.add(charID);
            
            for (ArrayList<String> i : ddch.getSpells()) {
                prepstmt = connection.prepareStatement("select * from Spells where"+
                        " spellName=?, description=?, level=?;");
                prepstmt.setString(1, i.get(0));
                prepstmt.setString(2, i.get(1));
                prepstmt.setInt(3, Integer.parseInt(i.get(2)));
                rs = prepstmt.executeQuery();
                
                if (rs.next()) {
                    rs = prepstmt.executeQuery();
                    int spellID=0;
                    while (rs.next()) {
                        spellID = rs.getInt("spellID");
                    }
                    //spell already in database, add to charspell and go to next spell
                    
                    prepstmt = connection.prepareStatement("insert into CharSpell "+
                            "(charID, spellID) values (?,?);");
                    prepstmt.setInt(1, charID);
                    prepstmt.setInt(2, spellID);
                    prepstmt.executeUpdate();
                }
                else {
                    prepstmt = connection.prepareStatement("insert into Spells (spellName,"+
                            "description,level) values (?,?,?);");
                    prepstmt.setString(1, i.get(0));
                    prepstmt.setString(2, i.get(1));
                    prepstmt.setInt(3, Integer.parseInt(i.get(2)));
                    prepstmt.executeUpdate();
                    
                    int spellID = 0;
                    rs = stmt.executeQuery("select last_insert_id();");
                    while(rs.next()) {
                        spellID = rs.getInt(1);
                    }
                    prepstmt = connection.prepareStatement("insert into CharSpell "+
                            "(charID, spellID) values (?,?);");
                    prepstmt.setInt(1, charID);
                    prepstmt.setInt(2, spellID);
                    prepstmt.executeUpdate();
                }
            }
            for (ArrayList<String> i: ddch.getItems()) {
                prepstmt = connection.prepareStatement("select * from Items where"+
                        " itemName=?, description=?;");
                prepstmt.setString(1, i.get(0));
                prepstmt.setString(2, i.get(1));
                
                rs = prepstmt.executeQuery();
                if (rs.next()) {
                    rs = prepstmt.executeQuery();
                    int itemID=0;
                    while (rs.next()) {
                        itemID = rs.getInt("itemID");
                    }
                    //item already in database, add to charspell and go to next spell
                    
                    prepstmt = connection.prepareStatement("insert into CharItem "+
                            "(charID, itemID) values (?,?);");
                    prepstmt.setInt(1, charID);
                    prepstmt.setInt(2, itemID);
                }
                else {
                    prepstmt = connection.prepareStatement("insert into Items (itemName,"+
                            "description) values (?,?);");
                    prepstmt.setString(1, i.get(0));
                    prepstmt.setString(2, i.get(1));
                    prepstmt.executeUpdate();
                    
                    int itemID = 0;
                    rs = stmt.executeQuery("select last_insert_id();");
                    while(rs.next()) {
                        itemID = rs.getInt(1);
                    }
                    prepstmt = connection.prepareStatement("insert into CharItem "+
                            "(charID, itemID) values (?,?);");
                    prepstmt.setInt(1, charID);
                    prepstmt.setInt(2, itemID);
                    prepstmt.executeUpdate();
                }
            }
            for (String i: ddch.getLanguages()) {
                prepstmt = connection.prepareStatement("insert into CharLang (" +
                        "charID, langName) values (?,?);");
                prepstmt.setInt(1, charID);
                prepstmt.setString(2, i);
                prepstmt.executeUpdate();
            }
        }
    }
    
}
