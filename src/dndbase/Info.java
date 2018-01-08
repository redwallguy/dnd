/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dndbase;

/**
 *
 * @author vibhusomeshwar
 */
public class Info {
    //combat table constants
    public static final String[] charClasses = {"Cleric","Druid","Monk","Magic-User","Illusionist",
            "Fighter","Paladin","Ranger","Bard","Level Zero Halfing/Human","Thief","Assassin"};
    public static final String[] races = {"Elf", "Dwarf", "Human", "Gnome", "Half-Elf",
            "Half-Orc", "Halfling"};
    public static final String[] alignments = {"Lawful Good", "Lawful Neutral", "Lawful Evil",
            "Neutral Good", "True Neutral", "Neutral Evil", "Chaotic Good", "Chaotic Neutral", "Chaotic Evil"};
    public static final int[] opponentArmorClassCombat = {-10,-9,-8,-7,-6,-5,-4,-3,-2,-1,0,1,2,3,4,
            5,6,7,8,9,10};
    public static final String[] levelOfCDMCombat = {"1-3","4-6","7-9","10-12","13-15","16-18",
            "19+"};
    public static final int[][] CDMCombat = {{25,23,21,20,20,20,19},{24,22,20,20,20,19,18},
            {23,21,20,20,20,18,17},{22,20,20,20,19,17,16},{21,20,20,20,18,16,15},
            {20,20,20,19,17,15,14},{20,20,20,18,16,14,13},{20,20,19,17,15,13,12},
            {20,20,18,16,14,12,11},{20,19,17,15,13,11,10},{20,18,16,14,12,10,9},
            {19,17,15,13,11,9,8},{18,16,14,12,10,8,7},{17,15,13,11,9,7,6},
            {16,14,12,10,8,6,5},{15,13,11,9,7,5,4},{14,12,10,8,6,4,3},
            {13,11,9,7,5,3,2},{12,10,8,6,4,2,1},{11,9,7,5,3,1,0},{10,8,6,4,2,0,-1}};
    public static final String[] levelOfMICombat = {"1-5","6-10","11-15","16-20","21+"};
    public static final int[][] MICombat = {{26,24,21,20,20},{25,23,20,20,20},{24,22,20,20,19},
            {23,21,20,20,18},{22,20,20,19,17},{21,20,20,18,16},{20,20,20,17,15},{20,20,19,16,14},
            {20,20,18,15,13},{20,20,17,14,12},{20,19,16,13,11},{20,18,15,12,10},{19,17,14,11,9},
            {18,16,13,10,8},{17,15,12,9,7},{16,14,11,8,6},{15,13,10,7,5},{14,12,9,6,4},
            {13,11,8,5,3},{12,10,7,4,2},{11,9,6,3,1}};
    public static final String[] levelofFPRBZHHCombat = {"0","1-2","3-4","5-6","7-8","9-10",
            "11-12","13-14","15-16","17+"};
    public static final int[][] FPRBZHHCombat = {{26,25,23,21,20,20,20,18,16,14},
            {25,24,22,20,20,20,19,17,15,13},{24,23,21,20,20,20,18,16,14,12},
            {23,22,20,20,20,19,17,15,13,11},{22,21,20,20,20,18,16,14,12,10},
            {21,20,20,20,19,17,15,13,11,9},{20,20,20,20,18,16,14,12,10,8},
            {20,20,20,19,17,15,13,11,9,7},{20,20,20,18,16,14,12,10,8,6},
            {20,20,19,17,15,13,11,9,7,5},{20,20,18,16,14,12,10,8,6,4},
            {20,19,17,15,13,11,9,7,5,3},{19,18,16,14,12,10,8,6,4,2},{18,17,15,13,11,9,7,5,3,1},
            {17,16,14,12,10,8,6,4,2,0},{16,15,13,11,9,7,5,3,1,-1},{15,14,12,10,8,6,4,2,0,-2},
            {14,13,11,9,7,5,3,1,-1,-3},{13,12,10,8,6,4,2,0,-2,-4},{12,11,9,7,5,3,1,-1,-3,-5},
            {11,10,8,6,4,2,0,-2,-4,-6}};
    public static final String[] levelofTACombat = {"1-4","5-8","9-12","13-16","17-20","21+"};
    public static final int[][] TACombat = {{26,24,21,20,20,20},{25,23,20,20,20,19},
            {24,22,20,20,20,18},{23,21,20,20,19,17},{22,20,20,20,18,16},{21,20,20,19,17,15},
            {20,20,20,18,16,14},{20,20,19,17,15,13},{20,20,18,16,14,12},{20,20,17,15,13,11},
            {20,19,16,14,12,10},{20,18,15,13,11,9},{19,17,14,12,10,8},{18,16,13,11,9,7},
            {17,15,12,10,8,6},{16,14,11,9,7,5},{15,13,10,8,6,4},{14,12,9,7,5,3},{13,11,8,6,4,2},
            {12,10,7,5,3,1},{11,9,6,4,2,0}};
    //follows 2x,3x,4x,5x,5x,5x dmg from surprise backstab (dmg, not chance, multiplier)
    //
    //Monster matrix
    //1-1 = 1d6 - 1 pip
    public static final String[] levelOfMonsterCombat = {"< 1-1","1-1","1", "1+","2-3+","4-5+",
            "6-7+","8-9+","10-11+","12-13+","14-15+","16+"};
    public static final int[][] MonsterCombat = {{26,25,24,23,21,20,20,20,20,19,18,17},
            {25,24,23,22,20,20,20,20,19,18,17,16},{24,23,22,21,20,20,20,20,18,17,16,15},
            {23,22,21,20,20,20,20,19,17,16,15,14},{22,21,20,20,20,20,19,18,16,15,14,13},
            {21,20,20,20,20,20,18,17,15,14,13,12},{20,20,20,20,20,19,17,16,14,13,12,11},
            {20,20,20,20,19,18,16,15,13,12,11,10},{20,20,20,20,18,16,15,14,12,11,10,9},
            {20,20,20,19,17,16,14,13,11,10,9,8},{20,20,19,18,16,15,13,12,10,9,8,7},
            {20,19,18,17,15,14,12,11,9,8,7,6},{19,18,17,16,14,13,11,10,8,7,6,5},
            {18,17,16,15,13,12,10,9,7,6,5,4},{17,16,15,14,12,11,9,8,6,5,4,3},
            {16,15,14,13,11,10,8,7,5,4,3,2},{15,14,13,12,10,9,7,6,4,3,2,1},
            {14,13,12,11,9,8,6,5,3,2,1,0},{13,12,11,10,8,7,5,4,2,1,0,-1},
            {12,11,10,9,7,6,4,3,1,0,-1,-2},{11,10,9,8,6,5,3,2,0,-1,-2,-3}};
    //Clerics and the undead
    public static final String[] listOfUndead = {"Skeleton","Zombie","Ghoul","Shadow","Wight",
            "Ghast","Wraith","Mummy","Spectre","Vampire","Ghost","Lich","Special"};
    public static final String[] levelOfCleric = {"1","2","3","4","5","6","7","8",
            "9-13","14+"};
    public static final int[][] UndeadMatrix = {{10,7,4,-1,-1,-2,-2,-2,-2,-2},
            {13,10,7,-1,-1,-2,-2,-2,-2,-2},{16,13,10,4,-1,-1,-2,-2,-2,-2},
            {19,16,13,7,4,-1,-1,-2,-2,-2},{20,19,16,10,7,4,-1,-1,-2,-2},
            {-3,20,19,13,10,7,4,-1,-1,-2},{-3,-3,20,16,13,10,7,4,-1,-2},
            {-3,-3,-3,20,16,13,10,7,4,-1},{-3,-3,-3,-3,20,16,13,10,7,-1},
            {-3,-3,-3,-3,-3,20,16,13,10,4},{-3,-3,-3,-3,-3,-3,20,16,13,7},
            {-3,-3,-3,-3,-3,-3,-3,19,16,10},{-3,-3,-3,-3,-3,-3,-3,20,19,13}};
    //Saving throws matrix
    public static final String[] listOfEventsSaved = {"Paralyzation, Poison, or Death Magic",
            "Petrification or Polymorph","Rod, Staff, or Wand", "Breath Weapon", "Spell"};
    public static final String[] levelofCSave = {"1-3","4-6","7-9","10-12","13-15","16-18","19+"};
    public static final int[][] CSave = {{10,13,14,16,15},{9,12,13,15,14},{7,10,11,13,12},
            {6,9,10,12,11},{5,8,9,11,10},{4,7,8,10,9},{2,5,6,8,7}};
    public static final String[] levelofFSave = {"0","1-2","3-4","5-6","7-8","9-10","11-12",
            "13-14","15-16","17+"};
    public static final int[][] FSave = {{16,17,18,20,19},{14,15,16,17,17},{13,14,15,16,16},
            {11,12,13,13,14},{10,11,12,13,13},{8,9,10,9,11},{7,8,9,8,10},{5,6,7,5,8},{4,5,6,4,7},
            {3,4,5,4,6}};
    public static final String[] levelofMSave = {"1-5","6-10","11-15","16-20","21+"};
    public static final int[][] MSave = {{14,13,11,15,12},{13,11,9,13,10},{11,9,7,11,8},
            {10,7,5,9,6},{8,5,3,7,4}};
    public static final String[] levelofTSave = {"1-4","5-8","9-12","13-16","17-20","21+"};
    public static final int[][] TSave = {{13,12,14,16,15},{12,11,12,15,13},{11,10,10,14,11},
            {10,9,8,13,9},{9,8,6,12,7},{8,7,4,11,5}};
    //Item saving throws matrix
    public static final String[] listOfItemEventsSaved = {"Acid","Crushing Blow","Normal Blow",
            "Disintegrate","Fall","Fireball","Magical Fire","Normal Fire","Frost","Lightning",
            "Electricity"};
    public static final String[] listOfItemsSaved = {"Bone or Ivory","Ceramic","Cloth",
            "Crystal or Vial","Glass","Leather or Book","Liquid","Metal, hard",
            "Metal, soft or Jewelwry","Mirror","Parchment or Paper","Stone, small or Gem",
            "Wood or Rope, thin","Wood or Rope, thick"};
    public static final int[][] itemSave = {{11,16,10,20,6,17,9,3,2,8,1},{4,18,12,19,11,5,3,2,4,2,1},
            {12,6,3,20,2,20,16,13,1,18,1},{6,19,14,20,13,10,6,3,7,15,5},{5,20,15,20,14,11,7,4,6,17,1},
            {10,4,2,20,1,13,6,4,3,13,1},{15,0,0,20,0,15,14,13,12,18,15},{7,6,2,17,2,6,2,1,1,11,1},
            {13,14,9,19,4,18,13,5,1,16,1},{12,20,15,20,13,14,9,5,6,18,1},{16,11,6,20,0,25,21,18,2,20,1},
            {3,17,7,18,4,7,3,2,1,14,2},{9,13,6,20,2,15,11,9,1,10,1},{8,10,3,19,1,11,7,5,1,12,1}};
    //Potion miscibility table
    public static final String[] PotionRoll = {"01","02-03","04-08","09-15","16-25","26-35","36-90","91,99","00"};
    public static final String[] PotionMixEffect = {"EXPLOSION! Internal damage is 6-60 hp; those " +
            "within a 5'' radius take 1-10 hp if mixed externally; all in a 10' radius take 4-24 " +
            "hp, no save", "Lethal poison results, and imbiber is dead; if externally mixed, a " +
            "poison gas cloud of 10’ diameter results, and all within it must save versus poiso" +
            "n or die.", "Mild poison which causes nausea and loss of 1 point each of strength and " +
            "dexterity for 5-20 rounds, no saving throw possible; one potion is cancelled, the other" +
            " is at half strength and duration. (Use random determination for which is cancelled and " +
            "which is at half efficacy.)", "Immiscible. Both potions totally destroyed, as one cancel" +
            "led the other.", "Immiscible. One potion cancelled, but the other remains normal " +
            "(random selection).", "Immiscible result which causes both potions to be at half normal" +
            " efficacy when consumed.", "Miscible. Potions work normally unless their effects are " +
            "contradictory, e.g. diminution and growth, which will simply cancel each other.", "Compat" +
            "ible result which causes one potion (randomly determined) to have 150% normal efficacy." +
            " (You must determine if both effect and duration are permissible," +
            "or if only the duration should be extended.)", "DISCOVERY! The admixture of the two pot" +
            "ions has caused a special formula which will cause one of the two potions only to funct" +
            "ion, but its effects will be permanent upon the imbiber. "};
    //Treasure - monetary
    public static final String[] MoneyTreasureRoll = {"1-2","3-5","6-10","11-12","13-15","16-17",
            "18","19","20"};
    public static final String[] MoneyTreasureDesc = {"20,000-80,000 copper pieces (2d4), 20,000-" +
            "50,000 silver pieces (d4 +1)", "5,000-30,000 electrum pieces (5d6)", "3,000-18,000 gold" +
            " pieces (3d6)", "500-2,000 platinum pieces (5d4)", "10-100gems(d10X 10)", "5-50 pieces " +
            "of jewelry (5d1O)", "Roll twice, discounting rolls above 17", "Roll thrice, discounting" +
            " rolls above 17", "Each monetary item above"};
    //Treasure - magic
    public static final String[] MagicTreasureRoll = {"1-5","6-8","9-12","13-14","15-18","19","20"};
    public static final String[] MagicTreasureDesc = {"Any item rolled on Magic ltem Table, plus 4 " +
            "Potions","Any 2 items rolled on Magic ltem Table","1 Sword, 1 Armor or Shield, 1 " +
            "Miscellaneous Weapon","Any 3 items, no Sword or Potions","Any 6 Potions and any 6 Scro" +
            "lls","Any 4 items, 1 is a Ring, 1 is a Rod","Any 5 items, 1 is a Rod, 1 is Miscellaneous Magic"};
    //Treasure - combined
    public static final String[] CombinedTreasureRoll = {"01-20","21-40","41-55","56-65","66-75",
            "76-80","81-85","86-90","91-96","97-00"};
    public static final String[] CombinedTreasureDesc = {"1-2 Monetary Treasure and 1-5 MagicTreasu" +
            "re","6-10 Monetary Treasure and 1-5 Magic Treasure","3-5 & 6-10 Monetary Treasure and " +
            "1-5 & 15-18 Magic Treasure","1-2, 3-5 & 6-10 Monetary Treasure and 9-12 & 13-14 Magic Treas" +
            "ure","6-10 & 11-12 Monetary Treasure and 6-8 & 15-18 Magic Treasure","3-5, 6-10, 11-12 &" +
            " 16-17 Monetary Treasure and 1-5 & 9-12 Magic Treasure","20 Monetary Treasure and map " +
            "to 1-5 Magic Treasure","20 Monetary Treasure and map to 19 Magic Treasure","Map to 1-2 & " +
            "3-5 Monetary Treasure, 20 Magic Treasure on hand","Map to 11-12 8 13-15 Monetary Treasu" +
            "re plus 15-18 Magic Treasure, 20 Magic Treasure on hand"};
}
