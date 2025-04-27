package application;

//Ameer Qadadha - 1221147
public class PassengerLinkedList {
	private BPNode front;// head of linked list
	private BPNode back;// tail of linked list
	private BPNode current;
	private CPNode Cfront;
	private CPNode Cback;
	private CPNode Ccurrent;
	private Queue VIPQueue;
	private Queue RegularQueue;
	private int size;
	private int queueVIP;
	private int queueRegular;
	private int boardedVIP;
	private int boardedRegular;
	public PassengerLinkedList() {

	}

	public PassengerLinkedList(BPNode front, BPNode back, BPNode current, CPNode Cfront, CPNode Cback,
			CPNode Ccurrent) {// Constructor with specified nodes
		this.front = front;
		this.back = back;
		this.current = current;
		this.Cfront = Cfront;
		this.Cback = Cback;
		this.Ccurrent = Ccurrent;
	}

	public boolean isEmpty() {
		if (size == 0) {
			return true;
		}
		return false;
	}

	public void checkIn(int passengerID, String passengerName, int flightID, String passengerStatus) {
		Passenger passenger = new Passenger(passengerID, passengerName, flightID, passengerStatus);
		if (passengerStatus.equalsIgnoreCase("VIP")) {
			VIPQueue.enQueue(passenger);
		} else {
			RegularQueue.enQueue(passenger);
		}
	}

	public Passenger BoardOneAtATime(FNode fnode, FlightLinkedList flightlist) {
		Queue vipQueue = fnode.getVIPQueue();
		Queue regularQueue = fnode.getRegularQueue();
		if (vipQueue != null && !vipQueue.isEmpty()) {
			Passenger vippass = vipQueue.deQueue();
			fnode.getPassengerList().addPassenger(vippass.getPassengerID(), vippass.getPassengerName(),
					vippass.getFlightID(), vippass.getPassengerStatus());
			queueVIP--;
			boardedVIP++;
			return vippass;
		}
		if (regularQueue != null && !regularQueue.isEmpty()) {
			Passenger regpass = regularQueue.deQueue();
			fnode.getPassengerList().addPassenger(regpass.getPassengerID(), regpass.getPassengerName(),
					regpass.getFlightID(), regpass.getPassengerStatus());
			queueRegular--;
			boardedRegular++;
			return regpass;
		}
		return null;
	}

	public void BoardAllPassengers(FNode fnode, FlightLinkedList flightlist) {
		Queue vipQueue = fnode.getVIPQueue();
		Queue regularQueue = fnode.getRegularQueue();

		while (vipQueue != null && !vipQueue.isEmpty()) {
			Passenger vippass = vipQueue.deQueue();
			fnode.getPassengerList().addPassenger(vippass.getPassengerID(), vippass.getPassengerName(),
					vippass.getFlightID(), vippass.getPassengerStatus());
			queueVIP--;
			boardedVIP++;
		}

		while (regularQueue != null && !regularQueue.isEmpty()) {
			Passenger regpass = regularQueue.deQueue();
			fnode.getPassengerList().addPassenger(regpass.getPassengerID(), regpass.getPassengerName(),
					regpass.getFlightID(), regpass.getPassengerStatus());
			queueRegular--;
			boardedRegular++;
		}
	}

	public boolean addPassenger(int passengerID, String passengerName, int flightID, String passengerStatus) {
		BPNode current2 = front;
		while (current2 != null) {
			if (current2.getElement().getPassengerID() == passengerID) {
				return false;
			}
			current2 = current2.getNext();
		}
		Passenger passenger = new Passenger(passengerID, passengerName, flightID, passengerStatus);
		BPNode newNode = new BPNode(passenger);
		if (isEmpty()) {
			front = back = newNode;
		} else if (passengerStatus.equalsIgnoreCase("VIP")) {
			newNode.setNext(front);
			front = newNode;
		} else {
			BPNode current = front;
			while (current.getNext() != null && current.getNext().getElement().getPassengerID() < passengerID) {
				current = current.getNext();
			}
			newNode.setNext(current.getNext());
			current.setNext(newNode);
		}
		size++;
		return true;
	}

	public void updatePassenger(int passengerID, String newName, String newStatus, int flightID) {
		BPNode current = front;
		while (current != null) {
			if (current.getElement().getPassengerID() == passengerID) {
				current.getElement().setPassengerName(newName);
				current.getElement().setPassengerStatus(newStatus);
				current.getElement().setFlightID(flightID);
				return;
			}
			current = current.getNext();
		}
		return;
	}

