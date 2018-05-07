package com.example.justin.silent_assassin;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rozmina on 3/20/2018.
 */

public class User implements Parcelable
{
    public String IPAddress;
    public String UserName;
    public String Password;
    public double Lat;
    public double Long;
    public double TLat;
    public double TLong;
    public int money;
    public int evader, smokescreen,counter,quickescape;
    public String target;
    public String isTargeted;

    public User()
    {

    }

    public User(String name, String pass)
    {
        IPAddress = "0";
        UserName = name;
        Password = pass;
        target = "";
        Lat = 0;
        Long = 0;
        TLat = 0;
        TLong = 0;
        money = 2000;
        evader = 0;
        smokescreen = 0;
        counter = 0;
        quickescape = 0;
        isTargeted = "n";
    }

    public User(String IPAddress, double Lat, double Long, String name)
    {
        this.IPAddress = IPAddress;
        this.Lat = Lat;
        this.Long = Long;
        this.UserName = name;
    }


    protected User(Parcel in) {
        IPAddress = in.readString();
        UserName = in.readString();
        Password = in.readString();
        Lat = in.readDouble();
        Long = in.readDouble();
        TLat = in.readDouble();
        TLong = in.readDouble();
        money = in.readInt();
        evader = in.readInt();
        smokescreen = in.readInt();
        counter = in.readInt();
        quickescape = in.readInt();
        target = in.readString();
        isTargeted = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public void ChangeLatAndLong(double lat, double lo)
    {
        this.Lat = lat;
        this.Long = lo;
    }

    public void SetTarget(String name, double La, double Lo)
    {
        this.target = name;
        this.TLat = La;
        this.TLong = Lo;
    }

    public void SetTargetLatLong(double La, double Lo)
    {
        this.TLat = La;
        this.TLong = Lo;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(IPAddress);
        parcel.writeString(UserName);
        parcel.writeString(Password);
        parcel.writeDouble(Lat);
        parcel.writeDouble(Long);
        parcel.writeDouble(TLat);
        parcel.writeDouble(TLong);
        parcel.writeInt(money);
        parcel.writeInt(evader);
        parcel.writeInt(smokescreen);
        parcel.writeInt(counter);
        parcel.writeInt(quickescape);
        parcel.writeString(target);
        parcel.writeString(isTargeted);
    }
    //location-not sure if this is in latitude/longitude/not sure how the Google API works yet
}