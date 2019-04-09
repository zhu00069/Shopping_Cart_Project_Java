package com.algonquincollege.cst8277.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * CustomerOrder class
 */
@Entity
@Table(name = "CUSTOMER_ORDER")
@EntityListeners({ AuditListener.class })
public class CustomerOrder extends ModelBase implements Serializable {
    /** explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;

    /**
     * double amount, total amount of orders
     * @author Bo Zhu
     */ 
    protected double amount;

    /**
     * Customer customer, customer
     * @author Bo Zhu
     */ 
    private Customer customer;

    /**
     * List OrderedProduct products, list of products
     * @author Bo Zhu
     */ 
    protected List<OrderedProduct> products = new ArrayList<>();

    /**
     * default constructor, JPA requires each @Entity class have a default constructor
     * @author Bo Zhu
     */
    public CustomerOrder() {
        super();
    }

    /**
     * JPA Many-To-One Mapping create a Many-To-One relationship between Order and
     * Customer, join column is CUSTOMER_ID
     * 
     * getter for customer
     * @return customer
     */
    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    public Customer getCustomer() {
        return customer;
    }

    /**
     * setter for customer
     * @param customer, customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * JPA One-To-Many Mapping create a One-To-Many relationship between
     * CustmomerOrder and OrderedProduct, mappedBy "customerOrder"
     * 
     * getter for ordered products
     * @return products
     */
    @OneToMany(mappedBy = "customerOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<OrderedProduct> getProducts() {
        return products;
    }

    /**
     * setter for ordered products
     * @param products, list of ordered products
     */
    public void setProducts(List<OrderedProduct> products) {
        this.products = products;
    }

    /**
     * method for adding product into OrderedProduct
     * @param product, add a product
     * @return product
     */
    public OrderedProduct addOrderedProduct(OrderedProduct product) {
        // add an product
        getProducts().add(product);
        return product;
    }

    /**
     * method for removing product from OrderedProduct
     * @param product
     * @return product
     */
    public OrderedProduct removeOrderedProduct(OrderedProduct product) {
        // remove an product
        getProducts().remove(product);
        return product;

    }
    
    /**
     * getter for amount of customer order
     * @return amount
     */
    @Column(name = "AMOUNT")
    public double getAmount() {

        amount = 0;

        for (int i = 0; i < getProducts().size(); i++) { 

            amount += products.get(i).getQuantity() * products.get(i).getProduct().getPrice(); }

        return amount;
    }

    /**
     * setter for amount of customer ordered
     * @param amount, total amount of customer order
     */
    public void setAmount(double amount) {

        this.amount = amount;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Product)) {
            return false;
        }
        Product other = (Product) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

}