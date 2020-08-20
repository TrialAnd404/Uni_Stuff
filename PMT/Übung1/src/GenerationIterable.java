import java.util.Iterator;
import java.util.function.Function;

public class GenerationIterable<A> implements Iterable<A> {
  A a;
  Function<A,A> f;
  public GenerationIterable(A a, Function<A,A> f){
    this.a=a;
    this.f=f;
    iterator().forEachRemaining((i)->System.out.println("forEach :"+i));
  }


  @Override
  public Iterator<A> iterator() {
    return new FunctionIterator<A>(f,a);
  }

  private class FunctionIterator<A> implements Iterator<A>{
    A a;
    Function<A,A> f;
    int plsStopMe =5;

    FunctionIterator(Function<A,A> f, A a){
      this.f=f;
      this.a=a;

    }

    @Override
    public boolean hasNext() {
      plsStopMe--;
      return plsStopMe>0;
    }

    @Override
    public A next() {
      A result = a;
      System.out.println("f.apply: "+f.apply(a));
      a=f.apply(a);

      return result;
    }
  }


}
