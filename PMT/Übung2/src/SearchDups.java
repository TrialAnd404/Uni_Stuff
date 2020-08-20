public class SearchDups {

  public static int actions=0;

  static boolean hasDups(int[] xs) {
    for(int i=0; i<xs.length-1; i++){
      if ( linearSearch(xs, i, xs[i]) )
        return true;
    }
    return false;
  }

  private static boolean linearSearch(int[] xs, int start, int v) {
    for(int i =start+1; i<xs.length; i++) {
      actions++;
      if (xs[i] == v)
        return true;
    }
    return false;
  }

  static boolean hasDupsFaster(int[] xs) {
    for(int i=0; i<xs.length-1; i++){
      actions++;
      if(xs[i]==xs[i+1]) {
        return true;
      }
    }
    return false;
  }


  // as requested on the exercise sheet, we
  // run a duplicate check and record runtime.
  public static void runTest(int[] xs) {
    boolean result;
    long startTime;
    long endTime;

    // run slow version
    startTime = System.currentTimeMillis();
    result = hasDups(xs);
    endTime = System.currentTimeMillis();
    System.out.println(startTime);
    System.out.println(endTime);
    System.out.format(
            "[QUADRATIC] %d values: %d ms. Found duplicate? %b\n",
            xs.length, (endTime - startTime), result);
    System.out.println(actions+ " actions");

    actions=0;
    startTime = System.currentTimeMillis();
    result = hasDupsFaster(xs);
    endTime = System.currentTimeMillis();

    System.out.println(startTime);
    System.out.println(endTime);
    System.out.format(
            "[LINEAR] %d values: %d ms. Found duplicate? %b\n",
            xs.length, (endTime - startTime), result);
    System.out.println(actions+ " actions");
  }

}