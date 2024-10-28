/*
 * Created by JFormDesigner on Fri Oct 25 15:57:57 IST 2024
 */

package com.a4b.androidhelper.ui;

import javax.swing.*;
import net.miginfocom.swing.*;

/**
 * @author rohan
 */
public class Window2 extends JPanel {
    public Window2() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - unknown

        //======== this ========
        setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax . swing. border .
        EmptyBorder ( 0, 0 ,0 , 0) ,  "JF\u006frm\u0044es\u0069gn\u0065r \u0045va\u006cua\u0074io\u006e" , javax. swing .border . TitledBorder. CENTER ,javax . swing
        . border .TitledBorder . BOTTOM, new java. awt .Font ( "D\u0069al\u006fg", java .awt . Font. BOLD ,12 ) ,
        java . awt. Color .red ) , getBorder () ) );  addPropertyChangeListener( new java. beans .PropertyChangeListener ( )
        { @Override public void propertyChange (java . beans. PropertyChangeEvent e) { if( "\u0062or\u0064er" .equals ( e. getPropertyName () ) )
        throw new RuntimeException( ) ;} } );
        setLayout(new MigLayout(
            "hidemode 3",
            // columns
            "[fill]" +
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]"));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - unknown
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
