package com.cao.commons.model;

import java.io.Serializable;

/**
 * 表单Model
 *
 * @author CJZ
 * @Time 2019/6/11
 */
public class FormModel implements Serializable {

    public String title;
    public boolean isRequired;


    public FormModel() {
    }

    public FormModel(String title, boolean isRequired) {
        this.title = title;
        this.isRequired = isRequired;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FormModel) {
            FormModel formModel = (FormModel) obj;
            return this.title.equals(formModel.title)
                    && this.isRequired == formModel.isRequired;
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "FormModel{" +
                "title='" + title + '\'' +
                ", isRequired=" + isRequired +
                '}';
    }
}
