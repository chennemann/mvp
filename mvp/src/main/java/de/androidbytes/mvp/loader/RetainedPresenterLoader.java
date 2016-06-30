/*
 * Copyright (C) 2016 Michał Łuszczuk.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.androidbytes.mvp.loader;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import de.androidbytes.mvp.common.TypeFactory;
import de.androidbytes.mvp.presenter.LifecycleActions;

/**
 * Loader class responsible to retain presenter object
 * along with support of providing initial state in a Bundle data
 *
 * @param <P> Type of presenter to retain
 */
public class RetainedPresenterLoader<P extends LifecycleActions> extends RetainedObjectLoader<P> {

    private Bundle savedStateBundle;

    public RetainedPresenterLoader(
            @NonNull Context context,
            @Nullable Bundle bundle,
            @NonNull TypeFactory<P> presenterFactory) {
        super(context, presenterFactory);

        this.savedStateBundle = bundle;
    }

    public P getPresenter() {
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
