package com.example.ordertrack.statusRequest;

public interface StatusInjector {
    void onUpdateSuccess(UpdateStatusResponse loginResponse);
    void onUpdateFailed(ApiError error);
}
