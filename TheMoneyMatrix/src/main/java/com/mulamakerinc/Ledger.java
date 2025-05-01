//package com.mulamakerinc;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
////I'm creating properties. They are private so they can only
//// be accessed through methods (the getters)
//public class Ledger {
//    private LocalDateTime localDateTime;
//    private String description;
//    private String vendor;
//    private double amount;
//    private String accountType;
//
//    public LocalDateTime getLocalDateTime() {
//        return localDateTime;
//    }
//
//    public void setLocalDateTime(LocalDateTime localDateTime) {
//        this.localDateTime = localDateTime;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getVendor() {
//        return vendor;
//    }
//
//    public void setVendor(String vendor) {
//        this.vendor = vendor;
//    }
//
//    public double getAmount() {
//        return amount;
//    }
//
//    public void setAmount(double amount) {
//        this.amount = amount;
//    }
//
//    public String getAccountType() {
//        return accountType;
//    }
//
//    public void setAccountType(String accountType) {
//        this.accountType = accountType;
//    }
//
//    public String goToCsv() {
//        DateTimeFormatter ledgerFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm");
//        return String.format("%s | %s | %s | %.2f | %s ", localDateTime.format(ledgerFormatter),
//        getDescription(), getVendor(), getAmount(), getAccountType());
//    }
//
//@Override
//public String toString() {
//    DateTimeFormatter ledgerFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm");
//    return "Date: " + localDateTime.format(ledgerFormatter)
//            + "Description: " + description
//            + "Vendor: "+ vendor
//            + "Amount: " + String.format(
//            "%.2f", amount);
//    }
//}
