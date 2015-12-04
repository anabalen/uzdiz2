package Memento;
import Element.Element;

import java.util.List;


/**
 *
 * @author Ana-Marija
 */
public class Memento {
   private int stanje;

   public Memento(int stanje){
      this.stanje = stanje;
   }

   public int getState(){
      return stanje;
   }	
}
