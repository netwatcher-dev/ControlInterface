/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlinterface;

import dataStruct.TransportProtocol;
import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 *
 * @author ben
 */
public class MyIconListRenderer extends DefaultListCellRenderer {
    
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) 
    {
        
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        if(value instanceof TransportProtocol)
        {
            TransportProtocol p = (TransportProtocol)value;
            short status = p.getStatus(System.currentTimeMillis()/1000);
            
            if(status == TransportProtocol.STATUS_NOT_DETECTED)
                label.setForeground(Color.red); //red
            else if(status == TransportProtocol.STATUS_RECENT_DETECTED)
                label.setForeground(new Color(255, 100, 0)); //orange
            else if(status == TransportProtocol.STATUS_DETECTED)
                label.setForeground(new Color(0, 150, 0));  // green
             
            if(p.isCaptured())
                label.setIcon(new ImageIcon(getClass().getResource("/controlinterface/icons/bullet_green.png")));
            else
                label.setIcon(new ImageIcon(getClass().getResource("/controlinterface/icons/bullet_red.gif")));
            
            label.setText(p.toString());
        }
        
        return label;
    }
}
