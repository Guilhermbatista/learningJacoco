package com.devsuperior.dscommerce.tests;

import com.devsuperior.dscommerce.entities.Category;
import com.devsuperior.dscommerce.entities.Product;

public class ProductFactory {

	public static Product createProduct() {
		Category category = CategoryFactory.createCategory();
		Product product = new Product(1L, "Playstation",
				"Teste aleatorio do game e descricaoo game e descricaoo game e descricaoo game e descricao", 3999.0,
				"playstation.com.br");
		product.getCategories().add(category);
		return product;
	}

	public static Product createProduct(String name) {
		Product product = createProduct();
		product.setName(name);
		return product;
	}

}
