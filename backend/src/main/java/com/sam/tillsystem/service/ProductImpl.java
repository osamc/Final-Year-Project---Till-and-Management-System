package com.sam.tillsystem.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sam.tillsystem.models.product.Group;
import com.sam.tillsystem.api.ProductAPI;
import com.sam.tillsystem.models.product.Product;

@Repository
public class ProductImpl extends BaseImpl implements ProductAPI {

	private RowMapper<Product> productMapper = new RowMapper<Product>() {

		@Override
		public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
			Product product = new Product();
			Group group = new Group();
			group.setId(rs.getInt("group_id"));
			group.setName(rs.getString("group_name"));
			product.setGroup(group);
			product.setId(rs.getInt("id"));
			product.setInfo(rs.getString("info"));
			product.setName(rs.getString("name"));
			product.setImage(rs.getString("image"));
			product.setPrice(rs.getDouble("price"));
			return product;
		}

	};

	private RowMapper<Group> groupMapper = new RowMapper<Group>() {

		@Override
		public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
			Group group = new Group();
			group.setId(rs.getInt("group_id"));
			group.setName(rs.getString("group_name"));
			group.setSystem(rs.getBoolean("system"));
			return group;
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
		return createProduct(name, info, imageUrl, price, 1);

	}

	@Override
	public Product createProduct(Product product) {
		return createProduct(product.getName(), product.getInfo(), product.getImage(), product.getPrice(),
				product.getGroup().getId());
	}

	@Override
	public Product createProduct(String name, String info, String imageUrl, Double price, Integer group) {
		int rows = this.template.update("INSERT INTO product (name, info, image, price, groupid) VALUES (?,?,?,?,?)",
				new Object[] { name, info, imageUrl, price, group },
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.DOUBLE, Types.INTEGER });

		return rows != 0 ? getLastProduct() : null;
	}

	@Override
	public boolean updateProduct(Product product) {
		return this.template.update("UPDATE product SET name=?, info=?, image=?, price=?, groupid=? WHERE id=?",
				new Object[] { product.getName(), product.getInfo(), product.getImage(), product.getPrice(),
						product.getGroup().getId(), product.getId(),  },
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.DOUBLE, Types.INTEGER, Types.INTEGER}) > 0;
	}

	@Override
	public boolean deleteProduct(Product product) {
		return deleteProduct(product.getId());
	}

	@Override
	public boolean deleteProduct(int id) {
		return this.template.update("DELETE FROM product WHERE id=?", new Object[] { id },
				new int[] { Types.INTEGER }) > 0;
	}

	@Override
	public Product getProduct(int id) {

		return this.template.query("SELECT * FROM product LEFT JOIN product_group ON product_group.group_id = product.groupid WHERE id=?", new Object[] { id }, new int[] { Types.INTEGER },
				productMapper).stream().findFirst().orElse(null);

	}

	@Override
	public Product getProduct(Product product) {
		return getProduct(product.getId());
	}

	@Override
	public List<Product> getProducts() {
		return this.template.query("SELECT * FROM product LEFT JOIN product_group ON product_group.group_id = product.groupid", productMapper);
	}

	public Product getLastProduct() {
		return this.template.query("SELECT * FROM product LEFT JOIN product_group ON product_group.group_id = product.groupid ORDER BY id DESC LIMIT 1", productMapper).stream().findFirst()
				.orElse(null);
	}

	public Group getLastGroup() {
		return this.template.query("SELECT * FROM product_group ORDER BY group_id DESC LIMIT 1", groupMapper).stream()
				.findFirst().orElse(null);
	}

	@Override
	public Group createGroup(String groupName) {
		int rows = this.template.update("INSERT INTO product_group (group_name, system) VALUES (?,false)", new Object[] { groupName },
				new int[] { Types.VARCHAR });
		return rows > 0 ? getLastGroup() : null;
	}

	@Override
	public boolean updateGroup(Group group) {
		int rows = this.template.update("UPDATE product_group SET group_name=? WHERE group_id=?",
				new Object[] { group.getName(), group.getId() }, new int[] { Types.VARCHAR, Types.INTEGER });
		return rows > 0;
	}

	@Override
	public boolean deleteGroup(Group group) {
		if (!group.isSystem()) {
			this.template.update("UPDATE product SET groupid=1 WHERE groupid=?", new Object[] { group.getId() },
					new int[] { Types.INTEGER });
			return this.template.update("DELETE FROM product_group WHERE group_id=?", new Object[] { group.getId() },
					new int[] { Types.INTEGER }) > 0;
		} else {
			return false;
		}
		
	}

	@Override
	public List<Group> getGroups() {
		return getGroups(false);
	}

	@Override
	public List<Group> getGroups(boolean withProducts) {
		List<Group> groups = this.template.query("SELECT * FROM product_group WHERE 1=1", groupMapper);

		if (withProducts) {

			groups.forEach(group -> {
				group.setProducts(this.template.query("SELECT * FROM product LEFT JOIN product_group ON product_group.group_id = product.groupid WHERE groupid=?",
						new Object[] { group.getId() }, new int[] { Types.INTEGER }, this.productMapper));
			});

		}

		return groups;

	}

}
