/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dndbase;
import java.io.*;
import java.sql.*;
import securesql.*;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author vibhusomeshwar
 */
public class DM extends User {
    
    public DM(String token) throws IOException, ClassNotFoundException, SQLException {
        userToken = token; //token for username and expiry time
    }
    
    @Override
    public void changeUserPassword(String oldPassword, String newPassword, DnDsql ddsql) throws
            SQLException, NoSuchAlgorithmException, UnsupportedEncodingException{
        Connection connection = ddsql.connection;
        HashMaster hm = new HashMaster();
        String uname = TokenMaster.userFromToken(userToken);
        prepstmt = connection.prepareStatement("select pwd, salt from DMs" +
                " where uname=?;");
        prepstmt.setString(1, uname);
        rs = prepstmt.executeQuery();
        while (rs.next()) {
            if (hm.checkPassword(oldPassword, rs.getString("salt"), rs.getString("pwd"))) {
                String[] newSPHash = hm.SPhash(newPassword);
                prepstmt = connection.prepareStatement("update DMs set pwd=?,salt=? where" +
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
        return;
    }
    
    @Override
    public void sendPartyRequest(int partyID, int charID, DnDsql ddsql) throws SQLException {
        
    }
    
    @Override
    public void respondPartyRequest(int partyID,boolean choice,
            DnDsql ddsql) throws SQLException {
        
    }
    
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
    
    public void block(int blockedID, boolean blockedType, DnDsql ddsql) throws
        SQLException {
    blockUser(userID, blockedID,ddsql.DM_BOOL,blockedType, ddsql);
    }
    
    public void removeFriend(int removeID, boolean removeType, DnDsql ddsql) 
            throws SQLException {
        removeFriend(userID, removeID, ddsql.DM_BOOL,removeType,ddsql);
    }
    
    /**
     * Creates D&D adventure
     * @param adventureName self-explanatory
     * @param adventureDescription self-explanatory
     * @param pwd Adventure password
     * @param ddsql connection object
     * @throws SQLException
     */
    public void createAdventure(String adventureName, String adventureDescription,
            String pwd, DnDsql ddsql) 
            throws SQLException {
        Connection connection = ddsql.connection;
        String token = userToken;
        prepstmt = connection.prepareStatement("select dmID from DMs where " +
                "uname=?");
        if (!TokenMaster.validateToken(token)) {
            System.out.println("Invalid token");
            ddsql.statusCode = ddsql.TIMEOUT_ERROR;
            return;
        }
        String username = TokenMaster.userFromToken(token);
        if (username.equals("")) {
            System.out.println("Invalid token.");
            ddsql.statusCode = ddsql.ILLEGAL_VALUE_ERROR;
            return;
        }
        prepstmt.setString(1, username);
        rs = prepstmt.executeQuery();
        
        if (!rs.next()) {
            System.out.println("It appears that you are not a registered DM." +
                    " Please register as a DM and try again.");
            ddsql.statusCode = ddsql.ILLEGAL_VALUE_ERROR;
            return;
        }
        
        rs = prepstmt.executeQuery();
        
        int dmID = 0;
        while(rs.next()) {
            dmID = rs.getInt(1);
        }
        
        prepstmt = connection.prepareStatement("insert into Adventures (" +
                "adventureName,advStatus,description,creatorID,pwd) values (?,'Newly begun',?,?,?)");
        prepstmt.setString(1, adventureName);
        prepstmt.setString(2, adventureDescription);
        prepstmt.setInt(3, dmID);
        prepstmt.setString(4, pwd);
        prepstmt.executeUpdate();
    }
    
}
