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

package controlinterface;

import dataStruct.NetworkProtocol;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import util.HTTPModule;
import util.Module;
import wrapper.CommunicationManagerV2;
import wrapper.CoreEvent;

/**
 *
 * @author djo
 */
public class SimpleControlFrame extends javax.swing.JFrame implements CoreEvent
{
    private ControlFrameMouseAdapter cfma;
    private static String JLABEL8_NETWORK_STRING = " Network Interface : ";
    private static String JLABEL8_FILE_STRING = " Captured File : ";
    private static String JLABEL1_PREFIX = "NetWatcher : ";
    private DefaultComboBoxModel dlm_source, dlm_target;
    private SimpleControlFrame This;
    private CommunicationManagerV2 cm;
    
    private final ImageIcon iconPlay = new ImageIcon(getClass().getResource("/controlinterface/icons/media_play.png"));
    private final ImageIcon iconPause = new ImageIcon(getClass().getResource("/controlinterface/icons/media_pause.png"));
    
    private boolean captureStarted;
    private Module m;
    
    /** Creates new form SimpleControlFrame */
    public SimpleControlFrame() 
    {
        This = this;
        dlm_source = new DefaultComboBoxModel();
        dlm_target = new DefaultComboBoxModel();
        
        initComponents();
        cfma = new ControlFrameMouseAdapter();
        
        jButton3.addMouseListener(cfma);
        jButton4.addMouseListener(cfma);
        jButton7.addMouseListener(cfma);
        jButton8.addMouseListener(cfma);
        jComboBox3.addMouseListener(cfma);
        jComboBox4.addMouseListener(cfma);
        jSlider3.addMouseListener(cfma);
        jSlider4.addMouseListener(cfma);
        jToggleButton4.addMouseListener(cfma);
        jToggleButton5.addMouseListener(cfma);
        jToggleButton6.addMouseListener(cfma);
        cm = CommunicationManagerV2.getInstance();
        cm.addListener(this);
        
        cm.getState().setFile(false);
        cm.getState().setStream(true);
        captureStarted = false;
        
        System.out.println(""+cm.getState());
    }

