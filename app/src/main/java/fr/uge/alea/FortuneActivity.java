package fr.uge.alea;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONTokener;

public class FortuneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fortune);

        final TextView textView = (TextView) findViewById(R.id.text);
        newFortune(textView);

        Button button = (Button) findViewById(R.id.new_fortune);
        button.setOnClickListener(v -> newFortune(textView));

        Button switchB = (Button) findViewById(R.id.switchDice);
        switchB.setOnClickListener(v -> {
            finish();
        });


    }
    public static String unescapeJSONString(String v) throws JSONException {
        return new JSONTokener(v).nextValue().toString();
    }

    public void newFortune(TextView textView){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://fortune.plade.org/";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //pas de probleme avec les guillemets pour moi
                        textView.setText(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("That didn't work because of this error: " + error);
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}