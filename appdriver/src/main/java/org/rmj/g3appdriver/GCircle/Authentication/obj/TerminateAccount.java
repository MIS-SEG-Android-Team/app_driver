package org.rmj.g3appdriver.GCircle.Authentication.obj;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.util.Log;

import org.json.JSONObject;
import org.rmj.g3appdriver.GCircle.Api.GCircleApi;
import org.rmj.g3appdriver.dev.Http.HttpHeaderManager;
import org.rmj.g3appdriver.dev.Http.HttpHeaderProvider;
import org.rmj.g3appdriver.dev.Http.WebClient;
import org.rmj.g3appdriver.lib.authentication.factory.iAuthenticate;

public class TerminateAccount implements iAuthenticate {
    private static final String TAG = TerminateAccount.class.getSimpleName();

    private final Application instance;
    private final GCircleApi poApi;
    private final HttpHeaderProvider poHeaders;

    private String message;

    public TerminateAccount(Application instance) {
        this.instance = instance;
        this.poApi = new GCircleApi(instance);
        this.poHeaders = HttpHeaderManager.getInstance(instance).initializeHeader();
    }

    @Override
    public int DoAction(Object args) {
        try{
            String lsPasswrd = (String) args;

            if(lsPasswrd == null){
                message = "";
                return 0;
            }

            if(lsPasswrd.trim().isEmpty()){
                message = "";
                return 0;
            }

            JSONObject params = new JSONObject();
            params.put("password", lsPasswrd);

            String lsResponse = WebClient.sendRequest(
                    poApi.getUrlDeactivateAccount(),
                    params.toString(),
                    poHeaders.getHeaders());

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

            message = "Account deactivation will be process for 24 hrs";
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
