package assegnamento2.assegnamento2.controller;

import assegnamento2.assegnamento2.communication.user.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * The class {@code RegisterController} represent
 * the controller where we can control all the buttons and functionality of the Register fxml file.
 * This class inherit from Controller class.
 */
public final class RegisterController extends Controller {

    @FXML
    private TextField newName;
    @FXML
    private TextField newSurname;
    @FXML
    private TextField newAddress;
    @FXML
    private TextField newUsername;
    @FXML
    private TextField newPass;

    /**
     * This method is used to sign up a new user, it sends you an alert message if username or password are exists.
     *
     * @param event The event.
     * @throws ParserConfigurationException Indicates a serious configuration error.
     * @throws IOException                  Signals that an I/O exception has occurred.
     * @throws SAXException                 Encapsulate a general SAX error or warning.
     * @throws XMLStreamException           The base exception for unexpected processing errors.
     */
    @FXML
    public void handleSignIn(ActionEvent event) throws ParserConfigurationException, IOException, SAXException, XMLStreamException {

        String name = this.newName.getText();
        String surname = this.newSurname.getText();
        String address = this.newAddress.getText();
        String username = this.newUsername.getText();
        String pass = this.newPass.getText();

        if (name.isBlank() || surname.isBlank() || address.isBlank() || pass.isBlank() || username.isBlank())
            popUp(Alert.AlertType.ERROR, "Blank fields!", "Registered failed");

        else {
            Boolean value = loginR(username);

            if (value)
                popUp(Alert.AlertType.ERROR, "Error Username!", "Username exist");

            else {
                addUser(new Client(name, surname, username, pass, address));

                popUp(Alert.AlertType.INFORMATION, "Registered!", "Client registered");
                handleHomeButton(event);
            }
        }
    }
}
