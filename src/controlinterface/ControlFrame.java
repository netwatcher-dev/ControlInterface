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

import controlinterface.dialog.MasterFilter;
import controlinterface.dialog.NewStreamDialog;
import controlinterface.dialog.ParameterDialog;
import controlinterface.dialog.filterDialog;
import dataStruct.AbstractProtocolCaptured;
import dataStruct.NetworkProtocol;
import dataStruct.TransportProtocol;
import exceptionPackage.ControlException;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import util.HTTPModule;
import util.Module;
import wrapper.CommunicationManagerV2;
import wrapper.CoreEvent;


public class ControlFrame extends javax.swing.JFrame implements UncaughtExceptionHandler, CoreEvent
{
    private static final String START_RECONSTITUTION = "Start reconstitution";
    private static final String STOP_RECONSTITUTION = "Stop reconstitution";
    private static final String HAVE_TO_BE_CONNECTED = "You have to be connected to the extraction system";
    private static final String PROTOCOL_CURRENTLY_DETECTED = "Protocol currently detected";
    private static final String PROTOCOL_RECENTLY_DETECTED = "Protocol recently detected";
    private static final String PROTOCOL_NOT_DETECTED = "Protocol not detected";
    private static final String PROTOCOL_NOT_SUPPORTED = "Protocol not supported";
    private static final String WANT_TO_LAUNCH_APP = "Do you want to launch the application ?";
    private static final String LAUNCH_APP = "Reconstitution";
    private static final String STOP_APP = "Stop";
    private static final String PAUSE = "Pause";
    private static final String RESUME = "Resume";
    private static final String RECORD = "Record";
    private static final String STOP_RECORD = "Stop recording";
    private static final String CORE_NOT_AVAILABLE = "Contacting the core is impossible";
    private static final String IP_FORMAT = "IP: incorrect format";
    private static final String PORT_FORMAT = "PORT: incorrect format";
    private static final String REFRESH_FORMAT = "Refresh interval: incorrect format";
    private static final String INTERFACE_LIST = "Interface list:";
    private static final String FILE_LIST = "File list:";
    private static final String NEW_IP = "Enter a new ip:";
    private static final String RECORD_FILENAME = "Enter the filename";
    private static final String BAD_FILENAME = "Filename not valid";
    
    private DefaultListModel dlm1; /* Interfaces listing, cfr jList1 */
    private DefaultListModel dlm2; /* Streams listing, cfr jList2 */
    private DefaultListModel dlm3; /* Protocol listing, cfr jList3 */
    private javax.swing.JFrame This;
    
    private final ImageIcon iconPlay = new ImageIcon(getClass().getResource("/controlinterface/icons/media_play.png"));
    private final ImageIcon iconPause = new ImageIcon(getClass().getResource("/controlinterface/icons/media_pause.png"));
    private final ImageIcon iconConnect = new ImageIcon(getClass().getResource("/controlinterface/icons/plug_24.png"));
    private final ImageIcon iconDisconnect = new ImageIcon(getClass().getResource("/controlinterface/icons/plug_delete_24.png"));
    
    private CommunicationManagerV2 cm;
    private boolean captureStarted;
    
    /** Creates new form ControlFrame */
    public ControlFrame(boolean checkState) 
    {
        Thread.setDefaultUncaughtExceptionHandler(this);
        
        /*init default */
        dlm1 = new DefaultListModel();        
        dlm2 = new DefaultListModel();
        dlm3 = new DefaultListModel();
        
        initComponents();  
        
        cm = CommunicationManagerV2.getInstance();
        cm.addListener(this);

        This = this;
        captureStarted = false;
        
        if(checkState)
        {
            if(cm.isConnected())
            {
                jToggleButton1.setSelected(true);
                jToggleButton1.setIcon(iconDisconnect);
                enableUtilButton(true);
                                
                
                /*if(cm.getState().IS_FILE())
                {
                    jCheckBox2.setSelected(false);
                }
                */
                
                cm.getState().setFile(false);
                cm.getState().setStream(true);
                
                getRefreshSourceWorker(null, true).execute();
            }
        }
        else
        {
            cm.getState().setFile(false);
            cm.getState().setStream(true);
        }
        cm.setFilters(filterDialog.getDefaultFilter());
    }

