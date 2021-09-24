package com.timeaccounting.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class UserActivityStateTag extends SimpleTagSupport {
    private int state;

    public void setState(int state) {
        this.state = state;
    }

    public void doTag() throws JspException, IOException {
        if (state == 1) {
            getJspContext().getOut().write("active");
        }
        if (state == 0) {
            getJspContext().getOut().write("inactive");
        }
    }
}
