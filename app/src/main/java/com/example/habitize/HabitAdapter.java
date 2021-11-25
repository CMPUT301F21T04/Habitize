package com.example.habitize;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.gesture.Gesture;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.HabitHolder> {
    private ArrayList<Habit> dataset;
    private ArrayList<Integer> posInFireBase;
    private ArrayList<Habit> allHabits;
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
    public HabitAdapter(ArrayList<Habit> habits, ArrayList<Integer> posInFirebase,ArrayList<Habit> allHabits,boolean viewing){
            // dataset contains today's habits, but when we perform swaps we want to update the allhabits
            // as it will delete habits otherwise
            this.dataset = habits;
            this.posInFireBase = posInFirebase;
            this.allHabits = allHabits;
    }


    @NonNull
    @Override
    public HabitHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_content,parent,false);
        this.mContext = parent.getContext();

        return new HabitHolder(view);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if(!this.mViewing) {
            ItemTouchHelper.SimpleCallback simpleCallback =
                    new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0)   {
                        private int toPos;
                        private int fromPos;
                        @Override
                        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                            // setting the positions selected
                            this.fromPos = viewHolder.getAdapterPosition();
                            this.toPos = target.getAdapterPosition();
                            return true;
                        }

                        @Override
                        public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                            super.onSelectedChanged(viewHolder, actionState);
                            // when we let go of the drag, let the swap happen
                            switch(actionState){
                                default:
                                    fromPos = viewHolder.getAdapterPosition();
                                    break;
                                case ItemTouchHelper.ACTION_STATE_IDLE:
                                    if(posInFireBase != null) {
                                        Collections.swap(allHabits, posInFireBase.get(fromPos), posInFireBase.get(toPos));
                                        recyclerView.getAdapter().notifyDataSetChanged();
                                        DatabaseManager.updateHabits(allHabits);
                                    }
                                    else{
                                        Collections.swap(dataset,fromPos,toPos);
                                        recyclerView.getAdapter().notifyDataSetChanged();
                                        DatabaseManager.updateHabits(dataset);
                                    }
                                    break;


                            }
                        }

                        @Override
                        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        }
                    };
            ItemTouchHelper helper = new ItemTouchHelper(simpleCallback);
            helper.attachToRecyclerView(recyclerView);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull HabitHolder holder, int position) {

        // each cell is responsible for communicating with firebase and populating its image.
        DatabaseManager.getAndSetImage(dataset.get(holder.getAdapterPosition()).getRecordAddress()
                ,holder.getHabitImageView());
        holder.getTitle().setText(dataset.get(holder.getAdapterPosition()).getName());
        // different cases based on whether we are viewing another person's habit or not.
        if(!mViewing) { // if we aren't viewing. Set recording habit and view/edit screen opening listeners
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
                    habitBundle.putSerializable("viewing",false);
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
            Intent intent = new Intent(mContext,ViewHabitTabsBase.class);
            Bundle habitBundle = new Bundle();
            habitBundle.putSerializable("viewing",false);
            mContext.startActivity(intent);

            // we open a viewing habit activity. This one does not allow us to toggle edit or delete.
        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
