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
 * Product class
 * 
 * date (modified) 2019 04 09
 * 
 * @author Bo Zhu,  040-684-747
 * 
 */
@Entity
@Table(name = "PRODUCT")
@EntityListeners({ AuditListener.class })
public class Product extends ModelBase implements Serializable {
	/** explicit set serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	 /**
     * String product_name, name of product
     * @author Bo Zhu
     */ 
	protected String product_name;
	 /**
     * String price, price of product
     * @author Bo Zhu
     */ 
	protected double price;
	 /**
     * Category category, category of product
     * @author Bo Zhu
     */ 
	private Category category;
	 /**
     * List OrderedProduct, list of orders
     * @author Bo Zhu
     */ 
	protected List<OrderedProduct> orders = new ArrayList<>();
	
	 /**
     * default constructor, JPA requires each @Entity class have a default constructor
     * @author Bo Zhu
     */
	public Product() {
		super();
	}

	/**
	 * JPA Many-To-One Mapping create a Many-To-One relationship between Product and
	 * Category, join column is CATEGORY_ID
	 * 
	 * getter for category
	 * @return category
	 */
	@ManyToOne
	@JoinColumn(name = "CATEGORY_ID")
	public Category getCategory() {
		return category;
	}
	 /**
     * setter for category
     * @param category, category of product
     * 
     */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * JPA One-To-Many Mapping create a One-To-Many relationship between Product and
	 * OrderedProduct, mappedBy "product"
	 * 
	 * getter for orders
	 * @return orders
	 */
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<OrderedProduct> getOrders() {
		return orders;
	}
	/**
     * 
     * setter for orders
     * @param orders, customer orders
     */
	public void setOrders(List<OrderedProduct> orders) {
		this.orders = orders;
	}
	/**
     * getter for product name
     * @return product_name
     */
	@Column(name = "PRODUCT_NAME")
	public String getProductName() {
		return product_name;
	}
    /**
     * setter for product_name
     * @param product_name, name of product
     */
	public void setProductName(String product_name) {
		this.product_name = product_name;
	}
    
	/**
	 * getter for price of product
	 * @return price
	 */
	@Column(name = "PRICE")
	public double getPrice() {
		return price;
	}
	
    /**
     * setter for price
     * @param price, price of product
     */
	public void setPrice(double price) {
		this.price = price;
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