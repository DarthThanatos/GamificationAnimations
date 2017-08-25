package com.example.vobis.gamificationanimations.websockets_okhttp;

import android.util.Log;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * Created by rbielas on 25.08.17
 */
class EchoWebSocketListener extends WebSocketListener {
    private static final int NORMAL_CLOSURE_STATUS = 1000;
    private static final String TAG = EchoWebSocketListener.class.getSimpleName();
    private OnWebSocketOutput onWebSocketOutput;

    EchoWebSocketListener(OnWebSocketOutput onWebSocketOutput){
        this.onWebSocketOutput = onWebSocketOutput;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        Log.d(TAG, "sending hello its samuel");
        webSocket.send("Hello, it's SSaurel !");
        Log.d(TAG, "sending whats up");
        webSocket.send("What's up ?");
        Log.d(TAG, "sending deadbeef");
        webSocket.send(ByteString.decodeHex("deadbeef"));
        Log.d(TAG, "closing with notmal closure status & msg goodbye");
        webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye!");
    }
    @Override
    public void onMessage(WebSocket webSocket, String text) {
        Log.d(TAG, "Receiving" + text);
        onWebSocketOutput.output("Receiving : " + text);
    }
    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        Log.d(TAG, "Receiving bytes : " + bytes.hex());
        onWebSocketOutput.output("Receiving bytes : " + bytes.hex());
    }
    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        Log.d(TAG, "Closing : " + code + " / " + reason);
        webSocket.close(NORMAL_CLOSURE_STATUS, null);
        onWebSocketOutput.output("Closing : " + code + " / " + reason);
    }
    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        Log.d(TAG, "Error : " + t.getMessage());
        onWebSocketOutput.output("Error : " + t.getMessage());
    }

}
