package bbs.dao;

import static bbs.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import bbs.beans.UserMessage;
import bbs.exception.SQLRuntimeException;

public class UserMessageDao {

	public List<UserMessage> getUserMessages(Connection connection, int num, String category, String fromDate,
			String toDate) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM user_message ");
			sql.append("where created_at >= ? and created_at <= ? "); // 投稿日時>=from_date
																		// 投稿日時<=to_date
			if (StringUtils.isEmpty(category) != true) {
				sql.append("and category = ? "); // カテゴリーが選択されていればappend
			}
			sql.append("ORDER BY created_at DESC limit " + num);

			ps = connection.prepareStatement(sql.toString());
			ps.setString(1, fromDate);
			ps.setString(2, toDate);
			if (StringUtils.isEmpty(category) != true) {
				ps.setString(3, category);
			}

			ResultSet rs = ps.executeQuery();
			List<UserMessage> ret = toUserMessageList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private static List<UserMessage> toUserMessageList(ResultSet rs) throws SQLException {

		List<UserMessage> ret = new ArrayList<UserMessage>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String subject = rs.getString("subject");
				String body = rs.getString("body");
				String category = rs.getString("category");
				int user_id = rs.getInt("user_id");
				String user_name = rs.getString("user_name");
				int branch_id = rs.getInt("branch_id");
				int position_id = rs.getInt("position_id");
				Timestamp created_at = rs.getTimestamp("created_at");

				UserMessage message = new UserMessage();
				message.setId(id);
				message.setSubject(subject);
				message.setBody(body);
				message.setCategory(category);
				message.setUser_id(user_id);
				message.setUser_name(user_name);
				message.setBranch_id(branch_id);
				message.setPosition_id(position_id);
				message.setCreated_at(created_at);

				ret.add(message);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	public List<UserMessage> getCategory(Connection connection) {
		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM user_message GROUP BY category ");

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<UserMessage> ret = toUserMessageList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

}
