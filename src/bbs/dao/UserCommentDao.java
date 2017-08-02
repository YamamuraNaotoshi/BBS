package bbs.dao;

import static bbs.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bbs.beans.UserComment;
import bbs.exception.SQLRuntimeException;

public class UserCommentDao {
	public List<UserComment> getUserComments(Connection connection, int num) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM user_comment ");
			sql.append("ORDER BY created_at DESC limit " + num);

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<UserComment> ret = toUserCommentList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private static List<UserComment> toUserCommentList(ResultSet rs) throws SQLException {

		List<UserComment> ret = new ArrayList<UserComment>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String body = rs.getString("body");
				int user_id = rs.getInt("user_id");
				int message_id = rs.getInt("message_id");
				String user_name = rs.getString("user_name");
				int branch_id = rs.getInt("branch_id");
				int position_id = rs.getInt("position_id");
				Timestamp created_at = rs.getTimestamp("created_at");

				UserComment comment = new UserComment();
				comment.setId(id);
				comment.setBody(body);
				comment.setUser_id(user_id);
				comment.setMessage_id(message_id);
				comment.setUser_name(user_name);
				comment.setBranch_id(branch_id);
				comment.setPosition_id(position_id);
				comment.setCreated_at(created_at);

				ret.add(comment);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

}
