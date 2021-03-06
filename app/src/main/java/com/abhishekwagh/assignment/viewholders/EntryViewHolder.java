package com.abhishekwagh.assignment.viewholders;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abhishekwagh.assignment.R;
import com.abhishekwagh.assignment.activities.ShowDetailsActivity;
import com.abhishekwagh.assignment.db.Entry;


public class EntryViewHolder extends RecyclerView.ViewHolder {

    public TextView textView;
    public ImageView imageView;
    public Entry entry;
    public Context mContext;

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public EntryViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.image_view);
        textView = itemView.findViewById(R.id.text_view);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, ShowDetailsActivity.class);
                i.putExtra("uid", String.valueOf(entry.getUid()));
                mContext.startActivity(i);
            }
        });
    }


}
