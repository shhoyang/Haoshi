package com.haoshi.rxjava.example4.support.delegate.view;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/**
 * @author: HaoShi
 */
public class MvpSavedState extends View.BaseSavedState {

	public static final Creator<MvpSavedState> CREATOR = new Creator<MvpSavedState>() {
		public MvpSavedState createFromParcel(Parcel in) {
			return new MvpSavedState(in);
		}

		public MvpSavedState[] newArray(int size) {
			return new MvpSavedState[size];
		}
	};

	//我要保存的数据
	private int mvpbyViewId = 0;
	
	public MvpSavedState(Parcelable superState) {
		super(superState);
	}

	protected MvpSavedState(Parcel in) {
		super(in);
		this.mvpbyViewId = in.readInt();
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		super.writeToParcel(out, flags);
		out.writeInt(mvpbyViewId);
	}

	public int getMosbyViewId() {
		return mvpbyViewId;
	}

	public void setMosbyViewId(int mosbyViewId) {
		this.mvpbyViewId = mosbyViewId;
	}

}
