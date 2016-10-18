package de.chennemann.libraries.mvp.presenter;

/**
 * @param <PRESENTER> Type of presenter
 */
public interface OnPresenterProvidedListener<PRESENTER> {
    void onPresenterProvided(PRESENTER presenter);
}
