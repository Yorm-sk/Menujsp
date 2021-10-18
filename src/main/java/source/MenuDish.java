package source;

import javax.persistence.*;

@Entity
@Table(name = "Menu")
public class MenuDish {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private double price;

    private double weigh;

    private boolean isDiscount;

    public MenuDish(){}

    public MenuDish(String name, double price, double weigh, boolean isDiscount){
        this.name = name;
        this.price = price;
        this.weigh = weigh;
        this.isDiscount = isDiscount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDish() {
        return name;
    }

    public void setDish(String dish) {
        this.name = dish;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getWeigh() {
        return weigh;
    }

    public void setWeigh(double weigh) {
        this.weigh = weigh;
    }

    public boolean isDiscount() {
        return isDiscount;
    }

    public void setDiscount(boolean discount) {
        isDiscount = discount;
    }

    @Override
    public String toString() {
        return "MenuDish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", weigh=" + weigh +
                ", isDiscount=" + isDiscount +
                '}';
    }
}
