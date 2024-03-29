/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.g3appdriver.GCircle.room;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.rmj.g3appdriver.lib.Telemarketing.dao.DAOClient2Call;
import org.rmj.g3appdriver.lib.Telemarketing.dao.DAOClientMobile;
import org.rmj.g3appdriver.lib.Telemarketing.dao.DAOHoutlineOutgoing;
import org.rmj.g3appdriver.lib.Telemarketing.dao.DAOLeadCalls;
import org.rmj.g3appdriver.lib.Telemarketing.dao.DAOMCInquiry;
import org.rmj.g3appdriver.lib.Telemarketing.dao.DAOPriorities;
import org.rmj.g3appdriver.lib.Telemarketing.entities.EClient2Call;
import org.rmj.g3appdriver.lib.Telemarketing.entities.EClientMobile;
import org.rmj.g3appdriver.lib.Telemarketing.entities.EHotline_Outgoing;
import org.rmj.g3appdriver.lib.Telemarketing.entities.ELeadCalls;
import org.rmj.g3appdriver.lib.Telemarketing.entities.EMCInquiry;
import org.rmj.g3appdriver.lib.Telemarketing.entities.EPriorities;
import org.rmj.g3appdriver.lib.addressbook.data.dao.DAddressRequest;
import org.rmj.g3appdriver.lib.addressbook.data.dao.DAddressUpdate;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DAppConfig;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DApprovalCode;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DAreaPerformance;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DBankInfo;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DBarangayInfo;
import org.rmj.g3appdriver.lib.Branch.dao.DBranchInfo;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DBranchLoanApplication;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DBranchOpeningMonitor;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DBranchPerformance;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DCIEvaluation;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DCashCount;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DClientInfo;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DClientUpdate;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DCountryInfo;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DCreditApplicantInfo;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DCreditApplicationDocuments;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DCreditOnlineApplicationCI;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DDCPCollectionDetail;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DDCPCollectionMaster;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DDCP_Remittance;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DEmployeeBusinessTrip;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DEmployeeLeave;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DEmployeeRole;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DFileCode;
import org.rmj.g3appdriver.lib.Inquiry.data.dao.DGanadoOnline;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DImageInfo;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DInventoryDao;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DInventoryDetail;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DInventoryMaster;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DItinerary;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DLRDcp;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DLocatorSysLog;
import org.rmj.g3appdriver.lib.Panalo.data.entity.EGuanzonPanalo;
import org.rmj.g3appdriver.lib.ProductInquiry.data.dao.DMcBrand;
import org.rmj.g3appdriver.lib.ProductInquiry.data.dao.DMcCategory;
import org.rmj.g3appdriver.lib.ProductInquiry.data.dao.DMcModel;
import org.rmj.g3appdriver.lib.ProductInquiry.data.dao.DMcModelPrice;
import org.rmj.g3appdriver.lib.ProductInquiry.data.dao.DMcTermCategory;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DMessages;
import org.rmj.g3appdriver.lib.addressbook.data.dao.DMobileRequest;
import org.rmj.g3appdriver.lib.addressbook.data.dao.DMobileUpdate;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DNNDMRequest;
import org.rmj.g3appdriver.lib.Notifications.data.dao.DNotification;
import org.rmj.g3appdriver.lib.Notifications.data.dao.DNotificationDetail;
import org.rmj.g3appdriver.lib.Notifications.data.dao.DNotificationMaster;
import org.rmj.g3appdriver.lib.Notifications.data.dao.DNotificationReceiver;
import org.rmj.g3appdriver.lib.Notifications.data.dao.DNotificationUser;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DOccupationInfo;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DPacita;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DPayslip;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DProvinceInfo;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DRaffleInfo;
import org.rmj.g3appdriver.lib.Panalo.data.dao.DPanalo;
import org.rmj.g3appdriver.lib.Panalo.data.dao.DRaffleStatus;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DRawDao;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DRelation;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DRemittanceAccounts;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DSelfieLog;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DSysConfig;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DToken;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DevTool;
import org.rmj.g3appdriver.lib.addressbook.data.entity.EAddressUpdate;
import org.rmj.g3appdriver.GCircle.room.Entities.EAppConfig;
import org.rmj.g3appdriver.GCircle.room.Entities.EAreaPerformance;
import org.rmj.g3appdriver.GCircle.room.Entities.EBankInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EBarangayInfo;
import org.rmj.g3appdriver.lib.Branch.entity.EBranchInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EBranchLoanApplication;
import org.rmj.g3appdriver.GCircle.room.Entities.EBranchOpenMonitor;
import org.rmj.g3appdriver.GCircle.room.Entities.EBranchPerformance;
import org.rmj.g3appdriver.GCircle.room.Entities.ECIEvaluation;
import org.rmj.g3appdriver.GCircle.room.Entities.ECashCount;
import org.rmj.g3appdriver.GCircle.room.Entities.EClientInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EClientUpdate;
import org.rmj.g3appdriver.GCircle.room.Entities.ECodeApproval;
import org.rmj.g3appdriver.GCircle.room.Entities.ECountryInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.ECreditApplication;
import org.rmj.g3appdriver.GCircle.room.Entities.ECreditApplicationDocuments;
import org.rmj.g3appdriver.GCircle.room.Entities.ECreditOnlineApplicationCI;
import org.rmj.g3appdriver.GCircle.room.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GCircle.room.Entities.EDCPCollectionMaster;
import org.rmj.g3appdriver.GCircle.room.Entities.EDCP_Remittance;
import org.rmj.g3appdriver.GCircle.room.Entities.EEmployeeBusinessTrip;
import org.rmj.g3appdriver.GCircle.room.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EEmployeeLeave;
import org.rmj.g3appdriver.GCircle.room.Entities.EEmployeeRole;
import org.rmj.g3appdriver.GCircle.room.Entities.EFileCode;
import org.rmj.g3appdriver.GCircle.room.Entities.EGLocatorSysLog;
import org.rmj.g3appdriver.lib.Inquiry.data.entity.EGanadoOnline;
import org.rmj.g3appdriver.GCircle.room.Entities.EImageInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EInventoryDetail;
import org.rmj.g3appdriver.GCircle.room.Entities.EInventoryMaster;
import org.rmj.g3appdriver.GCircle.room.Entities.EItinerary;
import org.rmj.g3appdriver.lib.ProductInquiry.data.entity.ELoanTerm;
import org.rmj.g3appdriver.lib.ProductInquiry.data.entity.EMCColor;
import org.rmj.g3appdriver.lib.ProductInquiry.data.entity.EMCModelCashPrice;
import org.rmj.g3appdriver.lib.ProductInquiry.data.entity.EMcBrand;
import org.rmj.g3appdriver.lib.ProductInquiry.data.entity.EMcCategory;
import org.rmj.g3appdriver.lib.ProductInquiry.data.entity.EMcModel;
import org.rmj.g3appdriver.lib.ProductInquiry.data.entity.EMcModelPrice;
import org.rmj.g3appdriver.lib.ProductInquiry.data.entity.EMcTermCategory;
import org.rmj.g3appdriver.lib.addressbook.data.entity.EMobileUpdate;
import org.rmj.g3appdriver.GCircle.room.Entities.ENNDMRequest;
import org.rmj.g3appdriver.lib.Notifications.data.entity.ENotificationMaster;
import org.rmj.g3appdriver.lib.Notifications.data.entity.ENotificationRecipient;
import org.rmj.g3appdriver.lib.Notifications.data.entity.ENotificationUser;
import org.rmj.g3appdriver.GCircle.room.Entities.EOccupationInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EPacitaEvaluation;
import org.rmj.g3appdriver.GCircle.room.Entities.EPacitaRule;
import org.rmj.g3appdriver.lib.Panalo.data.entity.EPanaloReward;
import org.rmj.g3appdriver.GCircle.room.Entities.EProvinceInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.ERaffleBasis;
import org.rmj.g3appdriver.GCircle.room.Entities.ERaffleInfo;
import org.rmj.g3appdriver.lib.Panalo.data.entity.ERaffleStatus;
import org.rmj.g3appdriver.GCircle.room.Entities.ERelation;
import org.rmj.g3appdriver.GCircle.room.Entities.ERemittanceAccounts;
import org.rmj.g3appdriver.GCircle.room.Entities.ESCA_Request;
import org.rmj.g3appdriver.GCircle.room.Entities.ESelfieLog;
import org.rmj.g3appdriver.GCircle.room.Entities.ESysConfig;
import org.rmj.g3appdriver.GCircle.room.Entities.ETokenInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.ETownInfo;

