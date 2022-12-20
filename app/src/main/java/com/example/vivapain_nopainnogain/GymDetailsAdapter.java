package com.example.vivapain_nopainnogain;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GymDetailsAdapter extends RecyclerView.Adapter {
    private final ArrayList<gymDetailsModule> GymData;
    private View.OnClickListener listener;


    public GymDetailsAdapter(ArrayList<gymDetailsModule> GymData) {
        this.GymData = GymData;
    }

    public class GymDetailsViewHolder extends RecyclerView.ViewHolder {
        public TextView GymName;

        public GymDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            GymName = itemView.findViewById(R.id.GnameTXT);
            itemView.setTag(this);
            itemView.setOnClickListener(listener);
        }

        public TextView getGdetails() {
            return GymName;
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.complex_list, parent, false);
        return new GymDetailsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        GymDetailsViewHolder cvh = (GymDetailsViewHolder) holder;
        cvh.getGdetails().setText(GymData.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return GymData.size();
    }

    public void setOnItemClickListener(View.OnClickListener l) {
        listener = l;
    }
}
