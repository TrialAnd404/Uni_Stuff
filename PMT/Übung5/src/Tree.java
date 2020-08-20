import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.*;

public class Tree<E> {
  final E element;
  final List<Tree<E>> children;
  private Tree<E> parent=null;


  public Tree<E> getParent() {
    return parent;
  }

  public void setParent(Tree<E> p) {
    if (null!=parent)throw new RuntimeException("Multiple assignment to parent node.");
    this.parent = p;
  }

  @SafeVarargs
  public Tree(E element, Tree<E>... children) {
    this.element = element;
    this.children = List.of(children);
    this.children.parallelStream().forEach(child->child.setParent(this));
  }

  public int size(){
    return
        children.parallelStream().reduce(0, (r,c)->c.size()+r,(x,y)->x+y)+1;
  }

  @Override
  public String toString() {
    return element.toString()+children.toString();
  }

  String toLaTeX() {
    var result = new StringBuffer();
    result.append("\\begin{tikzpicture}[sibling distance=10em,"
        + "every node/.style = {shape=rectangle, rounded corners,"
        + "draw, align=center,top color=white, bottom color=blue!20}]]");

    result.append("\\");
    toLaTeXAux(result);

    result.append(";\n");
    result.append("\\end{tikzpicture}");
    return result.toString();
  }

  void toLaTeXAux(StringBuffer result) {
    result.append("node {"+element+"}");
    children.stream().forEach(child -> {
      result.append("\n  child {");
      child.toLaTeXAux(result);
      result.append("}");
    });
  }

  String toXML() {
    var result = new StringBuffer();
    result.append("<?xml version=\"1.0\"?>\n");
    toXMLAux(result);
    return result.toString();
  }

  void toXMLAux(StringBuffer result) {
    result.append("<node> <element>"+element+"</element>");
    children.stream().forEach(child -> {
      result.append("\n  <child>");
      child.toXMLAux(result);
      result.append("</child>");
    });
    result.append("</node>");
  }

  List<Tree<E>> auntsAndUncles(){
    var opa = parent.parent;
    return opa.children.parallelStream()
        .filter(c->c!=parent).collect(Collectors.toList());
  }

  List<E> cousins(){
    List<E> result = new ArrayList<>();
    if (parent==null||parent.parent==null) return result;
    auntsAndUncles()
        .stream()
        .forEach(cs -> cs.children.stream().forEach(c->result.add(c.element)));
    return result;
  }


  public void forEach(Consumer<? super E> con) {
    //TODO
    con.accept(element);
    for ( Tree child : children)
      child.forEach(con);
  }


  public boolean contains(Predicate<? super E> pred) {
    //TODO
    if(pred.test(element))
      return true;
    for(Tree child : children)
      return child.contains(pred);
    return false;
  }

  public List<E> fringe() {
    var result = new ArrayList<E>();
    fringe(result);
    return result;
  }
  public void fringe(List<E> result) {
    result.add(element);
    for(Tree child : children)
      child.fringe(result);
  }

  public List<E> ancestors(){
    var result = new ArrayList<E>();
    ancestors(result);
    return result;
  }
  public void ancestors(List<E> result){
    result.add(parent.element);
    parent.ancestors(result);
  }

  public List<E> siblings(){
    var result = new ArrayList<E>();
    siblings(result);
    return result;
  }

  public void siblings(List<E> result) {
    if (parent == null || parent.children.isEmpty())
      return;
    parent.children.forEach(all -> {
      if (!all.equals(this))
        result.add(all.element);
    });
  }

  public List<E> pathTo(E elem) {
    var result = new ArrayList<E>();
    pathTo(elem,result);
    return result;
  }

  public void pathTo(E elem, List<E> result) {
    if (element.equals(elem)) {
      result.add(element);
      return;
    }

    for (Tree<E> child : children) {
      child.pathTo(elem, result);

      if (!result.isEmpty()) {
        result.add(0, element);
        return;
      }
    }
  }

  public <R> Tree<R> map(Function<? super E, ? extends R> f) {
    List<Tree<R>> newChildren = new ArrayList<>();
    for (Tree<E> child : children) {
      newChildren.add(child.map(f));
    }
    Tree<R>[] newChildrenArray = new Tree[newChildren.size()];
    for (int i = 0; i < newChildren.size(); i++) {
      newChildrenArray[i] = newChildren.get(i);
    }
    return new Tree<R>(f.apply(element), newChildrenArray);
  }
}