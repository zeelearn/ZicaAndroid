package com.zeelearn.ekidzee.mlzs.retrofit.request.login;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "menus")
public class MenuModel extends Model {
    @Column(name = "menuid")
    int Menu_Id;
    @Column(name = "menu_text")
    String MenuText;

    public MenuModel() {
    }

    public MenuModel(int menu_Id, String menuText) {
        Menu_Id = menu_Id;
        MenuText = menuText;
    }

    public int getMenu_Id() {
        return Menu_Id;
    }

    public void setMenu_Id(int menu_Id) {
        Menu_Id = menu_Id;
    }

    public String getMenuText() {
        return MenuText;
    }

    public void setMenuText(String menuText) {
        MenuText = menuText;
    }
}