    /**
      * Initialisation
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList3 = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jToolBar2 = new javax.swing.JToolBar();
        jToggleButton1 = new javax.swing.JToggleButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jButton3 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jCheckBox2 = new javax.swing.JCheckBox();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        jCheckBox1 = new javax.swing.JCheckBox();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        jButton6 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jSlider2 = new javax.swing.JSlider();
        jPanel2 = new javax.swing.JPanel();
        jSlider1 = new javax.swing.JSlider();
        jToggleButton2 = new javax.swing.JToggleButton();
        jPanel4 = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jList1.setModel(dlm1);
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList1.setEnabled(false);
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jList1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jList1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jList2.setModel(dlm2);
        jList2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList2.setEnabled(false);
        jList2.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList2ValueChanged(evt);
            }
        });
        jList2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jList2KeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jList2);

        jList3.setModel(dlm3);
        jList3.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList3.setCellRenderer(new MyIconListRenderer());
        jList3.setEnabled(false);
        jList3.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList3ValueChanged(evt);
            }
        });
        jList3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jList3KeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(jList3);

        jLabel1.setText("Interface list:");

        jLabel2.setText("Protocol list:");

        jLabel3.setText("Stream list:");

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);

        jToggleButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/controlinterface/icons/plug_24.png"))); // NOI18N
        jToggleButton1.setFocusable(false);
        jToggleButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });
        jToolBar2.add(jToggleButton1);
        jToolBar2.add(jSeparator2);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/controlinterface/icons/refresh_24.png"))); // NOI18N
        jButton3.setEnabled(false);
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton3);
        jToolBar2.add(jSeparator3);

        jCheckBox2.setSelected(true);
        jCheckBox2.setText("Real time");
        jCheckBox2.setEnabled(false);
        jCheckBox2.setFocusable(false);
        jCheckBox2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jCheckBox2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jCheckBox2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });
        jToolBar2.add(jCheckBox2);
        jToolBar2.add(jSeparator4);

        jCheckBox1.setSelected(true);
        jCheckBox1.setText("Auto-refresh");
        jCheckBox1.setToolTipText("Allow to refresh automatically the stream list and protocol list");
        jCheckBox1.setEnabled(false);
        jCheckBox1.setFocusable(false);
        jCheckBox1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jCheckBox1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jCheckBox1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        jToolBar2.add(jCheckBox1);
        jToolBar2.add(jSeparator5);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/controlinterface/icons/funnel_add_24.png"))); // NOI18N
        jButton6.setEnabled(false);
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setName("filter_add"); // NOI18N
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton6);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/controlinterface/icons/media_stop.png"))); // NOI18N
        jButton4.setEnabled(false);
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/controlinterface/icons/media_play.png"))); // NOI18N
        jButton1.setEnabled(false);
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setName("Pause"); // NOI18N
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jSlider2.setValue(0);
        jSlider2.setEnabled(false);
        jSlider2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jSlider2MouseReleased(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jButton4)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButton1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jSlider2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jSlider2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .add(jButton1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jButton4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jSlider1.setMajorTickSpacing(50);
        jSlider1.setMinimum(-100);
        jSlider1.setMinorTickSpacing(10);
        jSlider1.setPaintLabels(true);
        jSlider1.setPaintTicks(true);
        jSlider1.setSnapToTicks(true);
        jSlider1.setValue(0);
        jSlider1.setEnabled(false);
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }
        });

        jToggleButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/controlinterface/icons/rec.png"))); // NOI18N
        jToggleButton2.setEnabled(false);
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jToggleButton2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jSlider1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jSlider1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 38, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jToggleButton2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 38, Short.MAX_VALUE))
                .addContainerGap())
        );

        jButton8.setText("Reconsitution");
        jButton8.setEnabled(false);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .add(jButton8)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel7, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel4Layout.createSequentialGroup()
                .add(20, 20, 20)
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel7, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jButton8))
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .add(jPanel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jMenu1.setText("File");

        jMenuItem1.setText("Quit");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        jMenuItem2.setText("Preferences");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItem3.setText("Filtering");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuItem4.setText("Master filter");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jToolBar2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 878, Short.MAX_VALUE)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel1)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel3)
                    .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel2)
                    .add(jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE))
                .add(20, 20, 20)
                .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jToolBar2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(jLabel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jLabel2))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                    .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /* PLAY/PAUSE BUTTON */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(!cm.getState().IS_PARSED())
        {
            JOptionPane.showMessageDialog(this,"The file is not yet parsed, please wait and retry","Parsing file",JOptionPane.WARNING_MESSAGE);
            return;
        }

