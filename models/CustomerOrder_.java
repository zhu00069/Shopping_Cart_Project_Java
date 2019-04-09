package com.algonquincollege.cst8277.models;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-04-09T15:34:21.310+0000")
@StaticMetamodel(CustomerOrder.class)
public class CustomerOrder_ extends ModelBase_ {
	public static volatile ListAttribute<CustomerOrder, OrderedProduct> products;
	public static volatile SingularAttribute<CustomerOrder, Double> amount;
	public static volatile SingularAttribute<CustomerOrder, Customer> customer;
}
