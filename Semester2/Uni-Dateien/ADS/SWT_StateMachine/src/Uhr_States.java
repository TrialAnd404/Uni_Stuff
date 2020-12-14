import java.util.Scanner;

enum States{
  TIME, HRS, MIN;
}

public class Uhr_States {
  States state = States.HRS;

  public void do_State(){
    switch (state){
      case HRS:
        System.out.println(hrs);
        break;
      case MIN:
        System.out.println(min);
        break;
      case TIME:
        getTime();
        break;
      default:
        System.out.println("something went wrong...");
        break;
    }
  }

  public void inc(){
    switch (state){
      case HRS:
        hrs++;
        hrs = hrs%24;
        break;
      case MIN:
        min++;
        min = min%60;
        break;
      case TIME:
        System.out.println("cannot increment right now (State: TIME)");
        break;
      default:
        System.out.println("something went wrong...");
        break;
    }
  }

  public void set(){
    switch (state){
      case TIME:
        state = States.HRS;

        break;
      case HRS:
        state = States.MIN;
        break;
      case MIN:
        state = States.TIME;
        break;
      default:
        System.out.println("State Error, RESETTING...");
        hrs = 0;
        min = 0;
        state = States.HRS;
        break;
    }
  }

  public void getTime(){
    System.out.println("current time is "+hrs+" hrs and "+min+" minutes");
  }

  private int min = 0;
  private int hrs = 0;

  public static void main(String[] args) {
    Uhr_States uhr = new Uhr_States();
    Scanner myObj = new Scanner(System.in);  // Create a Scanner object
    while(true) {
      System.out.println(uhr.state);
      System.out.println("Enter action");

      String action = myObj.nextLine();  // Read user input

      switch (action) {
        case "+":
          uhr.inc();
          uhr.do_State();
          break;
        case "s":
          uhr.set();
          uhr.do_State();
          break;
        default:
          System.out.println("enter valid action [+(increment), s(set)]");
      }
    }
  }

}

