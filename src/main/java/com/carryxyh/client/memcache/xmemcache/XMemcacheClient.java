package com.carryxyh.client.memcache.xmemcache;

import com.carryxyh.client.memcache.AbstractMemcacheCacheClient;
import com.carryxyh.common.ByteArrayResult;
import com.carryxyh.common.Command;
import com.carryxyh.config.ClientConfig;
import com.carryxyh.config.Config;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.transcoders.CachedData;
import net.rubyeye.xmemcached.transcoders.CompressionMode;
import net.rubyeye.xmemcached.transcoders.Transcoder;
import net.rubyeye.xmemcached.utils.AddrUtil;

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
    protected void doInit(Config config) throws Exception {
        ClientConfig clientConfig = (ClientConfig) config;
        String host = clientConfig.getHost();
        int port = clientConfig.getPort();
        MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddressMap(host + ":" + port));
        this.memcachedClient = builder.build();
    }

    @Override
    public ByteArrayResult get(Command getCmd) {
        try {
            Object o = memcachedClient.get(getCmd.key(), DEFAULT_TRANSCODER);
            return ByteArrayResult.valueOf((byte[]) o);
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
