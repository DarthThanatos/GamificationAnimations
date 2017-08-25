package com.example.vobis.gamificationanimations.websockets_okhttp;

import android.util.Log;

import com.example.vobis.gamificationanimations.config.Config;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * Created by rbielas on 25.08.17
 */
public class GameInnWebSocketListener extends WebSocketListener{
    private static final String TAG = GameInnWebSocketListener.class.getSimpleName();
    private OnWebSocketOutput onWebSocketOutput;
    GameInnWebSocketListener(OnWebSocketOutput onWebSocketOutput){
        this.onWebSocketOutput = onWebSocketOutput;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {

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
        webSocket.close(Config.NORMAL_CLOSURE_STATUS, null);
        onWebSocketOutput.output("Closing : " + code + " / " + reason);
    }
    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        Log.d(TAG, "Error : " + t.getMessage());
        onWebSocketOutput.output("Error : " + t.getMessage());
    }
}
