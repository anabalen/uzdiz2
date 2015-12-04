package Iterator;

import static main.DatotekaHandler.konacnaLista;



/**
 *
 * @author Ana-Marija
 */
public class Podaci implements Container {

    @Override
    public MojIterator getIterator() {
        return new NameIterator();
    }

    private class NameIterator implements MojIterator {

        int index;

        @Override
        public boolean hasNext() {

            if (index < konacnaLista.size()) {
                return true;
            }
            return false;
        }

        @Override
        public Object next() {
            if (this.hasNext()) {
                
                return konacnaLista.get(index++);
            }
            return null;
        }

      

    }
}
