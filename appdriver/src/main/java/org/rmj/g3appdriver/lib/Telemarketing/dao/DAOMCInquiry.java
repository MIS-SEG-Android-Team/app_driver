package org.rmj.g3appdriver.lib.Telemarketing.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.lib.Telemarketing.entities.EMCInquiry;

import java.util.Date;
import java.util.List;

@Dao
public interface DAOMCInquiry {
    @Query("SELECT * FROM MC_Inquiry WHERE sTransNox= :sTransNox")
    EMCInquiry GetMCInquiry(String sTransNox);
    @Query("UPDATE MC_Inquiry SET dFollowUp= :dFollowUp WHERE sTransNox= :sTransNox")
    int UpdateFollowUp(String dFollowUp, String sTransNox);
    @Insert
    Long SaveMCInq(EMCInquiry emcInquiry);
    @Update
    int UpdateMCInq(EMCInquiry emcInquiry);
}
