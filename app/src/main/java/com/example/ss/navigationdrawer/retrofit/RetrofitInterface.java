package com.example.ss.navigationdrawer.retrofit;
import com.example.ss.navigationdrawer.request.CommonRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface RetrofitInterface {

    public String url = "http://www.developer.hostatecphasis.com/laflair_new/crm/mobile/";

    //public  String serverUrl="http://newspot.co.in/news/api/mobile/";

    public static String serverUrl="http://newspot.co.in/news/api/mobile/";

    @POST("login")
    Call<ListDetails> Login(@Body CommonRequest commonRequest);

    @POST("mob_signup")
    Call<ListDetails> Register_post (@Body CommonRequest commonRequest);

    @POST("recently_music_watch_list")
    Call<ListDetails> recently_music(@Body CommonRequest commonRequest);

    @POST("recently_live_watch_list")
    Call<ListDetails>recently_watch_list(@Body CommonRequest commonRequest);

    @POST("getuserprofile")
    Call<ListDetails> user_details(@Body CommonRequest commonRequest);

    @POST("profile_update")
    Call<ListDetails> profile_update (@Body CommonRequest commonRequest);


    @GET("category_list")
    Call<ListDetails> category_list();

}
