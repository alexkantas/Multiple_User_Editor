package gr.alexkantas.oop2.me;

import java.util.Observable;

/**
 *
 * @author Alex
 */
public class Model extends Observable {

    private String text = "HI!";
    
    private int i = 1;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        
        setChanged();
        notifyObservers();
    }

    public void addViewer() {
        Viewer v = new Viewer("Viewer #" + i, this);
        i++;
        addObserver(v);
        System.out.println("" + getListsize());
        setChanged();
        notifyObservers();
    }

    public void addViewer(Viewer viewer) {
        addObserver(viewer);
        System.out.println("" + getListsize());
        setChanged();
        notifyObservers();
    }

    public void removeViewer(Viewer viewer) {
        deleteObserver(viewer);
        setChanged();
        notifyObservers();
    }

    public int getListsize() {
        return countObservers()-1;
    }

}
