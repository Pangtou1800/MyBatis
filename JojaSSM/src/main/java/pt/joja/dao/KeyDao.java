package pt.joja.dao;

import pt.joja.bean.Key;

public interface KeyDao {

    public Key getKeyById(Integer id);

    public Key getKeyByIdSimple(Integer id);

    public Key getKeysByLockId(Integer lockId);
}
