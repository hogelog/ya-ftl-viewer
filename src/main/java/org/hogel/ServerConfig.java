package org.hogel;

import com.google.common.base.Charsets;
import com.google.common.base.Optional;
import com.google.common.io.Files;
import net.arnx.jsonic.JSON;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ServerConfig {
    private final Map<String, Object> configMap;
    private final int port;
    File baseDir;
    public ServerConfig(Map<String, Object> configMap) {
        this.configMap = configMap;
        baseDir = getFile("baseDir");
        port = getNumber("port").intValue();
    }

    public File getBaseDir() {
        return baseDir;
    }

    public int getPort() {
        return port;
    }

    private Number getNumber(String key) {
        Number value = (Number) configMap.get(key);
        return value;
    }

    private File getFile(String key) {
        String path = getString(key);
        return path == null ? null : new File(path);
    }

    private String getString(String key) {
        String value = (String) configMap.get(key);
        return value;
    }

    public static Optional<ServerConfig> loadConfig(String configFileName) throws IOException {
        File configFile = new File(configFileName);
        if (!configFile.exists())
            return Optional.absent();
        BufferedReader reader = Files.newReader(configFile, Charsets.UTF_8);
        Map<String, Object> configMap = JSON.decode(reader);
        ServerConfig config = new ServerConfig(configMap);
        return Optional.of(config);
    }
}
