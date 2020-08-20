import java.util.Iterator;

public class IntRange implements Iterable<Integer> {
  int from;
  int to;
  int step;
  boolean infinite = false;

  public IntRange(int from, int to, int step) {
    this.from = from;
    this.to = to;
    this.step = step;

    new IntegerRangeIterator(from, to, step, infinite)
            .forEachRemaining(i -> System.out.println(i));
  }

  public IntRange(int from, int to) {
    this(from, to, 1);

  }

  public IntRange(int from) {
    this(from, Integer.MAX_VALUE, 1);
    infinite = true;
  }

  public IntRange() {
    this(0, Integer.MAX_VALUE, 1);
    infinite = true;
  }

  public Iterator<Integer> iterator() {
    return new IntegerRangeIterator(from, to, step, infinite);
  }

  private static class IntegerRangeIterator implements Iterator<Integer> {
    boolean infinite;
    int from;
    int to;
    int step;

    IntegerRangeIterator(int from, int to, int step, boolean infinite) {
      this.from = from;
      this.to = to;
      this.step = step;
      this.infinite = infinite;
    }

    @Override
    public boolean hasNext() {
  //    if(infinite) return true;
      if(step < 0) {
        if(from-to>=0){
          return true;
        }
      }
      if(step > 0){
        if(to-from>=0){
          return true;
        }
      }
      return false;
    }

    @Override
    public Integer next() {
      int result = from;
      from = from + step;
      return result;
    }
  }
}
