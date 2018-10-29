package tasks.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Sorter {
    public static void main( String[] args ) {

       //2. Создать массив и нескольки слов и отсортировать их с помощью метода sort по длине, слово что длинне должно быть на первом      месте.
        String[] array1 = {"мама", "мыла", "раму"};
        String[] array2 = {"я", "очень", "люблю", "java"};
        String[] array3 = {"мир", "труд", "май"};

        List<String[]> arrays = new ArrayList<>();
        arrays.add(array1);
        arrays.add(array2);
        arrays.add(array3);

        for(String[] arr : arrays) {
            Arrays.sort( arr, new Comparator<String>() {
                @Override
                public int compare( String o1, String o2 ) {
                    return o2.length() - o1.length();
                }
            } );
        }
        arrays.forEach( arr -> System.out.println( Arrays.toString( arr ) ) );

        System.out.println();
        System.out.println("The same using lambda:");
        String[] array12 = {"мама", "мыла", "раму"};
        String[] array22 = {"я", "очень", "люблю", "java"};
        String[] array32 = {"мир", "труд", "май"};

        List<String[]> arrays2 = new ArrayList<>();
        arrays2.add(array12);
        arrays2.add(array22);
        arrays2.add(array32);
        arrays2.forEach( arr -> Arrays.sort(arr, ( o1, o2 ) -> o2.length() - o1.length() ));
        arrays2.forEach( arr -> System.out.println( Arrays.toString( arr ) ) );

        // 3. Сделать тоже самое , но с сортировкой по количеству согласных в каждом слове, в котором больше , тот первый.
        System.out.println();
        System.out.println("Sorting by numbers of the consonants in the words:");
        String[] array13 = {"мама", "мыла", "раму"};
        String[] array23 = {"я", "очень", "люблю", "java"};
        String[] array33 = {"мир", "труд", "май"};
        String[] array43 = {"ok", "good", "from"};

        List<String[]> arrays3 = new ArrayList<>();
        arrays3.add(array13);
        arrays3.add(array23);
        arrays3.add(array33);
        arrays3.add(array43);

        String regexAllVowels = "[аоиеёэыуюяaeiou]";
        arrays3.forEach( arr -> Arrays.sort(arr, ( o1, o2 ) -> o2.replaceAll( regexAllVowels, "" ).length()
                                                                              - o1.replaceAll( regexAllVowels, "" ).length()));
        arrays3.forEach( arr -> System.out.println( Arrays.toString( arr ) ) );
    }
}
