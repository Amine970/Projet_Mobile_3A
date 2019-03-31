package com.example.recyclerviewapi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.squareup.picasso.Picasso;

public class MyStandsAdapter extends RecyclerView.Adapter<MyStandsAdapter.MyViewHolder>
{
    List<Stand> stands;
    Context context;
    private OnItemClickListener myListener;
    MyStandsAdapter(List<Stand> stands, Context context )
    {
        this.stands = stands;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.stand_item_bis, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i)
    {
        myViewHolder.display(stands.get(i)); // i = position dans la liste
    }

    @Override
    public int getItemCount() {
        return stands.size();
    }

    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        myListener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView mFruit;
        private TextView mUser;
        private ImageView standImage;
        private TextView standName;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            standImage = (ImageView) itemView.findViewById(R.id.imageView);
            standName = (TextView) itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if(myListener != null)
                    {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            myListener.onItemClick(position);
                        }
                    }
                }
            });
        }
        void display(Stand stand) // méthode appelée à chaque recyclage
        {
            Picasso.with(context).load(stand.getStand_image()).fit().into(standImage);
            standName.setText(stand.getStand_name()+"");
        }
    }
}