        enableUtilButton(false); /*freeze the reader pannel*/
        
        if(!captureStarted) /*si pas de capture en cours*/
        {
            jSlider2.setValue(0);
            /*c'est le premier start, il n'y a pas encore eu de pause*/
            getStartWorker().execute();
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
            
    }//GEN-LAST:event_jButton1ActionPerformed

    /* QUIT MENU */
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    /* RECORD BUTTON ACTION */
    /* REFRESH BUTTON ACTION*/
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        enableUtilButton(false);
        getRefreshWorker().execute();
    }//GEN-LAST:event_jButton3ActionPerformed
     
    /* CONNECTION BUTTON */
    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        enableUtilButton(false);
        getConnectionWorker(jToggleButton1.isSelected()).execute();
    }//GEN-LAST:event_jToggleButton1ActionPerformed
    
    /* PREFERENCES MENU */
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        ParameterDialog.showDialog(this);
    }//GEN-LAST:event_jMenuItem2ActionPerformed
    
    /* REFRESH INTERVAL PREFERENCE JDIALOG */
    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        cm.setAutoRefresh(jCheckBox1.isSelected());
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    /*  LIVE MODE CHECKBOX*/
    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed

        enableUtilButton(false);
        
        cm.setRefreshCoreState(false); 
        cm.setAutoRefresh(false); /*stop auto refresh*/
        
        cm.getState().setStream(jCheckBox2.isSelected());
        cm.getState().setFile(!jCheckBox2.isSelected());
        
        this.clearJlist();
        getRefreshSourceWorker(null,true).execute(); 
    }//GEN-LAST:event_jCheckBox2ActionPerformed
    
    /* ADD A NEW IP IN STREAM LIST */
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        NewStreamDialog.showDialog(this);
    }//GEN-LAST:event_jButton6ActionPerformed

    /* START/STOP CAPTURE */
    /*run module*/
    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        AbstractProtocolCaptured apc;
        
        if(!jList3.isSelectionEmpty())
        {
            apc = (AbstractProtocolCaptured)jList3.getSelectedValue();
        }
        else if(!jList2.isSelectionEmpty())
        {
            apc = (AbstractProtocolCaptured)jList2.getSelectedValue();
        }
        else
        {
            return;
        }        
        
        if(apc.isCaptured())
        {
            
            enableUtilButton(false);
            getStopCaptureWorker(apc).execute();
        }
        else
        {
            FilenameFilter ff = new FilenameFilter() {@Override public boolean accept(File file, String name) 
            {
                return !name.startsWith(".") && name.endsWith(".jar") && !name.startsWith("control");
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
            
            Module m = new HTTPModule(s);

            
            enableUtilButton(false);
            getStartCaptureWorker(apc,m).execute();
        }
        
    }//GEN-LAST:event_jButton8ActionPerformed
    
    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        if(evt.getValueIsAdjusting())
            return;

        enableUtilButton(false);
        
        String source = (String)jList1.getSelectedValue();
        if(source != null)
        {
            cm.removeAllProtocol();
            dlm2.clear();
            dlm3.clear();
            getSelectSourceWorker(source).execute();
        }
    }//GEN-LAST:event_jList1ValueChanged

    /*Value change in JLIST 2 (IP)*/
    private void jList2ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList2ValueChanged
        if(evt.getValueIsAdjusting())
            return;
        
        fillTransportList();
        
        refreshStatusPanel( (NetworkProtocol) jList2.getSelectedValue());
    }//GEN-LAST:event_jList2ValueChanged

    /*Value change in JLIST 3 (TCP/UDP)*/
    private void jList3ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList3ValueChanged
        if(evt.getValueIsAdjusting())
            return;
        
        refreshStatusPanel( (TransportProtocol) jList3.getSelectedValue());
    }//GEN-LAST:event_jList3ValueChanged

    private void refreshStatusPanel(AbstractProtocolCaptured apc)
    {
        if(apc != null)
        {
            short status = apc.getStatus(System.currentTimeMillis()/1000);
            if(status == TransportProtocol.STATUS_DETECTED)
            {
                jLabel7.setText(PROTOCOL_CURRENTLY_DETECTED);
                jLabel7.setForeground(new Color(0, 150, 0));  // green
            }
            else if(status == TransportProtocol.STATUS_RECENT_DETECTED)
            {
                jLabel7.setText(PROTOCOL_RECENTLY_DETECTED); 
                jLabel7.setForeground(new Color(255, 100, 0)); // orange
            }          
            else
            {
                jLabel7.setText(PROTOCOL_NOT_DETECTED); // red
                jLabel7.setForeground(Color.red);
            }

            if(apc.isCaptured())
            {
                jButton8.setText(STOP_APP);
            }
            else
            {
                jButton8.setText(LAUNCH_APP);
            }

            jButton8.setEnabled(true);
        }
        else
        {
            jLabel7.setText("");
            jButton8.setText(LAUNCH_APP);
            jButton8.setEnabled(false);
        }
    }
    
    private void jSlider1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider1StateChanged

        if(!jSlider1.getValueIsAdjusting())
        {
            enableUtilButton(false); /*freeze the reader*/
            getSpeedWorker(jSlider1.getValue()).execute();
        }
        
    }//GEN-LAST:event_jSlider1StateChanged

    private void jList1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList1KeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_RIGHT || evt.getKeyCode() == KeyEvent.VK_KP_RIGHT)
        {
            jList2.requestFocusInWindow();
            if(jList2.getSelectedValue() == null && jList2.getModel().getSize() > 0)
            {
                jList2.setSelectedIndex(0);
            }
        }
        else if(evt.getKeyCode() == KeyEvent.VK_LEFT || evt.getKeyCode() == KeyEvent.VK_KP_LEFT)
        {
            jList3.requestFocusInWindow();
            if(jList3.getSelectedValue() == null && jList3.getModel().getSize() > 0)
            {
                jList3.setSelectedIndex(0);
            }
        }
    }//GEN-LAST:event_jList1KeyPressed

    private void jList2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList2KeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_RIGHT || evt.getKeyCode() == KeyEvent.VK_KP_RIGHT)
        {
            jList3.requestFocusInWindow();
            if(jList3.getSelectedValue() == null && jList3.getModel().getSize() > 0)
            {
                jList3.setSelectedIndex(0);
            }
        }
        else if(evt.getKeyCode() == KeyEvent.VK_LEFT || evt.getKeyCode() == KeyEvent.VK_KP_LEFT)
        {
                jList1.requestFocusInWindow();
        }
    }//GEN-LAST:event_jList2KeyPressed

    private void jList3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList3KeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_RIGHT || evt.getKeyCode() == KeyEvent.VK_KP_RIGHT)
        {
            jList1.requestFocusInWindow();
        }
        else if(evt.getKeyCode() == KeyEvent.VK_LEFT || evt.getKeyCode() == KeyEvent.VK_KP_LEFT)
        {
            jList2.requestFocusInWindow();
            if(jList2.getSelectedValue() == null && jList2.getModel().getSize() > 0)
            {
                jList2.setSelectedIndex(0);
            }
        }
    }//GEN-LAST:event_jList3KeyPressed

    /*STOP BUTTON*/
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        this.enableUtilButton(false);
        captureStarted = false;
        this.getStopWorker().execute(); /*execute the stop action*/
    }//GEN-LAST:event_jButton4ActionPerformed

    /*RECORD BUTTON*/
    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
        if(this.jToggleButton2.isSelected())
        {
            String inputValue = JOptionPane.showInputDialog(this,"Please input a record name", "Create a record...",JOptionPane.QUESTION_MESSAGE);
            
            if(inputValue == null)
            {
                jToggleButton2.setSelected(false);
                return;
            }
            
            /*add cap extension if not present*/
            if(!inputValue.endsWith(".cap"))
            {
                inputValue = inputValue + ".cap";
            }
            
            enableUtilButton(false); /*on verrouille l'interface*/
            this.getRecordStartWorker(inputValue).execute();
        }
        else
        {
            enableUtilButton(false);
            getRecordStopWorker().execute();
        }
    }//GEN-LAST:event_jToggleButton2ActionPerformed

    /*SELECT POSITION ON EVOLUTION BAR*/
    private void jSlider2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSlider2MouseReleased
        if(!jSlider2.isEnabled())
            return;
        
        if(!this.captureStarted)
        {
            if(cm.getState().IS_FINISHED())
                jSlider2.setValue(100);
            else
                jSlider2.setValue(0);
            return;
        }
            
            
        if(cm.getState().IS_PARSED())
        {
            enableUtilButton(false); /*freeze the reader*/
            getEvolutionWorker(jSlider2.getValue()).execute();
        }
        else
        {
            JOptionPane.showMessageDialog(this,"The file is not yet parsed, please wait and retry","Parsing file",JOptionPane.WARNING_MESSAGE);
            jSlider2.setValue(0);
        }
    }//GEN-LAST:event_jSlider2MouseReleased

    /*filtering menu*/
    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        new filterDialog(this, true).setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        new MasterFilter(this, true).setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    @Override
    public void protocoleListUpdated() 
    {
        java.awt.EventQueue.invokeLater(new Runnable() { @Override public void run() {
            enableJlist(false);
            fillNetworkList();
            enableJlist(true);
            jCheckBox1.setSelected(true);
        }});
    }

    @Override
    public void errorHasOccured(final Exception ex) 
    {
        if(ex instanceof ControlException)
        {
            java.awt.EventQueue.invokeLater(new Runnable() { @Override public void run() {
                managerControlException("Auto Refresh",(ControlException)ex);
            }});
        }
        else if(ex instanceof IOException)
        {
            java.awt.EventQueue.invokeLater(new Runnable() { @Override public void run() {
                managerIOException("Auto Refresh",(IOException)ex);
            }});
        }
    }

    @Override
    public void autoRefresh(final boolean enable) 
    {
        java.awt.EventQueue.invokeLater(new Runnable() { @Override public void run() {
            jCheckBox1.setSelected(enable);
        }});
    }
    
    /* FREEZE BUTTON */    
    private void enableUtilButton(boolean enable)
    {        
        jToggleButton1.setEnabled(enable); /*connexion button*/
        
        jButton3.setEnabled(enable); /*refresh button*/
        jButton6.setEnabled(enable); /*filter button*/
        
        jCheckBox1.setEnabled(enable); /*auto refresh*/
        jCheckBox2.setEnabled(enable); /*live mode*/
        
        if(cm.getState().IS_FILE() && !jList1.isSelectionEmpty())
        {
            jButton1.setEnabled(enable); /*start/stop*/
            jButton4.setEnabled(enable); /*stop button*/
            jSlider2.setEnabled(enable); /*evolution bar*/
        }
        else
        {
            jButton1.setEnabled(false); /*start/stop*/
            jButton4.setEnabled(false); /*stop button*/
            jSlider2.setEnabled(false); /*evolution bar*/
        }
        
        if(cm.getState().IS_STREAM() && cm.getState().IS_RUNNING())
        {
            jToggleButton2.setEnabled(enable); /*record file*/
            
        }
        else
        {
            jToggleButton2.setEnabled(false); /*record file*/
        }
        
        if((cm.getState().IS_FILE() && !jList1.isSelectionEmpty()) || (cm.getState().IS_STREAM() && cm.getState().IS_RUNNING()))
        {
            jSlider1.setEnabled(enable);
        }
        else
        {
            jSlider1.setEnabled(false);
        }
        
        
        enableJlist(enable);
        
        if(!jList3.isSelectionEmpty())
        {
            refreshStatusPanel((AbstractProtocolCaptured)jList3.getSelectedValue());
        }
        else if(!jList2.isSelectionEmpty())
        {
            refreshStatusPanel((AbstractProtocolCaptured)jList2.getSelectedValue());
        }
        else
        {
            refreshStatusPanel(null);
        }
    }
    
