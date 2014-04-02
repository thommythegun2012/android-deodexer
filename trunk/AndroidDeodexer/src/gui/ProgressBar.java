/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import static java.awt.Dialog.ModalityType.APPLICATION_MODAL;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author Luis Peregrina
 */
public class ProgressBar extends javax.swing.JDialog {

    /**
     * Creates new form ProgressBar
     */
    
    public float progressCounter = 0;
    private JFrame parent;
    
    public ProgressBar(JFrame parent) {
        //super(parent, true);
        this.parent = parent;
        initComponents();
        setBounds(50, 50, getBounds().width, getBounds().height);
        setVisible(true);
        setModalityType(APPLICATION_MODAL);
        setModal(true);
    }
    
    public void Add(float i, String msg){
        progressCounter = progressCounter + i;
        pb_progress.setValue(Math.round(progressCounter));
        lbl_text.setText(msg);
    }
    public void Substract(float i){
        progressCounter = progressCounter - i; 
        pb_progress.setValue(Math.round(progressCounter));
    }
    
    public void toggleOK(boolean toggle){
        btn_ok.setEnabled(toggle);
    }
    
    public void message(String msg){
        lbl_text.setText(msg);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pb_progress = new javax.swing.JProgressBar();
        btn_ok = new javax.swing.JButton();
        lbl_text = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));

        btn_ok.setText("OK");
        btn_ok.setEnabled(false);
        btn_ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_okActionPerformed(evt);
            }
        });

        lbl_text.setText("Please wait...");
        lbl_text.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_ok, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pb_progress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_text, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_text, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pb_progress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_ok)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_okActionPerformed
        // Exit
        this.dispose();
    }//GEN-LAST:event_btn_okActionPerformed

    /**
     * @param args the command line arguments
     */
    public void main(String args[]) {
        /* Create and display the form */
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ProgressBar(parent).setVisible(true);
                
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_ok;
    private javax.swing.JLabel lbl_text;
    private javax.swing.JProgressBar pb_progress;
    // End of variables declaration//GEN-END:variables
}
