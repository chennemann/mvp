package de.androidbytes.libraries.mvp.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import de.androidbytes.libraries.mvp.common.TypeFactory;
import de.androidbytes.libraries.mvp.loader.LoaderBridge;
import de.androidbytes.libraries.mvp.presenter.OnPresenterProvidedListener;
import de.androidbytes.libraries.mvp.presenter.Presenter;

/**
 * Base MVP presenter retain with loader fragment (android.support.v4.app.Fragment)
 *
 * @param <VIEW> Type of view
 * @param <PRESENTER> Type of presenter
 */
public abstract class PresenterFragment<VIEW, PRESENTER extends Presenter<? super VIEW>> extends Fragment implements OnPresenterProvidedListener<PRESENTER> {

    private static final int DEFAULT_FRAGMENT_LOADER_ID = 0x66726167;
    private PRESENTER presenter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new LoaderBridge<PRESENTER>(getActivity(), getLoaderManager(), DEFAULT_FRAGMENT_LOADER_ID)
                .retrievePresenter(savedInstanceState, getPresenterFactory(), this);
    }

    public abstract TypeFactory<PRESENTER> getPresenterFactory();

    @Override
    public void onPresenterProvided(PRESENTER presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.bindView(getViewLayer());
    }

    public abstract VIEW getViewLayer();

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unbindView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Activity activity = getActivity();
        if (activity != null && activity.isFinishing()) {
            presenter.onDestroy();
        }
    }
}
