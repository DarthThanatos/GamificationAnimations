package com.example.vobis.gamificationanimations.websockets_okhttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.Button;
import android.widget.TextView;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import com.example.vobis.gamificationanimations.R;

public class WebsocketOkHttpActivity extends AppCompatActivity implements OnWebSocketOutput {

    private Button start;
    private TextView output;
    private OkHttpClient client;
    private static final String TAG = WebsocketOkHttpActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_websocket_ok_http);
        start = (Button) findViewById(R.id.start_echo);
        output = (TextView) findViewById(R.id.output);
        start.setOnClickListener(view -> start());
    }
    private void start() {
        Request request = new Request.Builder().url("ws://echo.websocket.org").build();
        EchoWebSocketListener listener = new EchoWebSocketListener(this);
        client = new OkHttpClient();
        client.newWebSocket(request, listener);
        client.dispatcher().executorService().shutdown();
    }

     @Override
     public void output(final String txt) {
        runOnUiThread(() -> output.setText(output.getText().toString() + "\n\n" + txt));
    }
}
