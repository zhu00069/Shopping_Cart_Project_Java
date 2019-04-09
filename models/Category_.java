package com.algonquincollege.cst8277.models;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-04-07T14:55:32.693+0000")
@StaticMetamodel(Category.class)
public class Category_ extends ModelBase_ {
	public static volatile ListAttribute<Category, Product> products;
	public static volatile SingularAttribute<Category, String> categoryName;
}
