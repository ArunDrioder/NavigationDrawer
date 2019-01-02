package com.example.ss.navigationdrawer.adapters;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
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

public class LiveAdapter extends RecyclerView.Adapter<LiveAdapter.MyViewHolder> {


    public List<LiveNewsList> liveList;
    public static View.OnClickListener myOnClickListener;
    Activity activity;
    ArrayList<String> Thumbnail = new ArrayList<>();
    Session session;

    public static final String TAG = LiveAdapter.class.getSimpleName();

    public LiveAdapter(Activity activity, List<LiveNewsList> mItemlist) {
        this.activity = activity;
        this.liveList = mItemlist;
    }

    @Override
    public LiveAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.live_adapter, parent, false);



      /*  MyViewHolder myViewHolder = new MyViewHolder(view);
        myOnClickListener = new MyOnClickListener(mActivity);

        view.setOnClickListener(Channel_Adapter.myOnClickListener);
        MLog.e(TAG,"clicked_position==>"+myViewHolder.channel_title);
*/
        return new LiveAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LiveAdapter.MyViewHolder holder, final int position) {
        Log.e(TAG, "list_size==>" + liveList.size());
        session = new Session(activity);

        if (session.getRenewal_status().equalsIgnoreCase("Yes") && session.getPayment_status().equalsIgnoreCase("0")) {


            holder.channel_title.setText(liveList.get(position).title);

            if (liveList.get(position).embed_code != null) {
                if (!liveList.get(position).embed_code.equalsIgnoreCase("")) {

                    try {

                        Log.e(TAG, "embed_code==>" + liveList.get(position).embed_code);
                        String thumbnail = "https://img.youtube.com/vi/" + liveList.get(position).embed_code + "/mqdefault.jpg";

                        Thumbnail = new ArrayList<>();
                        Thumbnail.add(thumbnail);
                        for (int i = 0; i < Thumbnail.size(); i++) {

                            Picasso
                                    .with(activity)
                                    .load(Thumbnail.get(i)).skipMemoryCache()
                                    .error(R.drawable.noimage)
                                    .into(holder.image_channel);

                            holder.image_channel.setScaleType(ImageView.ScaleType.FIT_XY);
                        }


                    } catch (Exception e) {
                        e.printStackTrace();


                    }
                }
            }
        }
    }
    @Override
    public int getItemCount() {
        return liveList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView image_channel,menu_drop;
        TextView channel_title,channel_description;
        LinearLayout menu_layout;


        public MyViewHolder(View itemView) {
            super(itemView);
            menu_layout=(LinearLayout)itemView.findViewById(R.id.menu_layout);
            //imageview
            image_channel=(ImageView)itemView.findViewById(R.id.image_channel);
            menu_drop=(ImageView)itemView.findViewById(R.id.menu_drop);

            //textview
            channel_title=(TextView)itemView.findViewById(R.id.channel_title);
            channel_description=(TextView)itemView.findViewById(R.id.channel_description);

        }
    }
}
