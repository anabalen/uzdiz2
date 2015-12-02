package State;

/**
 *
 * @author Ana-Marija
 */
public class PrikaziElement implements State{
    public void doAction(Context context) {
      System.out.println("Player is in start state");
      context.setState(this);	
   }

   public String toString(){
      return "Prk";
   }

}
