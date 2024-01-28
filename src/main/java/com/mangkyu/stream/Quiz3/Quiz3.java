package com.mangkyu.stream.Quiz3;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Quiz3 {

    private static final List<Integer> numbers1 = Arrays.asList(1, 2, 3);
    private static final List<Integer> numbers2 = Arrays.asList(3, 4);

    public static void main(String[] args) {
        int a = numbers1.stream()
                .flatMap((n) -> numbers2.stream().map((m) -> new Integer[]{n,m}))
                .mapToInt((array) -> array[0] * array[1])
                .max().orElse(0);
    }

    // 3.1 모든 숫자 쌍의 배열 리스트를 반환하여라.
    // ex) numbers1 = [1,2,3], numbers2 = [3,4] -> [(1,3), (1,4), (2,3), (2,4), (3,3), (3,4)]
    public List<Integer[]> quiz1() {
        //map에서의 반환값이 Stream의 제네릭 타입이 된다.
        //그렇기에 number2.stream().map() 내부에서 반환되는 값은 new Integer[]이기 떄문에
        //Stream<Integer[]>을 반환하고 이에 대한 반환값이 number1.stream().map()의 내부에서 반환하는 값이 되기 떄문에
        //Stream<Stream<Integer[]>>가 되는 것
        Stream<Stream<Integer[]>> st =  numbers1.stream()
                .map((n) -> numbers2.stream().map((m) -> new Integer[]{n,m}));

        //동작 방식
        // number1 -> 1, 2, 3
        // number2 -> 3, 4
        // n = 1일 떼 number2.stream().map()에 의해 m에 대한 반복 발생 1,3 한번 1,4 한번
        // n = 2일 때도 동일하게 반복
        // flatMap을 안쓰고 map을 써버리면 위 st의 데이터타입을 보면 알겠지만 2중 Stream구조가 되어버린다.
        // flatMap은 이러한 2중 구조를 1중 구조로 낮추는 "평탄화" 연산을 해주기 때문에 아래와 같이
        // Stream<Integer[]>의 타입이 반환된다.
        Stream<Integer[]> st2 = numbers1.stream()
                .flatMap((n) -> numbers2.stream().map((m) -> new Integer[]{n,m}));

        return numbers1.stream()
                .flatMap((n) -> numbers2.stream().map((m) -> new Integer[]{n,m}))
                .collect(Collectors.toList());
    }

    // 3.2 모든 숫자 쌍의 곱이 가장 큰 값을 반환하여라.
    // ex) numbers1 = [1,2,3], numbers2 = [3,4] -> 12
    public int quiz2() {

        //map이 아닌 mapToInt를 사용하는 이유는
        //map을 사용하면 Stream<Integer>의 타입으로 되어서 max 메서드의 인자로 Comparator를 받기 때문이다.
        //mapToInt를 사용하면 IntStream으로 변환되어 max, min, sum 메서드를 인자 없이 사용이 가능하다.
        //참고로 반환값은 옵셔널로 감싸지기 때문에 빈 값일 경우에 반환할 값(아래와 같이 orElse로 0을 반환)이나
        //orElseThrow로 반환할 예외를 설정해주어야 함
        return numbers1.stream()
                .flatMap((n) -> numbers2.stream().map((m) -> new Integer[]{n,m}))
                .mapToInt((array) -> array[0] * array[1])
                .max().orElse(0);
    }

}
