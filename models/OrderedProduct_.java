package com.algonquincollege.cst8277.models;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-04-09T15:42:52.919+0000")
@StaticMetamodel(OrderedProduct.class)
public class OrderedProduct_ extends ModelBase_ {
	public static volatile SingularAttribute<OrderedProduct, CustomerOrder> customerOrder;
	public static volatile SingularAttribute<OrderedProduct, Product> product;
	public static volatile SingularAttribute<OrderedProduct, Integer> quantity;
}
