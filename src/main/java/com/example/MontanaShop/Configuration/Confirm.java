package com.example.MontanaShop.Configuration;

public class Confirm {
    private String key;
    private String link;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Confirm(String key, String link) {
        this.key = key;
        this.link = link;
    }
}
