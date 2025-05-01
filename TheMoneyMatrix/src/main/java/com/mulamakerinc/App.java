package com.mulamakerinc;

import java.io.*;
import java.time.LocalDateTime;
import java.time.YearMonth;
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
        Collections.reverse(transactions);
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
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // skips the header date|time|description|vendor|amount|account_type
                    continue;
                }

                String[] parts = line.split("\\|");
                if (parts.length == 6) {
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

        // Reverse the list to show newest transactions first
        Collections.reverse(transactions);
        boolean foundDeposit = false;

        for (String[] t : transactions) {
            String description = t[2].trim();

            //  If it's a deposit, print it
            if (description.equalsIgnoreCase("Deposit")) {
                System.out.printf("%s %s | %s | %s | $%.2f | %s\n",
                        t[0], t[1], t[2], t[3], Double.parseDouble(t[4]), t[5]);
                foundDeposit = true;
            }
        }
        if (!foundDeposit) {
            System.out.println("No deposit transactions found.");
        }
        promptReturnToLedgerMenu();
    }

    private static void runPayments() {
        System.out.println("\nPAYMENT TRANSACTIONS");
        System.out.println("---------------------");

        List<String[]> transactions = readAllTransactions();
        Collections.reverse(transactions);

        boolean foundPayment = false;

        // Step 4: Loop through each transaction
        for (String[] t : transactions) {
            String description = t[2].trim();

            if (description.equalsIgnoreCase("Payment")) {
                System.out.printf("%s %s | %s | %s | $%.2f | %s\n",
                        t[0], t[1], t[2], t[3], Double.parseDouble(t[4]), t[5]);
                foundPayment = true;
            }
        }

        if (!foundPayment) {
            System.out.println("No payment transactions found.");
        }

        promptReturnToLedgerMenu();
    }

    private static void runMonthToDate() {
        System.out.println("\nMONTH TO DATE REPORT");
        System.out.println("---------------------");

        List<String[]> transactions = readAllTransactions();

        Collections.reverse(transactions);

        String currentMonth = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        boolean foundThisMonth = false;

        for (String[] t : transactions) {
            String rawDate = t[0].trim();
            String dateOnly = rawDate.contains(" ") ? rawDate.split(" ")[0] : rawDate;

            if (dateOnly.startsWith(currentMonth)) {
                System.out.printf("%s %s | %s | %s | $%.2f | %s\n",
                        t[0], t[1], t[2], t[3], Double.parseDouble(t[4]), t[5]);
                foundThisMonth = true;
            }
        }
        if (!foundThisMonth) {
            System.out.println("No transactions found for this month.");
        }

        promptReturnToLedgerMenu();
    }

    private static void printTransaction(String[] t) {
        System.out.printf("%s %s | %s | %s | $%.2f | %s\n",
                t[0], t[1], t[2], t[3], Double.parseDouble(t[4]), t[5]);
    }
// NOT HAVING ANYTHING INSIDE THE METHOD IS THE REASON I WAS UP TILL 4AM THIS WEEK!!!!

    private static void runPreviousMonth() {
        System.out.println("\nPREVIOUS MONTH REPORT");
        System.out.println("---------------------");

        YearMonth previousMonth = YearMonth.now().minusMonths(1);

        List<String[]> transactions = readAllTransactions();
        Collections.reverse(transactions);

        boolean found = false;

        for (String[] t : transactions) {
            try {
                // Parse the date from the first field and time from the second
                LocalDateTime transactionDate = LocalDateTime.parse(t[0] + "T" + t[1]);

                YearMonth transactionMonth = YearMonth.from(transactionDate);
                if (transactionMonth.equals(previousMonth)) {
                    System.out.printf("%s %s | %s | %s | $%.2f | %s\n",
                            t[0], t[1], t[2], t[3], Double.parseDouble(t[4]), t[5]);
                    found = true;
                }
            } catch (Exception e) {

            }
        }

        if (!found) {
            System.out.println("No transactions from the previous month.");
        }

        promptReturnToLedgerMenu();
    }

    private static void runYearToDate() {
        System.out.println("\nYEAR TO DATE REPORT");
        System.out.println("---------------------");

        LocalDateTime startOfYear = LocalDateTime.of(LocalDateTime.now().getYear(), 1, 1, 0, 0);
        LocalDateTime now = LocalDateTime.now();

        List<String[]> transactions = readAllTransactions();
        Collections.reverse(transactions);

        boolean found = false;

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        for (String[] t : transactions) {
            try {
                LocalDateTime transactionDate = LocalDateTime.parse(
                        t[0].trim() + "T" + t[1].trim(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
                );

                if (!transactionDate.isBefore(startOfYear) && !transactionDate.isAfter(now)) {
                    printTransaction(t);
                    found = true;
                }

            } finally {

            }
        }

        if (!found) {
            System.out.println("No transactions found for this year so far.");
        }

        promptReturnToLedgerMenu();
    }

    private static void runPreviousYear() {
        System.out.println("\nPREVIOUS YEAR REPORT");
        System.out.println("---------------------");

        String lastYear = String.valueOf(LocalDateTime.now().getYear() - 1);

        List<String[]> transactions = readAllTransactions();
        Collections.reverse(transactions);

        boolean found = false;

        for (String[] t : transactions) {
            String datePart = t[0].trim();
            String yearPart = datePart.contains("-") ? datePart.split("-")[0] : "";

            if (yearPart.equals(lastYear)) {
                printTransaction(t);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No transactions from last year.");
        }

        promptReturnToLedgerMenu();
    }

    private static void runSearchByVendor() {
        System.out.println("\nSEARCH BY VENDOR");
        System.out.println("---------------------");

        System.out.print("Enter vendor name to search: ");
        String userInput = scanner.nextLine().toLowerCase();

        List<String[]> transactions = readAllTransactions();
        Collections.reverse(transactions);

        boolean found = false;

        for (String[] t : transactions) {
            String vendor = t[3].toLowerCase();

            if (vendor.contains(userInput)) {
                printTransaction(t);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No transactions found for vendor: " + userInput);
        }

        promptReturnToLedgerMenu();
    }
}