package com.example.newsapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.Activities.ListStackActivity;
import com.example.newsapp.Models.Sources;
import com.example.newsapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class SourcesAdapter extends RecyclerView.Adapter<SourcesAdapter.ViewHolder> {

    private static final String TAG = "SourcesAdapter";
     Context mContext;
     List<Sources>sources;
    public SourcesAdapter(Context context, List<Sources> sourceList) {
        this.mContext=context;
        this.sources=sourceList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.source_item,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.sourceName.setText(sources.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return sources.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.source_name) TextView sourceName;
        @BindView(R.id.source_image) CircleImageView sourceImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Sources sourceItem = sources.get(getAdapterPosition());
                    Intent intent = new Intent(mContext, ListStackActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("sourceid",sourceItem.getId());

                    mContext.startActivity(intent);

                }
            });

        }
    }
}
