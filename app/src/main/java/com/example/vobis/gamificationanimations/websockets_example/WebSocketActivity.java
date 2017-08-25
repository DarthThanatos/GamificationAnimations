package com.example.vobis.gamificationanimations.websockets_example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.vobis.gamificationanimations.R;
import com.websocket.client.QSocket;
import com.websocket.client.QSocketOptions;
import com.websocket.client.channel.Channel;
import com.websocket.client.channel.ChannelEventListener;
import com.websocket.client.channel.ChannelUnsubscriptionEventListener;
import com.websocket.client.connection.ConnectionEventListener;
import com.websocket.client.connection.ConnectionState;
import com.websocket.client.connection.ConnectionStateChange;

public class WebSocketActivity extends AppCompatActivity {

    private static final String TAG = WebSocketActivity.class.getSimpleName();
    private QSocket qSocket;
    private final long startTime = System.currentTimeMillis();
    private Channel channel;
    private TextView dataTV;

    private boolean shouldContinue = true;
    ConnectionEventListener connectionEventListener = new ConnectionEventListener() {
        @Override
        public void onConnectionStateChange(ConnectionStateChange change) {
            Log.i("Web socket Connection", String.format("[%1$s] Connection state changed from [%2$s] to [%3$s]", Long.toString(timestamp()),
                    change.getPreviousState(), change.getCurrentState()));
            doDisplay(String.format("[%1$s] Connection state changed from [%2$s] to [%3$s]", Long.toString(timestamp()),
                    change.getPreviousState(), change.getCurrentState()));
        }

        @Override
        public void onError(String message, String code, Exception e) {
            Log.i("Web socket Connection", String.format("[%d] An error was received with message [%s], code [%s], exception [%s]",
                    timestamp(), message, code, e));
            doDisplay(String.format("[%1$s] An error was received with message [%2$s], code [%3$s], exception [%4$s]",
                    Long.toString(timestamp()), message, code, e));
        }
    };

    ChannelEventListener channelEventListener = new ChannelEventListener() {
        @Override
        public void onSubscriptionSucceeded(String channelName) {
            Log.i("channelSubscription", String.format("[%1$s] Subscription to channel [%2$s] succeeded", Long.toString(timestamp()), channelName));
            doDisplay(String.format("[%1$s] Subscription to channel [%2$s] succeeded", Long.toString(timestamp()), channelName));
        }

        @Override
        public void onEvent(String channelName, String eventName, String data) {
            Log.i("ReceivedEventData:", String.format("[%d] Received event [%s] on channel [%s] with data [%s]", timestamp(),
                    eventName, channelName, data));
            doDisplay(String.format("[%1$s] Received event [%2$s] on channel [%3$s] with data [%4$s]", Long.toString(timestamp()),
                    eventName, channelName, data));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_socket);
        dataTV = (TextView) findViewById(R.id.dataTV);
        QSocketOptions options = new QSocketOptions().setAuthorizationToken("1234567890").setEncrypted(false);
        qSocket = new QSocket(options, this);

        connect();
        subscribeChannel(dataTV);
    }


    @Override
    public void onDestroy(){
        Log.d(TAG, "On destroy");
        unsubscribeChannel();
        disconnect();
        shouldContinue = false;
        Log.d(TAG, "before super on destroy");
        super.onDestroy();
        Log.d(TAG, "after super on destroy");
    }

    public void connect() {
        qSocket.connect(connectionEventListener, ConnectionState.ALL);
    }

    public void disconnect() {
        qSocket.disconnect();
    }

    public void subscribeChannel(View view) {
        channel = qSocket.subscribe("Channel B", channelEventListener);
    }

    public void unsubscribeChannel() {
        qSocket.unsubscribe("Channel B", channelName -> {
            Log.i("channelUnsubscription", String.format("[%d] Unsubscription to channel [%s] succeeded", timestamp(), channelName));
            doDisplay(String.format("[%1$s] Unsubscription to channel [%2$s] succeeded", Long.toString(timestamp()), channelName));
        });
    }

    private long timestamp() {
        return System.currentTimeMillis() - startTime;
    }

    private void doDisplay(final String data) {
        runOnUiThread(() -> dataTV.setText(data));
    }
}
