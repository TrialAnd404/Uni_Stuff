package draw;

public class CountRectangles implements Besucher{

  int anzahl = 0;

  public CountRectangles(){

  }

  @Override
  public void visit(Container c) {
    for(Drawable d : c.getDrawables())
      d.accept(this);
  }

  @Override
  public void visit(Line l) {

  }

  @Override
  public void visit(Rectangle r) {
    anzahl++;
  }

  @Override
  public void visit(Drawable d) {
    System.out.println("Kennen wir nicht (aus CountRectangles)");
  }

}
