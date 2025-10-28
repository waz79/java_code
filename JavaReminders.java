package com.karat.tenChallenges;

import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Account implements Comparable<Account> {
    private int id;
    private String name;
    private LocalDate dob;
    private int age;

    public Account(int id, String name, LocalDate dob) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.age = Period.between(dob,LocalDate.now()).getYears();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public LocalDate getDob() {
        return dob;
    }

    @Override
    public String toString() {
        return "Account [id=" + id + ", name=" + name + ", age=" + age + ", dob="+dob+"]";
    }

    @Override
    public int compareTo(Account o) {
        // return name.compareTo(o.name);
        return Integer.compare(age, o.age);
    }
}

public class JavaReminders {
    private static int n = 0;


    public static void incrementN() {
        n++;
    }

    public static int getN() {
        return n;
    }

    public static void main(String[] args) {
        List<Account> accounts = new ArrayList<>();
        accounts = generateAccounts(5);
        Collections.sort(accounts);
        for (Account account : accounts) {
            System.out.println(account);
        }
    }

    private static List<Account> generateAccounts(int num_accs) {
        List<Account> accounts = new ArrayList<>();
        Map<Integer, Account> nameAgeMap = new HashMap<>();
        Account acc;
        for (int j = 0; j < num_accs; j++) {
            acc = generateAccount();
            accounts.add(acc);
            nameAgeMap.put(j, acc);
        }
        // before returning accounts list output the map
        List<Map.Entry<Integer, Account>> mapList = new ArrayList<>(nameAgeMap.entrySet());
        mapList.sort(Comparator.comparing(entry -> entry.getValue().getName()));
        for (var entry : mapList) {
            System.out.println(entry.getValue().getName()+" is "+entry.getValue().getAge()+" years old.");
        }
        return accounts;
    }

    private static Account generateAccount() {
        String[] names = {"Ava", "Liam", "Sophia", "Noah", "Isabella","Ethan", "Mia", "Lucas", "Amelia", "Oliver",
                          "Charlotte", "Elijah", "Harper", "James", "Evelyn", "Ben", "Abigail", "Henry", "Emily", "Alex"};
        int id = JavaReminders.getN();
        JavaReminders.incrementN();
        String name = names[rand(8)];
        int year = 1940 + rand(71); // 2010 - 1940 + 1 = 71
        int month = 1 + rand(12);
        YearMonth yearMonth = YearMonth.of(year, month);
        int day = 1 + rand(yearMonth.lengthOfMonth());
        LocalDate dob = LocalDate.of(1940 + rand(71), 1 + rand(12), day);
        Account account = new Account(id, name, dob);
        return account;
    }

    private static int rand(int multiplier){
        return (int) (Math.random()*multiplier);
    }
}
