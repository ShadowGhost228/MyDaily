package com.esiea.mydaily.RecyclerView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.esiea.mydaily.R;
import com.esiea.mydaily.RestaurantActivity;

public class ViewListRestaurantActicity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list_restaurant_acticity);

        //Traitement recycler view
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listRestaurant);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewListRestaurantActicity.this));
        recyclerView.setAdapter(new MyRestaurantAdapter());
    }
}
