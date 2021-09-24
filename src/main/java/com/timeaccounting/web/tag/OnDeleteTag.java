package com.timeaccounting.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class OnDeleteTag extends SimpleTagSupport {
    private int onDelete;

    public void setOnDelete(int onDelete) {
        this.onDelete = onDelete;
    }

    public void doTag() throws JspException, IOException {
        if (onDelete == 1) {
            getJspContext().getOut().write("Yes");
        }
        if (onDelete == 0) {
            getJspContext().getOut().write("Not");
        }
    }
}
