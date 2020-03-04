package com.sam.tillsystem.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sam.tillsystem.api.PageAPI;
import com.sam.tillsystem.models.page.PageDefinition;
import com.sam.tillsystem.models.page.PageInfo;
import com.sam.tillsystem.models.product.Product;

@Repository
public class PageImpl extends BaseImpl implements PageAPI {

	@Autowired
	ProductImpl productService;

	private RowMapper<PageInfo> infoMapper = new RowMapper<PageInfo>() {

		@Override
		public PageInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			PageInfo toReturn = new PageInfo();
			toReturn.setInfoId(rs.getInt("infoid"));
			toReturn.setName(rs.getString("name"));
			toReturn.setXRows(rs.getInt("xrows"));
			toReturn.setYRows(rs.getInt("yrows"));
			toReturn.setContents(getPageDefinition(toReturn.getInfoId()));
			return toReturn;
		}

	};

	public PageImpl(JdbcTemplate template) {
		super(template);
	}

	@Override
	public PageInfo createPage(String name, int xcount, int ycount) {

		int rows = this.template.update("INSERT INTO page_info (name, xrows, yrows) VALUES (?,?,?)",
				new Object[] { name, xcount, ycount }, new int[] { Types.VARCHAR, Types.INTEGER, Types.INTEGER });

		return rows > 0 ? getLastPage() : null;
	}

	@Override
	public PageInfo createPage(PageInfo page) {
		return createPage(page.getName(), page.getXRows(), page.getYRows());
	}

	@Override
	public PageInfo getPage(int id) {
		return template.query("SELECT * FROM page_info WHERE infoid=?", new Object[] { id }, infoMapper).stream()
				.findFirst().orElse(null);
	}

	@Override
	public List<PageInfo> getPages() {
		List<PageInfo> pages = template.query("SELECT * from page_info", new Object[] {}, infoMapper);
		return pages;
	}

	@Override
	public int updatePage(PageInfo page) {
		return template.update("UPDATE page_info SET name=?, xrows=?, yrows=? WHERE infoid=?",
				new Object[] { page.getName(), page.getXRows(), page.getYRows(), page.getInfoId() },
				new int[] { Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER });
	}

	@Override
	public boolean deletePage(int id) {

		return template.update("DELETE FROM page_info WHERE infoid=?", new Object[] { id },
				new int[] { Types.INTEGER }) == 1;
	}

	@Override
	public boolean deletePage(PageInfo page) {
		return deletePage(page.getInfoId());
	}

	public PageInfo getLastPage() {
		return this.template.query("SELECT * FROM page_info ORDER BY infoid DESC LIMIT 1", infoMapper).stream().findFirst()
				.orElse(null);

	}

	public List<PageDefinition> getPageDefinition(int pageId) {

		return this.template.query("SELECT * FROM page_def WHERE page_id=?", new Object[] { pageId },
				new int[] { Types.INTEGER }, new RowMapper<PageDefinition>() {

					@Override
					public PageDefinition mapRow(ResultSet rs, int rowNum) throws SQLException {
						PageDefinition pageDef = new PageDefinition();

						pageDef.setX(rs.getInt("x"));
						pageDef.setY(rs.getInt("y"));

						Product p = productService.getProduct(rs.getInt("product_id"));
						pageDef.setProduct(p);

						return pageDef;
					}

				});

	}

}
