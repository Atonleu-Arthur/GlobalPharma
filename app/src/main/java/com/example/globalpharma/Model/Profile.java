package com.example.globalpharma.Model;

import android.graphics.Bitmap;
import android.icu.text.BidiRun;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.PasswordAuthentication;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Profile {
    
    private static final String NAME = "Name";
    private static final String SEX = "Sex";
    private static final String BIRTH_DATE = "Birth date";
    private static final String PASSWORD = "Password";
    private static final String EMAIL = "Email";
    private static final String PHONE = "PHONE NUMBER";

    private DocumentReference reference = FirebaseFirestore.getInstance().document("User/");


    public Map<String, Object> objectToSave;

    public PasswordAuthentication passwordAuthentication;

    private String name;

    @Nullable
    private String email;

    @Nullable
    private String phone;

    private String password;

    private Date birth;

    private String sex;

    private Bitmap bitmap;

    private int photo;

    //Authentication with Email
    public Profile(String email, String password) {
        this.email = email;
        this.password = password;
    }

    //Authentication with phone number
    public Profile(Long phone, String password) {
        this.phone = phone.toString().startsWith("+237") ? phone.toString() : "+237" + phone;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public void setEmail(@Nullable String email) {
        this.email = email;
    }

    @Nullable
    public String getPhone() {
        return phone;
    }

    public void setPhone(@Nullable String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public void saveUserToFirestore(Profile profile){
        passwordAuthentication = new PasswordAuthentication(profile.email, profile.password.toCharArray());
        objectToSave = new HashMap<>();
        objectToSave.put(NAME, profile.name.isEmpty()? null: profile.name);
        objectToSave.put(PHONE, profile.phone.isEmpty()? null: profile.phone);
        objectToSave.put(EMAIL, profile.email.isEmpty()? null: profile.email);
        objectToSave.put(PASSWORD, profile.password.isEmpty()? null: passwordAuthentication.getPassword());
        objectToSave.put(SEX, profile.sex.isEmpty()? null: profile.sex);
        reference.set(objectToSave);
    }

    public void saveUserToRealTimeDb(Profile profile){
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest hash = MessageDigest.getInstance("MD5");
        hash.update(password.getBytes());
        byte[] bytes = hash.digest();
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b:
                bytes) {
            stringBuffer.append(Integer.toHexString(b & 0xff));
        }
        return stringBuffer.toString();
    }

    private String unHashPassword(char[] hashPassword, byte[] hash){
        char[] hexChars = new char[hash.length * 2];
        for(int i = 0; i < hash.length; i++){
            int v = hash[i] & 0xFF;
            hexChars[i * 2] = hashPassword [v >>> -4];
            hexChars[i * 2 + 1] = hashPassword [v & 0x0F];
        }
        return new String(hexChars);
    }

}
