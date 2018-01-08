/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dndbase;

import java.util.Map;
import java.util.ArrayList;

/**
 *
 * @author vibhusomeshwar
 */
public class DnDChar {
    private int strength, wisdom, charisma, intelligence, dexterity, constitution, hp, level;
    private String name, race, dndclass, alignment, story;
    private ArrayList<ArrayList<String>> spells, items;
    private ArrayList<String> languages;
    public DnDChar(Map<String, Integer> dndStatMap, Map<String, String> dndTextMap,
            Map<String, ArrayList<ArrayList<String>>> dndArrMap, Map<String, ArrayList<String>> dndLangMap) {
        this.alignment = dndTextMap.getOrDefault("alignment", "True Neutral");
        this.charisma = dndStatMap.getOrDefault("charisma", 0);
        this.constitution = dndStatMap.getOrDefault("constitution", 0);
        this.dexterity = dndStatMap.getOrDefault("dexterity", 0);
        this.dndclass = dndTextMap.getOrDefault("dndclass", "None");
        this.hp = dndStatMap.getOrDefault("hp", 0);
        this.story = dndTextMap.getOrDefault("story", "Dark mysterious past");
        
        ArrayList<ArrayList<String>> defaultItems = new ArrayList<>();
        ArrayList<String> defaultItem1d = new ArrayList<>();
        defaultItem1d.add("None");
        defaultItem1d.add("No description");
        defaultItems.add(defaultItem1d);
        this.items = dndArrMap.getOrDefault("items", defaultItems);
        
        this.intelligence = dndStatMap.getOrDefault("intelligence", 0);
        this.wisdom = dndStatMap.getOrDefault("wisdom", 0);
        
        ArrayList<ArrayList<String>> defaultSpells = new ArrayList<>();
        ArrayList<String> defaultSpell1d = new ArrayList<>();
        defaultSpell1d.add("None");
        defaultSpell1d.add("No description");
        defaultSpell1d.add("1");
        defaultSpells.add(defaultSpell1d);
        this.spells = dndArrMap.getOrDefault("spells", defaultSpells);
        
        this.strength = dndStatMap.getOrDefault("strength", 0);
        
        ArrayList<String> defaultLangs = new ArrayList<>();
        defaultLangs.add("Common");
        this.languages = dndLangMap.getOrDefault("languages", defaultLangs);
        
        this.level = dndStatMap.getOrDefault("level", 0);
        this.race = dndTextMap.getOrDefault("race", "Human");
        this.name = dndTextMap.getOrDefault("name", "Sir Edgar the Deluded");
    }
    

    public int getCharisma() {
        return charisma;
    }

    public int getConstitution() {
        return constitution;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getHp() {
        return hp;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getLevel() {
        return level;
    }

    public int getStrength() {
        return strength;
    }

    public int getWisdom() {
        return wisdom;
    }

    public String getAlignment() {
        return alignment;
    }

    public String getDndclass() {
        return dndclass;
    }

    public ArrayList<ArrayList<String>> getItems() {
        return items;
    }

    public ArrayList<String> getLanguages() {
        return languages;
    }

    public String getName() {
        return name;
    }

    public String getRace() {
        return race;
    }

    public ArrayList<ArrayList<String>> getSpells() {
        return spells;
    }
    
    public String getStory() {
        return story;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public void setDndclass(String dndclass) {
        this.dndclass = dndclass;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public void setItems(ArrayList<ArrayList<String>> items) {
        this.items = items;
    }

    public void setLanguages(ArrayList<String> languages) {
        this.languages = languages;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public void setSpells(ArrayList<ArrayList<String>> spells) {
        this.spells = spells;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }
    
    public void setStory(String story) {
        this.story = story;
    }
}
