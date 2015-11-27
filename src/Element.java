
import java.util.List;


/**
 *
 * @author Ana-Marija
 */
public class Element {
    private int tip;
    private int sifra;
    private int roditelj;
    private List<Integer> koordinate;
    private String boja;
   
    public Element(int tipZapisa, int sifraZapisa, int roditelj, List<Integer> koordinate, String boja){
      
        this.tip = tipZapisa;
       this.sifra = sifraZapisa;
       this.roditelj = roditelj;
       this.koordinate = koordinate;
       this.boja = boja;
       
    }
    
    public Element(){}

    public int getTip() {
        return tip;
    }

    public int getSifra() {
        return sifra;
    }

    public int getRoditelj() {
        return roditelj;
    }

    public List<Integer> getKoordinate() {
        return koordinate;
    }

    public String getBoja() {
        return boja;
    }

    public void setTip(int tip) {
        this.tip = tip;
    }

    public void setSifra(int sifra) {
        this.sifra = sifra;
    }

    public void setRoditelj(int roditelj) {
        this.roditelj = roditelj;
    }

    public void setKoordinate(List<Integer> koordinate) {
        this.koordinate = koordinate;
    }

    public void setBoja(String boja) {
        this.boja = boja;
    }
    
 
}
