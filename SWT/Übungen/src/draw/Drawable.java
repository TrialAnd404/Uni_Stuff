package draw;

import java.awt.*;
import java.util.ArrayList;

public interface Drawable {
  void add(Drawable d);
  ArrayList<Drawable> getDrawables();
  void draw(Graphics g);
  void remove(Drawable d);

  void accept(Besucher b);
}
