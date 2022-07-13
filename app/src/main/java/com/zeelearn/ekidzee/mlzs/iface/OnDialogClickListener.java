package com.zeelearn.ekidzee.mlzs.iface;

import java.io.Serializable;

public interface OnDialogClickListener extends Serializable {

    public void onOkClicked(Object object);
    public void onCancelClicked();
}
