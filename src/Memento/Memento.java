package Memento;
import Element.Element;

import java.util.List;


/**
 *
 * @author Ana-Marija
 */
public class Memento {
   List<Element> listaElemenata;

   public Memento(List<Element> listaElemenata){
      this.listaElemenata = listaElemenata;
   }

   public List<Element> getState(){
      return listaElemenata;
   }	
}
