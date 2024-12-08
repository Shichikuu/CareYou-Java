package data;

import java.util.Date;

public class WithdrawalData extends TransactionData{
    private String withdrawMethod;

    public WithdrawalData(int userID, Date transactionDate, int amount, int programID, String withdrawMethod) {
        super(userID, transactionDate, amount, programID);
        this.withdrawMethod = withdrawMethod;
    }

    public String getWithdrawMethod() {
        return withdrawMethod;
    }

    public void setWithdrawMethod(String withdrawMethod) {
        this.withdrawMethod = withdrawMethod;
    }
}
