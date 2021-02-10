package draw;

import java.awt.*;
import java.util.ArrayList;

public class Container implements Drawable{

  ArrayList<Drawable> drawables = new ArrayList<Drawable>();

  @Override
  public void add(Drawable d) {
    drawables.add(d);
  }

  @Override
  public ArrayList<Drawable> getDrawables() {
    return drawables;
  }

  @Override
  public void draw(Graphics g) {
    for(Drawable d: drawables)
      d.draw(g);
  }

  @Override
  public void remove(Drawable d) {
    if(drawables.contains(d))
      drawables.remove(d);
  }

  @Override
  public void accept(Besucher b) {
    b.visit(this);
  }

}
