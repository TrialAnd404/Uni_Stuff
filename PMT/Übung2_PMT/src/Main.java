public class Main {
  public static void main(String[] args){
    LL<Integer> liste1 = generateIntList(5);
    LL<Integer> liste2 = generateIntList(3);
    LL<String> liste3 =generateStringList(4);
    LL<Integer> list4 = generateIntList(5);

    System.out.println("L1 (IntList): "+liste1.toString());
    System.out.println("L2 (IntList): "+liste2.toString());
    System.out.println("L3 (StringList): "+liste3.toString());

    System.out.println("append L1+L2: "+liste1.append(liste2).toString());
    System.out.println("L1 take 3: "+liste1.take(3).toString());
    System.out.println("L1 last: "+liste1.last());
    System.out.println("L2 Prefix von L1: "+liste2.isPrefixOf(liste1));
    System.out.println("L3 Prefix von L1: "+liste2.isPrefixOf(liste1));
    System.out.println("L1 drop 2: "+liste1.drop(2).toString());
    System.out.println("L1 sublist 2-4: "+liste1.sublist(2,4).toString());
  }

  public static LL<String> generateStringList(Integer length){
    if(length>0){
      int newLength=length-1;
      return new LL<String>(length.toString(), generateStringList(newLength));
    }
    else
      return new LL<String>();
  }
  public static LL<Integer> generateIntList(int length){
    if(length>0){
      int newLength=length-1;
      return new LL<Integer>(length, generateIntList(newLength));
    }
    else
      return new LL<Integer>();
  }

}
