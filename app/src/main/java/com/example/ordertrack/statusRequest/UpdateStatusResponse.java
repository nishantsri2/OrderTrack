package com.example.ordertrack.statusRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class UpdateStatusResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;

    @SerializedName("data")
    @Expose
    private HashMap<String, Integer> bar_details;

    public HashMap<String, Integer> getBar_details() {
        return bar_details;
    }

    public Boolean getSuccess() {
        return success;

    }
}