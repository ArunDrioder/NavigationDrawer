package com.example.ss.navigationdrawer.adapters;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ss.navigationdrawer.R;
import com.example.ss.navigationdrawer.session.Session;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder> {
    public List<CategoryList> cateogryList;
    Activity mActivity;
    Session session;
    public int requestType = 0;
    public static final String TAG = CategoriesAdapter.class.getSimpleName();



    public CategoriesAdapter(Activity activity, List<CategoryList> cateogryList) {
        this.mActivity = activity;
        this.cateogryList = cateogryList;

    }

    @Override

    public CategoriesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.categories_adapter, parent, false);


        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(CategoriesAdapter.MyViewHolder holder, final int position) {

        Log.e(TAG,"list_size==>"+cateogryList.size());
        session=new Session(mActivity);
            holder.category_title.setText(cateogryList.get(position).category_name);

            if (cateogryList.get(position).image_path != null) {
                if (!cateogryList.get(position).image_path.equalsIgnoreCase("")) {
                    Picasso
                            .with(mActivity)
                            .load(cateogryList.get(position).image_path)
                            .error(R.drawable.channel)
                            .into(holder.category_image);

                    holder.category_image.setScaleType(ImageView.ScaleType.FIT_XY);
                }
            }


        }


    @Override
    public int getItemCount() {
        return cateogryList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView category_image;
        TextView category_title;
        LinearLayout category_linear;
        public MyViewHolder(View itemView) {
            super(itemView);
            //linear layout
            category_linear=(LinearLayout)itemView.findViewById(R.id.category_linear);
            //imageview
            category_image=(ImageView)itemView.findViewById(R.id.category_image);
            //textview
            category_title=(TextView)itemView.findViewById(R.id.category_title);
        }
    }



}





