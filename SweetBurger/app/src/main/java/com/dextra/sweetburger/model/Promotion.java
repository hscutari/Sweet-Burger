package com.dextra.sweetburger.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by henriquescutari on 8/8/17.
 */

public class Promotion implements Parcelable {
    @SerializedName("id")
    public long id;

    @SerializedName("name")
    public String name;

    @SerializedName("description")
    public String description;

    @Override
    public int describeContents() {
        return 0;
    }

    protected Promotion(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
    }

    public static final Parcelable.Creator<Promotion> CREATOR = new Parcelable.Creator<Promotion>() {
        @Override
        public Promotion createFromParcel(Parcel source) {
            return new Promotion(source);
        }

        @Override
        public Promotion[] newArray(int size) {
            return new Promotion[size];
        }
    };
}
