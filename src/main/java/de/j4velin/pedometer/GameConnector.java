package de.j4velin.pedometer;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.nio.charset.StandardCharsets;

import de.j4velin.pedometer.widget.SaveSharedPreference;

public class GameConnector {
    private final Context ctx;

    //  MODIFY
    private final String baseUrl = "http://xxxxxxxxxx";

    private final String urlLogin = baseUrl + "/login.php";

   private final String urlSteps = baseUrl + "/";

    public GameConnector(Context c) {
        ctx = c;
    }

    public void loginUser(String userName, String password, final GameConnectorCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(ctx);

        String fullUrl = urlLogin
                + "?android-login=true"
                + "&username=" + Uri.encode(userName)
                + "&password=" + Uri.encode(password);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, fullUrl,
                response -> {
                    Log.d("CONNECT", "Server response: " + response);
                    callback.onResponseCallback(response);
                },
                error -> {
                    Log.e("CONNECT", "Volley error", error);

                    if (error.networkResponse != null) {
                        Log.e("CONNECT", "statusCode=" + error.networkResponse.statusCode);

                        if (error.networkResponse.data != null) {
                            String body = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                            Log.e("CONNECT", "body=" + body);
                        }
                    } else {
                        Log.e("CONNECT", "networkResponse=null (timeout / no connection / DNS / SSL / cleartext)");
                    }

                    callback.onResponseCallback("false");
                }
        );

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                0,
                1.0f
        ));

        queue.add(stringRequest);
    }

    public void updateSteps(int steps, final GameConnectorCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(ctx);

        String username = SaveSharedPreference.getUserName(ctx);
        String password = SaveSharedPreference.getUserPass(ctx);

        String fullUrl = urlSteps
                + "?update-steps=true"
                + "&username=" + Uri.encode(username)
                + "&password=" + Uri.encode(password)
                + "&steps=" + steps;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, fullUrl,
                response -> {
                    Log.d("CONNECT", "updateSteps response: " + response);
                    callback.onResponseCallback(response);
                },
                error -> {
                    Log.e("CONNECT", "updateSteps Volley error", error);
                    callback.onResponseCallback("false");
                }
        );

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                0,
                1.0f
        ));

        queue.add(stringRequest);
    }
}
