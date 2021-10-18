package source;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static EntityManagerFactory emf;
    static EntityManager em;

    public static void main(String[] args) {
        try (Scanner scan = new Scanner(System.in)){
            emf = Persistence.createEntityManagerFactory("JPATest");
            em  = emf.createEntityManager();
            try {
                while (true){
                    System.out.println("""
                            1 - Add a dish;
                            2 - Add a menu;
                            3 - View all dishes;
                            4 - View dish with price from to;
                            5 - View dish with discount;
                            6 - Show dishes with amount weigh 1 kilo or less.
                            """);

                    String s = scan.nextLine();
                    switch (s) {
                        case ("1") -> addDish(scan);
                        case ("2") -> addMenu();
                        case ("3") -> showMenu();
                        case ("4") -> choosePrice(scan);
                        case ("5") -> isDiscount();
                        case ("6") -> System.out.println(lessThanOneKilo());
                        default -> {
                            return;
                        }
                    }
                }
            } finally {
                em.close();
                emf.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void addDish(Scanner s){
        System.out.println("Enter name of a dish:");
        String name = s.nextLine();
        System.out.println("Enter a price:");
        try {
            double price = Double.parseDouble(s.nextLine());
            System.out.println("Enter a weigh:");
            double weigh = Double.parseDouble(s.nextLine());
            System.out.println("Is there a discount on this dish? (y or n)");
            String ds = s.nextLine();
            boolean discount;
            if (ds.equals("y")) discount = true;
            else if (ds.equals("n")) discount = false;
            else {
                System.out.println("You enter wrong symbol, so there is no discount");
                discount = false;
            }

            em.getTransaction().begin();

            try {
                MenuDish dish = new MenuDish(name, price, weigh , discount);
                em.persist(dish);
                em.getTransaction().commit();
            } catch (Exception e){
                em.getTransaction().rollback();
            }
        } catch (Exception e){
            System.out.println("You enter something wrong!");
        }
    }
    private static void addMenu(){
        em.getTransaction().begin();
        try {
            List<MenuDish> menu = new ArrayList<>();
            menu.add(new MenuDish("bread", 3.2, 0.2, false));
            menu.add(new MenuDish("fried meat", 20.6, 0.8, true));
            menu.add(new MenuDish("fried potato", 8.8, 0.5, false));
            menu.add(new MenuDish("salad", 5.3, 0.3, true));
            menu.add(new MenuDish("juice", 4.4, 0.3, false));
            for (MenuDish dish : menu){
                em.persist(dish);
            }
            em.getTransaction().commit();
        } catch (Exception e){
            em.getTransaction().rollback();
        }
    }

    private static void showMenu(){
        Query query = em.createQuery(
                "SELECT c from  MenuDish c", MenuDish.class);
        List<MenuDish> list = (List<MenuDish>) query.getResultList();
        for (MenuDish d: list) System.out.println(d);
    }

    private static void choosePrice(Scanner s){
        System.out.println("Enter start price:");
        double sPrice = Double.parseDouble(s.nextLine());
        System.out.println("Enter final price:");
        double fPrice = Double.parseDouble(s.nextLine());
        Query query = em.createQuery(
                "SELECT c FROM MenuDish c WHERE c.price > " + sPrice + " AND c.price < "  + fPrice, MenuDish.class
        );
        List<MenuDish> menuDishes = (List<MenuDish>) query.getResultList();
        for (MenuDish m : menuDishes) System.out.println(m);
    }

    private static void isDiscount(){
        Query query = em.createQuery(
                "SELECT c FROM MenuDish c WHERE c.isDiscount = true", MenuDish.class
        );

        System.out.println("Dishes with discount:");
        List<MenuDish> menuDishes = (List<MenuDish>) query.getResultList();
        for (MenuDish m : menuDishes) System.out.println(m);
    }

    private static ArrayList<MenuDish> lessThanOneKilo(){
        Query query = em.createQuery(
          "SELECT c from MenuDish c WHERE c.weigh <= 1"
        );
        List<MenuDish> menu = (List<MenuDish>) query.getResultList();
        ArrayList<MenuDish> result = new ArrayList<>();
        double sum = 0;
        for (MenuDish m : menu){
            sum += m.getWeigh();
            if (sum <= 1) result.add(m);
        }
        return result;
    }
}
