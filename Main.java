package application;
//Ameer Qadadha - 1221147

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Main extends Application {
	private FlightLinkedList flightList;
	private PassengerLinkedList BoardedPassengerList;
	private PassengerLinkedList CancelledPasssngerList;
	private PassengerLinkedList AllPassengersList;
	private Queue vipQueue;
	private Queue regularQueue;
	private Stack undoStack;
	private Stack redoStack;
	private int cancelledVIP;
	private int cancelledRegular;
	private int queueVIP;
	private int queueRegular;
	private int boardedVIP;
	private int boardedRegular;

	@SuppressWarnings({ "unchecked" })
	@Override
	public void start(Stage primaryStage) {
		try {
			if (flightList == null) {
				flightList = new FlightLinkedList();
			}
			if (BoardedPassengerList == null) {
				BoardedPassengerList = new PassengerLinkedList();
			}
			if (CancelledPasssngerList == null) {
				CancelledPasssngerList = new PassengerLinkedList();
			}
			if (vipQueue == null) {
				vipQueue = new Queue();
			}
			if (regularQueue == null) {
				regularQueue = new Queue();
			}
			if (undoStack == null) {
				undoStack = new Stack();
			}
			if (redoStack == null) {
				redoStack = new Stack();
			}
			if (AllPassengersList == null) {
				AllPassengersList = new PassengerLinkedList();
			}

			BorderPane root = new BorderPane();
			Label title = new Label("Airport Check-in and Boarding System");
			MenuBar menubar = new MenuBar();
			Menu menu1 = new Menu("Flight Management");// initializing all the menu with their menu items
			MenuItem readFlight = new MenuItem("Read Flights File");
			MenuItem addFlight = new MenuItem("Add Flight");
			MenuItem updateFlight = new MenuItem("Update Flight");
			MenuItem removeFlight = new MenuItem("Remove Flight");
			MenuItem searchFlight = new MenuItem("Search For Flight");
			MenuItem printAll = new MenuItem("Print All Flights");
			MenuItem printSpec = new MenuItem("Print A Specific Flight");
			MenuItem displayactive = new MenuItem("Display Active Flights");
			MenuItem displayinactive = new MenuItem("Display Inactive Flights");
			MenuItem navigate = new MenuItem("Navigate Between Flights");
			menu1.getItems().addAll(readFlight, addFlight, updateFlight, removeFlight, searchFlight, printAll,
					printSpec, displayactive, displayinactive, navigate);

			Menu menu2 = new Menu("Passenger Management");
			MenuItem readPassenger = new MenuItem("Read Passenger File");
			MenuItem addPassenger = new MenuItem("Add Passenger");
			MenuItem updatePassenger = new MenuItem("Update Passenger");
			MenuItem removePassnger = new MenuItem("Remove Passenger");// initializing all the menu with their menu
																		// items
			MenuItem searchPassenger = new MenuItem("Search For Passenger");
			MenuItem printeveryone = new MenuItem("Print All Passengers");
			MenuItem printAllPassengers = new MenuItem("Print All Boarded Passengers");
			MenuItem printCancelledPassengers = new MenuItem("Print All Cancelled Passengers");
			MenuItem printinQueue = new MenuItem("Print Queue Passengers");
			MenuItem printSpecPassenger = new MenuItem("Print Specific Passenger Info");
			menu2.getItems().addAll(readPassenger, addPassenger, updatePassenger, removePassnger, searchPassenger,
					printeveryone, printSpecPassenger, printAllPassengers, printCancelledPassengers, printinQueue);

			Menu menu3 = new Menu("Operation Menu");
			MenuItem checkin = new MenuItem("Check-in Passneger");// initializing all the menu with their menu items
			MenuItem boardP = new MenuItem("Board Passenger");
			MenuItem cancelP = new MenuItem("Cancel Passenger");
			MenuItem undo = new MenuItem("Undo");
			MenuItem redo = new MenuItem("Redo");
			menu3.getItems().addAll(checkin, boardP, cancelP, undo, redo);

			Menu menu4 = new Menu("Log File Menu");
			MenuItem print = new MenuItem("Print Log File");
			MenuItem export = new MenuItem("Export Log File");// initializing all the menu with their menu items
			menu4.getItems().addAll(print, export);

			Menu menu5 = new Menu("Statistical Menu");
			MenuItem showStats = new MenuItem("Display All Statistics");

			menu5.getItems().addAll(showStats);

			Label data = new Label();

			menu1.setStyle(
					"-fx-font-family: 'Serif';-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
			menu2.setStyle(
					"-fx-font-family: 'Serif';-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
			menu3.setStyle(
					"-fx-font-family: 'Serif';-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");// styles
			menu4.setStyle(
					"-fx-font-family: 'Serif';-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
			menu5.setStyle(
					"-fx-font-family: 'Serif';-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
			title.setStyle(
					"-fx-font-family: 'Serif'; -fx-font-size: 27px; -fx-font-weight: bold; -fx-text-fill: black;-fx-underline: true;");
			data.setStyle(
					"-fx-font-family: 'Serif';-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black; -fx-underline: true;");

			menubar.getMenus().addAll(menu1, menu2, menu3, menu4, menu5);

			VBox vbox = new VBox();
			vbox.getChildren().addAll(menubar, title, data);
			vbox.setAlignment(Pos.TOP_CENTER);

			readFlight.setOnAction(e -> {
				FileChooser fileChooser = new FileChooser();
				File file = fileChooser.showOpenDialog(primaryStage);// reading from files.txt and saving into a double
																		// linked list
				if (file != null) {
					try (Scanner scanner = new Scanner(file)) {// using scanner to read from file
						if (scanner.hasNextLine()) {
							scanner.nextLine();
						}
						while (scanner.hasNextLine()) {
							String line = scanner.nextLine().trim();
							String[] parts = line.split(",");
							if (parts.length == 3) {
								try {
									int flightID = Integer.parseInt(parts[0].trim());
									String destination = parts[1].trim();
									String status = parts[2].trim();
									flightList.insertFlight(flightID, destination, status);// adding the read data into
																							// the linked list
								} catch (NumberFormatException ex) {
									ex.printStackTrace();
								}
							} else {
							}
						}
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
					Alert readData = new Alert(Alert.AlertType.INFORMATION);// alert to inform saving and reading
					readData.setHeaderText("Success!");
					readData.setContentText("Data was read and saved successfully");
					readData.showAndWait();
				}
			});
			readPassenger.setOnAction(e -> {
				FileChooser fileChooser = new FileChooser();
				File file = fileChooser.showOpenDialog(primaryStage);
				if (file != null) {
					try (Scanner scanner = new Scanner(file)) {
						if (scanner.hasNextLine()) {
							scanner.nextLine();
						}
						while (scanner.hasNextLine()) {
							String line = scanner.nextLine().trim();
							String[] parts = line.split(",");// reading passengers and saving them in a linked list in
																// the flight node
							if (parts.length == 4) {
								try {
									int passengerID = Integer.parseInt(parts[0].trim());
									String passName = parts[1].trim();
									int fightID = Integer.parseInt(parts[2].trim());
									String status = parts[3].trim();
									flightList.getNode(fightID).getAllPassengersList().addPassenger(passengerID,
											passName, fightID, status);
									if (flightList.getNode(fightID) == null) {
										Alert readData = new Alert(Alert.AlertType.INFORMATION);
										readData.setHeaderText("Error!");
										readData.setContentText("Load Flights Data First!");// alert to check if the
																							// flights have been loaded
																							// first!
										readData.showAndWait();
										return;
									}
								} catch (NumberFormatException ex) {
									ex.printStackTrace();
								}
							} else {
							}
						}
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
					Alert readData = new Alert(Alert.AlertType.INFORMATION);// alert to inform saving and reading
					readData.setHeaderText("Success!");
					readData.setContentText("Data was read and saved successfully");
					readData.showAndWait();
				}
			});

			TableView<Flight> tableView = new TableView<>();

			TableColumn<Flight, Integer> flightIDColumn2 = new TableColumn<>("Flight ID");
			flightIDColumn2.setCellValueFactory(new PropertyValueFactory<>("flightID"));
			flightIDColumn2.setPrefWidth(100);

			TableColumn<Flight, String> destinationColumn2 = new TableColumn<>("Destination");// making a table view
																								// with its columns
			destinationColumn2.setCellValueFactory(new PropertyValueFactory<>("destination"));
			destinationColumn2.setPrefWidth(130);

			TableColumn<Flight, String> statusColumn2 = new TableColumn<>("Status");
			statusColumn2.setCellValueFactory(new PropertyValueFactory<>("status"));
			statusColumn2.setPrefWidth(100);
			tableView.getColumns().addAll(flightIDColumn2, destinationColumn2, statusColumn2);
			tableView.setMaxWidth(550);
			tableView.setMaxHeight(420);

			TableView<Passenger> tableView2 = new TableView<>();

			TableColumn<Passenger, Integer> passngerIDColumn = new TableColumn<>("Pass-ID");
			passngerIDColumn.setCellValueFactory(new PropertyValueFactory<>("passengerID"));
			passngerIDColumn.setPrefWidth(70);

			TableColumn<Passenger, String> passengerNameColumn = new TableColumn<>("Pass-Name");
			passengerNameColumn.setCellValueFactory(new PropertyValueFactory<>("passengerName"));// making a table view
																									// with its columns
			passengerNameColumn.setPrefWidth(70);

			TableColumn<Passenger, Integer> flightIDColumn = new TableColumn<>("Flight ID");
			flightIDColumn.setCellValueFactory(new PropertyValueFactory<>("flightID"));
			flightIDColumn.setPrefWidth(70);

			TableColumn<Passenger, String> statusColumn = new TableColumn<>("Status");
			statusColumn.setCellValueFactory(new PropertyValueFactory<>("passengerStatus"));// making a table view with
																							// its columns
			statusColumn.setPrefWidth(70);
			tableView2.setMaxWidth(300);
			tableView2.setMaxHeight(250);

			tableView2.getColumns().addAll(passngerIDColumn, passengerNameColumn, flightIDColumn, statusColumn);

			TableView<Passenger> tableView3 = new TableView<>();

			TableColumn<Passenger, Integer> passngerIDColumn3 = new TableColumn<>("Pass-ID");
			passngerIDColumn3.setCellValueFactory(new PropertyValueFactory<>("passengerID"));// making a table view with
																								// its columns
			passngerIDColumn3.setPrefWidth(70);

			TableColumn<Passenger, String> passengerNameColumn3 = new TableColumn<>("Pass-Name");
			passengerNameColumn3.setCellValueFactory(new PropertyValueFactory<>("passengerName"));
			passengerNameColumn3.setPrefWidth(70);

			TableColumn<Passenger, Integer> flightIDColumn3 = new TableColumn<>("FlightID");
			flightIDColumn3.setCellValueFactory(new PropertyValueFactory<>("flightID"));// making a table view with its
																						// columns
			flightIDColumn3.setPrefWidth(70);

			TableColumn<Passenger, String> statusColumn3 = new TableColumn<>("Status");
			statusColumn3.setCellValueFactory(new PropertyValueFactory<>("passengerStatus"));
			statusColumn3.setPrefWidth(70);
			tableView3.setMaxWidth(300);
			tableView3.setMaxHeight(250);

			tableView3.getColumns().addAll(passngerIDColumn3, passengerNameColumn3, flightIDColumn3, statusColumn3);

			TableView<Passenger> tableView4 = new TableView<>();

			TableColumn<Passenger, Integer> passngerIDColumn4 = new TableColumn<>("Pass-ID");
			passngerIDColumn4.setCellValueFactory(new PropertyValueFactory<>("passengerID"));
			passngerIDColumn4.setPrefWidth(70);

			TableColumn<Passenger, String> passengerNameColumn4 = new TableColumn<>("Pass-Name");
			passengerNameColumn4.setCellValueFactory(new PropertyValueFactory<>("passengerName"));// making a table view
																									// with its columns
			passengerNameColumn4.setPrefWidth(70);

			TableColumn<Passenger, Integer> flightIDColumn4 = new TableColumn<>("FlightID");
			flightIDColumn4.setCellValueFactory(new PropertyValueFactory<>("flightID"));
			flightIDColumn4.setPrefWidth(70);

			TableColumn<Passenger, String> statusColumn4 = new TableColumn<>("Status");
			statusColumn4.setCellValueFactory(new PropertyValueFactory<>("passengerStatus"));// making a table view with
																								// its columns
			statusColumn4.setPrefWidth(70);
			tableView4.setMaxWidth(300);
			tableView4.setMaxHeight(250);

			tableView4.getColumns().addAll(passngerIDColumn4, passengerNameColumn4, flightIDColumn4, statusColumn4);

			TableView<Passenger> tableView5 = new TableView<>();

			TableColumn<Passenger, Integer> passngerIDColumn5 = new TableColumn<>("Pass-ID");
			passngerIDColumn5.setCellValueFactory(new PropertyValueFactory<>("passengerID"));
			passngerIDColumn5.setPrefWidth(100);

			TableColumn<Passenger, String> passengerNameColumn5 = new TableColumn<>("Pass-Name");
			passengerNameColumn5.setCellValueFactory(new PropertyValueFactory<>("passengerName"));// making a table view
																									// with its columns
			passengerNameColumn5.setPrefWidth(100);

			TableColumn<Passenger, Integer> flightIDColumn5 = new TableColumn<>("FlightID");
			flightIDColumn5.setCellValueFactory(new PropertyValueFactory<>("flightID"));
			flightIDColumn5.setPrefWidth(100);// making a table view with its columns

			TableColumn<Passenger, String> statusColumn5 = new TableColumn<>("Status");
			statusColumn5.setCellValueFactory(new PropertyValueFactory<>("passengerStatus"));// making a table view with
																								// its columns
			statusColumn5.setPrefWidth(100);
			tableView5.setMaxWidth(500);
			tableView5.setMaxHeight(450);

			tableView5.getColumns().addAll(passngerIDColumn5, passengerNameColumn5, flightIDColumn5, statusColumn5);

			addFlight.setOnAction(e -> { // calling different stages
				addFlights(primaryStage);
			});

			updateFlight.setOnAction(e -> { // calling different stages
				updateFlight(primaryStage);
			});

			removeFlight.setOnAction(e -> { // calling different stages
				deleteFlight(primaryStage);
			});

			searchFlight.setOnAction(e -> { // calling different stages
				searchFlight(primaryStage);
			});
			printAll.setOnAction(e -> {
				if (flightList.getFront() == null) {
					data.setText("No flights available");
					return;
				}
				tableView.getItems().clear();
				root.setCenter(tableView);
				data.setText("All Flights: ");// print all flights in the linked list
				FNode current = flightList.getFront();
				while (current != null) {
					Flight flight = current.getElement();
					tableView.getItems().add(flight);
					current = current.getNext();
				}
			});
			printSpec.setOnAction(e -> { // calling different stages
				printSpec(primaryStage);
			});
			displayactive.setOnAction(e -> {// displaying active flights
				if (flightList.getFront() == null) {
					data.setText("No flights available");
					return;
				}
				tableView.getItems().clear();
				data.setText("All Active Flights: ");
				root.setCenter(tableView);
				flightList.printActiveFlights(tableView);
			});
			displayinactive.setOnAction(e -> {// displaying inactive flights
				if (flightList.getFront() == null) {
					data.setText("No flights available");
					return;
				}
				tableView.getItems().clear();
				data.setText("All Inactive Flights: ");
				root.setCenter(tableView);
				flightList.printInactiveFlights(tableView);
			});
			navigate.setOnAction(e -> {
				root.setLeft(null);// navigating through each plan with the cancelled boarded and enqueued
									// passengers
				root.setCenter(null);
				FNode fnode2 = flightList.getFront();
				Label first = new Label();
				Label second = new Label();
				Label third = new Label();
				if (fnode2 == null) {
					data.setText("No flights available");
					return;
				}

				tableView2.getItems().clear();
				data.setText(fnode2.getElement().toString());

				BPNode bnode = fnode2.getPassengerList().getFront();
				CPNode cnode = fnode2.getCancelledList().getCfront();
				Queue vipQueue = fnode2.getVIPQueue();
				Queue regularQueue = fnode2.getRegularQueue();// stars of all data structures
				while (bnode != null) {
					Passenger passenger = bnode.getElement();
					tableView2.getItems().add(passenger);// appending to the table view
					bnode = bnode.getNext();
				}
				while (cnode != null) {
					Passenger passenger = cnode.getElement();
					tableView4.getItems().add(passenger);// appending to the table view
					cnode = cnode.getNext();
				}
				if (vipQueue != null) {
					QNode currentNode = vipQueue.getFront();
					while (currentNode != null) {
						Passenger passenger = currentNode.getElement();
						tableView3.getItems().add(passenger);// appending to the table view
						currentNode = currentNode.getNext();
					}
				}
				if (regularQueue != null) {
					QNode currentNode = regularQueue.getFront();
					while (currentNode != null) {
						Passenger passenger = currentNode.getElement();
						tableView3.getItems().add(passenger);// appending to the table view
						currentNode = currentNode.getNext();
					}
				}

				Button next = new Button("Next Flight");
				Button prev = new Button("Previous Flight");
				next.setPrefHeight(50);
				next.setPrefWidth(130);
				prev.setPrefHeight(50);// navigating buttons + styles
				prev.setPrefWidth(170);
				next.setStyle(
						"-fx-font-family: 'Serif';-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: black;");
				prev.setStyle(
						"-fx-font-family: 'Serif';-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: black;");
				first.setStyle(
						"-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");
				second.setStyle(
						"-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");
				third.setStyle(
						"-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");

				next.setOnAction(e2 -> {
					FNode fnode3 = flightList.NavigateNext();
					if (fnode3 != null) {
						data.setText(fnode3.getElement().toString());

						tableView2.getItems().clear();// clearing all table views to ensure correct display
						tableView3.getItems().clear();
						tableView4.getItems().clear();
						BPNode bnode2 = fnode3.getPassengerList().getFront();
						QNode QNode = fnode3.getVIPQueue().getFront();
						QNode QNode2 = fnode3.getRegularQueue().getFront();
						CPNode cnode2 = fnode3.getCancelledList().getCfront();
						while (bnode2 != null) {
							tableView2.getItems().add(bnode2.getElement());
							bnode2 = bnode2.getNext();// appending to the table view
						}
						while (cnode2 != null) {
							Passenger passenger = cnode2.getElement();
							tableView4.getItems().add(passenger);
							cnode2 = cnode2.getNext();// appending to the table view
						}
						while (QNode != null) {
							Passenger passenger = QNode.getElement();
							tableView3.getItems().add(passenger);// appending to the table view
							QNode = QNode.getNext();
						}
						while (QNode2 != null) {
							Passenger passenger = QNode2.getElement();
							tableView3.getItems().add(passenger);// appending to the table view
							QNode2 = QNode2.getNext();
						}

					} else {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setHeaderText("ERROR");
						alert.setContentText("End of flight list");// alert to inform that its the end of the flight
																	// list
						alert.showAndWait();
					}
				});

				prev.setOnAction(e3 -> {
					FNode fnode3 = flightList.NavigatePrev();
					if (fnode3 != null) {
						data.setText(fnode3.getElement().toString());// setting flight name on the top

						tableView2.getItems().clear();
						tableView3.getItems().clear();// clearing all table views
						tableView4.getItems().clear();

						BPNode bnode2 = fnode3.getPassengerList().getFront();// assigning fronts for the lists and the
																				// queues for correct iteration
						CPNode cnode2 = fnode3.getCancelledList().getCfront();
						QNode QNode = fnode3.getVIPQueue().getFront();
						QNode QNode2 = fnode3.getRegularQueue().getFront();
						while (bnode2 != null) {
							tableView2.getItems().add(bnode2.getElement());// appending to the table view
							bnode2 = bnode2.getNext();
						}
						while (cnode2 != null) {
							Passenger passenger = cnode2.getElement();
							tableView4.getItems().add(passenger);// appending to the table view
							cnode2 = cnode2.getNext();
						}
						while (QNode != null) {
							Passenger passenger = QNode.getElement();
							tableView3.getItems().add(passenger);// appending to the table view
							QNode = QNode.getNext();
						}
						while (QNode2 != null) {
							Passenger passenger = QNode2.getElement();
							tableView3.getItems().add(passenger);// appending to the table view
							QNode2 = QNode2.getNext();
						}
					} else {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setHeaderText("ERROR");
						alert.setContentText("Start of flight list");
						alert.showAndWait();
					}
				});

				HBox hbox = new HBox();
				hbox.getChildren().addAll(prev, next);
				hbox.setAlignment(Pos.CENTER);

				first.setText("Boarded Passengers");
				second.setText("Passengers in Queue");
				third.setText("Cancelled Passengers");

				VBox vbox3 = new VBox();
				vbox3.getChildren().addAll(first, tableView2);

				VBox vbox4 = new VBox();
				vbox4.getChildren().addAll(second, tableView3);

				VBox vbox5 = new VBox();
				vbox5.getChildren().addAll(third, tableView4);

				HBox hbox2 = new HBox(40);
				hbox2.getChildren().addAll(vbox3, vbox4, vbox5);

				VBox vbox2 = new VBox();
				vbox2.getChildren().addAll(hbox2, hbox);
				vbox2.setAlignment(Pos.CENTER);
				root.setCenter(vbox2);

			});
			addPassenger.setOnAction(e -> {// calling different stages with their methods
				addPassenger(primaryStage);
			});

			updatePassenger.setOnAction(e -> {// calling different stages with their methods
				updatePassenger(primaryStage);
			});
			removePassnger.setOnAction(e -> {// calling different stages with their methods
				deletePassenger(primaryStage);
			});
			searchPassenger.setOnAction(e -> {// calling different stages with their methods
				searchPassenger(primaryStage);
			});

			printinQueue.setOnAction(e -> {// printing only enqueued passengers
				tableView5.getItems().clear();

				FNode fnode2 = flightList.getFront();
				data.setText("All Passengers In Queue: ");
				root.setCenter(tableView5);
				root.setLeft(null);
				while (fnode2 != null) {
					Queue vipQueue = fnode2.getVIPQueue();
					if (vipQueue != null) {
						QNode currentNode = vipQueue.getFront();// looping in the vip queue then the regular queue
						while (currentNode != null) {
							Passenger passenger = currentNode.getElement();
							tableView5.getItems().add(passenger);// appending to the table view
							currentNode = currentNode.getNext();
						}
					}
					Queue regularQueue = fnode2.getRegularQueue();
					if (regularQueue != null) {
						QNode currentNode = regularQueue.getFront();
						while (currentNode != null) {
							Passenger passenger = currentNode.getElement();
							tableView5.getItems().add(passenger);// appending to the table view
							currentNode = currentNode.getNext();
						}
					}
					fnode2 = fnode2.getNext();// next flight node
				}
			});

			printeveryone.setOnAction(e -> {
				tableView5.getItems().clear();
				root.setLeft(null);
				data.setText("All Passengers: ");
				root.setCenter(tableView5);
				FNode fnode2 = flightList.getFront();// printing ALL data that have been read from passengers.txt
				while (fnode2 != null) {
					BPNode bnode = fnode2.getAllPassengersList().getFront();
					while (bnode != null) {
						Passenger passenger = bnode.getElement();
						tableView5.getItems().add(passenger);// appending to the table view
						bnode = bnode.getNext();// next node
					}
					fnode2 = fnode2.getNext();
				}
			});

			printAllPassengers.setOnAction(e -> {
				root.setLeft(null);
				tableView5.getItems().clear();
				data.setText("All Boarded Passengers: ");
				root.setCenter(tableView5);// printing and displaying boarded passengers
				FNode fnode2 = flightList.getFront();
				while (fnode2 != null) {
					BPNode bnode = fnode2.getPassengerList().getFront();
					while (bnode != null) {
						Passenger passenger = bnode.getElement();
						tableView5.getItems().add(passenger);// appending on the table view
						bnode = bnode.getNext();
					}
					fnode2 = fnode2.getNext();
				}
			});

			printCancelledPassengers.setOnAction(e -> {
				root.setLeft(null);
				tableView5.getItems().clear();
				data.setText("All Cancelled Passengers: ");// printing and displaying cancelled passengers
				root.setCenter(tableView5);
				FNode fnode2 = flightList.getFront();
				while (fnode2 != null) {
					CPNode cnode = fnode2.getCancelledList().getCfront();
					while (cnode != null) {
						Passenger passenger = cnode.getElement();// appending on the table view
						tableView5.getItems().add(passenger);
						cnode = cnode.getNext();
					}
					fnode2 = fnode2.getNext();
				}
			});

			printSpecPassenger.setOnAction(e -> {// different buttons with different stages
				printspecificPassenger(primaryStage);
			});

			checkin.setOnAction(e -> {// different buttons with different stages
				checkinPassenger(primaryStage);
			});
			boardP.setOnAction(e -> {// different buttons with different stages
				boardPassengers(primaryStage);
			});
			cancelP.setOnAction(e -> {// different buttons with different stages
				cancelPassenger(primaryStage);
			});
			undo.setOnAction(e -> {
				if (undoStack.peek() == null) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText("Empty Stack");
					alert.setContentText("No operations done to undo");
					alert.showAndWait();
					return;
				}
				String operation = undoStack.pop();// popping the last operation done
				String[] parts = operation.split(" ");
				String action = parts[0];
				int flightID = Integer
						.parseInt(operation.substring(operation.indexOf("Flight ") + 7, operation.indexOf(")")));// saving
																													// flight
																													// id
				String id1 = parts[2];
				String id2 = id1.substring(3);
				Integer idop = Integer.parseInt(id2);// saving passenger id
				redoStack.push(operation);// pushing the operation in the redo stack
				FNode fnode = flightList.getNode(flightID);
				if (action.equalsIgnoreCase("CheckIn")) {// checking different action/operation cases
					if (fnode != null) {
						if (fnode.getVIPQueue().findPassenger(idop) == true) {
							Passenger passenger2 = fnode.getVIPQueue().peek();
							fnode.getVIPQueue().deQueue();// dequeueing from the queue and inserting back to the all
															// passenger list
							fnode.getAllPassengersList().addPassenger(passenger2.getPassengerID(),
									passenger2.getPassengerName(), passenger2.getFlightID(),
									passenger2.getPassengerStatus());
							Alert alert = new Alert(Alert.AlertType.INFORMATION);
							alert.setHeaderText("Success");
							alert.setContentText("Passenger was removed from Queue");// success alert
							alert.showAndWait();
							queueVIP--;// decrementing counter after removal
							try (BufferedWriter writer = new BufferedWriter(
									new FileWriter("C:\\Users\\My Technology\\Documents\\Ds\\log.txt", true))) {// writing
																												// to
																												// the
																												// log
																												// file
								writer.write(
										String.format("%s | Undo | Check-in | %s | %d | Checked-in %s for Flight %d%n",
												LocalDate.now(), passenger2.getPassengerName(), flightID,
												passenger2.getPassengerName(), flightID));
							} catch (IOException ex) {
								ex.printStackTrace();
							}
							return;
						} else {
							Passenger passenger2 = fnode.getRegularQueue().peek();
							fnode.getRegularQueue().deQueue();// removing from the regular queue and adding back to the
																// all passengers list
							fnode.getAllPassengersList().addPassenger(passenger2.getPassengerID(),
									passenger2.getPassengerName(), passenger2.getFlightID(),
									passenger2.getPassengerStatus());
							Alert alert = new Alert(Alert.AlertType.INFORMATION);
							alert.setHeaderText("Success");
							alert.setContentText("Passenger was removed from Queue");// success message
							alert.showAndWait();
							queueRegular--;// decrementing counter after removal
							try (BufferedWriter writer = new BufferedWriter(
									new FileWriter("C:\\Users\\My Technology\\Documents\\Ds\\log.txt", true))) {
								writer.write(
										String.format("%s | Undo | Check-in | %s | %d | Checked-in %s for Flight %d%n",
												LocalDate.now(), passenger2.getPassengerName(), flightID,
												passenger2.getPassengerName(), flightID));// appending onto the log file
							} catch (IOException ex) {
								ex.printStackTrace();
							}
							return;
						}

					}
				} else if (action.equalsIgnoreCase("Board")) {// removing from the boarded list and queueing back to the
																// appropriate queue
					Passenger pass = fnode.getPassengerList().getBoardedPassenger(idop);
					if (pass.getPassengerStatus().equalsIgnoreCase("vip")) {
						fnode.getVIPQueue().enQueue(pass);
						fnode.getPassengerList().DeletePassenger(pass.getPassengerID());
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setHeaderText("Success");
						alert.setContentText("Passenger was un-boarded and set back to Queue");// success alert
						alert.showAndWait();
						queueVIP++;
						boardedVIP--;
						try (BufferedWriter writer = new BufferedWriter(
								new FileWriter("C:\\Users\\My Technology\\Documents\\Ds\\log.txt", true))) {
							writer.write(String.format("%s | Undo | Boarding | %s | %d | Board %s for Flight %d%n",
									LocalDate.now(), pass.getPassengerName(), flightID, pass.getPassengerName(), // appending
																													// onto
																													// the
																													// log
																													// file
									flightID));
						} catch (IOException ex) {
							ex.printStackTrace();
						}
						return;
					} else {
						fnode.getRegularQueue().enQueue(pass);
						fnode.getPassengerList().DeletePassenger(pass.getPassengerID());
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setHeaderText("Success");
						alert.setContentText("Passenger was un-boarded and set back to Queue");
						alert.showAndWait();
						queueRegular++;// decrementing+incrementing counters
						boardedRegular--;
						try (BufferedWriter writer = new BufferedWriter(
								new FileWriter("C:\\Users\\My Technology\\Documents\\Ds\\log.txt", true))) {
							writer.write(String.format("%s | Undo | Boarding | %s | %d | Board %s for Flight %d%n",
									LocalDate.now(), pass.getPassengerName(), flightID, pass.getPassengerName(),
									flightID));// appending onto the log file
						} catch (IOException ex) {
							ex.printStackTrace();
						}
						return;
					}
				} else if (action.equalsIgnoreCase("cancel")) {
					Passenger passenger = fnode.getCancelledList().getCancelledPassenger(idop);
					fnode.getPassengerList().addPassenger(passenger.getPassengerID(), passenger.getPassengerName(),
							passenger.getFlightID(), passenger.getPassengerStatus());
					fnode.getCancelledList().deleteCancelledPassenger(passenger.getPassengerID());
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setHeaderText("Success");
					alert.setContentText("Passenger was un-cancelled and re-boarded");// success message
					alert.showAndWait();
					if (passenger.getPassengerStatus().equalsIgnoreCase("vip")) {
						cancelledVIP--;// decrementing+incrementing counters
						boardedVIP++;
					} else {
						cancelledRegular--;// decrementing+incrementing counters
						boardedRegular++;
					}
					try (BufferedWriter writer = new BufferedWriter(
							new FileWriter("C:\\Users\\My Technology\\Documents\\Ds\\log.txt", true))) {
						writer.write(String.format("%s | Undo | Cancel | %s | %d | Cancel %s for Flight %d%n",
								LocalDate.now(), passenger.getPassengerName(), flightID, passenger.getPassengerName(),
								flightID));// appending onto the log file
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					return;
				}
			});

			redo.setOnAction(e -> {
				if (redoStack.peek() == null) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText("Empty Stack");
					alert.setContentText("No operations to redo");// making sure stack isnt empty
					alert.showAndWait();
					return;
				}
				String operation = redoStack.pop();
				String[] parts = operation.split(" ");
				String action = parts[0];
				int flightID = Integer
						.parseInt(operation.substring(operation.indexOf("Flight ") + 7, operation.indexOf(")")));
				String id1 = parts[2];
				String id2 = id1.substring(3);
				Integer idop = Integer.parseInt(id2);
				undoStack.push(operation);
				FNode fnode = flightList.getNode(flightID);
				if (action.equalsIgnoreCase("CheckIn")) {// enqueing back to the queue and removing from the all
															// passengers list
					Passenger passenger = fnode.getAllPassengersList().getBoardedPassenger(idop);
					if (passenger.getPassengerStatus().equalsIgnoreCase("vip")) {
						fnode.getVIPQueue().enQueue(passenger);
						queueVIP++;
						boardedVIP--;
					} else {
						fnode.getRegularQueue().enQueue(passenger);
						queueRegular++;
						boardedRegular--;
					}
					try (BufferedWriter writer = new BufferedWriter(
							new FileWriter("C:\\Users\\My Technology\\Documents\\Ds\\log.txt", true))) {
						writer.write(String.format("%s | Redo | CheckIn | %s | %d | Checked-in %s for Flight %d%n",
								LocalDate.now(), passenger.getPassengerName(), flightID, passenger.getPassengerName(),
								flightID));// file appending
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					fnode.getAllPassengersList().DeletePassenger(passenger.getPassengerID());// remoing from the list
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setHeaderText("Success");
					alert.setContentText("Passenger enqueued again");
					alert.showAndWait();// success alert

				} else if (action.equalsIgnoreCase("Board")) {
					Passenger pass = null;// dequeueing from the queue and inserting back to the list
					if (fnode.getVIPQueue().findPassenger(idop)) {
						pass = fnode.getVIPQueue().deQueue();
						queueVIP--;
						boardedVIP++;
					} else {
						pass = fnode.getRegularQueue().deQueue();
						queueRegular--;
						boardedRegular++;
					}
					fnode.getPassengerList().addPassenger(pass.getPassengerID(), pass.getPassengerName(),
							pass.getFlightID(), pass.getPassengerStatus());
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setHeaderText("Success");
					alert.setContentText("Passenger was dequeued and re-boarded");// success alert
					alert.showAndWait();
					try (BufferedWriter writer = new BufferedWriter(
							new FileWriter("C:\\Users\\My Technology\\Documents\\Ds\\log.txt", true))) {// appending to
																										// file
						writer.write(String.format("%s | Redo | Board | %s | %d | Board %s for Flight %d%n",
								LocalDate.now(), pass.getPassengerName(), flightID, pass.getPassengerName(), flightID));
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					return;
				} else if (action.equalsIgnoreCase("cancel")) {
					Passenger passenger = fnode.getPassengerList().getBoardedPassenger(idop);
					fnode.getCancelledList().addCancelledPassengers(passenger);
					fnode.getPassengerList().DeletePassenger(passenger.getPassengerID());
					if (passenger.getPassengerStatus().equalsIgnoreCase("vip")) {
						boardedVIP--;
						cancelledVIP++;
					} else {
						boardedRegular--;
						cancelledRegular++;
					}
					try (BufferedWriter writer = new BufferedWriter(
							new FileWriter("C:\\Users\\My Technology\\Documents\\Ds\\log.txt", true))) {
						writer.write(String.format("%s | Redo | Cancel | %s | %d | Cancel%s for Flight %d%n",
								LocalDate.now(), passenger.getPassengerName(), flightID, passenger.getPassengerName(),
								flightID));
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setHeaderText("Success");
					alert.setContentText("Passenger was un-boarded and re-cancelled");
					alert.showAndWait();
					return;
				}
			});

			TextArea txtarea = new TextArea();

			print.setOnAction(e -> {
				root.setCenter(null);
				root.setLeft(null);
				root.setBottom(null);
				root.setRight(null);
				txtarea.clear();
				data.setText("History Of Operations: ");
				txtarea.setMaxHeight(400);// setting height + width
				txtarea.setMaxWidth(800);
				txtarea.setEditable(false); // to make the text area read only
				root.setCenter(txtarea);
				txtarea.setStyle(
						"-fx-font-family: 'Serif';-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
				String filePath = "C:\\Users\\My Technology\\Documents\\Ds\\log.txt";
				try (Scanner scanner = new Scanner(new File(filePath))) {
					while (scanner.hasNextLine()) {
						String line = scanner.nextLine();
						txtarea.appendText(line + "\n");
					}
					if (txtarea.getText().isEmpty()) {// printing error message if the log history is empty
						txtarea.appendText(
								"------------------------------------------------------------------No Operations done------------------------------------------------------------------");
					}
				} catch (FileNotFoundException ex) {
					txtarea.appendText("Error reading file: " + ex.getMessage());
					ex.printStackTrace();
				}
			});

			export.setOnAction(e -> {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Export Log File");// exporting the log file
				File selectedFile = fileChooser.showSaveDialog(null);
				if (selectedFile == null) {
					return;
				}
				String op = txtarea.getText().trim();
				try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile))) {
					writer.write(op);
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setHeaderText("Success");
					alert.setContentText("File was exported successfully");
					alert.showAndWait();
					return;
				} catch (IOException ex) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText("Error");
					alert.setContentText("Couldn't export file");
					alert.showAndWait();
					ex.printStackTrace();
				}

			});

			showStats.setOnAction(e -> {// printing all statistics of different flights + passengers.
				root.setCenter(null);
				Label first = new Label("Total number of canceled VIP passengers: " + getCancelledVIP());
				Label second = new Label("Total number of canceled regular passengers: " + getCancelledRegular());
				Label third = new Label("Total number of VIP passengers currently in the queue: " + getQueueVIP());
				Label fourth = new Label(
						"Total number of regular passengers currently in the queue: " + getQueueRegular());
				Label fifth = new Label("Total number of VIP passengers who have boarded: " + getBoardedVIP());
				Label sixth = new Label("Total number of regular passengers who have boarded: " + getBoardedRegular());

				first.setStyle(
						"-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");
				second.setStyle(
						"-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");
				third.setStyle(
						"-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");
				fourth.setStyle(
						"-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");
				fifth.setStyle(
						"-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");
				sixth.setStyle(
						"-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");
				HBox hbox = new HBox();
				hbox.getChildren().add(first);
				HBox hbox2 = new HBox();
				hbox2.getChildren().add(second);
				HBox hbox3 = new HBox();
				hbox3.getChildren().add(third);
				HBox hbox4 = new HBox();
				hbox4.getChildren().add(fourth);
				HBox hbox5 = new HBox();
				hbox5.getChildren().add(fifth);
				HBox hbox6 = new HBox();
				hbox6.getChildren().add(sixth);

				VBox vbox2 = new VBox(25);
				vbox2.getChildren().addAll(hbox, hbox2, hbox3, hbox4, hbox5, hbox6);
				root.setLeft(vbox2);

			});
			root.setTop(vbox);
			Scene scene = new Scene(root, 930, 600);

			Image backgroundImage = new Image(getClass().getResource("image.png").toExternalForm());// background image
			BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false,
					true, true);
			BackgroundImage bgImage = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
					BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
			root.setBackground(new Background(bgImage));

			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.setTitle("Airport Check-in and Boarding System");
			primaryStage.show();
		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	public void addFlights(Stage addFlight) {
		GridPane root = new GridPane();
		root.setHgap(8);
		root.setVgap(8);

		TextField txtfield1 = new TextField();// making 4 textfields for different needs
		TextField txtfield2 = new TextField();
		ComboBox<String> comboBox = new ComboBox<>();// combo box that gives major names

		comboBox.getItems().addAll("Active", "Inactive");
		Label title = new Label("Insertion Stage:");// title label
		Label label1 = new Label("Flight ID:");// other 4 labels for the textfields
		Label label2 = new Label("Destination:");
		Label label3 = new Label("Status:");

		title.setStyle(
				"-fx-font-family: 'Serif'; -fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: black;-fx-underline: true;");
		label1.setStyle("-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");
		label2.setStyle("-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");
		label3.setStyle("-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");
		comboBox.setStyle("-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");

		Button insert = new Button("Insert");// insert flight button!
		insert.setPrefHeight(50);
		insert.setPrefWidth(75);
		insert.setStyle("-fx-font-family: 'Serif';-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
		insert.setOnAction(e -> {
			if (comboBox.getValue() == null) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Empty Field");// an error type alert to make sure data is not empty!
				alert.setContentText("Please Pick Flight Status!");
				alert.showAndWait();
				return;
			}
			String destination = txtfield2.getText().trim();// returning the flight destination from the textfield
			String status = comboBox.getValue().trim();

			if (destination.isEmpty() || status.isEmpty()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Empty Field");// an error type alert to make sure flight destination // status is
													// not empty!
				alert.setContentText("Data entry can't be empty");
				alert.showAndWait();
				return;
			}
			try {
				int flightID = Integer.parseInt(txtfield1.getText().trim());
				if (!destination.matches("[a-zA-Z\\s]+") || !(status.matches("[a-zA-Z\\s]+"))) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText("Invalid Name");
					alert.setContentText("Strings cannot have numbers");// correct validation
					alert.showAndWait();
					return;
				}
				if (flightList.searchFlightID(flightID)) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText("Invalid Name");
					alert.setContentText("Flight ID already exists");// correct validation
					alert.showAndWait();
					return;
				}
				flightList.insertFlight(flightID, destination, status);
				Alert insertedAlert = new Alert(Alert.AlertType.INFORMATION);
				insertedAlert.setHeaderText("Inserted");// Alert for success
				insertedAlert.setContentText("Flight was inserted sucessfully");// success alert
				insertedAlert.showAndWait();
			} catch (NumberFormatException e2) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Invalid Input");// alert for empty text fields
				alert.setContentText("FlightID can't have letters");
				alert.showAndWait();
				return;
			}
		});

		Button back = new Button("Back");
		back.setPrefHeight(50);
		back.setPrefWidth(75);// Back button for previous stage
		back.setStyle("-fx-font-family: 'Serif';-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
		back.setOnAction(e -> {
			start(addFlight);
		});
		Scene scene = new Scene(root, 700, 400);// width and height

		Image backgroundImage = new Image(getClass().getResource("image.png").toExternalForm());// background image
		BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true,
				true);
		BackgroundImage bgImage = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

		root.setBackground(new Background(bgImage));// setting the bg image
		root.add(title, 2, 0);// adding different implementations to the fx scene
		root.addRow(6, label1, txtfield1);
		root.addRow(7, label2, txtfield2);
		root.addRow(8, label3, comboBox);
		root.addRow(10, insert);
		root.addRow(23, back);

		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		addFlight.setScene(scene);
		addFlight.setTitle("Insert Flight");
		addFlight.show();
	}

	public void updateFlight(Stage updateFlight) {
		GridPane root = new GridPane();
		root.setHgap(8);
		root.setVgap(5);

		ComboBox<String> comboBox = new ComboBox<>();// storing flight id to let the user pick from

		TextField txtfield1 = new TextField();
		TextField txtfield2 = new TextField();// textfields for new flight destination and other attributes

		Label title = new Label("Update Stage:");
		Label head = new Label("Change Flight Details");
		Label pick = new Label("Pick Flight ID:");
		Label newDest = new Label("New destination:");// labels different attributes
		Label newStat = new Label("New status:");

		title.setStyle(
				"-fx-font-family: 'Serif'; -fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: black;-fx-underline: true;");
		head.setStyle(
				"-fx-font-family: 'Serif';-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;-fx-underline: true;");
		pick.setStyle("-fx-font-family: 'Serif'; -fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");
		newDest.setStyle("-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");// styles
		newStat.setStyle("-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");

		FNode current = flightList.getFront();
		while (current != null) {
			comboBox.getItems().add(String.valueOf(current.getElement().getFlightID()));// appending the flight id to
																						// the combobox
			current = current.getNext();
		}

		Button update = new Button("Update");
		update.setPrefHeight(50);
		update.setPrefWidth(100);
		update.setStyle("-fx-font-family: 'Serif';-fx-font-size: 19px; -fx-font-weight: bold; -fx-text-fill: black;");
		update.setOnAction(e -> {
			if (comboBox.getValue() == null) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Invalid Name");
				alert.setContentText("Please pick a flight ID");// correct validation
				alert.showAndWait();
				return;
			}
			String sID = comboBox.getValue();
			int flightID = Integer.parseInt(sID);
			try {
				String destination = "";
				String status = "";
				if (txtfield1.getText().isEmpty()) {
					destination = flightList.getNode(flightID).getElement().getDestination();
				} else {
					destination = txtfield1.getText().trim();
				}
				if (txtfield2.getText().isEmpty()) {
					status = flightList.getNode(flightID).getElement().getStatus();
				} else {
					status = txtfield2.getText().trim();
				}
				if ((!destination.matches("[a-zA-Z\\s]+") || !(status.matches("[a-zA-Z\\\\s]+")))) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText("Invalid Name");
					alert.setContentText("Strings cannot have numbers");
					alert.showAndWait();
					return;
				}
				Alert updateAlert = new Alert(Alert.AlertType.CONFIRMATION);
				updateAlert.setHeaderText("Update");// alert to prevent accidental operations
				updateAlert.setContentText("Are you sure you want to update information?");

				Optional<ButtonType> result = updateAlert.showAndWait();
				if (result.get() == ButtonType.OK) {
					flightList.updateFlight(flightID, destination, status);
					Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
					successAlert.setHeaderText("Success");
					successAlert.setContentText("Flight information Was Updated successfully");
					successAlert.showAndWait();// success alert
					return;
				} else {
					Alert cancelAlert = new Alert(Alert.AlertType.INFORMATION);
					cancelAlert.setHeaderText("Cancelled");// cancelled update alert
					cancelAlert.setContentText("Major information was not updated!");
					cancelAlert.showAndWait();
				}
			} catch (NumberFormatException e2) {
				e2.printStackTrace();
			}
		});

		Button back = new Button("Back");
		back.setPrefHeight(50);// back button for previous stage
		back.setPrefWidth(75);
		back.setStyle("-fx-font-family: 'Serif';-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: black;");
		back.setOnAction(e -> {
			start(updateFlight);
		});

		Scene scene = new Scene(root, 700, 400);// width + height
		Image backgroundImage = new Image(getClass().getResource("image.png").toExternalForm());
		BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true,
				true);// bg image
		BackgroundImage bgImage = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

		root.setBackground(new Background(bgImage));
		root.add(title, 2, 0);
		root.add(head, 2, 1);// adding all implementation to the fx scene
		root.addRow(5, pick, comboBox);
		root.addRow(6, newDest, txtfield1);
		root.addRow(7, newStat, txtfield2);
		root.addRow(10, update);
		root.addRow(35, back);

		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		updateFlight.setScene(scene);
		updateFlight.setTitle("Update Flight Information");
		updateFlight.show();// title of the scene
	}

	public void deleteFlight(Stage deleteFlight) {
		GridPane root = new GridPane();
		root.setHgap(8);
		root.setVgap(5);

		ComboBox<String> comboBox = new ComboBox<>();// combo box that gives major names

		Label title = new Label("Remove Stage:");// scene title
		Label label1 = new Label("Pick Flight ID:");// label to specify what the combo box is for

		title.setStyle(
				"-fx-font-family: 'Serif'; -fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: black;-fx-underline: true;");
		label1.setStyle("-fx-font-family: 'Serif';-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: black;");

		FNode current = flightList.getFront();
		while (current != null) {
			comboBox.getItems().add(String.valueOf(current.getElement().getFlightID()));
			current = current.getNext();
		}

		Button delete = new Button("Remove");// deleting a major button
		delete.setPrefHeight(50);
		delete.setPrefWidth(105);
		delete.setStyle("-fx-font-family: 'Serif';-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: black;");
		delete.setOnAction(e -> {
			String sID = comboBox.getValue();
			if (sID == null) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Error");// alert for if the user doesnt pick a major
				alert.setContentText("Choose FlightID!");
				alert.showAndWait();
				return;
			}
			int flightID = Integer.parseInt(sID);
			Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
			deleteAlert.setHeaderText("Deletion");// alert for confirmation to make sure it was not an accidental
			deleteAlert.setContentText("Are you sure you want to delete flight with ID: " + flightID);

			Optional<ButtonType> option = deleteAlert.showAndWait();
			if (option.get() == ButtonType.OK) {// checking the result of the button
				flightList.deleteFlight(flightID);
				Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
				successAlert.setHeaderText("Success");// deleting the major if the user clicks on OK which means it was
														// not an accidental deletion
				successAlert.setContentText("Flight was deleted successfully");
				successAlert.showAndWait();
				return;
			} else {
				Alert cancelAlert = new Alert(Alert.AlertType.INFORMATION);
				cancelAlert.setHeaderText("Cancelled");// cancelled delete (accidental deletion).
				cancelAlert.setContentText("Flight was not deleted (cancelled)");
				cancelAlert.showAndWait();
			}
		});

		Button back = new Button("Back");
		back.setPrefHeight(50);// back button for previous stage
		back.setPrefWidth(75);
		back.setStyle("-fx-font-family: 'Serif';-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: black;");
		back.setOnAction(e -> {
			start(deleteFlight);
		});

		Scene scene = new Scene(root, 700, 400);// width and height of scene
		Image backgroundImage = new Image(getClass().getResource("image.png").toExternalForm());// back ground image
		BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true,
				true);
		BackgroundImage bgImage = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
		root.setBackground(new Background(bgImage));
		root.add(title, 10, 0);// setting the implementation in the fx scene
		root.addRow(6, label1, comboBox);
		root.addRow(7, delete);
		root.addRow(55, back);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		deleteFlight.setScene(scene);
		deleteFlight.setTitle("Remove Flight");// title of scene
		deleteFlight.show();
	}

	public void searchFlight(Stage searchFlight) {

		GridPane root = new GridPane();
		root.setHgap(8);
		root.setVgap(5);
		// Create a new TableView for displaying Major objects

		TextField txtfield = new TextField();
		TextField txtfield1 = new TextField();

		Label title = new Label("Search Stage");// title of the scene
		Label label = new Label("Enter Flight ID:");
		Label label1 = new Label("Enter Flight Destination:");

		title.setStyle(
				"-fx-font-family: 'Serif';-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: black;-fx-underline: true;");// style
		label1.setStyle("-fx-font-family: 'Serif';-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: black;");
		label.setStyle("-fx-font-family: 'Serif';-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: black;");

		Button search = new Button("Search");// search button
		search.setPrefHeight(50);
		search.setPrefWidth(100);
		search.setStyle("-fx-font-family: 'Serif';-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: black;");
		search.setOnAction(e -> {
			int flightID = 0;
			String sID = txtfield.getText().trim();
			String destination = txtfield1.getText().trim();

			if (sID.isEmpty() && destination.isEmpty()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Error");// checking that maj or name doesnt have numbers
				alert.setContentText("Empty textfields!");
				alert.showAndWait();
				return;
			}
			if (sID.matches("[a-zA-Z\\\\s]+")) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Error");// checking that major name doesnt have numbers
				alert.setContentText("Flight ID can't have letters");
				alert.showAndWait();
				return;

			}
			if (sID.isEmpty()) {
				flightID = 0;
			} else {
				flightID = Integer.parseInt(sID);
			}

			if (!destination.isEmpty() && !destination.matches("[a-zA-Z\\s]+")) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Invalid Name");// checking that major name doesnt have numbers
				alert.setContentText("Flight destination cannot have numbers");
				alert.showAndWait();
				return;
			}

			if (flightList.searchFlight(flightID, destination) == null) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Error");// alert error type to display that the letter is not in any major
				alert.setContentText("Flight does not exist");
				alert.showAndWait();
			} else {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText("Success");
				alert.setContentText("Flight exists");
				alert.showAndWait();
			}
		});

		Button back = new Button("Back");
		back.setPrefHeight(50);
		back.setPrefWidth(100);// back to previous stage
		back.setStyle("-fx-font-family: 'Serif';-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: black;");
		back.setOnAction(e -> {
			start(searchFlight);
		});

		Scene scene = new Scene(root, 700, 400);
		Image backgroundImage = new Image(getClass().getResource("image.png").toExternalForm());// bg image
		BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true,
				true);
		BackgroundImage bgImage = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

		root.setBackground(new Background(bgImage));
		root.add(title, 2, 0);
		root.addRow(3, label, txtfield);
		root.addRow(4, label1, txtfield1);// adding everything to the fx scene
		root.addRow(5, search);
		root.addRow(50, back);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		searchFlight.setScene(scene);
		searchFlight.setTitle("Search For Flight");// search flight title scene
		searchFlight.show();
	}

	@SuppressWarnings("unchecked")
	public void printAllFlights(Stage printAllFlights) {
		BorderPane root = new BorderPane();
		Label title = new Label("All Flights Data");
		title.setStyle(
				"-fx-font-family: 'Serif';-fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: black;-fx-underline: true;");// style
		HBox hbox = new HBox();
		hbox.getChildren().add(title);

		TableView<Flight> tableView = new TableView<>();

		TableColumn<Flight, Integer> flightIDColumn = new TableColumn<>("Flight ID");
		flightIDColumn.setCellValueFactory(new PropertyValueFactory<>("flightID"));
		flightIDColumn.setPrefWidth(100);

		TableColumn<Flight, String> destinationColumn = new TableColumn<>("Destination");
		destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
		destinationColumn.setPrefWidth(130);

		TableColumn<Flight, String> statusColumn = new TableColumn<>("Status");
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
		statusColumn.setPrefWidth(100);
		tableView.getColumns().addAll(flightIDColumn, destinationColumn, statusColumn);
		tableView.setMaxWidth(550);
		tableView.setMaxHeight(420);
		FNode current = flightList.getFront();// traversing and adding all flights data to the table view
		while (current != null) {
			Flight flight = current.getElement();
			tableView.getItems().add(flight);
			current = current.getNext();
		}
		Button back = new Button("Back");
		back.setPrefHeight(50);
		back.setPrefWidth(100);// back to previous stage
		back.setStyle("-fx-font-family: 'Serif';-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: black;");
		back.setOnAction(e -> {
			start(printAllFlights);
		});
		root.setCenter(tableView);
		root.setTop(hbox);
		hbox.setAlignment(Pos.TOP_CENTER);
		root.setBottom(back);
		Scene scene = new Scene(root, 700, 400);
		Image backgroundImage = new Image(getClass().getResource("image.png").toExternalForm());// bg image
		BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true,
				true);
		BackgroundImage bgImage = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

		root.setBackground(new Background(bgImage));

		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		printAllFlights.setScene(scene);
		printAllFlights.setTitle("Print All Flights Stage");
		printAllFlights.show();
	}

	@SuppressWarnings("unchecked")
	public void printSpec(Stage printSpecificFlight) {
		GridPane root = new GridPane();
		root.setHgap(8);
		root.setVgap(5);
		TableView<Flight> tableView = new TableView<>();

		TableColumn<Flight, Integer> flightIDColumn = new TableColumn<>("Flight ID");
		flightIDColumn.setCellValueFactory(new PropertyValueFactory<>("flightID"));
		flightIDColumn.setPrefWidth(100);

		TableColumn<Flight, String> destinationColumn = new TableColumn<>("Destination");
		destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
		destinationColumn.setPrefWidth(130);

		TableColumn<Flight, String> statusColumn = new TableColumn<>("Status");
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
		statusColumn.setPrefWidth(100);
		tableView.getColumns().addAll(flightIDColumn, destinationColumn, statusColumn);

		TextField txtfield = new TextField();
		TextField txtfield1 = new TextField();

		Label title = new Label("Print Specific Flight Info");// title of the scene
		Label label = new Label("Enter Flight ID:");
		Label label1 = new Label("Enter Flight Destination:");

		title.setStyle("-fx-font-family: 'Serif';-fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: white;");// style
		label1.setStyle("-fx-font-family: 'Serif';-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: black;");
		label.setStyle("-fx-font-family: 'Serif';-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: black;");

		Button search = new Button("Display");// search button
		search.setPrefHeight(50);
		search.setPrefWidth(100);
		search.setStyle("-fx-font-family: 'Serif';-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: black;");
		search.setOnAction(e -> {
			int flightID = 0;
			String sID = txtfield.getText().trim();

			if (sID.isEmpty()) {
				flightID = 0;
			} else {
				flightID = Integer.parseInt(sID);
			}

			String destination = txtfield1.getText().trim();
			if (!destination.isEmpty() && !destination.matches("[a-zA-Z\\s]+")) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Invalid Name");// checking that major name doesnt have numbers
				alert.setContentText("Flight destination cannot have numbers");
				alert.showAndWait();
				return;
			}

			if (flightList.printSpecificFlight(flightID, destination) == null) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Error");// alert error type to display that the letter is not in any major
				alert.setContentText("Flight was not found");
				alert.showAndWait();
			} else {
				tableView.getItems().add(flightList.printSpecificFlight(flightID, destination));
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText("Success");
				alert.setContentText("Displayed successfully");
				alert.showAndWait();
			}
		});

		Button back = new Button("Back");
		back.setPrefHeight(50);
		back.setPrefWidth(100);// back to previous stage
		back.setStyle("-fx-font-family: 'Serif';-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: black;");
		back.setOnAction(e -> {
			start(printSpecificFlight);
		});

		Scene scene = new Scene(root, 700, 400);
		Image backgroundImage = new Image(getClass().getResource("image.png").toExternalForm());// bg image
		BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true,
				true);
		BackgroundImage bgImage = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

		root.setBackground(new Background(bgImage));
		root.add(title, 2, 0);
		root.addRow(3, label, txtfield);
		root.addRow(4, label1, txtfield1);// adding everything to the fx scene
		root.addRow(5, search);
		root.add(tableView, 2, 8);
		root.addRow(10, back);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		printSpecificFlight.setScene(scene);
		printSpecificFlight.setTitle("Display A Flight");// search flight title scene
		printSpecificFlight.show();
	}

	public void addPassenger(Stage addPassenger) {
		GridPane root = new GridPane();
		root.setHgap(8);
		root.setVgap(8);

		TextField txtfield1 = new TextField();
		TextField txtfield2 = new TextField();

		ComboBox<Integer> comboBox = new ComboBox<>();
		ComboBox<String> comboBox2 = new ComboBox<>();

		comboBox2.getItems().addAll("Regular", "VIP");

		FNode current = flightList.getFront();
		while (current != null) {
			comboBox.getItems().add((current.getElement().getFlightID()));
			current = current.getNext();
		}

		Label title = new Label("Insertion Stage:");
		Label label1 = new Label("Passenger ID:");
		Label label2 = new Label("Passenger Name:");
		Label label3 = new Label("Flight ID");
		Label label4 = new Label("Status:");

		title.setStyle(
				"-fx-font-family: 'Serif'; -fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: black;-fx-underline: true;");
		label1.setStyle("-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");
		label2.setStyle("-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");
		label3.setStyle("-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");
		label4.setStyle("-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");
		comboBox.setStyle("-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");
		comboBox2
				.setStyle("-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");

		Button insert = new Button("Insert");
		insert.setPrefHeight(50);
		insert.setPrefWidth(75);
		insert.setStyle("-fx-font-family: 'Serif';-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
		insert.setOnAction(e -> {
			if (comboBox.getValue() == null || comboBox2.getValue() == null) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Empty Field");
				alert.setContentText("Please Pick ComboBox Values!");
				alert.showAndWait();
				return;
			}
			if (txtfield1.getText().matches("[a-zA-Z\\s]+")) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Error");
				alert.setContentText("Passenger ID cannot have letters");
				alert.showAndWait();
				return;
			}
			if (!txtfield2.getText().matches("[a-zA-Z\\s]+")) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Error");
				alert.setContentText("Passenger Name Cannot have numbers");
				alert.showAndWait();
				return;
			}
			int passID = Integer.parseInt(txtfield1.getText().trim());
			String passName = txtfield2.getText().trim();
			int flightID = comboBox.getValue();
			String status = comboBox2.getValue().trim();
			try {
				if (exists(passID)) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText("Duplicate");
					alert.setContentText("Passenger exists already");
					alert.showAndWait();
					return;
				} else {
					flightList.getNode(flightID).getAllPassengersList().addPassenger(passID, passName, flightID,
							status);
				}
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText("Success");
				alert.setContentText("Passenger was inserted successfully");
				alert.showAndWait();
				return;
			} catch (NumberFormatException e2) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Invalid Input");
				alert.setContentText("error");
				alert.showAndWait();
				return;
			}
		});

		Button back = new Button("Back");
		back.setPrefHeight(50);
		back.setPrefWidth(75);// Back button for previous stage
		back.setStyle("-fx-font-family: 'Serif';-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
		back.setOnAction(e -> {
			start(addPassenger);
		});
		Scene scene = new Scene(root, 650, 500);// width and height

		Image backgroundImage = new Image(getClass().getResource("pass.png").toExternalForm());// background image
		BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true,
				true);
		BackgroundImage bgImage = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

		root.setBackground(new Background(bgImage));
		root.add(title, 2, 0);
		root.addRow(6, label1, txtfield1);
		root.addRow(7, label2, txtfield2);
		root.addRow(8, label3, comboBox);
		root.addRow(9, label4, comboBox2);
		root.addRow(10, insert);
		root.addRow(35, back);

		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		addPassenger.setScene(scene);
		addPassenger.setTitle("Insert Passenger");
		addPassenger.show();
	}

	public void updatePassenger(Stage updatePassengerStage) {
		GridPane root = new GridPane(); // Updating passenger stage
		root.setHgap(8);
		root.setVgap(5);

		TextField txtfield1 = new TextField(); // text fields for all attributes
		ComboBox<Integer> comboBox = new ComboBox<>(); // combo box for selecting Passenger ID
		ComboBox<String> comboBox2 = new ComboBox<>();
		ComboBox<Integer> comboBox3 = new ComboBox<>(); // combo box for selecting Passenger ID

		Label title = new Label("Update Stage:");
		Label head = new Label("Change Passenger Details");
		Label sID = new Label("Choose Passenger based on Passenger's ID");
		Label newName = new Label("New Passenger Name:");
		Label Nstatus = new Label("Move to Queue:");
		Label flightLabel = new Label("Move to Flight:");

		FNode fnode = flightList.getFront();
		while (fnode != null) {
			BPNode bnode = fnode.getAllPassengersList().getFront();
			while (bnode != null) {
				comboBox.getItems().add(bnode.getElement().getPassengerID());
				bnode = bnode.getNext();
			}
			fnode = fnode.getNext();
		}

		comboBox2.getItems().addAll("Regular", "VIP");

		FNode fnode2 = flightList.getFront();
		while (fnode2 != null) {
			comboBox3.getItems().add(fnode2.getElement().getFlightID());
			fnode2 = fnode2.getNext();
		}

		title.setStyle(
				"-fx-font-family: 'Serif'; -fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: black;-fx-underline: true;");
		head.setStyle("-fx-font-family: 'Serif';-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
		sID.setStyle("-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");
		newName.setStyle("-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");
		Nstatus.setStyle("-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");
		comboBox.setStyle("-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");
		comboBox3
				.setStyle("-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");

		flightLabel
				.setStyle("-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");
		comboBox2
				.setStyle("-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");

		Button update = new Button("Update");
		update.setPrefHeight(50);
		update.setPrefWidth(95);
		update.setStyle("-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");

		update.setOnAction(e -> {
			Integer passengerID = 0;
			String newPassengerName = "";// saving from text fields into variables
			String newStatus = "";
			Integer flightId = 0;
			if (comboBox.getItems().isEmpty() && comboBox2.getItems().isEmpty()
					&& (newPassengerName == null || newPassengerName.isEmpty()) && comboBox3.getItems().isEmpty()) {
				Alert cancelAlert = new Alert(Alert.AlertType.ERROR);
				cancelAlert.setHeaderText("Error");
				cancelAlert.setContentText("Empty Data");
				cancelAlert.showAndWait();
				return;
			}
			passengerID = comboBox.getValue();
			newPassengerName = txtfield1.getText().trim();// saving from text fields into variables
			newStatus = comboBox2.getValue().trim();
			flightId = comboBox3.getValue();
			if (!newPassengerName.matches("[a-zA-Z\\s]+")) {
				Alert alert = new Alert(Alert.AlertType.ERROR);// alert type error to make sure
				alert.setHeaderText("Invalid Name");
				alert.setContentText("Passenger Name cannot have numbers");
				alert.showAndWait();
				return;
			}
			Alert updateAlert = new Alert(Alert.AlertType.CONFIRMATION);
			updateAlert.setHeaderText("Update");
			updateAlert.setContentText("Are you sure you want to update this passenger?");
			Optional<ButtonType> result = updateAlert.showAndWait();
			if (result.get() == ButtonType.OK) {
				FNode fnode3 = flightList.getFront();
				while (fnode3 != null) {
					BPNode bnode = fnode3.getAllPassengersList().getFront();
					while (bnode != null) {
						if (bnode.getElement().getPassengerID() == passengerID) {
							fnode3.getAllPassengersList().updatePassenger(passengerID, newPassengerName, newStatus,
									flightId);
						}
						bnode = bnode.getNext();
					}
					fnode3 = fnode3.getNext();
				}
				Alert cancelAlert = new Alert(Alert.AlertType.INFORMATION);
				cancelAlert.setHeaderText("Cancelled");
				cancelAlert.setContentText("Passenger was updated successfully");// alert type error(accident)
				cancelAlert.showAndWait();
			} else {
				Alert cancelAlert = new Alert(Alert.AlertType.INFORMATION);
				cancelAlert.setHeaderText("Cancelled");
				cancelAlert.setContentText("Passenger was not updated (cancelled)");// alert type error(accident)
				cancelAlert.showAndWait();
			}

		});
		Button back = new Button("Back");
		back.setPrefHeight(50);
		back.setPrefWidth(75);
		back.setStyle("-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");
		back.setOnAction(e -> {
			start(updatePassengerStage);
		});

		Scene scene = new Scene(root, 850, 500);
		Image backgroundImage = new Image(getClass().getResource("pass.png").toExternalForm());
		BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true,
				true);
		BackgroundImage bgImage = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

		root.setBackground(new Background(bgImage));
		root.add(title, 2, 0);
		root.add(head, 2, 1);
		root.addRow(5, sID, comboBox);
		root.addRow(6, newName, txtfield1);
		root.addRow(8, Nstatus, comboBox2);
		root.addRow(9, flightLabel, comboBox3);
		root.addRow(12, update);
		root.addRow(45, back);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		updatePassengerStage.setScene(scene);
		updatePassengerStage.setTitle("Update Passenger");
		updatePassengerStage.show();
	}

	public void deletePassenger(Stage deletePassenger) {
		GridPane root = new GridPane();
		root.setHgap(8);
		root.setVgap(5);

		Label title = new Label("Delete Stage:");
		Label label1 = new Label("Passenger ID:");

		ComboBox<Integer> comboBox = new ComboBox<>();// combo box to give student id

		FNode fnode = flightList.getFront();
		while (fnode != null) {
			BPNode bnode = fnode.getAllPassengersList().getFront();
			while (bnode != null) {
				comboBox.getItems().add(bnode.getElement().getPassengerID());
				bnode = bnode.getNext();
			}
			fnode = fnode.getNext();
		}

		title.setStyle(
				"-fx-font-family: 'Serif'; -fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: black;-fx-underline: true;");
		label1.setStyle("-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");
		comboBox.setStyle("-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");

		Button delete = new Button("Delete");
		delete.setPrefHeight(50);
		delete.setPrefWidth(75);// deleting style
		delete.setStyle("-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");
		delete.setOnAction(e -> {
			Integer passengerID = comboBox.getValue();// saving the id
			if (passengerID == null) {
				Alert failureAlert = new Alert(Alert.AlertType.ERROR);
				failureAlert.setHeaderText("Error");// alert type error if is combo box is empty or returns null
				failureAlert.setContentText("Pick an ID");
				failureAlert.showAndWait();
				return;
			}
			Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
			deleteAlert.setHeaderText("Deletion");
			deleteAlert.setContentText("Are you sure you want to delete this passenger?");// conformation for accidental

			Optional<ButtonType> result = deleteAlert.showAndWait();
			if (result.get() == ButtonType.OK) {
				FNode fnode2 = flightList.getFront();
				while (fnode2 != null) {
					BPNode bnode = fnode2.getAllPassengersList().getFront();
					while (bnode != null) {
						if (bnode.getElement().getPassengerID() == passengerID) {
							fnode2.getAllPassengersList().DeletePassenger(passengerID);
						}
						bnode = bnode.getNext();
					}
					fnode2 = fnode2.getNext();
				}
				Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
				successAlert.setHeaderText("Success");// alert type success
				successAlert.setContentText("passenger was deleted successfully");
				successAlert.showAndWait();
			} else {
				Alert cancelAlert = new Alert(Alert.AlertType.INFORMATION);
				cancelAlert.setHeaderText("Cancelled");// alert type cancelled
				cancelAlert.setContentText("passenger was not deleted (cancelled)");
				cancelAlert.showAndWait();
			}
		});

		Button back = new Button("Back");
		back.setPrefHeight(50);
		back.setPrefWidth(75);// back to previous stage
		back.setStyle("-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");
		back.setOnAction(e -> {
			start(deletePassenger);
		});

		Scene scene = new Scene(root, 800, 500);
		Image backgroundImage = new Image(getClass().getResource("pass.png").toExternalForm());
		BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true,
				true);
		BackgroundImage bgImage = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
		root.setBackground(new Background(bgImage));
		root.add(title, 10, 0);// back ground image
		root.addRow(6, label1, comboBox);
		root.addRow(8, delete);
		root.addRow(70, back);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		deletePassenger.setScene(scene);
		deletePassenger.setTitle("Delete Passenger");
		deletePassenger.show();
	}

	public void searchPassenger(Stage searchForPassenger) {
		GridPane root = new GridPane();
		root.setHgap(8);
		root.setVgap(5);

		TextField txtfield = new TextField();

		Label title = new Label("Search Stage");
		Label label = new Label("Search Passenger ID:");

		title.setStyle(
				"-fx-font-family: 'Serif';-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: black;-fx-underline: true;");// style
		label.setStyle("-fx-font-family: 'Serif';-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: black;");

		Button search = new Button("Search");// search button
		search.setPrefHeight(50);
		search.setPrefWidth(100);
		search.setStyle("-fx-font-family: 'Serif';-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: black;");
		search.setOnAction(e -> {
			int passengerID = 0;
			String sID = txtfield.getText().trim();

			if (sID.isEmpty()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Error");
				alert.setContentText("Please enter passenger ID!");
				alert.showAndWait();
				return;
			}
			if (sID.matches("[a-zA-Z\\\\s]+")) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Error");
				alert.setContentText("Passenger ID can't have letters");
				alert.showAndWait();
				return;
			}
			passengerID = Integer.parseInt(sID);
			FNode fnode2 = flightList.getFront();
			while (fnode2 != null) {
				BPNode bnode = fnode2.getAllPassengersList().getFront();
				while (bnode != null) {
					if (bnode.getElement().getPassengerID() == passengerID) {
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setHeaderText("Success");// checking that major name doesnt have numbers
						alert.setContentText("Passenger with ID: " + passengerID + " exists");
						alert.showAndWait();
						return;
					}
					bnode = bnode.getNext();
				}
				fnode2 = fnode2.getNext();
			}
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("Error");// checking that major name doesnt have numbers
			alert.setContentText("Passenger with ID: " + passengerID + " does not exist");
			alert.showAndWait();
			return;
		});

		Button back = new Button("Back");
		back.setPrefHeight(50);
		back.setPrefWidth(100);// back to previous stage
		back.setStyle("-fx-font-family: 'Serif';-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: black;");
		back.setOnAction(e -> {
			start(searchForPassenger);
		});

		Scene scene = new Scene(root, 700, 400);
		Image backgroundImage = new Image(getClass().getResource("image.png").toExternalForm());// bg image
		BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true,
				true);
		BackgroundImage bgImage = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

		root.setBackground(new Background(bgImage));
		root.add(title, 2, 0);
		root.addRow(3, label, txtfield);
		root.addRow(5, search);
		root.addRow(50, back);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		searchForPassenger.setScene(scene);
		searchForPassenger.setTitle("Search For Passenger");// search flight title scene
		searchForPassenger.show();
	}

	@SuppressWarnings("unchecked")
	public void printspecificPassenger(Stage printpecificPassenger) {
		GridPane root = new GridPane();
		root.setHgap(8);
		root.setVgap(5);

		ComboBox<Integer> comboBox = new ComboBox<>();// combo box to give student id

		FNode fnode = flightList.getFront();
		while (fnode != null) {
			BPNode bnode = fnode.getAllPassengersList().getFront();
			while (bnode != null) {
				comboBox.getItems().add(bnode.getElement().getPassengerID());
				bnode = bnode.getNext();
			}
			fnode = fnode.getNext();
		}

		TableView<Passenger> tableView2 = new TableView<>();

		TableColumn<Passenger, Integer> passngerIDColumn = new TableColumn<>("Passnger ID");
		passngerIDColumn.setCellValueFactory(new PropertyValueFactory<>("passengerID"));
		passngerIDColumn.setPrefWidth(100);

		TableColumn<Passenger, String> passengerNameColumn = new TableColumn<>("Passenger Name");
		passengerNameColumn.setCellValueFactory(new PropertyValueFactory<>("passengerName"));
		passengerNameColumn.setPrefWidth(100);

		TableColumn<Passenger, Integer> flightIDColumn = new TableColumn<>("Flight ID");
		flightIDColumn.setCellValueFactory(new PropertyValueFactory<>("flightID"));
		flightIDColumn.setPrefWidth(130);

		TableColumn<Passenger, String> statusColumn = new TableColumn<>("Status");
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("passengerStatus"));
		statusColumn.setPrefWidth(100);

		tableView2.getColumns().addAll(passngerIDColumn, passengerNameColumn, flightIDColumn, statusColumn);

		Label title = new Label("Print Specific Passenger Info");// title of the scene
		Label label = new Label("Passenger ID:");

		title.setStyle("-fx-font-family: 'Serif';-fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: black;");// style
		label.setStyle("-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");
		comboBox.setStyle("-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");

		Button search = new Button("Display");// search button
		search.setPrefHeight(70);
		search.setPrefWidth(95);
		search.setStyle("-fx-font-family: 'Serif';-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: black;");
		search.setOnAction(e -> {
			if (comboBox.getValue() == null || comboBox.getItems().isEmpty()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Error");
				alert.setContentText("Please pick a Passenger");
				alert.showAndWait();
				return;
			}
			int pID = comboBox.getValue();

			FNode fnode2 = flightList.getFront();
			while (fnode2 != null) {
				BPNode bnode = fnode2.getAllPassengersList().getFront();
				while (bnode != null) {
					if (bnode.getElement().getPassengerID() == pID) {
						tableView2.getItems().clear();
						tableView2.getItems().add(bnode.getElement());
						return;
					}
					bnode = bnode.getNext();
				}
				fnode2 = fnode2.getNext();
			}
		});

		Button back = new Button("Back");
		back.setPrefHeight(50);
		back.setPrefWidth(75);// back to previous stage
		back.setStyle("-fx-font-family: 'Serif';-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: black;");
		back.setOnAction(e -> {
			start(printpecificPassenger);
		});

		Scene scene = new Scene(root, 700, 400);
		Image backgroundImage = new Image(getClass().getResource("image.png").toExternalForm());// bg image
		BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true,
				true);
		BackgroundImage bgImage = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

		root.setBackground(new Background(bgImage));
		root.add(title, 2, 0);
		root.addRow(3, label, comboBox);
		root.addRow(5, search);
		root.add(tableView2, 2, 8);
		root.addRow(9, back);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		printpecificPassenger.setScene(scene);
		printpecificPassenger.setTitle("Print Passenger Info");// search flight title scene
		printpecificPassenger.show();
	}

	public void checkinPassenger(Stage checkin) {
		GridPane root = new GridPane();
		root.setHgap(8);
		root.setVgap(8);

		TextField txtfield1 = new TextField();

		ComboBox<Integer> comboBox = new ComboBox<>();
		ComboBox<Integer> comboBox2 = new ComboBox<>();

		FNode fnode = flightList.getFront();
		while (fnode != null) {
			BPNode bnode = fnode.getAllPassengersList().getFront();
			while (bnode != null) {
				comboBox.getItems().add(bnode.getElement().getPassengerID());
				bnode = bnode.getNext();
			}
			fnode = fnode.getNext();
		}

		FNode fnode4 = flightList.getFront();
		while (fnode4 != null) {
			comboBox2.getItems().add(fnode4.getElement().getFlightID());
			fnode4 = fnode4.getNext();
		}

		Label title = new Label("Check-In Passenger Stage:");
		Label label1 = new Label("Passenger ID");
		Label label4 = new Label("Flight ID:");
		Label label2 = new Label("Date");
		Label label3 = new Label("Time");
		DatePicker dp = new DatePicker();
		dp.setValue(LocalDate.now());
		txtfield1.setMaxHeight(50);
		txtfield1.setMaxWidth(150);
		txtfield1.setPromptText("HH:MM:SS");

		title.setStyle(
				"-fx-font-family: 'Serif'; -fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: black;-fx-underline: true;");
		label1.setStyle("-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");
		label2.setStyle("-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");
		label3.setStyle("-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");
		label4.setStyle("-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");
		dp.setStyle("-fx-font-family: 'Serif';-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: black;");
		comboBox.setStyle("-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");
		comboBox2
				.setStyle("-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");
		txtfield1
				.setStyle("-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");

		Button checkinPassenger = new Button("Check-In");
		checkinPassenger.setPrefHeight(60);
		checkinPassenger.setPrefWidth(90);
		checkinPassenger
				.setStyle("-fx-font-family: 'Serif';-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
		checkinPassenger.setOnAction(e -> {
			if (txtfield1.getText().isEmpty()) {
				Alert successAlert = new Alert(Alert.AlertType.ERROR);
				successAlert.setHeaderText("Invalid");
				successAlert.setContentText("Please pick time value");
				successAlert.showAndWait();
				return;
			}
			String date = dp.getValue().toString();
			String time = txtfield1.getText().trim();
			String combine = date + " " + time;
			if (!isValidTime(time)) {
				Alert successAlert = new Alert(Alert.AlertType.ERROR);
				successAlert.setHeaderText("Invalid");
				successAlert.setContentText("Time is in wrong format");
				successAlert.showAndWait();
				return;
			}
			if (comboBox.getValue() == null && comboBox2.getValue() == null) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Empty Field");
				alert.setContentText("Please Pick Check-In Operation");
				alert.showAndWait();
				return;
			}
			if (comboBox2.getValue() == null && comboBox.getValue() != null) {
				int passID = comboBox.getValue();
				FNode fnode3 = flightList.getFront();
				while (fnode3 != null) {
					BPNode bnode = fnode3.getAllPassengersList().getFront();
					while (bnode != null) {
						if (bnode.getElement().getPassengerID() == passID) {
							Passenger pass = bnode.getElement();
							int flightID = fnode3.getElement().getFlightID();
							fnode3.getAllPassengersList().DeletePassenger(passID);
							if (pass.getPassengerStatus().equalsIgnoreCase("vip")) {
								fnode3.getVIPQueue().enQueue(pass);
								queueVIP++;
								undoStack.push("CheckIn " + pass.getPassengerName() + " ID:" + pass.getPassengerID()
										+ " (Flight " + pass.getFlightID() + ")");
							} else {
								fnode3.getRegularQueue().enQueue(pass);
								undoStack.push("CheckIn " + pass.getPassengerName() + " ID:" + pass.getPassengerID()
										+ " (Flight " + pass.getFlightID() + ")");
								queueRegular++;
							}
							comboBox.setValue(null);
							comboBox2.setValue(null);

							try (BufferedWriter writer = new BufferedWriter(
									new FileWriter("C:\\Users\\My Technology\\Documents\\Ds\\log.txt", true))) {
								writer.write(String.format("%s | Check-in | %s | %d | Checked-in %s for Flight %d%n",
										combine, pass.getPassengerName(), flightID, pass.getPassengerName(), flightID));
							} catch (IOException ex) {
								ex.printStackTrace();
							}
							Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
							successAlert.setHeaderText("Success");
							successAlert.setContentText("Passenger has been enqueued successfully.");
							successAlert.showAndWait();
							return;
						}
						bnode = bnode.getNext();
					}
					fnode3 = fnode3.getNext();
				}
			} else if (comboBox.getValue() == null && comboBox2.getValue() != null) {
				int flightID = comboBox2.getValue();
				FNode fnode2 = flightList.getNode(flightID);
				if (fnode2.getAllPassengersList().getFront() == null) {
					Alert successAlert = new Alert(Alert.AlertType.ERROR);
					successAlert.setHeaderText("Empty Passengers List");
					successAlert.setContentText("No passengers have been enqueued.");
					successAlert.showAndWait();
					return;
				}
				BPNode bnode = fnode2.getAllPassengersList().getFront();
				while (bnode != null) {
					Passenger pass = bnode.getElement();
					fnode2.getAllPassengersList().DeletePassenger(pass.getPassengerID());
					if (pass.getPassengerStatus().equalsIgnoreCase("vip")) {
						fnode2.getVIPQueue().enQueue(pass);
						queueVIP++;
						undoStack.push("CheckIn " + pass.getPassengerName() + " ID:" + pass.getPassengerID()
								+ " (Flight " + pass.getFlightID() + ")");
					} else {
						fnode2.getRegularQueue().enQueue(pass);
						queueRegular++;
						undoStack.push("CheckIn " + pass.getPassengerName() + " ID:" + pass.getPassengerID()
								+ " (Flight " + pass.getFlightID() + ")");
					}
					try (BufferedWriter writer = new BufferedWriter(
							new FileWriter("C:\\Users\\My Technology\\Documents\\Ds\\log.txt", true))) {
						writer.write(String.format("%s | Check-in | %s | %d | Checked-in %s for Flight %d%n", combine,
								pass.getPassengerName(), flightID, pass.getPassengerName(), flightID));
					} catch (IOException ex) {
						ex.printStackTrace();
					}

					bnode = bnode.getNext();
				}
				comboBox.setValue(null);
				comboBox2.setValue(null);
				Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
				successAlert.setHeaderText("Success");
				successAlert.setContentText("All passengers for flight have been enqueued.");
				successAlert.showAndWait();
			} else {
				comboBox2.setValue(null);
				Alert successAlert = new Alert(Alert.AlertType.ERROR);
				successAlert.setHeaderText("Error");
				successAlert.setContentText("You can pick only one check-in operation");
				successAlert.showAndWait();
				return;
			}
		});

		Button back = new Button("Back");
		back.setPrefHeight(60);
		back.setPrefWidth(75);// Back button for previous stage
		back.setStyle("-fx-font-family: 'Serif';-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
		back.setOnAction(e -> {
			start(checkin);
		});
		Scene scene = new Scene(root, 650, 500);// width and height

		Image backgroundImage = new Image(getClass().getResource("pass.png").toExternalForm());// background image
		BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true,
				true);
		BackgroundImage bgImage = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

		root.setBackground(new Background(bgImage));
		root.add(title, 1, 0);
		root.addRow(3, label1, comboBox);
		root.addRow(4, label4, comboBox2);
		root.addRow(5, label2, dp);
		root.addRow(6, label3, txtfield1);
		root.addRow(13, checkinPassenger);
		root.addRow(30, back);

		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		checkin.setScene(scene);
		checkin.setTitle("Insert Passenger");
		checkin.show();
	}

	@SuppressWarnings("unchecked")
	public void boardPassengers(Stage boardPassengers) {
		BorderPane root = new BorderPane();

		TextField txtfield1 = new TextField();

		Label label2 = new Label("Date: ");
		Label label3 = new Label("Time: ");
		DatePicker dp = new DatePicker();
		dp.setValue(LocalDate.now());
		String date = dp.getValue().toString();

		txtfield1.setMaxHeight(50);
		txtfield1.setMaxWidth(150);
		txtfield1.setPromptText("HH:MM:SS");

		HBox hbox2 = new HBox(7);
		hbox2.getChildren().addAll(label2, dp);
		hbox2.setAlignment(Pos.CENTER_LEFT);
		HBox hbox3 = new HBox(7);
		hbox3.getChildren().addAll(label3, txtfield1);
		hbox3.setAlignment(Pos.CENTER_LEFT);

		Label title = new Label("Board A Passenger From Queue To The Airplane");

		Button single = new Button("Board One Passenger");
		Button all = new Button("Board All Passengers");
		HBox hbox = new HBox(5);
		ComboBox<Integer> comboBox = new ComboBox<>();
		single.setStyle("-fx-font-family: 'Serif';-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
		all.setStyle("-fx-font-family: 'Serif';-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
		hbox3.setStyle("-fx-font-family: 'Serif';-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
		hbox2.setStyle("-fx-font-family: 'Serif';-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
		comboBox.setStyle("-fx-font-family: 'Serif';-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
		title.setStyle(
				"-fx-font-family: 'Serif'; -fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: black;-fx-underline: true;");

		FNode current = flightList.getFront();
		while (current != null) {
			Flight flight = current.getElement();
			comboBox.getItems().add(flight.getFlightID());
			current = current.getNext();
		}
		VBox vbox3 = new VBox(10);
		vbox3.getChildren().addAll(hbox2, hbox3, single, all, comboBox);
		vbox3.setAlignment(Pos.CENTER_LEFT);

		TableView<Passenger> tableView2 = new TableView<>();

		TableColumn<Passenger, Integer> passngerIDColumn = new TableColumn<>("Passnger ID");
		passngerIDColumn.setCellValueFactory(new PropertyValueFactory<>("passengerID"));
		passngerIDColumn.setPrefWidth(100);

		TableColumn<Passenger, String> passengerNameColumn = new TableColumn<>("Passenger Name");
		passengerNameColumn.setCellValueFactory(new PropertyValueFactory<>("passengerName"));
		passengerNameColumn.setPrefWidth(100);

		TableColumn<Passenger, Integer> flightIDColumn = new TableColumn<>("Flight ID");
		flightIDColumn.setCellValueFactory(new PropertyValueFactory<>("flightID"));
		flightIDColumn.setPrefWidth(130);

		TableColumn<Passenger, String> statusColumn = new TableColumn<>("Status");
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("passengerStatus"));
		statusColumn.setPrefWidth(100);
		tableView2.setMaxWidth(450);
		tableView2.setMaxHeight(420);

		tableView2.getColumns().addAll(passngerIDColumn, passengerNameColumn, flightIDColumn, statusColumn);
		loadPassengers(tableView2);

		single.setOnAction(e -> {
			String time = "";
			if (comboBox.getValue() == null) {
				Alert insertedAlert = new Alert(Alert.AlertType.ERROR);
				insertedAlert.setHeaderText("Error");
				insertedAlert.setContentText("Choose a flight ID");
				insertedAlert.showAndWait();
				return;
			}
			if (txtfield1.getText().isEmpty()) {
				Alert insertedAlert = new Alert(Alert.AlertType.ERROR);
				insertedAlert.setHeaderText("Error");
				insertedAlert.setContentText("Please pick time value");
				insertedAlert.showAndWait();
				return;
			}
			time = txtfield1.getText().trim();
			if (!isValidTime(time)) {
				Alert successAlert = new Alert(Alert.AlertType.ERROR);
				successAlert.setHeaderText("Invalid");
				successAlert.setContentText("Time is in wrong format");
				successAlert.showAndWait();
				return;
			}
			String combine = date + " " + time;
			Integer flightID = comboBox.getValue();
			FNode fnode4 = flightList.getNode(flightID);

			if (fnode4.getVIPQueue().isEmpty() && fnode4.getRegularQueue().isEmpty()) {
				Alert insertedAlert = new Alert(Alert.AlertType.ERROR);
				insertedAlert.setHeaderText("Error");
				insertedAlert.setContentText("No More Passengers");
				insertedAlert.showAndWait();
				return;
			}
			Passenger boardedPassenger = BoardedPassengerList.BoardOneAtATime(fnode4, flightList);

			undoStack.push("Board " + boardedPassenger.getPassengerName() + " ID:" + boardedPassenger.getPassengerID()
					+ " (Flight " + boardedPassenger.getFlightID() + ")");
			try (BufferedWriter writer = new BufferedWriter(
					new FileWriter("C:\\Users\\My Technology\\Documents\\Ds\\log.txt", true))) {

				writer.write(String.format("%s | Boarding | %s | %d | Boarded %s on Flight %d%n", combine,
						boardedPassenger.getPassengerName(), flightID, boardedPassenger.getPassengerName(), flightID));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			tableView2.getItems().clear();
			loadPassengers(tableView2);
		});
		all.setOnAction(e -> {
			if (comboBox.getValue() == null) {
				Alert insertedAlert = new Alert(Alert.AlertType.ERROR);
				insertedAlert.setHeaderText("Error");
				insertedAlert.setContentText("Choose a flight ID");
				insertedAlert.showAndWait();
				return;
			}

			if (txtfield1.getText().isEmpty()) {
				Alert insertedAlert = new Alert(Alert.AlertType.ERROR);
				insertedAlert.setHeaderText("Error");
				insertedAlert.setContentText("Please pick a time value");
				insertedAlert.showAndWait();
				return;
			}

			String time = txtfield1.getText().trim();
			if (!isValidTime(time)) {
				Alert successAlert = new Alert(Alert.AlertType.ERROR);
				successAlert.setHeaderText("Invalid");
				successAlert.setContentText("Time is in wrong format");
				successAlert.showAndWait();
				return;
			}
			String combine = dp.getValue().toString() + " " + time;
			Integer flightID = comboBox.getValue();
			FNode fnode4 = flightList.getNode(flightID);

			if (fnode4.getVIPQueue().isEmpty() && fnode4.getRegularQueue().isEmpty()) {
				Alert insertedAlert = new Alert(Alert.AlertType.ERROR);
				insertedAlert.setHeaderText("Error");
				insertedAlert.setContentText("No More Passengers");
				insertedAlert.showAndWait();
				return;
			}
			try (BufferedWriter writer = new BufferedWriter(
					new FileWriter("C:\\Users\\My Technology\\Documents\\Ds\\log.txt", true))) {
				Passenger boardedPassenger;
				while ((boardedPassenger = BoardedPassengerList.BoardOneAtATime(fnode4, flightList)) != null) {
					writer.write(String.format("%s | Boarding | %s | %d | Boarded %s on Flight %d%n", combine,
							boardedPassenger.getPassengerName(), flightID, boardedPassenger.getPassengerName(),
							flightID));
					undoStack.push("Board " + boardedPassenger.getPassengerName() + " ID:"
							+ boardedPassenger.getPassengerID() + " (Flight " + boardedPassenger.getFlightID() + ")");
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}

			tableView2.getItems().clear();
			loadPassengers(tableView2);
		});

		Scene scene = new Scene(root, 700, 500);// width and height

		Image backgroundImage = new Image(getClass().getResource("pass.png").toExternalForm());// background image
		BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true,
				true);
		BackgroundImage bgImage = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

		Button back = new Button("Back");
		back.setPrefHeight(50);
		back.setPrefWidth(75);// Back button for previous stage
		back.setStyle("-fx-font-family: 'Serif';-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
		back.setOnAction(e -> {
			start(boardPassengers);
		});
		comboBox.setPromptText("Flight ID");
		single.setPrefHeight(50);
		single.setPrefWidth(170);
		all.setPrefHeight(50);
		all.setPrefWidth(170);
		comboBox.setPrefHeight(50);
		comboBox.setPrefWidth(170);
		root.setLeft(vbox3);
		hbox.getChildren().addAll(back);
		root.setBackground(new Background(bgImage));
		root.setCenter(tableView2);
		root.setBottom(hbox);
		root.setTop(title);

		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		boardPassengers.setScene(scene);
		boardPassengers.setTitle("Board Passenger");
		boardPassengers.show();
	}

	public void cancelPassenger(Stage cancelPassenger) {
		GridPane root = new GridPane();
		root.setHgap(8);
		root.setVgap(8);

		TextField txtfield1 = new TextField();

		Label label2 = new Label("Date: ");
		Label label3 = new Label("Time: ");
		DatePicker dp = new DatePicker();
		dp.setValue(LocalDate.now());
		String date = dp.getValue().toString();

		txtfield1.setMaxHeight(50);
		txtfield1.setMaxWidth(150);
		txtfield1.setPromptText("HH:MM:SS");

		ComboBox<Passenger> comboBox = new ComboBox<>();

		FNode fnode2 = flightList.getFront();
		while (fnode2 != null) {
			BPNode bnode = fnode2.getPassengerList().getFront();
			while (bnode != null) {
				comboBox.getItems().add(bnode.getElement());
				bnode = bnode.getNext();
			}
			fnode2 = fnode2.getNext();
		}

		Label title = new Label("Cancel A Passenger's Booking");
		Label label1 = new Label("Passenger ID:");

		HBox hbox = new HBox();
		hbox.getChildren().addAll(label1, comboBox);
		HBox hbox2 = new HBox();
		hbox2.getChildren().addAll(label2, dp);
		HBox hbox3 = new HBox();
		hbox3.getChildren().addAll(label3, txtfield1);

		title.setStyle(
				"-fx-font-family: 'Serif'; -fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: black;-fx-underline: true;");
		label1.setStyle("-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");
		comboBox.setStyle("-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");
		hbox2.setStyle("-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");
		hbox3.setStyle("-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");

		Button cancel = new Button("Cancel Booking");
		cancel.setPrefHeight(70);
		cancel.setPrefWidth(150);
		cancel.setStyle("-fx-font-family: 'Serif';-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
		cancel.setOnAction(e -> {
			if (comboBox.getValue() == null) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Empty Field");
				alert.setContentText("Please Pick Passenger ID");
				alert.showAndWait();
				return;
			}

			if (txtfield1.getText().isEmpty()) {
				Alert insertedAlert = new Alert(Alert.AlertType.ERROR);
				insertedAlert.setHeaderText("Error");
				insertedAlert.setContentText("Please pick time value");
				insertedAlert.showAndWait();
				return;
			}

			String time = txtfield1.getText().trim();
			if (!isValidTime(time)) {
				Alert successAlert = new Alert(Alert.AlertType.ERROR);
				successAlert.setHeaderText("Invalid");
				successAlert.setContentText("Time is in wrong format");
				successAlert.showAndWait();
				return;
			}

			String combine = date + " " + time;
			Passenger pass = comboBox.getValue();
			FNode fnode = flightList.getFront();
			while (fnode != null) {
				BPNode bnode = fnode.getPassengerList().getFront();
				while (bnode != null) {
					if (bnode.getElement() == pass) {
						fnode.getCancelledList().addCancelledPassengers(pass);
						fnode.getPassengerList().DeletePassenger(pass.getPassengerID());
						undoStack.push("Cancel " + pass.getPassengerName() + " ID:" + pass.getPassengerID()
								+ " (Flight " + pass.getFlightID() + ")");
						try (BufferedWriter writer = new BufferedWriter(
								new FileWriter("C:\\Users\\My Technology\\Documents\\Ds\\log.txt", true))) {
							writer.write(String.format("%s | Cancel | %s | %d | Canceled %s from Flight %d%n", combine,
									pass.getPassengerName(), pass.getFlightID(), pass.getPassengerName(),
									pass.getFlightID()));
						} catch (IOException ex) {
							ex.printStackTrace();
						}
						if (pass.getPassengerStatus().equalsIgnoreCase("vip")) {
							boardedVIP--;
							cancelledVIP++;
						} else {
							boardedRegular--;
							cancelledRegular++;
						}
						break;
					}
					bnode = bnode.getNext();
				}
				fnode = fnode.getNext();
			}

			Alert insertedAlert = new Alert(Alert.AlertType.INFORMATION);
			insertedAlert.setHeaderText("Cancelled Booking");
			insertedAlert.setContentText("Passenger's Booking Has Been Cancelled");
			insertedAlert.showAndWait();
		});

		Button back = new Button("Back");
		back.setPrefHeight(60);
		back.setPrefWidth(75);// Back button for previous stage
		back.setStyle("-fx-font-family: 'Serif';-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
		back.setOnAction(e -> {
			start(cancelPassenger);
		});
		Scene scene = new Scene(root, 650, 500);// width and height

		Image backgroundImage = new Image(getClass().getResource("pass.png").toExternalForm());// background image
		BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true,
				true);
		BackgroundImage bgImage = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

		root.setBackground(new Background(bgImage));
		root.add(title, 0, 0);
		root.addRow(5, hbox2);
		root.addRow(6, hbox3);
		root.addRow(9, hbox);
		root.addRow(13, cancel);
		root.addRow(35, back);

		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		cancelPassenger.setScene(scene);
		cancelPassenger.setTitle("Cancel Passenger");
		cancelPassenger.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public boolean exists(int passengerID) {
		FNode fnode = flightList.getFront();
		while (fnode != null) {
			BPNode bnode = fnode.getAllPassengersList().getFront();
			while (bnode != null) {
				if (bnode.getElement().getPassengerID() == passengerID) {
					return true;
				}
				bnode = bnode.getNext();
			}
			fnode = fnode.getNext();
		}
		return false;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void loadPassengers(TableView tableView) {
		FNode fnode = flightList.getFront();
		while (fnode != null) {
			Queue vipQueue = fnode.getVIPQueue();
			Queue regularQueue = fnode.getRegularQueue();
			if (vipQueue != null) {
				QNode currentNode = vipQueue.getFront();
				while (currentNode != null) {
					Passenger passenger = currentNode.getElement();
					tableView.getItems().add(passenger);
					currentNode = currentNode.getNext();
				}
			}
			if (regularQueue != null) {
				QNode currentNode = regularQueue.getFront();
				while (currentNode != null) {
					Passenger passenger = currentNode.getElement();
					tableView.getItems().add(passenger);
					currentNode = currentNode.getNext();
				}
			}
			fnode = fnode.getNext();
		}
		
	}
	

	private boolean isValidTime(String time) {
		try {
			String[] parts = time.split(":");
			if (parts.length == 3) {
				int hour = Integer.parseInt(parts[0]);
				int minute = Integer.parseInt(parts[1]);
				int second = Integer.parseInt(parts[2]);
				return hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59 && second >= 0 && second <= 59;
			}
			return false;
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	public int getCancelledVIP() {
		return cancelledVIP;
	}

	public void setCancelledVIP(int cancelledVIP) {
		this.cancelledVIP = cancelledVIP;
	}

	public int getCancelledRegular() {
		return cancelledRegular;
	}

	public void setCancelledRegular(int cancelledRegular) {
		this.cancelledRegular = cancelledRegular;
	}

	public int getQueueVIP() {
		return queueVIP + BoardedPassengerList.getQueueVIP();
	}

	public void setQueueVIP(int queueVIP) {
		this.queueVIP = queueVIP;
	}

	public int getQueueRegular() {
		return queueRegular + BoardedPassengerList.getQueueRegular();
	}

	public void setQueueRegular(int queueRegular) {
		this.queueRegular = queueRegular;
	}

	public int getBoardedVIP() {
		return boardedVIP + BoardedPassengerList.getBoardedVIP();
	}

	public void setBoardedVIP(int boardedVIP) {
		this.boardedVIP = boardedVIP;
	}

	public int getBoardedRegular() {
		return boardedRegular + BoardedPassengerList.getBoardedRegular();
	}

	public void setBoardedRegular(int boardedRegular) {
		this.boardedRegular = boardedRegular;
	}

}
