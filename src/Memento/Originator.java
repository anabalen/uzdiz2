package Memento;

import Element.Element;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Ana-Marija
 */
public class Originator {
   List<Element> listaElemenata = new ArrayList<>();

   public void setState(List<Element> listaElemenata){
      this.listaElemenata = listaElemenata;
   }

   public List<Element> getState(){
      return this.listaElemenata;
   }

   public Memento saveStateToMemento(){
      return new Memento(this.listaElemenata);
   }

   public void getStateFromMemento(Memento Memento){
      this.listaElemenata = Memento.getState();
   }

}
