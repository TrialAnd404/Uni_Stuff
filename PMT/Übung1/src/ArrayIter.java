import java.util.Iterator;

public class ArrayIter<A> implements Iterable<A> {
  A[] as;

  ArrayIter(A[] as) {
    this.as = as;
    new ArrayIterator(as)
            .forEachRemaining(i -> System.out.println(i));
  }

  public Iterator<A> iterator() {
    return new ArrayIterator(as);
  }

  private class ArrayIterator implements Iterator<A> {
    int length;
    int currentPos;
    A[] data;

    ArrayIterator(A[] as) {
      this.data = as;
      this.length = as.length;
      this.currentPos = 0;
    }

    @Override
    public boolean hasNext() {
      return currentPos < length;
    }

    @Override
    public A next() {
      A result = data[currentPos];
      currentPos++;
      return result;
    }
  }
}
