package com.d3fm.app;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.bundle.LanternaThemes;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.Button.DefaultButtonRenderer;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

/**
 * Hello world!
 */
public class App {
    
    static boolean show_hidden;
    static Screen screen;
    static DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
    static Terminal term;
    static Label dir_label;
    static File current_dir;
    static ActionListBox fileBox;
    
    
    public static void main(String[] args) throws IOException{
        current_dir = new File(System.getProperty("user.dir"));
        
        int test = pullin();
        JFM jfm_main = new JFM("nano");
        
        
        return;
        
    }
    
    static int pullin(){
        return WindowShadowRenderer.class.toString().length() +FatWindowDecorationRenderer.class.toString().length() +DefaultButtonRenderer.class.toString().length();
    }
        
}

