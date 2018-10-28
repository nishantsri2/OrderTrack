package com.example.ordertrack.statusRequest;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface StatusResource {
    @POST("order/update-status")
    @FormUrlEncoded
    Call<UpdateStatusResponse> updateOrderStatus(@Field("order_id") String orderId,
                                         @Field("status_code") int statusCode);
}
