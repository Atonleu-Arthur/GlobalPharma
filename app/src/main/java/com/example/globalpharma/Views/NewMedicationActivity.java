package com.example.globalpharma.Views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.provider.BaseColumns;
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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.globalpharma.Model.Alarm;
import com.example.globalpharma.Model.HourItem;
import com.example.globalpharma.Model.Medication;
import com.example.globalpharma.Model.MedicationForm;
import com.example.globalpharma.R;
import com.example.globalpharma.controller.AlarmThread;
import com.example.globalpharma.controller.AlertReceiver;
import com.example.globalpharma.controller.NotificationHelper;
import com.example.globalpharma.controller.NotificationReceiver;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class NewMedicationActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    private ImageView mBtnChooseMedicationForm;
    private ImageView mBtnAddHour;
    private ImageView mBtnAddHourMoreThan;
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
    private TextInputEditText mTxtMedicationName;
    private TextInputEditText mTxtMedicationPathology;
    private TextInputEditText mTxtMedicationForm;
    private TextInputEditText mTxtMedicationQuantity;
    private TextInputEditText mTxtTakingMomentValue;
    private TextInputEditText mTxtMedicationStartingDateValue;
    private TextInputEditText mTxtMedicationDurationIntValue;
    private TextInputEditText mTxtMedicationDurationStringValue;
    private TextInputEditText mTxtDurationTypeValue;
    private TextInputEditText mTxtMedicationNotes;
    private RecyclerView mRecyclerView;
    private String imagePath;
    private static final int RESULT_CODE_TAKING_PHOTO = 2;
    private Bitmap image;
    private String imageName;
    private List<Medication> mMedications;
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

    //Image storage
    private static final String DB_STORAGE_IMG_PATH = "Medication";
    private Uri downloadImageUrl;
    private UploadTask mUploadTaskImage;
    private FileOutputStream mFileOutputStream;

    //DbHelper

    private int month, year, day, hour, minute;

    private long repeatTime;

    private long selectedTimeStamp;

    private String title;

    private String time;

    private String date;

    private String repeat;

    private String noRepeat;

    private String repeatType;

    private String active;

    private Calendar calendar;

    private Uri currentReminderUri;

    private boolean alarmHasChanged = false;

    private static final String TABLE_NAME = "ALARMS";

    private static final String ID = BaseColumns._ID;

    private static final String KEY_TITLE = "TITLE";

    private static final String KEY_DATE = "DATE";

    private static final String KEY_TIME = "TIME";

    private static final String KEY_REPEAT = "REPEAT";

    private static final String KEY_REPEAT_NO = "REPEAT_NO";

    private static final String KEY_REPEAT_TYPE = "REPEAT_TYPE";

    private static final String KEY_ACTIVE = "ACTIVE";

    private NotificationManagerCompat notificationManager;

    //constants time
    public static final long milMinute = 60000L;
    public static final long milHour = 3600000L;
    public static final long milDay = 86400000L;
    public static final long milWeek = 6048000000L;
    public static final long milMonth = 2592000000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_medication);
        getSupportActionBar().hide();

        //Toast.makeText(this, (new File(Environment.getExternalStorageDirectory()+ "/MALSJD")).toString(), Toast.LENGTH_LONG).show();

        initActivity();

        //Choisir la photo du médicament
        addPhoto();

        //Affiche l'activité qui mène aux formes de médicaments
        showMedicationForms();

        setMedicationFormName();

        setMedicationFormImage();

        //Choix de la fréquence de prise de médicament
        showMedicationFrequencies();

        //Choix de la date début
        showDatePicker();

        //Choix de la fréquence de prise de médicament
        showDurationTypes();

        showMomentsTaking();

        showDurationValues();

        showTimePicker();

        showHours();

        initRecyclerViewHours();

        passToMedicationsActivity();
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
        mTxtMedicationName = findViewById(R.id.text_add_medication_name);
        mTxtMedicationForm = findViewById(R.id.text_add_medication_form);
        mTxtMedicationPathology = findViewById(R.id.text_add_medication_pathology);
        mTxtMedicationFrequency = findViewById(R.id.sp_add_frequency_medication);
        mTxtMedicationQuantity = findViewById(R.id.text_quantity);
        mTxtTakingMomentValue = findViewById(R.id.text_add_moment_taking_value);
        mTxtMedicationStartingDateValue = findViewById(R.id.text_starting_date_value);
        mTxtMedicationDurationIntValue = findViewById(R.id.text_add_medication_duration_int_value);
        mTxtDurationTypeValue = findViewById(R.id.text_add_medication_type_duration);
        mTxtMedicationDurationStringValue = findViewById(R.id.text_add_medication_duration_value);
        mTxtMedicationNotes = findViewById(R.id.text_add_medication_notes);
        mTxtAddPhoto = findViewById(R.id.text_add_medication_picture);
        mRecyclerView = findViewById(R.id.rv_hours);

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

        } else if (requestCode == RESULT_CODE_TAKING_PHOTO && resultCode == RESULT_OK) {
            //Photo prise avec la caméra
            image = BitmapFactory.decodeFile(imagePath);

            saveImageToGallery(image);

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
            File imagesFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Global Pharma");
            if (!imagesFolder.exists()) {
                imagesFolder.mkdir();
                MediaStore.Images.Media.insertImage(getContentResolver(), imagePath, imageName, "");
            } else {
                Toast.makeText(this, imagesFolder.toString(), Toast.LENGTH_LONG).show();

                MediaStore.Images.Media.insertImage(getContentResolver(), imagesFolder.toString(), imageName, "");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
    }


    //      - - - MANAGEMENT OF MEDICATION FORMS NAMES AND IMAGES - - -

    public String getMedicationFormDataFromIntent() {
        if (getIntent().hasExtra("SelectedMedicationFormInfo"))
            return getIntent().getStringExtra("SelectedMedicationFormInfo");
        else
            return null;
    }

    public MedicationForm getMedicationInfos() {
        MedicationForm medication = null;
        if (getMedicationFormDataFromIntent() != null) {
            mImageMedicationForm.setVisibility(View.VISIBLE);
            Gson gson = new Gson();
            Type medicationFormType = new TypeToken<MedicationForm>() {
            }.getType();
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
                takePhotoWithCamera();
                return true;
            }
            case R.id.item_choose_from_gallery: {
                choosePictureFromGallery();
                return true;
            }
            case R.id.item_daily: {
                getNameFrequency(item);
                mLayoutFrequencies.setVisibility(View.GONE);
                repeatType = "daily";
                return true;
            }
            case R.id.item_weekly: {
                getNameFrequency(item);
                mLayoutFrequencies.setVisibility(View.VISIBLE);
                mLayoutWeekly.setVisibility(View.VISIBLE);
                mLayoutFrequencies.setMaxHeight(mLayoutWeekly.getMaxHeight());
                mLayoutFrequencies.setMinHeight(mLayoutWeekly.getMinHeight());
                setLayoutInvisible(mLayoutMonthly);
                setLayoutInvisible(mLayoutAllThe);
                repeatType = "weekly";
                return true;
            }
            case R.id.item_monthly: {
                getNameFrequency(item);
                mLayoutFrequencies.setVisibility(View.VISIBLE);
                mLayoutMonthly.setVisibility(View.VISIBLE);
                mLayoutFrequencies.setMaxHeight(mLayoutMonthly.getMaxHeight());
                mLayoutFrequencies.setMinHeight(mLayoutMonthly.getMinHeight());
                setLayoutInvisible(mLayoutWeekly);
                setLayoutInvisible(mLayoutAllThe);
                repeatType = "monthly";
                return true;
            }
            case R.id.item_all_the: {
                getNameFrequency(item);
                mLayoutFrequencies.setVisibility(View.VISIBLE);
                mLayoutAllThe.setVisibility(View.VISIBLE);
                mLayoutFrequencies.setMaxHeight(mLayoutAllThe.getMaxHeight());
                mLayoutFrequencies.setMinHeight(mLayoutAllThe.getMinHeight());
                setLayoutInvisible(mLayoutWeekly);
                setLayoutInvisible(mLayoutMonthly);
                repeatType = "all the";
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
        mBtnChooseMedicationMoment.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp(v, R.menu.pop_up_menu_taking_moment);
            }
        });
    }


    //      - - - MANAGEMENT OF TIMES - - -

    public void showTimePicker() {
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
        Toast.makeText(this, hourOfDay + " : " + minute, Toast.LENGTH_SHORT).show();
        addHour(hourOfDay, minute);
        addAlarm();
    }

    public void showHours() {
        mBtnAddHourMoreThan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewMedicationActivity.this, HoursActivity.class);
                startActivity(intent);
            }
        });
    }

    public void addHour(int hourOfDay, int minute) {
        if (!TextUtils.isEmpty(mTxtMedicationForm.getText().toString())) {
            mHourItems.add(new HourItem("Horaire " + (mHourItems.size() + 1), hourOfDay + " : " + minute,
                    mTxtMedicationForm.getText().toString(), "2"));
            mHourAdapter.notifyDataSetChanged();
            mHourAdapter.notifyItemInserted(mHourItems.size());
            mRecyclerView.setAdapter(mHourAdapter);
            initRecyclerViewHours();
        } else
            Toast.makeText(this, "Choose a medication form", Toast.LENGTH_SHORT).show();

    }


    //      - - - MANAGEMENT OF NEW MEDICATIONS TO STORE - - -

    public void setNewMedicationsToList(List<HourItem> hourItems) {
        if (!hourItems.isEmpty()) {
            Medication medication = new Medication(
                    mTxtMedicationName.getText().toString(),
                    mTxtMedicationPathology.getText().toString(),
                    mTxtMedicationForm.getText().toString(),
                    mTxtMedicationStartingDateValue.getText().toString(),
                    mTxtMedicationQuantity.getText().toString(),
                    serializeHour(mHourItems),
                    mTxtTakingMomentValue.getText().toString(),
                    image
            );

            mMedications.add(medication);
        }
    }

    public String serializeMedication(List<Medication> medications) {
        Gson gson = new Gson();
        return gson.toJson(medications);
    }

    public String serializeMedication(Medication medication) {
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
                    try {
                        //setNewMedicationsToList(mHourItems);
                        Intent intent = new Intent(NewMedicationActivity.this, Accueil.class);
                        intent.putExtra("medications List", serializeMedication(mMedications));
                        startActivity(intent);
                        finish();
                    }
                    catch (Exception e){
                        Log.d("Error: ", e.toString());
                    }
                //notifyAlarm(1);
                //addAlarm(calendar);
            }
        });
    }


    //      - - - MANAGEMENT OF DATABASE - - -


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


        fixRepeatTime();
        //alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, 0, repeatTime, pendingIntent);
