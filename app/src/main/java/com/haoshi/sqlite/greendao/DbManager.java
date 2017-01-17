package com.haoshi.sqlite.greendao;


import android.content.Context;

import java.util.List;

/**
 * eq()：==
 * noteq()：!=
 * gt()： >
 * lt()：<
 * ge：>=
 * le:<=
 * like()：包含
 * between：俩者之间
 * in：在某个值内
 * notIn：不在某个值内
 * limit(int): 限制查询的数量;
 * offset(int): 每次返回的数量; offset不能单独使用；
 * <p>
 * 添加-----------------------------------------------------------
 * long    insert(T entity)：将给定的实体插入数据库
 * void    insertInTx(java.lang.Iterable<T> entities)：使用事务操作，将给定的实体集合插入数据库
 * void    insertInTx(java.lang.Iterable<T> entities, boolean setPrimaryKey)：使用事务操作，将给定的实体集合插入数据库，设置是否设定主键
 * void    insertInTx(T... entities)：将给定的实体插入数据库
 * long    insertOrReplace(T entity)：将给定的实体插入数据库，若此实体类存在，则覆盖
 * void    insertOrReplaceInTx(java.lang.Iterable<T> entities)：使用事务操作，将给定的实体插入数据库，若此实体类存在，则覆盖
 * void    insertOrReplaceInTx(java.lang.Iterable<T> entities, boolean setPrimaryKey)：使用事务操作，将给定的实体插入数据库，若此实体类存在，则覆盖，设置是否设定主键
 * void    insertOrReplaceInTx(T... entities)：使用事务操作，将给定的实体插入数据库，若此实体类存在，则覆盖
 * long    insertWithoutSettingPk(T entity)：将给定的实体插入数据库,但不设定主键
 * void    save(T entity)：将给定的实体插入数据库，若此实体类存在，则更新
 * void    saveInTx(T... entities)：使用事务操作，将给定的实体插入数据库，若此实体类存在，则更新
 * void    saveInTx(java.lang.Iterable<T> entities)：将给定的实体插入数据库，若此实体类存在，则更新
 * <p>
 * 删除-----------------------------------------------------------
 * void    delete(T entity)：从数据库中删除给定的实体
 * void    deleteAll() ：删除数据库中全部数据
 * void    deleteByKey(K key)：从数据库中删除给定Key所对应的实体
 * void    deleteByKeyInTx(java.lang.Iterable<K> keys)：使用事务操作删除数据库中给定的所有key所对应的实体
 * void    deleteByKeyInTx(K... keys)：使用事务操作删除数据库中给定的所有key所对应的实体
 * void    deleteInTx(java.lang.Iterable<T> entities)：使用事务操作删除数据库中给定实体集合中的实体
 * void    deleteInTx(T... entities)：使用事务操作删除数据库中给定的实体
 * <p>
 * 更新-----------------------------------------------------------
 * void    update(T entity) ：更新给定的实体
 * void    updateInsideSynchronized(T entity, DatabaseStatement stmt, boolean lock)
 * void    updateInsideSynchronized(T entity, android.database.sqlite.SQLiteStatement stmt, boolean lock)
 * void    updateInTx(java.lang.Iterable<T> entities) ：使用事务操作，更新给定的实体
 * void    updateInTx(T... entities)：使用事务操作，更新给定的实体
 * <p>
 * 查询-----------------------------------------------------------
 * T    load(K key)：加载给定主键的实体
 * T    loadByRowId(long rowId) ：加载某一行并返回该行的实体
 * T    loadUnique(android.database.Cursor cursor) ：从cursor中读取、返回唯一实体
 * T    loadUniqueAndCloseCursor(android.database.Cursor cursor) ：从cursor中读取、返回唯一实体，并关闭该cursor  
 * java.util.List<T>     loadAll()：加载数据库中所有的实体
 * java.util.List<T>     loadAllAndCloseCursor(android.database.Cursor cursor) ：从cursor中读取、返回实体的列表，并关闭该cursor
 * java.util.List<T>     loadAllFromCursor(android.database.Cursor cursor)：从cursor中读取、返回实体的列表
 *  
 */

/**
 * @author: HaoShi
 */
public class DbManager {

    private static final String DB_NAME = "greendao.db";
    private DaoMaster.OpenHelper openHelper;
    private PersonnelDao dao;
    private static DbManager dbManager;
    private Context context;


    public DbManager(Context context) {
        this.context = context;
        openHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        DaoMaster daoMaster = new DaoMaster(openHelper.getWritableDb());
        DaoSession daoSession = daoMaster.newSession();
        dao = daoSession.getPersonnelDao();
    }


    public static DbManager getInstance(Context context) {
        if (dbManager == null) {
            synchronized (DbManager.class) {
                if (dbManager == null) {
                    dbManager = new DbManager(context);
                }
            }
        }
        return dbManager;
    }

    public void insert(Personnel personnel) {
        dao.insert(personnel);
    }

    public void delete(Personnel personnel) {
        dao.delete(personnel);
    }

    public void deleteByNum(String num) {
        List<Personnel> list = dao.queryBuilder().where(PersonnelDao.Properties.Num.eq(num)).build().list();
        if (list != null && list.size() != 0) {
            for (Personnel p : list) {
                dao.delete(p);
            }
        }
    }

    public void deleteAll() {
        dao.deleteAll();
    }

    public void update(Personnel personnel) {
        dao.update(personnel);
    }

    public void updateByNum(Personnel personnel) {
        List<Personnel> list = dao.queryBuilder().where(PersonnelDao.Properties.Num.eq(personnel.getNum())).build().list();
        if (list != null && list.size() != 0) {
            for (Personnel p : list) {
                p.setName(personnel.getName());
                dao.update(p);
            }
        }
    }

    public List<Personnel> queryByNum(String num) {
        return dao.queryBuilder().where(PersonnelDao.Properties.Num.eq(num)).list();
    }

    public List<Personnel> query() {
        return dao.loadAll();
    }

    public long getCount() {
        return dao.count();
    }
}
