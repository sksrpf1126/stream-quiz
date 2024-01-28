package com.mangkyu.stream.Quiz1;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Quiz1 {

    // 1.1 각 취미를 선호하는 인원이 몇 명인지 계산하여라.
    public Map<String, Integer> quiz1() throws IOException {

        List<String[]> csvLines = readCsvLines();

        // 1. 문자열배열을 대상으로 연산하는 스트림이 생성
        //Stream<String[]> stream = csvLines.stream();

        // 2. 문자열 배열중 취미의 문자열을 가지고 있는 1번째 인덱스에 replaceAll을 통해 \\s(공백) 부분은 ""로 대체하며, String 타입으로 반환
        //Stream<String> stream2 = stream.map((line) -> line[1].replaceAll("\\s",""));

        // 3. flatMap 내부의 반환값은 Stream<String[]> 이지만, flatMap에 의해서 1차원의 형태인 Stream<String>으로 변환
        // 즉, Arrays.stream(new String[]{"축구",야구","농구"}) => Stream<String>타입이며, 스트림을 통해 또다른 Stream을 반환하고 있다.
        // 아래 stream3Test에서 일반적인 map을 사용하면 반환된 값의 데이터 타입이 Stream<Stream<String>>의 형태인 것을 알 수 있다.
        // flatMap은 "평탄화"의 역할을 하기 때문에 반환된 Stream<String>을 String으로 평탄화 해주기 때문에 stream3와 같은 데이터 타입을 가질 수 있게 한다.
        //Stream<Stream<String>> stream3Test = stream2.map(hobby -> Arrays.stream(hobby.split(":")));
        //Stream<String[]> stream3Test2 = stream2.map(hobby -> hobby.split(":"));
        //Stream<String> stream3 = stream2.flatMap(hobby -> Arrays.stream(hobby.split(":")));


        // 4. toMap은 오버로딩의로 인해 3개의 메서드가 존재하지만, 여기서 사용한 것은 3개의 매개변수가 존재하는 메서드를 사용한다.
        // 1번째 매개변수 : KeyMapper로써 Map에서 Key값이 될 값을 반환해주면 된다.
        // 2번째 매개변수 : ValueMapper로써 value값이 될 값을 반환해주면 되는데, 문제에서는 같은 취미의 수를 구하는 것이므로
        // 3번째 단계에서 하나씩 반환되는 취미로 Key값을, 그리고 Value는 1로 세팅해둔다.
        // 2번째 매개변수의 이해를 위해 아래의 코드 첨부
        //         List<String> words = Arrays.asList("apple", "banana", "orange");
        //
        //        Map<Integer, String> lengthToUppercaseMap = words.stream()
        //                .collect(Collectors.toMap(String::length, s -> s.toUpperCase()));
        // 위에선 문자열의 길이를 Key값으로, 문자열을 대문자로 변환한 후에 value값으로 저장하여 사용하지만
        // 여기선 Key값이 취미의 이름을, value값은 그냥 1로 고정한다. (1로 고정하는 이유는 3번째 매개변수에서 설명)
        // 3번째 매개변수는 중복된 키값이 들어올 경우 처리할 방법을 정의해 두는 것으로 3번째 매개변수가 없는 메서드를 사용할 때에는
        // 일반적으로 뒤에 온 값으로 덮어씌운다.
        // 여기선 기존에 있던 oldValue의 값에 새롭게 2번째 매개변수에서 할당한 1값을 더함으로써 수량을 더해간다.
        //stream3.collect(Collectors.toMap((hobby) -> hobby, hobby -> 1, (oldValue, newValue) -> oldValue += newValue));

        return  csvLines.stream()
                    .map((line) -> line[1].replaceAll("\\s", ""))
                    .flatMap((hobby) -> Arrays.stream(hobby.split(":")))
                    .collect(Collectors.toMap((hobby) -> hobby, (hobby) -> 1, (oldValue, newValue) -> oldValue += newValue));
    }

    // 1.2 각 취미를 선호하는 정씨 성을 갖는 인원이 몇 명인지 계산하여라.
    public Map<String, Integer> quiz2() throws IOException {
        List<String[]> csvLines = readCsvLines();

        return csvLines.stream()
                .filter((line) -> line[0].startsWith("정"))
                .map((line) -> line[1].replaceAll("\\s", ""))
                .flatMap((hobby) -> Arrays.stream(hobby.split(":")))
                .collect(Collectors.toMap((hobby) -> hobby, (hobby) -> 1, (oldValue, newValue) -> ++oldValue));
    }

    // 1.3 소개 내용에 '좋아'가 몇번 등장하는지 계산하여라.
    public int quiz3() throws IOException {
        List<String[]> csvLines = readCsvLines();

        return csvLines.stream()
                .mapToInt((line) -> countLikesString(line[2], 0))
                .sum();
    }

    private int countLikesString(String str, int startIdx) {
        int idx = str.indexOf("좋아", startIdx);
        if(idx >= 0) {
            return 1 + countLikesString(str, idx + 2); //찾은 인덱스에서 +2를 해야 "좋아" 이후의 인덱스부터 탐색
        }
        return 0;
    }

    private List<String[]> readCsvLines() throws IOException {
        CSVReader csvReader = new CSVReader(new FileReader(getClass().getResource("/user.csv").getFile()));
        csvReader.readNext();
        return csvReader.readAll();
    }

}
