package com.example.globalpharma.Views;

import android.Manifest;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.globalpharma.Model.HourItem;
import com.example.globalpharma.Model.Medication;
import com.example.globalpharma.Model.MedicationForm;
import com.example.globalpharma.Model.Medicine;
import com.example.globalpharma.R;
import com.example.globalpharma.controller.AlertReceiver;
import com.example.globalpharma.controller.NotificationHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.example.globalpharma.Views.Accueil.SP_NAME_STRING;


public class NewMedicationActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener, PopUpQuantity.PopUpQuantityListener {

    private static int STORAGE_PERMISSION_CODE = 1;
    private ImageView mBtnChooseMedicationForm;
    private ImageView mBtnAddHour;
    private ImageView mBtnAddHourMoreThan;
    private ImageView mDeleteHour;
    private ImageView mEditHour;
    private TextView mTxtAddPhoto;
    private Button mBtnChooseMedicationFrequency;
    private Button mBtnChooseMedicationDurationType;
    private Button mBtnValidateAddingMedication;
    private Button mBtnChooseMedicationMoment;
    private ImageView mImageViewMedicationPicture;
    private ImageView mImageMedicationForm;
    private ConstraintLayout mConstraintLayoutAddPhoto;
    private ConstraintLayout mLayoutNewMedication;
    private ConstraintLayout mLayoutAllThe;
    private ConstraintLayout mLayoutWeekly;
    private ConstraintLayout mLayoutMonthly;
    private ConstraintLayout mLayoutAddHours;
    private ConstraintLayout mLayoutStartingDate;
    private ConstraintLayout mLayoutDuration;
    private ConstraintLayout mLayoutFrequencies;
    private TextInputEditText mTxtMedicationFrequency;
    private TextInputEditText mMonday;
    private TextInputEditText mTuesday;
    private TextInputEditText mWednesday;
    private TextInputEditText mThursday;
    private TextInputEditText mFriday;
    private TextInputEditText mSaturday;
    private TextInputEditText mSunday;
    private AppCompatCheckBox mChkMonday;
    private AppCompatCheckBox mChkTuesday;
    private AppCompatCheckBox mChkWednesday;
    private AppCompatCheckBox mChkThursday;
    private AppCompatCheckBox mChkFriday;
    private AppCompatCheckBox mChkSaturday;
    private AppCompatCheckBox mChkSunday;
    private TextInputEditText mTxtMedicationName;
    private TextInputEditText mTxtMedicationPathology;
    private TextInputEditText mTxtMedicationForm;
    private TextInputEditText mTxtMedicationQuantity;
    private TextInputEditText mTxtMedicationQuantityText;
    private TextInputEditText mTxtTakingMomentValue;
    private TextInputEditText mTxtTakingMoment;
    private TextInputEditText mTxtMedicationStartingDateValue;
    private TextInputEditText mTxtMedicationDurationIntValue;
    private TextInputEditText mTxtMedicationDurationStringValue;
    private TextInputEditText mTxtDurationTypeValue;
    private TextInputEditText mTxtDurationInt;
    private TextInputEditText mTxtDurationSring;
    private TextInputEditText mTxtMedicationNotes;
    private TextInputEditText mTxtDayOfTaking;
    private RecyclerView mRecyclerView;
    private String imagePath;
    private static final int RESULT_CODE_TAKING_PHOTO = 2;
    private int choosenFrequence = 2;
    private Bitmap image;
    private String imageName;
    private List<Medicine> mMedications;
    private List<HourItem> mHourItems;
    private HourAdapter mHourAdapter;
    private SimpleDateFormat mSimpleDateFormat;
    private Date choosenDate;
    private StorageReference mStorageRef;
    private File localFile;
    private FirebaseFirestore db;
    private DatabaseReference dbRef;
    private Uri choosenImage;
    private NotificationManagerCompat notificationManagerCompat;
    private HourItem lastHourSet;
    List<AppCompatCheckBox> mAppCompatCheckBoxes;
    private Medicine currentMedicine;

    //Image storage
    private static final String DB_STORAGE_IMG_PATH = "Medication";
    private static final String SP_NAME_FORRM = "MedicationForm";
    public static final String FREQUENCY_DAILY = "1";
    public static final String FREQUENCY_MONTHLY = "3";
    public static final String FREQUENCY_WEEKLY = "2";
    public static final String FREQUENCY_ALL_THE = "4";

    public List<Integer> mIntegersDays;


    private Uri downloadImageUrl;
    private UploadTask mUploadTaskImage;
    private FileOutputStream mFileOutputStream;
    private  boolean isEdit = false;

    //DbHelper

    private int month, year, day, hour, minute;

    private boolean isAnEditItem = false;

    private int positionEditedItem;

    private OutputStream mOutputStream;

    private String title;

    private String time;

    private String date;

    private int poisitionHourEdited;

    private Calendar calendar;

    private NotificationManagerCompat notificationManager;

    private long selectedTimeStamp;

    public int dayOfEveryMonth;

    public int durationOfTreatment;

