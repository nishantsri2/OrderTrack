package com.example.ordertrack.statusRequest;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Retrofit;

public class StatusData {
    private StatusInjector injector;

    public StatusData(StatusInjector injector) {
        this.injector = injector;
    }
    public void loginUser(String orderId,int statusCode){
        Retrofit retrofit = HttpInjector.getRetrofit();
        StatusResource statusResource = retrofit.create(StatusResource.class);
        JSONObject paramObject = new JSONObject();
        try {
            paramObject.put("order_id", orderId);
            paramObject.put("status_code", statusCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Call<UpdateStatusResponse> call = statusResource.updateOrderStatus(orderId, statusCode);
        call.enqueue(new ApiCallback<UpdateStatusResponse>() {
            @Override
            public void onSuccess(UpdateStatusResponse statusResponse) {
                injector.onUpdateSuccess(statusResponse);
            }
            @Override
            public void onFailure(ApiError error) {
                injector.onUpdateFailed(error);
            }
        });
    }
}
