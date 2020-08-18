package com.rh.note.util;

public class LambdaUtil {

    public static <E extends Exception> void hiddenExceptionRun(RunnableWithExceptions<E> runnableWithExceptions) {
        try {
            runnableWithExceptions.run();
        } catch (Exception e) {
            throwAsUnchecked(e);
        }
    }

    public static <R, E extends Exception> R hiddenExceptionSup(SupplierWithExceptions<R, E> supplierWithExceptions) {
        try {
            return supplierWithExceptions.get();
        } catch (Exception e) {
            throwAsUnchecked(e);
        }
        return null;
    }

    @FunctionalInterface
    public interface SupplierWithExceptions<T, E extends Exception> {
        T get() throws E;
    }

    @FunctionalInterface
    public interface RunnableWithExceptions<E extends Exception> {
        void run() throws E;
    }

    @SuppressWarnings("unchecked")
    public static <E extends Throwable> void throwAsUnchecked(Exception exception) throws E {
        throw (E) exception;
    }

}
