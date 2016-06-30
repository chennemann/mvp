package de.androidbytes.mvp.activity;

import android.os.Bundle;
import android.support.annotation.CallSuper;



public abstract class ComponentActivity<C> extends ButterknifeActivity {

	private C injectionComponent;

	@Override
	@CallSuper
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handleComponentInjection();
	}

	private void handleComponentInjection() {
		injectionComponent = onCreateComponent();
		onComponentCreated();
	}

	protected final C getComponent() {
		return injectionComponent;
	}

	protected abstract C onCreateComponent();
	protected abstract void onComponentCreated();
}
