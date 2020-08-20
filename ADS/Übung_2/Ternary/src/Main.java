import java.util.Arrays;

public class Main {
  public static class TernarySearch {

    public static int ternarySearch(int[] a, int value) {
      return ternarySearch(a,0,a.length-1, value);
    }

    public static int ternarySearch(int[] a, int left, int right, int value) {
      int result_Search = 0;

      if (right < left)
        return -1;

      int innerLeft = left + (right - left) / 3;
      int innerRight = innerLeft + (right - left) / 3;

      if (a[innerLeft] == value)
        return innerLeft;

      if (a[innerRight] == value)
        return innerRight;

      if (a[innerLeft] > value)
        result_Search = ternarySearch(a, left, innerLeft - 1, value);

      if (a[innerRight] < value)
        result_Search = ternarySearch(a, innerRight + 1, right, value);

      if (a[innerLeft] < value && a[innerRight] > value)
        result_Search = ternarySearch(a, innerLeft + 1, innerRight - 1, value);

      return result_Search;
    }

    public static int ternarySearchRecursive(int[] a, int value) {
      int size = a.length;

      if (size == 1)
        if (value == a[0])
          return value;
        else
          return -1;

      int left = size / 3;
      int right = (size / 3) * 2;
      if (left == right)
        right++;

      if (value == a[left])
        return value;
      if (value == a[right])
        return value;

      int[] subArray;

      if (value < a[left]) {
        subArray = Arrays.copyOfRange(a, 0, left);
        return ternarySearch(Arrays.copyOfRange(a, 0, left), value);
      }

      if (value < a[right]) {
        subArray = Arrays.copyOfRange(a, left, right);
        return ternarySearch(subArray, value);
      }

      subArray = Arrays.copyOfRange(a, right, size);
      return ternarySearch(subArray, value);
    }

  }

  public static void main(String[] args) {
    int[] a = {1, 2, 3, 4, 5, 6, 7, 8};
    System.out.println(TernarySearch.ternarySearch(a, 5));
    System.out.println(TernarySearch.ternarySearch(a, 11));
    a = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    System.out.println(TernarySearch.ternarySearch(a, 1));
    System.out.println(TernarySearch.ternarySearch(a, 8));
    System.out.println(TernarySearch.ternarySearch(a, 6));
    a = new int[]{1};
    System.out.println(TernarySearch.ternarySearch(a, 2));
    System.out.println(TernarySearch.ternarySearch(a, 1));
    a = new int[]{1, 2};
    System.out.println(TernarySearch.ternarySearch(a, 5));
    System.out.println(TernarySearch.ternarySearch(a, 2));
    a = new int[]{1, 2, 3};
    System.out.println(TernarySearch.ternarySearch(a, 5));
    System.out.println(TernarySearch.ternarySearch(a, 2));
  }

}


