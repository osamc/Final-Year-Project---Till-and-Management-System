package com.sam.tillsystem.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sam.tillsystem.api.SellerAPI;
import com.sam.tillsystem.models.user.Seller;

@Repository
public class SellerImpl extends BaseImpl implements SellerAPI {

	public SellerImpl(JdbcTemplate template) {
		super(template);
	}

	private RowMapper<Seller> sellerMapper = new RowMapper<Seller>() {

		@Override
		public Seller mapRow(ResultSet rs, int rowNum) throws SQLException {
			Seller seller = new Seller();
			seller.setId(rs.getInt("id"));
			seller.setLogin(rs.getString("login_code"));
			seller.setName(rs.getString("name"));
			return seller;
		}

	};

	private Seller getLastSeller() {
		return this.template.query("SELECT * FROM seller ORDER BY id DESC LIMIT 1", this.sellerMapper).stream()
				.findFirst().orElse(null);
	}

	@Override
	public Seller createSeller(String name, String loginCode) {

		this.template.update("INSERT INTO SELLER (name, login_code) VALUES (?,?)", new Object[] { name, loginCode },
				new int[] { Types.VARCHAR, Types.VARCHAR });

		return getLastSeller();
	}

	@Override
	public Seller getSeller(int id) {
		return this.template.query("SELECT * FROM seller WHERE id=?", new Object[] { id }, new int[] { Types.INTEGER },
				sellerMapper).stream().findFirst().orElse(null);
	}

	@Override
	public boolean updateSeller(Seller seller) {
		return this.template.update("UPDATE seller SET name=?, login_code=? WHERE id=?",
				new Object[] { seller.getName(), seller.getLogin(), seller.getId() },
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.INTEGER }) > 0;
	}

	@Override
	public boolean removeSeller(Seller seller) {
		return removeSeller(seller.getId());
	}

	@Override
	public boolean removeSeller(int id) {
		return this.template.update("DELETE FROM seller WHERE id=?", new Object[] { id },
				new int[] { Types.INTEGER }) > 0;
	}

	@Override
	public List<Seller> getSellers() {
		return this.template.query("SELECT * FROM seller", sellerMapper);
	}

	@Override
	public Seller login(String loginCode) {
		return this.template
				.query("SELECT * FROM seller WHERE login_code=?", new Object[] {loginCode}, new int[] {Types.VARCHAR}, sellerMapper).stream()
				.findFirst().orElse(null);
	}

}
