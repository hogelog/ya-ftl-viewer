package org.hogel;

import com.google.common.base.Charsets;
import com.google.common.base.Optional;
import com.google.common.io.Files;
import net.arnx.jsonic.JSON;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonData {
    private final Map<String, Object> data;

    @SuppressWarnings("unchecked")
    public JsonData(File file, Map<String, Object> map) throws IOException {
        data = new HashMap<String, Object>();
        List<String> importPaths = (List<String>) map.get("import");
        if (importPaths != null) {
            File baseDir = file.getParentFile();
            importData(baseDir, importPaths);
        }
        Map<String, Object> mapData = (Map<String, Object>) map.get("data");
        if (mapData != null)
            data.putAll(mapData);
    }

    private void importData(File baseDir, List<String> importPaths) throws IOException {
        for (String importPath : importPaths) {
            File importFile = new File(baseDir, importPath);
            Optional<JsonData> importJsonData = JsonData.load(importFile);
            if (!importJsonData.isPresent()) {
                continue;
            }
            Map<String, Object> importData = importJsonData.get().getData();
            data.putAll(importData);
        }
    }

    public static Optional<JsonData> load(String path) throws IOException {
        File file = new File(path);
        return load(file);
    }

    public static Optional<JsonData> load(File file) throws IOException {
        if (!file.exists()) {
            return Optional.absent();
        }
        String text = Files.toString(file, Charsets.UTF_8);
        Map<String, Object> map = JSON.decode(text);
        return Optional.of(new JsonData(file, map));
    }

    public Map<String, Object> getData() {
        return data;
    }
}
