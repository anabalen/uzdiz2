package State;

import Element.Element;


/**
 *
 * @author Ana-Marija
 */
public class SakrijElement implements State{
     @Override
     public  Element doAction(Element element) {
      element.setErrorIspravnostiZapisa(false);
      return element;
   }

     @Override
   public String toString(){
      return "Sakriven";
   }
}