    //constants time

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_medication);
        getSupportActionBar().hide();

        //Toast.makeText(this, (new File(Environment.getExternalStorageDirectory()+ "/MALSJD")).toString(), Toast.LENGTH_LONG).show();

        initActivity();

        getEditIntent();

        //Choisir la photo du médicament
        addPhoto();

        //Affiche l'activité qui mène aux formes de médicaments
        showMedicationForms();

        setMedicationFormName();

        setMedicationFormImage();

        setTotalQuantity();

        //Choix de la fréquence de prise de médicament
        showMedicationFrequencies();

        //Choix de la date début
        showDatePicker();

        //Choix de la fréquence de prise de médicament
        showDurationTypes();

        showMomentsTaking();

        showDurationValues();

        showTimePicker();

        initRecyclerViewHours();

        deleteHour();

        checkDay();

        if(mTxtDurationTypeValue.getText().equals(getString(R.string.text_indeterminate_duration))){
            if(mTxtMedicationFrequency.getText().equals(getString(R.string.text_daily))){
                passToMedicationsActivity();
            }
            else if(mTxtMedicationFrequency.getText().equals(getString(R.string.text_monthly))){
                //setMedicationsMonthly();
            }
            if(mTxtMedicationFrequency.getText().equals(getString(R.string.text_weekly))){
                setMedicationsWeekly();
            }
        }
        else{

        }

        passToMedicationsActivity();

