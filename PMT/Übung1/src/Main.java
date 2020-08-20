import java.util.function.Function;

public class Main {
    public static void main(String[] args){
        IntRange intIter1 = new IntRange(10,1,-1);
        IntRange intIter2 = new IntRange(117,130);
        IntRange intIter4 = new IntRange(2147483640, Integer.MAX_VALUE);
        IntRange intIter3 = new IntRange(2147483640);

        Integer[] testArray1 = new Integer[]{1, 2, 3, 4, 5};
        String[] testArray2 = new String[]{"hallo","welt","foo"};
        ArrayIter arrayIter1 = new ArrayIter(testArray1);
        ArrayIter arrayIter2 = new ArrayIter(testArray2);
/*
        String text = "haha das ist\nso kacke";
        String[] words = text.split("\\n");
        String text2 = "\n";
        String[] words2 = text2.split("\\n");
        for (String w : words)
            System.out.println(w);
        for (String w : words2)
            System.out.println(w);

        StringSeperator stringSep1 = new StringSeperator("DIE NUTTEN SIND RAFFINIERT");
        StringSeperator stringSep2 = new StringSeperator("DIE NEHMEN DIE KOHLE AN");
*/
        int i=1;
        Function<Integer, Integer> func = x -> {
            System.out.println("I DID STUFF!!!");
            x=x+2;
            return x;
        };
        GenerationIterable func1 = new GenerationIterable(1, func);
        i = func.apply(i);

    }
}
