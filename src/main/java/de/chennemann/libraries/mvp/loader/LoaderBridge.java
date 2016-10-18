package de.chennemann.libraries.mvp.loader;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import de.chennemann.libraries.mvp.presenter.OnPresenterProvidedListener;
import de.chennemann.libraries.mvp.presenter.Presenter;
import de.chennemann.libraries.mvp.common.TypeFactory;


/**
 * Bridge to make it easier to implement usage of RetainedPresenterLoader inside specific Fragment or Activity
 *
 * @param <PRESENTER> Type of presenter
 */
public class LoaderBridge<PRESENTER extends Presenter<?>> {

	private static final String TAG = LoaderBridge.class.getSimpleName();

	private int loaderId;
	private final Context context;
	private final LoaderManager loaderManager;

	private boolean debug = false;

	/**
	 * @param context  Current context (used later to create retain loader)
	 * @param manager  LoaderManager in which retain loader will be stored
	 * @param loaderId Identifier for retain loader inside LoaderManager
	 */
	public LoaderBridge(@NonNull Context context, @NonNull LoaderManager manager, int loaderId) {
		this.context = context;
		this.loaderId = loaderId;
		this.loaderManager = manager;
	}

	@CallSuper
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	@CallSuper
	public void retrievePresenter(
			@Nullable Bundle savedInstanceState,
			@NonNull final TypeFactory<PRESENTER> factory,
			@NonNull final OnPresenterProvidedListener<PRESENTER> presenterListener
	) {

		Loader<PRESENTER> loader = loaderManager.getLoader(loaderId);

		if (loader instanceof RetainedPresenterLoader) {
			retrievePresenterFromExistingLoaderAndInformListener(
					(RetainedPresenterLoader) loader,
					factory.getTypeClazz(),
					presenterListener
			);
		} else {
			loaderManager.initLoader(loaderId, savedInstanceState, new LoaderManager.LoaderCallbacks<PRESENTER>() {
				@Override
				public Loader<PRESENTER> onCreateLoader(int id, Bundle args) {
					if (args == null) {
						logIfDebugMode("Presenter loader need to be created : with empty bundle");
					} else {
						logIfDebugMode("Presenter loader need to be recreated : with filled bundle");
					}

					return new RetainedPresenterLoader<>(context, args, factory);
				}

				@Override
				public void onLoadFinished(Loader<PRESENTER> loader, PRESENTER presenter) {
					logIfDebugMode("Presenter retrieved after creation");
					presenterListener.onPresenterProvided(presenter);
				}

				@Override
				public void onLoaderReset(Loader<PRESENTER> loader) {
					// TODO Handle Loader Reset
					// empty not used implementation
				}
			});
		}
	}

	private void retrievePresenterFromExistingLoaderAndInformListener(
			@NonNull RetainedPresenterLoader loader,
			@NonNull Class<? extends PRESENTER> presenterClazz,
			@NonNull OnPresenterProvidedListener<PRESENTER> presenterListener
	) {

		PRESENTER presenter = retrievePresenterFromExistingLoader(loader, presenterClazz);
		if (presenter != null) {
			presenterListener.onPresenterProvided(presenter);
		} else {
			throw new IllegalStateException("Loader presenter not of expected type");
		}

		logIfDebugMode("Presenter retrieved from existing loader");
	}

	/**
	 * Method get retained Presenter object from RetainedPresenterLoader
	 *
	 * @param loader         Loader retaining presenter object
	 * @param presenterClazz Type of presenter we are expecting to get
	 *
	 * @return Presenter object retained in RetainedPresenterLoader or null if presenter is not retained or
	 * type of presenter is other than expected
	 */
	@Nullable
	private PRESENTER retrievePresenterFromExistingLoader(RetainedPresenterLoader loader, Class<? extends PRESENTER> presenterClazz) {
		Object presenter = loader.getPresenter();
		if (presenterClazz.isInstance(presenter)) {
			return presenterClazz.cast(presenter);
		} else {
			return null;
		}
	}

	private void logIfDebugMode(String message) {
		if (debug)
			Log.d(TAG, message);
	}
}
