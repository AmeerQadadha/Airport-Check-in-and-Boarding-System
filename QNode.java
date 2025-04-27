package application;

public class QNode {
	private Passenger element;
	private QNode next;

	public QNode() {

	}

	public QNode(Passenger element) {
		this.element = element;
	}

	public QNode(Passenger element, QNode next) {
		this.element = element;
		this.next = next;
	}

	public Passenger getElement() {
		return element;
	}

	public void setElement(Passenger element) {
		this.element = element;
	}

	public QNode getNext() {
		return next;
	}

	public void setNext(QNode next) {
		this.next = next;
	}

}
