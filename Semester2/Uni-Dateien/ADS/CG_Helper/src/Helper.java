import java.util.ArrayList;

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

  static class turn{
    public double degrees;
    public double key;
    turn(double degrees, double key){
      this.degrees=degrees; this.key=key;
    }
  }

  public static void main(String[] args) {
    Integer coordCnt = 0;
    double total = 0;
    dir direction = dir.UP;
    dir startDirection= direction;
    ArrayList<turn> turns = new ArrayList<turn>();
    ArrayList<coord> coords = new ArrayList<coord>();
    ArrayList<Double> distances = new ArrayList<Double>();
    ArrayList<Double> subtotals = new ArrayList<Double>();
    subtotals.add(0.0);

    coord Ursprung = new coord(0, 0);
    coords.add(Ursprung);


    coord currCoord;
    coord prevCoord;

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




    for (int i = 0; i < coords.size(); i++) {
      int index1 = i;
      int index2 = (i+1)%coords.size();

      currCoord = coords.get(index1);
      prevCoord = coords.get(index2);

      double newDist = pythagoras(prevCoord,currCoord);
      distances.add(newDist);
      total = total+newDist;

      //System.out.println(currCoord.X + ", " + currCoord.Y);
      //System.out.println(Math.sqrt(Math.pow(2-currX,2)+Math.pow(currY,2)));
    }

    for(int i=0; i< distances.size(); i++)
      distances.set(i, distances.get(i)/total);

    degrees("L2L2L2L2R2R2R2R2", subtotals, turns, direction);
    output(distances, coords, turns);

    turns.clear();
    coords.clear();
    coords.add(Ursprung);
    direction=dir.UP;
    coordCnt=0;


    System.out.println("################");
    String strecke = "6R4R2L2L2R24R2L46R4R48R4L4L4R4R4";

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

    //einzelnen Abstände der Koordinaten ermitteln
    for (int i = 0; i < coords.size(); i++) {
      int index1 = i;
      int index2 = (i+1)%coords.size();

      currCoord = coords.get(index1);
      prevCoord = coords.get(index2);

      double newDist = pythagoras(prevCoord,currCoord);
      distances.add(newDist);
      total = total+newDist;
    }

    //einzelnen Abstände zu prozentualen Abständen umrechnen -> für keys
    for(int i=0; i< distances.size(); i++) {
      distances.set(i, distances.get(i) / total);
      subtotals.add(subtotals.get(i)+distances.get(i));
    }

    System.out.println(subtotals.size() + " " +distances.size() + " " +coords.size());
    degrees(strecke, subtotals, turns, direction);

    output(subtotals, coords, turns);
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

  static void degrees(String strecke, ArrayList<Double> timestamps, ArrayList<turn> turns, dir direction){

    double piHalf = Math.PI/2;
    double turnMe = 0;
    double ts;
    int currentTS = 1;

    switch (direction){
      case UP:
        break;
      case LEFT:
        break;
      case DOWN:
        break;
      case RIGHT:
        break;
    }

    for(int i=0; i<strecke.length();){
      if(strecke.charAt(i) == 'L'){
        currentTS+=PRECISION;

        ts = timestamps.get(currentTS);
        turnMe=turnMe-piHalf;
        turns.add(new turn(turnMe, ts));
        i+=2;
      }else if(strecke.charAt(i) == 'R'){
        currentTS+=PRECISION;
        ts = timestamps.get(currentTS);
        turnMe=turnMe+piHalf;

        turns.add(new turn(turnMe, ts));
        i+=2;
      }else {
        System.out.println(currentTS);
        turns.add(new turn(turnMe, timestamps.get(currentTS)));
        currentTS++;
        i++;
      }
    }


  }

  static double pythagoras(coord c1, coord c2){
    double result;

    result=Math.sqrt(Math.pow(c2.X - c1.X, 2)+Math.pow(c2.Y - c1.Y, 2));

    return result;
  }

  private static void output(ArrayList<Double> distances, ArrayList<coord> coords, ArrayList<turn> turns) {
    for(int i =0; i< distances.size(); i++){
      System.out.print(distances.get(i)+" ");
      if(i%10==0&&i>0){
        System.out.print("\n");
      }
    }
    System.out.print("\n");

    for(int i=0; i<coords.size(); i++){
      System.out.println(coords.get(i).X + " 0 "+coords.get(i).Y+",");
    }

    for(int i =0; i< turns.size(); i++){
      System.out.print(turns.get(i).key +" ");
      if(i%10==0&&i>0){
        System.out.print("\n");
      }
    }

    for(int i =0; i< turns.size(); i++){
      System.out.println("0 1 0 "+turns.get(i).degrees+",");
    }
  }
}


