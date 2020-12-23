import java.util.ArrayList;
import java.util.List;

public class Helper {

  public static final int PRECISION = 10;

  enum dir {
    UP, LEFT, RIGHT, DOWN
  }

  static class coord {
    public double X;
    public double Y;

    coord(double X, double Y) {
      this.X = X;
      this.Y = Y;
    }
  }

  public static void main(String[] args) {
    Integer coordCnt = 0;
    double total = 0;
    dir direction = dir.UP;
    ArrayList<coord> coords = new ArrayList<coord>();
    ArrayList<Double> distances = new ArrayList<Double>();
    distances.add(0.0);
    coord Ursprung = new coord(0, 0);
    coords.add(Ursprung);

    crvLeft(1, coords, direction, coordCnt);
    coordCnt+=10;
    direction = dir.LEFT;
    crvLeft(1, coords, direction, coordCnt);
    coordCnt+=10;
    direction= dir.DOWN;
    crvLeft(1, coords, direction, coordCnt);
    coordCnt+=10;
    direction = dir.RIGHT;
    crvLeft(1, coords, direction, coordCnt);
    coordCnt+=10;
    direction = dir.UP;

    crvRight(1, coords, direction, coordCnt);
    coordCnt+=10;
    direction = dir.RIGHT;
    crvRight(1, coords, direction, coordCnt);
    coordCnt+=10;
    direction= dir.DOWN;
    crvRight(1, coords, direction, coordCnt);
    coordCnt+=10;
    direction = dir.LEFT;
    crvRight(1, coords, direction, coordCnt);
    coordCnt+=10;
    direction = dir.UP;

    coord currCoord;
    coord prevCoord;


    for (int i = 0; i < coords.size(); i++) {
      int index1 = i;
      int index2 = (i+1)%coords.size();

      currCoord = coords.get(index1);
      nextCoord = coords.get(index2);

      double newDist = pythagoras(currCoord, nextCoord);
      distances.add(newDist);
      total = total+newDist;

      //System.out.println(currCoord.X + ", " + currCoord.Y);
      //System.out.println(Math.sqrt(Math.pow(2-currX,2)+Math.pow(currY,2)));
    }

    for(int i=0; i< distances.size(); i++)
      distances.set(i, distances.get(i)/total);

    output(distances, coords);


    coords.clear();
    coords.add(Ursprung);
    direction=dir.UP;
    coordCnt=0;


    System.out.println("################");
    String strecke = "2R2R28R24R24R22L2";

    for (int i = 0; i < strecke.length(); ) {
      if (Character.compare(strecke.charAt(i), 'L') == 0) {
        //Linkskurve gelesen
        //int radius, ArrayList<coord> coords, dir currDir, Integer last
        System.out.println(strecke.charAt(i+1));
        crvLeft(Integer.parseInt(String.valueOf(strecke.charAt(i + 1))), coords, direction, coordCnt);

        switch (direction) {
          case UP:
            direction = dir.LEFT;
            break;
          case DOWN:
            direction = dir.RIGHT;
            break;
          case LEFT:
            direction = dir.DOWN;
            break;
          case RIGHT:
            direction = dir.UP;
            break;
        }
        coordCnt += 10;
        i = i+ 2;
      } else if (Character.compare(strecke.charAt(i), 'R') == 0) {
        //Rechtskurve gelesen
        //int radius, ArrayList<coord> coords, dir currDir, Integer last
        System.out.println(strecke.charAt(i+1));
        crvRight(Integer.parseInt(String.valueOf(strecke.charAt(i + 1))), coords, direction, coordCnt);
        switch (direction) {
          case UP:
            direction = dir.RIGHT;
            break;
          case DOWN:
            direction = dir.LEFT;
            break;
          case LEFT:
            direction = dir.UP;
            break;
          case RIGHT:
            direction = dir.DOWN;
            break;
        }
        coordCnt += 10;
        i = i+ 2;
      } else {
        //Gerade gelesen
        switch (direction) {
          case UP:
            coords.add(new coord(coords.get(coordCnt).X, coords.get(coordCnt).Y + Integer.parseInt(String.valueOf(strecke.charAt(i)))));
            break;
          case DOWN:
            coords.add(new coord(coords.get(coordCnt).X, coords.get(coordCnt).Y - Integer.parseInt(String.valueOf(strecke.charAt(i)))));
            break;
          case LEFT:
            coords.add(new coord(coords.get(coordCnt).X - Integer.parseInt(String.valueOf(strecke.charAt(i))), coords.get(coordCnt).Y));
            break;
          case RIGHT:
            coords.add(new coord(coords.get(coordCnt).X + Integer.parseInt(String.valueOf(strecke.charAt(i))), coords.get(coordCnt).Y));
            break;
        }
        coordCnt++;
        i++;
      }

    }

    for (int i = 1; i < coords.size(); i++) {

      prevCoord = coords.get(i-1);
      currCoord = coords.get(i);

      double newDist = pythagoras(prevCoord, currCoord);
      distances.add(newDist);
      total = total+newDist;

      //System.out.println(currCoord.X + ", " + currCoord.Y);
      //System.out.println(Math.sqrt(Math.pow(2-currX,2)+Math.pow(currY,2)));
    }

    for(int i=0; i< distances.size(); i++)
      distances.set(i, distances.get(i)/total);

    output(distances, coords);
  }

