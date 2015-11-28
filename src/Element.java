
import java.util.List;


/**
 *
 * @author Ana-Marija
 */
public class Element {
    private int tip;
    private int sifra;
    private int roditelj;
    private String[] koordinate;
    private String boja;
    Boolean errorIspravnostiZapisa;
    String greska;
    String testPorukaValidacije;
   
    public Element(int tipZapisa, int sifraZapisa, int roditelj, String[] koordinate, String boja, Boolean errorIspravnostiZapisa, String error, String testPorukaValidacije){
      
        this.tip = tipZapisa;
       this.sifra = sifraZapisa;
       this.roditelj = roditelj;
       this.koordinate = koordinate;
       this.boja = boja;
       this.errorIspravnostiZapisa = errorIspravnostiZapisa;
       this.greska = error;
       this.testPorukaValidacije = testPorukaValidacije;
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

    public String[] getKoordinate() {
        return koordinate;
    }

    public String getBoja() {
        return boja;
    }
    
    public Boolean getErrorIspravnostiZapisa(){
        return errorIspravnostiZapisa;
    }
    
    public String getGreska(){
        return greska;
    }
    
    public String getTestPoruka(){
        return testPorukaValidacije;
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

    public void setKoordinate(String[] koordinate) {
        this.koordinate = koordinate;
    }

    public void setBoja(String boja) {
        this.boja = boja;
    }
    
    public void setErrorIspravnostiZapisa(Boolean errorIspravnostiZapisa) {
        this.errorIspravnostiZapisa = errorIspravnostiZapisa;
    }
    
    public void setGreska(String error) {
        this.greska = error;
    }
    
    public void setTestPoruka(String testPorukaValidacije) {
        this.testPorukaValidacije = testPorukaValidacije;
    }
 
}
