package assegnamento2.assegnamento2.controller;

import assegnamento2.assegnamento2.communication.user.Admin;
import assegnamento2.assegnamento2.communication.user.Client;
import assegnamento2.assegnamento2.communication.user.Employee;
import assegnamento2.assegnamento2.controller.AdminController;
import assegnamento2.assegnamento2.controller.ClientController;
import assegnamento2.assegnamento2.controller.Controller;
import assegnamento2.assegnamento2.controller.EmployeeController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * The class {@code LoginController} represent
 * the controller where we can control all the buttons and functionality of the Login fxml file.
 * This class inherit from Controller class.
 */
public class LoginController extends Controller {

    @FXML
    private TextField userName;
    @FXML
    private TextField pass;

    /**
     * This method is used to handle the contact us button.
     */
    @FXML
    public void handleContactUsLink() {

        popUp(Alert.AlertType.INFORMATION, "Nome - Michele Cairone\nMatricola - 284972\nNome - Leonardo Minaudo\nMatricola - 297792", "Contact Us");
    }

    /**
     * This method is used to handle the press of "Enter" to make login.
     *
     * @param event The event.
     * @throws IOException                  Signals that an I/O exception has occurred.
     * @throws ParserConfigurationException Indicates a serious configuration error.
     * @throws SAXException                 Encapsulate a general SAX error or warning.
     */
    @FXML
    public void onEnter(ActionEvent event) throws IOException, ParserConfigurationException, SAXException {

        handleLoginButton(event);
    }

    /**
     * This method is used to read the DB.xml file to search if there is a user with those specific parameters.
     *
     * @param userName It is the username of the user who is trying to login.
     * @param pass     It is the password of the user who is trying to login.
     * @return Returns an object of the specific type of user (which can be an Admin/Employee/Client) who logged in,
     * or it returns NULL because no user exists in the DB.xml file with those parameters.
     * @throws ParserConfigurationException Indicates a serious configuration error.
     * @throws SAXException                 Encapsulate a general SAX error or warning.
     * @throws IOException                  Signals that an I/O exception has occurred.
     */
    public Object login(String userName, String pass) throws ParserConfigurationException, SAXException, IOException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File("src/main/java/assegnamento2/assegnamento2/db/DB.XML"));

        NodeList nodeList = document.getElementsByTagName("administrator");

        for (int i = 0; i < nodeList.getLength(); i++) {
            org.w3c.dom.Node node = nodeList.item(i);

            if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {

                Element elem = (Element) node;
                String username = elem.getElementsByTagName("username").item(0).getChildNodes().item(0).getNodeValue();
                String password = elem.getElementsByTagName("password").item(0).getChildNodes().item(0).getNodeValue();

                if (userName.equals(username) && pass.equals(password)) {
                    String name = elem.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue();
                    String surname = elem.getElementsByTagName("surname").item(0).getChildNodes().item(0).getNodeValue();

                    Admin a = new Admin(name, surname, username, password);
                    return a;
                }
            }
        }

        NodeList nodeList1 = document.getElementsByTagName("employee");

        for (int i = 0; i < nodeList1.getLength(); i++) {
            org.w3c.dom.Node node = nodeList1.item(i);

            if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {

                Element elem = (Element) node;
                String username = elem.getElementsByTagName("username").item(0).getChildNodes().item(0).getNodeValue();
                String password = elem.getElementsByTagName("password").item(0).getChildNodes().item(0).getNodeValue();

                if (userName.equals(username) && pass.equals(password)) {
                    String name = elem.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue();
                    String surname = elem.getElementsByTagName("surname").item(0).getChildNodes().item(0).getNodeValue();

                    Employee e = new Employee(name, surname, username, password);
                    return e;
                }
            }
        }

        NodeList nodeList2 = document.getElementsByTagName("client");

        for (int i = 0; i < nodeList2.getLength(); i++) {
            org.w3c.dom.Node node = nodeList2.item(i);

            if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {

                Element elem = (Element) node;
                String username = elem.getElementsByTagName("username").item(0).getChildNodes().item(0).getNodeValue();
                String password = elem.getElementsByTagName("password").item(0).getChildNodes().item(0).getNodeValue();


                if (userName.equals(username) && pass.equals(password)) {
                    String name = elem.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue();
                    String surname = elem.getElementsByTagName("surname").item(0).getChildNodes().item(0).getNodeValue();
                    String address = elem.getElementsByTagName("address").item(0).getChildNodes().item(0).getNodeValue();

                    Client c = new Client(name, surname, username, password, address);
                    return c;
                }
            }
        }

        popUp(Alert.AlertType.WARNING, "TRY AGAIN", "Username or password is incorrect!");
        return null;
    }

    /**
     * This method is used to handle the login button.
     *
     * @param event The event that causes the scene change.
     * @throws ParserConfigurationException Indicates a serious configuration error.
     * @throws IOException                  Signals that an I/O exception has occurred.
     * @throws SAXException                 Encapsulate a general SAX error or warning.
     */
    @FXML
    public void handleLoginButton(ActionEvent event) throws ParserConfigurationException, IOException, SAXException {

        String userName = this.userName.getText();
        String pass = this.pass.getText();

        if (userName.isBlank() || pass.isBlank()) popUp(Alert.AlertType.ERROR, "Blank fields!", "Login failed");

        else {
            Object res = login(userName, pass);

            if (res instanceof Admin) changeScene(event, "Admin.fxml", "Administrator", res);

            else if (res instanceof Employee) changeScene(event, "Employee.fxml", "Employee", res);

            else if (res instanceof Client) changeScene(event, "Client.fxml", "Client", res);
        }
    }

    /**
     * This method is used to handle the Register Now button.
     *
     * @param event The event that causes the scene change.
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    public void onRegister(ActionEvent event) throws IOException {

        changeScene(event, "Register.fxml", "Registration");
    }

    /**
     * This method is used to change the scene whenever it is needed by passing parameters.
     *
     * @param event The event that causes the scene change.
     * @param fxml  It is the fxml file, basically it is the destination of the scene change.
     * @param title It is the title of the scene.
     * @param res   It is the type of object that can be Admin/Employee/Client to make the scene controller the right one (ClientController/EmployeeController/AdminController).
     * @throws ParserConfigurationException Indicates a serious configuration error.
     * @throws IOException                  Signals that an I/O exception has occurred.
     * @throws SAXException                 Encapsulate a general SAX error or warning.
     */
    @FXML
    public void changeScene(final ActionEvent event, final String fxml, final String title, Object res) throws IOException, ParserConfigurationException, SAXException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/assegnamento2/assegnamento2/" + fxml));
        Parent root = loader.load();

        switch (title) {
            case "Administrator":
                AdminController admControl = loader.getController();
                admControl.setAdmin((Admin) res);
                break;

            case "Employee":
                EmployeeController empControl = loader.getController();
                empControl.setEmployee((Employee) res);
                break;

            case "Client":
                ClientController clControl = loader.getController();
                clControl.setClient((Client) res);
                break;
        }

        Scene dashboardScene = new Scene(root); // creates a Scene for a specific root Node
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow(); // replaces the current scene with another scene in the same window
        window.setScene(dashboardScene); // specify the scene to be used on this stage
        window.setTitle(title); // set the title of the window
        window.show();
    }

}
