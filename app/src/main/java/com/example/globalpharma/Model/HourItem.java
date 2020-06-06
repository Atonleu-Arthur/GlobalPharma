package com.example.globalpharma.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class HourItem implements Parcelable {
    private String mTitle;
    private String mHourValue;
    private String mForm;
    private String mQuantity;

    public HourItem(String title, String hourValue, String form, String quantity) {
        mTitle = title;
        mHourValue = hourValue;
        mForm = form;
        mQuantity = quantity;
    }

    protected HourItem(Parcel in) {
        mTitle = in.readString();
        mHourValue = in.readString();
        mForm = in.readString();
        mQuantity = in.readString();
    }

    public static final Creator<HourItem> CREATOR = new Creator<HourItem>() {
        @Override
        public HourItem createFromParcel(Parcel in) {
            return new HourItem(in);
        }

        @Override
        public HourItem[] newArray(int size) {
            return new HourItem[size];
        }
    };

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getHourValue() {
        return mHourValue;
    }

    public void setHourValue(String hourValue) {
        mHourValue = hourValue;
    }

    public String getForm() {
        return mForm;
    }

    public void setForm(String form) {
        mForm = form;
    }

    public String getQuantity() {
        return mQuantity;
    }

    public void setQuantity(String quantity) {
        mQuantity = quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mHourValue);
        dest.writeString(mForm);
        dest.writeString(mQuantity);
    }
}
