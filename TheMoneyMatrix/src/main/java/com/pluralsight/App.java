package com.pluralsight;

import java.util.Scanner;

public class App {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        while (running){
            showMainMenu();
            String selectedMenuOption= scanner.nextLine().toLowerCase();

            switch (selectedMenuOption){
                case "d":
                    addDeposit();
                    break;
                case "p":
//                    addVisit();
                    break;
                case "l":
//                    runReports();
                    break;
                case "x":
                    System.out.println("Goodbye");
                    running =false;
                    break;
                default:
                    System.out.println("Invalid menu option. Try again.");
            }
        }
    }

    public static void showMainMenu(){
        System.out.println("Welcome To The Money Matrix :) ");
        System.out.println("---------------------");
        System.out.println("\nWhat do you want to do?");
        System.out.println(" D) Add Deposit");
        System.out.println(" P) Make Payment");
        System.out.println(" L) Ledger");
        System.out.println(" X) Exit Application");
        System.out.print("Enter command: ");
    }

    private static void promptReturnToMenu() {
        System.out.println("\nPress Enter to return to the main menu...");
        scanner.nextLine();
    }

    public static void addDeposit(){
        System.out.println("\nAdd Deposit");
        System.out.println("---------------------");
        System.out.println();
        System.out.println();
        promptReturnToMenu();
    }

    public static void makePayment(){
        System.out.println("\nMake Payment");
        System.out.println("---------------------");
        System.out.println();
        System.out.println();
        promptReturnToMenu();
    }
}
