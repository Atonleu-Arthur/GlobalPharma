package com.example.globalpharma.Model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.UploadTask;

import java.util.List;
import java.util.UUID;

public class Medication implements Parcelable {
    private String mId;
    private String mMedicationName;
    private String mTreatedPart;
    private String mMedicationForm;
    private String mMedicationQuantityText;
    private String mStartingDate;
    private String mTreatmentDuration;
    private String mTakingMoment;
    private String mTotalQuantity;
    private int mTotalMedecines;
    private List<HourItem> mHourItems;
    private String mMedicationHour;
    private String mDownloadUrl;
    private String mSerializedHourItem;
    @Nullable private String mMedicationPrecision;
    @Nullable private String mMissingTime;
    private int mMedicationImage;
    private int mTotalMedecinesPerDay;
    private Bitmap mMedicationImageBitmap;

    public Medication() {
        this.mId = UUID.randomUUID().toString();
    }

    //Constructeur pour un item dans la liste de traitements
    public Medication(String medicationName, String treatedPart,
                      String medicationForm, String startingDate,
                      String totalQuantity, List<HourItem> hourItems,
                      @Nullable String takingMoment,
                      @Nullable Bitmap medicationImageBitmap) {
        mId = UUID.randomUUID().toString().replace("-", "");
        mMedicationName = medicationName;
        mTreatedPart = treatedPart;
        mHourItems = hourItems;
        mMedicationForm = medicationForm;
        for (HourItem hourItem :
                mHourItems) {
            mTotalMedecinesPerDay += Integer.valueOf(hourItem.getQuantity());
        }
        mTreatmentDuration = String.valueOf(Integer.valueOf(totalQuantity) / mTotalMedecinesPerDay);
        mStartingDate = startingDate;
        mTotalQuantity = totalQuantity;
        mTakingMoment = takingMoment;
        mMedicationImageBitmap = medicationImageBitmap;
    }

    public Medication(String medicationName, String treatedPart,
                      String medicationForm, String startingDate,
                      String totalQuantity, String hourItem,
                      @Nullable String takingMoment,
                      @Nullable Bitmap medicationImageBitmap) {
        mId = UUID.randomUUID().toString().replace("-", "");
        mMedicationName = medicationName;
        mTreatedPart = treatedPart;
        mMedicationForm = medicationForm;
        mStartingDate = startingDate;
        mTotalQuantity = totalQuantity;
        mSerializedHourItem = hourItem;
        mTakingMoment = takingMoment;
        mMedicationImageBitmap = medicationImageBitmap;
    }

    //Constructeur avec pour image un bitmap
    public Medication(String id, String medicationName, String treatedPart, String medicationForm, String medicationQuantityText, String medicationHour, String downloadUrl, @Nullable String medicationPrecision, @Nullable String missingTime) {
        mId = id;
        mMedicationName = medicationName;
        mTreatedPart = treatedPart;
        mMedicationForm = medicationForm;
        mMedicationQuantityText = medicationQuantityText;
        mMedicationHour = medicationHour;
        mDownloadUrl = downloadUrl;
        mMedicationPrecision = medicationPrecision;
        mMissingTime = missingTime;
    }

    /*public Medication(String medicationName, String treatedPart, String medicationForm,
                      String medicationQuantityText, String medicationHour,
                      @Nullable String medicationPrecision, @Nullable String missingTime,
                      Bitmap medicationImageBitmap) {
        mId = UUID.randomUUID().toString();
        mMedicationName = medicationName;
        mTreatedPart = treatedPart;
        mMedicationForm = medicationForm;
        mMedicationQuantityText = medicationQuantityText;
        mMedicationHour = medicationHour;
        mMedicationPrecision = medicationPrecision;
        mMissingTime = missingTime;
        mMedicationImageBitmap = medicationImageBitmap;
    }*/



    public Medication(Bitmap image, String name, String pathology, String form, int totalQuantity, int totalPerDay, String hour, int quantityPerday, @Nullable String precision, String startingDate ){
        mId = UUID.randomUUID().toString().replace("-", "");
        mMedicationImageBitmap = image;
        mMedicationName = name;
        mTreatedPart = pathology;
        mMedicationForm = form;
        mTreatmentDuration = String.valueOf(totalQuantity%totalPerDay != 0 ? totalQuantity/totalPerDay + 1 : totalQuantity/totalPerDay);
        mMedicationHour = hour;
        mMedicationQuantityText = String.valueOf(quantityPerday);
        mMedicationPrecision = precision;
        mStartingDate = startingDate;
    }


    //Constructeur avec pour image son resId
    public Medication(String medicationName, String treatedPart, String medicationForm,
                      String medicationQuantity, String medicationHour,
                      @Nullable String medicationPrecision, @Nullable String missingTime,
                      int medicationImage) {
        mMedicationName = medicationName;
        mTreatedPart = treatedPart;
        mMedicationForm = medicationForm;
        mMedicationHour = medicationHour;
        mMedicationPrecision = medicationPrecision;
        mMissingTime = missingTime;
        mMedicationImage = medicationImage;
        mMedicationQuantityText = medicationQuantity;
    }

