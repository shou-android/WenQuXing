package com.example.wenquxing.ai.Ctable;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.wenquxing.R;
import com.example.wenquxing.ai.group.Exsuper;

import java.util.List;

/**
 */

public class CtableAdapter extends RecyclerView.Adapter<CtableAdapter.ViewHolder>{

    private List<Exsuper> mDataset;

    public CtableAdapter(List<Exsuper> mDataSet) {
        this.mDataset = mDataSet;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, teacher, detail, day, type, credit;

        public ViewHolder(View v) {
            super (v);
            name = (TextView) v.findViewById(R.id.name);
            teacher = (TextView) v.findViewById(R.id.teacher);
            detail = (TextView) v.findViewById(R.id.detail);
            day = (TextView) v.findViewById(R.id.day);
            type = (TextView) v.findViewById(R.id.type);
            credit = (TextView) v.findViewById(R.id.credit);
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @SuppressLint("SetTextI18n")
    public void onBindViewHolder(ViewHolder holder, int position) {
        Exsuper lesson = mDataset.get(position);
        holder.name.setText(lesson.getSname());
        holder.teacher.setText("老师: " + lesson.getScontext());
        holder.detail.setText("开始："+lesson.getSstar()+"结束："+lesson.getSfinish());
        holder.day.setText("星期: " + lesson.getTimedata());
        holder.credit.setText("地点: " + lesson.getSloty());
    }

    public int getItemCount() {
        return mDataset.size();
    }
}
