package com.d3fm.app;

import com.d3fm.app.JFM.OPEN_MODE;
import com.googlecode.lanterna.gui2.RadioBoxList;
import com.googlecode.lanterna.gui2.RadioBoxList.Listener;

class JFModeBox{
    private static RadioBoxList<JFM.OPEN_MODE> openModeBox;
    
    public static RadioBoxList<JFM.OPEN_MODE> getOpenModeBox(){
        openModeBox = new RadioBoxList<JFM.OPEN_MODE>();
        for(JFM.OPEN_MODE x : JFM.OPEN_MODE.values()){
            openModeBox.addItem(x);
        }
        openModeBox.setSelectedIndex(1);
        openModeBox.setCheckedItemIndex(1);
        openModeBox.addListener(new Listener() {
			@Override
			public void onSelectionChanged(int selectedIndex, int previousSelection) {
				if(JFM.open_mode == OPEN_MODE.EXE) JFM.open_mode = OPEN_MODE.TXT;
				else JFM.open_mode = OPEN_MODE.EXE;
			}
            
        });
        return openModeBox;
    }
}