import java.util.Spliterator;
import java.util.function.Consumer;

public class LL<A> implements Li<A> {
  private A hd;
  private LL<A> tl;

  public A head(){
    return hd;
  }
  public LL<A> tail(){
    return tl;
  }

  LL<A> take(int i){
    if(isEmpty()|| i==0)
      return new LL<A>();
    else {
      i=i-1;
      return new LL<A>(hd, tl.take(i));
    }
  }

  LL<A> drop(int i){
    if(isEmpty())
      return new LL<A>();
    if(i==0)
      return this;
    else
      return tl.drop(--i);
  }

  public boolean isEmpty() {
    return hd == null && tl == null;
  }

  public LL(A hd, LL<A> tl) {
    this.hd = hd;
    this.tl = tl;
  }

  public LL() {
    this(null, null);
  }

  @Override
  public Spliterator<A> getSpliterator() {
    return new MySplitter(LL.this);
  }

  private class MySplitter implements Spliterator<A> {
    LL<A> current;
    boolean splitMe=true;

    public MySplitter(LL<A> current){
      this.current=current;
    }

    private MySplitter(LL<A> current, boolean splitMe){
      this.current=current;
      this.splitMe = splitMe;
    }

    @Override
    public boolean tryAdvance(Consumer<? super A> consumer) {
      if(current.isEmpty())return false;
      consumer.accept(hd);
      current=current.tail();
      return true;
    }

    @Override
    public Spliterator<A> trySplit() {
      if(current.isEmpty())
        return null;
      if(splitMe) {
        var result = new MySplitter(current.take(4), false);
        current=current.drop(4);
      }
      return null;
    }

    @Override
    public long estimateSize() {
      return Integer.MAX_VALUE;
    }

    @Override
    public int characteristics() {
      return 0;
    }
  }
}