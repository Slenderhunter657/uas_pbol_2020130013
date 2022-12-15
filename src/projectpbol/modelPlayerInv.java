/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectpbol;

/**
 *
 * @author ASUS
 */
public class modelPlayerInv {
    private int player_id,item_id,item_quantity;
    private String item_name;
    private int item_hp, item_hg;
    private float item_w;

    public float getItem_w() {
        return item_w;
    }

    public void setItem_w(float item_w) {
        this.item_w = item_w;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getItem_hp() {
        return item_hp;
    }

    public void setItem_hp(int item_hp) {
        this.item_hp = item_hp;
    }

    public int getItem_hg() {
        return item_hg;
    }

    public void setItem_hg(int item_hg) {
        this.item_hg = item_hg;
    }

    public int getItem_quantity() {
        return item_quantity;
    }

    public void setItem_quantity(int item_quantity) {
        this.item_quantity = item_quantity;
    }

    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }
    
}
