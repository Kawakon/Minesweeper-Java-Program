public class GenericArrayStack<E> implements Stack<E> {
   
   // ADD YOUR INSTANCE VARIABLES HERE
   private E[] elems;
   private int top;

   // Constructor
   @SuppressWarnings ("unchecked")
    public GenericArrayStack( int capacity ) {
        
    
		elems = (E[]) new Object[capacity];
		top = 0;

    }

    // Returns true if this ArrayStack is empty
    public boolean isEmpty() {
        
    
		return top == 0;

    }

    public void push( E elem ) {
        
    
		elems[top] = elem;
		top++;

    }
    public E pop() {
        
    
		E item = elems[top-1];
		elems[top-1] = null;
		top--;
		return item;
    }

    public E peek() {
        
    
		return elems[top-1];

    }
}
