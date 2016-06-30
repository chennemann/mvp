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
import android.support.annotation.NonNull;
import android.support.v4.content.Loader;

import de.androidbytes.mvp.common.TypeFactory;

/**
 * Loader class responsible for retaining specific object between orientation changes
 *
 * @param <T> Type of object to retain
 */
public class RetainedObjectLoader<T> extends Loader<T> {

    private TypeFactory<T> typeFactory;

    private T objectToRetain;

    public RetainedObjectLoader(@NonNull Context context, @NonNull TypeFactory<T> typeFactory) {
        super(context);
        this.typeFactory = typeFactory;
    }

    /**
     * onStartLoading will be called automatically after loader will be asked for data
     * If object is currently loaded it will be returned with deliverResult() method otherwise
     * forceLoad() method will be called to create it for the first time
     */
    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        if (objectToRetain != null) {
            deliverResult(objectToRetain);
        } else {
            forceLoad();
        }
    }

    public T getRetainedObject() {
        return objectToRetain;
    }

    /**
     * Method will be called only if object to retain is not yet created, or loader was recreated
     * i.e. because of app process recreation
     */
    @Override
    public void forceLoad() {
        createObjectFromFactory();
        clearDataAfterCreation();

        deliverResult(objectToRetain);
    }

    private void createObjectFromFactory() {
        objectToRetain = typeFactory.create();
        onRetainedObjectCreated();
    }

    protected void onRetainedObjectCreated(){};

    private void clearDataAfterCreation() {
        typeFactory = null;
        onPostCreation();
    }

    protected void onPostCreation(){};
}