/*###########################################################################*/
/*########## LIST MANAGER ###################################################*/    
/*###########################################################################*/
    
    private boolean canEnabledJlist1()
    {
        return !(cm.isCaptureInProgress() || cm.getState().IS_READING() || cm.getState().IS_RECORDING());
    }
    
    private void enableJlist(boolean enable)
    {
        if(canEnabledJlist1())
            jList1.setEnabled(enable);
        else
            jList1.setEnabled(false);
        
        jList2.setEnabled(enable);
        jList3.setEnabled(enable);
    }
    
    private void clearJlist()
    {
        System.out.println("clearJlist");
        dlm1.clear();
        dlm2.clear();
        dlm3.clear();
    }
    
    private void fillSourceList(List<String> list,String selected)
    {
        System.out.println("fillSourceList");
        boolean se = false;
        dlm1.clear();
        for(String s : list /*CommunicationManagerV2.getInstance().getSourceList()*/)
        {
            if(selected != null && selected.equals(s))
                se = true;
            
            dlm1.addElement(s);
        }
        
        if(se)
        {
            jList1.setSelectedValue(selected, true);
        }
        
        fillNetworkList();
    }
    
    private void fillNetworkList()
    {
        NetworkProtocol selected = (NetworkProtocol)jList2.getSelectedValue();
        boolean stillThere = false;
        boolean focus = jList2.hasFocus();
        dlm2.clear(); /* Cleaning the list */
        for(NetworkProtocol np : cm.getNetworkProtocoles())
        {   
            if(np == selected)
                stillThere = true;

            dlm2.addElement(np);
        }
        
        if(stillThere)
        {
            jList2.setSelectedValue(selected, true);
            fillTransportList();
        }
        
        if(focus)
        {
            jList2.requestFocusInWindow();
        }
    }
    
    private void fillTransportList()
    {
        TransportProtocol selected = (TransportProtocol)jList3.getSelectedValue();
        boolean stillThere = false;
        boolean focus = jList3.hasFocus();
        
        NetworkProtocol np = (NetworkProtocol)jList2.getSelectedValue();
        if(np != null)
        {
            dlm3.clear(); /* Cleaning the list */
            
            /*TODO faire un synchronized ou quelque chose ici*/
            for(TransportProtocol tp : np.getEncapsulatedProtocolesSet().values())
            {   
                if(selected == tp)
                    stillThere = true;

                dlm3.addElement(tp);
            }
        }
        
        if(stillThere)
        {
            jList3.setSelectedValue(selected, true);
        }
        
        if(focus)
        {
            jList3.requestFocusInWindow();
        }
    }
    
