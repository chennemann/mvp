package de.chennemann.libraries.mvp.common;

public interface TypeFactory<TYPE> extends Factory<TYPE> {

    Class<? extends TYPE> getTypeClazz();
}
