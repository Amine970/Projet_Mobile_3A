package com.example.recyclerviewapi.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.recyclerviewapi.viewmodel.StandViewModel;

public class Stand implements Parcelable
{
    private String stand_name;
    private String stand_user;
    private String stand_image;
    private String user_image;
    private String apparition_part_fk;
    private String stand_power;
    private String stand_id;

    public Stand(String stand_name, String stand_user, String stand_image, String user_image, String stand_power) {
        this.stand_name = stand_name;
        this.stand_user = stand_user;
        this.stand_image = stand_image;
        this.user_image = user_image;
        this.stand_power = stand_power;
    }
    public Stand(StandViewModel stand)
    {
        stand_name = stand.stand_name;
        stand_user = stand.stand_user;
        stand_image = stand.stand_image;
        user_image = stand.user_image;
        stand_power = stand.stand_power;
    }

    protected Stand(Parcel in) {
        stand_name = in.readString();
        stand_user = in.readString();
        stand_image = in.readString();
        user_image = in.readString();
        apparition_part_fk = in.readString();
        stand_power = in.readString();
        stand_id = in.readString();
    }

    public static final Creator<Stand> CREATOR = new Creator<Stand>() {
        @Override
        public Stand createFromParcel(Parcel in) {
            return new Stand(in);
        }

        @Override
        public Stand[] newArray(int size) {
            return new Stand[size];
        }
    };

    public String getStand_name() {
        return stand_name;
    }

    public String getStand_user() {
        return stand_user;
    }

    public String getStand_image() {
        return stand_image;
    }

    public String getUser_image() {
        return user_image;
    }

    public String getApparition_part_fk() {
        return apparition_part_fk;
    }

    public String getStand_power() {
        return stand_power;
    }

    public String getStand_id() {
        return stand_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(stand_name);
        dest.writeString(stand_user);
        dest.writeString(stand_image);
        dest.writeString(user_image);
        dest.writeString(apparition_part_fk);
        dest.writeString(stand_power);
        dest.writeString(stand_id);
    }
}
