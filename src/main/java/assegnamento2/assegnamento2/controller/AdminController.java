package assegnamento2.assegnamento2.controller;

import assegnamento2.assegnamento2.communication.user.Admin;
import assegnamento2.assegnamento2.communication.user.Employee;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The class {@code AdminController} represent
 * the controller where we can control all the buttons and functionality of the Admin fxml file.
 * This class inherit from EmployeeController class.
 */
public final class AdminController extends EmployeeController implements Initializable {

    /**
     * It is the name to be assigned to the new employee account.
     */
    @FXML
    private TextField newEmpName;

    /**
     * It is the surname to be assigned to the new employee account.
     */
    @FXML
    private TextField newEmpSurname;

    /**
     * It is the username to be assigned to the new employee account.
     */
    @FXML
    private TextField newEmpUsername;

    /**
     * It is the password to be assigned to the new employee account.
     */
    @FXML
    private TextField newEmpPass;

    /**
     * It is the table that contains the whole list of the employees of the store.
     */
    @FXML
    private TableView employeeTableView;

    /**
     * It is the name to assign to the new product you are adding to the store.
     */
    @FXML
    private TextField newProductName;

    /**
     * It is the product to assign to the new product you are adding to the store.
     */
    @FXML
    private TextField newProductProducer;

    /**
     * It is the identification code to assign to the new product you are adding to the store.
     */
    @FXML
    private TextField newProductID;

    /**
     * It is the price to assign to the new product you are adding to the store.
     */
    @FXML
    private TextField newProductPrice;

    /**
     * It is the quantity to assign to the new product you are adding to the store.
     */
    @FXML
    private TextField newProductAmount;

    /**
     * It is the identification code of the product you want to modify the quantity.
     */
    @FXML
    private TextField newProductIdU;

    /**
     * It is the quantity you want to add to a product.
     */
    @FXML
    private TextField newProductAmountU;

    /**
     * It is the table that contains the complete list of products present in the store and also those that have finished stocking up.
     */
    @FXML
    private TableView addTableView;

    /**
     * It is the table that contains the complete list of products present in the store and also those that have finished stocking up.
     */
    @FXML
    private TableView rmvTableView;

    /**
     * It is the identification code of the product you want to delete.
     */
    @FXML
    private TextField newProductIdR;

    /**
     * It is the support list for employees.
     */
    List<Employee> empList = new ArrayList<>();

    private Admin admin = new Admin();

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

