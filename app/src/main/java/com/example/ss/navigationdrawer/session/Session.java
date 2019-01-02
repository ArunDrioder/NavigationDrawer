package com.example.ss.navigationdrawer.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {
    private SharedPreferences prefss;
    public SharedPreferences.Editor editorr;

    public final String user_id = "user_id";
    public final String username = "username";
    public final String password = "password";
    public final String email = "email";
    public final String mobile = "mobile";
    public final String status = "status";
    public final String approve_status = "approv_status";
    public final String delete_date = "delete_date";
    public final String breakfast_time = "bf_time";
    public final String lunch_time = "lu_time";
    public final String dinner_time = "dinn_time";
    public final String selected_date = "selected_date";
    public final String selected_day = "day";
    public final String seleted_date_mm_yyy="date_mm";
    public final String select_day_full_name="day_full_name";
    public static final String renewal_status="status";
    public static final String paymentstatus = "payment_status";


    public Session(Context cntx) {
        // TODO Auto-generated constructor stub

        prefss = PreferenceManager.getDefaultSharedPreferences(cntx);
        editorr = prefss.edit();
    }
    public void setUser_id(String id) {
        prefss.edit().putString(user_id, id).apply();
    }
    public String getUser_id() {
        String id = prefss.getString(user_id, "");
        return id;
    }
    public void setSelected_date(String date) {
        prefss.edit().putString(selected_date, date).apply();
    }
    public String getSelected_date() {
        String date = prefss.getString(selected_date, "");
        return date;
    }
    public void setSelected_day(String day) {
        prefss.edit().putString(selected_day, day).apply();
    }
    public String getSelected_day() {
        String DAY = prefss.getString(selected_day, "");
        return DAY;
    }
    public void setSelect_day_full_name(String day_name) {
        prefss.edit().putString(select_day_full_name, day_name).apply();
    }
    public String getSelect_day_full_name() {
        String DAY_name = prefss.getString(select_day_full_name, "");
        return DAY_name;
    }

    public void setSeleted_date_mm_yyy(String date_mm) {
        prefss.edit().putString(seleted_date_mm_yyy, date_mm).apply();
    }
    public String getSeleted_date_mm_yyy() {
        String dd_mm = prefss.getString(seleted_date_mm_yyy, "");
        return dd_mm;
    }
    public String getStatus(){
        String sta = prefss.getString(status, "");
        return sta;
    }
    public void setBreakfast_time(String bf_time){
        prefss.edit().putString(breakfast_time,bf_time).apply();
    }
    public String getBreakfast_time(){
        String bf_time = prefss.getString(breakfast_time, "");
        return bf_time;
    }
    public void setLunch_time(String l_time){
        prefss.edit().putString(lunch_time,l_time).apply();
    }
    public String getLunch_time(){
        String l_time = prefss.getString(lunch_time, "");
        return l_time;
    }
    public void setDinner_time(String d_time){
        prefss.edit().putString(dinner_time,d_time).apply();
    }
    public String getDinner_time(){
        String d_time = prefss.getString(dinner_time, "");
        return d_time;
    }
    public void setStatus(String status_user){
        prefss.edit().putString(status,status_user).apply();
    }
    public String getDelete_date(){
        String date = prefss.getString(delete_date, "");
        return date;
    }
    public void setDelete_date(String date){
        prefss.edit().putString(delete_date,date).apply();
    }
    public String getApprove_status(){
        String sta = prefss.getString(approve_status, "");
        return sta;
    }
    public void setApprove_status(String status_approval){
        prefss.edit().putString(approve_status,status_approval).apply();
    }


    public void setMobile(String number) {
        prefss.edit().putString(mobile, number).apply();
    }
    public String getMobile() {
        String number = prefss.getString(mobile, "");
        return number;
    }
    public void setUsername(String name) {
        prefss.edit().putString(username, name).apply();
    }
    public String getUsername() {
        String name = prefss.getString(username, "");
        return name;
    }

    public void setPassword(String pwd) {
        prefss.edit().putString(password, pwd).apply();
    }
    public String getPassword() {
        String name = prefss.getString(password, "");
        return name;
    }
    public void setEmail(String mail) {
        prefss.edit().putString(email, mail).apply();
    }
    public String getEmail() {
        String mail = prefss.getString(email, "");
        return mail;
    }
    public String getRenewal_status() {
        String status = prefss.getString(renewal_status,"");
        return status;
    }

    public String getPayment_status() {
        String status = prefss.getString(paymentstatus, "");
        return status;
    }
    public void setRenewal_status(String status) {
        prefss.edit().putString(renewal_status, status).apply();
    }





}
