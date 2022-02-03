package assegnamento2.assegnamento2.communication.user;

import assegnamento2.assegnamento2.communication.product.ElettronicDevice;

import java.util.List;

/**
 * The class {@code Employee} inherit from Person.
 * Defines the employee of the electronic device store and the methods used by them.
 */
public class Employee extends Person {

    /**
     * Class constructor. It is used by Admin
     * to instantiate new Employee objects without parameters.
     */
    public Employee() { }

    /**
     * Class constructor. It is used by Admin.
     *
     * @param name     It is the name of the employee.
     * @param surname  It is the surname of the employee.
     * @param username It is the username of the employee.
     * @param password It is the password of the employee.
     */
    public Employee(final String name, final String surname, final String username, final String password) {

        super(name, surname, username, password);
    }

    /**
     * Class constructor. It is used by Admin.
     *
     * @param emp It is Employee object.
     */
    public Employee(Employee emp) {

        super(emp.getName(), emp.getSurname(), emp.getUsername(), emp.getPassword());
    }

    /**
     * This method is used to add other quantities of electronic device.
     *
     * @param elDev    It is the ElettronicDevice's list.
     * @param buyElDev It is  list of ElettronicDevice for the employee to purchase.
     * @param ID       It is the identification code of the product to be modified.
     * @param amount   represents the quantity of the product that refers to the ID.
     * @return Returns an integer:<br>
     * 0 : if the addition of the quantity to the product was successful;<br>
     * 1 : if the addition of the quantity of the product is a negative number;<br>
     * 2 : if an appropriate value has not been entered when adding the quantity of the product;<br>
     * 3 : if product ID is not present.
     */
    public int addAmount(final List<ElettronicDevice> elDev, final List<ElettronicDevice> buyElDev, String ID, String amount) {

        int id;
        try {
            id = Integer.parseInt(ID);

        } catch (NumberFormatException e) {
            return 2;
        }

        for (ElettronicDevice i : elDev) {

            if (i.getId() == id) {

                int q;
                try {
                    q = Integer.parseInt(amount);

                    if (q > 0) {
                        i.setAmount(i.getAmount() + q);
                        return 0;

                    } else return 1;

                } catch (NumberFormatException e) {
                    return 2;
                }
            }
        }

        for (ElettronicDevice j : buyElDev) {

            if (j.getId() == id) {

                int q;
                try {
                    q = Integer.parseInt(amount);

                    if (q > 0) {
                        j.setAmount(j.getAmount() + q);
                        elDev.add(buyElDev.remove(buyElDev.indexOf(j)));
                        return 0;

                    } else return 1;

                } catch (NumberFormatException e) {
                    return 2;
                }
            }
        }

        return 3;
    }

}
