package com.example.globalpharma.Model;

import android.graphics.Bitmap;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Medicine {
    public Bitmap getImage() {
        return mImage;
    }

    public void setImage(Bitmap image) {
        mImage = image;
    }

    private Bitmap mImage;
    private String mId;
    private String mName;
    private String mPathology;
    private String mForm;
    private int mTotalQuantity;
    private int mQuantityPerDay;
    private String mFrequence;
    private String mImageName;
    private String mDurationType;
    private String mStartingDate;
    private String mEndingDate;
    private String mMoment;
    private int mDurationInDays;
    private HourItem mHourItem;
    private List<HourItem> mHourItems;
    private List<String> mDays;
    private List<String> mDayOfMonth;

    public Medicine(Bitmap image, String name, String pathology, String form, int totalQuantity, int quantityPerDay, String frequence, String durationType, String startingDate, String moment, int durationInDays, HourItem hourItem) {
        mId = UUID.randomUUID().toString().replace("-", "");
        mImage = image;
        mName = name;
        mPathology = pathology;
        mForm = form;
        mTotalQuantity = totalQuantity;
        mQuantityPerDay = quantityPerDay;
        mFrequence = frequence;
        mDurationType = durationType;
        mStartingDate = startingDate;
        mMoment = moment;
        mDurationInDays = durationInDays;
        mHourItem = hourItem;
    }

    public int getQuantityPerDay() {
        return mQuantityPerDay;
    }

    public void setQuantityPerDay(int quantityPerDay) {
        mQuantityPerDay = quantityPerDay;
    }

    public String getStartingDate() {
        return mStartingDate;
    }

    public void setStartingDate(String startingDate) {
        mStartingDate = startingDate;
    }

    public String getEndingDate() {
        Date date = new Date();
        date.setDate((int)Date.parse(mStartingDate));
        date.setDate(date.getDate() * mDurationInDays);
        return String.valueOf(date.getDate());
    }

    public void setEndingDate(String endingDate) {
        mEndingDate = endingDate;
    }

    public List<String> getDays() {
        return mDays;
    }

    public void setDays(List<String> days) {
        mDays = days;
    }

    public List<String> getDayOfMonth() {
        return mDayOfMonth;
    }

    public void setDayOfMonth(List<String> dayOfMonth) {
        mDayOfMonth = dayOfMonth;
    }

    public Medicine(Bitmap image, String name, String pathology, String form, int totalQuantity, int quantityPerDay, String frequence, String durationType, String startingDate, String moment, int durationInDays, List<HourItem> hourItems) {
        mId = UUID.randomUUID().toString().replace("-", "");
        mImage = image;
        mName = name;
        mPathology = pathology;
        mForm = form;
        mTotalQuantity = totalQuantity;
        mQuantityPerDay = quantityPerDay;
        mFrequence = frequence;
        mDurationType = durationType;
        mStartingDate = startingDate;
        mMoment = moment;
        mDurationInDays = durationInDays;
        mHourItems = hourItems;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPathology() {
        return mPathology;
    }

    public void setPathology(String pathology) {
        mPathology = pathology;
    }

    public String getForm() {
        return mForm;
    }

    public void setForm(String form) {
        mForm = form;
    }

    public int getTotalQuantity() {
        return mTotalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        mTotalQuantity = totalQuantity;
    }

    public String getFrequence() {
        return mFrequence;
    }

    public void setFrequence(String frequence) {
        mFrequence = frequence;
    }

    public String getDurationType() {
        return mDurationType;
    }

    public void setDurationType(String durationType) {
        mDurationType = durationType;
    }

    public String getMoment() {
        return mMoment;
    }

    public void setMoment(String moment) {
        mMoment = moment;
    }

    public int getDurationInDays() {
        return mDurationInDays;
    }

    public void setDurationInDays(int durationInDays) {
        mDurationInDays = durationInDays;
    }

    public HourItem getHourItem() {
        return mHourItem;
    }

    public void setHourItem(HourItem hourItem) {
        mHourItem = hourItem;
    }

    public List<HourItem> getHourItems() {
        return mHourItems;
    }

    public void setHourItems(List<HourItem> hourItems) {
        mHourItems = hourItems;
    }

    public String getMissingTime() {
        Date date = new Date();
        date.setDate((int)Date.parse(mStartingDate));

        Date date1 = new Date();
        date1.setDate(date.getDate() + mDurationInDays);
        return String.valueOf(date1.getDate() - date.getDate());
    }

    public String getDate() {
        return mStartingDate;
    }
}
