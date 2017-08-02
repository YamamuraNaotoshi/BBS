package bbs.dao;

import static bbs.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bbs.beans.Message;
import bbs.exception.SQLRuntimeException;

public class MessageDao {
	public void insert(Connection connection, Message message) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO messages ( ");
			sql.append("id");
			sql.append(", subject");
			sql.append(", body");
			sql.append(", category");
			sql.append(", user_id");
			sql.append(", user_name");
			sql.append(", branch_id");
			sql.append(", position_id");
			sql.append(", created_at");
			sql.append(", updated_at");
			sql.append(") VALUES (");
			sql.append("null"); // id
			sql.append(", ?"); // subject
			sql.append(", ?"); // body
			sql.append(", ?"); // category
			sql.append(", ?"); // user_id
			sql.append(", ?"); // user_name
			sql.append(", ?"); // branch_id
			sql.append(", ?"); // position_id
			sql.append(", CURRENT_TIMESTAMP"); // created_at
			sql.append(", CURRENT_TIMESTAMP"); // updated_at
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, message.getSubject());
			ps.setString(2, message.getBody());
			ps.setString(3, message.getCategory());
			ps.setInt(4, message.getUser_id());
			ps.setString(5, message.getUser_name());
			ps.setInt(6, message.getBranch_id());
			ps.setInt(7, message.getPosition_id());

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}

	}

	public void deleteMessage(Connection connection, int message) {

		PreparedStatement ps = null;
		try {
			String sql = "DELETE FROM messages WHERE id = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, message);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {

			close(ps);
		}
	}

	public void categoryMessage(Connection connection, String category) {

		PreparedStatement ps = null;
		try {
			String sql = "DELETE FROM messages WHERE id = ?";
			ps = connection.prepareStatement(sql);
			ps.setString(1, category);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {

			close(ps);
		}
	}

}
