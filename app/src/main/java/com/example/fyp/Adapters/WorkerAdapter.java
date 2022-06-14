package com.example.fyp.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp.Chat.ChatActivity;
import com.example.fyp.GigDetailedViewSeller;
import com.example.fyp.GigDetailedViewUser;
import com.example.fyp.Models.UserData;
import com.example.fyp.Models.WorkerData;
import com.example.fyp.R;
import com.example.fyp.Utilities.Constants;
import com.example.fyp.Utilities.PreferenceManager;
import com.example.fyp.databinding.ItemShowWorkerBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class WorkerAdapter extends RecyclerView.Adapter<WorkerAdapter.WorkerViewHolder> {
    ArrayList<WorkerData> workerArray;

    public WorkerAdapter(ArrayList<WorkerData> workerArray) {
        this.workerArray = workerArray;
    }

    @NonNull
    @Override
    public WorkerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_show_worker, parent, false);
        ItemShowWorkerBinding binding = ItemShowWorkerBinding.bind(view);
        return new WorkerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkerViewHolder holder, int position) {
        WorkerData worker = workerArray.get(position);
        holder.setData(worker);
        holder.binding.chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore.getInstance().collection("users")
                        .document(worker.getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            UserData userData=task.getResult().toObject(UserData.class);
                            Intent intent=new Intent(v.getContext(), ChatActivity.class);
                            intent.putExtra(Constants.KEY_USER,userData);
                            v.getContext().startActivity(intent);
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.workerArray.size();
    }

    class WorkerViewHolder extends RecyclerView.ViewHolder {
        ItemShowWorkerBinding binding;
        TextView mTitle, mDescription, mBudget, mTime, mService;

        public WorkerViewHolder(ItemShowWorkerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(WorkerData worker) {
            binding.textTitle.setText(worker.getFname() + " " + worker.getLname());
            binding.textPhone.setText(worker.getPhone());
            binding.textService.setText(worker.getService());
        }
    }


}

