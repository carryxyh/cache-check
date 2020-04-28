package com.carryxyh.client.memcache.xmemcache;

import com.carryxyh.client.memcache.AbstractMemcacheCacheClient;
import com.carryxyh.config.ClientConfig;
import com.carryxyh.config.ClientInfo;
import com.carryxyh.config.Config;
import com.google.common.collect.Lists;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.transcoders.CachedData;
import net.rubyeye.xmemcached.transcoders.CompressionMode;
import net.rubyeye.xmemcached.transcoders.Transcoder;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * XMemcacheClient
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public final class XMemcacheClient extends AbstractMemcacheCacheClient {

    private MemcachedClient memcachedClient;

    private static final Transcoder<byte[]> DEFAULT_TRANSCODER = new DefaultTranscoder();

    @Override
    protected void doInit(Config config) {
        ClientConfig clientConfig = (ClientConfig) config;
        List<ClientInfo> clientInfos = clientConfig.getClientInfos();
        List<InetSocketAddress> addresses = Lists.newArrayList();
        for (ClientInfo c : clientInfos) {
            addresses.add(new InetSocketAddress(c.getHost(), c.getPort()));
        }
        MemcachedClientBuilder builder = new XMemcachedClientBuilder(addresses);
        try {
            this.memcachedClient = builder.build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] get(String key) {
        try {
            Object o = memcachedClient.get(key, DEFAULT_TRANSCODER);
            return (byte[]) o;
        } catch (TimeoutException | InterruptedException | MemcachedException e) {
            return null;
        }
    }

    static class DefaultTranscoder implements Transcoder<byte[]> {

        @Override
        public CachedData encode(byte[] o) {
            return new CachedData(0, o);
        }

        @Override
        public byte[] decode(CachedData cachedData) {
            return cachedData.getData();
        }

        @Override
        public void setPrimitiveAsString(boolean b) {
        }

        @Override
        public void setPackZeros(boolean b) {
        }

        @Override
        public void setCompressionThreshold(int i) {
        }

        @Override
        public boolean isPrimitiveAsString() {
            return false;
        }

        @Override
        public boolean isPackZeros() {
            return false;
        }

        @Override
        public void setCompressionMode(CompressionMode compressionMode) {
        }
    }
}
