package com.zeelearn.ekidzee.mlzs.retrofit.request.login;

public class MenuRequest {

    int Menu_Id;
    String MenuText;

    public MenuRequest(int menu_Id, String menuText) {
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
