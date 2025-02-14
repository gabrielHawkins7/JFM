package com.d3fm.app;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.ActionListBox;
import com.googlecode.lanterna.gui2.LinearLayout;

class JFB{
    static ActionListBox fileBox;
    
    public static ActionListBox init(TerminalSize size){
        if(fileBox == null){
            fileBox = new ActionListBox();
            
        }
        
        reload(JFM.current_dir);
        return fileBox;
    }
    
    public static void reload(File directory){
        JFM.dir_label.setText(directory.getAbsolutePath());
        fileBox.clearItems();
        File[] flist = directory.listFiles();
        if(flist == null) 
        {
            return;
        }
        Arrays.sort(flist, Comparator.comparing(o -> o.getName().toLowerCase()));
        if(directory.getAbsoluteFile().getParentFile() != null)
        {
            fileBox.addItem("../" + directory.getAbsoluteFile().getParentFile().getName(), () -> {
                JFM.current_dir = directory.getAbsoluteFile().getParentFile();
                reload(JFM.current_dir); 
            });
        }else
        {
            fileBox.addItem("[---]", () -> {
            });
        }
        for(File f : flist)
        {
            if(f.isHidden() && !JFM.show_hidden)
            {
            }else
            {
                if(f.isDirectory())
                {
                    fileBox.addItem(f.getName() + "/", () -> {
                        JFM.current_dir = f.getAbsoluteFile();
                        reload(JFM.current_dir); 
                    });
                }else
                {
                    fileBox.addItem(f.getName(), () -> {
                        JFM.openFile(f);
                    });
                }
            }
        }
    }
}