package com.mulamakerinc;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class App {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            boolean running = true;
            while (running) {
                showMainMenu();
                String selectedMenuOption = scanner.nextLine().toUpperCase();

                switch (selectedMenuOption.toUpperCase()) {
                    case "D":
                        addDeposit();
                        break;
                    case "P":
                        makePayment();
                        break;
                    case "L":
                        showLedgerMenu();
                        break;
                    case "X":
                        System.out.println("See ya next time! ");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid selection. Please try again. ");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void showMainMenu() {
        System.out.println("Welcome To The Money Matrix :) ");
        System.out.println("---------------------");
        System.out.println("\nHow may we assist you?");
        System.out.println(" D) Add Deposit");
        System.out.println(" P) Make Payment");
        System.out.println(" L) Ledger");
        System.out.println(" X) Exit");
        System.out.print("Enter here: ");
    }

    private static void promptReturnToMainMenu() {
        System.out.println("\nPress Enter to return to the main menu...");
        scanner.nextLine();
    }

    public static void writeTransaction(String description, String vendor, double amount, String accountType) {
        try (FileWriter fileWriter = new FileWriter("src/data/transactions.csv", true)) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

                String date = now.format(dateFormat);
                String time = now.format(timeFormat);

                String line = String.format("%s|%s|%s|%s|%.2f|%s", date, time, description, vendor, amount, accountType);

                bufferedWriter.write(line);
                bufferedWriter.newLine();

                System.out.println("✅ Transaction saved!");
            }
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addDeposit() {
        System.out.println("\nAdd Deposit");
        System.out.println("---------------------");

        System.out.print("Enter vendor: ");
        String vendor = scanner.nextLine();

        System.out.println("Please enter your deposit amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter account type(checking/savings):  ");
        String accountType = scanner.nextLine();
        // use for make payment to write payment to file
        writeTransaction("Deposit", vendor, amount, accountType);

        promptReturnToMainMenu();
    }

    public static void makePayment() {
        System.out.println("\nMake Payment");
        System.out.println("---------------------");

        System.out.print("Enter vendor: ");
        String vendor = scanner.nextLine();

        System.out.println("Please enter your payment amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        // setting amount to absolute value with a negative so that
        // even if you forget the negative it will read as negative
        amount = Math.abs(amount);
        amount = -amount;

        String accountType = "checking";
        // use for make payment to write payment to file
        writeTransaction("Payment", vendor, amount, accountType);
        promptReturnToMainMenu();
    }

    public static void showLedgerMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\nLedger");
            System.out.println("---------------------");
            System.out.println("\nWhat ledger would you like to view?");
            System.out.println("A) All");
            System.out.println("D) Deposits");
            System.out.println("P) Payments ");
            System.out.println("R) Reports ");
            System.out.println("H) Home ");
            System.out.println("Enter here: ");

            String selectedMenuOption = scanner.nextLine().toUpperCase();

            switch (selectedMenuOption) {
                case "A":
                    runAll();
                    break;
                case "D":
                    runDeposits();
                    break;
                case "P":
                    runPayments();
                    break;
                case "R":
                    viewReports();
                    break;
                case "H":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid selection. Please try again.");
            }
        }
    }

    public static void viewReports() {
        boolean viewReports = true;

        while (viewReports) {
            System.out.println("\nReports");
            System.out.println("---------------------");
            System.out.println("1- Month to Date");
            System.out.println("2- Previous Month");
            System.out.println("3- Year To Date");
            System.out.println("4- Previous Year");
            System.out.println("5- Search by Vendor");
            System.out.println("0- Back to Ledger");
            System.out.println("Enter here: ");

            String selectedReportOption = scanner.nextLine();

            switch (selectedReportOption) {
                case "1":
                    runMonthToDate();
                    break;
                case "2":
                    runPreviousMonth();
                    break;
                case "3":
                    runYearToDate();
                    break;
                case "4":
                    runPreviousYear();
                    break;
                case "5":
                    runSearchByVendor();
                    break;
                case "0":
                    viewReports = false;
                    break;
                default:
                    System.out.println("Invalid selection.");
            }
        }
    }

    private static void promptReturnToLedgerMenu() {
        System.out.println("\nPress Enter to return to the ledger menu...");
        scanner.nextLine();
    }

    public static void runAll() {
        System.out.println("\nALL TRANSACTIONS");
        System.out.println("---------------------");

        List<String[]> transactions = readAllTransactions();
        Collections.reverse(transactions);  // Newest first
        for (String[] t : transactions) {
            System.out.printf("%s %s | %s | %s | $%.2f | %s\n",
                    t[0], t[1], t[2], t[3], Double.parseDouble(t[4]), t[5]);
        }
        promptReturnToLedgerMenu();
    }

    private static List<String[]> readAllTransactions() {
        List<String[]> transactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/data/transactions.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 6) {  // Only include well-formed rows
                    transactions.add(parts);
                }
            }
        } catch (IOException e) {
            System.out.println("⚠️ Error reading transactions: " + e.getMessage());
        }
        return transactions;
    }

    private static void runDeposits() {
        System.out.println("\nDEPOSIT TRANSACTIONS");
        System.out.println("---------------------");

        List<String[]> transactions = readAllTransactions();
        Collections.reverse(transactions);

        for (String[] t : transactions) {
            String description = t[2].trim(); // t[2] = description field
            if (description.equalsIgnoreCase("Deposit")) {
                System.out.printf("%s %s | %s | %s | $%.2f | %s\n",
                        t[0], t[1], t[2], t[3], Double.parseDouble(t[4]), t[5]);
            }
        }
        promptReturnToLedgerMenu();
    }

    public static void runPayments() {
        System.out.println("\nPAYMENT TRANSACTIONS");
        System.out.println("---------------------");

        List<String[]> transactions = readAllTransactions();
        Collections.reverse(transactions);

        for (String[] t : transactions) {
            String description = t[2].trim(); // t[2] = description field
            if (description.equalsIgnoreCase("Payment")) {
                System.out.printf("%s %s | %s | %s | $%.2f | %s\n",
                        t[0], t[1], t[2], t[3], Double.parseDouble(t[4]), t[5]);

            }
        }
        promptReturnToLedgerMenu();
    }

    // Try/catch and or buffered reader to pull info from the transaction.csv file
    //START HERE WITH CRAIG
    private static void runMonthToDate() {
        System.out.println("\nMONTH TO DATE");
        System.out.println("---------------------");

        List<String[]> transactions = readAllTransactions();

        String currentMonth = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        for (String[] t : transactions) {
            String dateField = t[0].trim();

            // If date includes time, split it
            String dateOnly = dateField.contains(" ") ? dateField.split(" ")[0] : dateField;

            if (dateOnly.startsWith(currentMonth)) {
                printTransaction(t);
            }
        }
        promptReturnToLedgerMenu();
    }

    private static void printTransaction(String[] t) {
    }

    private static void runPreviousMonth() {
        System.out.println("\nPREVIOUS MONTH");
        System.out.println("---------------------");
        LocalDateTime now = LocalDateTime.now().minusMonths(1);
        String prevMonth = now.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        List<String[]> transactions = readAllTransactions();
        for (String[] t : transactions) {
            if (t[0].startsWith(prevMonth)) {
                printTransaction(t);
            }
        }

        promptReturnToLedgerMenu();
    }

    private static void runYearToDate() {
        System.out.println("\nYEAR TO DATE");
        System.out.println("---------------------");

        String currentYear = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy"));

        List<String[]> transactions = readAllTransactions();
        for (String[] t : transactions) {
            if (t[0].startsWith(currentYear)) {
                printTransaction(t);
            }
        }

        promptReturnToLedgerMenu();
    }

    private static void runPreviousYear() {
        System.out.println("\nPREVIOUS YEAR");
        System.out.println("---------------------");
        String prevYear = LocalDateTime.now().minusYears(1).format(DateTimeFormatter.ofPattern("yyyy"));

        List<String[]> transactions = readAllTransactions();
        for (String[] t : transactions) {
            if (t[0].startsWith(prevYear)) {
                printTransaction(t);
            }
        }

        promptReturnToLedgerMenu();
    }

    private static void runSearchByVendor() {
        System.out.println("\nSEARCH BY VENDOR");
        System.out.println("---------------------");
        System.out.print("Enter vendor name to search: ");
        String keyword = scanner.nextLine().toLowerCase();

        List<String[]> transactions = readAllTransactions();
        for (String[] t : transactions) {
            String vendor = t[3].toLowerCase();
            if (vendor.contains(keyword)) {
                printTransaction(t);
            }
        }
        promptReturnToLedgerMenu();

    }
}