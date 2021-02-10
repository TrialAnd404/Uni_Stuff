package draw;

import java.util.ArrayList;
import java.util.Collection;

public class GetLines implements Besucher{

  ArrayList<Drawable> Lines = new ArrayList<Drawable>();

  public GetLines(){}

  @Override
  public void visit(Container c) {
    for(Drawable d : c.getDrawables())
      d.accept(this);
  }

  @Override
  public void visit(Line l) {
    Lines.add(l);
  }

  @Override
  public void visit(Rectangle r) {
  }

  @Override
  public void visit(Drawable d) {
    System.out.println("Kennen wir nicht (aus GetLines)");
  }

}
