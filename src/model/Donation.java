package model;

import dao.ProgramRepository;
import dao.TransactionRepository;

import java.sql.SQLException;
import java.util.Date;

import static main.Main.currUser;
import static main.Main.sc;

public class Donation extends Transaction {
    private String paymentMethod;
    private boolean hasComment;
    private Comment comment;

    // Constructors

    public Donation(int transactionID, int userID, Date transactionDate, int amount, Integer programID, String paymentMethod, boolean hasComment) {
        super(transactionID, userID, transactionDate, amount, programID);
        this.paymentMethod = paymentMethod;
        this.hasComment = hasComment;
        this.transactionType = "Donation";
    }

    public Donation(Program program) {
        super(program);
        this.transactionType = "Donation";
    }

    @Override
    protected int validateTransaction(Program program) {
        String ch;
        boolean isAnonymous;
        String content;
        System.out.println("Enter 0 to cancel the donation anytime");
        System.out.println();
        do{
            System.out.print("🔹 Enter your donation amount: ");
            this.amount = Integer.parseInt(sc.nextLine());
            if(this.amount < 0){
                System.out.println("Invalid amount. Please enter a valid amount.");
            }
            if(this.amount == 0){
                return 0;
            }
        }while (this.amount < 0);
        do{
            System.out.println("Choose a payment method:");
            System.out.println("1. Digital Wallet");
            System.out.println("2. Bank Transfer");
            System.out.print("🔹 Choose an option: ");
            ch = sc.nextLine();
            if(ch.equals("1")){
                this.paymentMethod = "Digital Wallet";
            }else if(ch.equals("2")){
                this.paymentMethod = "Bank Transfer";
            }
            if(ch.equals("0")){
                return 0;
            }
        }while (!ch.equals("1") && !ch.equals("2"));
        do{
            System.out.print("Do you want to leave a supporting message? [Y/N] : ");
            ch = sc.nextLine();
            if(ch.equals("0")){
                return 0;
            }
        }while (!ch.equalsIgnoreCase("Y") && !ch.equalsIgnoreCase("N"));
        if(ch.equalsIgnoreCase("Y")) {
            this.hasComment = true;
            System.out.print("Do you want to donate anonymously? [Y/N] : ");
            do{
                ch = sc.nextLine();
                if(ch.equals("0")){
                    return 0;
                }
            }while (!ch.equalsIgnoreCase("Y") && !ch.equalsIgnoreCase("N"));
            isAnonymous = ch.equalsIgnoreCase("Y");
            do{
                System.out.print("Enter your supporting words:");
                content = sc.nextLine();
                if(content.isEmpty()){
                    System.out.println("Comment cannot be empty");
                }else if(content.equals("0")){
                    return 0;
                }
            }while (content.isEmpty());
            if(hasComment){
                comment = new Comment();
                comment.setAmount(amount);
                comment.setContent(content);
                if(isAnonymous){
                    comment.setUserName("Anonymous");
                }else{
                    comment.setUserName(currUser.getUsername());
                }
            }
        }else{
            hasComment = false;
        }
        return 1;
    }

    @Override
    protected void executeTransaction(Program program) {
        int raised = program.getProgramRaised();
        program.setProgramRaised(raised + amount);
        try {
            ProgramRepository programRepo = ProgramRepository.getInstance();
            programRepo.updateProgramRaised(program.getProgramID(), amount);
            if(program.getProgramRaised() >= program.getProgramTarget()){
                programRepo.updateProgramStatus(program.getProgramID(), "Completed");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void recordTransaction() {
        try {
            TransactionRepository transactionRepo = TransactionRepository.getInstance();
            transactionRepo.insertDonation(transactionID, paymentMethod, hasComment);
            if(hasComment){
                addComment();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addComment(){
        TransactionRepository transactionRepo = null;
        try {
            transactionRepo = TransactionRepository.getInstance();
            transactionRepo.addComment(transactionID, comment.getContent(), comment.getUserName(), amount);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isHasComment() {
        return hasComment;
    }

    public void setHasComment(boolean hasComment) {
        this.hasComment = hasComment;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
