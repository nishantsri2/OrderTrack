package com.example.ordertrack.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.example.ordertrack.interfaces.OnStatusUpdated;
import com.example.ordertrack.R;
import com.example.ordertrack.models.OrderData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DrawView extends View {

    private Paint paint = new Paint();
    private int width;
    private Bitmap bmp1,bmp2,bmp3,bmp4,bmp5,bmp6,bmp7;
    private ArrayList<OrderData> orderData;
    private Boolean isUpdated;
    private OnStatusUpdated onStatusUpdated;
    private String orderNumber;

    private void init() {
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(50);
        orderData = new ArrayList<>();
        isUpdated = false;

    }

    public DrawView(Context context) {
        super(context);
//        this.context = context;
        onStatusUpdated = (OnStatusUpdated) context;
        init();
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    public void onDraw(Canvas canvas) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);

        int screenWidth = displayMetrics.widthPixels;
        this.width = screenWidth;

        canvas.drawLine(200, 100, width - 200, 100, paint);
        canvas.drawLine(200, 600, width - 200, 600, paint);
        canvas.drawLine(200, 1100, width - 200, 1100, paint);
        drawCurvedArrow(canvas, width - 200, 100, width - 200, 600, 200);
        drawCurvedArrow(canvas, 200, 600, 200, 1100, -200);

        if (!isUpdated) {
            try {
                JSONObject jsonObject = new JSONObject("{\"status\":\"success\",\"error\":\"\",\"data\":{\"order_number\":\"101101894\",\"placed_on\":\"Thu,02Aug2018\",\"order_state\":[{\"status\":1,\"label\":\"Ordered\",\"index\":0,\"date\":\"Thu,02Aug2018\"},{\"status\":1,\"label\":\"Confirmed\",\"index\":1,\"date\":\"Thu,02Aug2018\"},{\"status\":0,\"label\":\"Elvesatwork\",\"index\":2,\"date\":\"Sat,04Aug2018\"},{\"status\":0,\"label\":\"QCReject\",\"index\":3,\"date\":null},{\"status\":1,\"label\":\"QCApproved\",\"index\":4,\"date\":\"Sat,04Aug2018\"},{\"status\":0,\"label\":\"PackedWithLove\",\"index\":5,\"date\":\"Sat,04Aug2018\"},{\"status\":1,\"label\":\"Dispatched\",\"index\":6,\"date\":\"Sat,04Aug2018\"},{\"status\":1,\"label\":\"Delivered\",\"index\":7,\"date\":\"Sat,04Aug2018\"}]},\"serverInformation\":{\"serverName\":\"shop-api\",\"apiVersion\":\"0.0.3\",\"requestDuration\":363,\"currentTime\":1535863124118},\"requesterInformation\":{\"id\":\"70f4f17e937f340d23b3b45e582d63b507d873e6-06f5164a-2abe-4668-8399-efaf76c9d861\",\"fingerprint\":\"70f4f17e937f340d23b3b45e582d63b507d873e6\",\"remoteIP\":\"171.76.154.96\",\"receivedParams\":{\"apiVersion\":\"2\",\"order_number\":\"101101894\",\"action\":\"orderTracking\"}}}");
                JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("order_state");
                orderNumber = jsonObject.getJSONObject("data").getString("order_number");
                for (int i = 0; i < jsonArray.length(); i++) {
                    orderData.add(new OrderData(jsonArray.getJSONObject(i).getInt("status"),
                            jsonArray.getJSONObject(i).getString("label"),
                            jsonArray.getJSONObject(i).getString("date")));

                }
                // Item removed as screenshot doesnot include this index item.
                orderData.remove(3);
            } catch (JSONException e) {
                e.printStackTrace();

            }
        }

        bmp1 = getBitmapFromStatus(0);
        bmp2 = getBitmapFromStatus(1);
        bmp3 = getBitmapFromStatus(2);
        bmp4 = getBitmapFromStatus(3);
        bmp5 = getBitmapFromStatus(4);
        bmp6 = getBitmapFromStatus(5);
        bmp7 = getBitmapFromStatus(6);

        canvas.drawBitmap(bmp1, 200 - bmp1.getWidth() / 2, 100 - bmp1.getHeight() / 2, null);
        canvas.drawBitmap(bmp2, width / 2, 100 - bmp2.getHeight() / 2, null);
        canvas.drawBitmap(bmp3, width / 2 + 200, 600 - bmp3.getHeight() / 2, null);
        canvas.drawBitmap(bmp4, width / 2 - 300, 600 - bmp4.getHeight() / 2, null);
        canvas.drawBitmap(bmp5, width / 2 - 400, 1100 - bmp5.getHeight() / 2, null);
        canvas.drawBitmap(bmp6, width / 2, 1100 - bmp6.getHeight() / 2, null);
        canvas.drawBitmap(bmp7, width - 200 - bmp7.getHeight() / 2, 1100 - bmp7.getHeight() / 2, null);

        canvas.drawText(orderData.get(0).getLabel(), 200 - bmp1.getWidth() / 2, 100 +
                bmp1.getHeight(), paint);
        canvas.drawText(orderData.get(1).getLabel(), width / 2 - bmp2.getWidth() / 2, 100 +
                bmp2.getHeight(), paint);
        canvas.drawText(orderData.get(2).getLabel(), width / 2 + 200 - bmp3.getWidth() / 2,
                600 + bmp3.getHeight(), paint);
        canvas.drawText(orderData.get(3).getLabel(), width / 2 - 300 - bmp4.getWidth() / 2,
                600 + bmp4.getHeight(), paint);
        canvas.drawText(orderData.get(4).getLabel(), width / 2 - 400 - bmp5.getWidth() / 2,
                1100 + bmp5.getHeight(), paint);
        canvas.drawText(orderData.get(5).getLabel(), width / 2 - bmp6.getWidth() / 2,
                1100 + bmp6.getHeight(), paint);
        canvas.drawText(orderData.get(6).getLabel(), width - 200 - bmp7.getHeight(),
                1100 + bmp7.getHeight(), paint);

        canvas.drawText(orderData.get(0).getDate(), 200 - bmp1.getWidth(), 200 +
                bmp1.getHeight(), paint);
        canvas.drawText(orderData.get(1).getDate(), width / 2 - bmp2.getWidth() / 2,
                200 + bmp2.getHeight(), paint);
        canvas.drawText(orderData.get(2).getDate(), width / 2 + 200 - bmp3.getWidth() / 2,
                700 + bmp3.getHeight(), paint);
        canvas.drawText(orderData.get(3).getDate(), width / 2 - 300 - bmp4.getWidth() / 2,
                700 + bmp4.getHeight(), paint);
        canvas.drawText(orderData.get(4).getDate(), width / 2 - 400 - bmp5.getWidth() / 2,
                1200 + bmp5.getHeight(), paint);
        canvas.drawText(orderData.get(5).getDate(), width / 2 - bmp6.getWidth() / 2,
                1200 + bmp6.getHeight(), paint);
        canvas.drawText(orderData.get(6).getDate(), width - 200 - bmp7.getHeight(),
                1200 + bmp7.getHeight(), paint);

    }

    private Bitmap getBitmapFromStatus(int pos) {
        int status = orderData.get(pos).getStatus();
        if (status == 0) {
            return BitmapFactory.decodeResource(getResources(), R.drawable.hollow_circle);
        }
        return BitmapFactory.decodeResource(getResources(), R.drawable.filled_circle);
    }

    public void drawCurvedArrow(Canvas canvas, int x1, int y1, int x2, int y2, int curveRadius) {
        final Path path = new Path();
        int midX = x1 + ((x2 - x1) / 2);
        int midY = y1 + ((y2 - y1) / 2);
        float xDiff = midX - x1;
        float yDiff = midY - y1;
        double angle = (Math.atan2(yDiff, xDiff) * (180 / Math.PI)) - 90;
        double angleRadians = Math.toRadians(angle);
        float pointX = (float) (midX + curveRadius * Math.cos(angleRadians));
        float pointY = (float) (midY + curveRadius * Math.sin(angleRadians));

        path.moveTo(x1, y1);
        path.cubicTo(x1, y1, pointX, pointY, x2, y2);
        canvas.drawPath(path, paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // TODO: Getting clicked event for each image/Status
                if (getClickedEvent(x, y, 200, 100, bmp1)) {
                    swapStatus(0);
                } else if (getClickedEvent(x, y, width / 2 + bmp2.getWidth() / 2, 100, bmp2)) {
                    swapStatus(1);
                } else if (getClickedEvent(x, y, width / 2 + 200 + bmp3.getWidth() / 2, 600, bmp3)) {
                    swapStatus(2);
                } else if (getClickedEvent(x, y, width / 2 - 300 + bmp4.getWidth() / 2, 600, bmp4)) {
                    swapStatus(3);
                } else if (getClickedEvent(x, y, width / 2 - 400 + bmp5.getWidth() / 2, 1100, bmp5)) {
                    swapStatus(4);
                } else if (getClickedEvent(x, y, width / 2 + bmp6.getWidth() / 2, 1100, bmp6)) {
                    swapStatus(5);
                } else if (getClickedEvent(x, y, width - 200, 1100, bmp7)) {
                    swapStatus(6);
                }
                return true;
        }
        return false;
    }

    private boolean getClickedEvent(float eventXpos, float eventYpos, int startXpos, int startYpos, Bitmap bitmap) {
        return eventXpos > (startXpos - bitmap.getWidth() / 2) && eventXpos < (startXpos +
                bitmap.getWidth() / 2) && eventYpos > (startYpos - bitmap.getHeight() / 2) &&
                eventYpos < (startYpos + bitmap.getHeight() / 2);
    }

    private void swapStatus(int pos) {
        int status = orderData.get(pos).getStatus();
        if (status == 0) {
            orderData.get(pos).setStatus(1);
        } else {
            orderData.get(pos).setStatus(0);

        }
        if (!isUpdated) {
            isUpdated = true;
        }
        onStatusUpdated.statusUpdated(orderData.get(pos).getStatus(), orderNumber);
        invalidate();
    }

}
