
package Utils;

/** Lista jonka koko kasvaa automaattisesti tarvittaessa.
 * @param <Object>
 */
public class Lista<E> {
    private int size = 0;
    private final int capacity = 10;
    private Object[] list;
    
    public Lista(){
        this.list = new Object[capacity];
    }
    
    public void add(Object a){
        if(size == capacity){
            addCapacity();
        }
        list[size++] = a;
    }
    
    public Object get(int i){
        if(i < 0 || i >= size){
            throw new ArrayIndexOutOfBoundsException("Tarkistetaanpa ne indeksit! " + i);
        }
        return list[i];
    }
    
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
    
    public int size(){
        return size;
    }

    private void addCapacity() {
        int newSize = list.length * 2;
        Object[] newList = new Object[newSize];
        for (int i = 0; i < size; i++) {
            newList[i] = list[i];
        }
        list = newList;
    }
    
public void print(){
    System.out.println("[");
    for (int i = 0; i < size; i++) {
        System.out.print(list[i] + ", ");
    }
    System.out.println("]");
}

}
