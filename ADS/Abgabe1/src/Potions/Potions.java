package Potions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Potions {


  public static class Ingredient {

    String name;
    int cost;

    public Ingredient(String name, int cost) {
      this.name = name;
      this.cost = cost;
    }

    // use this method to compare two ingredients.
    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || o.getClass() != this.getClass() ) {
        return false;
      }
      Ingredient i = (Ingredient) o;
      return name == i.name && cost == i.cost;
    }

  }


  public static class Step {

    Ingredient[] input;   // the step's input ingredients (1:many)
    Ingredient output;    // the step's output ingredient (exactly 1)

    public Step(Ingredient output, Ingredient[] input) {
      this.output = output;
      this.input = input;
    }

    // prints a step
    public void print() {
      System.out.print("  <STEP> ");
      for (Ingredient i : input) {
        System.out.print(i.name + ", ");
      }
      System.out.println(" -> " + output.name + " </STEP>");
    }

    // prints a sequence of steps (e.g., a recipe).
    public static void print(Step[] steps) {
      System.out.println("<STEPS>");
      for (Step s : steps) {
        s.print();
      }
      System.out.println("</STEPS>");
    }

  }

  // checks if an ingredient is available on the table
  // or in the pantry.
  public static boolean available(Ingredient ing,
                                  Ingredient[] place) {
    for (Ingredient ing2 : place) {
      if (ing.equals(ing2))
        return true;
    }
    return false;
  }

  // removes an ingredient from the table or pantry.
  public static Ingredient[] remove(Ingredient ing,
                                    Ingredient[] place) {
    Ingredient[] result = new Ingredient[place.length - 1];
    boolean done = false;
    int c = 0;
    for (int i=0; i<place.length; ++i) {
      if (!done && ing.equals(place[i])) {
        done = true;
      } else {
        result[c++] = place[i];
      }
    }
    assert(done);   // assertion if ing not in place.
    return result;
  }

  // adds a new ingredient 'ing' to the table or pantry.
  public static Ingredient[] add(Ingredient ing,
                                 Ingredient[] place) {
    Ingredient[] result = new Ingredient[place.length + 1];
    result[0] = ing;
    for (int i=0; i<place.length; ++i) {
      result[i+1] = place[i];
    }
    return result;
  }


  /*
   * Create a potion recipe using a greedy strategy.
   *
   *  @param goal : The potion to produce.
   *  @param book : The book containing the possible brewing steps.
   *  @param pantry: The reserve of available ingredients.
   */
  public static Step[] greedy(Ingredient goal,
                              Step[] book,
                              Ingredient[] pantry) {

    Ingredient[] goals = new Ingredient[]{goal};
    List<Step> steps = new ArrayList<>();


    while(goals.length > 0){

      //check obs im regal ist

      if (Potions.available(goals[0], pantry)){
        pantry=Potions.remove(goals[0], pantry);
        goals = Arrays.copyOfRange(goals, 1, goals.length);
      }

      //zusammenbauen aus zutaten

      else {
        boolean foundInBook = false;
        ArrayList<Ingredient> goalsArrayList = new ArrayList<>(Arrays.asList(Arrays.copyOfRange(goals, 1, goals.length)));
        for(int i = 0; i < book.length; i++){
          if(book[i].output.equals(goals[0])) {
            foundInBook = true;
            steps.add(0, book[i]);
            for(int j = 0; j < book[i].input.length; j++){
              goalsArrayList.add(0, book[i].input[j]);
              goals = goalsArrayList.toArray(new Ingredient[0]);
            }
            break;
          }
        }

        // weder im buch noch im regal

        if(!foundInBook) return new Step[]{};


      }
    }
    return steps.toArray(new Step[0]);
  }


  /*
   * Create a potion recipe using a backtracking strategy.
   *
   *  @param goal : The potion to produce.
   *  @param book : The book containing the possible brewing steps.
   *  @param pantry: The reserve of available ingredients.
   */
  public static Step[] backtracking(Ingredient goal,
                                    Step[] rulebook,
                                    Ingredient[] pantry) {



    // FIXME: internal recursive methods are allowed!
    return backtrackingRecursive(goal, rulebook, pantry);
  }

  public static Step[] backtrackingRecursive(Ingredient goal,
                                             Step[] rulebook,
                                             Ingredient[] pantry) {

    Step[] result;

    int benchmark;
    int recipeCost=0;

    Ingredient[] restPantry = pantry.clone();
    //gibts mich im pantry?
    if(available(goal, restPantry)){
      benchmark=goal.cost;
    }
    //gibts ein rezept für mich?
    for(int i=0; i<rulebook.length; i++){
      if(rulebook[i].equals(goal)){
        for(int j=0; j<rulebook[i].input.length; j++){
          //kosten von rezeptkomponenten addieren
          //recipeCost=recipeCost + backtracking(rulebook[i].input[j],rulebook,restPantry);

        }

        //was kostet mehr?
        if(recipeCost<benchmark){
          //pantry günstiger -> aus dem pantry entfernen & zu gesamtliste hinzufügen
        }
        //aufruf von backtracking für rezeptkomponenten
        for(int i=0; i<rulebook.length; i++){

        }

      }
    }

    // FIXME: internal recursive methods are allowed!
    return result;
  }


}
