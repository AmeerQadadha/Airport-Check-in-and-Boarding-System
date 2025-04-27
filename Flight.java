package application;

//Ameer Qadadha - 1221147
public class Flight {
	private int flightID;
	private String destination;
	private String status;
	private PassengerLinkedList plist;
	private PassengerLinkedList clist;

	public Flight() {
		this.plist = new PassengerLinkedList();
		this.clist = new PassengerLinkedList();
	}

	public Flight(int flightID, String destination, String status) {
		this.flightID = flightID;
		this.destination = destination;
		this.status = status;
	}

	public int getFlightID() {
		return flightID;
	}

	public void setFlightID(int flightID) {
		this.flightID = flightID;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public PassengerLinkedList getPlist() {
		return plist;
	}

	public void setPlist(PassengerLinkedList plist) {
		this.plist = plist;
	}

	@Override
	public String toString() {
		return "Flight [flightID=" + flightID + ", destination=" + destination + ", status=" + status + "]";
	}

	public PassengerLinkedList getClist() {
		return clist;
	}

	public void setClist(PassengerLinkedList clist) {
		this.clist = clist;
	}

}
