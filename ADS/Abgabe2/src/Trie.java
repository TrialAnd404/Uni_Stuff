import java.io.File;
import java.io.FileReader;
import java.io.IOException;








public class Trie {

  static class TrieNode {

    SearchTree myTree;
    TrieNode maxNode;
    char value;
    long currentMax;

    public TrieNode(char c) {
      value = c;
      myTree = new SearchTree();
      maxNode = this;
    }

    public void add(char c, int count) {
      //c in SearchTree einfügen
      myTree.inc(c, count);
      if (myTree.getCount(c) > currentMax) {
        //count des Baumeintrages c ist höher als currentMax
        //maxNode referenz und currentMax aktualisieren
        maxNode = myTree.getNode(c);
        currentMax = myTree.getCount(c);
      }
    }

    public TrieNode get(char c){
      return myTree.getNode(c);
    }
  }

  public static class SearchTree {
    static class Node {

      Node left;
      Node right;
      char key;
      long count;
      TrieNode myTrieNode;

      // Node konstruktor
      public Node(char c, int count) {
        this.count = count;
        this.key = c;
        myTrieNode = new TrieNode(c);
      }

      public void incrementCount(int additionalCount) {
        count += additionalCount;
      }
    }

    Node root;

    public SearchTree() {
      root = null;
    }

    public void inc(char key, int count) {
      // FIXME
      if (root == null) {
        // Suchbaum root ist null, es gibt noch keinen Suchbaum
        root = new Node(key, count);
        //neue root erstellen
        return;
      }
      // root ist nicht null, von root aus nach key suchen
      // wenn schon existiert: incrementCount(value)
      Node tmp = root;
      while (tmp.key != key) {
        // nach key suchen
        if (key < tmp.key) {
          // key kleiner als tmp.key
          if (tmp.left == null) {
            //key existiert noch nicht
            Node newSearchTreeNode = new Node(key, count);
            //neue Node erstellen, linkes Blatt von tmp auf neue Node setzen
            tmp.left = newSearchTreeNode;
            return;
          }
          //auf linkes blatt gehen und weitersuchen
          tmp = tmp.left;
        } else {
          //key größer/gleich tmp key
          if (tmp.right == null) {
            //key existiert noch nicht
            Node newSearchTreeNode = new Node(key, count);
            //neue Node erstellen, rechtes Blatt von tmp auf neue Node setzen
            tmp.right = newSearchTreeNode;
            return;
          }
          //auf rechtes blatt gehen und weitersuchen
          tmp = tmp.right;
        }
      }
      //key wurde gefunden, count erhöhen
      tmp.incrementCount(count);
    }

    public long getCount(char key) {
      // FIXME
      if (root == null)
        //root ist NULL, return -1
        return 0L;

      //Setup: tmpNode auf Root aufsetzen
      Node tmp = root;
      //durch den Suchbaum iterieren
      while (tmp != null) {
        if (tmp.key == key)
          //char gefunden, return tmp.count
          return tmp.count;

        if (key < tmp.key) {
          //gesuchter key ist kleiner als key von tmp
          //--> key muss links von tmp sein,
          // tmp auf linkes kind von tmp weiterschalten
          tmp = tmp.left;
        } else {
          //--> key muss links von tmp sein,
          // tmp auf linkes kind von tmp weiterschalten
          tmp = tmp.right;
        }
      }
      //tmp ist NULL
      return 0L;
    }

    public TrieNode getNode(char key) {
      // FIXME
      if (root == null)
        //root ist NULL --> return NULL weil char dann ja nicht existieren kann
        return null;

      //Setup: tmpNode auf root aufsetzen
      Node tmp = root;
      //durch den Suchbaum iterieren
      while (tmp != null) {
        if (tmp.key == key)
          //char gefunden, return tmp
          return tmp.myTrieNode;
        if (key < tmp.key) {
          //gesuchter key ist kleiner als key von tmp
          //--> key muss links von tmp sein,
          // tmp auf linkes kind von tmp weiterschalten
          tmp = tmp.left;
        } else {
          //--> key muss rechts von tmp sein,
          // tmp auf rechtes kind von tmp weiterschalten
          tmp = tmp.right;
        }
      }
      //tmp ist NULL
      return null;
    }
  }

