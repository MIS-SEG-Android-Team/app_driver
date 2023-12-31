package org.rmj.g3appdriver.lib.Notifications.factory.FactoryImpl;

import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;

import androidx.sqlite.db.SimpleSQLiteQuery;

import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;
import org.rmj.g3appdriver.GCircle.Api.GCircleApi;
import org.rmj.g3appdriver.lib.Notifications.data.dao.DNotificationReceiver;
import org.rmj.g3appdriver.GCircle.room.Entities.EBranchOpenMonitor;
import org.rmj.g3appdriver.lib.Notifications.data.entity.ENotificationMaster;
import org.rmj.g3appdriver.lib.Notifications.data.entity.ENotificationRecipient;
import org.rmj.g3appdriver.lib.Notifications.data.entity.ENotificationUser;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.dev.Http.HttpHeaderManager;
import org.rmj.g3appdriver.dev.Http.WebClient;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.lib.Notifications.NOTIFICATION_STATUS;
import org.rmj.g3appdriver.lib.Notifications.RemoteMessageParser;
import org.rmj.g3appdriver.lib.Notifications.factory.NMM_Factory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GCN_TableUpdateNMMFactoryImpl implements NMM_Factory {
    private static final String TAG = GCN_TableUpdateNMMFactoryImpl.class.getSimpleName();

    private final Application instance;

    private final DNotificationReceiver poDao;

    private String message;

    public GCN_TableUpdateNMMFactoryImpl(Application instance) {
        this.instance = instance;
        this.poDao = GGC_GCircleDB.getInstance(instance).ntfReceiverDao();
    }

    @Override
    public String Save(RemoteMessage foVal) {
        try{
            RemoteMessageParser loParser = new RemoteMessageParser(foVal);
            String lsMesgIDx = loParser.getValueOf("transno");
            if(poDao.CheckNotificationIfExist(lsMesgIDx) >= 1){
                String lsStatus = loParser.getValueOf("status");
                poDao.updateNotificationStatusFromOtherDevice(lsMesgIDx, lsStatus);
            } else {
                ENotificationMaster loMaster = new ENotificationMaster();
                loMaster.setTransNox(CreateUniqueID());
                loMaster.setMesgIDxx(loParser.getValueOf("transno"));
                loMaster.setParentxx(loParser.getValueOf("parent"));
                loMaster.setCreatedx(loParser.getValueOf("stamp"));
                loMaster.setAppSrcex(loParser.getValueOf("appsrce"));
                loMaster.setCreatrID(loParser.getValueOf("srceid"));
                loMaster.setCreatrNm(loParser.getValueOf("srcenm"));
                loMaster.setDataSndx(loParser.getValueOf("infox"));
                loMaster.setMsgTitle(loParser.getDataValueOf("title"));
                loMaster.setMessagex(loParser.getDataValueOf("message"));
                loMaster.setMsgTypex(loParser.getValueOf("msgmon"));

                ENotificationRecipient loRecpnt = new ENotificationRecipient();
                loRecpnt.setTransNox(loParser.getValueOf("transno"));
                loRecpnt.setAppRcptx(loParser.getValueOf("apprcpt"));
                loRecpnt.setRecpntID(loParser.getValueOf("rcptid"));
                loRecpnt.setRecpntNm(loParser.getValueOf("rcptnm"));
                loRecpnt.setMesgStat(loParser.getValueOf("status"));
                loRecpnt.setReceived(new AppConstants().DATE_MODIFIED);
                loRecpnt.setTimeStmp(new AppConstants().DATE_MODIFIED);

                poDao.insert(loMaster);
                poDao.insert(loRecpnt);

                if(!"SYSTEM".equalsIgnoreCase(loParser.getValueOf("srceid"))) {
                    ENotificationUser loUser = new ENotificationUser();
                    loUser.setUserIDxx(loParser.getValueOf("srceid"));
                    loUser.setUserName(loParser.getValueOf("srcenm"));

                    if (poDao.CheckIfUserExist(loParser.getValueOf("srceid")) == null) {
                        poDao.insert(loUser);
                    }
                }

                String lsData = loParser.getValueOf("infox");

                JSONObject loJson = new JSONObject(lsData);

                String lsModule = loJson.getString("module");

                switch (lsModule){
                    case "00001":
                        SaveTableUpdate(lsData);
                        break;
                    case "00002":
                        SaveBranchOpening(lsData);
                        break;
                    default:
                        break;
                }
            }
            return lsMesgIDx;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }

    @Override
    public ENotificationMaster SendResponse(String mesgID, NOTIFICATION_STATUS status) {
        try{
            String lsTranStat = "";

            switch (status){
                case OPEN:
                    lsTranStat = "0";
                    break;
                case DELIVERED:
                    lsTranStat = "1";
                    break;
                case RECEIVED:
                    lsTranStat = "2";
                    break;
                case READ:
                    lsTranStat = "3";
                    break;
                case DELETED:
                    lsTranStat = "4";
                    break;
            }

            JSONObject params = new JSONObject();
            params.put("transno", mesgID);
            params.put("status", lsTranStat);
            params.put("stamp", new AppConstants().DATE_MODIFIED);
            params.put("infox", "");

            String lsResponse = WebClient.sendRequest(
                    new GCircleApi(instance).getUrlSendResponse(),
                    params.toString(),
                    HttpHeaderManager.getInstance(instance).initializeHeader().getHeaders());
            if(lsResponse == null){
                message = "Server no response while sending response.";
                return null;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if (!lsResult.equalsIgnoreCase("success")) {
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                return null;
            }

            poDao.UpdateSentResponseStatus(mesgID, lsTranStat, new AppConstants().DATE_MODIFIED);
            return poDao.CheckIfMasterExist(mesgID);
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }

    @Override
    public boolean CreateNotification(String title, String message) {
        try{

            return true;
        } catch (Exception e){
            e.printStackTrace();
            this.message = getLocalMessage(e);
            return false;
        }
    }

    @Override
    public String getMessage() {
        return message;
    }

    private String CreateUniqueID(){
        String lsUniqIDx = "";
        try{
            String lsBranchCd = "MX01";
            String lsCrrYear = new SimpleDateFormat("yy", Locale.getDefault()).format(new Date());
            StringBuilder loBuilder = new StringBuilder(lsBranchCd);
            loBuilder.append(lsCrrYear);

            int lnLocalID = poDao.GetNotificationCountForID() + 1;
            String lsPadNumx = String.format("%05d", lnLocalID);
            loBuilder.append(lsPadNumx);
            lsUniqIDx = loBuilder.toString();
        } catch (Exception e){
            e.printStackTrace();
        }
        return lsUniqIDx;
    }

    private boolean SaveTableUpdate(String args){
        try{
            JSONObject loJson = new JSONObject(args);
            JSONObject loData = loJson.getJSONObject("data");
            String lsTblUpdte = loData.getString("");
            SimpleSQLiteQuery query = new SimpleSQLiteQuery(lsTblUpdte);
            poDao.ExecuteTableUpdateQuery(query);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    private boolean SaveBranchOpening(String args){
        try{
            JSONObject loJson = new JSONObject(args);
            JSONObject loData = loJson.getJSONObject("data");
            EBranchOpenMonitor loDetail = new EBranchOpenMonitor();
            loDetail.setBranchCD(loData.getString("sBranchCD"));
            loDetail.setTransact(loData.getString("dTransact"));
            loDetail.setTimeOpen(loData.getString("sTimeOpen"));
            loDetail.setOpenNowx(loData.getString("sOpenNowx"));
            poDao.SaveBranchOpening(loDetail);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }
}
