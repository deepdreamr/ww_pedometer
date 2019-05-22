package de.j4velin.pedometer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import de.j4velin.pedometer.widget.SaveSharedPreference;


public class GameConnector {
    private Context ctx;
    private final String url ="http://www.test.walkingwarrior.hu/gateway.php";

    public GameConnector(Context c) {
        ctx = c;
    }

    public void loginUser(String userName, String password, final GameConnectorCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(ctx);

        String params = "?android-login=true&username=" + userName + "&password=" + password;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + params,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response:", response);
                        callback.onResponseCallback(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Something on error state
            }
        });
        queue.add(stringRequest);
    }

    public void updateSteps(int steps, final GameConnectorCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(ctx);

        String username = SaveSharedPreference.getUserName(ctx);
        String password = SaveSharedPreference.getUserPass(ctx);

        String params = "?update-steps=true&username=" + username + "&password=" + password + "&steps=" + steps;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + params,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onResponseCallback(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Something on error state
            }
        });
        queue.add(stringRequest);
    }
}
