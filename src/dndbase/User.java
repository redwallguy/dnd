/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dndbase;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import securesql.*;
import java.util.ArrayList;

/**
 *
 * @author vibhusomeshwar
 */
public abstract class User{
    
    String userToken, fName, lName;
    int userID;
    ArrayList<Integer> parties = new ArrayList<>();
    ArrayList<Integer> Pfriends = new ArrayList<>();
    ArrayList<Integer> Pblocked = new ArrayList<>();
    ArrayList<Integer> Dfriends = new ArrayList<>();
    ArrayList<Integer> Dblocked = new ArrayList<>();
    
    
    PreparedStatement prepstmt;
    ResultSet rs;
    ResultSet rs2;
    ResultSet rs3;
    
    /**
     * changes the user's password
     * @param oldPassword
     * @param newPassword
     * @param ddsql instance of DnDsql, used for connection to database
     * @throws SQLException
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException 
     */
    public abstract void changeUserPassword(String oldPassword, String newPassword,
            DnDsql ddsql) throws SQLException, NoSuchAlgorithmException,
            UnsupportedEncodingException;
    
    public abstract void sendFriendRequest(String friendName, boolean friendType,
            DnDsql ddsql) throws SQLException;
    
    public abstract void respondFriendRequest(String friendName, boolean friendType,
            boolean choice, DnDsql ddsql) throws SQLException;
    
    public abstract void sendPartyRequest(int partyID, int charID, DnDsql ddsql) throws
            SQLException;
    
    public abstract void respondPartyRequest(int partyID, boolean choice,
            DnDsql ddsql) throws SQLException;
    
    public abstract void sendMessage(String friendName, boolean friendType,
            String message, DnDsql ddsql) throws SQLException;
    
    /**
     * Adds receiver to sender's block list
     * @param blockerID ID of blocker
     * @param blockedID ID of blocked
     * @param blockertype DM or player (PLAYER_BOOL or DM_BOOL)
     * @param blockedType DM or player (PLAYER_BOOL or DM_BOOL)
     * @param ddsql incoming connection object
     * @throws SQLException 
     */
    public void blockUser(int blockerID, int blockedID, boolean blockertype,
        boolean blockedType, DnDsql ddsql) throws
        SQLException{
        Connection connection = ddsql.connection;
        
        prepstmt = connection.prepareStatement("select * from Friends where " +
                "friend1 = ? and friend2 = ? and friend1type = ? and " +
                "friend2type = ?");
        prepstmt.setInt(1, blockerID);
        prepstmt.setInt(2, blockedID);
        prepstmt.setBoolean(3, blockertype);
        prepstmt.setBoolean(4, blockedType);
        rs = prepstmt.executeQuery();
        if(rs.next()) {
            prepstmt = connection.prepareStatement("delete from Friends where " +
                "friend1 = ? and friend2 = ? and friend1type = ? and " +
                "friend2type = ?");
            prepstmt.setInt(1, blockerID);
            prepstmt.setInt(2, blockedID);
            prepstmt.setBoolean(3, blockertype);
            prepstmt.setBoolean(4, blockedType);
            prepstmt.executeUpdate();
            if (blockedType == ddsql.PLAYER_BOOL) {
                Pfriends.remove(Pfriends.indexOf(blockedID));
            }
            else {
                Dfriends.remove(Dfriends.indexOf(blockedID));
            }
        }
        
        prepstmt = connection.prepareStatement("select * from FriendRequest where " +
                "sender = ? and receiver = ? and sendertype = ? and " +
                "receivertype = ?");
        prepstmt.setInt(1, blockerID);
        prepstmt.setInt(2, blockedID);
        prepstmt.setBoolean(3, blockertype);
        prepstmt.setBoolean(4, blockedType);
        rs = prepstmt.executeQuery();
        if(rs.next()) {
            prepstmt = connection.prepareStatement("delete from FriendRequest where " +
                "sender = ? and receiver = ? and sendertype = ? and " +
                "receivertype = ?");
            prepstmt.setInt(1, blockerID);
            prepstmt.setInt(2, blockedID);
            prepstmt.setBoolean(3, blockertype);
            prepstmt.setBoolean(4, blockedType);
            prepstmt.executeUpdate();
        }
        
        prepstmt = connection.prepareStatement("select * from UserBlocks " +
                "where blocker = ? and blocked = ? and blockerType = ? and " +
                "blockedType = ?");
        prepstmt.setInt(1, blockerID);
        prepstmt.setInt(2, blockedID);
        prepstmt.setBoolean(3, blockertype);
        prepstmt.setBoolean(4, blockedType);
        rs = prepstmt.executeQuery();
        if(rs.next()) {
            return;
        }
        else {
            prepstmt = connection.prepareStatement("insert into UserBlocks(" +
                    "blocker, blocked, blockerType, blockedType) values (?,?,?,?)");
            prepstmt.setInt(1, blockerID);
            prepstmt.setInt(2, blockedID);
            prepstmt.setBoolean(3, blockertype);
            prepstmt.setBoolean(4, blockedType);
            prepstmt.executeUpdate();
            if (blockedType == ddsql.PLAYER_BOOL) {
                Pblocked.add(blockedID);
            }
            else {
                Dblocked.add(blockedID);
            }
        }
    }
    
    public void removeFriend(int removerID, int removedID, boolean removerType,
            boolean removedType, DnDsql ddsql) throws SQLException {
        Connection connection = ddsql.connection;
        
        prepstmt = connection.prepareStatement("select * from Friends where " +
                "friend1 = ? and friend2 = ? and friend1type = ? and " +
                "friend2type = ?");
        prepstmt.setInt(1, removerID);
        prepstmt.setInt(2, removedID);
        prepstmt.setBoolean(3, removerType);
        prepstmt.setBoolean(4, removedType);
        rs = prepstmt.executeQuery();
        if(rs.next()) {
            prepstmt = connection.prepareStatement("delete from Friends where " +
                "friend1 = ? and friend2 = ? and friend1type = ? and " +
                "friend2type = ?");
            prepstmt.setInt(1, removerID);
            prepstmt.setInt(2, removedID);
            prepstmt.setBoolean(3, removerType);
            prepstmt.setBoolean(4, removedType);
            prepstmt.executeUpdate();
            if (removedType == ddsql.PLAYER_BOOL) {
                Pfriends.remove(Pfriends.indexOf(removedID));
            }
            else {
                Dfriends.remove(Dfriends.indexOf(removedID));
            }
        }
        
        prepstmt = connection.prepareStatement("select * from FriendRequest where " +
                "sender = ? and receiver = ? and sendertype = ? and " +
                "receivertype = ?");
        prepstmt.setInt(1, removerID);
        prepstmt.setInt(2, removedID);
        prepstmt.setBoolean(3, removerType);
        prepstmt.setBoolean(4, removedType);
        rs = prepstmt.executeQuery();
        if(rs.next()) {
            prepstmt = connection.prepareStatement("delete from FriendRequest where " +
                "sender = ? and receiver = ? and sendertype = ? and " +
                "receivertype = ?");
        prepstmt.setInt(1, removerID);
        prepstmt.setInt(2, removedID);
        prepstmt.setBoolean(3, removerType);
        prepstmt.setBoolean(4, removedType);
        prepstmt.executeUpdate();
        }
    }
}
