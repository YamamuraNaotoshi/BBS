package bbs.dao;

import static bbs.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bbs.beans.Comment;
import bbs.exception.SQLRuntimeException;

public class CommentDao {
	public void insert(Connection connection, Comment comment) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO comments ( ");
			sql.append("id");
			sql.append(", body");
			sql.append(", message_id");
			sql.append(", user_id");
			sql.append(", user_name");
			sql.append(", branch_id");
			sql.append(", position_id");
			sql.append(", created_at");
			sql.append(", updated_at");
			sql.append(") VALUES (");
			sql.append("null"); // id
			sql.append(", ?"); // body
			sql.append(", ?"); // message_id
			sql.append(", ?"); // user_id
			sql.append(", ?"); // user_name
			sql.append(", ?"); // branch_id
			sql.append(", ?"); // position_id
			sql.append(", CURRENT_TIMESTAMP"); // created_at
			sql.append(", CURRENT_TIMESTAMP"); // updated_at
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, comment.getBody());
			ps.setInt(2, comment.getMessage_id());
			ps.setInt(3, comment.getUser_id());
			ps.setString(4, comment.getUser_name());
			ps.setInt(5, comment.getBranch_id());
			ps.setInt(6, comment.getPosition_id());

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void deleteComment(Connection connection, int comment) {

		PreparedStatement ps = null;
		try {
			String sql = "DELETE FROM comments WHERE id = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, comment);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {

			close(ps);
		}
	}

}
