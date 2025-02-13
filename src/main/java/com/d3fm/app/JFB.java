package com.d3fm.app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.LinearLayout.GrowPolicy;
import com.googlecode.lanterna.gui2.TextBox.TextChangeListener;
import com.googlecode.lanterna.input.KeyStroke;

public class JFB extends BasicWindow{
    static boolean show_hidden;
    static File current_dir;
    static ActionListBox fileBox;
    
    static Panel searchPanel;
    static ActionListBox searchBox;
    private static RadioBoxList<OPEN_MODE> openModeBox;
    

    
    private static Panel main_panel;
    private static Label dir_label;
    private static TerminalSize term_size;
    
    private static enum OPEN_MODE{
        EXE, TXT
    };
    
    
    JFB(String name, TerminalSize size){
        super(name);
        this.setHints(Arrays.asList(Window.Hint.FULL_SCREEN, Window.Hint.CENTERED, Window.Hint.NO_DECORATIONS));
        term_size = size;
        init();
        
        this.addWindowListener(new WindowListener() {

			@Override
			public void onInput(Window basePane, KeyStroke keyStroke, AtomicBoolean deliverEvent) {
    			if(keyStroke.getCharacter() == Character.valueOf('h')){
                    show_hidden = (show_hidden)? false : true;
                    reload(current_dir);
                }
			}

			@Override
			public void onUnhandledInput(Window basePane, KeyStroke keyStroke, AtomicBoolean hasBeenHandled) {
				
			}

			@Override
			public void onResized(Window window, TerminalSize oldSize, TerminalSize newSize) {
				
			}

			@Override
			public void onMoved(Window window, TerminalPosition oldPosition, TerminalPosition newPosition) {
				
			}
            
        });
    }
    
    
    private void init(){
        current_dir = new File(System.getProperty("user.dir"));
        main_panel = new Panel(new LinearLayout(Direction.VERTICAL));
        dir_label = new Label(current_dir.getAbsolutePath())
        .setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center))
        .addTo(main_panel);
        Panel contentPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
        main_panel.addComponent(contentPanel, LinearLayout.createLayoutData(LinearLayout.Alignment.Fill));
        fileBox = getFileBox(new TerminalSize((int)(term_size.getColumns() * 0.7), term_size.getRows()));
        contentPanel.addComponent(fileBox.withBorder(Borders.singleLine()));
        
        Panel rightPanel = new Panel(new LinearLayout(Direction.VERTICAL));
        rightPanel.addComponent(new Label("Open Mode").setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center)));
        
        openModeBox = getOpenModeBox();
        openModeBox.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
        rightPanel.addComponent(openModeBox.withBorder(Borders.singleLine()));
        
        
        
        contentPanel.addComponent(rightPanel, LinearLayout.createLayoutData(LinearLayout.Alignment.Fill, GrowPolicy.CanGrow));
        
        
        this.setComponent(main_panel.withBorder(Borders.singleLine("JFM")));
    }
    
    
    
    
    
    
    
    private RadioBoxList<OPEN_MODE> getOpenModeBox(){
        openModeBox = new RadioBoxList<OPEN_MODE>();
        
        for(OPEN_MODE x : OPEN_MODE.values()){
            openModeBox.addItem(x);
        }
        
        openModeBox.setSelectedIndex(1);
        openModeBox.setCheckedItemIndex(1);
        
        return openModeBox;
    }
    
   
    
    
    private ActionListBox getFileBox(TerminalSize size){
        fileBox = new ActionListBox(size);
        
        reload(current_dir);
        return fileBox;
    }
    
    private static void reload(File directory){
        dir_label.setText(directory.getAbsolutePath());
        fileBox.clearItems();
        File[] flist = directory.listFiles();
        if(flist == null) {
                    return;
        }
        Arrays.sort(flist, Comparator.comparing(o -> o.getName().toLowerCase()));
        if(directory.getAbsoluteFile().getParentFile() != null){
            fileBox.addItem("../" + directory.getAbsoluteFile().getParentFile().getName(), () -> {
                current_dir = directory.getAbsoluteFile().getParentFile();
                reload(current_dir); 
            });
        }else{
            fileBox.addItem("[---]", () -> {
            });
        }
        for(File f : flist){
            if(f.isHidden() && !show_hidden){
            }else{
                if(f.isDirectory()){
                    fileBox.addItem(f.getName() + "/", () -> {
                       current_dir = f.getAbsoluteFile();
                       reload(current_dir); 
                    });
                }else{
                    fileBox.addItem(f.getName(), () -> {
                        if(openModeBox.getSelectedItem() == OPEN_MODE.EXE){
                            if(!f.canExecute()){
                                return;
                            }
                            try {
                                    
    							JFM.runProgram(f.getAbsolutePath());
    						} catch (IOException e) {
    							// TODO Auto-generated catch block
    							e.printStackTrace();
    						}
                        }else{
                            try {
                                JFM.openFileIn("nano",f.getAbsolutePath());
    						} catch (IOException e) {
    							// TODO Auto-generated catch block
    							e.printStackTrace();
    						}
                        }
                        
                    });
                }
            }
        }
    }
    
    
    
    
    
}