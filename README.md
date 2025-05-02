# capstone-1
Welcome to The Money Matrix! This is a budgeting app that lets you make deposits, payments, and view filtered lists of banking information. This project goes over all the things I have learned so far in Java via Pluralsight. I learned a lot and got humbled many times but I am super excited about my progress so far. 
## What It Does

This app lets you:
- Add deposits or payments
- Save transactions to a `.csv` file
- View all transactions in a ledger
- Filter by:
  - Deposits only
  - Payments only
  - Month-to-date
  - Previous month
  - Year-to-date
  - Previous year
  - Vendor search

It helps users track where their money goes — and where it comes from!


## Interesting Code

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
                
 private static void printTransaction(String[] t) {
        System.out.printf("%s %s | %s | %s | $%.2f | %s\n",
                t[0], t[1], t[2], t[3], Double.parseDouble(t[4]), t[5]);
                
## I'm proud of this piece of code because my report menu was not reading from my csv file and I discovered how to fix it. After staying up multiple nights this week and being frustrated I figured out it was because I had the 1st line of the file with the format "date|time|description|vendor|amount|account_type" and it didn't read the file because that line was in the way. After doing research I figured out how to skip lines in code. Also my report screen wasn't working because I had an empty printtransaction menthod that I didn't discover until Thursday. There were many ups and downs of progress. I'm glad I was able to push myself.   ##
---
