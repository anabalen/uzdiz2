package Composite;

/**
 *
 * @author Ana-Marija
 */
public interface IElement {
    public void add(IElement element);
    public void remove(IElement element);
    public IElement getChild(int i);
    public int getSifra();
    public int getRoditelj();
    public String[] getKoordinate();
    public String getBoja();
    public void print();
}
