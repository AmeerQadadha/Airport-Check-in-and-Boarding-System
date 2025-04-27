package application;

//Ameer Qadadha - 1221147
public class Stack {
	private int size;
	private SNode Front;

	public Stack() {

	}

	public boolean isEmpty() {
		return (Front == null);
	}

	public int Size() {
		return size;
	}

	public void push(String element) {
		SNode newNode;
		newNode = new SNode(element);
		newNode.setNext(Front);
		Front = newNode;
		size++;
	}

	public String pop() {
		if (!isEmpty()) {
			SNode top = Front;
			Front = Front.getNext();
			size--;
			return top.getElement();
		} else
			return null;
	}

	public Object peek() {
		if (!isEmpty())
			return Front.getElement();
		else
			return null;
	}

}