@Database(entities = {
        EBranchInfo.class,
        EMcBrand.class,
        EMcModel.class,
        EMcCategory.class,
        EProvinceInfo.class,
        EMcModelPrice.class,
        EMCColor.class,
        ETownInfo.class,
        EBarangayInfo.class,
        EMcTermCategory.class,
        ECountryInfo.class,
        EOccupationInfo.class,
        ECreditApplication.class,
        ECreditApplicantInfo.class,
        EAreaPerformance.class,
        EBranchPerformance.class,
        ERaffleBasis.class,
        ERaffleInfo.class,
        ENotificationMaster.class,
        ENotificationRecipient.class,
        ENotificationUser.class,
        ECodeApproval.class,
        ESCA_Request.class,
        EDCPCollectionMaster.class,
        EDCPCollectionDetail.class,
        EAddressUpdate.class,
        EMobileUpdate.class,
        EImageInfo.class,
        EEmployeeInfo.class,
        EFileCode.class,
        EClientInfo.class,
        ETokenInfo.class,
        ECreditApplicationDocuments.class,
        EClientUpdate.class,
        EBankInfo.class,
        ESelfieLog.class,
        EBranchLoanApplication.class,
        ECIEvaluation.class,
        EDCP_Remittance.class,
        ERemittanceAccounts.class,
        ESysConfig.class,
        EGLocatorSysLog.class,
        ERelation.class,
        ENNDMRequest.class,
        EAppConfig.class,
        ECashCount.class,
        EBranchOpenMonitor.class,
        EEmployeeLeave.class,
        EEmployeeBusinessTrip.class,
        EEmployeeRole.class,
        EInventoryMaster.class,
        EInventoryDetail.class,
        ECreditOnlineApplicationCI.class,
        EItinerary.class,
        ERaffleStatus.class,
        EPacitaRule.class,
        EPacitaEvaluation.class,
        ELoanTerm.class,
        EGanadoOnline.class,
        EMCModelCashPrice.class,
        EPanaloReward.class,
        EGuanzonPanalo.class,
        ELeadCalls.class,
        EClient2Call.class,
        EMCInquiry.class,
        EClientMobile.class,
        EHotline_Outgoing.class,
        EPriorities.class}, version = 50, exportSchema = true)
