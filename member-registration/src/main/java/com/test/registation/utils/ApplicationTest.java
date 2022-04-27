package com.test.registation.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class ApplicationTest {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected String username = "username";
    protected String referenceCode = "referenceCode";
    protected static String readFile(String filePath) throws IOException {
        try (InputStream stream = ClassLoader.getSystemResourceAsStream(filePath)) {
            byte[] buffers = new byte[Objects.requireNonNull(stream).available()];
            stream.read(buffers, 0, stream.available());
            return new String(buffers);
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }
}
