package com.mangkyu.stream.Quiz4;


import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Quiz4 {

    private static List<Transaction> transactions;

    public Quiz4() {
        Trader kyu = new Trader("Kyu", "Seoul");
        Trader ming = new Trader("Ming", "Gyeonggi");
        Trader hyuk = new Trader("Hyuk", "Seoul");
        Trader hwan = new Trader("Hwan", "Busan");

        transactions = Arrays.asList(
                new Transaction(kyu, 2019, 30000),
                new Transaction(kyu, 2020, 12000),
                new Transaction(ming, 2020, 40000),
                new Transaction(ming, 2020, 7100),
                new Transaction(hyuk, 2019, 5900),
                new Transaction(hwan, 2020, 4900)
        );
    }

    // 4.1 2020년에 일어난 모든 거래 내역을 찾아 거래값을 기준으로 오름차순 정렬하라.
    public List<Transaction> quiz1() {
        
        //sorted의 Comparator 인자를 Comparator.comparingInt(Transaction::getValue)
        //또는 Comparator.comparing(Transaction::getValue)으로 변경 가능
        return transactions.stream()
                .filter((transaction -> transaction.getYear() == 2020))
                .sorted((o1, o2) -> o1.getValue() - o2.getValue())
                .collect(Collectors.toList());
    }

    // 4.2 거래 내역이 있는 거래자가 근무하는 모든 도시를 중복 없이 나열하라.
    public List<String> quiz2() {

        return transactions.stream()
                .map((transaction -> transaction.getTrader().getCity()))
                .distinct()
                .collect(Collectors.toList());
    }

    // 4.3 서울에서 근무하는 모든 거래자를 찾아서 이름순서대로 정렬하라.
    public List<Trader> quiz3() {

        //객체를 대상으로 distinct가 가능한 이유는 클래스에 equals와 hashcode를 재정의함으로써
        //name과 city값이 동일하다면 동일한 객체라 판단하게끔 구현이 되어있다.
        //실제로 equals와 hashcode 메서드를 주석처리하면 중복제거가 되지가 않는다.
        return transactions.stream()
                .filter((transaction -> transaction.getTrader().getCity() == "Seoul"))
                .map((transaction -> transaction.getTrader()))
                .distinct()
                .sorted(Comparator.comparing(trader -> trader.getName()))
                .collect(Collectors.toList());
    }

    // 4.4 모든 거래자의 이름을 구분자(",")로 구분하여 정렬하라.
    public String quiz4() {
        return transactions.stream()
                .map((transaction -> transaction.getTrader().getName()))
                .distinct()
                .sorted()
                .collect(Collectors.joining(","));
    }

    // 4.5 부산에 거래자가 있는지를 확인하라.
    public boolean quiz5() {

        return transactions.stream()
                .anyMatch((transaction -> transaction.getTrader().getCity().equals("Busan")));
    }

    // 4.6 서울에 거주하는 거래자의 모든 거래 금액을 구하라.
    public List<Integer> quiz6() {
        return transactions.stream()
                .filter((transaction) -> transaction.getTrader().getCity().equals("Seoul"))
                .map((transaction -> transaction.getValue()))
                .collect(Collectors.toList());
    }

    // 4.7 모든 거래 내역중에서 거래 금액의 최댓값과 최솟값을 구하라. 단, 최댓값은 reduce를 이용하고 최솟값은 stream의 min()을 이용하라.
    public Integer[] quiz7() {
        Integer[] array = new Integer[2];

        array[0] = transactions.stream()
                    .map((transaction -> transaction.getValue()))
                    .reduce(0, (v1, v2) -> Integer.max(v1, v2));

        array[1] = transactions.stream()
                    .mapToInt((transaction -> transaction.getValue()))
                    .min().orElse(0);

        return array;
    }

}
