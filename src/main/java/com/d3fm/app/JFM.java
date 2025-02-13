package com.d3fm.app;

import java.io.IOException;

import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.terminal.*;
import com.googlecode.lanterna.bundle.LanternaThemes;
import com.googlecode.lanterna.gui2.*;



public class JFM{
    static Screen screen;
    static DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
    static Terminal term;
    static WindowBasedTextGUI gui;
    
    JFM() throws IOException{
        term = defaultTerminalFactory.createHeadlessTerminal();
        screen = new TerminalScreen(term);
        gui = new MultiWindowTextGUI(screen);
        gui.setTheme(LanternaThemes.getRegisteredTheme("businessmachine"));
        screen.startScreen();
        JFB jfbWin = new JFB("JFM", screen.getTerminalSize());
        gui.addWindowAndWait(jfbWin);
        
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