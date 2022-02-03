package assegnamento2.assegnamento2.controller;

import assegnamento2.assegnamento2.communication.product.ElettronicDevice;
import assegnamento2.assegnamento2.communication.user.Client;
import assegnamento2.assegnamento2.communication.user.Employee;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The class {@code EmployeeController} represent
 * the controller where we can control all the buttons and functionality of the Employee fxml file.
 * This class inherit from Controller class.
 */
public class EmployeeController extends Controller implements Initializable {

    /**
     * It is the table which contains the list of products ordered by a client.
     */
    @FXML
    protected TableView deliveryTableView;

    /**
     * It is a kind of drop-down menu that displays the various clients who have placed at least one order.
     */
    @FXML
    protected ComboBox choiceClient;

    /**
     * It is the button that at each press confirms the displayed client order.
     */
    @FXML
    protected Button deliverySendAllE;

    /**
     * It is the table that contains the list of products that have run out of stock in the store.
     */
    @FXML
    protected TableView buyFinishedTableView;

    /**
     * It is the table that contains the complete list of products present in the store and also those that have finished stocking up.
     */
    @FXML
    protected TableView updateTableView;

    /**
     * It is the identification code of the product you want to modify the quantity.
     */
    @FXML
    protected TextField IDproductE;

    /**
     * It is the quantity you want to add to a product.
     */
    @FXML
    protected TextField amountE;

    /**
     * It is the button confirming the product modification.
     */
    @FXML
    protected Button uploadButtonE;

    /**
     * It is the support list for the products in the store.
     */
    List<ElettronicDevice> prodElDev = new ArrayList<>();

    /**
     * It is the support list for products in the store that have run out of stock.
     */
    List<ElettronicDevice> buyElDev = new ArrayList<>();

    /**
     * It is the support list for registered clients.
     */
    List<Client> clientList = new ArrayList<>();

    private Employee employee = new Employee();

    /**
     * This method is used initialize a controller after its root element has been fully processed.
     *
     * @param url            It is the location used to resolve relative paths for the root object or null if the location is unknown.
     * @param resourceBundle It is the resources used to locate the root object or null if the root object has not been located.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            readElDev(prodElDev, "src/main/java/assegnamento2/assegnamento2/db/DB_ElDev.XML");
            readElDev(buyElDev, "src/main/java/assegnamento2/assegnamento2/db/DB_BuyElDev.XML");

            readOrder();
            readUsername();

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        printListFX(updateTableView, Stream.concat(prodElDev.stream(), buyElDev.stream()).collect(Collectors.toList()));
    }

    /**
     * This method is used to set the Employee's object.
     *
     * @param emp is the Employee's object.
     */
    public void setEmployee(Employee emp) {

        this.employee = new Employee(emp);
    }

    /**
     * This method is used to print the list of products that have run out of stock in the graphical interface.
     */
    @FXML
    public void handleViewBuyElDev() {

        printListFX(buyFinishedTableView, buyElDev);
    }


    /**
     * This method is used to print in the combobox choiceClient the list of clients who have made at least one order.
     */
    @FXML
    public void handleChoiceClient() {

        for (Client cli : this.clientList) {
            if (cli.getUsername().equals(choiceClient.getValue())) {

                printListFX(deliveryTableView, cli.getShop());

            }
        }
    }


