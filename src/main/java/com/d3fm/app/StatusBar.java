package com.d3fm.app;

import com.d3fm.app.JFM.OPEN_MODE;
import com.googlecode.lanterna.gui2.Label;

class StatusBar{
    static Label status_label;
    
    static Label init(){
        status_label = new Label("");
        status_update();
        return status_label;
    }
    
    static void status_update(){
        StringBuilder sb = new StringBuilder();
        sb.append("Hidden:<alt+h> Search:<alt+s> Mode:<alt+e>");
        sb.append(" Current Mode:" + ((JFM.open_mode == OPEN_MODE.EXE) ? "EXE":"TXT"));
        sb.append(" Hidden:" + JFM.show_hidden);
        status_label.setText(sb.toString());
    }
    
}

