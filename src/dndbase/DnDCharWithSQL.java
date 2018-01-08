/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dndbase;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import securesql.TokenMaster;
import java.sql.*;

/**
 *
 * @author vibhusomeshwar
 */
public class DnDCharWithSQL extends Player {
    
    public DnDChar ddch;
    public int charID;
    
    public DnDCharWithSQL(String creatorToken, DnDChar dd, int chID)
            throws IOException, ClassNotFoundException, SQLException {
        super(creatorToken);
        ddch = dd;
        charID = chID;
    }
    
    /**
     * Retrieves character stats
     * @param ddsql connection object
     * @throws SQLException
     * @return D&D character statistics
     */
    public Map<String, Integer> getCharStats(DnDsql ddsql) throws SQLException {
        Connection connection = ddsql.connection;
        Statement stmt = connection.createStatement();
        String token = userToken;
        
        Map stats = new HashMap<String, Integer>();
        prepstmt = connection.prepareStatement("select privacy from dndCharacters"
                + " where charID=?");
        prepstmt.setInt(1, charID);
        rs = prepstmt.executeQuery();
        if(!rs.next()) {
            return stats;
        }
        else {
            rs = stmt.executeQuery("select level, hp, constitution, strength,"
                + " dexterity, wisdom, intelligence, charisma, privacy from dndCharacters"
                + " where charID=" + charID);
            while(rs.next()) {
                if(rs.getInt("privacy") == ddsql.PRIVACY_GLOBAL || 
                        TokenMaster.userFromToken(token).equals("admin")) {
                    stats.put("level", rs.getInt("level"));
                    stats.put("hp", rs.getInt("hp"));
                    stats.put("constitution", rs.getInt("constitution"));
                    stats.put("strength", rs.getInt("strength"));
                    stats.put("dexterity", rs.getInt("dexterity"));
                    stats.put("wisdom", rs.getInt("wisdom"));
                    stats.put("intelligence", rs.getInt("intelligence"));
                    stats.put("charisma", rs.getInt("charisma"));
                    return stats;
                }
                else {
                    String userType = TokenMaster.typeFromToken(token);
                    String username = TokenMaster.userFromToken(token);
                    if(rs.getInt("privacy") == ddsql.PRIVACY_PARTY) {
                        if (userType.equals("Player")){
                            prepstmt = connection.prepareStatement("select playerID "+
                                    "from Players where uname=?");
                            prepstmt.setString(1, username);
                            rs2 = prepstmt.executeQuery();
                            if(!rs2.next()) {
                                return stats;
                            }
                            else {
                                rs2 = prepstmt.executeQuery();
                                int playerID=0;
                                while(rs2.next()) {
                                    playerID = rs2.getInt(1);
                                }
                                prepstmt = connection.prepareStatement("select partyID" + 
                                        " from PlayerParty where playerID = ?");
                                prepstmt.setInt(1, playerID);
                                rs2 = prepstmt.executeQuery();
                                ArrayList<Integer> searcherParties = new ArrayList<>();
                                
                                while(rs2.next()) {
                                    searcherParties.add(rs2.getInt(1));
                                }
                                
                                prepstmt = connection.prepareStatement("select partyID " +
                                        "from CharParty where charID = ?");
                                prepstmt.setInt(1, charID);
                                rs2 = prepstmt.executeQuery();
                                ArrayList<Integer> charParties = new ArrayList<>();
                                
                                while(rs2.next()) {
                                    charParties.add(rs2.getInt(1));
                                }
                                
                                boolean sameParty = false;
                                
                                for (Integer i: searcherParties) {
                                    for (Integer j: charParties) {
                                        if (i.equals(j)) {
                                            sameParty = true;
                                            break;
                                        }
                                    }
                                }
                                
                                if (sameParty) {
                                    stats.put("level", rs.getInt("level"));
                                    stats.put("hp", rs.getInt("hp"));
                                    stats.put("constitution", rs.getInt("constitution"));
                                    stats.put("strength", rs.getInt("strength"));
                                    stats.put("dexterity", rs.getInt("dexterity"));
                                    stats.put("wisdom", rs.getInt("wisdom"));
                                    stats.put("intelligence", rs.getInt("intelligence"));
                                    stats.put("charisma", rs.getInt("charisma")); 
                                    return stats;
                                }
                                else {
                                    return stats;
                                }
                                //get parties of searcher + player, see if they coincide
                            }
                        }
                        else {
                            if (userType.equals("DM")) {
                                prepstmt = connection.prepareStatement("select playerID "+
                                    "from Players where uname=?");
                                prepstmt.setString(1, username);
                                rs2 = prepstmt.executeQuery();
                                if(!rs2.next()) {
                                    return stats;
                                }
                                else {
                                    rs2 = prepstmt.executeQuery();
                                    int dmID=0;
                                    while(rs2.next()) {
                                        dmID = rs2.getInt(1);
                                    }
                                    prepstmt = connection.prepareStatement("select partyID" + 
                                            " from Parties where dmID = ?");
                                    prepstmt.setInt(1, dmID);
                                    rs2 = prepstmt.executeQuery();
                                    ArrayList<Integer> searcherParties = new ArrayList<>();

                                    while(rs2.next()) {
                                        searcherParties.add(rs2.getInt(1));
                                    }

                                    prepstmt = connection.prepareStatement("select partyID " +
                                            "from CharParty where charID = ?");
                                    prepstmt.setInt(1, charID);
                                    rs2 = prepstmt.executeQuery();
                                    ArrayList<Integer> charParties = new ArrayList<>();

                                    while(rs2.next()) {
                                        charParties.add(rs2.getInt(1));
                                    }

                                    boolean sameParty = false;

                                    for (Integer i: searcherParties) {
                                        for (Integer j: charParties) {
                                            if (i.equals(j)) {
                                                sameParty = true;
                                                break;
                                            }
                                        }
                                    }

                                    if (sameParty) {
                                        stats.put("level", rs.getInt("level"));
                                        stats.put("hp", rs.getInt("hp"));
                                        stats.put("constitution", rs.getInt("constitution"));
                                        stats.put("strength", rs.getInt("strength"));
                                        stats.put("dexterity", rs.getInt("dexterity"));
                                        stats.put("wisdom", rs.getInt("wisdom"));
                                        stats.put("intelligence", rs.getInt("intelligence"));
                                        stats.put("charisma", rs.getInt("charisma")); 
                                        return stats;
                                    }
                                    else {
                                        return stats;
                                    }
                                    //get parties of searcher + player, see if they coincide
                                }
                            }
                        }
                    }
                }
            }
            return stats;
        }
    }
    
