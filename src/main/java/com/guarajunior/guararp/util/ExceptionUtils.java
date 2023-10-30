package com.guarajunior.guararp.util;

public class ExceptionUtils {

    public static String getExceptionRootCauseMessage(Exception ex) {
        return getRootCause(ex).getMessage();
    }

    public static Throwable getRootCause(Throwable throwable) {
        if (throwable == null) {
            return null;
        }

        Throwable cause = throwable.getCause();
        Throwable rootCause = throwable;

        while (cause != null) {
            rootCause = cause;
            cause = cause.getCause();
        }

        return rootCause;
    }
}
