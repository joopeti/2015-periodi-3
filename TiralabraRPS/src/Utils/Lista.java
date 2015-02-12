
package Utils;

import java.util.Iterator;

/** Lista jonka koko kasvaa automaattisesti tarvittaessa.
 * @param <E>
 */
public class Lista<E> implements Iterable{
    private int size = 0;
    private int capacity = 10;
    private Object[] list;

    public Lista(){
        this.list = new Object[capacity];
    }
    
    /**
     * Lisää listaan alkion.
     * @param a
     */
    public void add(Object a){
        if(size == capacity){
            addCapacity();
        }
        list[size] = a;
        size++;
    }
    
    /**
     * Palauttaa listan alkion indeksistä.
     * @param i indeksi.
     * @return
     */
    public E get(int i){
        if(i < 0 || i >= size){
            throw new ArrayIndexOutOfBoundsException("Tarkistetaanpa ne indeksit! " + i);
        }
        return (E) list[i];
    }
    
    /**
     * Palauttaa listan viimeisen alkion.
     * @return
     */
    public E getLast(){
        if(size == 0){
            return null;
        }
        return (E) list[size];
    }
    
    /**
     * Poistaa annetusta indeksistä alkion ja siirtää sitä ylempänä olleet alkiot yhden askeleen alemmas.
     * @param i
     */
    public void remove(int i){
        if(i < 0 || i >= size){
            throw new ArrayIndexOutOfBoundsException("Tarkistetaanpa ne indeksit! " + i);
        }
        list[i] = null;
        for (int j = i; j < size - 1; j++) {
            list[j] = list[j + 1];
        }
        list[size] = null;
    }
    
    /**
     * Palauttaa taulun alkioiden määrän.
     * @return
     */
    public int size(){
        return size;
    }
    
    /**
     * Luo kaksi kertaa isomman listan ja kopioi siihen vanhan listan alkiot.
     */

    private void addCapacity() {
        int newSize = list.length * 2;
        capacity = newSize; 
        Object[] newList = new Object[newSize];
        for (int i = 0; i < size; i++) {
            newList[i] = list[i];
        }
        list = newList;
    }
    
    /**
     * Tyhjentää listan ja palauttaa arvot oletusarvoisiksi.
     */
    public void clear(){
        this.size = 0;
        this.capacity = 10;
        this.list = new Object[capacity];
    }
    
    /**
     * Tulostaa listan sisällön.
     */
    public void print(){
    System.out.println("[");
    for (int i = 0; i < size; i++) {
        System.out.print(list[i] + ", ");
    }
    System.out.println("]");
}

    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
