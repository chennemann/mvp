package de.chennemann.libraries.mvp;

import android.app.Application;
import android.support.annotation.CallSuper;
import de.chennemann.libraries.mvp.common.ComponentHolder;



public abstract class ComponentApplication<COMPONENT extends ComponentApplication.ApplicationComponent>
		extends Application
		implements ComponentHolder<COMPONENT> {

	private COMPONENT applicationComponent;

	@Override
	@CallSuper
	public void onCreate() {
		super.onCreate();
		handleComponentInjection();
	}

	private void handleComponentInjection() {
		applicationComponent = onCreateComponent();
		onComponentCreated();
	}

	public final COMPONENT getComponent() {
		return applicationComponent;
	}

	public interface ApplicationComponent {
		void inject(Application application);
	}

}
