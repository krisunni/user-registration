package com.krisunni.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.krisunni.user.domain.User;
import org.springframework.http.MediaType;

import java.io.IOException;

/**
 * Utility for testing REST controllers.
 */
public final class TestUtil {
    public static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON_UTF8;
    private static final ObjectMapper mapper = createObjectMapper();
    public static final String DEFAULT_FIRST_NAME = "First Name";
    public static final String UPDATED_FIRST_NAME = "New First";
    public static final String DEFAULT_LAST_NAME = "Last Name";
    public static final String UPDATED_LAST_NAME = "New Last Name";
    public static final String DEFAULT_TELEPHONE = "123-456-7890";
    public static final String UPDATED_TELEPHONE = "098-765-4321";
    public static final String DEFAULT_EMAIL = "test@krisunni.com";
    public static final String UPDATED_EMAIL = "update@krisunni.com";

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    public static User initialUser() {
        return User.setUser(DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_TELEPHONE, DEFAULT_EMAIL);
    }

    public static User updatedUser() {
        return User.setUser(UPDATED_FIRST_NAME, UPDATED_LAST_NAME, UPDATED_TELEPHONE, UPDATED_EMAIL);
    }

    /**
     * Convert an object to JSON byte array.
     *
     * @param object the object to convert.
     * @return the JSON byte array.
     * @throws IOException
     */
    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        return mapper.writeValueAsBytes(object);
    }
}
