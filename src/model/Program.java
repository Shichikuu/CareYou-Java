package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Program {
    private int programID;
    private int fundraiserID;
    private String programTitle;
    private String programDesc;
    private String programStatus;
    private String programTopic;
    private String fundraiserName;
    private String beneficiaryName;
    private String programType;
    private int programTarget;
    private int programRaised;
    private Date startDate;
    private Date endDate;
    private int withdrawn;

    private List<Comment> comments;
    private List<Transaction> transactions;

    public Program(int programID, int fundraiserID, String programTitle, String programDesc, String programTopic, String fundraiserName, String beneficiaryName, String programType, int programTarget, Date endDate) {
        this.programID = programID;
        this.fundraiserID = fundraiserID;
        this.programTitle = programTitle;
        this.programDesc = programDesc;
        this.programStatus = "Not Finished";
        this.programTopic = programTopic;
        this.fundraiserName = fundraiserName;
        this.beneficiaryName = beneficiaryName;
        this.programType = programType;
        this.programTarget = programTarget;
        this.programRaised = 0;
        this.startDate = new Date();
        this.endDate = endDate;
        this.withdrawn = 0;
        this.comments = new ArrayList<Comment>();
        this.transactions = new ArrayList<Transaction>();
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

    public String getProgramTopic() {
        return programTopic;
    }

    public void setProgramTopic(String programTopic) {
        this.programTopic = programTopic;
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

    public String getProgramType() {
        return programType;
    }

    public void setProgramType(String programType) {
        this.programType = programType;
    }

    public int getProgramTarget() {
        return programTarget;
    }

    public void setProgramTarget(int programTarget) {
        this.programTarget = programTarget;
    }

    public int getProgramRaised() {
        return programRaised;
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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getWithdrawn() {
        return withdrawn;
    }

    public void setWithdrawn(int withdrawn) {
        this.withdrawn = withdrawn;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
