package com.example.fyp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp.GigDetailedViewSeller;
import com.example.fyp.GigDetailedViewUser;
import com.example.fyp.Models.CreateJob;
import com.example.fyp.R;
import com.example.fyp.Utilities.Constants;
import com.example.fyp.Utilities.PreferenceManager;

import java.lang.ref.PhantomReference;
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
        CreateJob createJob = jobArrayList.get(position);
        holder.setData(createJob);
        PreferenceManager preferenceManager=new PreferenceManager(holder.itemView.getContext().getApplicationContext());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppCompatActivity activity = (AppCompatActivity) holder.itemView.getContext();
                if (preferenceManager.getBoolean(Constants.KEY_IS_WORKER)) {
                    GigDetailedViewSeller gigDetailedViewSeller = GigDetailedViewSeller.newInstance(createJob);
                    gigDetailedViewSeller.show(activity.getSupportFragmentManager(), gigDetailedViewSeller.getTag());
                }
                else{
                    GigDetailedViewUser gigDetailedViewUser = GigDetailedViewUser.newInstance(createJob);
                    gigDetailedViewUser.show(activity.getSupportFragmentManager(), gigDetailedViewUser.getTag());

                }
            }

        });
    }

    @Override
    public int getItemCount() {
        return this.jobArrayList.size();
    }

    class GigViewHolder extends RecyclerView.ViewHolder{
        TextView mTitle,mDescription,mBudget,mTime,mService;

        public GigViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitle=itemView.findViewById(R.id.textTitle);
            mDescription=itemView.findViewById(R.id.textDescription);
            mBudget=itemView.findViewById(R.id.textBudget);
            mTime=itemView.findViewById(R.id.textTime);
            mService=itemView.findViewById(R.id.textService);
        }

        public void setData(CreateJob createJob){
            mTitle.setText(createJob.getTitle());
            mDescription.setText(createJob.getDescription());
            mBudget.setText(createJob.getBudget());
            mTime.setText(createJob.getTime());
            mService.setText(createJob.getService());
        }
    }


}

