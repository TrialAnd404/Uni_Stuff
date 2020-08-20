import java.util.Iterator;

public class IterableString implements Iterable<Character> {
  String str;

  public static void main(String[] args) {
    for (char c : new IterableString("Hello world!")) {
      System.out.println(c);
    }
   // System.out.println("actually garbage");
  }

  public IterableString(String str) {
    this.str = str;
  }

  @Override
  public Iterator<Character> iterator() {
    return new StringIterator(str);
  }

  private class StringIterator implements Iterator<Character> {
    String str;
    int pos;

    StringIterator(String str) {
      this.str = str;
      pos = 0;
    }

    @Override
    public boolean hasNext() {
      return str.length() > pos;
    }

    @Override
    public Character next() {
      Character result = str.charAt(pos);
      pos++;
      return result;
    }
  }
}
