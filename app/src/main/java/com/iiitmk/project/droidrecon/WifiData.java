package com.iiitmk.project.droidrecon;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class WifiData implements Parcelable {
    String ip;
    List<WifiPort> wifiPortList;

    protected WifiData(Parcel in) {
        ip = in.readString();
        wifiPortList = in.createTypedArrayList(WifiPort.CREATOR);
    }

    public static final Creator<WifiData> CREATOR = new Creator<WifiData>() {
        @Override
        public WifiData createFromParcel(Parcel in) {
            return new WifiData(in);
        }

        @Override
        public WifiData[] newArray(int size) {
            return new WifiData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ip);
        parcel.writeTypedList(wifiPortList);
    }

    public WifiData() {
    }

    public WifiData(String ip, List<WifiPort> wifiPortList) {
        this.ip = ip;
        this.wifiPortList = wifiPortList;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public List<WifiPort> getWifiPortList() {
        return wifiPortList;
    }

    public void setWifiPortList(List<WifiPort> wifiPortList) {
        this.wifiPortList = wifiPortList;
    }

    public static Creator<WifiData> getCREATOR() {
        return CREATOR;
    }
}
