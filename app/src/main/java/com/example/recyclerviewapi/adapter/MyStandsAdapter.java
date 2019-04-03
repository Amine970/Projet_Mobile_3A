package com.example.recyclerviewapi.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.recyclerviewapi.R;
import com.example.recyclerviewapi.databinding.StandBinding;
import com.example.recyclerviewapi.model.Stand;
import com.example.recyclerviewapi.viewmodel.StandViewModel;
import com.squareup.picasso.Picasso;

public class MyStandsAdapter extends RecyclerView.Adapter<MyStandsAdapter.MyViewHolder> implements Filterable
{
    private List<StandViewModel> stands;
    private List<StandViewModel> standsFull;
    private Context context;
    private OnItemClickListener myListener;
    private LayoutInflater layoutInflater;
    public MyStandsAdapter(List<StandViewModel> stands, Context context )
    {
        this.stands = stands;
        this.standsFull = new ArrayList<>(stands);
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(layoutInflater == null)
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        StandBinding standBinding = DataBindingUtil.inflate(layoutInflater, R.layout.stand_item_bis,viewGroup,false);

        //return new MyViewHolder(view);
        return new MyViewHolder(standBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i)
    {
        //myViewHolder.display(stands.get(i)); // i = position dans la liste
        StandViewModel standViewModel = stands.get(i);
        myViewHolder.bind(standViewModel);
    }

    @Override
    public int getItemCount() {
        return stands.size();
    }

    @Override
    public Filter getFilter() {
        return standFilter;
    }
    private Filter standFilter = new Filter()
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            List<StandViewModel> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0)
            {
                filteredList.addAll(standsFull);
            }
            else
            {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(StandViewModel item : standsFull)
                {
                    if(item.getStand_name().toLowerCase().contains(filterPattern))
                    {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            stands.clear();
            stands.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        myListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        private StandBinding standBinding;
        private ImageView standImage;
        private TextView standName;
        public MyViewHolder(StandBinding standBinding)
        {
            super(standBinding.getRoot());
            this.standBinding = standBinding;
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

        public void bind(StandViewModel standViewModel)
        {
            this.standBinding.setStandModel(standViewModel);
            standBinding.executePendingBindings();
        }

        public StandBinding getStandBinding()
        {
            return standBinding;
        }

        /*void display(Stand stand) // méthode appelée à chaque recyclage
        {
            Picasso.with(context).load(stand.getStand_image()).fit().into(standImage);
            standName.setText(stand.getStand_name()+"");
        }*/
    }
}
