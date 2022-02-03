package assegnamento2.assegnamento2.communication.user;

import assegnamento2.assegnamento2.communication.product.ElettronicDevice;

import java.util.List;

/**
 * The class {@code Admin } is used to represent Administrator entity.
 */
public final class Admin extends Employee {

    /**
     * Class constructor.
     *
     * @param name     It is the Admin name.
     * @param surname  It is the Admin surname.
     * @param username It is the Admin username.
     * @param password It is the Admin password.
     */
    public Admin(final String name, final String surname, final String username, final String password) {

        super(name, surname, username, password);
    }

    /**
     * Class constructor.
     * To instantiate new Admin objects without parameters.
     */
    public Admin() {
    }

    /**
     * Class constructor.
     *
     * @param a It is Admin object.
     */
    public Admin(Admin a) {

        super(a.getName(), a.getSurname(), a.getUsername(), a.getPassword());
    }

    /**
     * This method is used to add a new employee.
     *
     * @param name     It is the name of the new employee.
     * @param surname  It is the surname of the new employee.
     * @param username It is the username of the new employee.
     * @param password It is the password of the new employee.
     * @param list     It is the Employee list.
     * @return Returns a boolean value:<br>
     * false : if the method was successful and therefore the new employee was added to the employees list;<br>
     * true :  if the username of the employee to be added already exists.
     */
    public boolean addEmployee(final List<Employee> list, String name, String surname, String username, String password) {

        for (Employee i : list) {

            if (i.getUsername().equals(username)) return true;

        }

        list.add(new Employee(name, surname, username, password));
        return false;
    }

    /**
     * This method is used to remove an employee.
     *
     * @param username It is the username of the employee account you want to delete.
     * @param list     It is the Employee list.
     * @return Returns a boolean value: <br>
     * false : if the method was successful and therefore the employee's account has been removed from the employees list;<br>
     * true : if the username of the employee to be deleted does not exist.
     */
    public boolean rmvEmployee(final List<Employee> list, String username) {

        for (Employee e : list) {

            if (e.getUsername().equals(username)) {

                list.remove(e);

                return false;
            }
        }

        return true;
    }

    /**
     * This method is used to add a new product.
     *
     * @param name     It is the name of the new product.
     * @param id       It is the Identification code of the new product.
     * @param producer It is the producer of the new product.
     * @param price    It is the price of the new product.
     * @param amount   It is the amount of the new product.
     * @param list     It is the ElettronicDevice list.
     * @return Returns an integer:<br>
     * 0 : if the product was added successfully;<br>
     * 1 : if the amount of the product is less than or equal to zero;<br>
     * 2 : if the price of the product is less than or equal to zero;<br>
     * 3 : if the id of the product you want to add already exists.
     */
    public int addProduct(final List<ElettronicDevice> list, String name, int id, String producer, float price, int amount) {

        if (amount <= 0) return 1;
        if (price <= 0) return 2;

        for (ElettronicDevice i : list) {

            if (i.getId() == id) return 3;
        }

        list.add(new ElettronicDevice(name, id, producer, price, amount));
        return 0;
    }

    /**
     * This method is used to remove a product.
     *
     * @param id   It is the Identification code of the product to be removed.
     * @param list It is the ElettronicDevice list.
     * @return Returns an integer:<br>
     * 0 : if the product has been successfully deleted;<br>
     * 1 : if the id of the product to be deleted does not exist.
     */
    public int rmvProduct(final List<ElettronicDevice> list, int id) {

        for (ElettronicDevice e : list) {

            if (e.getId() == id) {

                list.remove(e);
                return 0;

            }
        }

        return 1;
    }

}
