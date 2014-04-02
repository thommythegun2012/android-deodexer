/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import androiddeodexer.DirectoryRestrictedFileSystemView;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.LinkedHashMap;
import androiddeodexer.PrintStreamCapturer;
import androiddeodexer.ScriptDEODEX;
import androiddeodexer.ScriptPULL;
import androiddeodexer.ScriptCLEAR;
import androiddeodexer.SysUtils;
import java.awt.Component;
import java.awt.Container;
import java.awt.Rectangle;
import java.io.File;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author Luis Peregrina
 */
public class Main extends javax.swing.JFrame {
    LinkedHashMap<String, String> api_levels = new LinkedHashMap<>();
    int debugWidth, debugHeight;
    Rectangle debugShow = new Rectangle(15, 15, 700, 400);
    Rectangle debugHide = new Rectangle(15, 15, 393, 400);

    
    
    private void set_api_levels(){
        api_levels.put("19", "4.4");
        api_levels.put("18", "4.3");
        api_levels.put("17", "4.2,4.2.2");
        api_levels.put("16", "4.1, 4.1.1");
        api_levels.put("15", "4.0.3, 4.0.4");
        api_levels.put("14", "4.0, 4.0.1, 4.0.2");
        api_levels.put("13", "3.2");
        api_levels.put("12", "3.1.x");
        api_levels.put("11", "3.0.x");
        api_levels.put("10", "2.3.3 - 2.3.4");
        api_levels.put("9", "2.3 - 2.3.2");
        api_levels.put("8", "2.2.x");
        api_levels.put("7", "2.1.x");
        api_levels.put("6", "2.0.1");
        api_levels.put("5", "2.0");
        api_levels.put("4", "1.6");
        api_levels.put("3", "1.5");
        api_levels.put("2", "1.1");
        api_levels.put("1", "1.0");
    }
    LinkedHashMap<Integer,String> indexedList = new LinkedHashMap<>();
    
    private void api_fill(){
        String[] v = api_levels.keySet().toArray(new String[api_levels.size()]);
        for(int x=0; x<api_levels.size(); x++){
            indexedList.put(x, v[x]);
        }
    }

