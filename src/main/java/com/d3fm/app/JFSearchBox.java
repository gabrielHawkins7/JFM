package com.d3fm.app;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

import com.googlecode.lanterna.gui2.ActionListBox;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Separator;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.TextBox.TextChangeListener;

class JFSearchBox {
    
    static Panel search_box_panel;
    
    private static String query;
    
    private static ActionListBox searchBox;
    
    public static Panel getSearchBoxPanel(){
        search_box_panel = new Panel(new LinearLayout(Direction.VERTICAL));
        
        search_box_panel.addComponent(
                    new Label("Search")
                    .setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center)));
        search_box_panel.addComponent(
                    new TextBox("")
                    .setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center))
                    .setTextChangeListener(new TextChangeListener() 
                        {
    						@Override
    						public void onTextChanged(String newText, boolean changedByUserInteraction) {
    							query = newText;
                                reload_search(JFM.current_dir);
                                JFM.dir_label.setText(newText);
    						}
                        })
                    .withBorder(Borders.singleLine())
                    );
        search_box_panel.addComponent(new Separator(Direction.HORIZONTAL)
                    .setLayoutData(LinearLayout
                    .createLayoutData(LinearLayout.Alignment.Fill)));
        
        searchBox = new ActionListBox();
        
        search_box_panel.addComponent(searchBox
                    .setLayoutData(LinearLayout
                    .createLayoutData(LinearLayout.Alignment.Fill)));
        
        
        
        return search_box_panel;
    }
    
    
    
    public static void reload_search(File dir){
        searchBox.clearItems();
        File[] files = dir.listFiles();
        sortByLevenshteinDistance(files, query);
        
        for(File f : files){
            if(f.isDirectory()){
                searchBox.addItem(f.getName() + "/", ()->{ JFM.global_reload(f);});
            }else{
                searchBox.addItem(f.getName(), ()->{ JFM.openFile(f);});
            }
        }
    }
    
    
    // Calculate Levenshtein distance
        private static int levenshteinDistance(String str1, String str2) {
            int len1 = str1.length();
            int len2 = str2.length();
            int[][] dp = new int[len1 + 1][len2 + 1];
    
            for (int i = 0; i <= len1; i++) {
                for (int j = 0; j <= len2; j++) {
                    if (i == 0) {
                        dp[i][j] = j;
                    } else if (j == 0) {
                        dp[i][j] = i;
                    } else {
                        dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                                dp[i - 1][j - 1] + (str1.charAt(i - 1) == str2.charAt(j - 1) ? 0 : 1));
                    }
                }
            }
            return dp[len1][len2];
        }
    
        // Sort array based on Levenshtein distance to query string
        private static void sortByLevenshteinDistance(File[] arr, String query) {
            Arrays.sort(arr, new Comparator<File>() {
                @Override
                public int compare(File s1, File s2) {
                    int dist1 = levenshteinDistance(s1.getName(), query);
                    int dist2 = levenshteinDistance(s2.getName(), query);
                    return Integer.compare(dist1, dist2);
                }
            });
        }
    
    
    
}