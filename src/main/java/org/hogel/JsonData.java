package org.hogel;

import com.google.common.base.Charsets;
import com.google.common.base.Optional;
import com.google.common.io.Files;
import net.arnx.jsonic.JSON;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class JsonData {
    private final Map<String, Object> data;

    public JsonData(Map<String, Object> map) {
        data = (Map<String, Object>) map.get("data");
    }

    public static Optional<JsonData> load(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            return Optional.absent();
        }
        String text = Files.toString(file, Charsets.UTF_8);
        Map<String, Object> map = JSON.decode(text);
        return Optional.of(new JsonData(map));
    }

    public Map<String, Object> getData() {
        return data;
    }
}
