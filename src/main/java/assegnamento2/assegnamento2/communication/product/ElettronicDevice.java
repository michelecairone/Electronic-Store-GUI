package assegnamento2.assegnamento2.communication.product;

/**
 * The class {@code ElettronicDevice } is used to represents ElettronicDevice entity.
 */
public class ElettronicDevice {

    /**
     * Class fields.
     * name - It is the ElettronicDevice's name
     * id - It is ElettronicDevice's identification code
     * producer - It is ElettronicDevice's producer
     * price - It is ElettronicDevice's price
     * amount - It is ElettronicDevice's amount
     */
    private String name;
    private int id;
    private String producer;
    private float price;
    private int amount;

    /**
     * Class constructor.
     *
     * @param name     It is the ElettronicDevice's name
     * @param id       It is ElettronicDevice's identification code
     * @param producer It is ElettronicDevice's producer
     * @param price    It is ElettronicDevice's price
     * @param amount   It is ElettronicDevice's amount
     */
    public ElettronicDevice(final String name, final int id, final String producer, final float price, final int amount) {

        this.setName(name);
        this.setId(id);
        this.setProducer(producer);
        this.setPrice(price);
        this.setAmount(amount);
    }

    /**
     * Class constructor.
     * to instantiate new ElettronicDevice objects without parameters.
     */
    public ElettronicDevice() { }

    /**
     * Class constructor.
     * to instantiate new ElettronicDevice objects with one parameter.
     *
     * @param i It is ElettronicDevice's object.
     */
    public ElettronicDevice(ElettronicDevice i) {

        this.name = i.getName();
        this.id = i.getId();
        this.producer = i.getProducer();
        this.price = i.getPrice();
        this.amount = i.getAmount();
    }

    /**
     * This method is used to get the ElettronicDevice's name.
     *
     * @return It returns the ElettronicDevice's name.
     */
    public String getName() {

        return this.name;
    }

    /**
     * This method is used to get the ElettronicDevice's identification code.
     *
     * @return It returns the ElettronicDevice's identification code.
     */
    public int getId() {

        return this.id;
    }

    /**
     * This method is used to get the ElettronicDevice's producer.
     *
     * @return It returns the ElettronicDevice's producer.
     */
    public String getProducer() {

        return this.producer;
    }

    /**
     * This method is used to get the ElettronicDevice's price.
     *
     * @return It returns the ElettronicDevice's price.
     */
    public float getPrice() {

        return this.price;
    }

    /**
     * This method is used to get the ElettronicDevice's amount.
     *
     * @return It returns the ElettronicDevice's amount.
     */
    public int getAmount() {

        return this.amount;
    }

    /**
     * This method is used to set the ElectronicDevice's name.
     *
     * @param name is the ElettronicDevice's name.
     */
    public void setName(final String name) {

        this.name = name;
    }

    /**
     * This method is used to set the ElectronicProduct's identification code.
     *
     * @param id is the ElectronicProduct's identification code.
     */
    public void setId(final int id) {

        this.id = id;
    }

    /**
     * This method is used to set the ElectronicProduct's producer.
     *
     * @param producer is the ElectronicProduct's producer.
     */
    public void setProducer(final String producer) {

        this.producer = producer;
    }

    /**
     * This method is used to set the ElectronicProduct's price.
     *
     * @param price is the ElectronicProduct's price.
     */
    public void setPrice(final float price) {

        this.price = price;
    }

    /**
     * This method is used to set the ElectronicProduct's amount.
     *
     * @param amount is the ElectronicProduct's amount.
     */
    public void setAmount(final int amount) {

        this.amount = amount;
    }

}
