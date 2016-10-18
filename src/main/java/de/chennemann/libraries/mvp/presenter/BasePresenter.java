package de.chennemann.libraries.mvp.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;



/**
 * Most common base representation of presenter class
 * with callback actions and MVP view layer support
 *
 * @param <VIEW> Type of the view
 */
public abstract class BasePresenter<VIEW> implements Presenter<VIEW> {

    private VIEW view;

    /**
     * Called when presenter and it's component (Activity/Fragment) is going to be removed from memory
     * This is time when state should be saved if we want to handle activity/process kill.
     * This will not be called if activity is recreated because of configuration change.
     *
     * @param bundle Bundle object to which we could save our presenter state
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {

    }

    /**
     * Called when presenter is created.
     * This will not be called if activity is recreated because of configuration change.
     *
     * @param bundle Bundle with saved state. Could be null when presenter is created for the first time.
     *               It will be filled with state data if presenter is recreated after activity/process kill
     */
    @Override
    @CallSuper
    public void onCreate(@Nullable Bundle bundle) {
        if (bundle == null)
            onConstruct();
    }

    @Override
    public void onConstruct() {

    }

    @Override
    public void onStart() {

    }

	@Override
    public void onResume() {

    }

	@Override
    public void onPause() {

    }

	@Override
    public void onStop() {

    }

    /**
     * Called when component Activity is being removed from the memory (it's finishing, i.e. because of back button
     * press action)
     */
    @Override
    public void onDestroy() {

    }

    /**
     * Called when view handled by this presenter is available.
     * It will be called no later than Activity/Fragment onStart() method call.
     *
     * @param view Object representing MVP view layer
     */
    @Override
    @CallSuper
    public void bindView(VIEW view) {
        this.view = view;
        onViewBound();
    }

    protected void onViewBound(){};

    /**
     * Called when view is being unbind from presenter component.
     * It will be called no later than Activity/Fragment onStop() method call.
     */
    @Override
    @CallSuper
    public void unbindView() {
        this.view = null;
        onViewUnbound();
    }

    protected void onViewUnbound(){};

    @Override
    @CallSuper
    public VIEW getView() {
        return view;
    }

    @Override
    public void handleActivityResult(final int requestCode, final int resultCode, final Intent data) {

    }
}
