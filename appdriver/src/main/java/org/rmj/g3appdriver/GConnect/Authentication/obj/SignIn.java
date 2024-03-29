package org.rmj.g3appdriver.GConnect.Authentication.obj;

import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.util.Log;

import org.json.JSONObject;
import org.rmj.g3appdriver.Config.DeviceConfig;
import org.rmj.g3appdriver.GConnect.Account.ClientSession;
import org.rmj.g3appdriver.GConnect.Api.GConnectApi;
import org.rmj.g3appdriver.GConnect.room.DataAccessObject.DClientInfo;
import org.rmj.g3appdriver.GConnect.room.Entities.EClientInfo;
import org.rmj.g3appdriver.GConnect.room.GGC_GConnectDB;
import org.rmj.g3appdriver.dev.Http.HttpHeaderManager;
import org.rmj.g3appdriver.dev.Http.WebClient;
import org.rmj.g3appdriver.lib.authentication.factory.iAuthenticate;
import org.rmj.g3appdriver.lib.authentication.pojo.LoginCredentials;

public class SignIn implements iAuthenticate {
    private static final String TAG = SignIn.class.getSimpleName();

    private final Application instance;
    private final DClientInfo poDao;
    private final DeviceConfig poConfig;

    private String message;

    public SignIn(Application instance) {
        this.instance = instance;
        this.poDao = GGC_GConnectDB.getInstance(instance).EClientDao();
        this.poConfig = DeviceConfig.getInstance(instance);
    }

    @Override
    public int DoAction(Object args) {
        try{
            LoginCredentials loInfo = (LoginCredentials) args;
            LoginCredentials.EntryValidator loValidator = new LoginCredentials.EntryValidator();
            if(!loValidator.isDataValid(loInfo)){
                message = loValidator.getMessage();
                return 0;
            }

            JSONObject params = new JSONObject();
            params.put("user", loInfo.getEmail());
            params.put("pswd", loInfo.getPassword());

            String lsResponse = WebClient.sendRequest(
                    new GConnectApi(instance).getSIGN_IN(),
                    params.toString(),
                    HttpHeaderManager.getInstance(instance).initializeHeader().getHeaders());

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if (lsResult.equalsIgnoreCase("error")) {

                JSONObject loError = loResponse.getJSONObject("error");
                String lsErrCode = loError.getString("code");
                if(lsErrCode.equalsIgnoreCase("40003")){
                    String lsOtp = loResponse.getString("otp");
                    String lsVfy = loResponse.getString("verify");
                    message = getErrorMessage(loError);
                    return 2;
                }

                message = getErrorMessage(loError);
                return 0;
            }

            ClientSession loAccount = ClientSession.getInstance(instance);
            loAccount.setUserID(loResponse.getString("sUserIDxx"));
            loAccount.setFullName(loResponse.getString("sUserName"));
            //loAccount.setMobileNo(loInfo.getMobileNo());
            loAccount.setLoginStatus(true);

            EClientInfo loClient = new EClientInfo();

            loClient.setDateMmbr(loResponse.getString("dCreatedx"));
            loClient.setEmailAdd(loResponse.getString("sEmailAdd"));
            loClient.setUserName(loResponse.getString("sUserName"));
            loClient.setMobileNo(loResponse.getString("sMobileNo"));
            loClient.setUserIDxx(loResponse.getString("sUserIDxx"));
            poDao.RemoveSessions();
            poDao.insert(loClient);
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
