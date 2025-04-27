package application;

//Ameer Qadadha - 1221147
public class SNode {
	private String element;
	private SNode next;

	public SNode() {

	}

	public SNode(String element) {
		this.element = element;
	}

	public SNode(String element, SNode next) {
		this.element = element;
		this.next = next;
	}

	public SNode getNext() {
		return next;
	}

	public void setNext(SNode next) {
		this.next = next;
	}

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}
}
