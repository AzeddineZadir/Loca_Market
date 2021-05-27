package com.example.loca_market.ui.client.Notifications;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitCaller {

    @Headers({"Authorization: key=AAAAti01zsw:APA91bFVx0YClqS4uHSgMswvtdpu138YFM3st6X3MwDjpj3FVZ_mTIirvJT9pRra_Dx1LiMS6FuucBWCCmFNiwDBPKv58q8MB-UQAD92K0qNJQRYMksKZOFGMUGpGsayJg4pilEZ208u",
            "Content-Type:application/json"})
    @POST("fcm/send")
    Call<NotificationRoot> PostData(@Body NotificationRoot notificationRoot);
}
