package de.chennemann.libraries.mvp.activity;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import de.chennemann.libraries.mvp.ComponentApplication;


public abstract class ComponentActivity<COMPONENT> extends ButterknifeActivity {

	private COMPONENT injectionComponent;

	@Override
	@CallSuper
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handleComponentInjection();
	}

	private void handleComponentInjection() {
		final Application application = getApplication();
		if (application instanceof ComponentApplication) {
			injectionComponent = onCreateComponent();
			onComponentCreated();
		} else {
			throw new RuntimeException(application.getClass().getName() + " must extend from " + ComponentApplication.class.getSimpleName() + " to work with children of " + ComponentActivity.class.getSimpleName());
		}
	}

	protected final COMPONENT getComponent() {
		return injectionComponent;
	}

	protected abstract COMPONENT onCreateComponent();
	protected abstract void onComponentCreated();
}
