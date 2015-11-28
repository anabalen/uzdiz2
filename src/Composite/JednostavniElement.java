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
    
    
    public JednostavniElement(int tipZapisa, int sifraZapisa, int roditelj, String[] koordinate, String boja){
       this.tip = tipZapisa;
       this.sifra = sifraZapisa;
       this.roditelj = roditelj;
       this.koordinate = koordinate;
       this.boja = boja;
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

    @Override
    public void print() {
        System.out.println(sifra + " " + roditelj + " " + koordinate + " " + boja);
    }
    
}
