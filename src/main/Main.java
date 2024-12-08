package main;

import dao.ProgramRepository;
import dao.TransactionRepository;
import dao.UserRepository;
import model.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

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

    public static void printTitle(){
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

    public void Home() {
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

    public void Login() {
        printTitle();
        System.out.println("=============================================================");
        System.out.println("                          LOGIN                              ");
        System.out.println("=============================================================");
        try {
            UserRepository userRepo = UserRepository.getInstance();
            String email;
            String password;
            do {
                System.out.print("🔹 Enter your email: ");
                email = sc.nextLine();
                if (email.isEmpty()) {
                    System.out.println("Email cannot be empty. Please try again.");
                } else if (!isValidEmail(email)) {
                    System.out.println("Invalid email format. Please try again.");
                } else {
                    break;
                }
            } while (true);
            do {
                System.out.print("🔹 Enter your password: ");
                password = sc.nextLine();
                if (password.isEmpty()) {
                    System.out.println("Password cannot be empty. Please try again.");
                } else if (password.length() < 6) {
                    System.out.println("Password must be at least 6 characters long. Please try again.");
                } else {
                    break;
                }
            } while (true);
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

    public void Register() {
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
                System.out.print("Enter your Username: ");
                name = sc.nextLine();
                if (name.isEmpty()) {
                    System.out.println("Username cannot be empty. Please try again.");
                } else if (name.length() < 3 || name.length() > 50) {
                    System.out.println("Username must be between 3 and 50 characters. Please try again.");
                } else {
                    break;
                }
            } while (true);

            do {
                System.out.print("Enter your email: ");
                email = sc.nextLine();
                if (email.isEmpty()) {
                    System.out.println("Email cannot be empty. Please try again.");
                } else if (!isValidEmail(email)) {
                    System.out.println("Invalid email format. Please try again.");
                } else {
                    break;
                }
            } while (true);

            do {
                System.out.print("Enter your password: ");
                password = sc.nextLine();
                if (password.isEmpty()) {
                    System.out.println("Password cannot be empty. Please try again.");
                } else if (password.length() < 6) {
                    System.out.println("Password must be at least 6 characters long. Please try again.");
                } else if (!isAlphanumeric(password)) {
                    System.out.println("Password must be alphanumeric. Please try again.");
                } else {
                    break;
                }
            } while (true);

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
        System.out.println("\n--- Your Profile ---");
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

    public static void updateProfile() {
        printTitle();
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
        System.out.println(program.getProgramRaisedString() + " raised of " + program.getProgramTargetString() + " goal");

        try {
            ProgramRepository programRepo = ProgramRepository.getInstance();
            List<Comment> comments = programRepo.getCommentsByProgramId(program.getProgramID());
            if(!comments.isEmpty()){
                System.out.println("--------------------------------------------------------------");
                System.out.println("Supporting Words");
                System.out.println("--------------------------------------------------------------");
                for (Comment comment : comments) {
                    System.out.println("🔹 " + comment.getUserName());
                    System.out.println("   " + comment.getAmount() + " donated");
                    System.out.print("   ");
                    printWrappedDescription(comment.getContent(), 60); // Wrap text if too long
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("0. Back");
        if(currUser!=null && currUser.getUserId() == program.getFundraiserID()){
            System.out.println("1. Withdraw");
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
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private void donate(Program program) {
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

    public void viewHistory() {
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

    private void createProgram(){

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
            int target;
            boolean confirmation;


            // Validate title
            do {
                System.out.print("Enter the program title: ");
                title = sc.nextLine();
                if (title.isEmpty()) {
                    System.out.println("Title cannot be empty. Please try again.");
                } else {
                    break;
                }
            } while (true);

            // Validate name
            do {
                System.out.print("Enter your name: ");
                name = sc.nextLine();
                if (name.isEmpty()) {
                    System.out.println("Name cannot be empty. Please try again.");
                } else {
                    break;
                }
            } while (true);

            // Validate beneficiary
            do {
                System.out.print("Enter the beneficiary name: ");
                beneficiary = sc.nextLine();
                if (beneficiary.isEmpty()) {
                    System.out.println("Beneficiary cannot be empty. Please try again.");
                } else {
                    break;
                }
            } while (true);

            // Validate description
            do {
                System.out.print("Enter the program description: ");
                desc = sc.nextLine();
                if (desc.isEmpty()) {
                    System.out.println("Description cannot be empty. Please try again.");
                } else {
                    break;
                }
            } while (true);


            // Validate target amount
            do {
                System.out.print("Enter the target amount (in IDR): ");
                String targetInput = sc.nextLine();
                try {
                    target = Integer.parseInt(targetInput);
                    if (target <= 0) {
                        System.out.println("Target must be greater than zero. Please try again.");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid amount. Please enter a valid number.");
                }
            } while (true);

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

            // At this point, all validations have passed, and the user has confirmed
            if (confirmation) {
                int programId = programRepo.createProgram(currUser.getUserId(), title, name, beneficiary, desc, target);

                if (programId != -1) {
                    System.out.println("Program created successfully. Program ID: " + programId);
                } else {
                    System.out.println("Failed to create program. Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private static boolean isAlphanumeric(String text) {
        String regex = "^[a-zA-Z0-9]*$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(text).matches();
    }

    private void viewPrograms(){
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

    public static void withdraw(Program program){
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
}

