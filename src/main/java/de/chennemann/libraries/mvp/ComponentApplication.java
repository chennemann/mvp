package de.chennemann.libraries.mvp;

import android.app.Application;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;


public abstract class ComponentApplication<COMPONENT> extends Application {

	private COMPONENT applicationComponent;

	@Override
	@CallSuper
	public void onCreate() {
		super.onCreate();
		handleComponentInjection();
	}

	private void handleComponentInjection() {
		applicationComponent = onCreateComponent();
	}

	public final COMPONENT getApplicationComponent() {
		return applicationComponent;
	}

	@NonNull
	protected abstract COMPONENT onCreateComponent();

}