    /**
     * This method is used to read orders that are present in the DB_Order.XML file
     * and after reading a whole order from a single client it adds it to the clientList support list.
     *
     * @throws ParserConfigurationException Indicates a serious configuration error.
     * @throws IOException                  Signals that an I/O exception has occurred.
     * @throws SAXException                 Encapsulate a general SAX error or warning.
     */
    public void readOrder() throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File("src/main/java/assegnamento2/assegnamento2/db/DB_Order.XML"));

        NodeList orderList = document.getElementsByTagName("order");

        for (int i = 0; i < orderList.getLength(); i++) {
            Client client = new Client();
            Node node = orderList.item(i);
            String attribute = node.getAttributes().getNamedItem("id").getNodeValue();

            client.setUsername(attribute);

            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element ord = (Element) node;
                NodeList devList = ord.getElementsByTagName("elDev");

                for (int j = 0; j < devList.getLength(); j++) {

                    Node node1 = devList.item(j);

                    if (node1.getNodeType() == Node.ELEMENT_NODE) {
                        Element el_d = (Element) node1;

                        String name = el_d.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue();
                        String id = el_d.getElementsByTagName("id").item(0).getChildNodes().item(0).getNodeValue();
                        String producer = el_d.getElementsByTagName("producer").item(0).getChildNodes().item(0).getNodeValue();
                        String price = el_d.getElementsByTagName("price").item(0).getChildNodes().item(0).getNodeValue();
                        String amount = el_d.getElementsByTagName("amount").item(0).getChildNodes().item(0).getNodeValue();

                        client.addOrder(new ElettronicDevice(name, Integer.parseInt(id), producer, Float.parseFloat(price), Integer.parseInt(amount)));
                    }
                }
            }

            this.clientList.add(client);
        }
    }


    /**
     * This method is used in order to clean the combobox
     * and immediately after to add all the usernames of the various clients that have carried out an order.
     *
     * @throws ParserConfigurationException Indicates a serious configuration error.
     * @throws IOException                  Signals that an I/O exception has occurred.
     * @throws SAXException                 Encapsulate a general SAX error or warning.
     */
    @FXML
    public void readUsername() throws ParserConfigurationException, IOException, SAXException {

        choiceClient.getItems().clear();
        for (Client cli : this.clientList) {

            choiceClient.getItems().addAll(cli.getUsername());

        }
    }


    /**
     * This method is used by the "Send All" button to confirm the order of a single client
     * and subsequently update the various lists and tableviews.
     *
     * @throws ParserConfigurationException Indicates a serious configuration error.
     * @throws IOException                  Signals that an I/O exception has occurred.
     * @throws SAXException                 Encapsulate a general SAX error or warning.
     * @throws XMLStreamException           is used to report well-formedness errors as well as unexpected processing conditions.
     */
    public void handleSendAll() throws ParserConfigurationException, IOException, SAXException, XMLStreamException {

        this.clientList.removeIf(cli -> cli.getUsername().equals(choiceClient.getValue()));

        readUsername();
        popUp(Alert.AlertType.INFORMATION, "Order confirmed", "Confirmed Order ");

        deliveryTableView.getItems().clear();
        writeOrder();
    }

    /**
     * This method is used in order to go to write on the DB_Order.XML file the various orders of the clients
     * using like support the method getShop() of the Client class.
     *
     * @throws IOException        Signals that an I/O exception has occurred.
     * @throws XMLStreamException is used to report well-formedness errors as well as unexpected processing conditions.
     */
    public void writeOrder() throws XMLStreamException, IOException {

        XMLOutputFactory output = XMLOutputFactory.newInstance();
        XMLStreamWriter writer = output.createXMLStreamWriter(new FileOutputStream("src/main/java/assegnamento2/assegnamento2/db/DB_Order.XML"));

        writer.writeStartDocument("1.0");
        writer.writeStartElement("database");

        for (Client cli : this.clientList) {

            writer.writeStartElement("order");
            writer.writeAttribute("id", cli.getUsername());

            for (ElettronicDevice e : (List<ElettronicDevice>) cli.getShop()) {

                writer.writeStartElement("elDev");

                writerElement("name", writer, e.getName());
                writerElement("id", writer, String.valueOf(e.getId()));
                writerElement("producer", writer, e.getProducer());
                writerElement("price", writer, String.valueOf(e.getPrice()));
                writerElement("amount", writer, String.valueOf(e.getAmount()));

                writer.writeEndElement();
            }

            writer.writeEndElement();
        }

        writer.writeEndElement();
        writer.writeEndDocument();

        writer.flush();
        writer.close();
    }

    /**
     * This method is used by the update buttons found in Admin.fxml and Employee.fxml
     * to update the quantity of the selected product.
     *
     * @throws XMLStreamException    is used to report well-formedness errors as well as unexpected processing conditions.
     * @throws FileNotFoundException reports that an attempt to open the file indicated by a specified path name has failed.
     */
    @FXML
    public void handleUpdate() throws XMLStreamException, FileNotFoundException {

        updateSwitch(this.employee.addAmount(prodElDev, buyElDev, this.IDproductE.getText(), this.amountE.getText()));

        this.IDproductE.clear();
        this.amountE.clear();
    }

    /**
     * This method is used by update buttons,
     * and it's formed mainly by a switch that according to the result of the parameter appears a different alert.
     *
     * @param result is an integer that handles integers:<br>
     *               0: external method executed successfully, the product is updated;<br>
     *               1: error, an invalid value has been inserted for the Amount field;<br>
     *               2: error, you have not inserted an integer;<br>
     *               3: error, product not present.
     * @throws XMLStreamException    is used to report well-formedness errors as well as unexpected processing conditions.
     * @throws FileNotFoundException reports that an attempt to open the file indicated by a specified path name has failed.
     */
    @FXML
    public void updateSwitch(int result) throws XMLStreamException, FileNotFoundException {

        switch (result) {
            case 0 -> {
                refreshList(prodElDev, "src/main/java/assegnamento2/assegnamento2/db/DB_ElDev.XML");
                refreshList(buyElDev, "src/main/java/assegnamento2/assegnamento2/db/DB_BuyElDev.XML");
                updateTableView.getItems().clear();
                printListFX(updateTableView, Stream.concat(prodElDev.stream(), buyElDev.stream()).collect(Collectors.toList()));
                popUp(Alert.AlertType.INFORMATION, "Successful operation", "Updated product");
            }
            case 1 -> popUp(Alert.AlertType.ERROR, "Error Amount", "A negative quantity was entered!");
            case 2 -> popUp(Alert.AlertType.ERROR, "Error", "An integer was not entered!");
            case 3 -> popUp(Alert.AlertType.INFORMATION, "ID not found", "Product ID not present!");
        }
    }

}
