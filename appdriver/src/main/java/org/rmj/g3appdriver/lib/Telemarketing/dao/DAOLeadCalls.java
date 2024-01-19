package org.rmj.g3appdriver.lib.Telemarketing.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.lib.Telemarketing.entities.ELeadCalls;

import java.util.List;

@Dao
public interface DAOLeadCalls {
    @Query("SELECT * FROM Lead_Calls WHERE sTransNox = :sTransNoxx")
    ELeadCalls GetLeadTrans(String sTransNoxx);
    @Query("SELECT a.sTransNox sTransNox, a.sReferNox sReferNox, a.sSourceCD sSourceCD, a.sClientID sClientID, " +
            "a.sMobileNo sMobileNo, a.cSubscrbr cSubscrbr " +
            "FROM Lead_Calls a, Call_Priorities b " +
            "WHERE a.sSourceCD = b.sSourceCD " +
            "AND (a.cTranStat = '0' " +
            "OR (a.cTranStat = '1' " +
            "AND a.sAgentIDx = :sAgentID)) " +
            "AND a.cSubscrbr IN (:sSim1, :sSim2) " +
            "ORDER BY a.dTransact DESC, a.cSubscrbr DESC, a.cTranStat DESC, b.srcIndex ASC LIMIT 1")
    LiveData<LeadInformation> GetInitLead(String sAgentID, String sSim1, String sSim2);
    @Query("SELECT lead.sReferNox sReferNox, ccl.sClientNM sClientNm, ccl.xAddressx sAddressx, lead.sMobileNo sMobileNo, " +
            "lead.sReferNox sReferNox, mci.sModelIDx sModelIDx, lead.dTransact dTransact " +
            "FROM Lead_Calls lead " +
            "LEFT JOIN  Call_Client ccl ON (lead.sClientID = ccl.sClientID) " +
            "LEFT JOIN MC_Inquiry mci ON (lead.sReferNox = mci.sTransNox) " +
            "WHERE lead.sReferNox = :sTransNox")
    LiveData<LeadDetails> GetLeadDetails(String sTransNox);
    @Query("SELECT lead.sReferNox sReferNox, ccl.sClientNM sClientNm, lead.sMobileNo sMobileNo, lead.dTransact dTransact, " +
            "lead.cTLMStatx cTLMStatx, lead.sRemarksx sRemarksx " +
            "FROM Lead_Calls lead " +
            "LEFT JOIN  Call_Client ccl ON (lead.sClientID = ccl.sClientID) " +
            "ORDER BY lead.dTransact DESC, lead.sMobileNo ASC, ccl.sClientNM ASC")
    LiveData<List<LeadHistory>> GetCallHistory();
    @Query("UPDATE Lead_Calls SET cTLMStatx= :cTLMStatx, cTranStat = :cTranStat, " +
            "sApprovCd = :sApprovCd , dCallStrt = :sCallStrt, " +
            "dCallEndx = :sCallEnd, sModified = :sModified, dModified = :dModified " +
            "WHERE sTransNox= :sTransNoxx")
    int UpdateLeadCall(String sTransNoxx, String cTLMStatx, String cTranStat, String sApprovCd,
                       String sCallStrt, String sCallEnd, String sModified, String dModified);
    @Insert
    Long SaveLeads(ELeadCalls eLeadCalls);
    @Update
    int UpdateLeads(ELeadCalls eLeadCalls);

    class LeadInformation{
        public String sTransNox;
        public String sReferNox;
        public String sSourceCD;
        public String sClientID;
        public String sMobileNo;
        public String cSubscrbr;
    }
    class LeadDetails{
        public String sReferNox;
        public String sClientNm;
        public String sAddressx;
        public String sMobileNo;
        public String sModelIDx;
    }
    class LeadHistory{
        public String sReferNox;
        public String sClientNm;
        public String sMobileNo;
        public String dTransact;
        public String cTLMStatx;
        public String sRemarksx;
    }
}
