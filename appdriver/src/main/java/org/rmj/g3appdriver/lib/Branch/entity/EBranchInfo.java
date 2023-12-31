package org.rmj.g3appdriver.lib.Branch.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Branch_Info")
public class EBranchInfo {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "sBranchCd")
    private String BranchCd;

    @ColumnInfo(name = "sBranchNm")
    private String BranchNm;

    @ColumnInfo(name = "sAreaCode")
    private String AreaCode;

    @ColumnInfo(name = "cDivision")
    private String Division;

    @ColumnInfo(name = "cPromoDiv")
    private String PromoDiv;

    @ColumnInfo(name = "sDescript")
    private String Descript;

    @ColumnInfo(name = "sAddressx")
    private String Addressx;

    @ColumnInfo(name = "sTownIDxx")
    private String TownIDxx;

    @ColumnInfo(name = "sTownName")
    private String TownName;

    @ColumnInfo(name = "sProvName")
    private String ProvName;

    @ColumnInfo(name = "nLatitude")
    private double Latitude = 0.0;

    @ColumnInfo(name = "nLongtude")
    private double Longtude = 0.0;

    @ColumnInfo(name = "sContactx")
    private String Contactx;

    @ColumnInfo(name = "sTelNumbr")
    private String TelNumbr;

    @ColumnInfo(name = "sEmailAdd")
    private String EmailAdd;

    @ColumnInfo(name = "cRecdStat")
    private String RecdStat;

    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp;
    public EBranchInfo() {
    }

    @NonNull
    public String getBranchCd() {
        return BranchCd;
    }

    public void setBranchCd(@NonNull String branchCd) {
        BranchCd = branchCd;
    }

    public String getBranchNm() {
        return BranchNm;
    }

    public void setBranchNm(String branchNm) {
        BranchNm = branchNm;
    }

    public String getDescript() {
        return Descript;
    }

    public void setDescript(String descript) {
        Descript = descript;
    }

    public String getAddressx() {
        return Addressx;
    }

    public void setAddressx(String addressx) {
        Addressx = addressx;
    }

    public String getContactx() {
        return Contactx;
    }

    public void setContactx(String contactx) {
        Contactx = contactx;
    }

    public String getTelNumbr() {
        return TelNumbr;
    }

    public void setTelNumbr(String telNumbr) {
        TelNumbr = telNumbr;
    }

    public String getEmailAdd() {
        return EmailAdd;
    }

    public void setEmailAdd(String emailAdd) {
        EmailAdd = emailAdd;
    }

    public String getRecdStat() {
        return RecdStat;
    }

    public void setRecdStat(String recdStat) {
        RecdStat = recdStat;
    }

    public String getTimeStmp() {
        return TimeStmp;
    }

    public void setTimeStmp(String timeStmp) {
        TimeStmp = timeStmp;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongtude() {
        return Longtude;
    }

    public void setLongtude(double longtude) {
        Longtude = longtude;
    }

    public String getTownIDxx() {
        return TownIDxx;
    }

    public void setTownIDxx(String townIDxx) {
        TownIDxx = townIDxx;
    }

    public String getTownName() {
        return TownName;
    }

    public void setTownName(String townName) {
        TownName = townName;
    }

    public String getProvName() {
        return ProvName;
    }

    public void setProvName(String provName) {
        ProvName = provName;
    }

    public String getAreaCode() {
        return AreaCode;
    }

    public void setAreaCode(String areaCode) {
        AreaCode = areaCode;
    }

    public String getDivision() {
        return Division;
    }

    public void setDivision(String division) {
        Division = division;
    }

    public String getPromoDiv() {
        return PromoDiv;
    }

    public void setPromoDiv(String promoDiv) {
        PromoDiv = promoDiv;
    }
}