*/

        //int days = 0;
        if (!mMedications.isEmpty()) {
            for (Medication medication :
                    mMedications) {
                int days = 0;
                for (HourItem hour :
                        medication.getHourItem()) {
                    days += Integer.valueOf(hour.getQuantity());
                    String endingDate = String.valueOf(Date.parse(mTxtMedicationStartingDateValue.getText().toString()) + days * 1000);


                }
            }
        }

        // Alarm alarm = new Alarm(mTxtMedicationStartingDateValue.getText().toString(),  endingDate, repeatTime, )
    }

    private void fixRepeatTime() {
        switch (repeatType.toLowerCase()) {
            case "all the":
                repeatTime = Integer.parseInt(noRepeat) * milHour;
                break;
            case "daily":
                repeatTime = Integer.parseInt(noRepeat) * milDay;
                break;
            case "weekly":
                repeatTime = Integer.parseInt(noRepeat) * milWeek;
                break;
            case "monthly":
                repeatTime = Integer.parseInt(noRepeat) * milMonth;
                break;
            default:
                break;
        }

    }

    private void notifyAlarm(int id) {
        String title = mTxtMedicationName.getText().toString() + " - " + mTxtMedicationPathology.getText().toString();
        String content = mTxtTakingMomentValue.getText().toString();
        NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());
        NotificationCompat.Builder nb = notificationHelper.getChannelNotificationForALert(title, content);
        notificationHelper.getManager().notify(id, nb.build());

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

}
