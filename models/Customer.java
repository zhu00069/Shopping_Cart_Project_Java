package com.algonquincollege.cst8277.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Customer class
 */
@Entity
@Table(name = "CUSTOMER")
@EntityListeners({ AuditListener.class })
public class Customer extends ModelBase implements Serializable {
	/** explicit set serialVersionUID */
	private static final long serialVersionUID = 1L;

	// JPA requires each @Entity class have a default constructor
	public Customer() {
		super();
	}

	protected String customerName;
	protected String email;
	protected String phone;
	protected String address;
	protected String city;
	protected PlatformUser user;
	protected List<CustomerOrder> orders = new ArrayList<>();

	/**
	 * create a One-To-One relationship between Customer and Plantform_User, join
	 * column is USER_ID
	 * 
	 * @return user
	 */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ID", unique = true)
	public PlatformUser getUser() {
		return user;
	}

	/**
	 * setter for user of Customer
	 * 
	 * @param user
	 */
	public void setUser(PlatformUser user) {
		this.user = user;
	}

	/**
	 * JPA One-To-Many Mapping create a One-To-Many relationship between Customer
	 * and Order, mappedBy "customerID"
	 * 
	 * @return List of orders
	 */
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<CustomerOrder> getOrders() {
		return orders;
	}

	/**
	 * setter for List of orders of Customer
	 * 
	 * @param orders
	 */
	public void setOrders(List<CustomerOrder> orders) {
		this.orders = orders;
	}

	/**
	 * method for adding order to List pf orders of Customer
	 * 
	 * @param order
	 * @return order
	 */
	public CustomerOrder addOrder(CustomerOrder order) {
		getOrders().add(order);
		order.setCustomer(this);
		return order;
	}

	/**
	 * method for removing order from List of orders of Customer
	 * 
	 * @param order
	 * @return order
	 */
	public CustomerOrder removeOrder(CustomerOrder order) {
		getOrders().remove(order);
		return order;
	}

	@Column(name = "CUSTOMER_NAME")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "PHONE")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "ADDRESS")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "CITY")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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