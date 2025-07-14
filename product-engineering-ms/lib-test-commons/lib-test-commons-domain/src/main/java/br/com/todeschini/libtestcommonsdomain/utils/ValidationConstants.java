package br.com.todeschini.libtestcommonsdomain.utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ValidationConstants {

    public static String blank = "";
    public static String smallerThan = "a";
    public static Integer negativeInt = -1;
    public static Float negativeFloat = -1.0f;
    public static Double negativeDouble = -1.0;
    public static BigDecimal negativeBigDecimal = BigDecimal.valueOf(-1.500);
    public static LocalDate pastDate = LocalDate.of(1000, 1, 1);
    public static LocalDate futureDate = LocalDate.of(3000, 1, 1);
    public static LocalDateTime pastDateTime = LocalDateTime.of(1000, 1, 1, 1, 1);
    public static LocalDateTime futureDateTime = LocalDateTime.of(3000, 1, 1, 1, 1);
    public static String invalid = "invalid";
    public static String specialCharacters = "*/+?!@#$%¨&*()";
    public static String largerThan = "a".repeat(700);
}
