package com.example.ordertrack.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ordertrack.views.DrawView;
import com.example.ordertrack.interfaces.OnStatusUpdated;
import com.example.ordertrack.statusRequest.ApiError;
import com.example.ordertrack.statusRequest.StatusInjector;
import com.example.ordertrack.statusRequest.UpdateStatusResponse;

public class HomeActivity extends AppCompatActivity implements OnStatusUpdated, StatusInjector {
    DrawView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawView = new DrawView(this);
        drawView.setBackgroundColor(Color.WHITE);
        setContentView(drawView);
    }

    @Override
    public void statusUpdated(int statusCode, String orderNumber) {
        // TODO: This commented code perform the order status update API request
        //new StatusData(this).loginUser(orderNumber, statusCode);

        Toast.makeText(this, "Status updated for order number " + orderNumber,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpdateSuccess(UpdateStatusResponse response) {
        // TODO: 28/10/18 Success response is catched over here 
    }

    @Override
    public void onUpdateFailed(ApiError error) {
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