  static void crvRight(int radius, ArrayList<coord> coords, dir currDir, Integer last) {
    double deg = 90 / PRECISION;
    boolean switcheroo;
    boolean negX;
    boolean negY;
    //System.out.println("RIGHT: last coordinate is: " + coords.get(last).X + " " + coords.get(last).Y);
    double deltaX, newX, lastX = coords.get(last).X;
    double deltaY, newY, lastY = coords.get(last).Y;

    //x/y invertieren
    if (currDir == dir.LEFT || currDir == dir.RIGHT)
      switcheroo = true;
    else
      switcheroo = false;

    //positiv/negative veränderung für x und y
    if (currDir == dir.UP || currDir == dir.RIGHT)
      negX = false;
    else
      negX = true;

    if (currDir == dir.UP || currDir == dir.LEFT)
      negY = false;
    else
      negY = true;

    for (int i = 0; i < PRECISION; i++) {
      if (switcheroo) {
        deltaY = radius - Math.cos(Math.toRadians(deg * (i + 1))) * radius;
        deltaX = Math.sin(Math.toRadians(deg * (i + 1))) * radius;
      } else {
        deltaX = radius - Math.cos(Math.toRadians(deg * (i + 1))) * radius;
        deltaY = Math.sin(Math.toRadians(deg * (i + 1))) * radius;
      }

      if (negX)
        deltaX = deltaX * -1;
      if (negY)
        deltaY = deltaY * -1;
      //System.out.println("deltas: " + deltaX + " " + deltaY);

      coords.add(new coord(lastX + deltaX, lastY + deltaY));

    }
  }

  static void crvLeft(int radius, ArrayList<coord> coords, dir currDir, Integer last) {
    double deg = 90 / PRECISION;
    boolean switcheroo;
    boolean negX;
    boolean negY;
   // System.out.println("LEFT: last coordinate is: " + coords.get(last).X + " " + coords.get(last).Y);

    double deltaX, newX, lastX = coords.get(last).X;
    double deltaY, newY, lastY = coords.get(last).Y;

    //x/y invertieren
    if (currDir == dir.LEFT || currDir == dir.RIGHT)
      switcheroo = true;
    else
      switcheroo = false;

    //positiv/negative veränderung für x und y
    if (currDir == dir.LEFT || currDir == dir.UP)
      negX = true;
    else
      negX = false;

    if (currDir == dir.DOWN || currDir == dir.LEFT)
      negY = true;
    else
      negY = false;

    for (int i = 0; i < PRECISION; i++) {
      if (switcheroo) {
        deltaY = radius - Math.cos(Math.toRadians(deg * (i + 1))) * radius;
        deltaX = Math.sin(Math.toRadians(deg * (i + 1))) * radius;
      } else {
        deltaX = radius - Math.cos(Math.toRadians(deg * (i + 1))) * radius;
        deltaY = Math.sin(Math.toRadians(deg * (i + 1))) * radius;
      }
      if (negX)
        deltaX = deltaX * -1;
      if (negY)
        deltaY = deltaY * -1;
      //System.out.println("deltas: "+deltaX+" "+deltaY);

      coords.add(new coord(lastX + deltaX, lastY + deltaY));
      //System.out.println(coords.get(last+i+1).Y);

    }
  }

  static double pythagoras(coord c1, coord c2){
    double result;

    result=Math.sqrt(Math.pow(c2.X - c1.X, 2)+Math.pow(c2.Y - c1.Y, 2));

    return result;
  }

  private static void output(ArrayList<Double> distances, ArrayList<coord> coords) {
    for(int i =0; i< distances.size(); i++){
      System.out.println(distances.get(i));
    }
    for(int i=0; i<coords.size(); i++){
      System.out.println(coords.get(i).X + " 0 "+coords.get(i).Y);
    }
  }
}


