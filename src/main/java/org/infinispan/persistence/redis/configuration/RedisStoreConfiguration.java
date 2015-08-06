package org.infinispan.persistence.redis.configuration;

import org.infinispan.commons.configuration.attributes.*;
import org.infinispan.persistence.redis.RedisStore;
import org.infinispan.commons.configuration.BuiltBy;
import org.infinispan.commons.configuration.ConfigurationFor;
import org.infinispan.commons.configuration.attributes.Attribute;
import org.infinispan.configuration.cache.AbstractStoreConfiguration;
import org.infinispan.configuration.cache.AsyncStoreConfiguration;
import org.infinispan.configuration.cache.SingletonStoreConfiguration;

import java.util.ArrayList;
import java.util.List;


@BuiltBy(RedisStoreConfigurationBuilder.class)
@ConfigurationFor(RedisStore.class)
final public class RedisStoreConfiguration extends AbstractStoreConfiguration
{
    public enum Topology
    {
        CLUSTER,
        SERVER
    }

    static final AttributeDefinition<Long> CONNECTION_TIMEOUT = AttributeDefinition.builder("connectionTimeout", 60000L).build();
    static final AttributeDefinition<Long> SOCKET_TIMEOUT = AttributeDefinition.builder("socketTimeout", 60000L).build();
    static final AttributeDefinition<String> PASSWORD = AttributeDefinition.builder("password", null, String.class).build();
    static final AttributeDefinition<Integer> DATABASE = AttributeDefinition.builder("database", 0).build();
    static final AttributeDefinition<Topology> TOPOLOGY = AttributeDefinition.builder("topology", Topology.CLUSTER).build();
    static final AttributeDefinition<List<RedisServerConfiguration>> SERVERS = AttributeDefinition.builder("servers", null, (Class<List<RedisServerConfiguration>>)(Class<?>)List.class).initializer(new AttributeInitializer<List<RedisServerConfiguration>>() {
        @Override
        public List<RedisServerConfiguration> initialize() {
            return new ArrayList<>();
        }
    }).build();

    public static AttributeSet attributeDefinitionSet() {
        return new AttributeSet(RedisStoreConfiguration.class, AbstractStoreConfiguration.attributeDefinitionSet(),
            PASSWORD, DATABASE, SERVERS, TOPOLOGY, CONNECTION_TIMEOUT, SOCKET_TIMEOUT);
    }

    private final Attribute<List<RedisServerConfiguration>> servers;
    private final Attribute<Integer> database;
    private final Attribute<String> password;
    private final Attribute<Topology> topology;
    private final Attribute<Long> socketTimeout;
    private final Attribute<Long> connectionTimeout;

    public RedisStoreConfiguration(
        AttributeSet attributes,
        AsyncStoreConfiguration async,
        SingletonStoreConfiguration singletonStore
    )
    {
        super(attributes, async, singletonStore);
        this.servers = attributes.attribute(SERVERS);
        this.password = attributes.attribute(PASSWORD);
        this.database = attributes.attribute(DATABASE);
        this.topology = attributes.attribute(TOPOLOGY);
        this.socketTimeout = attributes.attribute(SOCKET_TIMEOUT);
        this.connectionTimeout = attributes.attribute(CONNECTION_TIMEOUT);
    }

    public List<RedisServerConfiguration> servers()
    {
        return this.servers.get();
    }

    public int database()
    {
        return this.database.get();
    }

    public String password()
    {
        return this.password.get();
    }

    public Topology topology()
    {
        return this.topology.get();
    }

    public long connectionTimeout()
    {
        return this.connectionTimeout.get();
    }

    public long socketTimeout()
    {
        return this.socketTimeout.get();
    }
}
