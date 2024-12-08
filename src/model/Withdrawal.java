package model;

import dao.ProgramRepository;
import dao.TransactionRepository;

import java.sql.SQLException;
import java.util.Date;

import static main.Main.sc;

public class Withdrawal extends Transaction {
    // private String bankAccount;
    // private String bankName;
    private String withdrawMethod;

    // Constructors

    public Withdrawal(int transactionID, int userID, Date transactionDate, int amount, Integer programID, String withdrawMethod) {
        super(transactionID, userID, transactionDate, amount, programID);
        // this.bankAccount = bankAccount;
        // this.bankName = bankName;
        this.withdrawMethod = withdrawMethod;
        this.transactionType = "Withdrawal";
    }

    public Withdrawal(Program program) {
        super(program);
        this.transactionType = "Withdrawal";
    }

    @Override
    protected int validateTransaction(Program program) {
        if(program.getWithdrawn() == program.getProgramRaised()){
            return -1;
        }
        String ch;
        System.out.println("Enter 0 to cancel the withdrawal anytime");
        System.out.println();
        do{
            System.out.print("🔹 Enter your withdrawal amount: ");
            this.amount = Integer.parseInt(sc.nextLine());
            if(this.amount < 0){
                System.out.println("Invalid amount. Please enter a valid amount.");
            }
            if(program.getWithdrawn()+this.amount > program.getProgramRaised()){
                System.out.println("Withdrawal amount exceeds the raised amount. Please enter a valid amount.");
            }
            if(this.amount == 0){
                return 0;
            }
        }while (this.amount < 0);
        do{
            System.out.println("Choose a withdrawal method:");
            System.out.println("1. Digital Wallet");
            System.out.println("2. Bank Transfer");
            System.out.print("🔹 Choose an option: ");
            ch = sc.nextLine();
            if(ch.equals("1")){
                this.withdrawMethod = "Digital Wallet";
            }else if(ch.equals("2")){
                this.withdrawMethod = "Bank Transfer";
            }
            if(ch.equals("0")){
                return 0;
            }
        }while (!ch.equals("1") && !ch.equals("2"));
        return 1;
    }

    @Override
    protected void executeTransaction(Program program) {
        int newWithdrawn = program.getWithdrawn() + amount;
        program.setWithdrawn(newWithdrawn);
        try{
            ProgramRepository programRepo = ProgramRepository.getInstance();
            programRepo.updateProgramWithdrawn(program.getProgramID(), newWithdrawn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void recordTransaction() {
        try {
            TransactionRepository transactionRepo = TransactionRepository.getInstance();
            transactionRepo.insertWithdrawal(transactionID, withdrawMethod);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
