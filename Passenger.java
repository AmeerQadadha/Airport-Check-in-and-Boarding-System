package application;

//Ameer Qadadha - 1221147
public class Passenger {
	private int passengerID;
	private String passengerName;
	private int flightID;
	private String passengerStatus;

	public Passenger() {

	}

	public Passenger(int passengerID, String passengerName, int flightID, String passengerStatus) {
		this.passengerID = passengerID;
		this.passengerName = passengerName;
		this.flightID = flightID;
		this.passengerStatus = passengerStatus;
	}

	public int getPassengerID() {
		return passengerID;
	}

	public void setPassengerID(int passengerID) {
		this.passengerID = passengerID;
	}

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public int regularQueue() {
		return flightID;
	}

	public int getFlightID() {
		return flightID;
	}

	public void setFlightID(int flightID) {
		this.flightID = flightID;
	}

	public String getPassengerStatus() {
		return passengerStatus;
	}

	public void setPassengerStatus(String passengerStatus) {
		this.passengerStatus = passengerStatus;
	}

	public String toString2() {
		return "Passenger [passengerID=" + passengerID + ", passengerName=" + passengerName + ", flightID=" + flightID
				+ ", passengerStatus=" + passengerStatus + "]";
	}

	@Override
	public String toString() {
		return "Passenger [passengerName=" + passengerName + ", flightID=" + flightID + "]";
	}

}