  // Trie-Klasse

  TrieNode root;

  public Trie() {
    root = new TrieNode('*');
  }

  //Trainingsquery
  public void add(String s, int count) {
    //tmpNode auf root des Tries aufsetzen
    TrieNode tmp = root;
    //über charArray iterieren und einzelne chars in Trie einfügen
    for (char c : s.toCharArray()) {
      //char wird in Suchbaum von tmp eingefügt
      tmp.add(c, count);
      //tmp weiterschalten auf vorher eingefügten char
      tmp=tmp.get(c);
    }
    //wortende anfügen
    tmp.add('*', count);
  }

  public String predict(String prefix) {
    // FIXME
    //Setup:
    // prefix zu charArray damit wir iterieren können
    //tempNode auf root, damit wir durch den Trie iterieren können
    char[] prefixChars = prefix.toCharArray();
    TrieNode tmp = root;
    String result="";
    if(tmp==null)
      return null;

    //durch chars iterieren
    for (char c : prefixChars) {
      /*
      if (tmp.get(c) == null) {
        //wir sind auf NULL getroffen
        // --> prefix existiert nicht (oder nicht komplett)
        return null;
      }*/
      //wir gehen zum nächsten TrieNode mit dem entsprechenden char
      tmp = tmp.get(c);
      if (tmp == null) {
        //wir sind auf NULL getroffen
        // --> prefix existiert nicht (oder nicht komplett)
        return null;
      }
    }
    //result wird vorbereitet, resultstrinng beginnt immer mit dem prefix
    result = prefix;
    //iterieren über rest des Tries bis wir * finden
    //[[ * markiert wortende ]]
    while (tmp.maxNode.value != '*') {
      if(tmp.maxNode==null) {
        //[[ nur für den fall der fälle... ]]
        return null;
      }
      //result wird um den char mit dem höchsten Value von tmp verlängert
      result += tmp.maxNode.value;
      //wir schalten tmp weiter auf den TrieNode vom char mit dem höchsten Value
      tmp = tmp.maxNode;
    }
    //return
    return result;
  }


  public void eval() throws IOException {
    // FIXME (siehe Aufgabe 2)
    //FileReader Bolierplate...
    FileReader reader = new FileReader(new File("").getAbsolutePath() + "/keyphrases.txt");
    int i;
    StringBuilder buffer = new StringBuilder();
    String string = "";
    //bis EOF lesen
    while ((i = reader.read()) != -1) {
      if (i == 59) {
        //string-teil gelesen, jetzt wird count gelesen
        string = buffer.toString();
        //neuer buffer für den count
        buffer = new StringBuilder();
        continue;
      }
      if (i == 10) {
        //zeile fertig gelesen, alles in den Trie adden
        this.add(string, Integer.parseInt(buffer.toString()));
        buffer = new StringBuilder();
        continue;
      }
      //neuen char an buffer anhängen
      buffer.append((char) i);
    }
    //EOF erreicht
    reader.close();
  }

  public static void main(String[] args) throws IOException {

    System.out.println("#########################################");
    System.out.println("Tests starting ...");
    System.out.println("#########################################");
    Trie t = new Trie();
    t.eval();
    System.out.println("trump : " + t.predict("trump"));
    System.out.println("german : " + t.predict("german"));
    System.out.println("mo : " + t.predict("mo"));
    System.out.println("paw : " + t.predict("paw"));
    System.out.println("secret : " + t.predict("secret"));
    System.out.println("best : " + t.predict("best"));
    System.out.println("pro : " + t.predict("pro"));
    System.out.println("small : " + t.predict("small"));
    System.out.println("snow : " + t.predict("snow"));
    System.out.println("soc : " + t.predict("soc"));
  }
}
