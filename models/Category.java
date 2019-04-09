package com.algonquincollege.cst8277.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Category class
 * 
 * date (modified) 2019 04 09
 * 
 * @author Bo Zhu,  040-684-747
 * 
 */
@Entity
@Table(name = "CATEGORY")
@EntityListeners({ AuditListener.class })
public class Category extends ModelBase implements Serializable {
    /** explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;
    /**
     * String category_name, name of category 
     * @author Bo Zhu
     */ 
    protected String category_name;
    
    /**
     * List Product, a list of product
     * @author Bo Zhu
     */
    private List<Product> products = new ArrayList<>();
    
    /**
     * default constructor, JPA requires each @Entity class have a default constructor
     * @author Bo Zhu
     */
    public Category() {
        super();
    }

    /**
     * JPA One-To-Many Mapping create a One-To-Many relationship between Category
     * and Product, mappedBy "category"
     * 
     * getter for products
     * @return List of products
     */
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Product> getProducts() {
        return products;
    }

    /**
     * setter for products
     * @param products, products list
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    /**
     * getter for category_name
     * @return category_name
     */
    @Column(name = "CATEGORY_NAME")
    public String getCategoryName() {
        return category_name;
    }
    
    /**
     * setter for category_name
     * @param category_name, name of category
     */
    public void setCategoryName(String category_name) {
        this.category_name = category_name;
    }

    //Strictly speaking, JPA does not require hashcode() and equals(),
    // but it is a good idea to have one that tests using the PK (@Id) field
    
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
        if (!(obj instanceof Category)) {
            return false;
        }
        Category other = (Category) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

}