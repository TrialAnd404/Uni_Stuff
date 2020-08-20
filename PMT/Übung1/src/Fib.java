import java.util.Iterator;
import java.math.BigInteger;
import java.util.function.Consumer;

public class Fib implements Iterable<BigInteger> {

  public static void main(String[] args){
    new Fib().forEach(x-> System.out.println(x));
  }

  @Override
  public Iterator<BigInteger> iterator() {
    return new FibonacciIterator();
  }

  private class FibonacciIterator implements Iterator<BigInteger> {
    BigInteger i;
    BigInteger j;
    boolean letsGetItStarted=true;
    int keeprunning=5;
    public FibonacciIterator() {
      i= BigInteger.valueOf(0);
      j= BigInteger.valueOf(0);
    }
    @Override
    public boolean hasNext() {
      if(keeprunning>0){
        keeprunning--;
        return true;
      }
      return false;
    }

    @Override
    public BigInteger next() {
      BigInteger helper;
      if(letsGetItStarted) {
        //j&i sind die zahlen die hochgezählt werden
        j= BigInteger.valueOf(1);
        helper= BigInteger.valueOf(0);
        letsGetItStarted=false;
      } else {
        helper=i.add(j);
        i=j;
        j=helper;
        helper=i;
      }
      return helper;
    }
  }

}