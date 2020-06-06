package com.example.globalpharma.Views;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.globalpharma.Model.HourItem;
import com.example.globalpharma.R;

import java.util.ArrayList;
import java.util.List;

public class HoursActivity extends AppCompatActivity {
    protected static List<HourItem> mHourItems;
    private RecyclerView mRecyclerView;
    protected HourAdapter mHourAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hours);
        getSupportActionBar().hide();

        mRecyclerView = findViewById(R.id.rv_hours1);

        mHourItems = new ArrayList<>();

        Intent intent = getIntent();
        if(intent.hasExtra("hour")){
            HourItem hourItem = intent.getParcelableExtra("hour");
            mHourItems.add(hourItem);
        }

        mHourAdapter = new HourAdapter(HoursActivity.this, mHourItems);
        mRecyclerView.setAdapter(mHourAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    public void swipeItemToRight(){
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        };
    }
}
