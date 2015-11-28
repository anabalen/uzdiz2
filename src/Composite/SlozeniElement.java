package Composite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Ana-Marija
 */
public class SlozeniElement implements IElement {

    private int tip;
    private int sifra;
    private int roditelj;
    private String[] koordinate;
    private String boja;
    
    public SlozeniElement(int tipZapisa, int sifraZapisa, int roditelj, String[] koordinate, String boja){
       
       this.tip = tipZapisa;
       this.sifra = sifraZapisa;
       this.roditelj = roditelj;
       this.koordinate = koordinate;
       this.boja = boja;
    }
    
    List<IElement> elementi = new ArrayList<IElement>();
    
    @Override
    public void add(IElement element) {
        elementi.add(element);
    }

    @Override
    public void remove(IElement element) {
        elementi.remove(element);
    }

    @Override
    public IElement getChild(int i) {
        return elementi.get(i);
    }

    @Override
    public int getSifra() {
        return sifra;
    }

    @Override
    public int getRoditelj() {
        return roditelj;
    }

    @Override
    public String[] getKoordinate() {
        return koordinate;
    }

    @Override
    public String getBoja() {
        return boja;
    }

    @Override
    public void print() {
        System.out.println(sifra + " " + roditelj + " " + koordinate + " " + boja);
        Iterator<IElement> elementIterator = elementi.iterator();
        while(elementIterator.hasNext()){
        IElement element = elementIterator.next();
        element.print();
        }
    }
    
}
