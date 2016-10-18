package de.chennemann.libraries.mvp.presenter;

import android.content.Intent;


/**
 * @param <VIEW> Type of the view handled by presenter
 */
public interface Presenter<VIEW> extends LifecycleActions, ViewActions<VIEW> {
	void handleActivityResult(int requestCode, int resultCode, Intent data);
}
