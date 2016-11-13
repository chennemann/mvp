package de.chennemann.libraries.mvp.activity;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import de.chennemann.libraries.mvp.ComponentApplication;
import de.chennemann.libraries.mvp.common.ComponentHolder;



public abstract class ComponentActivity<COMPONENT extends ComponentActivity.ActivityComponent>
		extends ButterknifeActivity
		implements ComponentHolder<COMPONENT> {

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

	public final COMPONENT getComponent() {
		return injectionComponent;
	}

	public interface ActivityComponent {
		void inject(ComponentActivity componentActivity);
	}
}
