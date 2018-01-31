package com.carsgates.cr.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.carsgates.cr.R;
import com.carsgates.cr.webservices.CarDetails;
import com.carsgates.cr.webservices.CarSpecification;
import com.carsgates.cr.webservices.RetrofitApiBuilder;

import java.util.List;

/**
 * Created by Atul Kumar Gupta on 5/8/2017.
 * Contact Number : +91 8470967433
 */

public class SearchByMapAdapter extends RecyclerView.Adapter<SearchByMapAdapter.MyViewHolder> {

    private  final OnItemClickListener listener;
    List<CarDetails> list ;

    public interface OnItemClickListener {
        void  onItemClick(CarDetails carDetail);
    }

    public SearchByMapAdapter(List<CarDetails> list,OnItemClickListener listener){
        this.list =list ;
        this.listener  = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.search_by_map_result_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CarDetails searchByMapResult =  list.get(position);
        holder.bindListener(searchByMapResult,listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvCarName , tvSearchDescription ;
        ImageView ivCarImage ;
        private  View itemView ;
        MyViewHolder(View itemView) {
            super(itemView);
            tvCarName = (TextView) itemView.findViewById(R.id.tvCarModelName) ;
            tvSearchDescription = (TextView) itemView.findViewById(R.id.tvSearchDescription) ;
            ivCarImage = (ImageView) itemView.findViewById(R.id.tvCarImage) ;
            this.itemView  = itemView;
        }


        void bindListener(final CarDetails carDetail, final OnItemClickListener listener) {
            tvCarName.setText(carDetail.model_name);
            String banner = carDetail.company_logo ;
            if(banner !=null && ! banner.isEmpty()){
                String imagePath  = RetrofitApiBuilder.IMG_BASE_URL+banner ;
                Log.d("Image URL",RetrofitApiBuilder.IMG_BASE_URL+banner);
                Glide.with(itemView.getContext())
                        .load(imagePath)
                        .into(ivCarImage);
            }
            setupSpecification(carDetail.carmanagement_specifications) ;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(carDetail);
                }
            });
        }

        private void setupSpecification(List<CarSpecification> specs) {
            StringBuilder specification  = new StringBuilder();
            StringBuilder spec1 =  new StringBuilder();
            StringBuilder spec2 =  new StringBuilder();
            StringBuilder spec3 =  new StringBuilder();
            StringBuilder spec4 =  new StringBuilder();
            for (int indexSpec = 0; indexSpec < specs.size(); indexSpec++) {
                CarSpecification spec = specs.get(indexSpec);

                switch(spec.specification_display) {
                    case "1" :
                        spec1.append(spec.specification_name).append(" | ");
                        break ;

                    case "2" :
                        spec2.append(spec.specification_name).append(" | ");
                        break ;

                    case "3" :
                        spec3.append(spec.specification_name).append(" | ");
                        break ;

                    case "4" :
                        spec4.append(spec.specification_name).append(" | ");
                        break ;
                }

            }
            specification.append(spec1.toString()).append("\n")
                    .append(spec2.toString()).append("\n")
                    .append(spec3.toString()).append("\n")
                    .append(spec4.toString()).append("\n");

            tvSearchDescription.setText(specification.toString());
        }
    }
}
