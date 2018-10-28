package com.example.ordertrack.statusRequest;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * Created by Kustard on 11/09/17.
 */

public abstract class ApiCallback<T> implements Callback<T> {

    public abstract void onSuccess(T t);

    public abstract void onFailure(ApiError error);


    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            onSuccess(response.body());
        } else {
            onFailure(parseError(response));
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        //Need to change this to take into account various fail cases and not make this generic
        ApiError error = new ApiError();
        Log.e("error", t.toString());
        if (t instanceof JsonSyntaxException) {
            error.setStatusCode(501);
            error.setMessage("Something went wrong");
        }else {
            error.setStatusCode(501);
            error.setMessage("No Internet");
        }
        onFailure(error);
    }

    public static ApiError parseError(Response<?> response) {
        Converter<ResponseBody, ApiError> converter =
                HttpInjector.getRetrofit()
                        .responseBodyConverter(ApiError.class, new Annotation[0]);
        ApiError error;

        try {
            Log.d("tag",response.errorBody().string());
            error = new Gson().fromJson(response.errorBody().string(), ApiError.class);
            error.setStatusCode(response.code());
        } catch (Exception e) {
            e.printStackTrace();
            error = new ApiError();
            error.setStatusCode(501);
            error.setMessage("conversion failed");
        }
//        Logger.d(error.getMessage());
        return error;
    }
}