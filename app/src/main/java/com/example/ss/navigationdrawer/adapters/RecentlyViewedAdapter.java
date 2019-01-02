package com.example.ss.navigationdrawer.adapters;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.ss.navigationdrawer.R;
import com.example.ss.navigationdrawer.session.Session;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class RecentlyViewedAdapter extends RecyclerView.Adapter<RecentlyViewedAdapter.MyViewHolder>{
    public List<Recent_channel_List>recent_channel_list;
    ArrayList<String>Thumbnail = new ArrayList<>();
    Activity mActivity;;
    Context context;
    Session session;



    public static final String TAG = RecentlyViewedAdapter.class.getSimpleName();

    public RecentlyViewedAdapter(Activity activity , List<Recent_channel_List> recent_channel_list){
        this.mActivity = activity;
        this.recent_channel_list = recent_channel_list;
        session = new Session(mActivity);

    }

    @Override
    public RecentlyViewedAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recentlyviewed_adapter, parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecentlyViewedAdapter.MyViewHolder holder,final int position) {

        if (session.getRenewal_status().equalsIgnoreCase("Yes")&&session.getPayment_status().equalsIgnoreCase("0")) {
            if (recent_channel_list.get(position).embed_code != null){
                if (!recent_channel_list.get(position) .embed_code.equalsIgnoreCase("")){
                    try {
                        Log.e(TAG, "embed_code==>" + recent_channel_list.get(position).embed_code);
                        String thumbnail = "https://img.youtube.com/vi/" + recent_channel_list.get(position).embed_code + "/mqdefault.jpg";

                        Thumbnail = new ArrayList<>();
                        Thumbnail.add(thumbnail);

                        for (int i = 0; i < Thumbnail.size(); i++){
                            Picasso
                                    .with(mActivity)
                                    .load(Thumbnail.get(i)).skipMemoryCache()
                                    .error(R.drawable.noimage)
                                    .into(holder.image_channel);

                            holder.image_channel.setScaleType(ImageView.ScaleType.FIT_XY);
                        }

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }



        }










    }
    @Override
    public int getItemCount() {
        return recent_channel_list.size();
    }

public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView image_channel, menu_drop_icon;

        public MyViewHolder(View itemView){

        super(itemView);

        image_channel=(ImageView)itemView.findViewById(R.id.image_channel);
            menu_drop_icon=(ImageView)itemView.findViewById(R.id.menu_drop_icon);



        }
}

}
