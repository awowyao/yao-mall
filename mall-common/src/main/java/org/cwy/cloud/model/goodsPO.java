package org.cwy.cloud.model;


public class goodsPO {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(String goods_type) {
        this.goods_type = goods_type;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getInventory() {
        return Inventory;
    }

    public void setInventory(String inventory) {
        Inventory = inventory;
    }

    public Integer getStore_id() {
        return store_id;
    }

    public void setStore_id(Integer store_id) {
        this.store_id = store_id;
    }

    @Override
    public String toString() {
        return "goodsPO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", goods_type='" + goods_type + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", photo='" + photo + '\'' +
                ", price=" + price +
                ", Inventory='" + Inventory + '\'' +
                ", store_id=" + store_id +
                '}';
    }

    Integer id;
    String title;
    String goods_type;
    String synopsis;
    String photo;
    double price;
    String Inventory;
    Integer store_id;
}
