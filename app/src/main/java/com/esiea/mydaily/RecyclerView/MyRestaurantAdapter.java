package com.esiea.mydaily.RecyclerView;

import android.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.esiea.mydaily.GetLocationServices;
import com.esiea.mydaily.JsonTraitment.Restaurant;
import com.esiea.mydaily.R;

public class MyRestaurantAdapter extends RecyclerView.Adapter<MyRestaurantAdapter.MyViewHolder>{


    @Override
    public int getItemCount() {
        return GetLocationServices.getRestaurantArrayList().size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_cell, parent, false );
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Restaurant restaurant = GetLocationServices.getRestaurantArrayList().get(position);
        holder.display(restaurant);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView address;
        private TextView open;

        private Restaurant currentRestaurant;

        public MyViewHolder(final View itemView) {
            super(itemView);

            name = ((TextView) itemView.findViewById(R.id.name));
            address = ((TextView) itemView.findViewById(R.id.description));
            open = ((TextView) itemView.findViewById(R.id.open));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(itemView.getContext())
                            .setTitle(currentRestaurant.getName())
                            .setMessage(currentRestaurant.getFormatted_address())
                            .show();
                }
            });
        }

        public void display(Restaurant restaurant) {
            currentRestaurant = restaurant;
            name.setText(restaurant.getName());
            if(restaurant.getOpening_hours().getOpen_now() == "true"){
                open.setText("  Ouvert  ");
            } else {
                open.setText("  Fermé  ");
            }

            address.setText(restaurant.getFormatted_address());
        }
    }

}

