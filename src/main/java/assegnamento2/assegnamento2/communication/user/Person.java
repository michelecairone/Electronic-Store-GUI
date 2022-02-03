package assegnamento2.assegnamento2.communication.user;

/**
 * The class {@code Person} is used to represent client/employee/admin entity.
 * It contains property and methods used to manage users and employees.
 */
public class Person {

    /**
     * Class fields.
     * name - It is the name of the user or employee.
     * surname - It is the surname of the user or employee.
     * username - It is the username of the user or employee.
     * password - It is the password of the user or employee.
     */
    private String name;
    private String surname;
    private String username;
    private String password;

    /**
     * Class constructor. It is used by Users/Employee/Admin.
     *
     * @param name     It is the name of the user/employee.
     * @param surname  It is the surname of the user/employee.
     * @param username It is the username of the user/employee.
     * @param password It is the password of the user/employee.
     */
    public Person(final String name, final String surname, final String username, final String password) {

        this.setName(name);
        this.setSurname(surname);
        this.setUsername(username);
        this.setPassword(password);
    }

    /**
     * Class constructor. It is used by Users/Employee/Admin.
     * to instantiate new Person objects without parameters.
     */
    public Person() { }

    /**
     * This method is used to get the Person's name.
     *
     * @return It returns the Person's name.
     */
    public String getName() {

        return this.name;
    }

    /**
     * This method is used to get the Person's surname.
     *
     * @return It returns the Person's surname.
     */
    public String getSurname() {

        return this.surname;
    }

    /**
     * This method is used to get the Person's username.
     *
     * @return It returns the Person's username.
     */
    public String getUsername() {

        return this.username;
    }

    /**
     * This method is used to get the Person's password.
     *
     * @return It returns the Person's password.
     */
    public String getPassword() {

        return this.password;
    }

    /**
     * This method is used to set the Person's name.
     *
     * @param name is the Person's name.
     */
    public void setName(final String name) {

        this.name = name;
    }

    /**
     * This method is used to set the Person's name.
     *
     * @param surname is the Person's name.
     */
    public void setSurname(final String surname) {

        this.surname = surname;
    }

    /**
     * This method is used to set the Person's name.
     *
     * @param username is the Person's name.
     */
    public void setUsername(final String username) {

        this.username = username;
    }

    /**
     * This method is used to set the Person's name.
     *
     * @param password is the Person's name.
     */
    public void setPassword(final String password) {

        this.password = password;
    }

}
