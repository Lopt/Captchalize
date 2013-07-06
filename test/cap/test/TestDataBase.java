package cap.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    cap.test.TestDataBaseProxyHSQL.class,
    cap.test.TestDataBaseProxyMySQL.class,
    cap.test.TestDataBaseProxySQLite.class,
    cap.test.TestDataBaseJPA.class,
})
public class TestDataBase {}