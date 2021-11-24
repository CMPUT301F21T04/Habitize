package com.example.habitize;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
    private ArrayList<Integer> posInFireBase;
    private Context mContext;
    private boolean mViewing;
        public static class HabitHolder extends RecyclerView.ViewHolder{

        private final TextView title;
        private final FloatingActionButton recordButton;
        private final ImageView habitImg;
        private final View thisView;

        public HabitHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.habitName);
            recordButton = itemView.findViewById(R.id.completeHabit);
            habitImg = itemView.findViewById(R.id.habitImage);
            recordButton.setFocusable(false);
            recordButton.setFocusableInTouchMode(false);
            thisView = itemView;


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

        public View getView(){
            return thisView ;
        }
    }



    public HabitAdapter(ArrayList<Habit> habits,boolean viewing){
        this.dataset = habits;
        this.mViewing = viewing;
    }
    public HabitAdapter(ArrayList<Habit> habits, ArrayList<Integer> posInFirebase,boolean viewing){
            this.dataset = habits;
            this.posInFireBase = posInFirebase;
    }


    @NonNull
    @Override
    public HabitHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_content,parent,false);
        this.mContext = parent.getContext();
        return new HabitHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitHolder holder, int position) {

        // each cell is responsible for communicating with firebase and populating its image.
        DatabaseManager.getAndSetImage(dataset.get(holder.getAdapterPosition()).getRecordAddress()
                ,holder.getHabitImageView());
        holder.getTitle().setText(dataset.get(holder.getAdapterPosition()).getName());


        if(!mViewing) {
            holder.getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ViewHabitTabsBase.class);
                    Bundle habitBundle = new Bundle();
                    habitBundle.putSerializable("habit", dataset.get(holder.getAdapterPosition()));
                    if (posInFireBase != null) {
                        habitBundle.putSerializable("index", posInFireBase.get(holder.getAdapterPosition()));
                    } else {
                        habitBundle.putSerializable("index", holder.getAdapterPosition());
                    }
                    habitBundle.putSerializable("habits", dataset);
                    intent.putExtras(habitBundle);
                    mContext.startActivity(intent);

                }
            });
            holder.getRecordButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext,CreateRecordBase.class);
                    Bundle habitBundle = new Bundle();
                    // depending on whether our list is relative or an exact copy.
                    if(posInFireBase != null){
                        habitBundle.putSerializable("habit",dataset.get(holder.getAdapterPosition())); // pass down the habit at the position
                        habitBundle.putSerializable("index",posInFireBase.get(holder.getAdapterPosition()));
                        habitBundle.putSerializable("habits",dataset);

                    }
                    else{
                        habitBundle.putSerializable("habit",dataset.get(holder.getAdapterPosition())); // pass down the habit at the position
                        habitBundle.putSerializable("index",holder.getAdapterPosition());
                        habitBundle.putSerializable("habits",dataset);
                    }

                    intent.putExtras(habitBundle);
                    mContext.startActivity(intent);
                }
            });
        }
        else{
            holder.getRecordButton().setVisibility(View.INVISIBLE); // we hide the record button if we are viewing.
            // we should open a different activity if we are viewing someone else's habit. No edit button or anything
        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
