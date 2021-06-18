package com.proximity.technicaltest.exception;

import java.util.Objects;

public class ExceptionHelper {
    private ExceptionHelper() {
    }

    public static String getMessage(final String title, final String detail) {
        StringBuilder builder = new StringBuilder(title);
        if ( Objects.nonNull(detail) && !detail.isEmpty()) {
            builder = builder.append(" - ").append(detail);
        }

        return builder.toString();
    }
}
