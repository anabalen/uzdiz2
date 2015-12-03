package State;

import Element.Element;

/**
 *
 * @author Ana-Marija
 */
public class PrikaziElement implements State{
    @Override
    public  Element doAction(Element element) {
      element.setErrorIspravnostiZapisa(true);
      return element;
    
   }

    @Override
   public String toString(){
      return "Prk";
   }

}
