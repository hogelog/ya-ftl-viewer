package org.hogel;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class JsonDataTest {
    @Test
    public void hoge() throws IOException {
        JsonData data = JsonData.load("src/test/resources/web/hoge.json").get();
        assertThat((String) data.getData().get("name"), is("hogelog"));
    }

}
