package tasks.collections.regexes;

public class RegexTester {
    public static void main( String[] args ) {
        String regex1 = "(\\+38\\s)?\\(?0((91)|(98)|(71)|(93))\\)?\\s\\d{2,3}[-\\s]\\d{2}[-\\s]\\d{2,3}";
        String testString = "+38 (093) 345 56 78, (091) 234-56-78, 091 234-45-67, 091 89 78 892, , 786954 68 54, 38 095 2345678";
    }
}
