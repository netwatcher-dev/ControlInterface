/*
                    GNU GENERAL PUBLIC LICENSE
                       Version 3, 29 June 2007

 Copyright (C) 2007 Free Software Foundation, Inc. <http://fsf.org/>
 Everyone is permitted to copy and distribute verbatim copies
 of this license document, but changing it is not allowed.

                            Preamble

  The GNU General Public License is a free, copyleft license for
software and other kinds of works.

  The licenses for most software and other practical works are designed
to take away your freedom to share and change the works.  By contrast,
the GNU General Public License is intended to guarantee your freedom to
share and change all versions of a program--to make sure it remains free
software for all its users.  We, the Free Software Foundation, use the
GNU General Public License for most of our software; it applies also to
any other work released this way by its authors.  You can apply it to
your programs, too.

  When we speak of free software, we are referring to freedom, not
price.  Our General Public Licenses are designed to make sure that you
have the freedom to distribute copies of free software (and charge for
them if you wish), that you receive source code or can get it if you
want it, that you can change the software or use pieces of it in new
free programs, and that you know you can do these things.

  To protect your rights, we need to prevent others from denying you
these rights or asking you to surrender the rights.  Therefore, you have
certain responsibilities if you distribute copies of the software, or if
you modify it: responsibilities to respect the freedom of others.
*/

package controlinterface.dialog;

import dataStruct.AbstractProtocolCaptured;
import dataStruct.NetworkProtocol;
import dataStruct.TransportProtocol;
import java.util.LinkedList;
import java.util.List;
import util.DataFiltering;
import wrapper.CommunicationManagerV2;

public class filterDialog extends javax.swing.JDialog 
{
    
    public static DataFiltering PORT_UNDER_1024 = new DataFiltering() 
    {
        @Override
        public boolean validDatas(AbstractProtocolCaptured apc) 
        {
            if(apc instanceof TransportProtocol)
            {
                TransportProtocol tp = (TransportProtocol)apc;

                if(tp.getPort_destination() > 1024)
                    return false;

            }
            return true;
        }
    };
    
    public static DataFiltering ONLY_TCP = new DataFiltering() 
    {
        @Override
        public boolean validDatas(AbstractProtocolCaptured apc) 
        {
            if(apc instanceof TransportProtocol)
            {
                TransportProtocol tp = (TransportProtocol)apc;

                if(tp.getType() != TransportProtocol.PROTOCOL_TYPE_TCP)
                    return false;

            }
            return true;
        }
    };
    
    public static DataFiltering ONLY_UDP = new DataFiltering() 
    {
        @Override
        public boolean validDatas(AbstractProtocolCaptured apc) 
        {
            if(apc instanceof TransportProtocol)
            {
                TransportProtocol tp = (TransportProtocol)apc;

                if(tp.getType() != TransportProtocol.PROTOCOL_TYPE_UDP)
                    return false;

            }
            return true;
        }
    };
    
    public static DataFiltering TCP_OR_UDP = new DataFiltering() 
    {
        @Override
        public boolean validDatas(AbstractProtocolCaptured apc) 
        {
            if(apc instanceof TransportProtocol)
            {
                TransportProtocol tp = (TransportProtocol)apc;

                if(tp.getType() != TransportProtocol.PROTOCOL_TYPE_TCP && tp.getType() != TransportProtocol.PROTOCOL_TYPE_UDP)
                    return false;

            }
            return true;
        }
    };
    
    public static DataFiltering NO_LOOPBACK = new DataFiltering() 
    {
        @Override
        public boolean validDatas(AbstractProtocolCaptured apc) 
        {
            if(apc instanceof NetworkProtocol)
            {
                NetworkProtocol np = (NetworkProtocol)apc;

                if(np.getAddr_dest().isLoopbackAddress() || np.getAddr_dest().isLinkLocalAddress())
                    return false;

            }
            return true;
        }
    };
    
    public static DataFiltering NO_MULTICAST = new DataFiltering() 
    {
        @Override
        public boolean validDatas(AbstractProtocolCaptured apc) 
        {
            if(apc instanceof NetworkProtocol)
            {
                NetworkProtocol np = (NetworkProtocol)apc;

                if(np.getAddr_dest().isMulticastAddress())
                    return false;

            }
            return true;
        }
    };
    
