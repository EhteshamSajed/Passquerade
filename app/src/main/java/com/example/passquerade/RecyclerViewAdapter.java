package com.example.passquerade;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    ArrayList<String> passTextList = new ArrayList<>();
    Context context;

    public RecyclerViewAdapter(Context context, ArrayList<String> passTextList) {
        this.passTextList = passTextList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        viewHolder.itemText.setText(passTextList.get(position));
        viewHolder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mainActivity.removeItem(position);
                ((MainActivity) context).removeItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return passTextList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView itemText;
        Button removeButton;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemText = itemView.findViewById(R.id.itemText);
            removeButton = itemView.findViewById(R.id.removeButton);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
        }
    }
}