    @Override
    public void setVisible(boolean enable)
    {
        if(enable)
        {
            FilenameFilter ff = new FilenameFilter() {@Override public boolean accept(File file, String name) 
            {
                return !name.startsWith(".") && name.endsWith(".jar") && !name.startsWith("controlInterface");
            }};
            String [] files = new File(".").list(ff);
            
            if(files.length == 0)
            {
                JOptionPane.showMessageDialog(this,"There is no reconstitution module in the current directory : "+new File(".").getAbsolutePath(),"No reconstitution module",JOptionPane.WARNING_MESSAGE);
                System.exit(1);
            }
            
            String s = (String)JOptionPane.showInputDialog(this,"Module choice : ","Choose a Reconstitution module",JOptionPane.PLAIN_MESSAGE,null,files,null);
            
            if(s == null)
                System.exit(1);
            
            m = new HTTPModule(s);
            
            
            SwingWorker worker = getConnectionWorker();
            worker.execute();
        }
        
        super.setVisible(enable);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jComboBox3 = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jToggleButton5 = new javax.swing.JToggleButton();
        jToggleButton6 = new javax.swing.JToggleButton();
        jComboBox4 = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jSlider4 = new javax.swing.JSlider();
        jLabel6 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jToggleButton4 = new javax.swing.JToggleButton();
        jButton4 = new javax.swing.JButton();
        jSlider3 = new javax.swing.JSlider();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Netwatcher");

        jComboBox3.setModel(dlm_target);
        jComboBox3.setEnabled(false);
        jComboBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox3ItemStateChanged(evt);
            }
        });

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Target address : ");

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText(" Network Interface : ");

        jToggleButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/controlinterface/icons/document.png"))); // NOI18N
        jToggleButton5.setText("Open a record");
        jToggleButton5.setEnabled(false);
        jToggleButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton5ActionPerformed(evt);
            }
        });

        jToggleButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/controlinterface/icons/earth_network.png"))); // NOI18N
        jToggleButton6.setSelected(true);
        jToggleButton6.setText("Listen the network");
        jToggleButton6.setEnabled(false);
        jToggleButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton6ActionPerformed(evt);
            }
        });

        jComboBox4.setModel(dlm_source);
        jComboBox4.setEnabled(false);
        jComboBox4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox4ItemStateChanged(evt);
            }
        });

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Mode : ");

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel9)
                    .add(jLabel8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 130, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel7))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jComboBox3, 0, 409, Short.MAX_VALUE)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jToggleButton6)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jToggleButton5))
                    .add(jComboBox4, 0, 409, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jToggleButton6)
                    .add(jToggleButton5)
                    .add(jLabel9, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 22, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jComboBox4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 19, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jComboBox3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 0, 0, 0, new java.awt.Color(102, 102, 102)));

        jSlider4.setValue(0);
        jSlider4.setEnabled(false);
        jSlider4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jSlider4MouseReleased(evt);
            }
        });

        jLabel6.setText("Reading speed : ");

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/controlinterface/icons/media_stop.png"))); // NOI18N
        jButton8.setEnabled(false);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/controlinterface/icons/media_play.png"))); // NOI18N
        jButton7.setEnabled(false);
        jButton7.setName("start"); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/controlinterface/icons/gears.png"))); // NOI18N
        jButton3.setText("Advanced");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jToggleButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/controlinterface/icons/rec.png"))); // NOI18N
        jToggleButton4.setEnabled(false);
        jToggleButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton4ActionPerformed(evt);
            }
        });

        jButton4.setText("Exit");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jSlider3.setMajorTickSpacing(50);
        jSlider3.setMinimum(-100);
        jSlider3.setMinorTickSpacing(10);
        jSlider3.setPaintLabels(true);
        jSlider3.setPaintTicks(true);
        jSlider3.setSnapToTicks(true);
        jSlider3.setValue(0);
        jSlider3.setEnabled(false);
        jSlider3.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider3StateChanged(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(jLabel6)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jSlider3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                        .add(18, 18, 18)
                        .add(jButton3)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jButton4))
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(jButton8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jButton7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jSlider4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jToggleButton4)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jSlider4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jToggleButton4, 0, 0, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jButton8)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jButton7, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                .add(9, 9, 9)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel6, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jButton3)
                        .add(jButton4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jSlider3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 44, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(21, 21, 21))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 0, 0, 0, new java.awt.Color(102, 102, 102)));
        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        jLabel1.setText("NetWatcher, connection to the extraction system...");
        jPanel2.add(jLabel1);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton4ActionPerformed

    /*Play/pause button*/
    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        System.out.println(""+cm.getState());
        if(cm.getState().IS_FILE() && !cm.getState().IS_PARSED())
        {
            JOptionPane.showMessageDialog(this,"The file is not yet parsed, please wait and retry","Parsing file",JOptionPane.WARNING_MESSAGE);
            return;
        }

        enableReader(false); /*freeze the reader pannel*/
                
        if(!captureStarted) /*si pas de capture en cours*/
        {
            if(!cm.getState().IS_RECORDING())/*s'il n'y a pas de record en cours, il faut verouiller le target panel*/
            {
                enableTargetPanel(false);
            }
            jSlider4.setValue(0);
            
            /*c'est le premier start, il n'y a pas encore eu de pause*/
            getStartWorker((NetworkProtocol)jComboBox3.getSelectedItem(),m).execute();
        }
        else if(cm.getState().IS_PAUSE())
        {
            /*sortie de pause*/
            getResumeWorker().execute();
        }
        else
        {
            /*pause*/
            getPauseWorker().execute();
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    /*Stop button*/
    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        captureStarted = false;
        jButton8.setEnabled(false); /*disable stop button*/
        enableReader(false); /*freeze the reader pannel*/
        this.getStopWorker().execute(); /*execute the stop action*/
    }//GEN-LAST:event_jButton8ActionPerformed

    /*Network mode*/
    private void jToggleButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton6ActionPerformed
        jToggleButton5ActionPerformed(evt);     
    }//GEN-LAST:event_jToggleButton6ActionPerformed

    /*File mode*/
    private void jToggleButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton5ActionPerformed
        if(jToggleButton6.isSelected())
        {
            jLabel8.setText(JLABEL8_NETWORK_STRING);
        }
        else
        {
            jLabel8.setText(JLABEL8_FILE_STRING);
        }

        if(evt.getSource() == jToggleButton6)
        {
            jToggleButton5.setSelected(!jToggleButton6.isSelected());
        }
        else
        {
            jToggleButton6.setSelected(!jToggleButton5.isSelected()); 
        }

        cm.setRefreshCoreState(false); 
        cm.setAutoRefresh(false); /*stop auto refresh*/
        
        cm.getState().setStream(jToggleButton6.isSelected());
        cm.getState().setFile(!jToggleButton6.isSelected());

        restart();
        
        dlm_target.removeAllElements();
        getRefreshSourceWorker().execute();
        
    }//GEN-LAST:event_jToggleButton5ActionPerformed

    /*advanced button*/
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        cm.removeListener(this);
        this.setVisible(false);
        new ControlFrame(true).setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    /*Item selected in source list*/
    private void jComboBox4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox4ItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED)
        {
            System.out.println(""+jComboBox4.getSelectedItem());
            if(jComboBox4.getSelectedIndex() > 0)
            {
                enableTargetPanel(false);
                dlm_target.removeAllElements();
                getSelectSourceWorker((String)jComboBox4.getSelectedItem()).execute();
            }
            else
            {
                jComboBox3.setEnabled(false);
                getSelectSourceWorker(null).execute();
            }
        }  
    }//GEN-LAST:event_jComboBox4ItemStateChanged

    /*Item selected in target list*/
    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED)
        {
            if(jComboBox3.getSelectedIndex() > 0)
            {
                enableReader(true);
            }
            else
            {
                enableReader(false);
            }
        }
    }//GEN-LAST:event_jComboBox3ItemStateChanged

    /*Record Button*/
    private void jToggleButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton4ActionPerformed
        
        if(this.jToggleButton4.isSelected())
        {
            String inputValue = JOptionPane.showInputDialog(this,"Please input a record name", "Create a record...",JOptionPane.QUESTION_MESSAGE);
            
            if(inputValue == null)
            {
                jToggleButton4.setSelected(false);
                return;
            }
            
            /*add cap extension if not present*/
            if(!inputValue.endsWith(".cap"))
            {
                inputValue = inputValue + ".cap";
            }
            
            if(!captureStarted)/*si la capture n'a pas encore démarrée, il faut verouiller le target panel*/
            {
                enableTargetPanel(false); /*disable choice pannel*/
            }
            
            enableReader(false); /*freeze the reader pannel*/
            
            this.getRecordStartWorker(inputValue).execute();
        }
        else
        {
            enableReader(false); /*freeze the reader pannel*/
            getRecordStopWorker().execute();
        }
    }//GEN-LAST:event_jToggleButton4ActionPerformed

    private void jSlider3StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider3StateChanged
        
        if(!jSlider3.getValueIsAdjusting())
        {
            enableReader(false); /*freeze the reader*/
            this.getSpeedWorker(jSlider3.getValue()).execute();
        }
        
    }//GEN-LAST:event_jSlider3StateChanged

    private void jSlider4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSlider4MouseReleased
        if(!jSlider4.isEnabled())
            return;
        
        if(!this.captureStarted)
        {
            if(cm.getState().IS_FINISHED())
                jSlider4.setValue(100);
            else
                jSlider4.setValue(0);
            return;
        }
            
            
        if(cm.getState().IS_PARSED())
        {
            enableReader(false); /*freeze the reader*/
            getEvolutionWorker(jSlider4.getValue()).execute();
        }
        else
        {
            JOptionPane.showMessageDialog(this,"The file is not yet parsed, please wait and retry","Parsing file",JOptionPane.WARNING_MESSAGE);
            jSlider4.setValue(0);
        }
    }//GEN-LAST:event_jSlider4MouseReleased

    
    private void restart()/*restart the graphical interface*/
    {   
        Runnable r = new Runnable() { @Override public void run() 
        {
            /*reader panel*/
            jButton7.setEnabled(false); /*play button*/
            jSlider4.setEnabled(false); /*evolution bar*/
            jButton8.setEnabled(false); /*stop button*/
            jSlider3.setEnabled(false); /*speed bar*/
            jToggleButton4.setEnabled(false); /*record button*/
            
            
            /*target panel*/
            jToggleButton5.setEnabled(false); /*Network button*/
            jToggleButton6.setEnabled(false); /*file button*/
            jComboBox3.setEnabled(false); /*target list*/
            jComboBox4.setEnabled(false); /*file/interface list*/
            
            jSlider3.setValue(0);
            jSlider4.setValue(0);
        }};
        
        if(java.awt.EventQueue.isDispatchThread())
        {
            r.run();
        }
        else
        {
            java.awt.EventQueue.invokeLater(r);
        }
    }
    
    /*
     * enable or disable the reader panel
     */
    private void enableReader(final boolean enable)
    {
        Runnable r = new Runnable() { @Override public void run() 
        {
            jButton7.setEnabled(enable); /*play button*/
            jSlider3.setEnabled(enable); /*speed bar*/

            if(cm.getState().IS_STREAM())
            {
                jToggleButton4.setEnabled(enable); /*record button*/
                jSlider4.setEnabled(false); /*evolution bar*/
            }
            else
            {
                jSlider4.setEnabled(enable); /*evolution bar*/
                jToggleButton4.setEnabled(false); /*record button*/
            }
        }};
        
        if(java.awt.EventQueue.isDispatchThread())
        {
            r.run();
        }
        else
        {
            java.awt.EventQueue.invokeLater(r);
        }
    }
    
    /*
     * enable or disable the target panel
     */
    private void enableTargetPanel(final boolean enable)
    {
        Runnable r = new Runnable() { @Override public void run() 
        {
            jToggleButton5.setEnabled(enable);
            jToggleButton6.setEnabled(enable);
            jComboBox4.setEnabled(enable);
            
            if(jComboBox4.getSelectedIndex() > 0)
            {
                jComboBox3.setEnabled(enable);
                
                if(enable && dlm_target.getSize() == 0)
                {
                    dlm_target.addElement("Wait for item");
                }
                
            }
            else
            {
                jComboBox3.setEnabled(false);
            }
            
        }};
        
        if(java.awt.EventQueue.isDispatchThread())
        {
            r.run();
        }
        else
        {
            java.awt.EventQueue.invokeLater(r);
        }
    }
    
    protected void managerExecutionException(String from,Exception cex)
    {
        if(!cm.isConnected())
        {
            System.out.println("ERROR 1");
            JOptionPane.showMessageDialog(this, cex.getMessage()+"\nConnexion closed",from,JOptionPane.ERROR_MESSAGE);
        
            java.awt.EventQueue.invokeLater(new Runnable() {
                
                @Override
                public void run() {
                    restart();
                    dlm_target.removeAllElements();
                    dlm_source.removeAllElements();
                    SwingWorker worker = getConnectionWorker();
                    worker.execute();
                }});
            
        }
        else
        {
            System.out.println("ERROR 2");
            JOptionPane.showMessageDialog(this, cex.getMessage(),from,JOptionPane.ERROR_MESSAGE);
        }
        cex.printStackTrace();        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) 
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SimpleControlFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SimpleControlFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SimpleControlFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SimpleControlFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {@Override public void run() {new SimpleControlFrame().setVisible(true);}});
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSlider jSlider3;
    private javax.swing.JSlider jSlider4;
    private javax.swing.JToggleButton jToggleButton4;
    private javax.swing.JToggleButton jToggleButton5;
    private javax.swing.JToggleButton jToggleButton6;
    // End of variables declaration//GEN-END:variables

    @Override
    public void protocoleListUpdated() 
    {
        java.awt.EventQueue.invokeLater(new Runnable() {@Override public void run() {
                
                NetworkProtocol np_selected = null;
                boolean in_the_new_selection = false;
                
                if(jComboBox3.getSelectedIndex() > 0)
                    np_selected = (NetworkProtocol)jComboBox3.getSelectedItem();
                
                dlm_target.removeAllElements();
                
                Collection<NetworkProtocol> cnp = cm.getNetworkProtocoles();
                
                if(cnp.isEmpty())
                {
                    dlm_target.addElement("Wait for item");
                }
                else
                {
                    dlm_target.addElement("Choose an item");
                    for(NetworkProtocol np : cnp)
                    {
                        if(np == np_selected)
                        {
                            in_the_new_selection = true;
                        }
                        
                        dlm_target.addElement(np);
                    }
                    
                    if(in_the_new_selection)
                        dlm_target.setSelectedItem(np_selected);
                }
                
                
            }});
    }

    @Override
    public void errorHasOccured(Exception ex) 
    {
        this.managerExecutionException("Communication manager", ex);
    }

    @Override
    public void autoRefresh(boolean enable) 
    {        
                    java.awt.EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    SwingWorker worker = getConnectionWorker();
                    worker.execute();
                }});
    }

    @Override
    public void coreStateRefresh() 
    {
        System.out.println(""+cm.getState());
        
        if(cm.getState().IS_FILE())
        {
            final double state_bis = (cm.getState().getComplete_tv() == 0)?0:cm.getState().getCurrent_tv()/cm.getState().getComplete_tv()*100;
            
            System.out.println(""+state_bis);
            java.awt.EventQueue.invokeLater(new Runnable() {@Override public void run() {

                if(!captureStarted)
                    jSlider4.setValue(0);
                
                if(cm.getState().IS_READING())
                {
                    jSlider4.setValue((int)state_bis);
                }

                if(cm.getState().IS_FINISHED())
                {
                    System.out.println("FINISHED");
                    captureStarted = false;
                    jButton7.setIcon(iconPlay); /*set play button*/
                    jSlider4.setValue(100);
                }

            }});
        }
    }

    private class ControlFrameMouseAdapter extends MouseAdapter
    {
        String previous;
        
        @Override
        public void mouseEntered(final MouseEvent e) 
        {
            previous = jLabel1.getText();

            if(e.getComponent() == jToggleButton6)/*network*/
            {
                if(jToggleButton6.isSelected())
                    jLabel1.setText("Disable network mode and switch to file mode");
                else
                    jLabel1.setText("Enable network mode");
            }
            else if(e.getComponent() == jToggleButton5) /*file*/
            {
                if(jToggleButton6.isSelected())
                    jLabel1.setText("Disable file mode and switch to network mode");
                else
                    jLabel1.setText("Enable fil mode");
            }
            else if(e.getComponent() == jComboBox3) /*target list*/
            {
                jLabel1.setText("Select a target");
            }
            else if(e.getComponent() == jComboBox4) /*file/interface list*/
            {
                if(jToggleButton5.isSelected())
                    jLabel1.setText("Select a file to read");
                else
                    jLabel1.setText("Select a network interface to listen");
            }
            else if(e.getComponent() == jButton8) /*stop button*/
            {
                jLabel1.setText("Stop the reconstitution");
            }
            else if(e.getComponent() == jButton7) /*play button*/
            {
                if(jButton7.getName().equals("start"))
                    jLabel1.setText("Start the reconstitution");
                else
                    jLabel1.setText("Pause the reconstitution");
            }
            else if(e.getComponent() == jSlider4) /*evolution bar*/
            {
                if(jToggleButton6.isSelected())
                    jLabel1.setText("File evolution bar");
            }
            else if(e.getComponent() == jSlider3) /*speed bar*/
            {
                jLabel1.setText("Select reconstitution speed");
            }
            else if(e.getComponent() == jToggleButton4) /*record button*/
            {
                jLabel1.setText("Record a capture");
            }
            else if(e.getComponent() == jButton3) /*Advanced*/
            {
                jLabel1.setText("Open the advanced window");
            }
            else if(e.getComponent() == jButton4) /*Exit*/
            {
                jLabel1.setText("Quit the program");
            }
        }

        @Override
        public void mouseExited(final MouseEvent e) 
        {
            jLabel1.setText(previous);
        }
    }

    private SwingWorker getConnectionWorker()
    {
        return new SwingWorker<List<String>,Void>() 
        {
            @Override
            protected List<String> doInBackground() throws Exception
            {
                CommunicationManagerV2 com = cm;
                com.connect();
                return com.getSourceList();
            }

            @Override
            public void done() 
            {
                try 
                {
                    List<String> ls = get();
                    dlm_source.removeAllElements();
                    if(ls.isEmpty())
                    {
                        dlm_source.addElement("no source available");
                    }
                    else
                    {
                        dlm_source.addElement("Choose an item");
                        for(String s : ls)
                        {
                            dlm_source.addElement(s);
                        }
                    }
                } 
                catch (InterruptedException ex) {} /*ne surviendra jamais dans le done*/
                catch (ExecutionException ex) 
                {
                    String options[] = {"Retry","Quit"};
                    int n = JOptionPane.showOptionDialog(This,"Connection to core failed : "+ex.getMessage()+"\n\n do you want to retry?","Connexion with core failed",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[0]);
                    if(n == 0)
                    {
                        SwingWorker s = getConnectionWorker();
                        s.execute();
                    }
                    else
                    {
                        System.exit(0);
                    }
                    return;
                }
                
                enableTargetPanel(true);
                jLabel1.setText(JLABEL1_PREFIX+"Connected to the extraction system");
            }
        };
    }

    private SwingWorker getRefreshSourceWorker()
    {
        return new SwingWorker<List<String>,Void>() 
        {
            @Override
            protected List<String> doInBackground() throws Exception
            {
                CommunicationManagerV2 com = cm;
                return com.getSourceList();
            }

            @Override
            public void done() 
            {
                try 
                {
                    dlm_source.removeAllElements();
                    List<String> ls = get();
                    if(ls.isEmpty())
                    {
                        dlm_source.addElement("Wait for item");
                        return;
                    }
                    
                    dlm_source.addElement("Choose an item");
                    for(String s : ls)
                    {
                        dlm_source.addElement(s);
                    }
                } 
                catch (InterruptedException ex) {} /*ne surviendra jamais dans le done*/
                catch (ExecutionException ex) 
                {
                    managerExecutionException("Refresh Source", ex);
                }
                
                jToggleButton5.setEnabled(true);
                jToggleButton6.setEnabled(true);
                jComboBox4.setEnabled(true);
            }
        };
    }

    private SwingWorker getSelectSourceWorker(final String src)
    {
        return new SwingWorker<Void,Void>() 
        {
            @Override
            protected Void doInBackground() throws Exception
            {
                System.out.println("SET SOURCE");
                cm.setSource(src,"tcp");
                System.out.println("SET SOURCE DONE");
                return null;
            }

            @Override
            public void done() 
            {
                try 
                {
                    get();
                } 
                catch (InterruptedException ex) {} /*ne surviendra jamais dans le done*/
                catch (ExecutionException ex) 
                {
                    managerExecutionException("Refresh Source", ex);
                }
                enableTargetPanel(true);
            }
        };
    }

    private SwingWorker getRecordStartWorker(final String filename)
    {
        return new SwingWorker<Void,Void>() 
        {
            @Override
            protected Void doInBackground() throws Exception
            {
                cm.startRecord(filename);
                return null;
            }

            @Override
            public void done() 
            {
                try 
                {
                    get();
                } 
                catch (InterruptedException ex) {} /*ne surviendra jamais dans le done*/
                catch (ExecutionException ex) 
                {
                    managerExecutionException("Refresh Source", ex);
                    
                    if(!cm.getState().IS_READING()
                       && !cm.getState().IS_RECORDING())
                    {
                        enableTargetPanel(true); /*disable choice pannel*/
                    }
                    jToggleButton4.setSelected(false);
                }
                
                enableReader(true); /*unfreeze */
            }
        };
    }
    
    private SwingWorker getRecordStopWorker()
    {
        return new SwingWorker<Void,Void>() 
        {
            @Override
            protected Void doInBackground() throws Exception
            {
                cm.stopRecord();
                return null;
            }

            @Override
            public void done() 
            {
                try 
                {
                    get();
                    
                    if(!cm.getState().IS_RUNNING()
                       && !cm.getState().IS_RECORDING())
                    {
                        enableTargetPanel(true); /*enable choice pannel*/
                    }
                } 
                catch (InterruptedException ex) {} /*ne surviendra jamais dans le done*/
                catch (ExecutionException ex) 
                {
                    managerExecutionException("Refresh Source", ex);
                    jToggleButton4.setSelected(true);
                }
                
                enableReader(true); /*unfreeze */
            }
        };
    }
    
    private SwingWorker getStopWorker()
    {
        return new SwingWorker<Void,Void>() 
        {
            @Override
            protected Void doInBackground() throws Exception
            {
                cm.stopAllCapture();
                cm.stopAllModule();
                return null;
            }

            @Override
            public void done() 
            {
                try 
                {
                    get();
                    jButton7.setIcon(iconPlay); /*set play button*/
                    jSlider4.setValue(0);
                    /*unfreeze the choice pannel*/
                    if(!cm.getState().IS_READING()
                       && !cm.getState().IS_RECORDING())
                    {
                        enableTargetPanel(true);
                    }
                    
                } 
                catch (InterruptedException ex) {} /*ne surviendra jamais dans le done*/
                catch (ExecutionException ex) 
                {
                    managerExecutionException("Refresh Source", ex);
                    jButton8.setEnabled(true); /*re-enable stop button*/
                }
                
                enableReader(true); /*unfreeze */
            }
        };
    }

    private SwingWorker getStartWorker(final NetworkProtocol tp,final Module m)
    {
        return new SwingWorker<Void,Void>() 
        {
            @Override
            protected Void doInBackground() throws Exception
            {
                if(tp != null && !tp.isCaptured())
                {
                    cm.startCapture(tp);
                    cm.startModule(tp,m);
                }
                    
                cm.play();
                
                return null;
            }

            @Override
            public void done() 
            {
                try 
                {
                    get();
                    captureStarted = true;
                    enableTargetPanel(false); /*disable target choice*/
                    jButton7.setIcon(iconPause); /*set pause button*/
                    jButton8.setEnabled(true); /*enable stop*/                    
                } 
                catch (InterruptedException ex) {} /*ne surviendra jamais dans le done*/
                catch (ExecutionException ex) 
                {
                    managerExecutionException("Refresh Source", ex);
                    if(!cm.getState().IS_READING()
                       && !cm.getState().IS_RECORDING())
                    {
                        enableTargetPanel(true); /*disable target choice*/
                    }
                }
                
                enableReader(true); /*unfreeze */
            }
        };
    }
    
    private SwingWorker getResumeWorker()
    {
        return new SwingWorker<Void,Void>() 
        {
            @Override
            protected Void doInBackground() throws Exception
            {
                cm.resume();
                
                return null;
            }

            @Override
            public void done() 
            {
                try 
                {
                    get();
                    enableTargetPanel(false); /*disable target choice*/
                    jButton7.setIcon(iconPause); /*set pause button*/
                    jButton8.setEnabled(true); /*enable stop*/                    
                } 
                catch (InterruptedException ex) {} /*ne surviendra jamais dans le done*/
                catch (ExecutionException ex) 
                {
                    managerExecutionException("Refresh Source", ex);
                    if(!cm.getState().IS_READING()
                       && !cm.getState().IS_RECORDING())
                    {
                        enableTargetPanel(true); /*disable target choice*/
                    }
                }
                
                enableReader(true); /*unfreeze */
            }
        };
    }
    
    private SwingWorker getPauseWorker()
    {
        return new SwingWorker<Void,Void>() 
        {
            @Override
            protected Void doInBackground() throws Exception
            {
                cm.pause();
                return null;
            }

            @Override
            public void done() 
            {
                try 
                {
                    get();
                    jButton7.setIcon(iconPlay); /*set play button*/
                } 
                catch (InterruptedException ex) {} /*ne surviendra jamais dans le done*/
                catch (ExecutionException ex) 
                {
                    managerExecutionException("Refresh Source", ex);
                }
                
                enableReader(true); /*unfreeze */
            }
        };
    }
    
    private SwingWorker getSpeedWorker(final int speed)
    {
        return new SwingWorker<Void,Void>() 
        {
            @Override
            protected Void doInBackground() throws Exception
            {
                cm.setSpeed(speed);
                return null;
            }

            @Override
            public void done() 
            {
                try 
                {
                    get();
                } 
                catch (InterruptedException ex) {} /*ne surviendra jamais dans le done*/
                catch (ExecutionException ex) 
                {
                    managerExecutionException("Refresh Source", ex);
                }
                
                enableReader(true); /*unfreeze */
            }
        };
    }

    private SwingWorker getEvolutionWorker(final int percent)
    {
        return new SwingWorker<Void,Void>() 
        {
            @Override
            protected Void doInBackground() throws Exception
            {
                cm.gotoPercent(percent);
                return null;
            }

            @Override
            public void done() 
            {
                try 
                {
                    get();
                    jSlider4.setValue(percent);
                } 
                catch (InterruptedException ex) {} /*ne surviendra jamais dans le done*/
                catch (ExecutionException ex) 
                {
                    managerExecutionException("Evolution bar set", ex);
                }
                
                enableReader(true); /*unfreeze reader*/
            }
        };
    }

}
