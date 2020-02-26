package com.sam.tillsystem.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.sam.tillsystem.api.ProductAPI;
import com.sam.tillsystem.models.Product.Product;

public class ProductImpl extends BaseImpl implements ProductAPI {

	private RowMapper<Product> productMapper = new RowMapper<Product>() {

		@Override
		public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
			Product product = new Product();
			product.setId(rs.getInt("id"));
			product.setInfo(rs.getString("info"));
			product.setName(rs.getString("name"));
			product.setImage(rs.getString("image"));
			product.setPrice(rs.getDouble("price"));
			return null;
		}

	};

	public ProductImpl(JdbcTemplate template) {
		super(template);
	}

	@Override
	public Product createProduct(String name, String imageUrl, Double price) {

		return createProduct(name, null, imageUrl, price);
	}

	@Override
	public Product createProduct(String name, String info, String imageUrl, Double price) {

		int rows = this.template.update("INSERT INTO product (name,info, image, price) VALUES (?,?,?,?)",
				new Object[] { name, info, imageUrl, price },
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.DOUBLE });

		return rows != 0 ? getLastProduct() : null;
	}

	@Override
	public Product createProduct(Product product) {
		return createProduct(product.getName(), product.getInfo(), product.getImage(), product.getPrice());
	}

	@Override
	public int updateProduct(Product product) {
		return this.template.update("UPDATE product SET name=?, image=?, price=? WHERE id=?",
				new Object[] { product.getName(), product.getImage(), product.getPrice(), product.getId() },
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.DOUBLE, Types.INTEGER });
	}

	@Override
	public int deleteProduct(Product product) {
		return deleteProduct(product.getId());
	}

	@Override
	public int deleteProduct(int id) {
		return this.template.update("DELETE FROM product WHERE id=?", new Object[] { id }, new int[] { Types.INTEGER });
	}

	@Override
	public Product getProduct(int id) {

		return this.template.query("SELECT * FROM product WHERE id=?", new Object[] { id }, new int[] { Types.INTEGER },
				productMapper).stream().findFirst().orElse(null);

	}

	@Override
	public Product getProduct(Product product) {
		return getProduct(product.getId());
	}

	@Override
	public List<Product> getProducts() {
		return this.template.query("SELECT * FROM product", productMapper);
	}

	public Product getLastProduct() {
		return this.template.query("SELECT * FROM product ORDER BY ID DESC LIMIT 1", productMapper).stream().findFirst()
				.orElse(null);
	}

}
