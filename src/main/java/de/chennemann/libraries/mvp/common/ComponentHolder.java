/**
 * Christoph Hennemann
 *
 * 12.11.2016, Version 1.0
 */
package de.chennemann.libraries.mvp.common;

import android.support.annotation.NonNull;



public interface ComponentHolder<COMPONENT> {

	@NonNull
	COMPONENT onCreateComponent();
	void onComponentCreated();
	COMPONENT getComponent();

}
