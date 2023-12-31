package org.rmj.g3appdriver.GConnect.Authentication.obj;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import org.json.JSONObject;
import org.rmj.g3appdriver.GConnect.Api.GConnectApi;
import org.rmj.g3appdriver.dev.Http.HttpHeaderManager;
import org.rmj.g3appdriver.dev.Http.WebClient;
import org.rmj.g3appdriver.lib.authentication.factory.iAuthenticate;

public class ResetPassword implements iAuthenticate {
    private static final String TAG = ResetPassword.class.getSimpleName();

    private final Application instance;

    private String message;

    public ResetPassword(Application instance) {
        this.instance = instance;
    }

    @Override
    public int DoAction(Object args) {
        try{
            if(args == null){
                message = "Please enter your email";
                return 0;
            }

            String lsEmail = (String) args;
            if(lsEmail.trim().isEmpty()){
                message = "Please enter your email";
                return 0;
            }

            if(TextUtils.isEmpty(lsEmail) &&
                    Patterns.EMAIL_ADDRESS.matcher(lsEmail).matches()){
                message = "Please enter valid email";
                return 0;
            }

            JSONObject params = new JSONObject();
            params.put("email", lsEmail);

            String lsResponse = WebClient.sendRequest(
                    new GConnectApi(instance).getRetrievePasswordAPI(),
                    params.toString(),
                    HttpHeaderManager.getInstance(instance).initializeHeader().getHeaders());
            if(lsResponse == null){
                message = SERVER_NO_RESPONSE;
                return 0;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if (lsResult.equalsIgnoreCase("error")) {
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                Log.e(TAG, message);
                return 0;
            }

            message = "We've sent an email to your registered address with instructions for password recovery.";
            return 1;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return 0;
        }
    }

    @Override
    public String getMessage() {
        return message;
    }
}
