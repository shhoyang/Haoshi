package com.haoshi.sqlite.ormlite;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * @author: HaoShi
 */

public class DbManager {

    private OpenHelper openHelper;
    private Dao dao;

    public DbManager(Context context) throws SQLException {
        openHelper = OpenHelper.getInstance(context);
        dao = openHelper.getDao(Personnel.class);
    }

    public int insert(Personnel personnel) throws SQLException {
        return dao.create(personnel);
    }

    public int delete(Personnel personnel) throws SQLException {
        return dao.delete(personnel);
    }

    public int deleteByNum(String num) throws SQLException {
        DeleteBuilder builder = dao.deleteBuilder();
        builder.where().eq("num", num);
        return builder.delete();
    }

    public int deleteAll() throws SQLException {
        int result = -1;
        for (Personnel personnel : query()) {
            result = delete(personnel);
        }
        return result;
    }

    public int update(Personnel personnel) throws SQLException {
        return dao.update(personnel);
    }

    public int updateByNum(Personnel personnel) throws SQLException {
        UpdateBuilder builder = dao.updateBuilder();
        builder.updateColumnValue("name", personnel.getName()).where().eq("num", personnel.getNum());
        return builder.update();
    }

    public List<Personnel> queryByNum(String num) throws SQLException {
        QueryBuilder builder = dao.queryBuilder();
        builder.where().eq("num", num);
        return builder.query();
    }

    public List<Personnel> query() throws SQLException {
        return dao.queryForAll();
    }
}
