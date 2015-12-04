package Memento;

import Element.Element;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Ana-Marija
 */
public class Originator {
   private int stanje;

   public void setState(int stanje){
      this.stanje = stanje;
   }

   public int getState(){
      return stanje;
   }

   public Memento saveStateToMemento(){
      return new Memento(stanje);
   }

   public void getStateFromMemento(Memento Memento){
      stanje = Memento.getState();
   }

}
