import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import java.util.LinkedList;
import java.util.NoSuchElementException;


public class BracketChecker {


  public static class Stack<T> {

    LinkedList<T> list;

    public Stack() {
      list = new LinkedList<T>();
    }

    public void push(T e) {
      list.addFirst(e);
    }

    public void pop() throws NoSuchElementException {
      list.removeFirst();
    }

    public T top() throws NoSuchElementException {
      return list.getFirst();
    }

    public boolean isEmpty() {
      return list.isEmpty();
    }

  }


  public static boolean check(String toCheck) {

    BracketChecker.Stack<Character> stack = new BracketChecker.Stack<Character>();


    for (int i = 0; i < toCheck.length(); i++) {
      char current = toCheck.charAt(i);
      if ((Character.toString(current).matches("\\(")) ||
          (Character.toString(current).matches("\\{")) ||
          (Character.toString(current).matches("\\["))) {
        stack.push(toCheck.charAt(i));
      } else if (Character.toString(current).matches("\\)")) {
        if(Character.toString(stack.top()).matches("\\(")){
          stack.pop();
        }
        else
          return false;
      } else if (Character.toString(current).matches("\\}")) {
        if(Character.toString(stack.top()).matches("\\{")){
          stack.pop();
        }
        else
          return false;
      } else if (Character.toString(current).matches("\\]")) {
        if(Character.toString(stack.top()).matches("\\[")){
          stack.pop();
        }
        else
          return false;
      }
    }
    if(stack.isEmpty())
      return true;
    return false;

  }


  private static String[] readStringArray(String filename) {
    // open a file, read its lines, return them as an array.

    try {

      ArrayList<String> lines = new ArrayList<String>();

      // Kein Scanner, wÃ¤re viel zu langsam
      // Erzwingen von UTF-8 (sonst komische Ergebnisse unter Windows)
      Reader in = new InputStreamReader(new FileInputStream(filename),
          "UTF-8");

      BufferedReader reader = new BufferedReader(in);

      String s;
      while ((s = reader.readLine()) != null) {
        // Ignoriere Leerzeilen und Kommentare
        if (s.length() != 0 && s.charAt(0) != '#') {
          lines.add(s);
        }
      }

      reader.close();

      String[] result = new String[lines.size()];
      return lines.toArray(result);

    } catch (IOException e) {

      String msg = "I/O-Fehler bei " + filename + "\n" + e.getMessage();
      throw new RuntimeException(msg);

    }

  }


  public static void checkFile(String filepath) {

    String[] lines = readStringArray(filepath);
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < lines.length; ++i) {
      sb.append(lines[i]);
    }
    String content = sb.toString();

    System.out.format("File %s correct? %b", filepath, check(content));
  }


  public static void main(String[] args) {
    checkFile("src/files/testfile");
    checkFile("src/files/testfile2");
  }

}