    public Map<String, String> getCharText(DnDsql ddsql) throws SQLException {
        Map text = new HashMap<String, String>();
        Connection connection = ddsql.connection;
        Statement stmt = connection.createStatement();
        String token = userToken;
        prepstmt = connection.prepareStatement("select privacy from dndCharacters"
                + " where charID=?");
        prepstmt.setInt(1, charID);
        rs = prepstmt.executeQuery();
        if(!rs.next()) {
            return text;
        }
        else {
            rs = stmt.executeQuery("select race, dndClass, story, chName,"
                + " privacy from dndCharacters"
                + " where charID=" + charID);
            while(rs.next()) {
                if(rs.getInt("privacy") == ddsql.PRIVACY_GLOBAL || 
                        TokenMaster.userFromToken(token).equals("admin")) {
                    text.put("race", rs.getString("race"));
                    text.put("dndclass", rs.getString("dndClass"));
                    text.put("story", rs.getString("story"));
                    text.put("name", rs.getString("chName"));
                    return text;
                }
                else {
                    String userType = TokenMaster.typeFromToken(token);
                    String username = TokenMaster.userFromToken(token);
                    if(rs.getInt("privacy") == ddsql.PRIVACY_PARTY) {
                        if (userType.equals("Player")){
                            prepstmt = connection.prepareStatement("select playerID "+
                                    "from Players where uname=?");
                            prepstmt.setString(1, username);
                            rs2 = prepstmt.executeQuery();
                            if(!rs2.next()) {
                                return text;
                            }
                            else {
                                rs2 = prepstmt.executeQuery();
                                int playerID=0;
                                while(rs2.next()) {
                                    playerID = rs2.getInt(1);
                                }
                                prepstmt = connection.prepareStatement("select partyID" + 
                                        " from PlayerParty where playerID = ?");
                                prepstmt.setInt(1, playerID);
                                rs2 = prepstmt.executeQuery();
                                ArrayList<Integer> searcherParties = new ArrayList<>();
                                
                                while(rs2.next()) {
                                    searcherParties.add(rs2.getInt(1));
                                }
                                
                                prepstmt = connection.prepareStatement("select partyID " +
                                        "from CharParty where charID = ?");
                                prepstmt.setInt(1, charID);
                                rs2 = prepstmt.executeQuery();
                                ArrayList<Integer> charParties = new ArrayList<>();
                                
                                while(rs2.next()) {
                                    charParties.add(rs2.getInt(1));
                                }
                                
                                boolean sameParty = false;
                                
                                for (Integer i: searcherParties) {
                                    for (Integer j: charParties) {
                                        if (i.equals(j)) {
                                            sameParty = true;
                                            break;
                                        }
                                    }
                                }
                                
                                if (sameParty) {
                                    text.put("race", rs.getString("race"));
                                    text.put("dndclass", rs.getString("dndClass"));
                                    text.put("story", rs.getString("story"));
                                    text.put("name", rs.getString("chName"));
                                    return text;
                                }
                                else {
                                    return text;
                                }
                                //get parties of searcher + player, see if they coincide
                            }
                        }
                        else {
                            if (userType.equals("DM")) {
                                prepstmt = connection.prepareStatement("select playerID "+
                                    "from Players where uname=?");
                                prepstmt.setString(1, username);
                                rs2 = prepstmt.executeQuery();
                                if(!rs2.next()) {
                                    return text;
                                }
                                else {
                                    rs2 = prepstmt.executeQuery();
                                    int dmID=0;
                                    while(rs2.next()) {
                                        dmID = rs2.getInt(1);
                                    }
                                    prepstmt = connection.prepareStatement("select partyID" + 
                                            " from Parties where dmID = ?");
                                    prepstmt.setInt(1, dmID);
                                    rs2 = prepstmt.executeQuery();
                                    ArrayList<Integer> searcherParties = new ArrayList<>();

                                    while(rs2.next()) {
                                        searcherParties.add(rs2.getInt(1));
                                    }

                                    prepstmt = connection.prepareStatement("select partyID " +
                                            "from CharParty where charID = ?");
                                    prepstmt.setInt(1, charID);
                                    rs2 = prepstmt.executeQuery();
                                    ArrayList<Integer> charParties = new ArrayList<>();

                                    while(rs2.next()) {
                                        charParties.add(rs2.getInt(1));
                                    }

                                    boolean sameParty = false;

                                    for (Integer i: searcherParties) {
                                        for (Integer j: charParties) {
                                            if (i.equals(j)) {
                                                sameParty = true;
                                                break;
                                            }
                                        }
                                    }

                                    if (sameParty) {
                                        text.put("race", rs.getString("race"));
                                        text.put("dndclass", rs.getString("dndClass"));
                                        text.put("story", rs.getString("story"));
                                        text.put("name", rs.getString("chName"));
                                        return text;
                                    }
                                    else {
                                        return text;
                                    }
                                    //get parties of searcher + player, see if they coincide
                                }
                            }
                        }
                    }
                }
            }
            return text;
        }
    }
    
