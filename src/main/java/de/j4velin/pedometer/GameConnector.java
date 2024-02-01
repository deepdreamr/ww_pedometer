package de.j4velin.pedometer;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import de.j4velin.pedometer.widget.SaveSharedPreference;


public class GameConnector {
    private final Context ctx;
    private final String url ="http://www.test.walkingwarrior.hu/gateway.php";

    public GameConnector(Context c) {
        ctx = c;
    }

    public void loginUser(String userName, String password, final GameConnectorCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(ctx);

        String params = "?android-login=true&username=" + userName + "&password=" + password;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + params,
                response -> {
                    Log.e("Response:", response);
                    callback.onResponseCallback(response);
                }, error -> {
                    //Something on error state
                });
        queue.add(stringRequest);
    }

    public void updateSteps(int steps, final GameConnectorCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(ctx);

        String username = SaveSharedPreference.getUserName(ctx);
        String password = SaveSharedPreference.getUserPass(ctx);

        String params = "?update-steps=true&username=" + username + "&password=" + password + "&steps=" + steps;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + params,
                response -> callback.onResponseCallback(response), error -> {
                    //Something on error state
                });
        queue.add(stringRequest);
    }
}
