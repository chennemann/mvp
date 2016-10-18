package de.chennemann.libraries.mvp.presenter;

/**
 * @param <VIEW> Type of the view
 */
public interface ViewActions<VIEW> {
    void bindView(VIEW view);

    void unbindView();

    VIEW getView();
}
