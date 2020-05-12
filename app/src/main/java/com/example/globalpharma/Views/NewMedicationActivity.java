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

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

import com.example.globalpharma.Model.AlarmContract;
import com.example.globalpharma.Model.AlarmScheduler;
import com.example.globalpharma.Model.HourItem;
import com.example.globalpharma.Model.Medication;
import com.example.globalpharma.R;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
        TimePickerDialog.OnTimeSetListener, LoaderManager.LoaderCallbacks<Cursor> {

    private ImageView mBtnChooseMedicationForm;
    private ImageView mBtnAddHour;
    private ImageView mBtnAddHourMoreThan;
    private TextView mTxtAddPhoto;
    private Button mBtnChooseMedicationFrequency;
    private Button mBtnChooseMedicationDurationType;
    private Button mBtnValidateAddingMedication;
    private Button mBtnChooseMedicationMoment;
    private ImageView mImageViewMedicationPicture;
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
    private TextInputEditText mTxtMedicationTakingMomentValue;
    private TextInputEditText mTxtMedicationStartingDateValue;
    private TextInputEditText mTxtMedicationDurationIntValue;
    private TextInputEditText mTxtMedicationDurationStringValue;
    private TextInputEditText mTxtMedicationDurationType;
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
    private NotificationManagerCompat mNotificationManagerCompat;
    private static final String DB_STORAGE_IMG_PATH = "Medication";
    private Uri downloadImageUrl;
    private UploadTask mUploadTaskImage;

    //DbHelper

    private int month, year, day,  hour, minute;

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

    private static final String KEY_TITLE = "TITLE" ;

    private static final String KEY_DATE = "DATE" ;

    private static final String KEY_TIME = "TIME" ;

    private static final String KEY_REPEAT = "REPEAT" ;

    private static final String KEY_REPEAT_NO = "REPEAT_NO" ;

    private static final String KEY_REPEAT_TYPE = "REPEAT_TYPE" ;

    private static final String KEY_ACTIVE = "ACTIVE" ;

    //constants time
    public static final long milMinute = 60000L;
    public static final long milHour = 3600000L;
    public static final long milDay = 86400000L;
    public static final long milWeek = 6048000000L;
    public static final long milMonth = 2592000000L;

    private View.OnTouchListener mOnTouchListener = (view, notionEvent) -> {
            alarmHasChanged = true;
            return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_medication);
        getSupportActionBar().hide();

        initActivity();

        initRecyclerViewHours();

        //Affiche l'activité qui mène aux formes de médicaments
        showMedicationForms();

        //Choisir la photo du médicament
        addPhoto();

        //Choix de la fréquence de prise de médicament
        showMedicationFrequencies();

        //Choix de la fréquence de prise de médicament
        showDurationTypes();

        showMomentsTaking();

        showDurationValues();

        showDatePicker();

        showTimePicker();

        showHours();

        setMedicationFormName();

        passNewMedicationsToList();

        initAlarmDefaultValues();
    }

    //      - - - INITIATION OF VIEW - - -

    private void initRecyclerViewHours() {
        if (mHourItems.isEmpty())
            mRecyclerView.setVisibility(View.GONE);
        else
            mRecyclerView.setVisibility(View.VISIBLE);
    }

    public void initActivity(){
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
        mTxtMedicationName= findViewById(R.id.text_add_medication_name);
        mTxtMedicationForm = findViewById(R.id.text_add_medication_form);
        mTxtMedicationPathology = findViewById(R.id.text_add_medication_pathology);
        mTxtMedicationFrequency = findViewById(R.id.sp_add_frequency_medication);
        mTxtMedicationQuantity = findViewById(R.id.text_quantity);
        mTxtMedicationTakingMomentValue = findViewById(R.id.text_add_moment_taking_value);
        mTxtMedicationStartingDateValue = findViewById(R.id.text_starting_date_value);
        mTxtMedicationDurationIntValue = findViewById(R.id.text_add_medication_duration_int_value);
        mTxtMedicationDurationStringValue = findViewById(R.id.text_add_medication_duration_value);
        mTxtMedicationDurationType = findViewById(R.id.text_add_medication_type_duration);
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

        mHourItems.add(new HourItem("Horaire 1", "13:45", "Gellule", "04"));
        mHourItems.add(new HourItem("Horaire 1", "13:45", "Gellule", "04"));
        mHourItems.add(new HourItem("Horaire 1", "13:45", "Gellule", "04"));
        mHourItems.add(new HourItem("Horaire 1", "13:45", "Gellule", "04"));
        mHourItems.add(new HourItem("Horaire 1", "13:45", "Gellule", "04"));
        mHourItems.add(new HourItem("Horaire 1", "13:45", "Gellule", "04"));
        mHourItems.add(new HourItem("Horaire 1", "13:45", "Gellule", "04"));
        mHourItems.add(new HourItem("Horaire 1", "13:45", "Gellule", "04"));
        mHourItems.add(new HourItem("Horaire 1", "13:45", "Gellule", "04"));
        mHourItems.add(new HourItem("Horaire 1", "13:45", "Gellule", "04"));
        mHourItems.add(new HourItem("Horaire 1", "13:45", "Gellule", "04"));
        mHourItems.add(new HourItem("Horaire 1", "13:45", "Gellule", "04"));
        mHourItems.add(new HourItem("Horaire 1", "13:45", "Gellule", "04"));
        mHourItems.add(new HourItem("Horaire 1", "13:45", "Gellule", "04"));
        mHourItems.add(new HourItem("Horaire 1", "13:45", "Gellule", "04"));
        mHourItems.add(new HourItem("Horaire 1", "13:45", "Gellule", "04"));

        mHourAdapter = new HourAdapter(NewMedicationActivity.this, mHourItems);
        mRecyclerView.setAdapter(mHourAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Default states of values
        //String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        Calendar calendar = Calendar.getInstance();
        /*String dateS = calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.MONTH)
                + " " + calendar.get(Calendar.YEAR);*/
        String dateString = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(calendar.getTime());
        mTxtMedicationStartingDateValue.setText(dateString);

        mNotificationManagerCompat = NotificationManagerCompat.from(this);
    }

    //      - - - MANAGEMENT OF PHOTOS - - -

    public void addPhoto(){
        mConstraintLayoutAddPhoto.setOnClickListener(new ConstraintLayout.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp(v, R.menu.pop_up_get_photo_mode_menu);
            }
        });
    }

    public void choosePictureFromGallery(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, 1);
    }

    public void takePhotoWithCamera(){
        Intent captureImageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(captureImageIntent.resolveActivity(getPackageManager()) != null){
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
        if(requestCode == 1 && resultCode == RESULT_OK){
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
                Bitmap image1 = MediaStore.Images.Media.getBitmap(getContentResolver(), choosenImage);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Bitmap image = BitmapFactory.decodeFile(imagePath);

            imageName = imagePath.substring(imagePath.lastIndexOf("/") + 1);
            mTxtAddPhoto.setText(imagePath.substring(imagePath.lastIndexOf("/") + 1, imagePath.lastIndexOf("/") + 10) + ".." + imagePath.substring(imagePath.length() - 5));

        }
        else if(requestCode == RESULT_CODE_TAKING_PHOTO && resultCode == RESULT_OK){
            //Photo prise avec la caméra
            Bitmap image = BitmapFactory.decodeFile(imagePath);

            saveImageToGallery(image);
            imageName = imagePath.substring(imagePath.lastIndexOf("/") + 1);
            mTxtAddPhoto.setText(imagePath.substring(imagePath.lastIndexOf("/") + 1, imagePath.lastIndexOf("/") + 10) + ".." + imagePath.substring(imagePath.length() - 5, imagePath.length()));

             uploadPhoto();
        }
    }

    public void saveImageToGallery(Bitmap image){
        try {
            MediaStore.Images.Media.insertImage(getContentResolver(), imagePath, imageName, "Tof");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void uploadPhoto(){
        Uri file = Uri.fromFile(new File(imagePath));
        String fileName = UUID.randomUUID().toString();

        mUploadTaskImage = Medication.uploadImageToDatabase(file, DB_STORAGE_IMG_PATH + "/" + fileName);

        //return mUploadTaskImage.getSnapshot().getDownloadUrl();
    }

    //      - - - MANAGEMENT OF POP UP - - -

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
                mLayoutFrequencies.setVisibility(View.GONE);
                return true;
            }
            case R.id.item_weekly:{
                getNameFrequency(item);
                mLayoutFrequencies.setVisibility(View.VISIBLE);
                mLayoutWeekly.setVisibility(View.VISIBLE);
                mLayoutFrequencies.setMaxHeight(mLayoutWeekly.getMaxHeight());
                mLayoutFrequencies.setMinHeight(mLayoutWeekly.getMinHeight());
                setLayoutInvisible(mLayoutMonthly);
                setLayoutInvisible(mLayoutAllThe);
                return true;
            }
            case R.id.item_monthly:{
                getNameFrequency(item);
                mLayoutFrequencies.setVisibility(View.VISIBLE);
                mLayoutMonthly.setVisibility(View.VISIBLE);
                mLayoutFrequencies.setMaxHeight(mLayoutMonthly.getMaxHeight());
                mLayoutFrequencies.setMinHeight(mLayoutMonthly.getMinHeight());
                setLayoutInvisible(mLayoutWeekly);
                setLayoutInvisible(mLayoutAllThe);
                return true;
            }
            case R.id.item_all_the:{
                getNameFrequency(item);
                mLayoutFrequencies.setVisibility(View.VISIBLE);
                mLayoutAllThe.setVisibility(View.VISIBLE);
                mLayoutFrequencies.setMaxHeight(mLayoutAllThe.getMaxHeight());
                mLayoutFrequencies.setMinHeight(mLayoutAllThe.getMinHeight());
                setLayoutInvisible(mLayoutWeekly);
                setLayoutInvisible(mLayoutMonthly);
                return true;
            }
            case R.id.item_determinate_duration:{
                mTxtMedicationDurationType.setText(item.getTitle());
                mLayoutDuration.setVisibility(View.VISIBLE);
            }
            case R.id.item_indeterminate_duration:{
                mTxtMedicationDurationType.setText(item.getTitle());
                mLayoutDuration.setVisibility(View.GONE);
            }
            case R.id.item_before_eating:{
                mTxtMedicationTakingMomentValue.setText(item.getTitle());
            }
            case R.id.item_while_eating:{
                mTxtMedicationTakingMomentValue.setText(item.getTitle());
            }
            case R.id.item_after_eating:{
                mTxtMedicationTakingMomentValue.setText(item.getTitle());
            }
            case R.id.item_out_of_eating:{
                mTxtMedicationTakingMomentValue.setText(item.getTitle());
            }
            case R.id.item_whatever:{
                mTxtMedicationTakingMomentValue.setText(item.getTitle());
            }
            default:{
                return false;
            }
        }
    }

    //      - - - MANAGEMENT OF MEDICATION FORMS - - -

    public void showMedicationForms(){
        mBtnChooseMedicationForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewMedicationActivity.this, MedicationFormsActivity.class));
            }
        });
    }

    //      - - - MANAGEMENT OF MEDICATION FREQUENCIES - - -

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

    //      - - - MANAGEMENT OF LAYOUTS - - -

    public void setLayoutInvisible(ConstraintLayout constraintLayout){
        constraintLayout.setVisibility(View.INVISIBLE);
    }

    //      - - - MANAGEMENT OF TAKING MOMENTS OF MEDICATION - - -

    public void showMomentsTaking(){
        mBtnChooseMedicationMoment.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp(v, R.menu.pop_up_menu_taking_moment);
            }
        });
    }


    //      - - - MANAGEMENT OF TYPE OF DURATION OF MEDICATION - - -

    public void showDurationTypes(){
        mBtnChooseMedicationDurationType.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp(v, R.menu.pop_up_menu_duration_type);
            }
        });
    }

    public void showDurationValues(){
        mTxtMedicationDurationStringValue.setOnClickListener(new TextInputEditText.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp(v, R.menu.pop_up_menu_duration_suggestions);
            }
        });
    }

    //      - - - MANAGEMENT OF DATES - - -

    public void showDatePicker(){
        mTxtMedicationStartingDateValue.setOnClickListener(new ConstraintLayout.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
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
        day = dayOfMonth;
        this.month = month;
        this.year = year;
        this.date = day + "-" + this.month + "-" + this.year;
        String dateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        try {
            choosenDate = mSimpleDateFormat.parse(dateString);
            if (choosenDate.before(mSimpleDateFormat.parse(mTxtMedicationStartingDateValue.getText().toString()))) {
                Toast.makeText(this, getText(R.string.text_error_starting_date_medication), Toast.LENGTH_SHORT).show();
                showDatePicker();
            }
            else
                mTxtMedicationStartingDateValue.setText(dateString);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //      - - - MANAGEMENT OF TIMES - - -

    public void showTimePicker(){
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
        if(this.minute < 10)
            this.time = hour + " : 0" + this.minute;
        else
            this.time = hour + " : " + this.minute;
        selectedTimeStamp = calendar.getTimeInMillis();
        Toast.makeText(this, hourOfDay + " : " + minute, Toast.LENGTH_SHORT).show();
        addHour(hourOfDay, minute);
    }

    public void showHours(){
        mBtnAddHourMoreThan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewMedicationActivity.this, HoursActivity.class);
                startActivity(intent);
            }
        });
    }

    public void addHour(int hourOfDay, int minute){
       mHourItems.add(new HourItem("1", hourOfDay + " : " + minute,
            "Gellule", "8"));
       mHourAdapter.notifyDataSetChanged();
       mHourAdapter.notifyItemInserted(mHourItems.size());
       mRecyclerView.setAdapter(mHourAdapter);
    }

    //      - - - MANAGEMENT OF MEDICATION NAMES - - -

    public String getMedicationFormName(){
        if(getIntent().hasExtra("mTxtMedicationForm"))
            return getIntent().getStringExtra("mTxtMedicationForm");
        else
            return null;
    }

    public void setMedicationFormName(){
        if (getMedicationFormName() != null)
            mTxtMedicationForm.setText(getMedicationFormName());
    }

    //      - - - MANAGEMENT OF NEW MEDICATIONS TO STORE - - -

    public void passNewMedicationsToList(){
        mBtnValidateAddingMedication.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(true == true){
                    addMedicationsToDatabase();
                    Intent intent = new Intent(NewMedicationActivity.this, Accueil.class);
                    passMedicationsList(mMedications, intent);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public String serializeMedications(List<Medication> objects){
        Gson gson = new Gson();
        return gson.toJson(objects);
    }

    public void passMedicationsList(List<Medication> medicationlist, Intent intent){
        Medication medication = new Medication("Paracétamol", "Head", "Comprimé", "4", "12h35", null, null, image);
        Medication medication1 = new Medication("Paracétamol1", "Head", "Comprimé", "4", "12h35", null, null, image);
        Medication medication2 = new Medication("Paracétamol2", "Head", "Comprimé", "4", "12h35", null, null, image);
        Medication medication3 = new Medication("Paracétamol3", "Head", "Comprimé", "4", "12h35", null, null, image);
        Medication medication4 = new Medication("Paracétamol4", "Head", "Comprimé", "4", "12h35", null, null, image);

        medicationlist.add(medication);
        medicationlist.add(medication1);
        medicationlist.add(medication2);
        medicationlist.add(medication3);
        medicationlist.add(medication4);

        intent.putExtra("medications List", serializeMedications(medicationlist));
        addAlarm(new HourItem("Month", "12h48", "Comprimé", "5"));
    }

    //      - - - MANAGEMENT OF DATABASE - - -

    public void addMedicationsToDatabase(){
        Medication.setMedicationToDatabase(new Medication());

        uploadPhoto();
        downloadImageUrl = mUploadTaskImage.getSnapshot().getDownloadUrl();
    }

    public String getExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public boolean areFieldsCorrect(){
        boolean result = true;
        if(TextUtils.isEmpty(mTxtMedicationName.getText())){
            mTxtMedicationName.setError(getText(R.string.text_error_mediction_name));
            result = false;
        }
        else if(TextUtils.isEmpty(mTxtMedicationPathology.getText())){
            mTxtMedicationPathology.setError(getText(R.string.text_error_pathology));
            result = false;
        }
        else if(TextUtils.isEmpty(mTxtMedicationName.getText())){
            mTxtMedicationForm.setError(getText(R.string.text_error_medication_form));
            result = false;
        }
        else if(TextUtils.isEmpty(mTxtMedicationQuantity.getText())){
            mTxtMedicationQuantity.setError(getText(R.string.text_error_medication_quantity));
            result = false;
        }
        else if(TextUtils.isEmpty(mTxtMedicationFrequency.getText())){
            mTxtMedicationFrequency.setError(getText(R.string.text_error_medication_frequency));
            result = false;
        }
        else if(TextUtils.isEmpty(mTxtMedicationDurationType.getText())){
            mTxtMedicationDurationType.setError(getText(R.string.text_error_medication_type_duration));
            result = false;
        }
        else if(mTxtMedicationDurationType.getText().equals(getText(R.string.text_determinate_duration))){
            if(TextUtils.isEmpty(mTxtMedicationDurationIntValue.getText())){
                mTxtMedicationDurationIntValue.setError(getText(R.string.text_errore_duration_medication));
                result = false;
            }
            if(TextUtils.isEmpty(mTxtMedicationDurationStringValue.getText())){
                mTxtMedicationDurationIntValue.setError(getText(R.string.text_errore_duration_medication));
                result = false;
            }
        }
        if(TextUtils.isEmpty(mTxtMedicationTakingMomentValue.getText())){
            mTxtMedicationTakingMomentValue.setError(getText(R.string.text_error_moment_taking_medication));
            result = false;
        }
        if(HoursActivity.mHourItems.isEmpty()){
            Toast.makeText(this, "Veuillez renseigner les horaires de prise", Toast.LENGTH_SHORT).show();
            result = false;
        }
        return result;
    }

    public void insertHour(int hourOfDay, int minute){
        Intent intent = new Intent(NewMedicationActivity.this, HoursActivity.class);
        intent.putExtra("hour", new HourItem("1", hourOfDay + " : " + minute,
                "Gellule", "8"));
        startActivity(intent);
    }

    public void addAlarm(HourItem hourItem){
        ContentValues values = new ContentValues();

        values.put(AlarmContract.AlarmEntry.KEY_TITLE, mTxtMedicationPathology.getText().toString());
        values.put(AlarmContract.AlarmEntry.KEY_DATE, mTxtMedicationStartingDateValue.getText().toString());
        values.put(AlarmContract.AlarmEntry.KEY_TIME, hourItem.getHourValue());
        values.put(AlarmContract.AlarmEntry.KEY_REPEAT, repeat);
        values.put(AlarmContract.AlarmEntry.KEY_REPEAT_NO, noRepeat);
        values.put(AlarmContract.AlarmEntry.KEY_REPEAT_TYPE, repeatType);
        values.put(AlarmContract.AlarmEntry.KEY_ACTIVE, active);

        fixRepeatTime();

        if(currentReminderUri == null){
            Uri uri =  getContentResolver().insert(AlarmContract.AlarmEntry.CONTENT_URI, values);
            if(uri == null){
                //Error
                Toast.makeText(this, "Alarm erronée", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Alarm inserted", Toast.LENGTH_SHORT).show();
                notifyAlarm();
            }
        }
        else{
            int rowsAffected = getContentResolver().update(currentReminderUri, values, null, null);
            if(rowsAffected == 0){
                //Error
            }
            else{
                //Done
            }
        }

    }

    public void initAlarmDefaultValues(){Intent intent = getIntent();
        currentReminderUri = intent.getData();

        if(currentReminderUri == null){
            setTitle("Add alarms details");
            invalidateOptionsMenu();
        }
        else{
            setTitle("Edit Reminder");
        }

        active = "true";
        repeat = "true";
        noRepeat = Integer.toString(1);
        repeatType = "day";
        calendar = Calendar.getInstance();
        minute = calendar.get(Calendar.MINUTE);
        hour = calendar.get(Calendar.HOUR);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        date = day + "-" + month + "-" + year;
        time = hour + " : " + minute;
    }

    private void deleteAlarm(){
        if(currentReminderUri != null){
            int deletedRows = getContentResolver().delete(currentReminderUri, null, null);
            if(deletedRows == 0){
                //Error
            }
            else{
                //deleted
            }
        }
    }

    private void fixRepeatTime(){
        switch (repeatType.toLowerCase()){
            case "minute":
                repeatTime = Integer.parseInt(noRepeat) * milMinute;
                break;
            case "hour":
                repeatTime = Integer.parseInt(noRepeat) * milHour;
                break;
            case "day":
                repeatTime = Integer.parseInt(noRepeat) * milDay;
                break;
            case "week":
                repeatTime = Integer.parseInt(noRepeat) * milWeek;
                break;
            case "month":
                repeatTime = Integer.parseInt(noRepeat) * milMonth;
                break;

        }

    }

    private void notifyAlarm(){
        if(active.toLowerCase().equals("true")){
            if(repeat.toLowerCase().equals("true")){
                new AlarmScheduler().setRepeatAlarm(NewMedicationActivity.this, selectedTimeStamp, currentReminderUri, repeatTime);
            }
            else if(repeat.toLowerCase().equals("false")){
                new AlarmScheduler().setAlarm(NewMedicationActivity.this,selectedTimeStamp, currentReminderUri);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle){
        String[] projection = {
                AlarmContract.AlarmEntry.ID,
                AlarmContract.AlarmEntry.KEY_TITLE,
                AlarmContract.AlarmEntry.KEY_DATE,
                AlarmContract.AlarmEntry.KEY_TIME,
                AlarmContract.AlarmEntry.KEY_REPEAT,
                AlarmContract.AlarmEntry.KEY_REPEAT_NO,
                AlarmContract.AlarmEntry.KEY_REPEAT_TYPE,
                AlarmContract.AlarmEntry.KEY_ACTIVE
        };
        return new CursorLoader(this, currentReminderUri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if(data == null || data.getCount() < 1){
            return;
        }

        if(data.moveToFirst()){
            int dateColumn = data.getColumnIndex(AlarmContract.AlarmEntry.KEY_DATE);
            int timeColumn = data.getColumnIndex(AlarmContract.AlarmEntry.KEY_TIME);
            int repeatColumn = data.getColumnIndex(AlarmContract.AlarmEntry.KEY_REPEAT);
            int noRepeatColumn = data.getColumnIndex(AlarmContract.AlarmEntry.KEY_REPEAT_NO);
            int repeatTypeColumn = data.getColumnIndex(AlarmContract.AlarmEntry.KEY_REPEAT_TYPE);
            int activeColumn = data.getColumnIndex(AlarmContract.AlarmEntry.KEY_ACTIVE);

            String dateValue = data.getString(dateColumn);
            String repeatValue = data.getString(repeatColumn);
            String noRepeatValue = data.getString(noRepeatColumn);
            String repeatTypeValue = data.getString(repeatTypeColumn);
            String activeValue = data.getString(activeColumn);
            String timeValue = data.getString(timeColumn);

        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
