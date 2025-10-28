package com.karat.cheatsheet;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// https://docs.oracle.com/en/java/javase/24/docs/api/index.html

public class DatetimeAndStringsCheatSheet {
    public static void main(String[] args) {
        // ✅ Instant — best for UTC timestamps (e.g. "2023-10-24T19:30:00Z")
        Instant parsedInstant = Instant.parse("2023-10-24T19:30:00Z");
        Instant nowInstant = Instant.now();
        long minutesAgo = Duration.between(parsedInstant, nowInstant).toMinutes();
        System.out.println("Minutes ago: " + minutesAgo);

        // ✅ Convert Instant to LocalDateTime (system zone)
        LocalDateTime localFromInstant = parsedInstant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        System.out.println("Local from Instant: " + localFromInstant);

        // ✅ ZonedDateTime — handles time zones explicitly
        ZonedDateTime utcTime = ZonedDateTime.parse("2023-10-24T19:30:00Z");
        ZonedDateTime londonTime = utcTime.withZoneSameInstant(ZoneId.of("Europe/London"));
        System.out.println("London time: " + londonTime);

        // ✅ Duration math — between Instants or LocalDateTimes
        Duration d = Duration.between(parsedInstant, nowInstant);
        System.out.println("Duration: " + d.toMinutes() + " minutes");

        // ✅ ChronoUnit — for date-based math
        LocalDate startDate = LocalDate.of(2025, 10, 20);
        LocalDate endDate = LocalDate.of(2025, 10, 25);
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        System.out.println("Days between: " + daysBetween);

        // ✅ LocalDateTime — flexible, zone-less
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime later = now.plusHours(3);
        boolean isBefore = now.isBefore(later);
        System.out.println("Is before: " + isBefore);

        // ✅ Truncating time — round down to hour/day
        LocalDateTime roundedHour = now.truncatedTo(ChronoUnit.HOURS);
        System.out.println("Rounded to hour: " + roundedHour);

        // ✅ Custom format parsing — e.g. "24-10-2025 21:55"
        String customTs = "24-10-2025 21:55";
        DateTimeFormatter customFmt = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime parsedCustom = LocalDateTime.parse(customTs, customFmt);
        System.out.println("Parsed custom: " + parsedCustom);

        // ✅ Formatting output
        DateTimeFormatter outFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = startDate.format(outFmt);
        System.out.println("Formatted date: " + formattedDate);

        // ✅ Regex — extract from logs or strings
        String logLine = "ERROR: Disk full at 14:32";
        Matcher timeMatcher = Pattern.compile("\\d{2}:\\d{2}").matcher(logLine);
        if (timeMatcher.find()) {
            System.out.println("Extracted time: " + timeMatcher.group());
        }

        // ✅ Regex — validate email
        String email = "user@example.com";
        boolean isValidEmail = Pattern.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$", email);
        System.out.println("Valid email: " + isValidEmail);

        // ✅ String operations — mutation, slicing, checks
        String s = "  Hello World!  ";
        System.out.println("Trimmed: " + s.trim());
        System.out.println("Uppercase: " + s.toUpperCase());
        System.out.println("Replaced: " + s.replace("World", "Java"));
        System.out.println("Substring: " + s.substring(2, 7)); // "Hello"
        System.out.println("Contains 'Hello': " + s.contains("Hello"));

        // ✅ StringBuilder — efficient mutation
        StringBuilder sb = new StringBuilder("audit");
        sb.append("-trail");
        sb.insert(0, "governance-");
        sb.replace(0, 10, "traceable");
        sb.reverse();
        System.out.println("StringBuilder: " + sb.toString());

        // ✅ Null-safe string check
        String maybeNull = args.length > 0 ? args[0] : null;
        if (maybeNull != null && !maybeNull.isEmpty()) {
            System.out.println("Safe to use: " + maybeNull);
        }
    }
}