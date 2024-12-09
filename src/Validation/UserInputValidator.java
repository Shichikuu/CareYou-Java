package Validation;

import java.util.regex.Pattern;

import static main.Main.sc;

public class UserInputValidator {

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

    public static boolean validateEmail(String email) {
        if (email.isEmpty()) {
            System.out.println("Email cannot be empty. Please try again.");
        } else if (!isValidEmail(email)) {
            System.out.println("Invalid email format. Please try again.");
        } else {
            return true;
        }
        return false;
    }

    public static boolean validateUsername(String name) {
        if (name.isEmpty()) {
            System.out.println("Username cannot be empty. Please try again.");
        } else if (name.length() < 3 || name.length() > 50) {
            System.out.println("Username must be between 3 and 50 characters. Please try again.");
        } else {
            return true;
        }
        return false;
    }

    public static boolean validatePassword(String password){
        if (password.isEmpty()) {
            System.out.println("Password cannot be empty. Please try again.");
        } else if (password.length() < 6) {
            System.out.println("Password must be at least 6 characters long. Please try again.");
        } else {
            return true;
        }
        return false;
    }

    public static boolean validateRegisterPassword(String password){
        if (password.isEmpty()) {
            System.out.println("Password cannot be empty. Please try again.");
        } else if (password.length() < 6) {
            System.out.println("Password must be at least 6 characters long. Please try again.");
        } else if (!isAlphanumeric(password)) {
            System.out.println("Password must be alphanumeric. Please try again.");
        } else {
            return true;
        }
        return false;
    }

    public static boolean validateTitle(String title) {
        if (title.isEmpty()) {
            System.out.println("Title cannot be empty. Please try again.");
        } else if (title.length() < 3 || title.length() > 50) {
            System.out.println("Title must be between 3 and 50 characters. Please try again.");
        } else {
            return true;
        }
        return false;
    }

    public static boolean validateName(String name) {
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty. Please try again.");
        } else if (name.length() < 3 || name.length() > 50) {
            System.out.println("Name must be between 3 and 50 characters. Please try again.");
        } else {
            return true;
        }
        return false;
    }

    public static boolean validateBeneficiary(String name){
        if (name.isEmpty()) {
            System.out.println("Beneficiary cannot be empty. Please try again.");
        } else if (name.length() < 3 || name.length() > 50) {
            System.out.println("Beneficiary must be between 3 and 50 characters. Please try again.");
        } else {
            return true;
        }
        return false;
    }

    public static boolean validateDescription(String desc){
        if (desc.isEmpty()) {
            System.out.println("Description cannot be empty. Please try again.");
        } else if (desc.length() < 3 || desc.length() > 100) {
            System.out.println("Description must be between 3 and 100 characters. Please try again.");
        } else {
            return true;
        }
        return false;
    }

    public static boolean validateTarget(int target){
        if (target < 0) {
            System.out.println("Target amount cannot be negative. Please try again.");
        } else {
            return true;
        }
        return false;
    }
}
