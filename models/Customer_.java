package com.algonquincollege.cst8277.models;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-04-07T21:41:36.587+0000")
@StaticMetamodel(Customer.class)
public class Customer_ extends ModelBase_ {
	public static volatile SingularAttribute<Customer, PlatformUser> user;
	public static volatile ListAttribute<Customer, CustomerOrder> orders;
	public static volatile SingularAttribute<Customer, String> customerName;
	public static volatile SingularAttribute<Customer, String> email;
	public static volatile SingularAttribute<Customer, String> phone;
	public static volatile SingularAttribute<Customer, String> address;
	public static volatile SingularAttribute<Customer, String> city;
}
