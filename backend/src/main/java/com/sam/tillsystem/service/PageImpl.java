package com.sam.tillsystem.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sam.tillsystem.api.PageAPI;
import com.sam.tillsystem.models.page.PageInfo;

@Repository
public class PageImpl extends BaseImpl implements PageAPI {

	private RowMapper<PageInfo> infoMapper = new RowMapper<PageInfo>() {

		@Override
		public PageInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			PageInfo toReturn = new PageInfo();
			toReturn.setInfoId(rs.getInt("infoid"));
			toReturn.setName(rs.getString("name"));
			toReturn.setXRows(rs.getInt("xrows"));
			toReturn.setYRows(rs.getInt("yrows"));
			return toReturn;
		}

	};

	public PageImpl(JdbcTemplate template) {
		super(template);
	}

	@Override
	public int createPage(String name, int xcount, int ycount) {

		int rows = this.template.update("INSERT INTO page_info (name, xrows, yrows) VALUES (?,?,?)",
				new Object[] { name, xcount, ycount }, new int[] { Types.VARCHAR, Types.INTEGER, Types.INTEGER });
		System.out.println(rows + " of rows inserted.");

		return rows;
	}

	@Override
	public int createPage(PageInfo page) {
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

}
