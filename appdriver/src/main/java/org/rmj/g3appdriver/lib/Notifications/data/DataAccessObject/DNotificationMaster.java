package org.rmj.g3appdriver.lib.Notifications.data.DataAccessObject;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.lib.Notifications.data.Entity.ENotificationMaster;

@Dao
public interface DNotificationMaster {

    @Insert
    void Insert(ENotificationMaster foVal);

    @Update
    void Update(ENotificationMaster foVal);

    @Query("SELECT * FROM Notification_Info_Master WHERE sMesgIDxx =:fsVal")
    ENotificationMaster GetNotificationMaster(String fsVal);

    @Query("SELECT COUNT(*) FROM Notification_Info_Master")
    int GetRowsCountForID();
}