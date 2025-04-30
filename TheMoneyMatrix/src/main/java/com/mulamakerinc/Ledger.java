package com.mulamakerinc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//I'm creating properties. They are private so they can only
// be accessed through methods (the getters)
public class Ledger {
    private LocalDateTime localDateTime;
    private String description;
    private String vendor;
    private double amount;
    private String accountType;

    public Ledger(LocalDateTime localDateTime, String accountType, double amount, String vendor, String description) {
        this.localDateTime = localDateTime;
        this.accountType = accountType;
        this.amount = amount;
        this.vendor = vendor;
        this.description = description;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public String getAccountType() {
        return accountType;
    }

    public double getAmount() {
        return amount;
    }

    public String getVendor() {
        return vendor;
    }

    public String getDescription() {
        return description;
    }
    public String goToCsv() {
        DateTimeFormatter ledgerFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm");
        return String.format("%s | %s | %s | %.2f | %s ", localDateTime.format(ledgerFormatter),
        getDescription(), getVendor(), getAmount(), getAccountType());
    }
//    @Override
//    public String toString() {
//        return String.format("Date: %s, %s, Description: %s, Vendor: %s. Amount: %.2f, " ,
//        localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),
//        description, vendor, amount);
////    }
@Override
public String toString() {
    DateTimeFormatter ledgerFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm");
    return "Date: " + localDateTime.format(ledgerFormatter)
            + "Description: " + description
            + "Vendor: "+ vendor
            + "Amount: " + String.format(
            "%.2f", amount);
    }
}
