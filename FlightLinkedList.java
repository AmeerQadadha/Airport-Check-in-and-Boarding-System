package application;

import javafx.scene.control.TableView;

//Ameer Qadadha - 1221147
public class FlightLinkedList {
	private FNode front;// head of the double linked list
	private FNode back;// tail of the linked list
	private FNode current;// current node for to use in methods
	private int size;// size of the linked list

	// default constructor
	public FlightLinkedList() {

	}

	// constructor with attributes
	public FlightLinkedList(FNode front, FNode back, FNode current) {
		this.front = front;
		this.back = back;
		this.current = current;
	}

	public boolean isEmpty() {
		if (size == 0) {
			return true;
		}
		return false;
	}

	public void insertFlight(int flightID, String destination, String status) {
		Flight flight = new Flight(flightID, destination, status);
		FNode newNode = new FNode(flight);
		if (isEmpty()) {
			front = back = newNode;
			size++;
			return;
		}
		FNode current = front;
		while (current != null) {
			if (current.getElement().getFlightID() == flightID) {
				return;
			}
			current = current.getNext();
		}
		if (flightID < front.getElement().getFlightID()) {
			newNode.setNext(front);
			front.setPrev(newNode);
			front = newNode;
			size++;
			return;
		}
		current = front;
		while (current.getNext() != null && flightID > current.getNext().getElement().getFlightID()) {
			current = current.getNext();
		}

		newNode.setNext(current.getNext());
		newNode.setPrev(current);

		if (current.getNext() != null) {
			current.getNext().setPrev(newNode);
		} else {
			back = newNode;
		}

		current.setNext(newNode);
		size++;
	}

	public void updateFlight(int flightID, String destination, String status) {
		FNode current = front;
		while (current != null) {
			if (current.getElement().getFlightID() == flightID) {
				current.getElement().setFlightID(flightID);
				current.getElement().setDestination(destination);
				current.getElement().setStatus(status);
				return;
			} else {
				current = current.getNext();
			}
		}
		return;
	}

	public void deleteFlight(int flightID) {
		if (isEmpty()) {
			return;
		}
		if (size == 1) {
			front = back = null;
			size--;
			return;
		}
		FNode current = front;
		while (current != null) {
			if (current.getElement().getFlightID() == flightID) {
				if (current == front) {
					front = front.getNext();
					size--;
					return;
				} else if (current == back) {
					back = current.getPrev();
					back.setNext(null);
					size--;
					return;
				} else {
					current.getPrev().setNext(current.getNext());
					current.getNext().setPrev(current.getPrev());
					size--;
					return;
				}
			}
			current = current.getNext();
		}
	}

	public Flight searchFlight(int flightID, String destination) {
		if (isEmpty()) {
			return null;
		}
		FNode current = front;
		while (current != null) {
			Flight flight = current.getElement();
			if (flight.getFlightID() == flightID
					|| (destination != null && flight.getDestination().equalsIgnoreCase(destination))) {
				return flight;
			}
			current = current.getNext();
		}
		return null;
	}

	public boolean searchFlightID(int flightID) {
		if (isEmpty()) {
			return false;
		}
		FNode current = front;
		while (current != null) {
			Flight flight = current.getElement();
			if (flight.getFlightID() == flightID) {
				return true;
			}
			current = current.getNext();
		}
		return false;
	}

	public Flight printSpecificFlight(int flightID, String destination) {
		if (isEmpty()) {
			return null;
		}
		FNode current = front;
		while (current != null) {
			Flight flight = current.getElement();
			if (flight.getFlightID() == flightID
					|| (destination != null && flight.getDestination().equalsIgnoreCase(destination))) {
				return flight;
			}
			current = current.getNext();
		}
		return null;
	}

	public FNode getNode(int flightID) {
		FNode current = front;
		while (current != null) {
			if (flightID == current.getElement().getFlightID()) {
				return current;
			}
			current = current.getNext();
		}
		return null;
	}

	public String printAllFlights() {
		if (isEmpty()) {
			return null;
		}
		String flights = "";
		FNode current = front;
		while (current != null) {
			Flight flight = current.getElement();
			flights += flight.toString() + "\n";
			current = current.getNext();
		}
		return flights;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Flight printActiveFlights(TableView tableview) {
		if (isEmpty()) {
			return null;
		}
		FNode current = front;
		while (current != null) {
			Flight flight = current.getElement();
			if (flight.getStatus().equalsIgnoreCase("active")) {
				tableview.getItems().add(flight);
			}
			current = current.getNext();
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Flight printInactiveFlights(TableView tableview) {
		if (isEmpty()) {
			return null;
		}
		FNode current = front;
		while (current != null) {
			Flight flight = current.getElement();
			if (flight.getStatus().equalsIgnoreCase("inactive")) {
				tableview.getItems().add(flight);
			}
			current = current.getNext();
		}
		return null;
	}

	public FNode NavigateNext() {
		if (current == null) {
			if (front != null) {
				current = front.getNext();
			} else {
				current = null;
			}
		} else if (current.getNext() != null) {
			current = current.getNext();
		} else {
			return null;
		}
		return current;
	}

	public FNode NavigatePrev() {
		if (current == null) {
			if (front != null) {
				current = front.getPrev();
			} else {
				current = null;
			}
		} else if (current.getPrev() != null) {
			current = current.getPrev();
		} else {
			return null;
		}
		return current;
	}

	public FNode getFront() {
		return front;
	}

	public void setFront(FNode front) {
		this.front = front;
	}

	public FNode getBack() {
		return back;
	}

	public void setBack(FNode back) {
		this.back = back;
	}

	public FNode getCurrent() {
		return current;
	}

	public void setCurrent(FNode current) {
		this.current = current;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
