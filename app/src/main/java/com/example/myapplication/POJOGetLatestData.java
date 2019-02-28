package com.example.myapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class POJOGetLatestData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("right_word")
    @Expose
    private String rightWord;
    @SerializedName("wrong_word")
    @Expose
    private String wrongWord;

    @SerializedName("last_update")
    @Expose
    private String lastUpdate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRightWord() {
        return rightWord;
    }

    public void setRightWord(String rightWord) {
        this.rightWord = rightWord;
    }

    public String getWrongWord() {
        return wrongWord;
    }

    public void setWrongWord(String wrongWord) {
        this.wrongWord = wrongWord;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

}
