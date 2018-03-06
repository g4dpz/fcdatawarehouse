package com.badgersoft.datawarehouse.rawdata.config;

import java.util.Properties;

public class TestJpaConfig extends JpaConfig {

    @Override
    protected Properties additionalProperties() {
        return new Properties() {
            private static final long serialVersionUID = -2580236965844364715L;

            {
                setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
                setProperty("hibernate.hbm2ddl.auto", "create-drop");
            }
        };
    }

}
