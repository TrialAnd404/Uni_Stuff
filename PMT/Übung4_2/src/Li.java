import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface Li<A> {
  Spliterator<A> getSpliterator();

  default Stream<A> stream() {
    return StreamSupport.stream(getSpliterator(), false);
  }

  default Stream<A> parallelStream() {
    return StreamSupport.stream(getSpliterator(), true);
  }

}