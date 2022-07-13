package com.zeelearn.ekidzee.mlzs.model;


public class MenuInfo {

    String title;
    String bgColor;
    String fgColor;
    int backgoundIconPath;
    int menuId;
    int defImage;
    boolean selection;


    public boolean isSelection() {
        return selection;
    }

    public void setSelection(boolean selection) {
        this.selection = selection;
    }

    public MenuInfo(String title, String bgColor, String fgColor, int backgoundIconPath, int menuId) {
        this.title = title;
        this.bgColor = bgColor;
        this.fgColor = fgColor;
        this.backgoundIconPath = backgoundIconPath;
        //this.backgoundIconPath = "https://www.merchant.rewallet.co.uk/UAT/DEAL_IMAGES/"+backgoundIconPath;
        this.menuId = menuId;
    }

    public int getDefImage() {
        return defImage;
    }

    public MenuInfo(String title, String bgColor, String fgColor, int backgoundIconPath, int menuId, int defImage) {
        this.title = title;
        this.bgColor = bgColor;
        this.fgColor = fgColor;
        this.backgoundIconPath = backgoundIconPath;
        this.menuId = menuId;
        this.defImage = defImage;
    }

    public int getMenuId() {
        return menuId;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public String getFgColor() {
        return fgColor;
    }

    public void setFgColor(String fgColor) {
        this.fgColor = fgColor;
    }

    public int getBackgoundIconPath() {
        return backgoundIconPath;
    }


    public void setBackgoundIconPath(int backgoundIconPath) {
        this.backgoundIconPath = backgoundIconPath;
    }

    public MenuInfo(String title, String bgColor, int fgColor) {
        this.title = title;
        this.bgColor = bgColor;
        this.backgoundIconPath = fgColor;
    }
}