            readEmp();

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        printListFX(updateTableView, Stream.concat(prodElDev.stream(), buyElDev.stream()).collect(Collectors.toList()));
        printListFX(addTableView, Stream.concat(prodElDev.stream(), buyElDev.stream()).collect(Collectors.toList()));
        printListFX(rmvTableView, Stream.concat(prodElDev.stream(), buyElDev.stream()).collect(Collectors.toList()));
        printListFX(employeeTableView, empList);
    }

    /**
     * This method is used to set the Admin's object.
     *
     * @param a is the Admin's object.
     */
    public void setAdmin(Admin a) {

        this.admin = new Admin(a);
    }


    /**
     * This method is used by the "Add" button of the employee tab, it checks the validity of the various fields and in case of failure is managed through alerts,
     * instead if the method is successful it adds the new employee in the empList and prints it in the table of the graphical interface.
     *
     * @throws XMLStreamException           is used to report well-formedness errors as well as unexpected processing conditions.
     * @throws IOException                  Signals that an I/O exception has occurred.
     * @throws ParserConfigurationException Indicates a serious configuration error.
     * @throws SAXException                 Encapsulate a general SAX error or warning.
     */
    @FXML
    public void handleAddEmployee() throws XMLStreamException, IOException, ParserConfigurationException, SAXException {

        String name = this.newEmpName.getText();
        String surname = this.newEmpSurname.getText();
        String username = this.newEmpUsername.getText();
        String password = this.newEmpPass.getText();

        if (name.isBlank() || surname.isBlank() || username.isBlank() || password.isBlank())
            popUp(Alert.AlertType.ERROR, "Blank fields!", "Add Employee failed");
        else if (loginR(username)) {
            popUp(Alert.AlertType.ERROR, "Error Username!", "Username exist");
        } else {
            this.admin.addEmployee(this.empList, name, surname, username, password);
            addUser(new Employee(name, surname, username, password));
            printListFX(employeeTableView, empList);
            popUp(Alert.AlertType.INFORMATION, "Employee Added!", "");

            this.newEmpName.clear();
            this.newEmpSurname.clear();
            this.newEmpUsername.clear();
            this.newEmpPass.clear();
        }
    }

    /**
     * This method is used to read the list of employees that are present in the DB.xml file
     * and adds the various employees found in a support empList
     *
     * @throws IOException                  Signals that an I/O exception has occurred.
     * @throws ParserConfigurationException Indicates a serious configuration error.
     * @throws SAXException                 Encapsulate a general SAX error or warning.
     */
    public void readEmp() throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File("src/main/java/assegnamento2/assegnamento2/db/DB.XML"));

        NodeList nodeList1 = document.getElementsByTagName("employee");

        for (int i = 0; i < nodeList1.getLength(); i++) {
            org.w3c.dom.Node node = nodeList1.item(i);

            if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {

                Element elem = (Element) node;
                String username = elem.getElementsByTagName("username").item(0).getChildNodes().item(0).getNodeValue();
                String password = elem.getElementsByTagName("password").item(0).getChildNodes().item(0).getNodeValue();
                String name = elem.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue();
                String surname = elem.getElementsByTagName("surname").item(0).getChildNodes().item(0).getNodeValue();

                this.empList.add(new Employee(name, surname, username, password));
            }
        }
    }

    /**
     * This method is used by the "Add" button of the "add product" tab, it checks the validity of the various fields if the method fails it is managed through alerts,
     * instead if the method succeeds it adds the new product and updates the list and the corresponding tableview.
     *
     * @throws XMLStreamException    is used to report well-formedness errors as well as unexpected processing conditions.
     * @throws FileNotFoundException reports that an attempt to open the file indicated by a specified path name has failed.
     */
    @FXML
    public void handleAddProd() throws XMLStreamException, FileNotFoundException {

        String name = this.newProductName.getText();
        String producer = this.newProductProducer.getText();
        String id = this.newProductID.getText();
        String price = this.newProductPrice.getText();
        String amount = this.newProductAmount.getText();

        int i, a;
        float p;

        if (name.isBlank() || producer.isBlank() || id.isBlank() || price.isBlank() || amount.isBlank()) {
            popUp(Alert.AlertType.ERROR, "Blank fields!", "Add Product failed");
            return;
        }
        try {
            i = Integer.parseInt(id);
            a = Integer.parseInt(amount);
            p = Float.parseFloat(price);

        } catch (NumberFormatException e) {
            popUp(Alert.AlertType.ERROR, "Instruction fields!", "ID: int\nPRICE: float\nAMOUNT: int");
            return;
        }

        int result = admin.addProduct(prodElDev, name, i, producer, p, a);

        switch (result) {
            case 0 -> {
                refreshList(prodElDev, "src/main/java/assegnamento2/assegnamento2/db/DB_ElDev.XML");
                printListFX(addTableView, Stream.concat(prodElDev.stream(), buyElDev.stream()).collect(Collectors.toList()));
                popUp(Alert.AlertType.INFORMATION, "Product successfully added", "");

                this.newProductName.clear();
                this.newProductProducer.clear();
                this.newProductID.clear();
                this.newProductPrice.clear();
                this.newProductAmount.clear();
            }
            case 1 -> popUp(Alert.AlertType.ERROR, "Error Amount", "A negative quantity was entered!");
            case 2 -> popUp(Alert.AlertType.ERROR, "Error Price", "An integer was not entered!");
            case 3 -> popUp(Alert.AlertType.INFORMATION, "Product ID already exists", "Product ID already exists!");
        }
    }

    /**
     * This method is used by the "Remove" button of the "remove product" tab,
     * it checks if the inserted id is valid and if so it deletes the product, updating the relative list and table.
     *
     * @throws XMLStreamException    is used to report well-formedness errors as well as unexpected processing conditions.
     * @throws FileNotFoundException reports that an attempt to open the file indicated by a specified path name has failed.
     */
    @FXML
    public void handleRmvProd() throws XMLStreamException, FileNotFoundException {

        String id = this.newProductIdR.getText();
        int i;

        if (id.isBlank()) {
            popUp(Alert.AlertType.ERROR, "Blank fields!", "Remove Product failed");
            return;
        }

        try {
            i = Integer.parseInt(id);

        } catch (NumberFormatException e) {
            popUp(Alert.AlertType.ERROR, "Instruction fields!", "ID: int");
            return;
        }

        int result = admin.rmvProduct(prodElDev, i);

        if (result == 1) {

            result = admin.rmvProduct(buyElDev, i);

            if (result == 0) refreshList(buyElDev, "src/main/java/assegnamento2/assegnamento2/db/DB_BuyElDev.XML");

        } else refreshList(prodElDev, "src/main/java/assegnamento2/assegnamento2/db/DB_ElDev.XML");

        switch (result) {
            case 0 -> {
                printListFX(rmvTableView, Stream.concat(prodElDev.stream(), buyElDev.stream()).collect(Collectors.toList()));
                popUp(Alert.AlertType.INFORMATION, "Product successfully deleted", "");

                this.newProductIdR.clear();
            }
            case 1 -> popUp(Alert.AlertType.ERROR, "Error ID", "ID not found!");
        }
    }

    /**
     * This method is used by the "update" button of the "update product" tab,
     * it checks if the id and the quantity inserted is valid and if so it updates the product, updating the relative list and table.
     *
     * @throws XMLStreamException    is used to report well-formedness errors as well as unexpected processing conditions.
     * @throws FileNotFoundException reports that an attempt to open the file indicated by a specified path name has failed.
     */
    public void handleUpdate() throws XMLStreamException, FileNotFoundException {

        updateSwitch(this.admin.addAmount(prodElDev, buyElDev, this.newProductIdU.getText(), this.newProductAmountU.getText()));

        this.newProductIdU.clear();
        this.newProductAmountU.clear();
    }

    /**
     * This method is used by the "remove" button of the "employees" tab, it checks if the employee username exists and if so it deletes the account related to that username,
     * updating the related list of employees and table.
     *
     * @throws XMLStreamException           is used to report well-formedness errors as well as unexpected processing conditions.
     * @throws IOException                  Signals that an I/O exception has occurred.
     * @throws ParserConfigurationException Indicates a serious configuration error.
     * @throws SAXException                 Encapsulate a general SAX error or warning.
     */
    public void handleRmvEmp() throws XMLStreamException, IOException, ParserConfigurationException, SAXException {

        String username = this.newEmpUsername.getText();

        if (username.isBlank()) popUp(Alert.AlertType.ERROR, "Blank fields!", "Remove Employee failed");

        else {
            boolean value = admin.rmvEmployee(this.empList, username);

            if (value) popUp(Alert.AlertType.INFORMATION, "Username not found!", "");

            else {
                refreshEmp(this.empList);
                printListFX(employeeTableView, this.empList);
                popUp(Alert.AlertType.INFORMATION, "Operation performed successfully", "Employee account removed");

                this.newEmpUsername.clear();
            }
        }
    }

    /**
     * This method is used by the "Add product" tab and prints the updated product table.
     */
    public void handleRefreshAddTable() {

        printListFX(addTableView, Stream.concat(prodElDev.stream(), buyElDev.stream()).collect(Collectors.toList()));
    }

    /**
     * This method is used by the "Remove product" tab and prints the updated product table.
     */
    public void handleRefreshRmvTable() {

        printListFX(rmvTableView, Stream.concat(prodElDev.stream(), buyElDev.stream()).collect(Collectors.toList()));
    }

    /**
     * This method is used by the "Update product" tab and prints the updated product table.
     */
    public void handleRefreshUpdateTable() {

        printListFX(updateTableView, Stream.concat(prodElDev.stream(), buyElDev.stream()).collect(Collectors.toList()));
    }

}
