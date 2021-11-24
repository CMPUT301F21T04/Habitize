package com.example.habitize;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.HabitHolder> {
    private ArrayList<Habit> dataset;
        public static class HabitHolder extends RecyclerView.ViewHolder{

        private final TextView title;
        private final FloatingActionButton recordButton;
        private final ImageView habitImg;

        public HabitHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.habitName);
            recordButton = itemView.findViewById(R.id.completeHabit);
            habitImg = itemView.findViewById(R.id.habitImage);


            recordButton.setFocusable(false);
            recordButton.setFocusableInTouchMode(false);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Hello");
                }
            });

        }

        public TextView getTitle(){
            return title;
        }

        public FloatingActionButton getRecordButton(){
            return recordButton;
        }

        public ImageView getHabitImageView(){
            return habitImg;
        }
    }



    public HabitAdapter(ArrayList<Habit> habits){
        this.dataset = habits;
    }


    @NonNull
    @Override
    public HabitHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_content,parent,false);
        return new HabitHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitHolder holder, int position) {
        holder.getTitle().setText(dataset.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
