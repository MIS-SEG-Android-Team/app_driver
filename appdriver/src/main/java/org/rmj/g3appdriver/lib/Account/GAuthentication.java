package org.rmj.g3appdriver.lib.Account;

import android.app.Application;
import android.util.Log;

import org.rmj.g3appdriver.lib.Account.Model.iAuthentication;
import org.rmj.g3appdriver.GCircle.Authentication.GCircleAccount_Impl;
import org.rmj.g3appdriver.GConnect.Authentication.gConnectAuth;
import org.rmj.g3appdriver.etc.AppConfigPreference;

public class GAuthentication {
    private static final String TAG = GAuthentication.class.getSimpleName();

    private final Application instance;
    private final AppConfigPreference poConfig;

    public GAuthentication(Application instance) {
        this.instance = instance;
        this.poConfig = AppConfigPreference.getInstance(instance);
    }

    public iAuthentication initAppAuthentication(){
        String lsProdctID = poConfig.ProducID();
        if ("gRider".equals(lsProdctID)) {
            Log.d(TAG, "Initialize employee authentication");
            return new GCircleAccount_Impl(instance);
        }
        Log.d(TAG, "Initialize client account");
        return new gConnectAuth(instance);
    }
}
