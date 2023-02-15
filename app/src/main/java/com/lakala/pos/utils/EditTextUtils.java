package com.lakala.pos.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class EditTextUtils {

    public static void setPricePoint(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 1) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 2);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }
                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                judgeNumber(s,editText);
            }
        });
    }

    /**
     * 金额输入框中的内容限制（最大：小数点前五位，小数点后2位）
     *
     * @param edt
     */
    public static void judgeNumber(Editable edt,EditText editText) {

        String temp = edt.toString();
        int posDot = temp.indexOf(".");//返回指定字符在此字符串中第一次出现处的索引
        int index = editText.getSelectionStart();//获取光标位置
//          if (posDot == 0) {//必须先输入数字后才能输入小数点
//          edt.delete(0, temp.length());//删除所有字符
//          return;
//          }
        if (posDot < 0) {//不包含小数点
            if (temp.length() <= 9) {
                return;//小于五位数直接返回
            } else {
                LogUtil.i("index " +index);
                edt.delete(index-1, index);//删除光标前的字符
                return;
            }
        }
        LogUtil.i(index+" ================");
        if (posDot > 9) {//小数点前大于9位数就删除光标前一位
            edt.delete(index-1, index);//删除光标前的字符
            return;
        }
        if (temp.length() - posDot - 1 > 2)//如果包含小数点
        {
            edt.delete(index-1, index);//删除光标前的字符
            return;
        }
    }


    /**
     * 设置edittext只能输入小数点后两位
     */
    public static void afterDotTwo(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // 限制最多能输入9位整数
                if (s.toString().contains(".")) {
                    if (s.toString().indexOf(".") > 9) {
                        s = s.toString().subSequence(0,9) + s.toString().substring(s.toString().indexOf("."));
                        editText.setText(s);
                        editText.setSelection(9);
                    }
                }else {
                    if (s.toString().length() > 9){
                        s = s.toString().subSequence(0,9);
                        editText.setText(s);
                        editText.setSelection(9);
                    }
                }
                // 判断小数点后只能输入两位
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                //如果第一个数字为0，第二个不为点，就不允许输入
                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if (editText.getText().toString().trim() != null && !editText.getText().toString().trim().equals("")) {
                    if (editText.getText().toString().trim().substring(0, 1).equals(".")) {
                        editText.setText("0" + editText.getText().toString().trim());
                        editText.setSelection(1);
                    }
                }
            }
        });
    }
}
