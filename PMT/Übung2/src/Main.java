public class Main {
  public static void main(String[] args){
    int[] bestCase = {1,1,2};
    int[] worstCase = {1,2,3,3};
    int[] mediumWorstCase = new int[10];
    for(int i=0; i< 10; i++){
      mediumWorstCase[i]=i;
    }
    mediumWorstCase[9]=8;
    for(int i=0; i<mediumWorstCase.length; i++) {
      System.out.println(mediumWorstCase[i]);
    }
    int[] longWorstCase = new int[200000];
    for(int i=0; i< 199999; i++){
      longWorstCase[i]=i;
    }
    longWorstCase[199999]=199998;

   /* SearchDups.runTest(bestCase);
    SearchDups.runTest(worstCase);
    SearchDups.runTest(mediumWorstCase);
   */ SearchDups.runTest(longWorstCase);
  }
}
