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
            Log.i("WebsocketConnection", String.format("[%d] Connection state changed from [%s] to [%s]", timestamp(),
                    change.getPreviousState(), change.getCurrentState()));
            doDisplay(String.format("[%d] Connection state changed from [%s] to [%s]", timestamp(),
                    change.getPreviousState(), change.getCurrentState()));
        }

        @Override
        public void onError(String message, String code, Exception e) {
            Log.i("WebsocketConnection", String.format("[%d] An error was received with message [%s], code [%s], exception [%s]",
                    timestamp(), message, code, e));
            doDisplay(String.format("[%d] An error was received with message [%s], code [%s], exception [%s]",
                    timestamp(), message, code, e));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_socket);
        dataTV = (TextView) findViewById(R.id.dataTV);
        QSocketOptions options = new QSocketOptions().setAuthorizationToken("1234567890").setEncrypted(false);
        qSocket = new QSocket(options, this);

        connect(dataTV);
//        subscribeChannel(dataTV);
    }


    @Override
    public void onDestroy(){
        Log.d(TAG, "On destroy");
//        disconnect(dataTV);
//        unsubscribeChannel(dataTV);
        shouldContinue = false;
        super.onDestroy();
    }

    public void connect(View view) {
        qSocket.connect(connectionEventListener, ConnectionState.ALL);
    }

    public void disconnect(View view) {
        qSocket.disconnect();
    }

    public void subscribeChannel(View view) {
        channel = qSocket.subscribe("Channel B", new ChannelEventListener() {
            @Override
            public void onSubscriptionSucceeded(String channelName) {
                Log.i("channelSubscription", String.format("[%d] Subscription to channel [%s] succeeded", timestamp(), channelName));
                doDisplay(String.format("[%d] Subscription to channel [%s] succeeded", timestamp(), channelName));
            }

            @Override
            public void onEvent(String channelName, String eventName, String data) {
                Log.i("ReceivedEventData:", String.format("[%d] Received event [%s] on channel [%s] with data [%s]", timestamp(),
                        eventName, channelName, data));
                doDisplay(String.format("[%d] Received event [%s] on channel [%s] with data [%s]", timestamp(),
                        eventName, channelName, data));
            }
        });
    }

    public void unsubscribeChannel(View view) {
        qSocket.unsubscribe("Channel B", new ChannelUnsubscriptionEventListener() {
            @Override
            public void onUnsubscribed(String channelName) {
                Log.i("channelUnsubscription", String.format("[%d] Unsubscription to channel [%s] succeeded", timestamp(), channelName));
                doDisplay(String.format("[%d] Unsubscription to channel [%s] succeeded", timestamp(), channelName));
            }
        });
    }

    private long timestamp() {
        return System.currentTimeMillis() - startTime;
    }

    private void doDisplay(final String data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dataTV.setText(data);
            }
        });
    }
}
