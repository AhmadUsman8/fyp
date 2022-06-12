package com.example.fyp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp.Models.CreateJob;
import com.example.fyp.R;

import java.util.ArrayList;

public class GigAdapter extends RecyclerView.Adapter<GigAdapter.GigViewHolder>{
    ArrayList<CreateJob> jobArrayList;

    public GigAdapter(ArrayList<CreateJob> jobArrayList){
        this.jobArrayList=jobArrayList;
    }


    @NonNull
    @Override
    public GigViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gig_layout,parent,false);

        return new GigViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GigViewHolder holder, int position) {
        holder.setData(jobArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return this.jobArrayList.size();
    }

    class GigViewHolder extends RecyclerView.ViewHolder{
        TextView mTitle,mDescription,mBudget,mTime;

        public GigViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitle=itemView.findViewById(R.id.textTitle);
            mDescription=itemView.findViewById(R.id.textDescription);
            mBudget=itemView.findViewById(R.id.textBudget);
            mTime=itemView.findViewById(R.id.textTime);


        }

        public void setData(CreateJob createJob){
            mTitle.setText(createJob.getTitle());
            mDescription.setText(createJob.getDescription());
            mBudget.setText(createJob.getBudget());
            mTime.setText(createJob.getTime());


        }
    }


}

