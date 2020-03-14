package com.example.globalpharma.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.globalpharma.Model.DatabaseHelper;
import com.example.globalpharma.Model.User;
import com.example.globalpharma.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.Random;

public class RegisterActivity extends AppCompatActivity {
    protected static DatabaseHelper databaseHelper;
    private Button btnconnect;
    private Button mBtnSubmit;
    private TextView mTxtPhone;
    private TextView mTxtPassword;
    private TextView mTxtConfirm;
    private TextView mTxtName;
    private Random random = new Random();
    private int code;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        initElements();

        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mTxtName.getText().toString();
                String password = mTxtPassword.getText().toString();
                String confirmation = mTxtConfirm.getText().toString();
                String phone = mTxtPhone.getText().toString();

                //check if fields are empty
                if((name.equals("")) || (password.equals("")) || (confirmation.equals("")) ||
                        phone.equals(""))
                    Toast.makeText(RegisterActivity.this, getString(R.string.remplir_correctement_champ), Toast.LENGTH_SHORT).show();

                //fields not empty
                else{
                    //password and confirmation password are not matching
                    if(!password.equals(confirmation))
                        Toast.makeText(RegisterActivity.this, getString(R.string.password_not_matching), Toast.LENGTH_SHORT).show();

                    else{
                        boolean checkPhone = databaseHelper.checkPhoneNumber(phone); //check if phone number is correct
                        //Number is correct
                        if(checkPhone == true) {
                            user = new User(name, password, phone);
                            boolean insertion = databaseHelper.onInsert(user);
                            //insertion successful
                            if (insertion == true) {

                                Toast.makeText(RegisterActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(RegisterActivity.this, "Echec d'enregistrement", Toast.LENGTH_SHORT).show();
                        }
                        else //Number is not correct
                            Toast.makeText(RegisterActivity.this, "Ohlala", Toast.LENGTH_SHORT).show();
                    }
                }

                /*code = random.nextInt((9999-1000)+1) + 1000;
                SmsManager.getDefault().sendTextMessage(mTxtPhone.getText().toString(), null,
                       "Code cde confirmation: " + code, null, null);*/


            }
        });

        btnconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

    }

    private void initElements(){
        btnconnect= (Button) findViewById(R.id.btnConnect);
        mBtnSubmit = findViewById(R.id.btnSubmit);
        mTxtPhone = findViewById(R.id.txtEmail);
        mTxtPassword = findViewById(R.id.txtPassword);
        mTxtConfirm = findViewById(R.id.txtConfirmPassword);
        mTxtName = findViewById(R.id.txtName);
        databaseHelper = new DatabaseHelper(this);
    }

    private User initUser(String name, String password, String phone){
        User user = new User(name, phone, password);
        user.setName(name);
        user.setPassword(password);
        user.setPhone(phone);
        return user;
    }


    private void emptyInputEditText() {
        mTxtPhone.setText(null);
        mTxtConfirm.setText(null);
        mTxtPassword.setText(null);
        mTxtName.setText(null);
    }


}
