package com.example.ss.navigationdrawer.fragment;


import android.app.Activity;
import android.os.Bundle;


import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;
import com.example.ss.navigationdrawer.adapters.LiveAdapter;
import com.example.ss.navigationdrawer.adapters.CategoriesAdapter;
import com.example.ss.navigationdrawer.adapters.NewsCategoryList;
import com.example.ss.navigationdrawer.adapters.RecentlyViewedAdapter;
import com.example.ss.navigationdrawer.adapters.CategoryList;
import com.example.ss.navigationdrawer.adapters.LiveNewsList;
import com.example.ss.navigationdrawer.adapters.Recent_channel_List;



import com.example.ss.navigationdrawer.R;
import com.example.ss.navigationdrawer.request.CommonRequest;
import com.example.ss.navigationdrawer.retrofit.ListDetails;
import com.example.ss.navigationdrawer.retrofit.RetrofitInterface;
import com.example.ss.navigationdrawer.session.Session;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class HomeFragment extends Fragment {

    RecyclerView categories_recyclerview, recent_live_recyclerview, recent_recyclerview;
    TextView title_three, title_two, title;
    RetrofitInterface apiInterface;
    Activity activity;
    RecentlyViewedAdapter recentadapter;
    LiveAdapter liveadapter;
    CategoriesAdapter categoryadapter;
    public List<Recent_channel_List> recentChannelList;
    public List<LiveNewsList> liveNewslist;
    List<CategoryList> categoryList;
    public static int requestType = 0;
    Session session;
    public static final String TAG = HomeFragment.class.getSimpleName();
    public List<NewsCategoryList> newsCategoryList;

    public static ArrayList<NewsCategoryList> news_CategoryLists = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        activity = getActivity();
        session = new Session(activity);
        Initialization(view);


        return view;
    }

    public void Initialization(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitInterface.serverUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(RetrofitInterface.class);

        GetRecent();
        GetLive();
        GetCategories();



        title = (TextView) view.findViewById(R.id.title);
        title_two = (TextView) view.findViewById(R.id.title_two);
        title_three = (TextView) view.findViewById(R.id.title_three);

        recent_recyclerview = (RecyclerView) view.findViewById(R.id.recent_recyclerview);
        recent_live_recyclerview = (RecyclerView) view.findViewById(R.id.recent_live_recyclerview);

        categories_recyclerview = (RecyclerView) view.findViewById(R.id.categories_recyclerview);



    }

    public void GetRecent() {
        Log.e(TAG, "GetRecent==>");
        CommonRequest commonRequest = new CommonRequest();
        commonRequest.user_id = "1";
        final Call<ListDetails> recently_music = apiInterface.recently_music(commonRequest);
        recently_music.enqueue(new Callback<ListDetails>() {
            @Override
            public void onResponse(Call<ListDetails> call, Response<ListDetails> response) {
                if (response.body() != null) {
                    if (response.body().status.equalsIgnoreCase("success")) {
                        if (!response.body().music_detail.isEmpty()) {
                            if (response.body().music_detail != null) {
                                recentChannelList = response.body().music_detail;
                                Log.e(TAG, "list_size ==>" + recentChannelList);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false);
                                recentadapter = new RecentlyViewedAdapter(activity, recentChannelList);
                                recent_recyclerview.setAdapter(recentadapter);
                                recent_recyclerview.setLayoutManager(layoutManager);
                                recentadapter.notifyDataSetChanged();


                            } else {
                                Log.e(TAG, "list_empty==>");
                            }
                        } else {
                            Log.e(TAG, "list_empty==>");
                        }

                    } else {
                        Toast.makeText(activity, response.body().msg, Toast.LENGTH_LONG).show();

                    }
                }


            }

            @Override
            public void onFailure(Call<ListDetails> call, Throwable t) {
                Toast.makeText(getActivity(),"Server Busy",Toast.LENGTH_LONG).show();


            }
        });
    }


    public void GetLive() {
        Log.e(TAG, "GetLive==>");
        CommonRequest commonRequest = new CommonRequest();
        commonRequest.user_id = "1";
        final Call<ListDetails> recently_live_music = apiInterface.recently_watch_list(commonRequest);
        recently_live_music.enqueue(new Callback<ListDetails>() {
            @Override
            public void onResponse(Call<ListDetails> call, Response<ListDetails> response) {
                if (response.body() != null) {
                    if (response.body().status.equalsIgnoreCase("success")) {
                        if (!response.body().live_detail.isEmpty()) {
                            if (response.body().live_detail != null) {
                                liveNewslist = response.body().live_detail;
                                Log.e(TAG, "list_size ==>" + liveNewslist);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false);
                                liveadapter = new LiveAdapter(activity,liveNewslist);
                                recent_live_recyclerview.setAdapter(liveadapter);
                                recent_live_recyclerview.setLayoutManager(layoutManager);
                                liveadapter.notifyDataSetChanged();


                            } else {
                                Log.e(TAG, "list_empty==>");
                            }
                        } else {
                            Log.e(TAG, "list_empty==>");
                        }

                    } else {
                        Toast.makeText(activity, response.body().msg, Toast.LENGTH_LONG).show();

                    }
                }


            }

            @Override
            public void onFailure(Call<ListDetails> call, Throwable t) {
                Toast.makeText(getActivity(),"Server Busy",Toast.LENGTH_LONG).show();


            }
        });

    }

    public void GetCategories(){
        Log.e(TAG, "GetCategories==>");
//        CommonRequest commonRequest = new CommonRequest();
//        commonRequest.user_id = "1";
        final Call<ListDetails> categories_list = apiInterface.category_list();
        categories_list.enqueue(new Callback<ListDetails>() {
            @Override
            public void onResponse(Call<ListDetails> call, Response<ListDetails> response) {
                if(response.body()!=null) {
                    if (response.body().status.equalsIgnoreCase("success")) {
                        if (!response.body().category_list.isEmpty()) {
                            if (response.body().category_list != null) {

                               categoryList = response.body().category_list;
                                Log.e(TAG, "list_size ==>" + categoryList );
                                GridLayoutManager gridLayoutManager = new GridLayoutManager(activity,3);
                                categoryadapter = new CategoriesAdapter(activity, categoryList);
                                categories_recyclerview.setAdapter(categoryadapter);
                                categories_recyclerview.setLayoutManager(gridLayoutManager);
                                categoryadapter.notifyDataSetChanged();





                            }else {
                                Log.e(TAG, "list_empty==>");
                            }

                        }else {
                            Log.e(TAG, "list_empty==>");
                        }
                    } else {
                        Toast.makeText(activity, response.body().msg, Toast.LENGTH_LONG).show();

                    }

                }


            }

            @Override
            public void onFailure(Call<ListDetails> call, Throwable t) {
                Toast.makeText(getActivity(),"Server Busy",Toast.LENGTH_LONG).show();

            }
        });



    }


}