	public void DeletePassenger(int passengerID) {
		if (isEmpty()) {
			return;
		}
		if (size == 1) {
			if (front.getElement().getPassengerID() == passengerID) {
				front = back = null;
				size--;
			}
			return;
		}
		if (front.getElement().getPassengerID() == passengerID) {
			front = front.getNext();
			size--;
			return;
		}

		BPNode current = front;
		while (current != null && current.getNext() != null) {
			if (current.getNext().getElement().getPassengerID() == passengerID) {
				current.setNext(current.getNext().getNext());
				if (current.getNext() == null) {
					back = current;
				}
				size--;
				return;
			}
			current = current.getNext();
		}
	}

	public void addCancelledPassengers(Passenger passenger) {
	    CPNode newNode = new CPNode(passenger);
	    if (isEmpty()) {
	        Cfront = Cback = newNode;  // Initialize both front and back to the new node
	    } else {
	        newNode.setNext(Cfront);   // Insert the new node at the beginning of the list
	        Cfront = newNode;          // Update Cfront to point to the new node
	    }
	    size++;  // Increment the size of the list
	}


	public void deleteCancelledPassenger(int passengerID) {
		if (Cfront == null) {
			return;
		}

		if (Cfront.getElement().getPassengerID() == passengerID) {
			if (Cfront == Cback) {
				Cfront = Cback = null;
			} else {
				Cfront = Cfront.getNext();
			}
			size--;
			return;
		}

		CPNode current = Cfront;
		while (current.getNext() != null) {
			if (current.getNext().getElement().getPassengerID() == passengerID) {
				current.setNext(current.getNext().getNext());
				if (current.getNext() == null) {
					Cback = current;
				}
				size--;
				return;
			}
			current = current.getNext();
		}
	}

	public Passenger getBoardedPassenger(int passengerID) {
		BPNode currentPassenger = front;
		while (currentPassenger != null) {
			if (currentPassenger.getElement().getPassengerID()== passengerID) {
				return currentPassenger.getElement();
			}
			currentPassenger = currentPassenger.getNext();
		}
		return null;
	}
	public Passenger getCancelledPassenger(int passengerID) {
		CPNode currentPassenger = Cfront;
		while (currentPassenger != null) {
			if (currentPassenger.getElement().getPassengerID()== passengerID) {
				return currentPassenger.getElement();
			}
			currentPassenger = currentPassenger.getNext();
		}
		return null;
	}

	public BPNode getFront() {
		return front;
	}

	public void setFront(BPNode front) {
		this.front = front;
	}

	public BPNode getBack() {
		return back;
	}

	public void setBack(BPNode back) {
		this.back = back;
	}

	public BPNode getCurrent() {
		return current;
	}

	public void setCurrent(BPNode current) {
		this.current = current;
	}

	public CPNode getCfront() {
		return Cfront;
	}

	public void setCfront(CPNode cfront) {
		Cfront = cfront;
	}

	public CPNode getCback() {
		return Cback;
	}

	public void setCback(CPNode cback) {
		Cback = cback;
	}

	public CPNode getCcurrent() {
		return Ccurrent;
	}

	public void setCcurrent(CPNode ccurrent) {
		Ccurrent = ccurrent;
	}

	public Queue getVIPQueue() {
		return VIPQueue;
	}

	public void setVIPQueue(Queue vIPQueue) {
		VIPQueue = vIPQueue;
	}

	public Queue getRegularQueue() {
		return RegularQueue;
	}

	public void setRegularQueue(Queue regularQueue) {
		RegularQueue = regularQueue;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getQueueVIP() {
		return queueVIP;
	}

	public void setQueueVIP(int queueVIP) {
		this.queueVIP = queueVIP;
	}

	public int getQueueRegular() {
		return queueRegular;
	}

	public void setQueueRegular(int queueRegular) {
		this.queueRegular = queueRegular;
	}

	public int getBoardedVIP() {
		return boardedVIP;
	}

	public void setBoardedVIP(int boardedVIP) {
		this.boardedVIP = boardedVIP;
	}

	public int getBoardedRegular() {
		return boardedRegular;
	}

	public void setBoardedRegular(int boardedRegular) {
		this.boardedRegular = boardedRegular;
	}

}