public abstract class GGC_GCircleDB extends RoomDatabase {
    private static final String TAG = "GhostRider_DB_Manager";
    private static GGC_GCircleDB instance;

    public abstract DEmployeeInfo EmployeeDao();
    public abstract DFileCode FileCodeDao();
    public abstract DClientInfo ClientDao();
    public abstract DBarangayInfo BarangayDao();
    public abstract DTownInfo TownDao();
    public abstract DProvinceInfo ProvinceDao();
    public abstract DCountryInfo CountryDao();
    public abstract DMcCategory McCategoryDao();
    public abstract DMcTermCategory McTermCategoryDao();
    public abstract DMcBrand McBrandDao();
    public abstract DMcModel McModelDao();
    public abstract DMcModelPrice McModelPriceDao();
    public abstract DBranchInfo BranchDao();
    public abstract DOccupationInfo OccupationDao();
    public abstract DCreditApplication CreditApplicationDao();
    public abstract DCreditApplicantInfo CreditApplicantDao();
    public abstract DAreaPerformance AreaPerformanceDao();
    public abstract DBranchPerformance BranchPerformanceDao();
    public abstract DRawDao RawDao();
    public abstract DRaffleInfo RaffleDao();
    public abstract DNotificationReceiver ntfReceiverDao();
    public abstract DApprovalCode ApprovalDao();
    public abstract DLRDcp DcpDao();
    public abstract DDCPCollectionMaster DcpMasterDao();
    public abstract DDCPCollectionDetail DcpDetailDao();
    public abstract DAddressRequest AddressRequestDao();
    public abstract DAddressUpdate addressUpdateDao();
    public abstract DMobileUpdate mobileUpdateDao();
    public abstract DMobileRequest MobileRequestDao();
    public abstract DImageInfo ImageInfoDao();
    public abstract DCreditApplicationDocuments DocumentInfoDao();
    public abstract DClientUpdate ClientUpdateDao();
    public abstract DBankInfo BankInfoDao();
    public abstract DSelfieLog SelfieDao();
    public abstract DInventoryDao InventoryDao();
    public abstract DBranchLoanApplication CreditAppDocsDao();
    public abstract DCIEvaluation CIDao();
    public abstract DRelation RelDao();
    public abstract DDCP_Remittance DCPRemitanceDao();
    public abstract DRemittanceAccounts RemitanceAccDao();
    public abstract DSysConfig sysConfigDao();
    public abstract DLocatorSysLog locatorSysLogDao();
    public abstract DNNDMRequest nndmRequestDao();
    public abstract DAppConfig appConfigDao();
    public abstract DCashCount CashCountDao();
    public abstract DBranchOpeningMonitor openingMonitoryDao();
    public abstract DEmployeeLeave employeeLeaveDao();
    public abstract DEmployeeBusinessTrip employeeOBDao();
    public abstract DEmployeeRole employeeRoleDao();
    public abstract DInventoryMaster inventoryMasterDao();
    public abstract DInventoryDetail inventoryDetailDao();
    public abstract DCreditOnlineApplicationCI creditEvaluationDao();
    public abstract DItinerary itineraryDao();
    public abstract DToken dToken();
    public abstract DevTool devTool();
    public abstract DNotificationMaster nMasterDao();
    public abstract DNotificationDetail nDetailDao();
    public abstract DNotificationUser nUserDao();
    public abstract DPanalo panaloDao();
    public abstract DPayslip payslipDao();
    public abstract DRaffleStatus raffleStatusDao();
    public abstract DMessages messagesDao();
    public abstract DNotification notificationDao();
    public abstract DPacita pacitaDao();
    public abstract DGanadoOnline ganadoDao();
    public abstract DAOLeadCalls teleLeadsDao();
    public abstract DAOClient2Call teleCallClientsDao();
    public abstract DAOMCInquiry teleMCInquiryDao();
    public abstract DAOClientMobile teleClientMobDao();
    public abstract DAOHoutlineOutgoing teleHOutgoingDao();
    public abstract DAOPriorities telePriorities();

    public static synchronized GGC_GCircleDB getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            GGC_GCircleDB.class, "GGC_ISysDBF.db")
                    .allowMainThreadQueries()
                    .addCallback(roomCallBack)
                    .addMigrations(MIGRATION_V50)
                    .build();
        }
        return instance;
    }

    private static final Callback roomCallBack = new Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.e(TAG, "Local database has been created.");
        }
    };

    public static final Migration MIGRATION_V50 = new Migration(49, 50) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `MC_Cash_Price` " +
                    "(`sModelIDx` TEXT NOT NULL, `sMCCatNme` TEXT NOT NULL, " +
                    "`sModelNme` TEXT NOT NULL, `sBrandNme` TEXT, `nSelPrice` REAL, " +
                    "`nLastPrce` REAL, `nDealrPrc` REAL, `dPricexxx` TEXT, " +
                    "`sBrandIDx` TEXT, `sMCCatIDx` TEXT, " +
                    "PRIMARY KEY(`sModelIDx`, `sMCCatNme`, `sModelNme`))");

            database.execSQL("ALTER TABLE  Ganado_Online ADD COLUMN nCashPrce REAL");
            database.execSQL("ALTER TABLE Ganado_Online ADD COLUMN dPricexxx TEXT");
        }
    };
}
