package com.mangkyu.stream.Quiz5;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Quiz5 {

    private static final String[] STRING_ARR = {"aaa", "bb", "c", "dddd"};

    // 5.1 모든 문자열의 길이를 더한 결과를 출력하여라.
    public int quiz1() {
        return Arrays.stream(STRING_ARR)
                .mapToInt((s) -> s.length())
                .sum();
    }

    // 5.2 문자열 중에서 가장 긴 것의 길이를 출력하시오.
    public int quiz2() {
        return Arrays.stream(STRING_ARR)
                .mapToInt(String :: length)
                .max().orElseThrow(RuntimeException::new);
    }

    // 5.3 임의의 로또번호(1~45)를 정렬해서 출력하시오.
    public List<Integer> quiz3() {
        return Collections.emptyList();
    }

    // 5.4 두 개의 주사위를 굴려서 나온 눈의 합이 6인 경우를 모두 출력하시오.
    public List<Integer[]> quiz4() {
        Integer[] dice = new Integer[]{1,2,3,4,5,6};

        //나의 경우 위와 같이 배열을 만들어서 했지만
        //IntStream.rangeClosed(1, 6)와 같이 IntStream에서 숫자 범위를 정해서 stream을 만드는 방식을 지원해준다.
        return Arrays.stream(dice)
                .flatMap((i1) -> Arrays.stream(dice).map((i2) -> new Integer[]{i1,i2}))
                .filter((array) -> array[0] + array[1] == 6)
                .collect(Collectors.toList());

    }

}
