package structures;

public class DoublyCircularLinkedListImplementation<T extends Comparable<T>> implements
		DoublyCicularLinkedList<T> {
	
	private int numberOfNodes;
	private DLLNode<T> doubleLinkedListNodeHead;	
	private DLLNode<T> currentPos;
	
	public DoublyCircularLinkedListImplementation() {
			
			doubleLinkedListNodeHead = null;
            numberOfNodes = 0;
            currentPos = doubleLinkedListNodeHead;
	}
	
	@Override
	public int size() {
            // TODO
            return numberOfNodes;
            
	}

	@Override
	public void add(T element) {
            // TODO
		if(doubleLinkedListNodeHead == null) {
			
			doubleLinkedListNodeHead = new DLLNode<T>(element);
			doubleLinkedListNodeHead.setForward(doubleLinkedListNodeHead);
			doubleLinkedListNodeHead.setBack(doubleLinkedListNodeHead);
			
		}else {
			
			DLLNode<T> temp = new DLLNode<T>(element);							
			DLLNode<T> tailNode = doubleLinkedListNodeHead.getForward();
			temp.setForward(tailNode);
			tailNode.setBack(temp);
			temp.setBack(doubleLinkedListNodeHead);
			doubleLinkedListNodeHead.setForward(temp);
				
			}
		
		numberOfNodes++;
		
	}

	@Override
	public boolean remove(T element) {
		if(doubleLinkedListNodeHead == null) {
			
            return false;
            
		}else {
			
			DLLNode<T> temp = doubleLinkedListNodeHead;
			for(int breakCounter = size(); breakCounter>0; breakCounter--) {
				
				if(temp.getInfo().equals(element)) {
//					System.out.println("Successfuly removed element " + temp.getInfo());
//					System.out.println("Currenct size is "+ size());
					if(size() == 1){
						
						doubleLinkedListNodeHead = null;
						
					}else {
						
						temp.getForward().setBack(temp.getBack());
						temp.getBack().setForward(temp.getForward());
						if(temp == doubleLinkedListNodeHead) {
							
							doubleLinkedListNodeHead = doubleLinkedListNodeHead.getBack();
							
						}
					}
					
					numberOfNodes--;
//					System.out.println("Size after removed " + size()+"\n");
					return true;
					
				}
				
				temp = temp.getBack();
				
			}
			
			return false;
			
		}				
	}

	
	@Override
	public boolean contains(T element) {
		
		DLLNode<T> temp = doubleLinkedListNodeHead;
		for(int breakCounter = size(); breakCounter>0; breakCounter--) {
			
			if(temp.getInfo().equals(element)) {	
				
				return true;
				
			}
			
			temp = temp.getBack();
			
		}
		
		return false;
		
	}

	@Override
	public T get(T element) {
		
		if(contains(element))
			return element;
		else
			return null;
		
	}

	@Override
	public void reset() {
            // TODO
        currentPos = doubleLinkedListNodeHead;

	}

	@Override
	public T getNext() {
         
		DLLNode<T> temp = currentPos;
		currentPos = currentPos.getBack();
		System.out.println("My next position is " + temp.getInfo());
		return temp.getInfo();
		
	}

	@Override
	public T getPrevious() {
		
		currentPos = currentPos.getForward();
		return currentPos.getInfo();
		
	}

}
