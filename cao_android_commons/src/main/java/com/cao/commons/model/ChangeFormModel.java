package com.cao.commons.model;

/**
 * 修改FormModel
 *
 * @author CJZ
 * @Time 2019/6/12
 */
public class ChangeFormModel {

    public int index;
    public FormModel formModel;

    public ChangeFormModel(int index, FormModel formModel) {
        this.index = index;
        this.formModel = formModel;
    }

    @Override
    public String toString() {
        return "ChangeFormModel{" +
                "index=" + index +
                ", formModel=" + formModel +
                '}';
    }
}
