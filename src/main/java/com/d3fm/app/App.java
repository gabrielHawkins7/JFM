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
        // term = defaultTerminalFactory.createHeadlessTerminal();
        // screen = new TerminalScreen(term);
        
        // WindowBasedTextGUI gui = new MultiWindowTextGUI(screen);
        // gui.setTheme(LanternaThemes.getRegisteredTheme("businessmachine"));
        // screen.startScreen();
        // Window window = new BasicWindow("JFM");
        // TerminalSize size  = screen.getTerminalSize();
        

        
        // Panel layoutPanel = new Panel(new LinearLayout(Direction.VERTICAL));
        // dir_label = new Label(current_dir.getAbsolutePath())
        // .setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center))
        // .addTo(layoutPanel);
        // Panel contentPanel = new Panel(new GridLayout(2));
        // layoutPanel.addComponent(contentPanel.withBorder(Borders.singleLine()));
        
        // fileBox = new ActionListBox();
        
        // contentPanel.addComponent(fileBox);
        
        
        // window.setHints(Arrays.asList(Window.Hint.FULL_SCREEN, Window.Hint.CENTERED, Window.Hint.NO_DECORATIONS));
        // window.setComponent(layoutPanel.withBorder(Borders.singleLine("JFM")));
        
        // reload(current_dir);
        // gui.addWindowAndWait(window);
        // screen.stopScreen();
        // 
        // 
        int test = pullin();
        JFM jfm_main = new JFM("nano");
        
        
        return;
        
    }
    
    static int pullin(){
        return WindowShadowRenderer.class.toString().length() +FatWindowDecorationRenderer.class.toString().length() +DefaultButtonRenderer.class.toString().length();
    }
    
    
    static void runProgramOn(String prog, String fn) throws IOException{
        screen.stopScreen();
		ProcessBuilder pb = new ProcessBuilder(prog, fn);
        Process process = pb.inheritIO().start();
        while(process.isAlive()){
            
        }
        screen.startScreen();
    }
    
    public static void reload(File directory){
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
                        try {
							runProgramOn("nano", f.getAbsolutePath());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    });
                }
            }
        }
    }
    
    // ProcessBuilder pb = new ProcessBuilder("nano", "");
    // Process process = pb.inheritIO().start();
    // while(process.isAlive()){
        
    // }
    
    
    
    // public static void bmain (String[] args) {
    //     System.out.println("Hello World!");
        
    //     dir = new File(System.getProperty("user.home")).getAbsoluteFile();
        
        
    //     try{
            
    //         screen = defaultTerminalFactory.createScreen();
            
            
    //         screen.startScreen();
    //         WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
    //         TerminalSize size = screen.getTerminalSize();
    //         Window window = new BasicWindow("JFM");
    //         Panel layoutPanel = new Panel(new LinearLayout(Direction.VERTICAL));
    //         nLabel = new Label(dir.getAbsolutePath())
    //         .setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center))
    //         .addTo(layoutPanel);
    //         Panel contentPanel = new Panel(new GridLayout(2));
    //         layoutPanel.addComponent(contentPanel.withBorder(Borders.singleLine()));
    //         fileBox = new ActionListBox(new TerminalSize(size.getColumns() / 2, size.getRows()));
    //         reload(dir);
    //         contentPanel.addComponent(fileBox);
    //         window.setHints(Arrays.asList(Window.Hint.FULL_SCREEN, Window.Hint.CENTERED, Window.Hint.NO_DECORATIONS));
    //         window.setComponent(layoutPanel.withBorder(Borders.singleLine("JFM")));
            
            
    //         window.addWindowListener(new WindowListener() {
    //             @Override
    //             public void onInput(Window basePane, KeyStroke keyStroke, AtomicBoolean deliverEvent) {
    //                 if(keyStroke.getCharacter() == Character.valueOf('h')){
    //                     show_hidden = (show_hidden)? false : true;
    //                     reload(dir);
    //                 }
    //             }

				// @Override
				// public void onUnhandledInput(Window basePane, KeyStroke keyStroke, AtomicBoolean hasBeenHandled) {
				//     return;
				// }

				// @Override
				// public void onResized(Window window, TerminalSize oldSize, TerminalSize newSize) {
				//     return;
				// }

				// @Override
				// public void onMoved(Window window, TerminalPosition oldPosition, TerminalPosition newPosition) {
				//     return;
				// }
    //         });
    //         textGUI.addWindowAndWait(window);

            
            
            
            
    //     }catch(Exception e){
    //         e.printStackTrace();
    //     }finally{
    //         if(screen != null) {
    //                 try {
    //                     screen.stopScreen();
    //                 }catch(IOException e){
    //                     e.printStackTrace();
    //                 }
    //         }
    //     }
            
    // }
    
    
    // public static void reload(File directory){
    //     nLabel.setText(directory.getAbsolutePath());
    //     fileBox.clearItems();
    //     File[] flist = directory.listFiles();
    //     if(flist == null) {
    //                 return;
    //     }
    //     Arrays.sort(flist, Comparator.comparing(o -> o.getName().toLowerCase()));
        
    //     if(directory.getAbsoluteFile().getParentFile() != null){
    //         fileBox.addItem("../" + directory.getAbsoluteFile().getParentFile().getName(), () -> {
    //             dir = directory.getAbsoluteFile().getParentFile();
    //             reload(dir); 
    //         });
    //     }else{
    //         fileBox.addItem("[---]", () -> {
                 
    //         });
    //     }
        
        // for(File f : flist){
            
        //     if(f.isHidden() && !show_hidden){
                
        //     }else{
        //         if(f.isDirectory()){
        //             fileBox.addItem(f.getName() + "/", () -> {
        //                dir = f.getAbsoluteFile();
        //                reload(dir); 
        //             });
        //         }else{
        //             fileBox.addItem(f.getName(), () -> {
    //                     try {
    //                         screen.stopScreen();
                
    //                         // Run nano inside the same terminal session
    //                         Process nanoProcess = new ProcessBuilder("/bin/zsh"," ls ", f.getParentFile().getAbsolutePath())
    //                                 .inheritIO()  // This allows nano to take control of the terminal
    //                                 .start();
                
    //                         // Wait for nano to exit
    //                         nanoProcess.waitFor();
                
    //                         // Restore Lanterna's terminal mode after nano exits
    //                         screen.startScreen();
				// 		} catch (IOException e) {
				// 			// TODO Auto-generated catch block
				// 			e.printStackTrace();
				// 		}
                        
    //                 });
    //             }
    //         }
    //     }
        
}
    
    
    
    
    // public Panel drawDir(Window w){
    //     Panel contentPanel = new Panel(new GridLayout(2));
    //     Label l = new Label(cur_directory);
        
        
    //     contentPanel.addComponent(l);
        
    //     Button b = new Button("Back", () -> {
    //        w.wa
    //     });
    //     return contentPanel;
    // }
