package com.mulamakerinc;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class App {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            boolean running = true;
            while (running) {
                showMainMenu();
                String selectedMenuOption = scanner.nextLine().toUpperCase();

                switch (selectedMenuOption) {
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
        try (FileWriter fw = new FileWriter("src/data/transactions.csv", true)) {
            try (BufferedWriter bw = new BufferedWriter(fw)) {

                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

                String date = now.format(dateFormat);
                String time = now.format(timeFormat);

                String line = String.format("%s|%s|%s|%s|%.2f|%s", date, time, description, vendor, amount, accountType);
                bw.write(line);
                bw.newLine();

                System.out.println("✅ Transaction saved!");
            }
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addDeposit() {
        System.out.println("\nAdd Deposit");
        System.out.println("---------------------");

        System.out.println("Please enter your deposit amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter vendor (where it's from): ");
        String vendor = scanner.nextLine();

        System.out.print("Is this for checking or savings? ");
        String accountType = scanner.nextLine();

        writeTransaction("Deposit", vendor, amount, accountType);

        promptReturnToMainMenu();
    }

    public static void makePayment() {
        System.out.println("\nMake Payment");
        System.out.println("---------------------");
        System.out.println();
        System.out.println();
        promptReturnToMainMenu();
    }

    public static void  showLedgerMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\nLedger");
            System.out.println("---------------------");
            System.out.println("\nWhat ledger would you like to view?");
            System.out.println("A- All");
            System.out.println("D- Deposits ");
            System.out.println("P- Payments ");
            System.out.println("R- Reports ");
            System.out.println("L- Return to the Ledger Menu.");
            System.out.println("Enter here: ");

            String selectedMenuOption = scanner.nextLine();

            switch (selectedMenuOption) {
                case "A":
                    //may need to go in Ledger window
                    runAll();
                    break;
                case "2":
                    //may need to go in ledger window
                    runDeposits();
                    break;
                case "3":
                    runPayments();
                    break;
                case "4":
                    runReports();
                    break;
                case "5":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid selection. Please try again.");
            }
        }
    }

    private static void runReports() {
    }

    private static void promptReturnToLedgerMenu() {
            System.out.println("\nPress Enter to return to the ledger menu...");
            scanner.nextLine();
        }

        public static void runAll() {
            System.out.println("\n");
            System.out.println("---------------------");
            System.out.println();
            System.out.println();
            promptReturnToLedgerMenu();
        }

        private static void runDeposits() {
            System.out.println("\n");
            System.out.println("---------------------");
            System.out.println();
            System.out.println();
            promptReturnToLedgerMenu();
        }

        public static void runPayments() {
            System.out.println("\n");
            System.out.println("---------------------");
            System.out.println();
            System.out.println();
            promptReturnToLedgerMenu();

//        private static void runReports() {
//            System.out.println("\nTransactions within the last 24 hours.");
//            System.out.println("---------------------");
//            System.out.println();
//            System.out.println();
//            promptReturnToLedgerMenu();
//            }
            }
        }

