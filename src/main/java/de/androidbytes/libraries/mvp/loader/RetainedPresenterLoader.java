package de.androidbytes.libraries.mvp.loader;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import de.androidbytes.libraries.mvp.common.TypeFactory;
import de.androidbytes.libraries.mvp.presenter.LifecycleActions;

/**
 * Loader class responsible to retain presenter object
 * along with support of providing initial state in a Bundle data
 *
 * @param <LIFECYCLE_ACTIONS> Type of presenter to retain
 */
public class RetainedPresenterLoader<LIFECYCLE_ACTIONS extends LifecycleActions> extends RetainedObjectLoader<LIFECYCLE_ACTIONS> {

    private Bundle savedStateBundle;

    public RetainedPresenterLoader(
            @NonNull Context context,
            @Nullable Bundle bundle,
            @NonNull TypeFactory<LIFECYCLE_ACTIONS> presenterFactory) {
        super(context, presenterFactory);

        this.savedStateBundle = bundle;
    }

    public LIFECYCLE_ACTIONS getPresenter() {
        return getRetainedObject();
    }

    @Override
    protected void onRetainedObjectCreated() {
        getPresenter().onCreate(savedStateBundle);
    }

    @Override
    protected void onPostCreation() {
        savedStateBundle = null;
    }
}
