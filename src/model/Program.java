package model;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Program {

    private int programID;
    private int fundraiserID;
    private String programTitle;
    private String programDesc;
    private String programStatus;
    private String fundraiserName;
    private String beneficiaryName;
    private int programTarget;
    private int programRaised;
    private Date startDate;
    private int withdrawn;

    public Program(int programID, int fundraiserID, String programTitle, String programDesc, String fundraiserName, String beneficiaryName, int programTarget) {
        this.programID = programID;
        this.fundraiserID = fundraiserID;
        this.programTitle = programTitle;
        this.programDesc = programDesc;
        this.programStatus = "Not Finished";
        this.fundraiserName = fundraiserName;
        this.beneficiaryName = beneficiaryName;
        this.programTarget = programTarget;
        this.programRaised = 0;
        this.startDate = new Date();
        this.withdrawn = 0;
    }

    public int getProgramID() {
        return programID;
    }

    public void setProgramID(int programID) {
        this.programID = programID;
    }

    public int getFundraiserID() {
        return fundraiserID;
    }

    public void setFundraiserID(int fundraiserID) {
        this.fundraiserID = fundraiserID;
    }

    public String getProgramTitle() {
        return programTitle;
    }

    public void setProgramTitle(String programTitle) {
        this.programTitle = programTitle;
    }

    public String getProgramDesc() {
        return programDesc;
    }

    public void setProgramDesc(String programDesc) {
        this.programDesc = programDesc;
    }

    public String getProgramStatus() {
        return programStatus;
    }

    public void setProgramStatus(String programStatus) {
        this.programStatus = programStatus;
    }

    public String getFundraiserName() {
        return fundraiserName;
    }

    public void setFundraiserName(String fundraiserName) {
        this.fundraiserName = fundraiserName;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getProgramTarget() {
        return formatAmount(programTarget);
    }

    public void setProgramTarget(int programTarget) {
        this.programTarget = programTarget;
    }

    private String formatAmount(double amount){
        Locale localeID = new Locale("in", "ID");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(localeID);
        currencyFormat.setMaximumFractionDigits(0);
        String formattedPrice = currencyFormat.format(amount);

        return formattedPrice.replace("Rp", "Rp ");
    }

    public String getProgramRaised() {
        return formatAmount(programRaised);
    }

    public void setProgramRaised(int programRaised) {
        this.programRaised = programRaised;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getWithdrawn() {
        return withdrawn;
    }

    public void setWithdrawn(int withdrawn) {
        this.withdrawn = withdrawn;
    }

    public String getPercentage() {
        return String.format("%.2f", (double) programRaised / programTarget * 100);
    }
}
