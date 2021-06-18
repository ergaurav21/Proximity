package com.proximity.technicaltest;

import org.apache.commons.io.IOUtils;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class TestHelper {
    private static final String BEARER_AUTH_HEADER = "Bearer ";

    public static MockHttpServletRequestBuilder servletRequestBuilder(String url) {
        return MockMvcRequestBuilders.get(url)
                .header(BEARER_AUTH_HEADER, fromResource("token/validTokenWithKeyId.jwt"));
    }


    public static MockHttpServletRequestBuilder servletPostRequestBuilder(String url) {
        return MockMvcRequestBuilders.post(url)
                .header(BEARER_AUTH_HEADER, fromResource("token/validTokenWithKeyId.jwt"));
    }

    public static MockHttpServletRequestBuilder servletPutRequestBuilder(String url) {
        return MockMvcRequestBuilders.put(url)
                .header(BEARER_AUTH_HEADER, fromResource("token/validTokenWithKeyId.jwt"));
    }

    public static MockHttpServletRequestBuilder servletDeleteRequestBuilder(String url) {
        return MockMvcRequestBuilders.delete(url)
                .header(BEARER_AUTH_HEADER, fromResource("token/validTokenWithKeyId.jwt"));
    }

    public static MockHttpServletRequestBuilder servletPatchRequestBuilder(String url) {
        return MockMvcRequestBuilders.put(url)
                .header(BEARER_AUTH_HEADER, fromResource("token/validTokenWithKeyId.jwt"));
    }



    public static String fromResource(String resource) {
        try {
            return IOUtils.toString(
                    Objects.requireNonNull(TestHelper.class.getClassLoader().getResourceAsStream(resource)),
                    StandardCharsets.UTF_8);
        } catch (IOException ignore) {
            return null;
        }
    }

}
