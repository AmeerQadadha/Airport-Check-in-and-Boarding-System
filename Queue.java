package application;

//Ameer Qadadha - 1221147
public class Queue {
	private QNode front;
	private QNode rear;
	private static int counter = -1;

	public Queue() {
	}

	public Queue(QNode front, QNode rear) {
		this.front = front;
		this.rear = rear;
	}

	public boolean isEmpty() {
		return front == null;
	}

	public void enQueue(Passenger pass) {
		QNode newNode = new QNode(pass);
		if (rear == null) {
			front = rear = newNode;
		} else {
			rear.setNext(newNode);
			rear = newNode;
		}
		counter++;
	}

	public Passenger deQueue() {
		QNode removedNode = front;
		if (isEmpty()) {
			return null;
		}
		if (front == rear) {
			front = rear = null;
			counter--;
		} else {
			front = front.getNext();
			removedNode.setNext(null);
			counter--;
		}
		return removedNode.getElement();
	}

	public Passenger peek() {
		if (front == null) {
			return null;
		}
		return front.getElement();
	}

	public boolean findPassenger(int passengerID) {
		QNode currentNode = front;
		while (currentNode != null) {
			if (currentNode.getElement().getPassengerID() == passengerID) {
				return true; // Return the type of the queue (VIP or Regular)
			}
			currentNode = currentNode.getNext();
		}
		return false;
	}

	public QNode getFront() {
		return front;
	}

	public void setFront(QNode front) {
		this.front = front;
	}

	public QNode getRear() {
		return rear;
	}

	public void setRear(QNode rear) {
		this.rear = rear;
	}

	public static int getCounter() {
		return counter;
	}

	public static void setCounter(int counter) {
		Queue.counter = counter;
	}

	public int Size() {
		return counter + 1;
	}
}