/*###########################################################################*/
/*########## ERROR MANAGER ##################################################*/    
/*###########################################################################*/

    private void managerIOException(String from,IOException ioex)
    {        
        if(!cm.isConnected())
        {
            enableUtilButton(false);
            JOptionPane.showMessageDialog(this, ioex.getMessage()+"\nConnexion closed",from,JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            JOptionPane.showMessageDialog(this, ioex.getMessage(),from,JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    private void managerControlException(String from,ControlException cex)
    {
        JOptionPane.showMessageDialog(this, cex.getMessage(),from,JOptionPane.WARNING_MESSAGE);
    }
    
    @Override
    public void uncaughtException(Thread thread, Throwable thrwbl) 
    {
        thrwbl.printStackTrace();
        JOptionPane.showMessageDialog(this, thrwbl.toString(),"Exception",JOptionPane.ERROR_MESSAGE);
        if(CommunicationManagerV2.getInstance().isConnected())
            this.enableUtilButton(false);
    }
    
    protected void managerExecutionException(String from,ExecutionException cex)
    {
        JOptionPane.showMessageDialog(this, cex.getMessage(),from,JOptionPane.ERROR_MESSAGE);
        cex.printStackTrace();
        
        if(!cm.isConnected())
        {
            enableUtilButton(false);
            jToggleButton1.setIcon(iconDisconnect);
            jToggleButton1.setSelected(false);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try 
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) 
            {
                if ("Nimbus".equals(info.getName())) 
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ControlFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ControlFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ControlFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ControlFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                new ControlFrame(false).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton8;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JList jList3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JSlider jSlider2;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JToolBar jToolBar2;
    // End of variables declaration//GEN-END:variables

    @Override
    public void coreStateRefresh() 
    {
        System.out.println(""+cm.getState());
        
        if(cm.getState().IS_FILE())
        {
            final double state_bis = (cm.getState().getComplete_tv() == 0)?0:cm.getState().getCurrent_tv()/cm.getState().getComplete_tv()*100;
            
            System.out.println(""+state_bis);
            java.awt.EventQueue.invokeLater(new Runnable() {@Override public void run() {

                if(cm.getState().IS_READING())
                {
                    jSlider2.setValue((int)state_bis);
                }

                if(cm.getState().IS_FINISHED())
                {
                    System.out.println("FINISHED");
                    captureStarted = false;
                    jButton1.setIcon(iconPlay); /*set play button*/
                    jSlider2.setValue(100);
                }

            }});
        }
    }
    
    private SwingWorker getConnectionWorker(final boolean enable)
    {
        return new SwingWorker<List<String>,Void>() 
        {
            @Override
            protected List<String> doInBackground() throws Exception
            {
                if(enable)
                {
                    cm.connect();
                    return cm.getSourceList();
                }
                else
                {
                    cm.disconnect();
                    return new LinkedList<String>();
                }
                
            }

            @Override
            public void done() 
            {
                clearJlist();
                
                if(enable)
                {
                    try 
                    {
                        
                        List<String> ls = get();
                        dlm1.removeAllElements();
                        if(!ls.isEmpty())
                        {
                            for(String s : ls)
                            {
                                dlm1.addElement(s);
                            }
                        }
                    } 
                    catch (InterruptedException ex) {} /*ne surviendra jamais dans le done*/
                    catch (ExecutionException ex) 
                    {
                        JOptionPane.showMessageDialog(This,"Connexion failed, check connexion parameter : "+ex.getMessage(),"Connexion failed",JOptionPane.WARNING_MESSAGE);
                        jToggleButton1.setSelected(false);
                        jToggleButton1.setEnabled(false);
                        return;
                    }

                    jToggleButton1.setIcon(iconDisconnect);
                    enableUtilButton(true);
                }
                else
                {
                    jToggleButton1.setIcon(iconConnect);
                }
                jToggleButton1.setEnabled(true);
            }
        };
    }

    private SwingWorker getRefreshSourceWorker(final String selected,final boolean reset)
    {
        return new SwingWorker<List<String>,Void>() 
        {
            @Override
            protected List<String> doInBackground() throws Exception
            {
                if(reset)
                {
                    cm.setSource(null, null);
                    cm.stopAllCapture();
                    cm.stopAllModule();
                    cm.removeAllProtocol();
                }
                
                return cm.getSourceList();
            }

            @Override
            public void done() 
            {
                try 
                {
                    fillSourceList(get(),selected);
                } 
                catch (InterruptedException ex) {} /*ne surviendra jamais dans le done*/
                catch (ExecutionException ex) 
                {
                    managerExecutionException("Refresh Source", ex);
                    return;
                }
                
                enableUtilButton(true);
            }
        };
    }
    
    private SwingWorker getRefreshWorker()
    {
        return new SwingWorker<Void,Void>() 
        {
            @Override
            protected Void doInBackground() throws Exception
            {
                cm.refreshProtocolList();
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
                    managerExecutionException("Evolution bar set", ex);
                    return;
                }
                enableUtilButton(true);
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
                
                enableUtilButton(true); /*unfreeze */
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
                    jToggleButton2.setSelected(false);
                }
                
                enableUtilButton(true); /*unfreeze */
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
                } 
                catch (InterruptedException ex) {} /*ne surviendra jamais dans le done*/
                catch (ExecutionException ex) 
                {
                    managerExecutionException("Refresh Source", ex);
                    jToggleButton2.setSelected(true);
                }
                
                enableUtilButton(true); /*unfreeze */
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
                cm.getCm().fileStop();
                return null;
            }

            @Override
            public void done() 
            {
                try 
                {
                    get();
                    
                    jButton1.setIcon(iconPlay); /*set play button*/
                    jSlider2.setValue(0);
                } 
                catch (InterruptedException ex) {} /*ne surviendra jamais dans le done*/
                catch (ExecutionException ex) 
                {
                    managerExecutionException("Refresh Source", ex);
                    jButton4.setEnabled(true); /*re-enable stop button*/
                }
                
                enableUtilButton(true);
            }
        };
        
    }
    
    
    private SwingWorker getStartWorker()
    {
        return new SwingWorker<Void,Void>() 
        {
            @Override
            protected Void doInBackground() throws Exception
            {  
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
                    jButton1.setIcon(iconPause); /*set pause button*/
                    jButton4.setEnabled(true); /*enable stop*/                    
                } 
                catch (InterruptedException ex) {} /*ne surviendra jamais dans le done*/
                catch (ExecutionException ex) 
                {
                    managerExecutionException("Refresh Source", ex);
                }
                
                enableUtilButton(true); /*unfreeze */
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
                    jButton1.setIcon(iconPause); /*set pause button*/
                } 
                catch (InterruptedException ex) {} /*ne surviendra jamais dans le done*/
                catch (ExecutionException ex) 
                {
                    managerExecutionException("Refresh Source", ex);
                }
                
                enableUtilButton(true); /*unfreeze */
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
                    jButton1.setIcon(iconPlay); /*set play button*/
                } 
                catch (InterruptedException ex) {} /*ne surviendra jamais dans le done*/
                catch (ExecutionException ex) 
                {
                    managerExecutionException("Refresh Source", ex);
                }
                
                enableUtilButton(true); /*unfreeze */
            }
        };
    }
    
    private SwingWorker getStartCaptureWorker(final AbstractProtocolCaptured epc, final Module m)
    {
        return new SwingWorker<Void,Void>() 
        {
            @Override
            protected Void doInBackground() throws Exception
            {
                cm.startCapture(epc);
                cm.startModule(epc,m);
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
                
                enableUtilButton(true); /*unfreeze */
            }
        };
    }
    
    private SwingWorker getStopCaptureWorker(final AbstractProtocolCaptured epc)
    {
        return new SwingWorker<Void,Void>() 
        {
            @Override
            protected Void doInBackground() throws Exception
            {
                cm.stopCapture(epc);
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
                
                enableUtilButton(true); /*unfreeze */
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
                    jSlider2.setValue(percent);
                } 
                catch (InterruptedException ex) {} /*ne surviendra jamais dans le done*/
                catch (ExecutionException ex) 
                {
                    managerExecutionException("Evolution bar set", ex);
                }
                
                enableUtilButton(true); /*unfreeze reader*/
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
                cm.setSource(src,null);
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
                enableUtilButton(true);
            }
        };
    }
}

