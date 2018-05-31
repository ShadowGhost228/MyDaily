package com.esiea.mydaily.RecyclerView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.esiea.mydaily.R;

public class ViewListKioskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list_kiosk);

        //Traitement recycler view
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listKiosk);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewListKioskActivity.this));
        recyclerView.setAdapter(new MyKioskAdapter());
    }
}
