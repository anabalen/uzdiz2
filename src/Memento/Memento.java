package Memento;

import java.util.List;
import javax.lang.model.element.Element;

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
