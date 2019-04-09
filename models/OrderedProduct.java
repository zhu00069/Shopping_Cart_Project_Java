package com.algonquincollege.cst8277.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * OrderedProduct class
 */
@Entity
@Table(name = "ORDERED_PRODUCT")
@EntityListeners({ AuditListener.class })
public class OrderedProduct extends ModelBase implements Serializable {
	/** explicit set serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	 /**
     * int quantity, quantity of oreded products
     * @author Bo Zhu
     */ 
	protected int quantity;

	 /**
     * CustomerOrder customerOrder, customer ordered
     * @author Bo Zhu
     */ 
	protected CustomerOrder customerOrder;

	/**
	 * Product product, ordered products
	 */
	protected Product product;

	 /**
     * default constructor, JPA requires each @Entity class have a default constructor
     * @author Bo Zhu
     */
	public OrderedProduct() {
		super();
	}

	/**
	 * getter for customer orders
	 * @return customerOrder
	 */
	@ManyToOne(targetEntity=CustomerOrder.class)
	@JoinColumn(name = "CUSTOMER_ORDER_ID")
	public CustomerOrder getCustomerOrder() {
		return customerOrder;
	}

	/**
	 * setter for customer orders
	 * @param customerOrder, customer orders
	 */
	public void setCustomerOrder(CustomerOrder customerOrder) {
		this.customerOrder = customerOrder;
	}

	/**
	 * getter for product
	 * @return product
	 */
	@ManyToOne(targetEntity=Product.class)
	@JoinColumn(name = "PRODUCT_ID")
	public Product getProduct() {
		return product;
	}

	/**
	 * setter for product
	 * @param product, product
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * getter for quantity of ordered products
	 * @return quantity
	 */
	@Column(name = "QUANTITY")
	public int getQuantity() {
		return quantity;
	}

	/**
	 * setter for quantity of ordered products
	 * @param quantity, quantity of products
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}