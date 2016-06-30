package de.androidbytes.mvp.activity;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;



public abstract class ButterknifeActivity extends AppCompatActivity {

	@Override
	@CallSuper
	protected void onCreate(@Nullable final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutResourceId());
	}

	protected abstract int getLayoutResourceId();

	@Override
	public final void setContentView(@LayoutRes final int layoutResID) {
		super.setContentView(layoutResID);
		bindViews();
	}

	@Override
	public void setContentView(final View view) {
		super.setContentView(view);
		bindViews();
	}

	@Override
	public void setContentView(final View view, final ViewGroup.LayoutParams params) {
		super.setContentView(view, params);
		bindViews();
	}

	private void bindViews() {
		ButterKnife.bind(this);
	}
}
