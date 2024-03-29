package org.rmj.g3appdriver.Ganado;

import static org.junit.Assert.assertTrue;

import android.app.Application;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.Config.AppConfig;
import org.rmj.g3appdriver.lib.ProductInquiry.data.entity.EMcModel;
import org.rmj.g3appdriver.lib.authentication.GAuthentication;
import org.rmj.g3appdriver.lib.authentication.factory.Auth;
import org.rmj.g3appdriver.lib.authentication.factory.iAuthenticate;
import org.rmj.g3appdriver.lib.authentication.pojo.LoginCredentials;
import org.rmj.g3appdriver.lib.Inquiry.obj.ProductInquiry;
import org.rmj.g3appdriver.lib.Inquiry.pojo.InstallmentInfo;

import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestProductInquiry {
    private static final String TAG = TestProductInquiry.class.getSimpleName();

    private Application instance;

    private iAuthenticate poAuth;
    private ProductInquiry poSys;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception {
        instance = ApplicationProvider.getApplicationContext();
        AppConfig.getInstance(instance).setProductID("gRider");
        this.poSys = new ProductInquiry(instance);
        poAuth = new GAuthentication(instance).initAppAuthentication().getInstance(Auth.AUTHENTICATE);
        poAuth.DoAction(new LoginCredentials("mikegarcia8748@gmail.com", "123456"));
    }

    @Test
    public void test01GetBrandList() {
        final boolean[] isSuccess = {false};
        poSys.GetMotorcycleBrands().observeForever(eMcBrands -> {
            try{
                if(eMcBrands == null){
                    return;
                }

                if(eMcBrands.size() == 0){
                    return;
                }

                for(int x = 0; x < eMcBrands.size(); x++){
                    Log.d(TAG, eMcBrands.get(x).getBrandNme());
                    Log.d(TAG, eMcBrands.get(x).getBrandIDx());
                }

                isSuccess[0] = true;
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        assertTrue(isSuccess[0]);
    }

    @Test
    public void test02GetModelList() {
        final boolean[] isSuccess = {false};
        poSys.GetModelsList("M0W1001").observeForever(new Observer<List<EMcModel>>() {
            @Override
            public void onChanged(List<EMcModel> eMcModels) {
                try{
                    if(eMcModels == null){
                        return;
                    }

                    if(eMcModels.size() == 0){
                        return;
                    }

                    for(int x = 0; x < eMcModels.size(); x++){
                        Log.d(TAG, eMcModels.get(x).getModelNme());
                        Log.d(TAG, eMcModels.get(x).getModelIDx());
                    }

                    isSuccess[0] = true;
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        assertTrue(isSuccess[0]);
    }

    @Test
    public void test03RetrieveMinimumDownpayment() {
        boolean isSuccess = false;
        InstallmentInfo loInfo = poSys.GetMinimumDownpayment("M00123048");
        if(loInfo == null){
            Log.e(TAG, poSys.getMessage());
        } else {
            Log.d(TAG, "Minimum Downpayment: Php. "+loInfo.getMinimumDownpayment());
            Log.d(TAG, "Amortization: Php. " + loInfo.getMonthlyAmortization());
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }

    @Test
    public void test04RecalculateNewDownPayment() {
        boolean isSuccess = false;
        double lnResult = poSys.GetMonthlyAmortization("M00123048", 24);
        if(lnResult == 0.0){
            Log.e(TAG, poSys.getMessage());
        } else {
            Log.d(TAG, "Amortization: Php. " + lnResult);
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }
}
