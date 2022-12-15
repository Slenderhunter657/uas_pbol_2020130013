/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectpbol;

/**
 *
 * @author ASUS
 */
public class modelItem {
    private int itemId,itemHappy,itemHunger;
    private boolean consumable;

    public boolean isConsumable() {
        return consumable;
    }

    public void setConsumable(boolean consumable) {
        this.consumable = consumable;
    }

    public int getItemHappy() {
        return itemHappy;
    }

    public void setItemHappy(int itemHappy) {
        this.itemHappy = itemHappy;
    }

    public int getItemHunger() {
        return itemHunger;
    }

    public void setItemHunger(int itemHunger) {
        this.itemHunger = itemHunger;
    }
    private String itemName;
    private float itemWeight;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public float getItemWeight() {
        return itemWeight;
    }

    public void setItemWeight(float itemWeight) {
        this.itemWeight = itemWeight;
    }
    
    
}
