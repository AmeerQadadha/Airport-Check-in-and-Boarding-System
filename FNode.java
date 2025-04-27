package application;

//Ameer Qadadha - 1221147
public class FNode {
	private Flight element;
	private FNode next;
	private FNode prev;
	private Queue regularQueue;
	private Queue VIPQueue;
	private Stack undoStack;
	private Stack redoStack;
	private PassengerLinkedList passengerList;
	private PassengerLinkedList cancelledList;
	private PassengerLinkedList AllPassengersList;

	public FNode() {// default constructor
		this.passengerList = new PassengerLinkedList();
		this.cancelledList = new PassengerLinkedList();
		this.VIPQueue = new Queue();
		this.regularQueue = new Queue();
		this.AllPassengersList = new PassengerLinkedList();
	}

	public FNode(Flight element) {
		this.element = element;
		this.passengerList = new PassengerLinkedList();
		this.cancelledList = new PassengerLinkedList();
		this.VIPQueue = new Queue();
		this.regularQueue = new Queue();
		this.AllPassengersList = new PassengerLinkedList();
	}

	public FNode(Flight element, Queue regularQueue, Queue VIPQueue, Stack undoStack, Stack redoStack,
			PassengerLinkedList passengerList, PassengerLinkedList cancelledList) {
		this.element = element;
		this.regularQueue = regularQueue;
		this.VIPQueue = VIPQueue;
		this.undoStack = undoStack;
		this.redoStack = redoStack;
		this.passengerList = new PassengerLinkedList();
		this.cancelledList = new PassengerLinkedList();

	}

	public PassengerLinkedList getCancelledList() {
		return cancelledList;
	}

	public void setCancelledList(PassengerLinkedList cancelledList) {
		this.cancelledList = cancelledList;
	}

	public Flight getElement() {
		return element;
	}

	public void setElement(Flight element) {
		this.element = element;
	}

	public FNode getNext() {
		return next;
	}

	public void setNext(FNode next) {
		this.next = next;
	}

	public FNode getPrev() {
		return prev;
	}

	public void setPrev(FNode prev) {
		this.prev = prev;
	}

	public PassengerLinkedList getPassengerList() {
		return passengerList;
	}

	public void setPassengerList(PassengerLinkedList passengerList) {
		this.passengerList = passengerList;
	}

	public Queue getRegularQueue() {
		return regularQueue;
	}

	public void setRegularQueue(Queue regularQueue) {
		this.regularQueue = regularQueue;
	}

	public Queue getVIPQueue() {
		return VIPQueue;
	}

	public void setVIPQueue(Queue vIPQueue) {
		VIPQueue = vIPQueue;
	}

	public Stack getUndoStack() {
		return undoStack;
	}

	public void setUndoStack(Stack undoStack) {
		this.undoStack = undoStack;
	}

	public Stack getRedoStack() {
		return redoStack;
	}

	public void setRedoStack(Stack redoStack) {
		this.redoStack = redoStack;
	}

	public PassengerLinkedList getAllPassengersList() {
		return AllPassengersList;
	}

	public void setAllPassengersList(PassengerLinkedList allPassengersList) {
		AllPassengersList = allPassengersList;
	}

}