    /**
     * Creates new form frame_Main
     */
    public Main() {
        set_api_levels();
        initComponents();
        lst_android_version.setSelectedIndex(3);
        
        api_fill();
        
        
        setIconImage(new ImageIcon(getClass().getClassLoader().
                getResource("resources/icons/main.png")).getImage());
        
        
        
        
        pack();
        setVisible(true);
        
        debugHeight = this.getHeight();
        toggleDebug(false);
        
        System.setOut(new PrintStreamCapturer(txt_debug, System.out));
        System.setErr(new PrintStreamCapturer(txt_debug, System.err, "Error: "));

        cb_debug.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent event){
                if (cb_debug.isSelected()){
                    toggleDebug(true);
                }
                else{
                    toggleDebug(false);
                }
            }
        });
        
        try{
            install();
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
    }
    
    private void toggleDebug(boolean show){
        this.setResizable(true);
        if(show){
            this.setBounds(debugShow);
        }
        else{
            this.setBounds(debugHide);
        }
        
        this.setResizable(false);
    }
    
    
    public void disableSomeButtons(Container c) {  
        int len = c.getComponentCount();  
        for (int i = 0; i < len; i++) {  
            Component comp = c.getComponent(i);  
            if (comp instanceof JButton) {  
                JButton b = (JButton) comp;  
                Icon icon = b.getIcon();             
                if (icon != null  
                && (icon == UIManager.getIcon("FileChooser.upFolderIcon") 
                        || icon == UIManager.getIcon("FileChooser.homeFolderIcon") 
                        || icon == UIManager.getIcon("FileChooser.newFolderIcon") ) ){  
                    b.setEnabled(false);  
                }   
            } else if (comp instanceof Container) {  
                disableSomeButtons((Container) comp);  
            }  
        }  
    }  
    
    private void install() throws Exception{
        System.out.println("Verifying necessary files...");
        SysUtils Utils = new SysUtils();
        Utils.OSCommands();
        File adp = new File(Utils.adb);
        if(!adp.exists()){
            System.out.println("Files missing, unpacking...");
            Utils.directoryMake("resources");
            Utils.fileCopyFromJar("/"+adp.getPath().replace(File.separator,"/"), adp.getPath() );
            if(Utils.OSCommands()==0){
                Utils.fileCopyFromJar("/resources/AdbWinApi.dll", "resources/AdbWinApi.dll");
                Utils.fileCopyFromJar("/resources/AdbWinUsbApi.dll", "resources/AdbWinUsbApi.dll");
            }
        }
        System.out.println("Done with nessesary files.");
    }
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbl_compression = new javax.swing.JLabel();
        spn_compression = new javax.swing.JSpinner();
        btn_exit = new javax.swing.JButton();
        pnl_deodex = new javax.swing.JPanel();
        btn_deodex_apk = new javax.swing.JButton();
        btn_deodex_jar = new javax.swing.JButton();
        pnl_framework = new javax.swing.JPanel();
        btn_pull_framework = new javax.swing.JButton();
        btn_clear_framework = new javax.swing.JButton();
        pnl_version = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lst_android_version = new javax.swing.JList();
        cb_debug = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_debug = new javax.swing.JTextArea();
        pnl_apks = new javax.swing.JPanel();
        btn_pull_apk = new javax.swing.JButton();
        btn_clear_apk = new javax.swing.JButton();
        btn_debug_clear = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("AndroidDeodexer");

        lbl_compression.setText("Compression:");

        spn_compression.setModel(new javax.swing.SpinnerNumberModel(0, 0, 9, 1));

        btn_exit.setText("Exit");
        btn_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_exitActionPerformed(evt);
            }
        });

        pnl_deodex.setBorder(javax.swing.BorderFactory.createTitledBorder("Deodex"));

        btn_deodex_apk.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("resources/icons/apks.png")));
        btn_deodex_apk.setText("APK(s)");
        btn_deodex_apk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deodex_apkActionPerformed(evt);
            }
        });

        btn_deodex_jar.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("resources/icons/framework.png")));
        btn_deodex_jar.setText("JAR(s)");
        btn_deodex_jar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deodex_jarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_deodexLayout = new javax.swing.GroupLayout(pnl_deodex);
        pnl_deodex.setLayout(pnl_deodexLayout);
        pnl_deodexLayout.setHorizontalGroup(
            pnl_deodexLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_deodex_apk, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btn_deodex_jar, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        pnl_deodexLayout.setVerticalGroup(
            pnl_deodexLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_deodexLayout.createSequentialGroup()
                .addComponent(btn_deodex_apk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_deodex_jar))
        );

        pnl_framework.setBorder(javax.swing.BorderFactory.createTitledBorder("Framework"));

        btn_pull_framework.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("resources/icons/pull.png")));
        btn_pull_framework.setText("Pull from phone");
        btn_pull_framework.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pull_frameworkActionPerformed(evt);
            }
        });

        btn_clear_framework.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("resources/icons/clear.png")));
        btn_clear_framework.setText("Delete from folder");
        btn_clear_framework.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clear_frameworkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_frameworkLayout = new javax.swing.GroupLayout(pnl_framework);
        pnl_framework.setLayout(pnl_frameworkLayout);
        pnl_frameworkLayout.setHorizontalGroup(
            pnl_frameworkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_pull_framework, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_clear_framework, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
        );
        pnl_frameworkLayout.setVerticalGroup(
            pnl_frameworkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_frameworkLayout.createSequentialGroup()
                .addComponent(btn_pull_framework)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_clear_framework))
        );

        pnl_version.setBorder(javax.swing.BorderFactory.createTitledBorder("Android version"));

        lst_android_version.setBackground(javax.swing.UIManager.getDefaults().getColor("control"));
        lst_android_version.setModel(new javax.swing.AbstractListModel() {
            String[] strings = api_levels.values().toArray(new String[api_levels.size()]);
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(lst_android_version);

        javax.swing.GroupLayout pnl_versionLayout = new javax.swing.GroupLayout(pnl_version);
        pnl_version.setLayout(pnl_versionLayout);
        pnl_versionLayout.setHorizontalGroup(
            pnl_versionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
        );
        pnl_versionLayout.setVerticalGroup(
            pnl_versionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_versionLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        cb_debug.setText("Debug");

        txt_debug.setColumns(20);
        txt_debug.setRows(5);
        txt_debug.setEnabled(false);
        jScrollPane2.setViewportView(txt_debug);

        pnl_apks.setBorder(javax.swing.BorderFactory.createTitledBorder("APKs"));

        btn_pull_apk.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("resources/icons/pull.png")));
        btn_pull_apk.setText("Pull from phone");
        btn_pull_apk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pull_apkActionPerformed(evt);
            }
        });

        btn_clear_apk.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("resources/icons/clear.png")));
        btn_clear_apk.setText("Delete from folder");
        btn_clear_apk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clear_apkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_apksLayout = new javax.swing.GroupLayout(pnl_apks);
        pnl_apks.setLayout(pnl_apksLayout);
        pnl_apksLayout.setHorizontalGroup(
            pnl_apksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_pull_apk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_clear_apk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnl_apksLayout.setVerticalGroup(
            pnl_apksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_apksLayout.createSequentialGroup()
                .addComponent(btn_pull_apk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_clear_apk))
        );

        btn_debug_clear.setText("Clear");
        btn_debug_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_debug_clearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btn_exit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(pnl_deodex, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(pnl_apks, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(pnl_framework, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnl_version, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbl_compression)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spn_compression, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cb_debug)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                    .addComponent(btn_debug_clear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnl_version, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_compression)
                            .addComponent(spn_compression, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cb_debug)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnl_deodex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnl_apks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnl_framework, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_exit)
                    .addComponent(btn_debug_clear))
                .addContainerGap())
        );

        lbl_compression.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exitActionPerformed
        // We exit
        this.dispose();
    }//GEN-LAST:event_btn_exitActionPerformed

    private void btn_deodex_apkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deodex_apkActionPerformed
        File dir = new File("app\\");
        if(dir.isDirectory()){
            FileSystemView fsv = new DirectoryRestrictedFileSystemView(dir);
            JFileChooser fileChooser = new JFileChooser(fsv);
            disableSomeButtons(fileChooser);
            fileChooser.setMultiSelectionEnabled(true);
            fileChooser.setDialogTitle("Select APK");
            fileChooser.setCurrentDirectory(dir);
            fileChooser.setFileFilter(new FileNameExtensionFilter("Apps", "odex"));
            if( fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION ){
                File[] sel = fileChooser.getSelectedFiles();
                txt_debug.setText("");
                String[] files = new String[sel.length];
                for(int x=0; x<sel.length; x++){
                    files[x] = sel[x].getName().replaceFirst("[.][^.]+$", "");
                }
                ScriptDEODEX apk = new ScriptDEODEX(
                    files, 
                    indexedList.get(lst_android_version.getSelectedIndex()), 
                    (int)spn_compression.getValue(),
                    "apk",
                    this
                );
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "The APKs have not been pulled.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_deodex_apkActionPerformed

    private void btn_deodex_jarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deodex_jarActionPerformed
        File dir = new File("framework\\");
        if(dir.isDirectory()){
            FileSystemView fsv = new DirectoryRestrictedFileSystemView(dir);
            JFileChooser fileChooser = new JFileChooser(fsv);
            disableSomeButtons(fileChooser);
            fileChooser.setMultiSelectionEnabled(true);
            fileChooser.setDialogTitle("Select Jar");
            fileChooser.setCurrentDirectory(dir);
            fileChooser.setFileFilter(new FileNameExtensionFilter("Jars", "odex"));
            if( fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
                txt_debug.setText("");
                File[] sel = fileChooser.getSelectedFiles();
                String[] files = new String[sel.length];
                for(int x=0; x<sel.length; x++){
                    files[x] = sel[x].getName().replaceFirst("[.][^.]+$", "");
                }
                ScriptDEODEX apk = new ScriptDEODEX(
                    files, 
                    indexedList.get(lst_android_version.getSelectedIndex()), 
                    (int)spn_compression.getValue(),
                    "jar",
                    this
                );
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "The framework has not been pulled.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_deodex_jarActionPerformed

    private void btn_pull_frameworkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pull_frameworkActionPerformed
        txt_debug.setText("");
        System.out.println("Pulling framework from phone");
        ScriptPULL pull = new ScriptPULL("framework", this);
    }//GEN-LAST:event_btn_pull_frameworkActionPerformed
    
    private void btn_clear_frameworkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clear_frameworkActionPerformed
        txt_debug.setText("");
        ScriptCLEAR delete = new ScriptCLEAR("framework", this);
    }//GEN-LAST:event_btn_clear_frameworkActionPerformed

    private void btn_pull_apkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pull_apkActionPerformed
        txt_debug.setText("");
        System.out.println("Pulling apks from phone");
        ScriptPULL pull = new ScriptPULL("app", this);
    }//GEN-LAST:event_btn_pull_apkActionPerformed

    private void btn_clear_apkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clear_apkActionPerformed
        txt_debug.setText("");
        ScriptCLEAR delete = new ScriptCLEAR("app", this);
    }//GEN-LAST:event_btn_clear_apkActionPerformed

    private void btn_debug_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_debug_clearActionPerformed
        txt_debug.setText("");
    }//GEN-LAST:event_btn_debug_clearActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_clear_apk;
    private javax.swing.JButton btn_clear_framework;
    private javax.swing.JButton btn_debug_clear;
    private javax.swing.JButton btn_deodex_apk;
    private javax.swing.JButton btn_deodex_jar;
    private javax.swing.JButton btn_exit;
    private javax.swing.JButton btn_pull_apk;
    private javax.swing.JButton btn_pull_framework;
    private javax.swing.JCheckBox cb_debug;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbl_compression;
    private javax.swing.JList lst_android_version;
    private javax.swing.JPanel pnl_apks;
    private javax.swing.JPanel pnl_deodex;
    private javax.swing.JPanel pnl_framework;
    private javax.swing.JPanel pnl_version;
    private javax.swing.JSpinner spn_compression;
    private javax.swing.JTextArea txt_debug;
    // End of variables declaration//GEN-END:variables
}
