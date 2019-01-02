package com.example.ss.navigationdrawer.retrofit;

import com.example.ss.navigationdrawer.BeanClass.LoginDetails;
import com.example.ss.navigationdrawer.BeanClass.UserDetails;
import com.example.ss.navigationdrawer.adapters.CategoryList;
import com.example.ss.navigationdrawer.adapters.LiveNewsList;
import com.example.ss.navigationdrawer.adapters.Recent_channel_List;

import java.util.ArrayList;
import java.util.List;


public class ListDetails {
    public String status;
    public String user_id;

    public String obvious;

    public String free_code_count;
    public LoginDetails user;
    public String name;
    public String mobile;
    public String email;
    public String branch_name;
    public String room_no;


    //public List<Mentions> mentions;

    public String msg;
    public String user_bot_report_id;


    public String remaining_count;

    public List<Recent_channel_List> music_detail;
    public List<LiveNewsList> live_detail;

    public ArrayList<CategoryList> category_list = new ArrayList<>();

    public List<ProfileDetails> user_details;

    public List<ProfileDetails> profile_update;


}