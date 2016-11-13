package de.chennemann.libraries.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import de.chennemann.libraries.mvp.common.TypeFactory;
import de.chennemann.libraries.mvp.loader.LoaderBridge;
import de.chennemann.libraries.mvp.presenter.OnPresenterProvidedListener;
import de.chennemann.libraries.mvp.presenter.Presenter;

/**
 * Base MVP presenter retain with loader activity (AppCompatActivity)
 *
 * @param <COMPONENT> Type of DaggerComponent used to inject the PresenterFactory
 * @param <VIEW> Type of view
 * @param <PRESENTER> Type of presenter
 */
public abstract class PresenterActivity<COMPONENT extends ComponentActivity.ActivityComponent, VIEW, PRESENTER extends Presenter<VIEW>>
        extends ComponentActivity<COMPONENT>
        implements OnPresenterProvidedListener<PRESENTER> {

    private static final int DEFAULT_ACTIVITY_LOADER_ID = 0x61637469;
    private PRESENTER presenter;

    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new LoaderBridge<PRESENTER>(getApplicationContext(), getSupportLoaderManager(), DEFAULT_ACTIVITY_LOADER_ID)
                .retrievePresenter(savedInstanceState, getPresenterFactory(), this);
    }

    protected abstract TypeFactory<PRESENTER> getPresenterFactory();

    @Override
    @CallSuper
    public void onPresenterProvided(PRESENTER presenter) {
        this.presenter = presenter;
    }

    @Override
    @CallSuper
    public void onStart() {
        super.onStart();
	    presenter.bindView(getViewLayer());
	    presenter.onStart();
    }

	@Override
	@CallSuper
	public void onResume() {
		super.onResume();
		presenter.onResume();
	}

    public abstract VIEW getViewLayer();

    protected PRESENTER getPresenter() {
        return presenter;
    }

    @Override
    @CallSuper
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.onSaveInstanceState(outState);
    }

	@Override
	@CallSuper
	protected void onPause() {
		super.onPause();
		presenter.onPause();
	}

	@Override
    @CallSuper
    public void onStop() {
        super.onStop();
        presenter.onStop();
        presenter.unbindView();
    }

    @Override
    @CallSuper
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            presenter.onDestroy();
        }
    }

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		getPresenter().handleActivityResult(requestCode, resultCode, data);
	}
}
