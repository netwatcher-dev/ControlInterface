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

package wrapper;

import dataStruct.AbstractProtocolCaptured;
import dataStruct.CoreState;
import util.ParameterLoader;
import dataStruct.NetworkProtocol;
import dataStruct.TransportProtocol;
import exceptionPackage.ControlException;
import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import util.DataFiltering;
import util.Module;

public class CommunicationManagerV2 
{
    private static CommunicationManagerV2 self;
    private Socket socket;
    private ControlMethod cm;
    private List<CoreEvent> object_to_call;
    private boolean autoRefresh; 
    //private boolean notYetStarted;
    
    private boolean refreshTarget, refreshCoreState;
    private Thread refresh;
    private Map<NetworkProtocol,NetworkProtocol> networkProtocolSet;
    private String source;
    private CoreState state;
    private List<Module> modules;
    private List<DataFiltering> filters;
    
    private CommunicationManagerV2()
    {
        this.cm = new ControlMethod();
        this.socket = null;
        this.object_to_call = new LinkedList<CoreEvent>();
        this.autoRefresh = true;
        this.refresh = null;
        networkProtocolSet = new HashMap<NetworkProtocol, NetworkProtocol>();
        source = null;
        refreshTarget = true;
        refreshCoreState = false;
        
        state = new CoreState(0, 0, 0);
        
        modules = new LinkedList<Module>();
        
        filters = new LinkedList<DataFiltering>();
        
        /*routine de sortie*/
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() 
        {@Override public void run() {disconnect();}}));
    }
    
    public List<String> getSourceList() throws ControlException, IOException
    {
        try 
        {
            System.out.println("getSourceList + "+state);
            if(state.IS_STREAM())
            {
                System.out.println("cm.listInterfaces()");
                return cm.listInterfaces();
            }
            else if(state.IS_FILE())
                return cm.listFiles();
            else
                return new LinkedList<String>();
        } 
        catch (IOException ex) 
        {
            disconnect();
            throw ex;
        }  
    }
    
    public void setSource(String source, String masterFilter) throws ControlException, IOException
    {        
        System.out.println("setSource : "+state);
        if(source == null)
        {
            removeAllProtocol();
            try 
            {
                
                cm.disableCurrentDevice();
                cm.clearProtoList();
                cm.setSpeed(0);
                this.source = null;
                
                setAutoRefresh(false);
                setRefreshCoreState(false);
                
                if(state.IS_FILE())
                    state.setState(CoreState.FILE);
                else if(state.IS_STREAM())
                    state.setState(CoreState.STREAM);
                else
                    state.setState(0);
            } 
            catch (IOException ex) 
            {
                disconnect();
                throw ex;
            }
            
            return;
        }
        else if(this.source != null && this.source.equals(source))
        {
            return;
        }
        
        removeAllProtocol();
        try 
        {
            cm.disableCurrentDevice();
            cm.clearProtoList();
            cm.setSpeed(0);
            
            if(state.IS_STREAM())
            {
                state.setState(CoreState.STREAM); /*reinit state*/
                
                cm.selectDevice(source);
                
                if(masterFilter != null && masterFilter.length() > 0)
                    cm.setMasterFilter(masterFilter);
                                
                setRefreshTarget(true);
                setRefreshCoreState(false);
                setAutoRefresh(true);
                
                this.source = source;
                
                state.setRunning(true);
            }
            else if(state.IS_FILE())
            {
                state.setState(CoreState.FILE); /*reinit state*/
                
                cm.selectFile(source);
                if(masterFilter != null && masterFilter.length() > 0)
                    cm.setMasterFilter(masterFilter);
                cm.parseFile();
                
                setRefreshTarget(true);
                setRefreshCoreState(true);
                setAutoRefresh(true);
                
                this.source = source;
            }
        } 
        catch (IOException ex) 
        {
            disconnect();
            throw ex;
        }  
    }
    
    public synchronized void setAutoRefresh(boolean enable)
    {
        if(enable)
        {
            autoRefresh = true;
            if(this.refresh == null)
            {
                this.refresh = new Thread(new Runnable() 
                {
                    @Override
                    public void run() 
                    {
                        while(autoRefresh)
                        {
                            if(refreshTarget)
                            {
                                removeOldProtocol();
                                try 
                                {
                                    refreshProtocolList();
                                    
                                } 
                                catch (IOException ex) 
                                {
                                    autoRefresh = false;
                                    fireAutorefresh(false);
                                    fireError(ex);
                                } 
                                catch (ControlException ex) 
                                {
                                    autoRefresh = false;
                                    fireAutorefresh(false);
                                    fireError(ex);
                                }
                                
                            }
                            
                            if(refreshCoreState)
                            {
                                try
                                {
                                    state.copy(cm.getInfo());
                                    fireCoreState();
                                }
                                catch (IOException ex) 
                                {
                                    autoRefresh = false;
                                    fireAutorefresh(false);
                                    fireError(ex);
                                } 
                                catch (ControlException ex) 
                                {
                                    autoRefresh = false;
                                    fireAutorefresh(false);
                                    fireError(ex);
                                }
                            }
                            
                            try
                            {
                                Thread.sleep(ParameterLoader.getInstance().getRefreshInterval()*1000);
                            }
                            catch (InterruptedException ex){}
                        }
                    }
                });
                this.refresh.start();
            }
        }
        else
        {
            autoRefresh = false;
            if(this.refresh != null)
            {
                this.refresh.interrupt();
            }
            this.refresh = null;
        }

    }
    
    public void refreshProtocolList() throws IOException, ControlException
    {
        cm.updateProtocolList(networkProtocolSet, filters);
        fireListUpdated();
    }
    
    public void addNewProtocol(int ver, InetAddress ip, int port, int protocol)
    {
        
        NetworkProtocol np = new NetworkProtocol(); /* IP */
        TransportProtocol tp = new TransportProtocol(np); /* TCP/UDP */
        
        np.setAddr_src(ip); 
        np.setVer(ver);
        np.addEncapsulatedProtocol(tp);
        
        tp.setPort_destination(port);
        tp.setType(protocol);
        tp.setReadonly(true);
        
        if(networkProtocolSet.containsKey(np))
            networkProtocolSet.get(np).setReadonly(true);
        else
            networkProtocolSet.put(np, np);
        
    }
    
    public void removeAllProtocol()
    {
        List<NetworkProtocol> l = new LinkedList<NetworkProtocol>();
        
        for(NetworkProtocol np : this.networkProtocolSet.values())
        {
            if(!np.removeAllEncapsulated())
            {
                l.add(np);
            }
        }
        
        for(NetworkProtocol np : l)
        {
            this.networkProtocolSet.remove(np);
        }
    }
    
    public void removeOldProtocol()
    {
        int DelayBeforeRemove = ParameterLoader.getInstance().getDelayBeforeRemoveData();
        
        if(DelayBeforeRemove > 0)
        {
            long time = System.currentTimeMillis()/1000 + DelayBeforeRemove;
            List<NetworkProtocol> l = new LinkedList<NetworkProtocol>();
            for(NetworkProtocol np : this.networkProtocolSet.values())
            {
                if(np.removeOld(time))
                {
                    l.add(np);
                }
            }
            
            for(NetworkProtocol np : l)
            {
                this.networkProtocolSet.remove(np);
            }
        }
    }
    
    public Collection<NetworkProtocol> getNetworkProtocoles()
    {
        return this.networkProtocolSet.values();
    }
    
    private void fireCoreState()
    {
        for(CoreEvent ce : object_to_call)
        {
            ce.coreStateRefresh();
        }
    }
    
    private void fireListUpdated()
    {
        for(CoreEvent ce : object_to_call)
        {
            ce.protocoleListUpdated();
        }
    }
    
    private void fireError(Exception ex)
    {
        for(CoreEvent ce : object_to_call)
        {
            ce.errorHasOccured(ex);
        }
    }
    
    private void fireAutorefresh(boolean enable)
    {
        for(CoreEvent ce : object_to_call)
        {
            ce.autoRefresh(enable);
        }
    }
    
    public static CommunicationManagerV2 getInstance()
    {
        if(self == null)
        {
            self = new CommunicationManagerV2();
        }
        
        return self;
    }
    
    public void connect(String host, int port) throws IOException
    {
        socket = new Socket(InetAddress.getByName(host), port);
        cm.setSocket(socket);
    }
    
    public void connect(String host) throws IOException
    {
        this.connect(host, ParameterLoader.getInstance().getCorePort());
    }
    
    public void connect(int port) throws IOException
    {
        this.connect(ParameterLoader.getInstance().getCoreHost(), port);
    }
    
    public void connect() throws IOException
    {
        this.connect(ParameterLoader.getInstance().getCoreHost());
    }
    
    public void disconnect()
    {
        System.out.println("disconnect");
        setAutoRefresh(false);
        if(this.isConnected())
        {
            try {disableDevice();} catch (Exception ex) {}
            try {cm.stopAllCapture();} catch (Exception ex) {}
            try {cm.clearProtoList();} catch (Exception ex) {}
        }
        
        try {cm.setSocket(null);} catch (Exception ex) {}
        try {socket.close();} catch (Exception ex) {}
        socket = null;
        this.source = null;
    }
    
    public boolean isConnected()
    {
        return socket != null && socket.isConnected();
    }
    
    public void addListener(CoreEvent ce)
    {
        this.object_to_call.add(ce);
    }
    
    public void removeListener(CoreEvent ce)
    {
        this.object_to_call.remove(ce);
    }

    /**
     * @return the cm
     */
    public ControlMethod getCm() {
        return cm;
    }

    public void play() throws IOException, ControlException 
    {
        if(this.getState().IS_FILE())
        {
            cm.fileRead();
            state.setReading(true);
        }
    }
    
    public void resume() throws IOException, ControlException 
    {
        cm.streamResume();
        state.setPause(false);
    }

    public void pause() throws IOException, ControlException 
    {
        cm.streamPause();
        state.setPause(true);
    }
    
    public void stopAllCapture() throws IOException, ControlException
    {
        cm.stopAllCapture();
        
        for(NetworkProtocol np : this.networkProtocolSet.values())
        {
            np.stopAllCapture();
        }
        
        state.setReading(false);
        state.setPause(false);
    }
    
    public void startCapture(AbstractProtocolCaptured apc) throws IOException, ControlException 
    {
        //builf bpf filter
        String bpf = null;
        if(apc instanceof TransportProtocol)
        {
            TransportProtocol tp = (TransportProtocol) apc;
            
            if(tp.getParent().getAddr_src() instanceof Inet6Address)
            {
                bpf = "ip6 host "+tp.getParent().getAddr_src().getHostAddress()
                   + " and tcp port "+tp.getPort_destination();
            }
            else
            {
                bpf = "ip host "+tp.getParent().getAddr_src().getHostAddress()
                   + " and tcp port "+tp.getPort_destination();
            }
            
            
        }
        else if(apc instanceof NetworkProtocol)
        {
            NetworkProtocol np = (NetworkProtocol)apc;
            if(np.getAddr_src() instanceof Inet6Address)
            {
                bpf = "ip6 host "+np.getAddr_src().getHostAddress()
                   + " and tcp port 80";
            }
            else
            {
                bpf = "ip host "+np.getAddr_src().getHostAddress()
                   + " and tcp port 80";
            }
        }
        else
        {
            return;
        }
        
        
        apc.setCaptureID(cm.startCapture(bpf));
        apc.setCaptured(true);
    }
    
    public void stopCapture(AbstractProtocolCaptured tp) throws IOException, ControlException 
    {
        if(tp.isCaptured())
        {
            cm.stopCapture(tp.getCaptureID());
            tp.setCaptured(false);
            tp.setCaptureID(-1);
            
            stopModule(tp);
        }
    }
    
    private void disableDevice() throws IOException, ControlException
    {
        this.source = null;
        cm.disableCurrentDevice();
    }

    /**
     * @return the refreshTarget
     */
    public boolean isRefreshTarget() {
        return refreshTarget;
    }

    /**
     * @param refreshTarget the refreshTarget to set
     */
    public void setRefreshTarget(boolean refreshTarget) 
    {
        if(this.autoRefresh && !this.refreshCoreState)
        {
            this.setAutoRefresh(false);
        }
        
        this.refreshTarget = refreshTarget;
    }

    /**
     * @return the refreshCoreState
     */
    public boolean isRefreshCoreState() {
        return refreshCoreState;
    }

    /**
     * @param refreshCoreState the refreshCoreState to set
     */
    public void setRefreshCoreState(boolean refreshCoreState) 
    {
        this.state.setLocked(!refreshCoreState); /*empeche le thread de pouvoir aller modifier des infos*/
        
        if(this.autoRefresh && !this.refreshTarget)
        {
            this.setAutoRefresh(false);
        }
        
        this.refreshCoreState = refreshCoreState;
    }

    public void startRecord(String filename) throws IOException, ControlException
    {
        cm.startRecord(filename);
        state.setRecording(true);
    }
    
    public void stopRecord() throws IOException, ControlException
    {
        cm.stopRecord();
        state.setRecording(false);
    }

    public void setSpeed(int speed) throws IOException, ControlException 
    {
        cm.setSpeed((int)(speed * 1.27));        
    }

    /**
     * @return the state
     */
    public CoreState getState() {
        return state;
    }

    public void gotoPercent(int percent) throws IOException, ControlException 
    {
        boolean in_pause = state.IS_PAUSE();
        
        cm.fileStop();
        double result = (state.getComplete_tv()*percent)/100;
        long lresult = (long)result;
        result = result - lresult;
        int iresult = (int)(result*1000000);
                
        cm.fileGotoAndRead(lresult, iresult);
        
        if(in_pause)
        {
            cm.streamPause();
        }
    }

    public void startModule(AbstractProtocolCaptured tp, Module m) throws IOException 
    {
        if(!tp.isCaptured())
        {
            throw new RuntimeException("protocol is not in capture mode");
        }
        
        
        
        m.launch(tp.getCaptureID());
        tp.setCaptureModule(m);
        this.modules.add(m);
    }
    
    public void stopModule(AbstractProtocolCaptured tp)
    {
        if(tp.isCaptured())
        {
            Module m = tp.getCaptureModule();
            
            if(m != null)
            {
                m.halt();
            }
            
            tp.setCaptureModule(null);
        }
    }
    
    public void stopAllModule()
    {
        for(Module m : modules)
        {
            m.halt();
        }
        modules = new LinkedList<Module>();
    }

    public String getSelectedSource() {
        return this.source;
    }

    public boolean isCaptureInProgress() 
    {
        for(NetworkProtocol np : networkProtocolSet.values())
        {
            if(np.isCaptured())
            {
                return true;
            }
        }
        
        return false;
    }

    /**
     * @return the filters
     */
    public List<DataFiltering> getFilters() {
        return filters;
    }

    /**
     * @param filters the filters to set
     */
    public void setFilters(List<DataFiltering> filters) {
        this.filters = filters;
    }
}
