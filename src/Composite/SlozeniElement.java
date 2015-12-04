package Composite;

import Element.Element;
import Iterator.MojIterator;
import Iterator.Podaci;
import java.util.ArrayList;
import java.util.Arrays;
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
    public List<IElement> elementi;
    Boolean errorIspravnostiZapisa;
    String error;
    String presjek;
    String[] koordinateRoditelja;

    public SlozeniElement(int tipZapisa, int sifraZapisa, int roditelj, String[] koordinate, String boja, Boolean errorIspravnostiZapisa, String error, String presjek, String[] koordinateRoditelja) {

        this.tip = tipZapisa;
        this.sifra = sifraZapisa;
        this.roditelj = roditelj;
        this.koordinate = koordinate;
        this.boja = boja;
        this.elementi = new ArrayList<>();
        this.errorIspravnostiZapisa = errorIspravnostiZapisa;
        this.error = error;
        this.presjek = presjek;
        this.koordinateRoditelja = koordinateRoditelja;
    }

    @Override
    public void add(IElement element) {
        this.elementi.add(element);
    }

    @Override
    public void remove(IElement element) {
        this.elementi.remove(element);
    }

    @Override
    public IElement getChild(int i) {
        return this.elementi.get(i);
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

    public List<IElement> getElementi() {
        return this.elementi;
    }

    public void setElementi(List<IElement> elementi) {
        this.elementi = elementi;
    }

    public Boolean getErrorIspravnostiZapisa() {
        return errorIspravnostiZapisa;
    }

    public String getError() {
        return error;
    }

    public String[] getKoordinateRoditelja() {
        return koordinateRoditelja;
    }

    public void setErrorIspravnostiZapisa(Boolean errorIspravnostiZapisa) {
        this.errorIspravnostiZapisa = errorIspravnostiZapisa;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setKoordinateRoditelja(String[] koordinateRoditelja) {
        this.koordinateRoditelja = koordinateRoditelja;
    }

    public String getPresjek() {
        return presjek;
    }

    public void setPresjek(String presjek) {
        this.presjek = presjek;
    }

    
    @Override
    public void print() {

        if (errorIspravnostiZapisa == true) {
            System.out.println(this.getSifra() + " " + this.getRoditelj() + " " + Arrays.toString(this.getKoordinate()) + " " + this.getBoja() + " " + this.getPresjek());
        }
        
        Podaci podaci = new Podaci();

        for (MojIterator iter = podaci.getIterator(); iter.hasNext();) {
            Object lista = (Object) iter.next();
            System.out.println("Dijete : " + lista);
        }
        

    }

}