    public static DataFiltering ONLY_WEB = new DataFiltering() 
    {
        @Override
        public boolean validDatas(AbstractProtocolCaptured apc) 
        {
            if(apc instanceof TransportProtocol)
            {
                TransportProtocol tp = (TransportProtocol)apc;

                if(tp.getPort_destination() != 80 && tp.getPort_destination() != 8080)
                    return false;

            }
            return true;
        }
    };
    
    /** Creates new form filterDialog */
    public filterDialog(java.awt.Frame parent, boolean modal) 
    {
        super(parent, modal);
        initComponents();
        
        if(CommunicationManagerV2.getInstance().getFilters() == null)
            return;
        
        jCheckBox1.setSelected(CommunicationManagerV2.getInstance().getFilters().contains(PORT_UNDER_1024));
        jCheckBox2.setSelected(CommunicationManagerV2.getInstance().getFilters().contains(NO_LOOPBACK));
        jCheckBox3.setSelected(CommunicationManagerV2.getInstance().getFilters().contains(NO_MULTICAST));
        
        if(CommunicationManagerV2.getInstance().getFilters().contains(ONLY_TCP))
        {
            jRadioButton1.setSelected(true);
            jRadioButton2.setSelected(false);
            jRadioButton3.setSelected(false);
        }
        else if(CommunicationManagerV2.getInstance().getFilters().contains(ONLY_UDP))
        {
            jRadioButton1.setSelected(false);
            jRadioButton2.setSelected(true);
            jRadioButton3.setSelected(false);
        }
        else
        {
            jRadioButton1.setSelected(false);
            jRadioButton2.setSelected(false);
            jRadioButton3.setSelected(true);
        }
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jCheckBox1 = new javax.swing.JCheckBox();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jCheckBox1.setSelected(true);
        jCheckBox1.setText("Allow only port below 1024");

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("TCP");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("UDP");

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setSelected(true);
        jRadioButton3.setText("UDP and TCP");

        jCheckBox2.setSelected(true);
        jCheckBox2.setText("Hide local address (::, 127.0.0.1, etc.)");

        jCheckBox3.setSelected(true);
        jCheckBox3.setText("Hide multicast (224.0.0.1, etc.)");

        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jCheckBox1)
                    .add(layout.createSequentialGroup()
                        .add(jRadioButton1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jRadioButton2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jRadioButton3))
                    .add(jCheckBox2)
                    .add(jCheckBox3))
                .addContainerGap(27, Short.MAX_VALUE))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap(133, Short.MAX_VALUE)
                .add(jButton2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jButton1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jCheckBox1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jRadioButton1)
                    .add(jRadioButton2)
                    .add(jRadioButton3))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jCheckBox2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jCheckBox3)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButton1)
                    .add(jButton2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        List<DataFiltering> filter = new LinkedList<DataFiltering>();
        
        if(jCheckBox1.isSelected())/*port under 1024*/
        {
            filter.add(PORT_UNDER_1024);
        }
        
        if(jRadioButton1.isSelected())
        {
            filter.add(ONLY_TCP);
        }
        else if(jRadioButton2.isSelected())
        {
            filter.add(ONLY_UDP);
        }
        else if(jRadioButton3.isSelected())
        {
            filter.add(TCP_OR_UDP);
        }
        
        if(jCheckBox2.isSelected())/*loopback*/
        {
            filter.add(NO_LOOPBACK);
        }
        
        if(jCheckBox3.isSelected())/*multicast*/
        {
            filter.add(NO_MULTICAST);
        }
        
        CommunicationManagerV2.getInstance().setFilters(filter);
        
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    // End of variables declaration//GEN-END:variables
    
    public static List<DataFiltering> getDefaultFilter()
    {
        List<DataFiltering> filter = new LinkedList<DataFiltering>();
        
        filter.add(PORT_UNDER_1024);
        filter.add(TCP_OR_UDP);
        filter.add(NO_LOOPBACK);
        filter.add(NO_MULTICAST);

        return filter;
    }
    
    
    /*
     *
     */
    public static List<DataFiltering> getDefaultHTTPFilter()
    {
        List<DataFiltering> filter = new LinkedList<DataFiltering>();
        
        filter.add(ONLY_WEB);
        filter.add(ONLY_TCP);
        filter.add(NO_LOOPBACK);
        filter.add(NO_MULTICAST);

        return filter;
    }
}