//        getDayOfMonth();

        switch (String.valueOf(choosenFrequence)){
            case FREQUENCY_DAILY :
                //passToMedicationsActivity();
                break;

            case FREQUENCY_WEEKLY :
                mIntegersDays = new ArrayList<>();
                for (Integer i :
                        getDaysChecked()) {
                    switch (i){
                        case 0:
                            mIntegersDays.add(Calendar.MONDAY);
                            break;
                        case 1:
                            mIntegersDays.add(Calendar.TUESDAY);
                            break;
                        case 2:
                            mIntegersDays.add(Calendar.WEDNESDAY);
                            break;
                        case 3:
                            mIntegersDays.add(Calendar.THURSDAY);
                            break;
                        case 4:
                            mIntegersDays.add(Calendar.FRIDAY);
                            break;
                        case 5:
                            mIntegersDays.add(Calendar.SATURDAY);
                            break;
                        case 6:
                            mIntegersDays.add(Calendar.SUNDAY);
                            break;
                    }
                }
                break;

            case FREQUENCY_MONTHLY :
                dayOfEveryMonth = getDayOfMonth();
                break;

        }
    }

    private void setMedicationsWeekly() {

    }

    private void getEditIntent() {
        if (getIntent().hasExtra("MedicationToEdit")){
            isAnEditItem = true;
            Type type = new TypeToken<Medicine>(){}.getType();
            currentMedicine = new Gson().fromJson(getIntent().getStringExtra("MedicationToEdit"), type);
            mTxtMedicationName.setText(currentMedicine.getName());
            mTxtMedicationForm.setText(currentMedicine.getForm());
            mImageViewMedicationPicture.setImageBitmap(currentMedicine.getImage());
            image = currentMedicine.getImage();
            mTxtAddPhoto.setText(currentMedicine.getImage().toString());
            mHourItems.clear();
            mHourItems.add(currentMedicine.getHourItem());
            mTxtMedicationPathology.setText(currentMedicine.getPathology());
            mTxtMedicationQuantity.setText(currentMedicine.getTotalQuantity() + "");
            mTxtDurationTypeValue.setText(currentMedicine.getDurationType());
            mTxtTakingMomentValue.setText(currentMedicine.getMoment());
            Type typeInt = new TypeToken<Integer>(){}.getType();
            positionEditedItem = new Gson().fromJson(getIntent().getStringExtra("position"), typeInt);
            mTxtMedicationFrequency.setText(currentMedicine.getFrequence());

            initRecyclerViewHours();

        }
    }

    private int getDayOfMonth() {
        return Integer.parseInt(mTxtDayOfTaking.getText().toString());
    }



    private List<Integer> getDaysChecked() {
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < mAppCompatCheckBoxes.size(); i++) {
            if (mAppCompatCheckBoxes.get(i).isChecked()){
                integers.add(i);
            }
        }
        return integers;
    }

    private void checkDay() {
        mMonday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mChkMonday.isChecked())
                    mChkMonday.setChecked(false);
                else
                    mChkMonday.setChecked(true);
            }
        });

        mTuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mChkTuesday.isChecked())
                    mChkTuesday.setChecked(false);
                else
                    mChkTuesday.setChecked(true);
            }
        });

        mWednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mChkWednesday.isChecked())
                    mChkWednesday.setChecked(false);
                else
                    mChkWednesday.setChecked(true);
            }
        });

        mThursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mChkThursday.isChecked())
                    mChkThursday.setChecked(false);
                else
                    mChkThursday.setChecked(true);
            }
        });

        mFriday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mChkFriday.isChecked())
                    mChkFriday.setChecked(false);
                else
                    mChkFriday.setChecked(true);
            }
        });

        mSaturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mChkSaturday.isChecked())
                    mChkSaturday.setChecked(false);
                else
                    mChkSaturday.setChecked(true);
            }
        });

        mSunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mChkSunday.isChecked())
                    mChkSunday.setChecked(false);
                else
                    mChkSunday.setChecked(true);
            }
        });
    }

    //      - - - INITIATION OF VIEW - - -

    private void initRecyclerViewHours() {
        if (mHourItems.isEmpty())
            mRecyclerView.setVisibility(View.GONE);
        else
            mRecyclerView.setVisibility(View.VISIBLE);
    }

    public void initActivity() {
        //Graphics elements
        mBtnChooseMedicationForm = findViewById(R.id.btn_add_medication_form);
        mBtnAddHour = findViewById(R.id.img_add_hour);
        mBtnAddHourMoreThan = findViewById(R.id.img_add_hour_more_than);
        mBtnChooseMedicationFrequency = findViewById(R.id.btn_add_medication_frequency);
        mBtnChooseMedicationMoment = findViewById(R.id.btn_add_moment_taking_scroll);
        mBtnChooseMedicationDurationType = findViewById(R.id.btn_add_medication_type_duration);
        mBtnValidateAddingMedication = findViewById(R.id.btn_validate_adding_medication);
        mConstraintLayoutAddPhoto = findViewById(R.id.add_photo);
        mLayoutFrequencies = findViewById(R.id.frequencies);
        mLayoutAllThe = findViewById(R.id.cl_all_the);
        mLayoutWeekly = findViewById(R.id.cl_weekly_frequency);
        mLayoutDuration = findViewById(R.id.cl_duration);
        mLayoutMonthly = findViewById(R.id.cl_monthly);
        mLayoutAddHours = findViewById(R.id.cl_add_hours);
        mLayoutStartingDate = findViewById(R.id.cl_starting_day);
        mLayoutNewMedication = findViewById(R.id.cl_new_medication);
        mImageViewMedicationPicture = findViewById(R.id.img_add_medication_picture);
        mImageMedicationForm = findViewById(R.id.img_medication_form);
        mDeleteHour = findViewById(R.id.img_delete_hour);
        mEditHour = findViewById(R.id.img_edit_hour);
        mTxtMedicationName = findViewById(R.id.text_add_medication_name);
        mTxtMedicationForm = findViewById(R.id.text_add_medication_form);
        mTxtMedicationPathology = findViewById(R.id.text_add_medication_pathology);
        mTxtMedicationFrequency = findViewById(R.id.sp_add_frequency_medication);
        mTxtMedicationQuantity = findViewById(R.id.text_quantity);
        mTxtMedicationQuantityText = findViewById(R.id.text_add_medication_count);
        mTxtTakingMomentValue = findViewById(R.id.text_add_moment_taking_value);
        mTxtTakingMoment = findViewById(R.id.text_add_moment_taking);
        mTxtMedicationStartingDateValue = findViewById(R.id.text_starting_date_value);
        mTxtMedicationDurationIntValue = findViewById(R.id.text_add_medication_duration_int_value);
        mTxtDurationTypeValue = findViewById(R.id.text_add_medication_type_duration);
        mTxtMedicationDurationStringValue = findViewById(R.id.text_add_medication_duration_value);
        mTxtMedicationNotes = findViewById(R.id.text_add_medication_notes);
        mTxtDayOfTaking = findViewById(R.id.text_day_taking);
        mTxtAddPhoto = findViewById(R.id.text_add_medication_picture);
        mRecyclerView = findViewById(R.id.rv_hours);

        mMonday = findViewById(R.id.text_monday);
        mChkMonday = findViewById(R.id.chk_monday);
        mTuesday = findViewById(R.id.text_tuesday);
        mChkTuesday = findViewById(R.id.chk_tuesday);
        mWednesday = findViewById(R.id.text_wednesday);
        mChkWednesday = findViewById(R.id.chk_wednesday);
        mThursday = findViewById(R.id.text_thursday);
        mChkThursday = findViewById(R.id.chk_thursday);
        mFriday = findViewById(R.id.text_friday);
        mChkFriday = findViewById(R.id.chk_friday);
        mSaturday = findViewById(R.id.text_saturday);
        mChkSaturday = findViewById(R.id.chk_saturday);
        mSunday = findViewById(R.id.text_sunday);
        mChkSunday = findViewById(R.id.chk_sunday);

        mTxtDurationInt = findViewById(R.id.text_add_medication_duration_int_value);
        mTxtDurationSring = findViewById(R.id.text_add_medication_duration);

        //Memory elements
        mSimpleDateFormat = new SimpleDateFormat("dd/MM/yyy");
        mMedications = new ArrayList<>();
        mHourItems = new ArrayList<>();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        db = FirebaseFirestore.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();
        notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());

        mHourAdapter = new HourAdapter(NewMedicationActivity.this, mHourItems);
        mRecyclerView.setAdapter(mHourAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Default states of values
        //String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        Calendar calendar = Calendar.getInstance();
        /*String dateS = calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.MONTH)
                + " " + calendar.get(Calendar.YEAR);*/
        String dateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        mTxtMedicationStartingDateValue.setText(dateString);

        notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());

        mAppCompatCheckBoxes = new ArrayList<>();
        mAppCompatCheckBoxes.add(mChkMonday);
        mAppCompatCheckBoxes.add(mChkTuesday);
        mAppCompatCheckBoxes.add(mChkWednesday);
        mAppCompatCheckBoxes.add(mChkThursday);
        mAppCompatCheckBoxes.add(mChkFriday);
        mAppCompatCheckBoxes.add(mChkSaturday);
        mAppCompatCheckBoxes.add(mChkSunday);
    }


    //      - - - MANAGEMENT OF PHOTOS - - -

    public void addPhoto() {
        mConstraintLayoutAddPhoto.setOnClickListener(new ConstraintLayout.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp(v, R.menu.pop_up_get_photo_mode_menu);
            }
        });
        mImageViewMedicationPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp(v, R.menu.pop_up_get_photo_mode_menu);
            }
        });
    }

    public void deletePhoto(){
        mConstraintLayoutAddPhoto.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
        mImageViewMedicationPicture.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
    }

    public void choosePictureFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, 1);
    }

    public void takePhotoWithCamera() {
        Intent captureImageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (captureImageIntent.resolveActivity(getPackageManager()) != null) {
            String time = new SimpleDateFormat("ddMMyyy_HHmmss").format(new Date());
            File imageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            try {
                File imageFile = File.createTempFile("IMG_" + time, ".jpg", imageDir);
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
        if (requestCode == 1 && resultCode == RESULT_OK) {
            //Choix de la photo depuis la galérie

            //accès à l'image
            choosenImage = data.getData();
            String[] imagePathTable = {MediaStore.Images.Media.DATA};

            Cursor cursor = this.getContentResolver().query(choosenImage, imagePathTable, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(imagePathTable[0]);
            imagePath = cursor.getString(columnIndex);

            cursor.close();

            try {
                image = MediaStore.Images.Media.getBitmap(getContentResolver(), choosenImage);
            } catch (IOException e) {
                e.printStackTrace();
            }

            image = BitmapFactory.decodeFile(imagePath);

            imageName = imagePath.substring(imagePath.lastIndexOf("/") + 1);
            mTxtAddPhoto.setText(imagePath.substring(imagePath.lastIndexOf("/") + 1, imagePath.lastIndexOf("/") + 10) + ".." + imagePath.substring(imagePath.length() - 5));

            saveImageToGallery(image);

        } else if (requestCode == RESULT_CODE_TAKING_PHOTO && resultCode == RESULT_OK) {
            //Photo prise avec la caméra
            image = BitmapFactory.decodeFile(imagePath);

            saveImageToGallery(image);
            choosenImage = data.getData();

            imageName = imagePath.substring(imagePath.lastIndexOf("/") + 1);
            mTxtAddPhoto.setText(imagePath.substring(imagePath.lastIndexOf("/") + 1, imagePath.lastIndexOf("/") + 10) + ".." + imagePath.substring(imagePath.length() - 5, imagePath.length()));

            //uploadPhoto();
        }
    }

    public String getImagePath(Context context, Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);

        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(columnIndex);
        }
        return null;
    }

    public void saveImageToGallery(Bitmap image) {
        try {
            File imagesFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pictures" + "/Global Pharma");
            if (!imagesFolder.exists()) {
                imagesFolder.mkdir();
                MediaStore.Images.Media.insertImage(getContentResolver(), getImagePath(getApplicationContext(), choosenImage), imageName, "");
            } else {
                Toast.makeText(this, imagesFolder.toString(), Toast.LENGTH_LONG).show();

                mOutputStream = new FileOutputStream(new File(imagesFolder, imageName));

                image.compress(Bitmap.CompressFormat.PNG, 100, mOutputStream);
                MediaStore.Images.Media.insertImage(getContentResolver(), imagesFolder.toString() , imageName, "");
            }
        } catch (FileNotFoundException e) {
            Log.d("Image Error: ", e.toString());
        }
    }

    public void uploadPhoto() {
        Uri file = Uri.fromFile(new File(imagePath));
        String fileName = UUID.randomUUID().toString();

        mUploadTaskImage = Medication.uploadImageToDatabase(file, DB_STORAGE_IMG_PATH + "/" + fileName);

        //return mUploadTaskImage.getSnapshot().getDownloadUrl();
    }

    public String getExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }



    //      - - - MANAGEMENT OF MEDICATION FORMS - - -

    public void showMedicationForms() {
        mBtnChooseMedicationForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewMedicationActivity.this, MedicationFormsActivity.class));
            }
        });

        mTxtMedicationForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewMedicationActivity.this, MedicationFormsActivity.class));
            }
        });

        mImageMedicationForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewMedicationActivity.this, MedicationFormsActivity.class));
            }
        });
    }
    //      - - - MANAGEMENT OF MEDICATION FORMS NAMES AND IMAGES - - -

    public String getMedicationFormDataFromIntent(){
        if(getIntent().hasExtra(SP_NAME_FORRM))
            return getIntent().getStringExtra(SP_NAME_FORRM);
        else
            return null;
    }

    public MedicationForm getMedicationInfos(){
        MedicationForm medication = null;
        if (getMedicationFormDataFromIntent() != null) {
            mImageMedicationForm.setVisibility(View.VISIBLE);
            Gson gson = new Gson();
            Type medicationFormType = new TypeToken<MedicationForm>(){}.getType();
            medication = gson.fromJson(getMedicationFormDataFromIntent(), medicationFormType);
        }
        return medication;
    }


    public void setMedicationFormName() {
        if (getMedicationInfos() != null)
            mTxtMedicationForm.setText(getMedicationInfos().getFormName());
    }

    public void setMedicationFormImage() {
        if (getMedicationInfos() != null) {
            mImageMedicationForm.setImageResource(getMedicationInfos().getFormImage());
        }
    }

    public void setTotalQuantity() {
        mTxtMedicationQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTxtMedicationQuantityText.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        mTxtMedicationQuantity.setKeyboardNavigationCluster(true);
                    }
                });
            }
        });
    }


    //      - - - MANAGEMENT OF POP UP - - -

    public void showPopUp(View view, int menu) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(menu);
        popupMenu.show();
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_take_photo_with_camera: {
                if (ContextCompat.checkSelfPermission(NewMedicationActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    takePhotoWithCamera();
                } else {
                    requestStoragePermission();
                }
                return true;
            }
            case R.id.item_choose_from_gallery: {
                if (ContextCompat.checkSelfPermission(NewMedicationActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    choosePictureFromGallery();
                } else {
                    requestStoragePermission();
                }
                return true;
            }
            case R.id.item_daily: {
                choosenFrequence = Integer.parseInt(FREQUENCY_DAILY);
                getNameFrequency(item);
                mLayoutFrequencies.setVisibility(View.GONE);
                return true;
            }
            case R.id.item_weekly: {
                choosenFrequence = Integer.parseInt(FREQUENCY_WEEKLY);
                getNameFrequency(item);
                mLayoutFrequencies.setVisibility(View.VISIBLE);
                mLayoutWeekly.setVisibility(View.VISIBLE);
                mLayoutFrequencies.setMaxHeight(mLayoutWeekly.getMaxHeight());
                mLayoutFrequencies.setMinHeight(mLayoutWeekly.getMinHeight());
                setLayoutInvisible(mLayoutMonthly);
                setLayoutInvisible(mLayoutAllThe);
                return true;
            }
            case R.id.item_monthly: {
                choosenFrequence = Integer.parseInt(FREQUENCY_MONTHLY);
                getNameFrequency(item);
                mLayoutFrequencies.setVisibility(View.VISIBLE);
                mLayoutMonthly.setVisibility(View.VISIBLE);
                mLayoutFrequencies.setMaxHeight(mLayoutMonthly.getMaxHeight());
                mLayoutFrequencies.setMinHeight(mLayoutMonthly.getMinHeight());
                setLayoutInvisible(mLayoutWeekly);
                setLayoutInvisible(mLayoutAllThe);
                return true;
            }
            case R.id.item_all_the: {
                choosenFrequence = Integer.parseInt(FREQUENCY_ALL_THE);
                getNameFrequency(item);
                mLayoutFrequencies.setVisibility(View.VISIBLE);
                mLayoutAllThe.setVisibility(View.VISIBLE);
                mLayoutFrequencies.setMaxHeight(mLayoutAllThe.getMaxHeight());
                mLayoutFrequencies.setMinHeight(mLayoutAllThe.getMinHeight());
                setLayoutInvisible(mLayoutWeekly);
                setLayoutInvisible(mLayoutMonthly);
                return true;
            }
            case R.id.item_determinate_duration: {
                mTxtDurationTypeValue.setText(item.getTitle());
                mLayoutDuration.setVisibility(View.VISIBLE);
                return true;
            }
            case R.id.item_indeterminate_duration: {
                mTxtDurationTypeValue.setText(item.getTitle());
                mLayoutDuration.setVisibility(View.GONE);
                return true;
            }
            case R.id.item_before_eating: {
                mTxtTakingMomentValue.setText(item.getTitle());
                return true;
            }
            case R.id.item_while_eating: {
                mTxtTakingMomentValue.setText(item.getTitle());
                return true;
            }
            case R.id.item_after_eating: {
                mTxtTakingMomentValue.setText(item.getTitle());
                return true;
            }
            case R.id.item_out_of_eating: {
                mTxtTakingMomentValue.setText(item.getTitle());
                return true;
            }
            case R.id.item_whatever: {
                mTxtTakingMomentValue.setText(item.getTitle());
                return true;
            }
            default: {
                return false;
            }
        }
    }



    //      - - - MANAGEMENT OF MEDICATION FREQUENCIES - - -

    public void showMedicationFrequencies() {
        mBtnChooseMedicationFrequency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp(v, R.menu.pop_up_frequence_medication);
            }
        });

        mTxtMedicationFrequency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp(v, R.menu.pop_up_frequence_medication);
            }
        });

        mBtnChooseMedicationFrequency.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp(v, R.menu.pop_up_frequence_medication);
            }
        });
    }

    public void getNameFrequency(MenuItem item) {
        mTxtMedicationFrequency.setText(item.getTitle().toString());
    }



    //      - - - MANAGEMENT OF LAYOUTS - - -

    public void setLayoutInvisible(ConstraintLayout constraintLayout) {
        constraintLayout.setVisibility(View.INVISIBLE);
    }


    //      - - - MANAGEMENT OF DATES - - -

    public void showDatePicker() {
        mTxtMedicationStartingDateValue.setOnClickListener(new ConstraintLayout.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Date Picker");
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {//hourOfDay + " : " + minute
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String dateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        if (Date.parse(dateString) < Date.parse(mTxtMedicationStartingDateValue.getText().toString())) {
            Toast.makeText(this, getText(R.string.text_error_starting_date_medication), Toast.LENGTH_SHORT).show();
            showDatePicker();
        } else
            mTxtMedicationStartingDateValue.setText(dateString);
    }



    //      - - - MANAGEMENT OF TYPE OF DURATION OF MEDICATION - - -

    public void showDurationTypes() {
        mTxtDurationTypeValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp(v, R.menu.pop_up_menu_duration_type);
            }
        });

        mBtnChooseMedicationDurationType.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp(v, R.menu.pop_up_menu_duration_type);
            }
        });
    }

    public void showDurationValues() {
        mTxtMedicationDurationStringValue.setOnClickListener(new TextInputEditText.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp(v, R.menu.pop_up_menu_duration_suggestions);
            }
        });
    }

    //      - - - MANAGEMENT OF TAKING MOMENTS OF MEDICATION - - -

    public void showMomentsTaking() {
        mTxtTakingMomentValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp(v, R.menu.pop_up_menu_taking_moment);
            }
        });

        mBtnChooseMedicationMoment.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp(v, R.menu.pop_up_menu_taking_moment);
            }
        });

        mTxtTakingMoment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp(v, R.menu.pop_up_menu_taking_moment);
            }
        });
    }


    //      - - - MANAGEMENT OF TIMES - - -

    public void showTimePicker() {
        mBtnAddHourMoreThan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "Time Picker");
            }
        });

        mLayoutAddHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "Time Picker");
            }
        });

        mBtnAddHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "Time Picker");
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        hour = hourOfDay;
        this.minute = minute;
        if (this.minute < 10)
            this.time = hour + " : 0" + this.minute;
        else
            this.time = hour + " : " + this.minute;
        selectedTimeStamp = calendar.getTimeInMillis();


        if(isEdit == false){
            lastHourSet = new HourItem("Horaire " + (mHourItems.size() + 1), time,
                    mTxtMedicationForm.getText().toString(), mTxtMedicationQuantity.getText().toString());
            showPopUpForQuantity();
        }
        else{
            lastHourSet = new HourItem("Horaire " + (mHourItems.size() + 1), time,
                    mTxtMedicationForm.getText().toString(), mTxtMedicationQuantity.getText().toString());
            showPopUpForQuantity();
        }




    }


    public void addHour(int hourOfDay, int minute) {
        if (!TextUtils.isEmpty(mTxtMedicationForm.getText().toString())) {
            mHourAdapter.notifyDataSetChanged();
            mHourAdapter.notifyItemInserted(mHourItems.size());
            mRecyclerView.setAdapter(mHourAdapter);
            initRecyclerViewHours();
        } else
            Toast.makeText(this, "Choose a medication form", Toast.LENGTH_SHORT).show();

    }

    public void setEditHour(HourItem hourItem){

    }

    public void deleteHour() {
        mHourAdapter.setOnItemClickListener(new HourAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                mHourItems.remove(mHourItems.get(position));
                mHourAdapter.notifyItemRemoved(position);
            }

            @Override
            public void onEditClick(int position) {
                isEdit = true;
                poisitionHourEdited = position;
                //calendar.set(Calendar.HOUR, Integer.valueOf(hourItem.getHourValue().substring(0,1)));
                //calendar.set(Calendar.MINUTE, Integer.valueOf(hourItem.getHourValue().substring(5, 6)));
                showTimePicker();
            }
        });
    }

    private void showPopUpForQuantity(){
        PopUpQuantity popUpQuantity = new PopUpQuantity();
        popUpQuantity.show(getSupportFragmentManager(), "Quantity");
    }

    @Override
    public void setQuantity(int value) {
        if(isEdit == false) {
            lastHourSet.setQuantity(String.valueOf(value));
            mHourItems.add(lastHourSet);
            addHour(hour, minute);
        }
        else{
            lastHourSet.setQuantity(String.valueOf(value));
            mHourItems.set(poisitionHourEdited, lastHourSet);
            mHourAdapter.notifyItemChanged(poisitionHourEdited);
            isEdit = false;
        }
    }


    //      - - - MANAGEMENT OF NEW MEDICATIONS TO STORE - - -


    public void setMedicationsMonthly(List<HourItem> hourItems){
        int totalPerDay = 0;
        for (HourItem hourItem :
                hourItems) {
            totalPerDay += Integer.parseInt(hourItem.getQuantity());
        }
    }

    public void setNewMedicationsToList(List<HourItem> hourItems) {
        int totalPerDay = 0;
        for (HourItem hourItem :
                hourItems) {
            totalPerDay += Integer.parseInt(hourItem.getQuantity());
        }

        //Verify the type of duration
        if(mBtnChooseMedicationDurationType.equals(getString(R.string.text_determinate_duration))){
            setDurationTimeWithDeterminateduration();
        }
        else {
            if(mTxtMedicationFrequency.equals(R.string.text_daily)){
                durationOfTreatment = Integer.parseInt(mTxtMedicationQuantity.getText().toString())%totalPerDay != 0 ? Integer.parseInt(mTxtMedicationQuantity.getText().toString())/totalPerDay + 1 : Integer.parseInt(mTxtMedicationQuantity.getText().toString())/totalPerDay;
            }
        }
        if (!hourItems.isEmpty()) {
            /*for (HourItem hourItem :
                    hourItems) {
                mMedications.add(new Medication(
                        image,
                        mTxtMedicationName.getText().toString(),
                        mTxtMedicationPathology.getText().toString(),
                        mTxtMedicationForm.getText().toString(),
                        Integer.parseInt(mTxtMedicationQuantity.getText().toString()),
                        totalPerDay,
                        hourItem.getHourValue(),
                        Integer.parseInt(hourItem.getQuantity()),
                        mTxtTakingMomentValue.getText().toString(),
                        mTxtMedicationStartingDateValue.getText().toString()
                        )
                );

            }*/

            mMedications.clear();

            for (HourItem hour:
                    mHourItems) {

                Medicine medicine = new Medicine(
                        image,
                        mTxtMedicationName.getText().toString(),
                        mTxtMedicationPathology.getText().toString(),
                        mTxtMedicationForm.getText().toString(),
                        Integer.parseInt(mTxtMedicationQuantity.getText().toString()),
                        totalPerDay,
                        mTxtMedicationFrequency.getText().toString(),
                        mTxtDurationTypeValue.getText().toString(),
                        mTxtMedicationStartingDateValue.getText().toString(),
                        mTxtTakingMomentValue.getText().toString(),
                        durationOfTreatment,
                        hour
                );

                mMedications.add(medicine);
            }
        }
    }

    private void setDurationTimeWithDeterminateduration() {
        switch (mTxtDurationSring.getText().toString().toLowerCase()){
            case "jour":
                durationOfTreatment = Integer.parseInt(mTxtDurationInt.getText().toString());
                break;
            case "day":
                durationOfTreatment = Integer.parseInt(mTxtDurationInt.getText().toString());
                break;
            case "semaine":
                durationOfTreatment = Integer.parseInt(mTxtDurationInt.getText().toString()) * 7;
                break;
            case "week":
                durationOfTreatment = Integer.parseInt(mTxtDurationInt.getText().toString()) * 7;
                break;
            case "mois":
                durationOfTreatment = Integer.parseInt(mTxtDurationInt.getText().toString()) * 30;
                break;
            case "month":
                durationOfTreatment = Integer.parseInt(mTxtDurationInt.getText().toString()) * 30;
                break;
            case "bimestre":
                durationOfTreatment = Integer.parseInt(mTxtDurationInt.getText().toString()) * 60;
                break;
            case "bimester":
                durationOfTreatment = Integer.parseInt(mTxtDurationInt.getText().toString()) * 60;
                break;
            case "semestre":
                durationOfTreatment = Integer.parseInt(mTxtDurationInt.getText().toString()) * 180;
                break;
            case "semester":
                durationOfTreatment = Integer.parseInt(mTxtDurationInt.getText().toString()) * 180;
                break;
            case "trimestre":
                durationOfTreatment = Integer.parseInt(mTxtDurationInt.getText().toString()) * 90;
                break;
            case "trimester":
                durationOfTreatment = Integer.parseInt(mTxtDurationInt.getText().toString()) * 90;
                break;
            case "année":
                durationOfTreatment = Integer.parseInt(mTxtDurationInt.getText().toString()) * 365;
                break;
            case "year":
                durationOfTreatment = Integer.parseInt(mTxtDurationInt.getText().toString()) * 365;
                break;
        }
    }

    public String serializeMedication(List<Medicine> medications) {
        Gson gson = new Gson();
        return gson.toJson(medications);
    }

    public String serializeMedication(Medicine medication) {
        Gson gson = new Gson();
        return gson.toJson(medication);
    }

    public String serializeHour(List<HourItem> hours) {
        Gson gson = new Gson();
        return gson.toJson(hours);
    }

    public String serializeHour(HourItem hour) {
        Gson gson = new Gson();
        return gson.toJson(hour);
    }

    public void passToMedicationsActivity() {
        mBtnValidateAddingMedication.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAnEditItem == false && mTxtDurationTypeValue.getText().toString().equals(getString(R.string.text_indeterminate_duration)))
                {
                    setNewMedicationsToList(mHourItems);
                    Intent intent = new Intent(NewMedicationActivity.this, Accueil.class);
                    intent.putExtra("NewItem", new Gson().toJson(mMedications));
                    startActivity(intent);
                    finish();
                }
                else if (isAnEditItem == true) {
                    setNewMedicationsToList(mHourItems);
                    Intent intent = new Intent(NewMedicationActivity.this, Accueil.class);
                    intent.putExtra("ItemModified", new Gson().toJson(mMedications));
                    intent.putExtra("positionEditedItem", new Gson().toJson(positionEditedItem));
                    startActivity(intent);
                    finish();

                }
            }
        });

        isAnEditItem = false;
    }


    //      - - - MANAGEMENT OF DATABASE - - -


    private void storeToSharedPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mMedications);
        editor.putString(SP_NAME_STRING, json);
        editor.apply();
    }

    public boolean areFieldsCorrect() {
        boolean result = true;
        if (TextUtils.isEmpty(mTxtMedicationName.getText())) {
            mTxtMedicationName.setError(getText(R.string.text_error_mediction_name));
            result = false;
        } else if (TextUtils.isEmpty(mTxtMedicationPathology.getText())) {
            mTxtMedicationPathology.setError(getText(R.string.text_error_pathology));
            result = false;
        } else if (TextUtils.isEmpty(mTxtMedicationName.getText())) {
            mTxtMedicationForm.setError(getText(R.string.text_error_medication_form));
            result = false;
        } else if (TextUtils.isEmpty(mTxtMedicationQuantity.getText())) {
            mTxtMedicationQuantity.setError(getText(R.string.text_error_medication_quantity));
            result = false;
        } else if (TextUtils.isEmpty(mTxtMedicationFrequency.getText())) {
            mTxtMedicationFrequency.setError(getText(R.string.text_error_medication_frequency));
            result = false;
        } else if (TextUtils.isEmpty(mTxtDurationTypeValue.getText())) {
            mTxtDurationTypeValue.setError(getText(R.string.text_error_medication_type_duration));
            result = false;
        } else if (mTxtDurationTypeValue.getText().equals(getText(R.string.text_determinate_duration))) {
            if (TextUtils.isEmpty(mTxtMedicationDurationIntValue.getText())) {
                mTxtMedicationDurationIntValue.setError(getText(R.string.text_errore_duration_medication));
                result = false;
            }
            if (TextUtils.isEmpty(mTxtMedicationDurationStringValue.getText())) {
                mTxtMedicationDurationIntValue.setError(getText(R.string.text_errore_duration_medication));
                result = false;
            }
        }
        if (TextUtils.isEmpty(mTxtTakingMomentValue.getText())) {
            mTxtTakingMomentValue.setError(getText(R.string.text_error_moment_taking_medication));
            result = false;
        }
        if (HoursActivity.mHourItems.isEmpty()) {
            Toast.makeText(this, "Veuillez renseigner les horaires de prise", Toast.LENGTH_SHORT).show();
            result = false;
        }
        return result;
    }


    //      --- ALARMS ---

    private void deleteAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, 0);

        alarmManager.cancel(pendingIntent);
    }

    private void addAlarm() {
        /*AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, 0);


        //alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, 0, repeatTime, pendingIntent);
*/

        //int days = 0;
        if (!mMedications.isEmpty()) {
            for (Medicine medication :
                    mMedications) {
                int days = 0;
                for (HourItem hour :
                        medication.getHourItems()) {
                    days += Integer.valueOf(hour.getQuantity());
                    String endingDate = String.valueOf(Date.parse(mTxtMedicationStartingDateValue.getText().toString()) + days * 1000);


                }
            }
        }

        // Alarm alarm = new Alarm(mTxtMedicationStartingDateValue.getText().toString(),  endingDate, repeatTime, )
    }

    private void notifyAlarm(int id) {
        String title = mTxtMedicationName.getText().toString() + " - " + mTxtMedicationPathology.getText().toString();
        String content = mTxtTakingMomentValue.getText().toString();
        NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());
        NotificationCompat.Builder nb = notificationHelper.getChannelNotificationForALert(title, content);
        //notificationHelper.getManager().notify(id, nb.build());

        /*Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

        Intent broadcastIntent = new Intent(getApplicationContext(), NotificationReceiver.class);
        broadcastIntent.putExtra("Action", "Text");

        PendingIntent acionIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(getApplicationContext(), "Channel1")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Title")
                .setContentText("Text")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setContentIntent(pendingIntent)
                .addAction(R.mipmap.ic_launcher, "Pris", acionIntent)
                .addAction(R.mipmap.ic_launcher, "Non Pris", acionIntent)
                .setColor(Color.RED)
                .build();

        notificationManagerCompat.notify(1, notification);*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void requestStoragePermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed to access photos in the storage")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(NewMedicationActivity.this,
                                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
