package com.example.globalpharma.Views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.globalpharma.Model.AllTheFrequencyItem;
import com.example.globalpharma.Model.DefaultFrequencyItem;
import com.example.globalpharma.Model.MonthlyFrequencyItem;
import com.example.globalpharma.Model.WeeklyFrequencyItem;
import com.example.globalpharma.R;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewMedicationActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private ImageView mBtnChooseMedicationForm;
    private Button mBtnChooseMedicationFrequency;
    private ImageView mImageViewMedicationPicture;
    private ConstraintLayout mConstraintLayoutAddPhoto;
    private ConstraintLayout mLayoutNewMedication;
    private TextInputEditText mTxtMedicationFrequency;
    private TextInputEditText mTxtMedicationName;
    private TextInputEditText mTxtMedicationPathology;
    private TextInputEditText mTxtMedicationForm;
    private String imagePath = null;
    private static final int RESULT_CODE_TAKING_PHOTO = 2;
    private Bitmap image;
    private RecyclerView mRvMedicationFrequencies;
    private List<WeeklyFrequencyItem> mWeeklyFrequencyItems;
    private List<AllTheFrequencyItem> mAllTheFrequencyItems;
    private List<DefaultFrequencyItem> mDefaultFrequencyItems;
    private List<MonthlyFrequencyItem> mMonthlyFrequencyItems;
    private DefaultFrequencyAdapter defaultFrequenceAdapter;
    private AllTheFrequencyAdapter allTheFrequencyAdapter;
    private MonthlyFrequencyAdapter monthlyFrequencyAdapter;
    private WeeklyFrequenceAdapter weeklyFrequenceAdapter;
    private final String WEEKLY_ID = getText(R.string.text_weekly).toString();
    private final String DAILY_ID = getText(R.string.text_daily).toString();
    private final String MONTHKLY_ID = getText(R.string.text_monthly).toString();
    private final String ALL_THE_ID = getText(R.string.text_all_the).toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_medication);
        getSupportActionBar().hide();

        initActivity();

        mAllTheFrequencyItems.add(new AllTheFrequencyItem("Tous les "));
        mDefaultFrequencyItems.add(new DefaultFrequencyItem("a"));
        mMonthlyFrequencyItems.add(new MonthlyFrequencyItem("Jour de prise"));
        mWeeklyFrequencyItems = getWeeklyFrequencyItems(mWeeklyFrequencyItems);

        mRvMedicationFrequencies.setVisibility(View.INVISIBLE);
        //mRvMedicationFrequencies.setLayoutManager(new LinearLayoutManager(this));
       // mRvMedicationFrequencies.setAdapter(weeklyFrequenceAdapter);

        showMedicationForms();

        addPhoto();

        showMedicationFrequencies();
    }

    public void initActivity(){
        //Graphics elements
        mBtnChooseMedicationForm = findViewById(R.id.btn_add_medication_form);
        mBtnChooseMedicationFrequency = findViewById(R.id.btn_add_medication_frequency);
        mConstraintLayoutAddPhoto = findViewById(R.id.add_photo);
        mLayoutNewMedication = findViewById(R.id.cl_new_medication);
        mImageViewMedicationPicture = findViewById(R.id.img_add_medication_picture);
        mTxtMedicationName= findViewById(R.id.text_add_medication_name);
        mTxtMedicationForm = findViewById(R.id.text_add_medication_form);
        mTxtMedicationPathology = findViewById(R.id.text_add_medication_pathology);
        mTxtMedicationFrequency = findViewById(R.id.sp_add_frequency_medication);
        mRvMedicationFrequencies = findViewById(R.id.rv_frequency_item);

        //Memory elements
        mWeeklyFrequencyItems = new ArrayList<>();
        mAllTheFrequencyItems = new ArrayList<>();
        mDefaultFrequencyItems = new ArrayList<>();
        mMonthlyFrequencyItems = new ArrayList<>();

        //recyclerview


        //adapters
        allTheFrequencyAdapter = new AllTheFrequencyAdapter(this, mAllTheFrequencyItems);
        defaultFrequenceAdapter = new DefaultFrequencyAdapter(mDefaultFrequencyItems, this);
        monthlyFrequencyAdapter = new MonthlyFrequencyAdapter(this, mMonthlyFrequencyItems);
        weeklyFrequenceAdapter = new WeeklyFrequenceAdapter(mWeeklyFrequencyItems, this);

        mAllTheFrequencyItems.add(new AllTheFrequencyItem("Tous les "));
        mDefaultFrequencyItems.add(new DefaultFrequencyItem("a"));
        mMonthlyFrequencyItems.add(new MonthlyFrequencyItem("Jour de prise"));
    }

    public void choosePictureFromGallery(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 1);
    }

    public void takePhotoWithCamera(){
        Intent captureImageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(captureImageIntent.resolveActivity(getPackageManager()) != null){
            String time = new SimpleDateFormat("ddMMyyy_HHmmss").format(new Date());
            File imageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            try {
                File imageFile = File.createTempFile("photo - " + time, ".png", imageDir);
                imagePath = imageFile.getAbsolutePath();

                Uri imageUri = FileProvider.getUriForFile(NewMedicationActivity.this,
                        NewMedicationActivity.this.getApplicationContext().getPackageName()
                                + ".provider", imageFile);

                captureImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(captureImageIntent, RESULT_CODE_TAKING_PHOTO);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Vérifie si l'image a été bien récupérée
        if(requestCode == 1 && resultCode == RESULT_OK){

            //accès à l'image
            Uri choosenImage = data.getData();
            String[] imagePathTable = {MediaStore.Images.Media.DATA};

            Cursor cursor = this.getContentResolver().query(choosenImage, imagePathTable, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(imagePathTable[0]);
            String imagePath = cursor.getString(columnIndex);

            cursor.close();

            Bitmap image = BitmapFactory.decodeFile(imagePath);

            mImageViewMedicationPicture.setImageBitmap(image);
        }
        else if(requestCode == RESULT_CODE_TAKING_PHOTO && resultCode == RESULT_OK){
            image = BitmapFactory.decodeFile(imagePath);
            mImageViewMedicationPicture.setImageBitmap(image);
            saveImageToGallery();
        }
        else Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }

    public void saveImageToGallery(){
        MediaStore.Images.Media.insertImage(getContentResolver(), image, "Drey", "Description");
    }

    public void showPopUp(View view, int menu){
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_take_photo_with_camera:{
                takePhotoWithCamera();
                return true;
            }
            case R.id.item_choose_from_gallery:{
                choosePictureFromGallery();
                return true;
            }
            case R.id.item_daily:{
                getNameFrequency(item);
                mRvMedicationFrequencies.setVisibility(View.INVISIBLE);
                //mRvMedicationFrequencies.setAdapter(defaultFrequenceAdapter);
                return true;
            }
            case R.id.item_weekly:{
                getNameFrequency(item);
                mRvMedicationFrequencies.setAdapter(weeklyFrequenceAdapter);
                return true;
            }
            case R.id.item_monthly:{
                getNameFrequency(item);
                mRvMedicationFrequencies.setAdapter(monthlyFrequencyAdapter);
                return true;
            }
            case R.id.item_all_the:{
                getNameFrequency(item);
                mRvMedicationFrequencies.setAdapter(allTheFrequencyAdapter);
                return true;
            }
            default:{
                return false;
            }
        }
    }

    public void showMedicationForms(){
        mBtnChooseMedicationForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewMedicationActivity.this, MedicationFormsActivity.class));
            }
        });
    }

    public void addPhoto(){
        mConstraintLayoutAddPhoto.setOnClickListener(new ConstraintLayout.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp(v, R.menu.pop_up_get_photo_mode_menu);
            }
        });
    }

    public void showMedicationFrequencies(){
        mBtnChooseMedicationFrequency.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp(v, R.menu.pop_up_frequence_medication);
            }
        });
    }

    public void getNameFrequency(MenuItem item){
        mTxtMedicationFrequency.setText(item.getTitle().toString());
    }

    public void showFragmentDuration(int resId){
        switch (resId){
            /*case R.string.text_daily:{
                initRvDefault();
            }*/
            case R.string.text_weekly:{
            }
            case R.string.text_monthly:{
            }
            case R.string.text_all_the:{
            }
            default:{
            }
        }
    }

    public List<WeeklyFrequencyItem> getWeeklyFrequencyItems(List<WeeklyFrequencyItem> list){
        list.add(new WeeklyFrequencyItem(getText(R.string.text_lundi).toString()));
        list.add(new WeeklyFrequencyItem(getText(R.string.text_mardi).toString()));
        list.add(new WeeklyFrequencyItem(getText(R.string.text_mercredi).toString()));
        list.add(new WeeklyFrequencyItem(getText(R.string.text_jeudi).toString()));
        list.add(new WeeklyFrequencyItem(getText(R.string.text_vendredi).toString()));
        list.add(new WeeklyFrequencyItem(getText(R.string.text_samedi).toString()));
        list.add(new WeeklyFrequencyItem(getText(R.string.text_dimanche).toString()));

        return list;
    }

}
