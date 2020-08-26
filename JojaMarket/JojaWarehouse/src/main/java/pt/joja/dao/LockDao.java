package pt.joja.dao;

import pt.joja.bean.Lock;

public interface LockDao {

    public Lock getLockById(Integer id);

    public Lock getLockByIdSimple(Integer id);

    public Lock getLockByIdByStep(Integer id);

}
