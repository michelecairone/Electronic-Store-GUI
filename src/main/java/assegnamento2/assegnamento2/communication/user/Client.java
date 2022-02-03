package assegnamento2.assegnamento2.communication.user;

import assegnamento2.assegnamento2.communication.product.ElettronicDevice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The class {@code Client } inherit from Person
 * is used to represents  Client entity.
 */
public final class Client extends Person {

    /**
     * Class fields.
     * address - It is the address of the client.
     * shop - It is the shopping cart of various clients.
     */
    private String address;
    private final List<ElettronicDevice> shop = new ArrayList<>();

    /**
     * Class constructor.
     */
    public Client() { }

    /**
     * Class constructor.
     *
     * @param name     It is the Client's name.
     * @param surname  It is the Client's surname.
     * @param username It is the Client's username.
     * @param password It is the Client's password.
     * @param address  It is the Client's address.
     */
    public Client(final String name, final String surname, final String username, final String password, final String address) {

        super(name, surname, username, password);
        this.setAddress(address);
    }

    /**
     * Class constructor.
     *
     * @param registerClient It is the Client registration.
     */
    public Client(final Client registerClient) {

        super(registerClient.getName(), registerClient.getSurname(), registerClient.getUsername(), registerClient.getPassword());
        this.address = registerClient.getAddress();
    }

    /**
     * This method is used to get the Client's address.
     *
     * @return It returns the Client's address.
     */
    public String getAddress() {

        return this.address;
    }

    /**
     * This method is used to obtain the Client's shopping cart.
     *
     * @return Returns a client's shopping cart.
     */
    public Object getShop() {

        return this.shop;
    }

    /**
     * This method is used to set the Client's address.
     *
     * @param address is the Client's address.
     */
    public void setAddress(final String address) {

        this.address = address;
    }


    /**
     * This method is used to search for products.
     *
     * @param list     It is the ElettronicDevice list.
     * @param name     It is the name of a product that is present in the list.
     * @param producer It is the producer of a product that is present in the list.
     * @param min      It is the minimum price of a product that is on the list.
     * @param max      It is the maximum price of a product that is on the list.
     * @return Returns an ElettronicDevice type list (elprint) where inside there are the products that respect the various parameters passed.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    public Object searchProduct(final List<ElettronicDevice> list, String name, String producer, String min, String max) throws IOException {

        float minPrice = 0, maxPrice = 0;
        List<ElettronicDevice> elprint = new ArrayList<>();

        if (min.equals("")) minPrice = 0;
        else {
            try {
                minPrice = Float.parseFloat(min);

            } catch (NumberFormatException ignored) {
            }
        }

        if (max.equals("")) maxPrice = 0;
        else {
            try {
                maxPrice = Float.parseFloat(max);

            } catch (NumberFormatException ignored) {
            }
        }

        for (ElettronicDevice e : list) {

            if (name.equalsIgnoreCase(e.getName()) || (name.equals(""))) {
                elprint.add(e);

                if ((!producer.equalsIgnoreCase(e.getProducer())) && (!(producer.equals("")))) {
                    elprint.remove(e);
                }
            }
        }

        float finalMinPrice = minPrice;
        elprint.removeIf(n -> (n.getPrice() <= finalMinPrice));

        if (maxPrice != 0) {
            float finalMaxPrice = maxPrice;
            elprint.removeIf(n -> (n.getPrice() >= finalMaxPrice));
        }

        return elprint;
    }

    /**
     * This method is used to buy a product.
     *
     * @param elDev    It is the ElettronicDevice list.
     * @param buyElDev It is  list of ElettronicDevice for the employee to purchase.
     * @param ID       It is the identification code of the product to be purchased.
     * @param amount   represents the quantity of the product that refers to the ID.
     * @return Returns an integer:<br>
     * 0 : if the product order has been successful;<br>
     * 1 : if the quantity of the product ordered is less than zero or greater than the quantity available in the store;<br>
     * 2 : if the product ID is not found, then the product is not there.
     */
    public int orderProduct(final List<ElettronicDevice> elDev, final List<ElettronicDevice> buyElDev, String ID, String amount) {

        int id = 0;

        try {
            id = Integer.parseInt(ID);

        } catch (NumberFormatException ignored) {
        }

        for (ElettronicDevice i : elDev) {

            if (i.getId() == id) {

                int qnt = Integer.parseInt(amount);

                if ((qnt > 0) && (qnt <= i.getAmount())) {

                    if (i.getAmount() == qnt) {

                        addOrder(new ElettronicDevice(i));
                        i.setAmount(i.getAmount() - qnt);
                        buyElDev.add(elDev.remove(elDev.indexOf(i)));

                    } else {

                        int a = i.getAmount();
                        i.setAmount(qnt);
                        addOrder(new ElettronicDevice(i));
                        i.setAmount(a - qnt);
                    }
                    return 0;

                } else return 1;
            }
        }

        return 2;
    }


    /**
     * This method add an order into shop list.
     *
     * @param e It is an ElettronicDevice element.
     */
    public void addOrder(final ElettronicDevice e) {

        for (ElettronicDevice i : this.shop) {

            if (i.getId() == e.getId()) {
                i.setAmount(i.getAmount() + e.getAmount());
                return;
            }
        }

        this.shop.add(e);
    }

}
