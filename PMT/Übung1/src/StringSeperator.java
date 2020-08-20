import java.util.ArrayList;
import java.util.Iterator;

public class StringSeperator implements Iterable<String> {
  String text;

  public StringSeperator(String text) {
    this.text = text;
    new Rasierklingenliebe(text)
            .forEachRemaining(i -> System.out.println(i));
  }

  @Override
  public Iterator<String> iterator() {
    return new Rasierklingenliebe(text);
  }

  private class Rasierklingenliebe implements Iterator<String> {
    String text;
    String[] words;
    int pos;

    public Rasierklingenliebe(String text) {
      this.text = text;
      words = this.text.split("\\s+");
      pos = 0;
    }

    @Override
    public boolean hasNext() {
      return words.length > pos;
    }

    @Override
    public String next() {
      String result = words[pos];
      pos++;
      return result;
    }
  }
}