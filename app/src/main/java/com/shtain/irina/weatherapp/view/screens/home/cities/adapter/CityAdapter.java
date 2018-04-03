package com.shtain.irina.weatherapp.view.screens.home.cities.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shtain.irina.weatherapp.R;

import java.util.ArrayList;

/**
 * Created by Irina Shtain on 03.04.2018.
 */
public class CityAdapter extends RecyclerView.Adapter<CityVH> {

    private ArrayList<CityDH> mCities;
    private OnCityClicklistener mClicklistener;

    public CityAdapter() {
        this.mCities = new ArrayList<>();
    }

    public void addCities(ArrayList<CityDH> cities) {
        mCities.addAll(cities);
        notifyDataSetChanged();
    }

    public void addListener(OnCityClicklistener clicklistener) {
        mClicklistener = clicklistener;
    }


    @NonNull
    @Override
    public CityVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_city, parent, false);
        return new CityVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityVH holder, int position) {
        holder.bindData(mCities.get(position));
        if (mClicklistener != null)
            holder.itemView.setOnClickListener(v -> mClicklistener.onCityClick(mCities.get(position).getCity()));
    }

    @Override
    public int getItemCount() {
        return mCities == null ? 0 : mCities.size();
    }

    public CityDH getItem(int pos) {
        return mCities.get(pos);
    }

    public void removeItem(int adapterPosition) {
        mCities.remove(adapterPosition);
        notifyDataSetChanged();
    }

    public void addCity(CityDH cityDH, int position) {
        if (position > 0)
            mCities.add(position, cityDH);
        else
            mCities.add(cityDH);
        notifyDataSetChanged();
    }
}
