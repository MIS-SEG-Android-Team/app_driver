package org.rmj.g3appdriver.Telemarketing;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.rmj.g3appdriver.Config.AppConfig;
import org.rmj.g3appdriver.Config.AppStatusConfig;
import org.rmj.g3appdriver.GCircle.Account.EmployeeMaster;
import org.rmj.g3appdriver.GCircle.Apps.TeleMarketing.CallInteractManager;
import org.rmj.g3appdriver.GCircle.Apps.TeleMarketing.LeadsInformation;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.lib.Telemarketing.dao.DAOLeadCalls;
import org.rmj.g3appdriver.lib.Telemarketing.dao.DAOPriorities;
import org.rmj.g3appdriver.lib.Telemarketing.entities.EPriorities;

@RunWith(AndroidJUnit4.class)
public class TestAppImports {
    private Application instance;
    private String TAG = getClass().getSimpleName();
    private EmployeeMaster.UserAuthInfo loAuth;
    private EmployeeMaster poUser;
    private CallInteractManager poCallManager;
    private AppConfig loConfig;
    public DAOLeadCalls poDao;
    public DAOPriorities poDaoPriorities;

    public LeadsInformation GetQueues(){
        LeadsInformation loLeads = new LeadsInformation();
        loLeads.setsClientID("M09123002535");
        loLeads.setsMobileNo("09153876313");
        loLeads.setsTransNox("M0T123090941");
        loLeads.setsReferNox("M09123000121");

        loLeads.setsSourceCD("INQR");
        loLeads.setsSubscr("0");

        return loLeads;
    }
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();
    @Before
    public void setUp() throws Exception{
        instance= ApplicationProvider.getApplicationContext();
        loConfig = AppConfig.getInstance(instance);
        loConfig.setProductID("gRider");

        AppStatusConfig.getInstance(instance).setTestStatus(true); //use this to test api on device local ip

        poUser = new EmployeeMaster(instance);

        poCallManager = new CallInteractManager(instance);
        poCallManager.InitTransaction(GetQueues());
        poCallManager.InitQueue("2023-12-08 11:40:33", "2023-12-08 11:50:33");

        poDao = GGC_GCircleDB.getInstance(ApplicationProvider.getApplicationContext()).teleLeadsDao();
        poDaoPriorities = GGC_GCircleDB.getInstance(ApplicationProvider.getApplicationContext()).telePriorities();
    }
    /*IF ERROR OCCURS, TRY TO RUN ONLY TEST YOU NEED AND COMMENT OTHER TESTS.*/
    @Test
    public void ImportCalls(){
        EPriorities ePriorities = new EPriorities();
        ePriorities.setIndex(2);
        ePriorities.setsSourceCD("GNDO");

        ePriorities.setIndex(1);
        ePriorities.setsSourceCD("MCCA");

        ePriorities.setIndex(3);
        ePriorities.setsSourceCD("INQR");

        poDaoPriorities.UpdatePriorities(ePriorities);

        Boolean hasSim = poCallManager.GetSimCards();

        System.out.println(poCallManager.sim1);
        System.out.println(poCallManager.sim2);
        System.out.println(poCallManager.simCondition);
        System.out.println(poCallManager.getMessage());
        assertTrue(hasSim);

        Boolean isImported = poCallManager.ImportCalls();

        System.out.println(poCallManager.getMessage());
        assertTrue(isImported);

        poDao.GetInitLead("GAP023000374", poCallManager.sim1, poCallManager.sim2).observeForever(new Observer<DAOLeadCalls.LeadInformation>() {
            @Override
            public void onChanged(DAOLeadCalls.LeadInformation leadInformation) {
                assertNotNull(leadInformation);
                System.out.println(leadInformation.sTransNox);
                System.out.println(leadInformation.sReferNox);
                System.out.println(leadInformation.sSourceCD);
                System.out.println(leadInformation.sClientID);
                System.out.println(leadInformation.sMobileNo);
            }
        });
    }
    @Test
    public void ImportPriorities(){
        Boolean isImported = poCallManager.ImportPriorities();
        System.out.println(poCallManager.getMessage());
        assertTrue(isImported);
    }
    @Test
    public void ImportClient() throws JSONException {
        Boolean isImported = poCallManager.SaveClient2Call();
        System.out.println(poCallManager.getMessage());
        assertTrue(isImported);
    }
    @Test
    public void ImportClientMobile() throws JSONException {
        Boolean isImported = poCallManager.SaveClientMobile();
        System.out.println(poCallManager.getMessage());
        assertTrue(isImported);
    }
    @Test
    public void ImportInquiries() throws JSONException {
        Boolean isImported = poCallManager.SaveInquiries();
        System.out.println(poCallManager.getMessage());
        assertTrue(isImported);
    }
    @Test
    public void SaveCallStatus(){
        Boolean isSaved = poCallManager.SaveCallStatus("POSSIBLE SALES", "2", "C00112062302");
        System.out.println(poCallManager.getMessage());
        assertTrue(isSaved);
    }
    @Test
    public void SaveSchedule(){
        Boolean isSaved = poCallManager.SaveSchedule("2023-12-06", "0", "RESCHEDULE CLIENT IS BUSY AGAIN");
        System.out.println(poCallManager.getMessage());
        assertTrue(isSaved);
    }
    @Test
    public void RemoveSession(){
        poCallManager.RemoveCallSession();
    }
}
