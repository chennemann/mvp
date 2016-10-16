package de.androidbytes.libraries.mvp.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface LifecycleActions {

	void onSaveInstanceState(@NonNull Bundle bundle);

    void onCreate(@Nullable Bundle bundle);

    void onConstruct();

    void onStart();

    void onResume();

	void onPause();

    void onStop();

    void onDestroy();
}
