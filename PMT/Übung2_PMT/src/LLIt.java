import java.util.Iterator;

public class LLIt<A> implements Iterable<A> {
  final private A hd;
  final private LLIt<A> tl;

  public boolean isEmpty() {
    return hd == null && tl == null;
  }

  public LLIt(A hd, LLIt<A> tl) {
    this.hd = hd;
    this.tl = tl;
  }

  public LLIt() {
    this(null, null);
  }

  public A get(int i) {
    return i == 0 ? hd : tl.get(i - 1);
  }

  public int size() {
    if (isEmpty())
      return 0;
    return 1 + tl.size();
  }

  @SuppressWarnings("unchecked")
  static <A> LL<A> create(A... es){
    LL<A> result = new LL<A>();
    for (int i=es.length-1;i>=0;i--){
      result = new LL<A>(es[i],result);
    }
    return result;
  }

  @Override
  public String toString(){
    StringBuffer result = new StringBuffer("[");
    boolean first = true;
    for (LLIt<A> it = this;!it.isEmpty();it=it.tl){
      if (first){
        first = false;
      } else{
        result.append(", ");
      }
      result.append(it.hd);
    }
    result.append("]");
    return result.toString();
  }

  @Override
  public Iterator<A> iterator() {
    return new Iterator<A>(){

      LL<A> tmp = new LL<A>(hd, tl);

      @Override
      public boolean hasNext() {
        return isEmpty();
      }

      @Override
      public A next() {
        A result = tmp.hd;
        tmp=tmp.tl;
        return result;
      }
    };
  }
}