package com.fpuente.simple_ripley.model;

public class Product {

    private String name;
    private String url_img;
    private String sku;
    private String trademark;
    private int disc;
    private String normal_price;
    private String internet_price;
    private String ripley_card_price;


    public Product() {
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getTrademark() {
        return trademark;
    }

    public void setTrademark(String trademark) {
        this.trademark = trademark;
    }

    public int getDisc() {
        return disc;
    }

    public void setDisc(int disc) {
        this.disc = disc;
    }

    public String getNormal_price() {
        return normal_price;
    }

    public void setNormal_price(String normal_price) {
        this.normal_price = normal_price;
    }

    public String getInternet_price() {
        return internet_price;
    }

    public void setInternet_price(String internet_price) {
        this.internet_price = internet_price;
    }

    public String getRipley_card_price() {
        return ripley_card_price;
    }

    public void setRipley_card_price(String ripley_card_price) {
        this.ripley_card_price = ripley_card_price;
    }
}
