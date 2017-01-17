package com.haoshi.rxjava.example4.support.delegate.view;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.SparseArrayCompat;

import com.haoshi.rxjava.example4.base.presenter.MvpPresenter;
import com.haoshi.rxjava.example4.base.view.MvpView;

/**
 * @author: HaoShi
 * 
 * 通过Fragment保存数据（而且这个Fragment是一个静态内部类
 */
public class OrientationChangeManager<V extends MvpView, P extends MvpPresenter<V>> {

	static final String FRAGMENT_TAG = "com.hannesdorfmann.mosby.mvp.OrientationChangeFragment";
	
	private OrientationChangeFragment internalFragment = null;
	
	public int nextViewId(Context context) {
		return getFragment(context).nextViewId();
	}

	private FragmentActivity getActivity(Context context) {
		while (context instanceof ContextWrapper) {
			if (context instanceof FragmentActivity) {
				return (FragmentActivity) context;
			}
			context = ((ContextWrapper) context).getBaseContext();
		}
		throw new IllegalStateException(
				"Could not find the surrounding FragmentActivity. Does your activity extends from android.support.v4.app.FragmentActivity like android.support.v7.app.AppCompatActivity ?");
	}
	
	@UiThread
	private OrientationChangeFragment getFragment(Context context) {

		if (internalFragment != null) {
			return internalFragment;
		}

		FragmentActivity activity = getActivity(context);

		OrientationChangeFragment fragment = (OrientationChangeFragment) activity
				.getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);

		// Already existing Fragment found
		if (fragment != null) {
			this.internalFragment = fragment;
			return fragment;
		}

		// No internalFragment found, so create a new one
		this.internalFragment = new OrientationChangeFragment();
		activity.getSupportFragmentManager().beginTransaction()
				.add(internalFragment, FRAGMENT_TAG).commit();

		return this.internalFragment;
	}
	
	@UiThread
	public P getPresenter(int id, @NonNull Context context) {

		OrientationChangeFragment fragment = getFragment(context);

		CacheEntry<V, P> entry = fragment.get(id);
		return entry == null ? null : entry.presenter;
	}
	
	@UiThread
	public <T> T getViewState(int id, @NonNull Context context) {

		OrientationChangeFragment fragment = getFragment(context);

		CacheEntry<V, P> entry = fragment.get(id);
		return entry == null ? null : (T) entry.viewState;
	}
	
	@UiThread
	public void cleanUp() {
		internalFragment = null;
	}
	
	public boolean willViewBeDestroyedPermanently(Context context) {
		OrientationChangeFragment fragment = getFragment(context);
		return fragment.destroyed;
	}
	
	public boolean willViewBeDetachedBecauseOrientationChange(Context context) {
		OrientationChangeFragment fragment = getFragment(context);
		return fragment.stopped;
	}
	
	public void removePresenterAndViewState(int viewId, Context context) {
		OrientationChangeFragment fragment = getFragment(context);
		fragment.remove(viewId);
	}
	
	public void putPresenter(int viewId, P presenter, Context context) {
		OrientationChangeFragment fragment = getFragment(context);
		CacheEntry<V, P> entry = fragment.get(viewId);
		if (entry == null) {
			entry = new CacheEntry<V, P>(presenter);
			fragment.put(viewId, entry);
		} else {
			entry.presenter = presenter;
		}
	}
	
	public void putViewState(int viewId, Object viewState, Context context) {
		OrientationChangeFragment fragment = getFragment(context);
		CacheEntry<V, P> entry = fragment.get(viewId);
		if (entry == null) {
			throw new IllegalStateException(
					"Try to put the ViewState into cache. However, the presenter hasn't been put into cache before. This is not allowed. Ensure that the presenter is saved before putting the ViewState into cache.");
		} else {
			entry.viewState = viewState;
		}
	}
	
	static final class CacheEntry<V extends MvpView, P extends MvpPresenter<V>> {
		P presenter;
		Object viewState; // workaround: dont want to introduce dependency to
							// viewstate module

		public CacheEntry(P presenter) {
			this.presenter = presenter;
		}
	}
	
	public static final class OrientationChangeFragment extends Fragment {

		// 0 is preserverd as "no view id"
		private int VIEW_ID = 0;
		//缓存数据的集合
		private SparseArrayCompat<CacheEntry> cache = new SparseArrayCompat<CacheEntry>();
		private boolean stopped = true;
		private boolean destroyed = false;
		
		<T> T get(int key) {
			return (T) cache.get(key);
		}
		
		void put(int key, CacheEntry entry) {
			cache.put(key, entry);
		}
		
		void remove(int key) {
			cache.remove(key);
		}

		@Override
		public void onCreate(@Nullable Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setRetainInstance(true);
		}

		@Override
		public void onDestroy() {
			destroyed = true;
			cache.clear();
			cache = null;

			super.onDestroy();
		}

		@Override
		public void onStart() {
			super.onStart();
			stopped = false;
		}

		@Override
		public void onStop() {
			super.onStop();
			stopped = true;
		}
		
		int nextViewId() {
			while (cache.get(++VIEW_ID) != null) {
				if (VIEW_ID == Integer.MAX_VALUE) {
					throw new IllegalStateException(
							"Oops, it seems that we ran out of (mosby internal) view id's. It seems that your user has navigated more than "
									+ Integer.MAX_VALUE
									+ " times through your app. There is nothing you can do to fix that");
				}
			}
			return VIEW_ID;
		}
	}
}
