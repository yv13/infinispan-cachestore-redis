package org.infinispan.persistence.redis;

import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.persistence.BaseStoreTest;
import org.infinispan.persistence.redis.configuration.RedisStoreConfigurationBuilder;
import org.infinispan.persistence.redis.support.RedisCluster;
import org.infinispan.persistence.spi.AdvancedLoadWriteStore;
import org.infinispan.test.fwk.TestCacheManagerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

@Test(testName = "persistence.redis.RedisStoreRawValuesTest", groups = "functional")
public class RedisClusterStoreRawValuesTest extends BaseStoreTest
{
    RedisCluster redisCluster;

    @BeforeTest(alwaysRun = true)
    public void startUp()
        throws IOException
    {
        System.out.println("RedisStoreRawValuesTest:Setting up");
        redisCluster = new RedisCluster();
        redisCluster.start();
    }

    @AfterClass
    public void tearDown()
    {
        System.out.println("RedisStoreRawValuesTest:Tearing down");

        try {
            super.tearDown();
        }
        finally {
            redisCluster.kill();
        }
    }

    @Override
    protected AdvancedLoadWriteStore createStore() throws Exception
    {
        ConfigurationBuilder builder = TestCacheManagerFactory.getDefaultCacheConfiguration(false);
        RedisStoreConfigurationBuilder storeConfigurationBuilder = builder.persistence().addStore(RedisStoreConfigurationBuilder.class);
        storeConfigurationBuilder
            .addServer()
            .host("localhost")
            .port(6379)
        ;

        RedisStore store = new RedisStore();
        store.init(createContext(builder.build()));

        return store;
    }

    @Override
    public void testPreload()
    {
        // No support for pre-load
    }
}
