package tasks.collections.regexes;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RegexTester {
    public static void main( String[] args ) {
        String regex = "(\\+38\\s)?\\(?0((91)|(98)|(71)|(93))\\)?\\s\\d{2,3}[-\\s]\\d{2}[-\\s]\\d{2,3}";
        String clearingRegex = "[\\s\\+\\-\\(\\)]";
        String testString = "+38 (093) 345 56 78, (091) 234-56-78, 091 234-45-67, 091 89 78 892, , 786954 68 54, 38 095 2345678";
        List<String> findings = new ArrayList<>(  );
        Pattern pattern = Pattern.compile( regex );
        Matcher matcher = pattern.matcher( testString );
        while(matcher.find()){
            findings.add(testString.substring( matcher.start(), matcher.end() ));
        }
        findings.forEach( System.out::println );
        List<String> phoneNumbers = findings.stream().map(phone -> phone.replaceAll(clearingRegex, ""))
                                    .collect(Collectors.toList());
        phoneNumbers.forEach( System.out::println );
    }
}
