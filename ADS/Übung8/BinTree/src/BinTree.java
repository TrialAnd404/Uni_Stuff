import java.util.ArrayList;
import java.util.List;

public class BinTree {

  Node root;

  static class Node {

    Node left=null;
    Node right=null;
    int value;

    Node(int value) {
      this.value = value;
    }

    Node(int value, Node left, Node right) {
      this.value = value;
      this.left = left;
      this.right = right;
    }


    public ArrayList<Node> getChildren(){
      ArrayList<Node> result = new ArrayList<Node>();
      if(!(right==null)){
        result.add(right);
      }
      if(!(left==null)){
        result.add(right);
      }
      return result;
    }

  }


  public BinTree() {
    root = null;
  }

  public BinTree(Node root) {
    this.root = root;
  }

  public int height() {
    return height(root);
  }

  private int height(Node n){
    int bestHeight=0;
    for( Node child : n.getChildren()){
      if(height(child)>bestHeight)
        bestHeight=height(child);
    }
    return bestHeight+1;
  }

  public String pathToMax() {
    int max = findMax(root, root.value);

  }

  public String pathToMax(Node node, int max, String path) {
    if(node.value==max)return "";
    if(node.getChildren().isEmpty())
      return "";
    String addedPath="";
    if(!(node.left==null))
      addedPath+=(pathToMax(node.left, max, path+"->left"));
    if(!(node.right==null))
      addedPath+=(pathToMax(node.left, max, path+"->right"));
    return addedPath;
  }

  public int findMax(Node node, int currentMax){
    int newMax=currentMax;
    if(node.value>currentMax)
      newMax=node.value;
    for(Node child : node.getChildren()){
      newMax = findMax(child, newMax);
    }
    return newMax;
  }

  public boolean isCompleteOrAlmostComplete() {
    // FIXME
  }

  public static void main(String[] args) {
  }

}
