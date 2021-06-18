package com.proximity.technicaltest.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends HandledException {
    private static final long serialVersionUID = -3288463931791618926L;
    protected  String type;

    public NotFoundException(final String type, final String title) {
        super(HttpStatus.NOT_FOUND, title);
        this.type = type;
    }

    public NotFoundException(final String title) {
        super(HttpStatus.NOT_FOUND, title);
    }

    public NotFoundException(final String type, final String title, final String detail) {
        super(HttpStatus.NOT_FOUND, title, detail);
        this.type = type;
    }

    public NotFoundException(final String type, final Throwable cause, final String title) {
        super(cause, HttpStatus.NOT_FOUND, title);
        this.type = type;
    }

    public NotFoundException(final String type, final Throwable cause, final String title, final String detail) {
        super(cause, HttpStatus.NOT_FOUND, title, detail);
        this.type = type;
    }

    public NotFoundException(final Class<?> type, final String title) {
        super(HttpStatus.NOT_FOUND, title);
        this.type = type.getSimpleName();
    }

    public NotFoundException(final Class<?> type, final String title, final String detail) {
        super(HttpStatus.NOT_FOUND, title, detail);
        this.type = type.getSimpleName();
    }

    public NotFoundException(final Class<?> type, final Throwable cause, final String title) {
        super(cause, HttpStatus.NOT_FOUND, title);
        this.type = type.getSimpleName();
    }

    public NotFoundException(final Class<?> type, final Throwable cause, final String title, final String detail) {
        super(cause, HttpStatus.NOT_FOUND, title, detail);
        this.type = type.getSimpleName();
    }

    @Override
    public String getProblemName() {
        return this.type.toLowerCase() + "-not-found";
    }
}