package draw;

import java.awt.*;
import java.util.ArrayList;

public class Line implements Drawable{
  int x1, x2, y1, y2;

  public Line(int x1,int x2, int y1, int y2){
    this.x1=x1;
    this.x2=x2;
    this.y1=y1;
    this.y2=y2;
  }

  @Override
  public void add(Drawable d) {

  }

  @Override
  public ArrayList<Drawable> getDrawables() {
    return null;
  }

  @Override
  public void draw(Graphics g) {
    g.drawLine(x1, y1, x2, y2);
  }

  @Override
  public void remove(Drawable d) {

  }

  @Override
  public void accept(Besucher b) {
    b.visit(this);
  }
}