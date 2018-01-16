package com.example.vishalverma.androidonlinequizapp.Model;

/**
 * Created by vishalverma on 12/01/18.
 */

public class Category {


    public Category(){}


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getKeyName() {
        return KeyName;
    }

    public void setKeyName(String keyName) {
        KeyName = keyName;
    }

    public boolean isBooleanvalue() {
        return booleanvalue;
    }

    public void setBooleanvalue(boolean booleanvalue) {
        this.booleanvalue = booleanvalue;
    }

    public Category(String name, String image, String keyName, boolean booleanvalue) {
        Name = name;
        Image = image;
        KeyName = keyName;
        this.booleanvalue = booleanvalue;
    }

    private String Name;
    private String Image;
    private String KeyName;
    private boolean booleanvalue;


}
