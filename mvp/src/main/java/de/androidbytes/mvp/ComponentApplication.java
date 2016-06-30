package de.androidbytes.mvp;

import android.app.Application;
import android.support.annotation.CallSuper;



public abstract class ComponentApplication<C> extends Application {

	private C applicationComponent;

	@Override
	@CallSuper
	public void onCreate() {
		super.onCreate();
		handleComponentInjection();
	}

	private void handleComponentInjection() {
		applicationComponent = onCreateComponent();
	}

	public final C getApplicationComponent() {
		return applicationComponent;
	}

	protected abstract C onCreateComponent();

}
