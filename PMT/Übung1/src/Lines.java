import java.util.Iterator;

public class Lines implements Iterable<String> {
  static String NEW_LINE = System.getProperty("line.separator");
  String text;

  public Lines(String str) {
    this.text = str;
  }

  @Override
  public Iterator<String> iterator() {
    return new LedZepplin(text);
  }

  private class LedZepplin implements Iterator<String> {
    String text;
    String[] words;
    int pos;

    public LedZepplin(String text) {
      this.text = text;
      words = this.text.split("\\n");
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

  public static void main(String[] args) {
    for (String s : new Lines("hallo" + NEW_LINE + "welt!" + NEW_LINE + "wie" + NEW_LINE + "geht" + NEW_LINE + "es"))
      System.out.println(s);
  }
}
