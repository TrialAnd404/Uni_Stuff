import java.util.stream.Stream;

public class main {

  static Stream<String> turboHallo(int x){
    return Stream
        .iterate("hallo",(next)->next)
        .limit(x);
  }

  static String concatenate(Stream<String> stream){
    return stream
        .reduce(
            "",
            (result, next)-> result=result+next,
            (firstresult, secondresult) -> firstresult+=secondresult
        );
  }

  static int countL(Stream<String> stream){
    return stream
        .reduce(
            0,
            (result, next)->{
              int thisWord=0;
              for(Character c : next.toCharArray()){
                if(c=='l')
                  thisWord++;
              }
              return result+thisWord;
            },
            (firstResult, secondResult)->firstResult+=secondResult
        );
  }

  public static void main(String[] args){
    Stream meinStream = turboHallo(5);
    System.out.println(countL(meinStream));

    Stream meinZweiterStream = turboHallo(5);
    System.out.println(concatenate(meinZweiterStream));
  }
}
