/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIManager;


import MovieManager.Movie;
import charManager.characterList;
import charNetManager.CharNetList;

import graphVisualizer.graphDrawer;

import javafx.stage.Stage;

import plotManager.linePlotDrawer;


/**
 *
 * @author user
 */
public class mainFrameHandler extends javax.swing.JFrame {

    /**
     * Creates new form mainFrameHandler
     */

	public Movie mainClass;
    public int time_point;
    
    public mainFrameHandler(Movie mainClass) {
        this.mainClass = mainClass;
        
        initComponents();
        
        time_point = 0;
        timeSlider.setMaximum(mainClass.totalLengthOfMovie);
        timeSlider.setMinimum(1);
        timeSlider.setMajorTickSpacing(1000);
        timeSlider.setMinorTickSpacing(100);
        
        
        
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new mainFrameHandler().setVisible(true);
//            }
//        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        timeSlider = new javax.swing.JSlider();
        charNetBoard = new javax.swing.JPanel();
        accCharNetBoard = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        showCharNet = new javax.swing.JButton();
        timePoint = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        timeSlider.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        timeSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                timeSliderStateChanged(evt);
            }
        });
        timeSlider.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                timeSliderMouseReleased(evt);
            }
        });

        charNetBoard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        charNetBoard.setMaximumSize(new java.awt.Dimension(470, 470));
        charNetBoard.setMinimumSize(new java.awt.Dimension(470, 470));
        charNetBoard.setPreferredSize(new java.awt.Dimension(470, 470));
        
        accCharNetBoard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        accCharNetBoard.setMaximumSize(new java.awt.Dimension(470, 470));
        accCharNetBoard.setMinimumSize(new java.awt.Dimension(470, 470));
        accCharNetBoard.setPreferredSize(new java.awt.Dimension(470, 470));

        javax.swing.GroupLayout charNetBoardLayout = new javax.swing.GroupLayout(charNetBoard);
        javax.swing.GroupLayout accCharNetBoardLayout = new javax.swing.GroupLayout(accCharNetBoard);
        
        charNetBoard.setLayout(charNetBoardLayout);
        charNetBoardLayout.setHorizontalGroup(
            charNetBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1013, Short.MAX_VALUE)
        );
        charNetBoardLayout.setVerticalGroup(
            charNetBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        
        accCharNetBoard.setLayout(accCharNetBoardLayout);
        accCharNetBoardLayout.setHorizontalGroup(
            accCharNetBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGap(0, 1013, Short.MAX_VALUE)
        );
        accCharNetBoardLayout.setVerticalGroup(
            accCharNetBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        org.openide.awt.Mnemonics.setLocalizedText(showCharNet, org.openide.util.NbBundle.getMessage(mainFrameHandler.class, "mainFrameHandler.showCharNet.text")); // NOI18N
        showCharNet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showCharNetMouseClicked(evt);
            }
        });

        timePoint.setText(org.openide.util.NbBundle.getMessage(mainFrameHandler.class, "mainFrameHandler.timePoint.text")); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(showCharNet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(timePoint, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(showCharNet, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(timePoint, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 481, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(timeSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 1167, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(charNetBoard, javax.swing.GroupLayout.PREFERRED_SIZE, 1015, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(accCharNetBoard, javax.swing.GroupLayout.PREFERRED_SIZE, 1015, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(timeSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(charNetBoard, javax.swing.GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE)
                    .addComponent(accCharNetBoard, javax.swing.GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        charNetBoard.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(mainFrameHandler.class, "mainFrameHandler.charNetBoard.AccessibleContext.accessibleName")); // NOI18N
        accCharNetBoard.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(mainFrameHandler.class, "mainFrameHandler.charNetBoard.AccessibleContext.accessibleName")); // NOI18N

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    private void timeSliderMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_timeSliderMouseReleased
        // TODO add your handling code here:
        //time_point = timeSlider.getValue();
    }//GEN-LAST:event_timeSliderMouseReleased

    private void showCharNetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showCharNetMouseClicked
        // TODO add your handling code here:
        
        //time_point = Integer.parseInt(timePoint.getText());
        charNetBoard.removeAll();
        accCharNetBoard.removeAll();
        mainClass.gd.graphVisualizer(charNetBoard, mainClass.charNetList.charNetList.get(time_point-1), mainClass.characterList.characterList);
        mainClass.gd.graphVisualizer2(accCharNetBoard, mainClass.charNetList.charNetList.get(time_point-1), mainClass.characterList.characterList);
    }//GEN-LAST:event_showCharNetMouseClicked

    private void timeSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_timeSliderStateChanged
        // TODO add your handling code here:
        time_point = timeSlider.getValue();
    }//GEN-LAST:event_timeSliderStateChanged

    
    //mainClass.gDrawer.graphVisualizer(charNetBoard, mainClass.dCharNet.DynamicCharNet[time_point], mainClass.charlist.CharacterList, time_point);
    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(mainFrameHandler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(mainFrameHandler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(mainFrameHandler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(mainFrameHandler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPanel charNetBoard;
    public javax.swing.JPanel accCharNetBoard;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton showCharNet;
    private javax.swing.JTextField timePoint;
    private javax.swing.JSlider timeSlider;
    // End of variables declaration//GEN-END:variables
    
   
}
