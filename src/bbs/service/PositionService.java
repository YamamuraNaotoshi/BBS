package bbs.service;

import static bbs.utils.CloseableUtil.*;
import static bbs.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import bbs.beans.Position;
import bbs.dao.PositionDao;

public class PositionService {
	private static final int LIMIT_NUM = 1000;

	public List<Position> getPosition() {

		Connection connection = null;
		try {
			connection = getConnection();

			PositionDao positionDao = new PositionDao();
			List<Position> ret = positionDao.getPosition(connection, LIMIT_NUM);

			commit(connection);

			return ret;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

}