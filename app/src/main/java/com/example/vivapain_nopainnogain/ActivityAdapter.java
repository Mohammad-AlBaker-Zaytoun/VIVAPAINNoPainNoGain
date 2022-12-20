package com.example.vivapain_nopainnogain;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ActivityAdapter extends RecyclerView.Adapter {
    private final ArrayList<ActivityClass> activityData;
    private View.OnClickListener listener;


    public ActivityAdapter(ArrayList<ActivityClass> activityData) {
        this.activityData = activityData;
    }

    public class ActivityViewHolder extends RecyclerView.ViewHolder {
        public TextView Activity;

        public ActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            Activity = itemView.findViewById(R.id.GnameTXT);
            itemView.setTag(this);
            itemView.setOnClickListener(listener);
        }

        public TextView getActivity() {
            return Activity;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.complex_list, parent, false);
        return new ActivityViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ActivityViewHolder cvh = (ActivityViewHolder) holder;
        cvh.getActivity().setText(activityData.get(position).getActivityName());
    }

    @Override
    public int getItemCount() {
        return activityData.size();
    }

    public void setOnItemClickListener(View.OnClickListener l) {
        listener = l;
    }
}
