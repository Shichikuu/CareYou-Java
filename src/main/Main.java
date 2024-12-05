package main;

import dao.ProgramRepository;
import dao.TransactionRepository;
import dao.UserRepository;
import model.Comment;
import model.Program;
import model.Transaction;
import model.User;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static User currUser = null;
    private static Scanner sc = new Scanner(System.in);

    public Main() {
        while (true) {
            Home();
        }
    }

    public static void main(String[] args) {
        new Main();
    }

    public void Home() {
        clearConsole();
        System.out.println("=============================================================");
        System.out.println();
        System.out.println("  ██████╗ █████╗ ██████╗ ███████╗██╗   ██╗ ██████╗ ██╗   ██╗ ");
        System.out.println(" ██╔════╝██╔══██╗██╔══██╗██╔════╝╚██╗ ██╔╝██╔═══██╗██║   ██║ ");
        System.out.println(" ██║     ███████║██████╔╝█████╗   ╚████╔╝ ██║   ██║██║   ██║ ");
        System.out.println(" ██║     ██╔══██║██╔══██╗██╔══╝    ╚██╔╝  ██║   ██║██║   ██║ ");
        System.out.println(" ╚██████╗██║  ██║██║  ██║███████╗   ██║   ╚██████╔╝╚██████╔╝ ");
        System.out.println("  ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝   ╚═╝    ╚═════╝  ╚═════╝  ");
        System.out.println();
        System.out.println("=============================================================");
        System.out.println("              JOIN US IN MAKING DREAMS A REALITY             ");
        System.out.println("=============================================================");
        System.out.println();
        System.out.println("=========================== MENU ============================");
        if (currUser == null) {
            System.out.println("\uD83C\uDF1F  1. Login");
            System.out.println("\uD83C\uDF1F  2. Register");
            System.out.println("\uD83C\uDF1F  3. Start Donating");
            System.out.println("\uD83C\uDF1F  4. Start Fundraising");
            System.out.println("\uD83D\uDEAA  0. Exit");
        } else {
            System.out.println("\uD83C\uDF1F 1. Profile");
            System.out.println("\uD83C\uDF1F 2. History");
            System.out.println("\uD83C\uDF1F 3. Start Donating");
            System.out.println("\uD83C\uDF1F 4. Start Fundraising");
            System.out.println("\uD83C\uDF1F 5. Logout");
            System.out.println("\uD83D\uDEAA  0. Exit");
        }
        System.out.println("=============================================================");
        System.out.print("🔹 Choose an option: ");
        switch (sc.nextLine()) {
            case "1":
                if (currUser == null) {
                    Login();
                } else {
                    viewProfile();
                }
                break;
            case "2":
                if (currUser == null) {
                    Register();
                } else {
                    //viewHistory();
                }
                break;
            case "3":
                startDonating();
                break;
            case "4":
                //startFundraising();
                break;
            case "5":
                if (currUser != null) {
                    logout();
                } else {
                    System.out.println("Invalid option. Please try again.");
                }
                break;
            case "0":
                System.out.println("Thank you for using CareYou. Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                // For Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // For Linux/Unix/Mac
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Could not clear the console.");
        }
    }

    public void Login() {
        clearConsole();
        System.out.println("=============================================================");
        System.out.println();
        System.out.println("  ██████╗ █████╗ ██████╗ ███████╗██╗   ██╗ ██████╗ ██╗   ██╗ ");
        System.out.println(" ██╔════╝██╔══██╗██╔══██╗██╔════╝╚██╗ ██╔╝██╔═══██╗██║   ██║ ");
        System.out.println(" ██║     ███████║██████╔╝█████╗   ╚████╔╝ ██║   ██║██║   ██║ ");
        System.out.println(" ██║     ██╔══██║██╔══██╗██╔══╝    ╚██╔╝  ██║   ██║██║   ██║ ");
        System.out.println(" ╚██████╗██║  ██║██║  ██║███████╗   ██║   ╚██████╔╝╚██████╔╝ ");
        System.out.println("  ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝   ╚═╝    ╚═════╝  ╚═════╝  ");
        System.out.println();
        System.out.println("=============================================================");
        System.out.println("                          LOGIN                              ");
        System.out.println("=============================================================");
        try {
            UserRepository userRepo = UserRepository.getInstance();
            System.out.print("🔹 Email: ");
            String email = sc.nextLine();
            System.out.print("🔹 Password: ");
            String password = sc.nextLine();
            User user = userRepo.validateUser(email, password);
            if (user != null) {
                currUser = user;
                System.out.println("Login successful. Welcome, " + user.getUsername() + "!");
            } else {
                System.out.println("Invalid email or password. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Register() {
        clearConsole();
        System.out.println("=============================================================");
        System.out.println();
        System.out.println("  ██████╗ █████╗ ██████╗ ███████╗██╗   ██╗ ██████╗ ██╗   ██╗ ");
        System.out.println(" ██╔════╝██╔══██╗██╔══██╗██╔════╝╚██╗ ██╔╝██╔═══██╗██║   ██║ ");
        System.out.println(" ██║     ███████║██████╔╝█████╗   ╚████╔╝ ██║   ██║██║   ██║ ");
        System.out.println(" ██║     ██╔══██║██╔══██╗██╔══╝    ╚██╔╝  ██║   ██║██║   ██║ ");
        System.out.println(" ╚██████╗██║  ██║██║  ██║███████╗   ██║   ╚██████╔╝╚██████╔╝ ");
        System.out.println("  ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝   ╚═╝    ╚═════╝  ╚═════╝  ");
        System.out.println();
        System.out.println("=============================================================");
        System.out.println("                          REGISTER                           ");
        System.out.println("=============================================================");
        try {
            UserRepository userRepo = UserRepository.getInstance();

            System.out.print("🔹 Enter your name: ");
            String name = sc.nextLine();

            System.out.print("🔹 Enter your email: ");
            String email = sc.nextLine();

            System.out.print("🔹 Enter your password: ");
            String password = sc.nextLine();

            User user = userRepo.register(name, email, password);

            if (user != null) {
                System.out.println("Registration successful. You can now log in.");
                Login();
            } else {
                System.out.println("Registration failed. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void logout() {
        currUser = null;
        System.out.println("You have been logged out.");
    }

    private static void viewProfile() {
        clearConsole();
        System.out.println("\n--- Your Profile ---");
        System.out.println("Name: " + currUser.getUsername());
        System.out.println("Email: " + currUser.getEmail());
        System.out.println("Option :");
        System.out.println("1. Update Profile");
        System.out.println("2. Back");
        System.out.print("🔹 Choose an option: ");
        switch (sc.nextLine()) {
            case "1":
                // updateProfile();
                break;
            case "2":
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    public void updateProfile() {
        clearConsole();
        System.out.println("=============================================================");
        System.out.println();
        System.out.println("  ██████╗ █████╗ ██████╗ ███████╗██╗   ██╗ ██████╗ ██╗   ██╗ ");
        System.out.println(" ██╔════╝██╔══██╗██╔══██╗██╔════╝╚██╗ ██╔╝██╔═══██╗██║   ██║ ");
        System.out.println(" ██║     ███████║██████╔╝█████╗   ╚████╔╝ ██║   ██║██║   ██║ ");
        System.out.println(" ██║     ██╔══██║██╔══██╗██╔══╝    ╚██╔╝  ██║   ██║██║   ██║ ");
        System.out.println(" ╚██████╗██║  ██║██║  ██║███████╗   ██║   ╚██████╔╝╚██████╔╝ ");
        System.out.println("  ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝   ╚═╝    ╚═════╝  ╚═════╝  ");
        System.out.println();
        System.out.println("=============================================================");
        System.out.println("                          UPDATE PROFILE                     ");
        System.out.println("=============================================================");
        try {
            UserRepository userRepo = UserRepository.getInstance();
            System.out.print("🔹 Enter your name: ");
            String name = sc.nextLine();
            System.out.print("🔹 Enter your email: ");
            String email = sc.nextLine();
            System.out.print("🔹 Enter your password: ");
            String password = sc.nextLine();
            userRepo.updateUserProfile(currUser, name, email, password);
            System.out.println("Profile updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void startDonating() {
        clearConsole();
        System.out.println("=============================================================");
        System.out.println();
        System.out.println("  ██████╗ █████╗ ██████╗ ███████╗██╗   ██╗ ██████╗ ██╗   ██╗ ");
        System.out.println(" ██╔════╝██╔══██╗██╔══██╗██╔════╝╚██╗ ██╔╝██╔═══██╗██║   ██║ ");
        System.out.println(" ██║     ███████║██████╔╝█████╗   ╚████╔╝ ██║   ██║██║   ██║ ");
        System.out.println(" ██║     ██╔══██║██╔══██╗██╔══╝    ╚██╔╝  ██║   ██║██║   ██║ ");
        System.out.println(" ╚██████╗██║  ██║██║  ██║███████╗   ██║   ╚██████╔╝╚██████╔╝ ");
        System.out.println("  ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝   ╚═╝    ╚═════╝  ╚═════╝  ");
        System.out.println();
        System.out.println("=============================================================");
        System.out.println("                           PROGRAMS                          ");
        System.out.println("=============================================================");
        System.out.println();
        try {
            ProgramRepository programRepo = ProgramRepository.getInstance();
            List<Program> programs = programRepo.getAllPrograms();
            int i = 1;
            for (Program program : programs) {
                System.out.println("=============================================================");
                System.out.print(i + ". ");
                printWrappedDescription(program.getProgramTitle(), 55);
                System.out.println("   Fundraiser : " + program.getFundraiserName());
                System.out.println("   Percentage : " + program.getPercentage());
                System.out.println("=============================================================");
            }
            System.out.println("0. Back");
            System.out.print("🔹 Choose a program to donate to: ");
            int choice = Integer.parseInt(sc.nextLine());
            if (choice == 0) {
                return;
            }
            Program selectedProgram = programs.get(choice - 1);
            getProgramDetails(selectedProgram);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void printWrappedDescription(String description, int maxLineLength) {
        int length = description.length();
        int startIndex = 0;

        while (startIndex < length) {
            // Calculate the end index for the current line, but ensure we don't exceed the string length
            int endIndex = Math.min(startIndex + maxLineLength, length);

            // If endIndex is in the middle of a word, find the previous space to avoid breaking words
            if (endIndex < length && description.charAt(endIndex) != ' ') {
                int lastSpace = description.lastIndexOf(' ', endIndex);
                if (lastSpace > startIndex) {
                    endIndex = lastSpace;
                }
            }

            // Print the substring for the current line
            System.out.println(description.substring(startIndex, endIndex).trim());

            // Update the startIndex for the next iteration
            startIndex = endIndex + 1;
        }
    }

    private void getProgramDetails(Program program) {
        clearConsole();
        System.out.println("=============================================================");
        printWrappedDescription(program.getProgramTitle(), 60);
        System.out.println("=============================================================");
        System.out.println("Fundraiser: " + program.getFundraiserName());
        System.out.println("Beneficiary: " + program.getBeneficiaryName());
        System.out.println();
        printWrappedDescription(program.getProgramDesc(), 60);
        System.out.println();
        System.out.println(program.getProgramRaised() + " raised of " + program.getProgramTarget() + " goal");
        System.out.println("--------------------------------------------------------------");
        System.out.println("Supporting Words");
        System.out.println("--------------------------------------------------------------");
        try {
            ProgramRepository programRepo = ProgramRepository.getInstance();
            List<Comment> comments = programRepo.getCommentsByProgramId(program.getProgramID());

            UserRepository userRepo = UserRepository.getInstance();

            for (Comment comment : comments) {
                System.out.println("🔹 " + comment.getUserName());
                System.out.println("   " + comment.getAmount() + " donated");
                System.out.print("   ");
                printWrappedDescription(comment.getContent(), 60); // Wrap text if too long
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("0. Back");
        System.out.print("1. Donate");
        System.out.print("🔹 Choose an option: ");
        switch (sc.nextLine()) {
            case "0":
                return;
            case "1":
                donate(program);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private void donate(Program program) {
        int amount;
        String ch;
        boolean isAnonymous = true;
        boolean hasComment;
        String content = "";
        clearConsole();
        System.out.println("=============================================================");
        System.out.println("                            PAYMENT                          ");
        System.out.println("=============================================================");
        System.out.println();
        do{
            System.out.print("🔹 Enter your donation amount: ");
            amount = Integer.parseInt(sc.nextLine());
        }while (amount < 0);
        do{
            System.out.print("Do you want to leave a supporting message? [Y/N] : ");
            ch = sc.nextLine();
        }while (!ch.equalsIgnoreCase("Y") && !ch.equalsIgnoreCase("N"));
        if(ch.equalsIgnoreCase("Y")) {
            hasComment = true;
            System.out.print("Do you want to donate anonymously? [Y/N] : ");
            do{
                ch = sc.nextLine();
            }while (!ch.equalsIgnoreCase("Y") && !ch.equalsIgnoreCase("N"));
            if(ch.equalsIgnoreCase("Y")){
                isAnonymous = true;
            }else{
                isAnonymous = false;
            }
            do{
                System.out.print("Enter your supporting words:");
                content = sc.nextLine();
            }while (content.isEmpty());
        }else{
            hasComment = false;
        }
        try {
            TransactionRepository transactionRepo = TransactionRepository.getInstance();
            int id = transactionRepo.insertTransaction(currUser.getUserId(), new Date(), amount, "Donation", program.getProgramID());
            transactionRepo.insertDonation(id, "",hasComment);
            if(hasComment){
                if(isAnonymous){
                    transactionRepo.addComment(program.getProgramID(), content, "Anonymous", amount);
                }else{
                    transactionRepo.addComment(program.getProgramID(), content, currUser.getUsername(), amount);
                }
            }
            System.out.println("Donation successful. Thank you for your support!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewHistory() {
        clearConsole();
        System.out.println("=============================================================");
        System.out.println();
        System.out.println("  ██████╗ █████╗ ██████╗ ███████╗██╗   ██╗ ██████╗ ██╗   ██╗ ");
        System.out.println(" ██╔════╝██╔══██╗██╔══██╗██╔════╝╚██╗ ██╔╝██╔═══██╗██║   ██║ ");
        System.out.println(" ██║     ███████║██████╔╝█████╗   ╚████╔╝ ██║   ██║██║   ██║ ");
        System.out.println(" ██║     ██╔══██║██╔══██╗██╔══╝    ╚██╔╝  ██║   ██║██║   ██║ ");
        System.out.println(" ╚██████╗██║  ██║██║  ██║███████╗   ██║   ╚██████╔╝╚██████╔╝ ");
        System.out.println("  ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝   ╚═╝    ╚═════╝  ╚═════╝  ");
        System.out.println();
        System.out.println("=============================================================");
        System.out.println("                          HISTORY                            ");
        System.out.println("=============================================================");
        System.out.println();
        try {
            TransactionRepository transactionRepo = TransactionRepository.getInstance();
            List<Transaction> transactions = transactionRepo.getTransactionByUserID(currUser.getUserId());
            for (Transaction transaction : transactions) {
                System.out.println("=============================================================");
                System.out.println("Date: " + transaction.getTransactionDate());
                System.out.println("Amount: " + transaction.getAmount());
                System.out.println("Transaction Type: " + transaction.getTransactionType());
                System.out.println("=============================================================");
            }
            System.out.println("0. Back");
            System.out.print("🔹 Choose an option: ");
            switch (sc.nextLine()) {
                case "0":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void startFundraising() {
        clearConsole();
        System.out.println("=============================================================");
        System.out.println();
        System.out.println("  ██████╗ █████╗ ██████╗ ███████╗██╗   ██╗ ██████╗ ██╗   ██╗ ");
        System.out.println(" ██╔════╝██╔══██╗██╔══██╗██╔════╝╚██╗ ██╔╝██╔═══██╗██║   ██║ ");
        System.out.println(" ██║     ███████║██████╔╝█████╗   ╚████╔╝ ██║   ██║██║   ██║ ");
        System.out.println(" ██║     ██╔══██║██╔══██╗██╔══╝    ╚██╔╝  ██║   ██║██║   ██║ ");
        System.out.println(" ╚██████╗██║  ██║██║  ██║███████╗   ██║   ╚██████╔╝╚██████╔╝ ");
        System.out.println("  ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝   ╚═╝    ╚═════╝  ╚═════╝  ");
        System.out.println();
        System.out.println("=============================================================");
        System.out.println("                          FUNDRAISING                        ");
        System.out.println("=============================================================");
        System.out.println();
        System.out.println("1. Start a Fundraiser");
        System.out.println("2. View Your Fundraisers");
        System.out.println("0. Back");
        System.out.print("🔹 Choose an option: ");
        switch (sc.nextLine()) {
            case "1":
                // startFundraiser();
                break;
            case "2":
                // viewFundraisers();
                break;
            case "0":
                return;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

}

