package application;

//Ameer Qadadha - 1221147
public class CPNode {
	private Passenger element;
	private CPNode next;

	public CPNode() {

	}

	public CPNode(Passenger element) {
		this.element = element;
	}

	public Passenger getElement() {
		return element;
	}

	public void setElement(Passenger element) {
		this.element = element;
	}

	public CPNode getNext() {
		return next;
	}

	public void setNext(CPNode next) {
		this.next = next;
	}

}
