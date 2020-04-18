package com.sam.tillsystem.jwt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.sam.tillsystem.service.BaseImpl;

@Repository
public class JwtUserImpl extends BaseImpl implements JwtUserAPI {

	@Autowired
	BCryptPasswordEncoder encoder;

	private RowMapper<JwtUser> userMapper = new RowMapper<JwtUser>() {
		@Override
		public JwtUser mapRow(ResultSet rs, int rowNum) throws SQLException {
			JwtUser user = new JwtUser();
			user.setUsername(rs.getString("username"));
			user.setPassword(rs.getString("password"));
			return user;
		}
	};

	public JwtUserImpl(JdbcTemplate template) {
		super(template);
	}

	@Override
	public JwtUser findByUsername(String username) {
		return this.template.query("SELECT * FROM jwt WHERE username=?", new Object[] { username },
				new int[] { Types.VARCHAR }, userMapper).stream().findFirst().orElse(null);
	}

	@Override
	public boolean saveUser(JwtUser user) {
		int rows = this.template.update("INSERT INTO jwt (username, password) VALUES (?,?)",
				new Object[] { user.getUsername(), encoder.encode(user.getPassword()) },
				new int[] { Types.VARCHAR, Types.VARCHAR });
		return rows > 0;
	}

	@Override
	public boolean updateUser(JwtUser user) {
		int rows = this.template.update("UPDATE jwt SET password=? WHERE username=?",
				new Object[] { encoder.encode(user.getPassword()), user.getUsername() },
				new int[] { Types.VARCHAR, Types.VARCHAR });
		return rows > 0;
	}

}
