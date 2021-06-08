import java.awt.Taskbar.State;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class baumtraverse {
    
    static class State{
        public State(
            int a, int b, int c,
            int d, int e, int f,
            int g, int h, int i)
        {
            /*
                a   b   c
                
                d   e   f

                g   h   i

            */
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
            this.e = e;
            this.f = f;
            this.g = g;
            this.h = h;
            this.i = i;
        };
        int a,b,c,d,e,f,g,h,i;

        public boolean compareState(State s){
            return(s.equals(this));
        }
    }

    State goal = new State(1,2,3,4,0,5,6,7,8);

    static class Node{
        State currentState;
        List<State> daughterStates;

        public Node(State s){
            this.currentState = s;
        }
    }

    public static void main(String args[]){

    }

}
