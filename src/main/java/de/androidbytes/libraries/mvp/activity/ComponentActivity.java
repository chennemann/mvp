package de.androidbytes.libraries.mvp.activity;

import android.os.Bundle;
import android.support.annotation.CallSuper;



public abstract class ComponentActivity<COMPONENT> extends ButterknifeActivity {

	private COMPONENT injectionComponent;

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

	protected final COMPONENT getComponent() {
		return injectionComponent;
	}

	protected abstract COMPONENT onCreateComponent();
	protected abstract void onComponentCreated();
}