    public Map<String, ArrayList<ArrayList<String>>> getCharArrs(DnDsql ddsql) 
            throws SQLException{
        Map arrs = new HashMap<String, ArrayList<ArrayList<String>>>();
        ArrayList<ArrayList<String>> spells = new ArrayList<>();
        ArrayList<ArrayList<String>> items = new ArrayList<>();
        ArrayList<ArrayList<String>> langs = new ArrayList<>();
        Connection connection = ddsql.connection;
        Statement stmt = connection.createStatement();
        String token = userToken;
        
        prepstmt = connection.prepareStatement("select privacy from dndCharacters"
                + " where charID=?");
        prepstmt.setInt(1, charID);
        rs = prepstmt.executeQuery();
        if(!rs.next()) {
            return arrs;
        }
        else {
            rs = stmt.executeQuery("select privacy"
                + " from dndCharacters"
                + " where charID=" + charID);
            while(rs.next()) {
                if(rs.getInt("privacy") == ddsql.PRIVACY_GLOBAL || 
                        TokenMaster.userFromToken(token).equals("admin")) {
                    rs2 = stmt.executeQuery("select spellID from " +
                            "CharSpell where charID = " +  charID);
                    while(rs2.next()) {
                        rs3 = stmt.executeQuery("select * from Spells where " +
                                "spellID = " + rs2.getInt(1));
                        while(rs3.next()) {
                            ArrayList<String> tempSpell = new ArrayList<>();
                            tempSpell.add("" + rs3.getInt("spellID"));
                            tempSpell.add("" + rs3.getString("spellName"));
                            tempSpell.add("" + rs3.getInt("level"));
                            tempSpell.add("" + rs3.getString("description"));
                            spells.add(tempSpell);
                        }
                    }
                    rs2 = stmt.executeQuery("select itemID from CharItem" + 
                            " where charID = " + charID);
                    while(rs2.next()) {
                        rs3 = stmt.executeQuery("select * from Items where " +
                                "itemID = " + rs2.getInt(1));
                        while(rs3.next()) {
                            ArrayList<String> tempItem = new ArrayList<>();
                            tempItem.add("" + rs3.getInt("itemID"));
                            tempItem.add("" + rs3.getString("itemName"));
                            tempItem.add("" + rs3.getString("description"));
                            items.add(tempItem);
                        }
                    }
                    rs2 = stmt.executeQuery("select langName from charLang " + 
                            "where charID = " + charID);
                    while(rs2.next()) {
                            ArrayList<String> tempLang = new ArrayList<>();
                            tempLang.add("" + rs2.getString(1));
                            langs.add(tempLang);
                    }
                    arrs.put("spells", spells);
                    arrs.put("items", items);
                    arrs.put("langs", langs);
                    return arrs;
                }
                else {
                    String userType = TokenMaster.typeFromToken(token);
                    String username = TokenMaster.userFromToken(token);
                    if(rs.getInt("privacy") == ddsql.PRIVACY_PARTY) {
                        if (userType.equals("Player")){
                            prepstmt = connection.prepareStatement("select playerID "+
                                    "from Players where uname=?");
                            prepstmt.setString(1, username);
                            rs2 = prepstmt.executeQuery();
                            if(!rs2.next()) {
                                return arrs;
                            }
                            else {
                                rs2 = prepstmt.executeQuery();
                                int playerID=0;
                                while(rs2.next()) {
                                    playerID = rs2.getInt(1);
                                }
                                prepstmt = connection.prepareStatement("select partyID" + 
                                        " from PlayerParty where playerID = ?");
                                prepstmt.setInt(1, playerID);
                                rs2 = prepstmt.executeQuery();
                                ArrayList<Integer> searcherParties = new ArrayList<>();
                                
                                while(rs2.next()) {
                                    searcherParties.add(rs2.getInt(1));
                                }
                                
                                prepstmt = connection.prepareStatement("select partyID " +
                                        "from CharParty where charID = ?");
                                prepstmt.setInt(1, charID);
                                rs2 = prepstmt.executeQuery();
                                ArrayList<Integer> charParties = new ArrayList<>();
                                
                                while(rs2.next()) {
                                    charParties.add(rs2.getInt(1));
                                }
                                
                                boolean sameParty = false;
                                
                                for (Integer i: searcherParties) {
                                    for (Integer j: charParties) {
                                        if (i.equals(j)) {
                                            sameParty = true;
                                            break;
                                        }
                                    }
                                }
                                
                                if (sameParty) {
                                    rs2 = stmt.executeQuery("select spellID from " +
                                            "CharSpell where charID = " +  charID);
                                    while(rs2.next()) {
                                        rs3 = stmt.executeQuery("select * from Spells where " +
                                                "spellID = " + rs2.getInt(1));
                                        while(rs3.next()) {
                                            ArrayList<String> tempSpell = new ArrayList<>();
                                            tempSpell.add("" + rs3.getInt("spellID"));
                                            tempSpell.add("" + rs3.getString("spellName"));
                                            tempSpell.add("" + rs3.getInt("level"));
                                            tempSpell.add("" + rs3.getString("description"));
                                            spells.add(tempSpell);
                                        }
                                    }
                                    rs2 = stmt.executeQuery("select itemID from CharItem" + 
                                            " where charID = " + charID);
                                    while(rs2.next()) {
                                        rs3 = stmt.executeQuery("select * from Items where " +
                                                "itemID = " + rs2.getInt(1));
                                        while(rs3.next()) {
                                            ArrayList<String> tempItem = new ArrayList<>();
                                            tempItem.add("" + rs3.getInt("itemID"));
                                            tempItem.add("" + rs3.getString("itemName"));
                                            tempItem.add("" + rs3.getString("description"));
                                            items.add(tempItem);
                                        }
                                    }
                                    rs2 = stmt.executeQuery("select langName from charLang " + 
                                            "where charID = " + charID);
                                    while(rs2.next()) {
                                            ArrayList<String> tempLang = new ArrayList<>();
                                            tempLang.add("" + rs2.getString(1));
                                            langs.add(tempLang);
                                    }
                                    arrs.put("spells", spells);
                                    arrs.put("items", items);
                                    arrs.put("langs", langs);
                                    return arrs;
                                }
                                else {
                                    return arrs;
                                }
                                //get parties of searcher + player, see if they coincide
                            }
                        }
                        else {
                            if (userType.equals("DM")) {
                                prepstmt = connection.prepareStatement("select playerID "+
                                    "from Players where uname=?");
                                prepstmt.setString(1, username);
                                rs2 = prepstmt.executeQuery();
                                if(!rs2.next()) {
                                    return arrs;
                                }
                                else {
                                    rs2 = prepstmt.executeQuery();
                                    int dmID=0;
                                    while(rs2.next()) {
                                        dmID = rs2.getInt(1);
                                    }
                                    prepstmt = connection.prepareStatement("select partyID" + 
                                            " from Parties where dmID = ?");
                                    prepstmt.setInt(1, dmID);
                                    rs2 = prepstmt.executeQuery();
                                    ArrayList<Integer> searcherParties = new ArrayList<>();

                                    while(rs2.next()) {
                                        searcherParties.add(rs2.getInt(1));
                                    }

                                    prepstmt = connection.prepareStatement("select partyID " +
                                            "from CharParty where charID = ?");
                                    prepstmt.setInt(1, charID);
                                    rs2 = prepstmt.executeQuery();
                                    ArrayList<Integer> charParties = new ArrayList<>();

                                    while(rs2.next()) {
                                        charParties.add(rs2.getInt(1));
                                    }

                                    boolean sameParty = false;

                                    for (Integer i: searcherParties) {
                                        for (Integer j: charParties) {
                                            if (i.equals(j)) {
                                                sameParty = true;
                                                break;
                                            }
                                        }
                                    }

                                    if (sameParty) {
                                        rs2 = stmt.executeQuery("select spellID from " +
                                                "CharSpell where charID = " +  charID);
                                        while(rs2.next()) {
                                            rs3 = stmt.executeQuery("select * from Spells where " +
                                                    "spellID = " + rs2.getInt(1));
                                            while(rs3.next()) {
                                                ArrayList<String> tempSpell = new ArrayList<>();
                                                tempSpell.add("" + rs3.getInt("spellID"));
                                                tempSpell.add("" + rs3.getString("spellName"));
                                                tempSpell.add("" + rs3.getInt("level"));
                                                tempSpell.add("" + rs3.getString("description"));
                                                spells.add(tempSpell);
                                            }
                                        }
                                        rs2 = stmt.executeQuery("select itemID from CharItem" + 
                                                " where charID = " + charID);
                                        while(rs2.next()) {
                                            rs3 = stmt.executeQuery("select * from Items where " +
                                                    "itemID = " + rs2.getInt(1));
                                            while(rs3.next()) {
                                                ArrayList<String> tempItem = new ArrayList<>();
                                                tempItem.add("" + rs3.getInt("itemID"));
                                                tempItem.add("" + rs3.getString("itemName"));
                                                tempItem.add("" + rs3.getString("description"));
                                                items.add(tempItem);
                                            }
                                        }
                                        rs2 = stmt.executeQuery("select langName from charLang " + 
                                                "where charID = " + charID);
                                        while(rs2.next()) {
                                            rs3 = stmt.executeQuery("select * from Languages where " + 
                                                    "langName = " + rs2.getString("langName"));
                                            while(rs3.next()) {
                                                ArrayList<String> tempLang = new ArrayList<>();
                                                tempLang.add("" + rs3.getString("langName"));
                                                tempLang.add("" + rs3.getString("description"));
                                                langs.add(tempLang);
                                            }
                                        }
                                        arrs.put("spells", spells);
                                        arrs.put("items", items);
                                        arrs.put("langs", langs);
                                        return arrs;
                                    }
                                    else {
                                        return arrs;
                                    }
                                    //get parties of searcher + player, see if they coincide
                                }
                            }
                        }
                    }
                }
            }
        }
        return arrs;
    }
    
    /**
     * Updates D&D character in the database
     * @param newDdch New character information to update the database
     * @param ddsql database connection object
     * @throws SQLException SQL error
     */
    public void updateChar(DnDChar newDdch, DnDsql ddsql) throws SQLException {
        
    }
    
    public void removeSelfFromParty(int partyID, DnDsql ddsql) throws SQLException {
        
    }
    
    @Override
    public void sendPartyRequest(int partyID, int charID, DnDsql ddsql) throws SQLException {
        
    }

    @Override
    public void respondPartyRequest(int partyID, boolean choice, DnDsql ddsql) 
            throws SQLException{
        
    }
    
}
