package com.d3fm.app;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.terminal.*;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.bundle.LanternaThemes;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.LinearLayout.GrowPolicy;



public class JFM{
    static Screen screen;
    static DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
    static Terminal term;
    static TerminalSize size;
    static WindowBasedTextGUI gui;
    static Window window;
    static Panel main_panel;
    static File current_dir;
    static boolean show_hidden; 
    static Label dir_label;   
    static Panel contnet_panel;
    static Panel right_panel;
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

        contnet_panel= new Panel(new LinearLayout(Direction.HORIZONTAL));
        main_panel.addComponent(contnet_panel, LinearLayout.createLayoutData(LinearLayout.Alignment.Fill));
        
        initBrowerBox();
        
        
        right_panel = new Panel(new LinearLayout(Direction.VERTICAL));
        contnet_panel.addComponent(
                    right_panel, 
                    LinearLayout.createLayoutData(LinearLayout.Alignment.Fill, GrowPolicy.CanGrow));
        right_panel.addComponent(
                    new Label("Open Mode")
                    .setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center)));
        right_panel.addComponent(JFModeBox.
                    getOpenModeBox().
                    setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center))
                    .withBorder(Borders.singleLine()));
        
        right_panel.addComponent(JFSearchBox
                    .getSearchBoxPanel()
                    .withBorder(Borders.singleLine()), 
                    LinearLayout.createLayoutData(LinearLayout.Alignment.Fill, GrowPolicy.CanGrow));
        
        window.setComponent(main_panel.withBorder(Borders.singleLine("JFM")));
        //JFB jfbWin = new JFB("JFM", screen.getTerminalSize());
        gui.addWindowAndWait(window);
        
    }
    
    public static void global_reload(File dir){
        JFB_2.reload(dir);
        JFSearchBox.reload_search(dir);
    }
    
    private static void initBrowerBox(){
        //fileBox = getFileBox(new TerminalSize((int)(term_size.getColumns() * 0.7), term_size.getRows()));
        contnet_panel.addComponent(
                    JFB_2.init(new TerminalSize((int)(size.getColumns() * .7), size.getRows()))
                    .withBorder(Borders.singleLine())
        );
    }
    
    public static void openFile(File f){
        if(!f.canExecute()) return;
        try
        {
            if(open_mode == OPEN_MODE.EXE)
            {
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