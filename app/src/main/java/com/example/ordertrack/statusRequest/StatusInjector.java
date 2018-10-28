package com.example.ordertrack.statusRequest;

public interface StatusInjector {
    void onUpdateSuccess(UpdateStatusResponse statusResponse);
    void onUpdateFailed(ApiError error);
}
