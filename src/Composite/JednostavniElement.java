package Composite;

/**
 *
 * @author Ana-Marija
 */
public class JednostavniElement implements IElement{

    private int tip;
    private int sifra;
    private int roditelj;
    private String[] koordinate;
    private String boja;
    Boolean errorIspravnostiZapisa;
    String error;
    String presjek;
    String[] koordinateRoditelja;
 
    public JednostavniElement(int tipZapisa, int sifraZapisa, int roditelj, String[] koordinate, String boja, Boolean errorIspravnostiZapisa, String error, String[] koordinateRoditelja){
       this.tip = tipZapisa;
       this.sifra = sifraZapisa;
       this.roditelj = roditelj;
       this.koordinate = koordinate;
       this.boja = boja;
       this.errorIspravnostiZapisa = errorIspravnostiZapisa;
       this.error = error;
       this.koordinateRoditelja = koordinateRoditelja;
    }
            
    @Override
    public void add(IElement element) {
       
    }

    @Override
    public void remove(IElement element) {
       
    }

    @Override
    public IElement getChild(int i) {
        return null;
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
    
    

    @Override
    public void print() {
        System.out.println(sifra + " " + roditelj + " " + koordinate + " " + boja + " " + presjek);
    }
    
}
