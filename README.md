# Shopping_Cart_Project_Java
This is a E_commence platform for shopping cart, only back-end models.

Note: It is a jpin table caleed OrderedProduct, which relates to CustomerOrder and Product. We usually use @JoinTable to simple join two tables, but in this case, in OrderedProduct table has extra column called quanitity, so I used 2 1:M relationship betweenCustomerOrder and Product, both these two tables mapped to OrderedProduct.
