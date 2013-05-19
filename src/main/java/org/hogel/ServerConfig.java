package org.hogel;

import com.google.common.base.Charsets;
import com.google.common.base.Optional;
import com.google.common.io.Files;
import net.arnx.jsonic.JSON;

import javax.servlet.ServletContext;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.Channels;
import java.util.Map;

public class ServerConfig {
    private final Map<String, Object> configMap;
    File baseDir;
    public ServerConfig(Map<String, Object> configMap) {
        this.configMap = configMap;
        baseDir = getFile("baseDir");
    }

    public File getBaseDir() {
        return baseDir;
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
