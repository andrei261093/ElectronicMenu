package com.example.andreiiorga.electronicmenu.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by andreiiorga on 21/06/2017.
 */

public class Product implements Parcelable {
    private int id;
    private String name;
    private String imageUrl;
    private int price;
    private int categoryId;
    private int weight;
    private String shortDescription;
    private String longDescription;
    public Boolean hasBeenSent= false;


    public Product(int id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.imageUrl);
        dest.writeInt(this.price);
        dest.writeInt(this.categoryId);
        dest.writeInt(this.weight);
        dest.writeString(this.shortDescription);
        dest.writeString(this.longDescription);
    }

    protected Product(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.imageUrl = in.readString();
        this.price = in.readInt();
        this.categoryId = in.readInt();
        this.weight = in.readInt();
        this.shortDescription = in.readString();
        this.longDescription = in.readString();
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