    protected Medication(Parcel in) {
        mMedicationName = in.readString();
        mTreatedPart = in.readString();
        mMedicationForm = in.readString();
        mMedicationQuantityText = in.readString();
        mMedicationHour = in.readString();
        mMedicationPrecision = in.readString();
        mMissingTime = in.readString();
        mMedicationImage = in.readInt();
        mMedicationImageBitmap = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<Medication> CREATOR = new Creator<Medication>() {
        @Override
        public Medication createFromParcel(Parcel in) {
            return new Medication(in);
        }

        @Override
        public Medication[] newArray(int size) {
            return new Medication[size];
        }
    };

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getMedicationName() {
        return mMedicationName;
    }

    public void setMedicationName(String medicationName) {
        mMedicationName = medicationName;
    }

    public String getTreatedPart() {
        return mTreatedPart;
    }

    public void setTreatedPart(String treatedPart) {
        mTreatedPart = treatedPart;
    }

    public String getMedicationForm() {
        return mMedicationForm;
    }

    public void setMedicationForm(String medicationForm) {
        mMedicationForm = medicationForm;
    }

    public String getMedicationQuantityText() {
        return mMedicationQuantityText;
    }

    public void setMedicationQuantityText(String medicationQuantityText) {
        mMedicationQuantityText = medicationQuantityText;
    }

    public String getStartingDate() {
        return mStartingDate;
    }

    public void setStartingDate(String startingDate) {
        mStartingDate = startingDate;
    }

    public String getTreatmentDuration() {
        return mTreatmentDuration;
    }

    public void setTreatmentDuration(String treatmentDuration) {
        mTreatmentDuration = treatmentDuration;
    }

    public String getTakingMoment() {
        return mTakingMoment;
    }

    public void setTakingMoment(String takingMoment) {
        mTakingMoment = takingMoment;
    }

    public String getTotalQuantity() {
        return mTotalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        mTotalQuantity = totalQuantity;
    }

    public int getTotalMedecines() {
        return mTotalMedecines;
    }

    public void setTotalMedecines(int totalMedecines) {
        mTotalMedecines = totalMedecines;
    }

    public List<HourItem> getHourItems() {
        return mHourItems;
    }

    public void setHourItems(List<HourItem> hourItems) {
        mHourItems = hourItems;
    }

    public String getMedicationHour() {
        return mMedicationHour;
    }

    public void setMedicationHour(String medicationHour) {
        mMedicationHour = medicationHour;
    }

    public String getDownloadUrl() {
        return mDownloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        mDownloadUrl = downloadUrl;
    }

    public String getSerializedHourItem() {
        return mSerializedHourItem;
    }

    public void setSerializedHourItem(String serializedHourItem) {
        mSerializedHourItem = serializedHourItem;
    }

    @Nullable
    public String getMedicationPrecision() {
        return mMedicationPrecision;
    }

    public void setMedicationPrecision(@Nullable String medicationPrecision) {
        mMedicationPrecision = medicationPrecision;
    }

    @Nullable
    public String getMissingTime() {
        return mMissingTime;
    }

    public void setMissingTime(@Nullable String missingTime) {
        mMissingTime = missingTime;
    }

    public int getMedicationImage() {
        return mMedicationImage;
    }

    public void setMedicationImage(int medicationImage) {
        mMedicationImage = medicationImage;
    }

    public int getTotalMedecinesPerDay() {
        return mTotalMedecinesPerDay;
    }

    public void setTotalMedecinesPerDay(int totalMedecinesPerDay) {
        mTotalMedecinesPerDay = totalMedecinesPerDay;
    }

    public Bitmap getMedicationImageBitmap() {
        return mMedicationImageBitmap;
    }

    public void setMedicationImageBitmap(Bitmap medicationImageBitmap) {
        mMedicationImageBitmap = medicationImageBitmap;
    }

    // --- PARCELABLE ---

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mMedicationName);
        dest.writeString(mTreatedPart);
        dest.writeString(mMedicationForm);
        dest.writeString(mMedicationQuantityText);
        dest.writeString(mMedicationHour);
        dest.writeString(mMedicationPrecision);
        dest.writeString(mMissingTime);
        dest.writeInt(mMedicationImage);
        dest.writeParcelable(mMedicationImageBitmap, flags);
    }

    // --- FIREBASE STORAGE ---

    public static DatabaseReference getAllMedicationsFromDatabase(){
        return new Database<Medication>(new Medication()).getReference();
    }

    public static UploadTask uploadImageToDatabase(Uri imageUri, String child){
        return new Database<Medication>(new Medication()).uploadImage(imageUri, child);
    }

    public static void deleteImageFromDatabase(String fullUrl){
        new Database<Medication>(new Medication()).deleteImage(fullUrl);
    }

    public static void deleteMedicationToDatabase(String id){
        new Database<Medication>(new Medication()).removeObject(id);
    }

    public static void setMedicationToDatabase(Medication medication){
        new Database<Medication>(new Medication()).setObject(medication, medication.getId());
    }
}
