package com.bahaa.github.data.network;

import android.content.Context;
import android.util.Log;

import com.bahaa.github.ui.activities.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkInterceptor implements Interceptor {

    private Context context;
    private NetworkEvent networkEvent;

    public NetworkInterceptor(Context context) {
        this.context = context;
        this.networkEvent = NetworkEvent.getInstance();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (!ConnectivityStatus.isConnected(context)) {
            BaseActivity.getInstance().hideProgressDialog();
            networkEvent.publish(NetworkState.NO_INTERNET);
            Log.d("NETWORK", "code : " + request.body().toString()+ " " + request.url());
        } else {
            try {
                Response response = chain.proceed(request);
//                Log.d("NETWORK", "////////////////////////////////////////////////");
//                Log.d("NETWORK", "code : " + response.code() + "  " + request.url());
//                Log.d("NETWORK", "Response : " + response.body().string());
                BaseActivity.getInstance().hideProgressDialog();
                switch (response.code()) {
                    case 401:
                        networkEvent.publish(NetworkState.UNAUTHORISED);
                        break;
                    case 500:
                        networkEvent.publish(NetworkState.SERVER_ERROR);
                        break;
                    case 404:
                        networkEvent.publish(NetworkState.API_NOT_FOUND);
                        break;
                    case 405:
                        networkEvent.publish(NetworkState.NOT_ALLOWED_METHOD);
                        break;
                    case 503:
                        networkEvent.publish(NetworkState.MAINTENANCE);
                        break;
                    case 400:
                    case 403:
                        String errorMessage403 = "";
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject (response.body().string());
                            errorMessage403 = jsonObject.optJSONArray("error").getString(0);
                        } catch (JSONException e) {
                            Log.e("NETWORK", e.getMessage());
                        }
                        if (jsonObject != null) {
                            handle400(errorMessage403, jsonObject);
                        }
                        break;
                }
                return response;

            } catch (IOException e) {
                Log.e("NETWORK", e.getMessage());
                networkEvent.publish(NetworkState.NO_RESPONSE);
            }
        }
        return null;
    }

    private void handle400(String message, JSONObject jsonObject){
        switch (message){
            case "User is logged.":
                networkEvent.publish(NetworkState.ALREADY_LOGIN);
                break;
            case "User is not logged.":
            case "User is not logged in":
                networkEvent.publish(NetworkState.UNAUTHORISED);
                break;
            default:
                networkEvent.publish(NetworkState.BAD_REQUEST);
        }
    }
}
