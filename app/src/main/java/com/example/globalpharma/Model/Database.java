package com.example.globalpharma.Model;

import android.net.Uri;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class Database<T> {
    private T data;
    private FirebaseFirestore db;
    private DocumentReference documentReference;

    public Database(T data) {
        this.data = data;
    }


    protected StorageReference getStorageReference(){
        return FirebaseStorage.getInstance().getReference("/" + this.data.getClass().getSimpleName() + "/");
    }

    protected DatabaseReference getReference(){
        return FirebaseDatabase.getInstance().getReference("/" + this.data.getClass().getSimpleName() + "/");
    }

    protected void setObject(T data , String id){
        getReference().child(id).setValue(data);
    }

    protected void removeObject (String id){
        getReference().child(id).removeValue();
    }

    protected UploadTask uploadImage(Uri imageUri, String child){
        if (imageUri == null) {
            return null;
        }
        String fileName = UUID.randomUUID().toString();
        StorageReference storageReference = getStorageReference();
        if(!child.isEmpty()){
            return storageReference.child(child).child(fileName).putFile(imageUri);
        }
        return storageReference.child(fileName).putFile(imageUri);
    }

    protected void deleteImage(String fullUrl){
        FirebaseStorage.getInstance().getReferenceFromUrl(fullUrl).delete();
    }

    protected void editImage(Uri imageUri, String child, String fullUrl){
        deleteImage(fullUrl);
        uploadImage(imageUri, child);
    }
}