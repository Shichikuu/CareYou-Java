package main;

import dao.ProgramRepository;
import dao.TransactionRepository;
import dao.UserRepository;
import model.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import static Validation.UserInputValidator.*;

public class Main {
    public static User currUser = null;
    public static Scanner sc = new Scanner(System.in);

    public Main() {
        while (true) {
            Home();
        }
    }

    public static void main(String[] args) {
        new Main();
    }

    private static void printTitle(){
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
    }

    private static void Home() {
        printTitle();
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
            System.out.println("\uD83D\uDEAA 0. Exit");
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
                    viewHistory();
                }
                break;
            case "3":
                startDonating();
                break;
            case "4":
                startFundraising();
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



    private static void Login() {
        printTitle();
        System.out.println("=============================================================");
        System.out.println("                          LOGIN                              ");
        System.out.println("=============================================================");
        try {
            UserRepository userRepo = UserRepository.getInstance();
            String email;
            String password;
            do{
                System.out.print("🔹 Enter your email: ");
                email = sc.nextLine();
            }while(!validateEmail(email));
            do {
                System.out.print("🔹 Enter your password: ");
                password = sc.nextLine();
            } while (!validatePassword(password));
            User user = userRepo.validateUser(email, password);
            if (user != null) {
                currUser = user;
                System.out.println("Login successful. Welcome, " + user.getUsername() + "!");
                sc.nextLine();
            } else {
                System.out.println("Invalid email or password. Please try again.");
                sc.nextLine();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void Register() {
        printTitle();
        System.out.println("=============================================================");
        System.out.println("                          REGISTER                           ");
        System.out.println("=============================================================");
        try {
            UserRepository userRepo = UserRepository.getInstance();
            String name;
            String email;
            String password;
            do {
                System.out.print("🔹 Enter your Username: ");
                name = sc.nextLine();
            } while (!validateUsername(name));

            do {
                System.out.print("🔹 Enter your email: ");
                email = sc.nextLine();
            } while (!validateEmail(email));

            do {
                System.out.print("🔹 Enter your password: ");
                password = sc.nextLine();
            } while (!validateRegisterPassword(password));

            User user = userRepo.register(name, email, password);

            if (user != null) {
                System.out.println("Registration successful. You can now log in.");
                sc.nextLine();
                Login();
            } else {
                System.out.println("Registration failed. Please try again.");
                sc.nextLine();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void logout() {
        currUser = null;
        System.out.println("You have been logged out.");
        sc.nextLine();
    }

    private static void viewProfile() {
        clearConsole();
        System.out.println("=============================================================");
        System.out.println("                           PROFILE                           ");
        System.out.println("=============================================================");
        System.out.println("Name: " + currUser.getUsername());
        System.out.println("Email: " + currUser.getEmail());
        System.out.println("Option :");
        System.out.println("1. Update Profile");
        System.out.println("2. Back");
        System.out.print("🔹 Choose an option: ");
        switch (sc.nextLine()) {
            case "1":
                updateProfile();
                break;
            case "2":
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void updateProfile() {
        printTitle();
        System.out.println("=============================================================");
        System.out.println("                          UPDATE PROFILE                     ");
        System.out.println("=============================================================");
        try {
            UserRepository userRepo = UserRepository.getInstance();
            String name;
            String email;
            String password;
            do {
                System.out.print("🔹 Enter your Username: ");
                name = sc.nextLine();
            } while (!validateUsername(name));

            do {
                System.out.print("🔹 Enter your email: ");
                email = sc.nextLine();
            } while (!validateEmail(email));

            do {
                System.out.print("🔹 Enter your password: ");
                password = sc.nextLine();
            } while (!validateRegisterPassword(password));
            if(userRepo.updateUserProfile(currUser, name, email, password)) {
                currUser.setUsername(name);
                currUser.setEmail(email);
                currUser.setPassword(password);
                System.out.println("Profile updated successfully.");
            }else{
                System.out.println("Profile update failed. Please try again.");
                sc.nextLine();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void startDonating() {
        printTitle();
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
            while (choice < 0 || choice > programs.size()) {
                System.out.println("Invalid option. Please try again.");
                choice = Integer.parseInt(sc.nextLine());
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

            int endIndex = Math.min(startIndex + maxLineLength, length);

            if (endIndex < length && description.charAt(endIndex) != ' ') {
                int lastSpace = description.lastIndexOf(' ', endIndex);
                if (lastSpace > startIndex) {
                    endIndex = lastSpace;
                }
            }

            System.out.println(description.substring(startIndex, endIndex).trim());

            startIndex = endIndex + 1;
        }
    }

    private static void getProgramDetails(Program program) {
        clearConsole();
        System.out.println("=============================================================");
        printWrappedDescription(program.getProgramTitle(), 60);
        System.out.println("=============================================================");
        System.out.println("Fundraiser: " + program.getFundraiserName());
        System.out.println("Beneficiary: " + program.getBeneficiaryName());
        System.out.println();
        printWrappedDescription(program.getProgramDesc(), 60);
        System.out.println();
        System.out.println(program.getProgramRaisedString() + " raised of " + program.getProgramTargetString() + " goal");
        List<Comment> comments = program.getComments();
        if(comments != null && !comments.isEmpty()){
            System.out.println("--------------------------------------------------------------");
            System.out.println("Supporting Words");
            System.out.println("--------------------------------------------------------------");
            for (Comment comment : comments) {
                System.out.println("🔹 " + comment.getUserName());
                System.out.println("   " + comment.getAmount() + " donated");
                System.out.print("   ");
                printWrappedDescription(comment.getContent(), 60);
                System.out.println();
            }
        }
        System.out.println("0. Back");
        if(currUser!=null && currUser.getUserId() == program.getFundraiserID()){
            System.out.println("1. Withdraw");
            System.out.println("2. Delete");
        }else{
            System.out.println("1. Donate");
        }
        System.out.print("🔹 Choose an option: ");
        switch (sc.nextLine()) {
            case "0":
                return;
            case "1":
                if(currUser!=null && currUser.getUserId() == program.getFundraiserID()){
                    withdraw(program);
                }else{
                    donate(program);
                }
                break;
            case "2":
                if(currUser!=null && currUser.getUserId() == program.getFundraiserID()){
                    deleteProgram(program);
                }else{
                    System.out.println("Invalid option. Please try again.");
                }
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void donate(Program program) {
        if(currUser == null){
            System.out.println("You need to login first.");
            sc.nextLine();
            return;
        }
        clearConsole();
        System.out.println("=============================================================");
        System.out.println("                            PAYMENT                          ");
        System.out.println("=============================================================");
        System.out.println();
        Donation donation = new Donation(program);
        int flag = donation.processTransaction(program);
        if(flag == 1){
            System.out.println("Donation successful. Thank you for your support!");
        }else{
            System.out.println("Donation canceled.");
        }
        sc.nextLine();

    }

    private static void viewHistory() {
        printTitle();
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
            if (sc.nextLine().equals("0")) {
                return;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void startFundraising() {
        if(currUser == null){
            System.out.println("You need to login first.");
            sc.nextLine();
            return;
        }
        printTitle();
        System.out.println("=============================================================");
        System.out.println("                          FUNDRAISING                        ");
        System.out.println("=============================================================");
        System.out.println();
        System.out.println("1. Create a Program");
        System.out.println("2. View Your Programs");
        System.out.println("0. Back");
        System.out.print("🔹 Choose an option: ");
        switch (sc.nextLine()) {
            case "1":
                createProgram();
                break;
            case "2":
                viewPrograms();
                break;
            case "0":
                return;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void createProgram(){

        System.out.println("=============================================================");
        System.out.println("                      CREATE YOUR PROGRAM                    ");
        System.out.println("=============================================================");
        System.out.println();
        try {
            ProgramRepository programRepo = ProgramRepository.getInstance();

            String title;
            String name;
            String beneficiary;
            String desc;
            int target = 0;
            boolean confirmation;

            do {
                System.out.print("🔹 Enter the program title: ");
                title = sc.nextLine();
            } while (!validateTitle(title));

            do {
                System.out.print("🔹 Enter your name: ");
                name = sc.nextLine();
            } while (!validateName(name));

            do {
                System.out.print("🔹 Enter the beneficiary name: ");
                beneficiary = sc.nextLine();
            } while (!validateBeneficiary(beneficiary));

            do {
                System.out.print("🔹 Enter the program description: ");
                desc = sc.nextLine();
            } while (!validateDescription(desc));

            do {
                System.out.print("🔹 Enter the target amount (in IDR): ");
                String targetInput = sc.nextLine();
                try {
                    target = Integer.parseInt(targetInput);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid amount. Please enter a valid number.");
                }
            } while (!validateTarget(target));

            // Confirmation
            do {
                System.out.print("Do you confirm the data entered? (yes/no): ");
                String confirmationInput = sc.nextLine().toLowerCase();
                if (confirmationInput.equals("yes")) {
                    confirmation = true;
                    break;
                } else if (confirmationInput.equals("no")) {
                    confirmation = false;
                    System.out.println("Program creation cancelled.");
                    return;
                } else {
                    System.out.println("Please enter 'yes' or 'no'.");
                }
            } while (true);

            if (confirmation) {
                int programId = programRepo.createProgram(currUser.getUserId(), title, name, beneficiary, desc, target);

                if (programId != -1) {
                    System.out.println("Program created successfully. Program ID: " + programId);
                } else {
                    System.out.println("Failed to create program. Please try again.");
                }
                sc.nextLine();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewPrograms(){
        printTitle();
        System.out.println("=============================================================");
        System.out.println("                        YOUR PROGRAMS                        ");
        System.out.println("=============================================================");
        System.out.println();
        try {
            ProgramRepository programRepo = ProgramRepository.getInstance();
            List<Program> programs = programRepo.getProgramsByUserID(currUser.getUserId());
            if(programs.isEmpty()){
                System.out.println("You have not created any programs yet.");
                sc.nextLine();
                return;
            }
            int i = 1;
            for (Program program : programs) {
                System.out.println("=============================================================");
                System.out.println("Title: " + program.getProgramTitle());
                System.out.println("Beneficiary: " + program.getBeneficiaryName());
                System.out.println("Target: " + program.getProgramTargetString());
                System.out.println("Raised: " + program.getProgramRaisedString());
                System.out.println("=============================================================");
            }

            System.out.println("0. Back");
            System.out.print("🔹 Choose a program : ");
            int choice = Integer.parseInt(sc.nextLine());
            if (choice == 0) {
                return;
            }
            while (choice < 0 || choice > programs.size()) {
                System.out.println("Invalid option. Please try again.");
                choice = Integer.parseInt(sc.nextLine());
            }
            Program selectedProgram = programs.get(choice - 1);
            getProgramDetails(selectedProgram);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void withdraw(Program program){
        clearConsole();
        System.out.println("=============================================================");
        System.out.println("                          WITHDRAW                            ");
        System.out.println("=============================================================");
        System.out.println();
        Withdrawal withdrawal = new Withdrawal(program);
        int flag = withdrawal.processTransaction(program);
        if(flag == 1){
            System.out.println("Withdrawal successful.");
        }else if(flag == -1){
            System.out.println("The program has already been fully withdrawn.");
        }else{
            System.out.println("Withdrawal canceled.");
        }
        sc.nextLine();
    }

    private static void deleteProgram(Program program){
        if(program.getWithdrawn() != program.getProgramRaised()){
            System.out.println("You cannot delete the program because it has not been fully withdrawn.");
            sc.nextLine();
            return;
        }
        clearConsole();
        System.out.println("=============================================================");
        System.out.println("                          DELETE PROGRAM                      ");
        System.out.println("=============================================================");
        System.out.println();
        try {
            ProgramRepository programRepo = ProgramRepository.getInstance();
            programRepo.deleteProgram(program.getProgramID());
            System.out.println("Program deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

