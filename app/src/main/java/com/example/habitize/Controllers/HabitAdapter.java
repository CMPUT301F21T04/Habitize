package com.example.habitize.Controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habitize.Activities.CreateRecord.CreateRecordBase;
import com.example.habitize.Activities.ViewHabit.ViewHabitTabsBase;
import com.example.habitize.Activities.ViewOther.ViewOtherHabitTabsBase;
import com.example.habitize.R;
import com.example.habitize.Structural.Habit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.HabitHolder> {
    private ArrayList<Habit> dataset;
    private ArrayList<Integer> posInFireBase;
    private ArrayList<Habit> allHabits;
    private Context mContext;
    private boolean mViewing;
    private activityEnder ender;
    private reorderEnabler enabler;

    @NonNull
    @Override
    public HabitHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_content, parent, false);
        this.mContext = parent.getContext();
        ender = (activityEnder) parent.getContext();
        enabler = (reorderEnabler) parent.getContext();
        return new HabitHolder(view);
    }

    public static class HabitHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final ImageButton recordButton;
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

        }

        public TextView getTitle(){
            return title;
        }

        public ImageButton getRecordButton(){
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

    @Override
    public void onBindViewHolder(@NonNull HabitHolder holder, int position) {

        // each cell is responsible for communicating with firebase and populating its image/ deciding
        // whether is should display its record button. (if the habit has already been complete
        // today it should not display.)
        DatabaseManager.getAndSetImage(dataset.get(holder.getAdapterPosition()).getRecordAddress()
                ,holder.getHabitImageView());
        holder.getRecordButton().setVisibility(View.INVISIBLE);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        Date d = new Date();
        //gives the day of the week of the user (if today is actually Monday it will say Monday)
        String dayOfTheWeek = simpleDateFormat.format(d);

        boolean mondayRec = dataset.get(holder.getAdapterPosition()).getMondayR();
        boolean tuesdayRec = dataset.get(holder.getAdapterPosition()).getTuesdayR();
        boolean wednesdayRec = dataset.get(holder.getAdapterPosition()).getWednesdayR();
        boolean thursdayRec = dataset.get(holder.getAdapterPosition()).getThursdayR();
        boolean fridayRec = dataset.get(holder.getAdapterPosition()).getFridayR();
        boolean saturdayRec = dataset.get(holder.getAdapterPosition()).getSaturdayR();
        boolean sundayRec = dataset.get(holder.getAdapterPosition()).getSundayR();

        if ((mondayRec == true) && (dayOfTheWeek.equals("Monday"))){
            holder.getRecordButton().setVisibility(View.VISIBLE);
        }
        if ((tuesdayRec == true) && (dayOfTheWeek.equals("Tuesday"))){
            holder.getRecordButton().setVisibility(View.VISIBLE);
        }
        if ((wednesdayRec == true) && (dayOfTheWeek.equals("Wednesday"))){
            holder.getRecordButton().setVisibility(View.VISIBLE);
        }
        if ((thursdayRec == true) && (dayOfTheWeek.equals("Thursday"))){
            holder.getRecordButton().setVisibility(View.VISIBLE);
        }
        if ((fridayRec == true) && (dayOfTheWeek.equals("Friday"))){
            holder.getRecordButton().setVisibility(View.VISIBLE);
        }
        if ((saturdayRec == true) && (dayOfTheWeek.equals("Saturday"))){
            holder.getRecordButton().setVisibility(View.VISIBLE);
        }
        if ((sundayRec == true) && (dayOfTheWeek.equals("Sunday"))){
            holder.getRecordButton().setVisibility(View.VISIBLE);
        }

        DatabaseManager.habitComplete(dataset.get(holder.getAdapterPosition()).getRecordAddress(),holder.getRecordButton());





        holder.getTitle().setText(dataset.get(holder.getAdapterPosition()).getName());
        holder.getView().setClickable(true);
        // different cases based on whether we are viewing another person's habit or not.
        if(!mViewing) { // if we aren't viewing. Set recording habit and view/edit screen opening listeners
            holder.getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!enabler.reoderEnabled()) {
                        // onclick pass the habit down into an activity for viewing.
                        Intent intent = new Intent(mContext, ViewHabitTabsBase.class);
                        Bundle habitBundle = new Bundle();
                        habitBundle.putSerializable("habit", dataset.get(holder.getAdapterPosition()));
                        if (posInFireBase != null) {
                            habitBundle.putSerializable("index", posInFireBase.get(holder.getAdapterPosition()));
                        } else {
                            habitBundle.putSerializable("index", holder.getAdapterPosition());
                        }
                        habitBundle.putSerializable("habits", dataset);
                        habitBundle.putSerializable("viewing", false);
                        intent.putExtras(habitBundle);
                        mContext.startActivity(intent);
                        ender.endActivity();
                    }
                }
            });
            holder.getRecordButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!enabler.reoderEnabled()) {
                        // on clicking of the record button, pass all the data down required to record.
                        Intent intent = new Intent(mContext, CreateRecordBase.class);
                        Bundle habitBundle = new Bundle();
                        // depending on whether our list is relative or an exact copy.
                        if (posInFireBase != null) {
                            habitBundle.putSerializable("habit", dataset.get(holder.getAdapterPosition())); // pass down the habit at the position
                            habitBundle.putSerializable("index", posInFireBase.get(holder.getAdapterPosition()));
                            habitBundle.putSerializable("habits", dataset);

                        } else {
                            habitBundle.putSerializable("habit", dataset.get(holder.getAdapterPosition())); // pass down the habit at the position
                            habitBundle.putSerializable("index", holder.getAdapterPosition());
                            habitBundle.putSerializable("habits", dataset);
                        }

                        intent.putExtras(habitBundle);
                        mContext.startActivity(intent);
                    }
                }
            });
        }
        else{
            // else we are viewing. The record button should not be visible.
            holder.getRecordButton().setVisibility(View.INVISIBLE); // we hide the record button if we are viewing.
            holder.getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ViewOtherHabitTabsBase.class);
                    Bundle habitBundle = new Bundle();
                    habitBundle.putSerializable("habit", dataset.get(holder.getAdapterPosition()));
                    if (posInFireBase != null) {
                        habitBundle.putSerializable("index", posInFireBase.get(holder.getAdapterPosition()));
                    } else {
                        habitBundle.putSerializable("index", holder.getAdapterPosition());
                    }
                    habitBundle.putSerializable("habits", dataset);
                    habitBundle.putSerializable("viewing",false);
                    habitBundle.putSerializable("searchedUser", DatabaseManager.getSearched());
                    intent.putExtras(habitBundle);
                    mContext.startActivity(intent);
                }
            });

            // we open a viewing habit activity. This one does not allow us to toggle edit or delete.
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        // different cases based on if we are viewing another person's habits.
        if (!this.mViewing) {
            ItemTouchHelper.SimpleCallback simpleCallback =
                    new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
                        private int toPos;
                        private int fromPos;

                        @Override
                        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                            // setting the positions selected
                            this.fromPos = viewHolder.getAdapterPosition();
                            this.toPos = target.getAdapterPosition();
                            return true;
                        }


                        // setting the drag/drop reorder logic.
                        @Override
                        public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                            super.onSelectedChanged(viewHolder, actionState);
                            // when we let go of the drag, let the swap happen
                            if(enabler.reoderEnabled()){
                                switch (actionState) {
                                    default:
                                        fromPos = viewHolder.getAdapterPosition();
                                        break;
                                    case ItemTouchHelper.ACTION_STATE_IDLE:
                                        if (posInFireBase != null) {
                                            Collections.swap(allHabits, posInFireBase.get(fromPos), posInFireBase.get(toPos));
                                            recyclerView.getAdapter().notifyItemMoved(posInFireBase.get(fromPos), posInFireBase.get(toPos));
                                            fromPos = toPos;
                                            DatabaseManager.updateHabits(allHabits);
                                        } else {
                                            Collections.swap(dataset, fromPos, toPos);
                                            recyclerView.getAdapter().notifyItemMoved(fromPos, toPos);
                                            fromPos = toPos;
                                            DatabaseManager.updateHabits(dataset);
                                        }
                                        break;
                                }
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

    // after editing of the image, it doesn't change right away. This is a cheap fix, we will
    // just go back to the main menu after clicking a habit.
    public interface activityEnder {
        void endActivity();
    }
    public interface reorderEnabler{
        public boolean reoderEnabled();
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }


}
