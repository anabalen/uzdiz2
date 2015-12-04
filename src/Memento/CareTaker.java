package Memento;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ana-Marija
 */
public class CareTaker {
   private List<Memento> mementoList = new ArrayList<Memento>();

   public void add(Memento stanje){
      mementoList.add(stanje);
   }

   public Memento get(int index){
      return mementoList.get(index);
   }
}
