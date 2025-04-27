package application;

//Ameer Qadadha - 1221147
public class BPNode {
	private Passenger element;
	private BPNode next;

	public BPNode() {

	}

	public BPNode(Passenger element) {
		this.element = element;
	}

	public Passenger getElement() {
		return element;
	}

	public void setElement(Passenger element) {
		this.element = element;
	}

	public BPNode getNext() {
		return next;
	}

	public void setNext(BPNode next) {
		this.next = next;
	}

}
