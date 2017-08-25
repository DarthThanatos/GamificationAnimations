package com.example.vobis.gamificationanimations.websockets_okhttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.Button;
import android.widget.TextView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

import com.example.vobis.gamificationanimations.R;
import com.example.vobis.gamificationanimations.config.Config;

public class WebsocketOkHttpActivity extends AppCompatActivity implements OnWebSocketOutput {

    private TextView output;
    private OkHttpClient client;
    private static final String TAG = WebsocketOkHttpActivity.class.getSimpleName();
    private WebSocket gameInnWebSocket;
    private WebSocket echoWebSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_websocket_ok_http);

        Button startEcho = (Button) findViewById(R.id.start_echo);
        startEcho.setOnClickListener(view -> startEchoing());

        Button startGameInn = (Button) findViewById(R.id.start_gameinn);
        startGameInn.setOnClickListener(view -> startGameInning());

        output = (TextView) findViewById(R.id.output);
    }

    private void closeGameInnWebSocket(){
        if(gameInnWebSocket != null) gameInnWebSocket.close(Config.NORMAL_CLOSURE_STATUS, null);
    }

    private void closeEchoSocket(){
        if(echoWebSocket != null) echoWebSocket.close(Config.NORMAL_CLOSURE_STATUS, null);
    }

    @Override
    public void onDestroy(){
        closeGameInnWebSocket();
        closeEchoSocket();
        super.onDestroy();
    }

    private void startGameInning() {
        String apiURL = "http://192.168.0.51:8080/rankings/updates"; //"https://gameinn.sosoftware.pl/akka/rankings/updates";
        Request request = new Request.Builder().url(apiURL).build();
        GameInnWebSocketListener gameInnWebSocketListener = new GameInnWebSocketListener(this);
        client = new OkHttpClient();
        closeGameInnWebSocket();
        gameInnWebSocket = client.newWebSocket(request, gameInnWebSocketListener);
        client.dispatcher().executorService().shutdown();
    }

    private void startEchoing() {
        Request request = new Request.Builder().url("ws://echo.websocket.org").build();
        EchoWebSocketListener listener = new EchoWebSocketListener(this);
        client = new OkHttpClient();
        closeEchoSocket();
        echoWebSocket = client.newWebSocket(request, listener);
        client.dispatcher().executorService().shutdown();
    }

     @Override
     public void output(final String txt) {
        runOnUiThread(() -> output.setText(output.getText().toString() + "\n\n" + txt));
    }
}
