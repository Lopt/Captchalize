package cap.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    TestDataBaseProxyHSQL.class,
    TestDataBaseProxyMySQL.class,
    TestDataBaseProxySQLite.class,
    TestDataBaseJPA.class,
})
public class TestDataBase {}