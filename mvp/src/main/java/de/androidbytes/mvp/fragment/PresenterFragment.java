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
package de.androidbytes.mvp.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import de.androidbytes.mvp.common.TypeFactory;
import de.androidbytes.mvp.loader.LoaderBridge;
import de.androidbytes.mvp.presenter.OnPresenterProvidedListener;
import de.androidbytes.mvp.presenter.Presenter;

/**
 * Base MVP presenter retain with loader fragment (android.support.v4.app.Fragment)
 *
 * @param <V> Type of view
 * @param <P> Type of presenter
 */
public abstract class PresenterFragment<V, P extends Presenter<? super V>> extends Fragment implements OnPresenterProvidedListener<P> {

    private static final int DEFAULT_FRAGMENT_LOADER_ID = 0x66726167;
    private P presenter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new LoaderBridge<P>(getActivity(), getLoaderManager(), DEFAULT_FRAGMENT_LOADER_ID)
                .retrievePresenter(savedInstanceState, getPresenterFactory(), this);
    }

    public abstract TypeFactory<P> getPresenterFactory();

    @Override
    public void onPresenterProvided(P presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.bindView(getViewLayer());
    }

    public abstract V getViewLayer();

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unbindView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Activity activity = getActivity();
        if (activity != null && activity.isFinishing()) {
            presenter.onDestroy();
        }
    }
}
