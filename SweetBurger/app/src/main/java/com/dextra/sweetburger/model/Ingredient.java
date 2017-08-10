package com.dextra.sweetburger.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by henriquescutari on 8/8/17.
 */

public class Ingredient implements Parcelable {

    @SerializedName("id")
    public long id;

    @SerializedName("name")
    public String name;

    @SerializedName("price")
    public double price;

    @SerializedName("image")
    public String image;

    protected Ingredient(){}

    protected Ingredient(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.price = in.readDouble();
        this.image = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeDouble(this.price);
        dest.writeString(this.image);
    }

    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
