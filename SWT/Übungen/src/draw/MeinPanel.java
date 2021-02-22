package draw;

import draw.Drawable;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MeinPanel extends JPanel {

  Drawable d;

  public MeinPanel(Drawable d) {
    this.d = d;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    d.draw(g);
  }

  public static void main(String[] args) {
    JFrame f = new JFrame();
    f.setTitle("Beispiel");
    f.setSize(450,300);
    Container c = new Container();
    Line l = new Line(0, 50, 100, 150);
    Rectangle r = new Rectangle(100, 150, 200, 250);
    c.add(l);
    c.add(r);
    MeinPanel p = new MeinPanel(c);
    f.add(p);
    f.setVisible(true);

    CountRectangles cR = new CountRectangles();
    GetLines gL = new GetLines();

    c.accept(gL);
    c.accept(cR);

    System.out.println(cR.anzahl);
    System.out.println(gL.Lines.size());
  }

}
