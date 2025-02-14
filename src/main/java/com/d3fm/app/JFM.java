package com.d3fm.app;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.terminal.*;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.bundle.LanternaThemes;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.LinearLayout.GrowPolicy;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;



public class JFM{
    static Screen screen;
    static DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
    static Terminal term;
    static TerminalSize size;
    static WindowBasedTextGUI gui;
    static Window window;
    static Panel main_panel;
    static Panel bottom_panel;
    static File current_dir;
    static boolean show_hidden; 
    static Label dir_label;   
   
    static OPEN_MODE open_mode;
    static enum OPEN_MODE{
        EXE,TXT
    };
    static String prog;
    
    
    JFM(String prog) throws IOException{
        JFM.prog = prog;
        current_dir = new File(System.getProperty("user.dir"));
        term = defaultTerminalFactory.createHeadlessTerminal();
        screen = new TerminalScreen(term);
        size = screen.getTerminalSize();
        gui = new MultiWindowTextGUI(screen);
        gui.setTheme(LanternaThemes.getRegisteredTheme("businessmachine"));
        screen.startScreen();
        window = new BasicWindow("JFM");
        window.setHints(Arrays.asList(Window.Hint.FULL_SCREEN, Window.Hint.CENTERED, Window.Hint.NO_DECORATIONS));
        main_panel = new Panel(new LinearLayout(Direction.VERTICAL));
        
        dir_label = new Label(current_dir.getAbsolutePath())
                    .setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center))
                    .addTo(main_panel);
        
        main_panel.addComponent(new Separator(Direction.HORIZONTAL)
                    .setLayoutData(LinearLayout
                    .createLayoutData(LinearLayout.Alignment.Fill)));

        
        main_panel.addComponent(
                    JFB.init(size), 
                    LinearLayout.createLayoutData(LinearLayout.Alignment.Fill, GrowPolicy.CanGrow)
        );
       
        bottom_panel = new Panel(new LinearLayout(Direction.HORIZONTAL));
        main_panel.addComponent(
                    bottom_panel.withBorder(Borders.singleLine()),
                    LinearLayout.createLayoutData(LinearLayout.Alignment.Fill)
        );
        
        bottom_panel.addComponent(
                    StatusBar.init(),
                    LinearLayout.createLayoutData(LinearLayout.Alignment.Center, GrowPolicy.CanGrow)
        );
        
        
        window.setComponent(main_panel.withBorder(Borders.singleLine("JFM")));
        
        initKeyCommands();
        
        
        gui.addWindowAndWait(window);
        
    }
    
    private void initKeyCommands(){
        window.addWindowListener(new WindowListener() {

			@Override
			public void onInput(Window basePane, KeyStroke keyStroke, AtomicBoolean deliverEvent) {
			         if(keyStroke.getCharacter() == Character.valueOf('h') && keyStroke.isAltDown()){
                        show_hidden = (show_hidden)? false : true;
                        JFB.reload(current_dir);
                        StatusBar.status_update();
                    }
                    if(keyStroke.getKeyType() == KeyType.ArrowUp && keyStroke.isAltDown()){
                        JFB.reload(current_dir);
                        StatusBar.status_update();
                    }
                    if(keyStroke.getCharacter() == Character.valueOf('e') && keyStroke.isAltDown()){
                        open_mode = (open_mode == OPEN_MODE.EXE)? OPEN_MODE.TXT : OPEN_MODE.EXE;
                        StatusBar.status_update();
                    }
                    if(keyStroke.getCharacter() == Character.valueOf('s') && keyStroke.isAltDown()){
                        Window search = new BasicWindow("Search");
                        search.setHints(Arrays.asList(Window.Hint.CENTERED));
                        search.setComponent(JFSearchBox.getSearchBoxPanel());
                        search.addWindowListener(new WindowListener() {
							@Override
							public void onInput(Window basePane, KeyStroke keyStroke, AtomicBoolean deliverEvent) {
								if(keyStroke.getKeyType() == KeyType.Escape){
								    search.close();
								}
								 if(keyStroke.getCharacter() == Character.valueOf('s') && keyStroke.isAltDown()){
									   search.close();
								}
							}
							@Override
							public void onUnhandledInput(Window basePane, KeyStroke keyStroke,
									AtomicBoolean hasBeenHandled) {
								return;
							}
							@Override
							public void onResized(Window window, TerminalSize oldSize, TerminalSize newSize) {
							 return;
							}
							@Override
							public void onMoved(Window window, TerminalPosition oldPosition,
									TerminalPosition newPosition) {
									return;
							}
                        });
                        gui.addWindowAndWait(search);
                    }
			}

			@Override
			public void onUnhandledInput(Window basePane, KeyStroke keyStroke, AtomicBoolean hasBeenHandled) {return;}

			@Override
			public void onResized(Window window, TerminalSize oldSize, TerminalSize newSize) {
			     JFB.fileBox.setSize(new TerminalSize((int)(newSize.getColumns() * .7), newSize.getRows()));
			}

			@Override
			public void onMoved(Window window, TerminalPosition oldPosition, TerminalPosition newPosition) {return;}
            
        });
    }
    
    public static void global_reload(File dir){
        JFB.reload(dir);
        JFSearchBox.reload_search(dir);
        StatusBar.status_update();
    }
    
    
    
    public static void openFile(File f){
        
        try
        {
            if(open_mode == OPEN_MODE.EXE)
            {
                if(!f.canExecute()) return;
                runProgram(f.getAbsolutePath());
            }else
            {
                openFileIn(prog, f.getAbsolutePath());
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    public static void openFileIn(String prog, String fn) throws IOException{
        screen.clear();
        screen.stopScreen();
		ProcessBuilder pb = new ProcessBuilder(prog, fn);
        Process process = pb.inheritIO().start();
        while(process.isAlive()){
            
        }
        screen.startScreen();
    }
    public static void runProgram(String fn) throws IOException{
        screen.clear();
        screen.stopScreen();
		ProcessBuilder pb = new ProcessBuilder(fn);
        Process process = pb.inheritIO().start();
        while(process.isAlive()){
            
        }
        screen.startScreen();
    }
}