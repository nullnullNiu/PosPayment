package com.lakala.pos.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class OnlyoneEditText  implements TextWatcher {

    private EditText editText;
    private int inputNum = 1;

    public OnlyoneEditText(EditText money_et) {
    }

    public void OnlyOneEditText(EditText et) {
        editText = et;
    }
    public OnlyoneEditText setInputNum(int d) {
        inputNum = d;
        return this;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().contains(".")) {
            //举例3.67  4-1-1=2>1  s=3.67
            if (s.length() - 1 - s.toString().indexOf(".") > inputNum) {
                //subSequence(0,3)   包括第0位不包括第3位  所以就是3.6
                s = s.toString().subSequence(0,
                        s.toString().indexOf(".") + inputNum +1);
                editText.setText(s);
                editText.setSelection(s.length()); //光标移到最后
            }
        }
        //如果"."在起始位置,则起始位置自动补0
        if (s.toString().trim().equals(".")) {
            s = "0" + s;
            editText.setText(s);
            editText.setSelection(2);
        }

        //如果起始位置为0,且第二位跟的不是".",则无法后续输入
        if (s.toString().startsWith("0")
                && s.toString().trim().length() > 1) {
            if (!s.toString().substring(1, 2).equals(".")) {
                editText.setText(s.subSequence(0, 1));
                editText.setSelection(1);
                return;
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

}
