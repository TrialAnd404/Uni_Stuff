package draw;

public interface Besucher{
  void visit(Container c);
  void visit(Line l);
  void visit(Rectangle r);
  void visit(Drawable d);
  //visit Drawable für Exceptions
}