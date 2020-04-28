package com.carryxyh.config;

import com.carryxyh.Bootstrap;
import com.carryxyh.TempDataDB;
import com.carryxyh.constants.TempDataDBType;
import com.carryxyh.tempdata.FileTempDataDB;
import com.carryxyh.tempdata.MemoryTempDataDB;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * TempDBConfig
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-08
 */
public class TempDBConfig extends AbstractConfig {

    private static final long serialVersionUID = 6422321304840710610L;

    private TempDataDBType tempDataDBType;

    private List<ClientInfo> clientInfos;

    private String tempDataDBHost;

    private int tempDataDBPort;

    private String tempDataDBPassword;

    private int tempDataDBTimeout = 5000;

    private String tempDBPath = "./";

    public TempDataDBType getTempDataDBType() {
        return tempDataDBType;
    }

    public void setTempDataDBType(TempDataDBType tempDataDBType) {
        this.tempDataDBType = tempDataDBType;
    }

    public String getTempDataDBHost() {
        return tempDataDBHost;
    }

    public void setTempDataDBHost(String tempDataDBHost) {
        this.tempDataDBHost = tempDataDBHost;
    }

    public int getTempDataDBPort() {
        return tempDataDBPort;
    }

    public void setTempDataDBPort(int tempDataDBPort) {
        this.tempDataDBPort = tempDataDBPort;
    }

    public String getTempDataDBPassword() {
        return tempDataDBPassword;
    }

    public void setTempDataDBPassword(String tempDataDBPassword) {
        this.tempDataDBPassword = tempDataDBPassword;
    }

    public int getTempDataDBTimeout() {
        return tempDataDBTimeout;
    }

    public void setTempDataDBTimeout(int tempDataDBTimeout) {
        this.tempDataDBTimeout = tempDataDBTimeout;
    }

    public String getTempDBPath() {
        return tempDBPath;
    }

    public void setTempDBPath(String tempDBPath) {
        this.tempDBPath = tempDBPath;
    }

    public List<ClientInfo> getClientInfos() {
        return clientInfos;
    }

    public void setTempDBUrl(String url) {
        if (StringUtils.isBlank(url)) {
            return;
        }
        this.clientInfos = parseClientInfo(url);
    }

    public TempDataDB buildTempDataDB() throws Exception {
        TempDataDBType tempDataDBType = getTempDataDBType();
        if (tempDataDBType == TempDataDBType.MEMORY) {
            TempDataDB db = new MemoryTempDataDB();
            db.init(this);
            return db;
        } else if (tempDataDBType == TempDataDBType.FILE) {
            if (StringUtils.isBlank(tempDBPath)) {
                throw new IllegalArgumentException("out put path is blank, pls use " +
                        Bootstrap.CONFIG_OP + " to config.");
            }
            return new FileTempDataDB(tempDBPath);
        } else if (tempDataDBType == TempDataDBType.MYSQL) {
            // TODO
        } else if (tempDataDBType == TempDataDBType.REDIS) {
            // TODO
        }
        return null;
    }
}
