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

package dataStruct;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import util.Module;

public abstract class AbstractProtocolCaptured<E extends AbstractProtocolCaptured>
{
    public static final short STATUS_NOT_DETECTED = 0;
    public static final short STATUS_RECENT_DETECTED = 1;
    public static final short STATUS_DETECTED = 2;
    
    public static final int SECONDS_BEFORE_NOT_DETECTED = 20;
    public static final int SECONDS_BEFORE_RECENT_DETECTED = 10;
    
    private int captureID;
    protected boolean readonly, captured;
    private Module captureModule;
    protected Map<E,E> EncapsulatedProtocolesSet;

    public AbstractProtocolCaptured()
    {
        captured = readonly = false;
        captureID = -1;
        EncapsulatedProtocolesSet = new HashMap<E, E>();
    }
    
    
    /**
     * @return the captureID
     */
    public int getCaptureID() {
        return captureID;
    }

    /**
     * @param captureID the captureID to set
     */
    public void setCaptureID(int captureID) {
        this.captureID = captureID;
    }

    /**
     * @return the readonly
     */
    public boolean isReadonly() 
    {
        for(E e : this.EncapsulatedProtocolesSet.values())
        {
            if(e.isReadonly())
                return true;
        }
        
        return readonly || captured;
    }

    /**
     * @param readonly the readonly to set
     */
    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

    /**
     * @return the captured
     */
    public boolean isCaptured() 
    {
        for(E e: this.EncapsulatedProtocolesSet.values())
        {
            if(e.isCaptured())
                return true;
        }
        
        return captured;
    }

    /**
     * @param captured the captured to set
     */
    public void setCaptured(boolean captured) {
        this.captured = captured;
    }

    /**
     * @return the captureModule
     */
    public Module getCaptureModule() {
        return captureModule;
    }

    /**
     * @param captureModule the captureModule to set
     */
    public void setCaptureModule(Module captureModule) {
        this.captureModule = captureModule;
    }
    
    void stopCapture() 
    {
        this.captureID = -1;
        this.captured = false;
    }

    /**
     * @return the EncapsulatedProtocolesSet
     */
    public Map<E,E> getEncapsulatedProtocolesSet() {
        return EncapsulatedProtocolesSet;
    }

    /**
     * @param EncapsulatedProtocolesSet the EncapsulatedProtocolesSet to set
     */
    public void setEncapsulatedProtocolesSet(Map<E,E> EncapsulatedProtocolesSet) {
        this.EncapsulatedProtocolesSet = EncapsulatedProtocolesSet;
    }
    
    public abstract void update_local(AbstractProtocolCaptured epc);
    
    public void update_encapsulated(E epc)
    {
        if(EncapsulatedProtocolesSet.containsKey(epc))
        {
            EncapsulatedProtocolesSet.get(epc).update_local(epc);
            
        }
        else
        {
            EncapsulatedProtocolesSet.put(epc, epc);
        }
    }
    
    public void stopAllCapture() 
    {
        this.captureID = -1;
        this.captured = false;
        
        for(E e : this.EncapsulatedProtocolesSet.values())
        {
            e.stopCapture();
        }
    }
    
    public boolean removeAllEncapsulated()
    {
        boolean readOnly = this.readonly;
        
        List<E> to_remove = new LinkedList<E>();
        
        for(E e : this.EncapsulatedProtocolesSet.values())
        {
            if(e.isReadonly() || e.isCaptured())
            {
                readOnly = true;
            }
            else
            {
                to_remove.add(e);
            }
        }
        
        for(E e : to_remove)
            EncapsulatedProtocolesSet.remove(e);
        
        return readOnly;
    }
    
    public void addEncapsulatedProtocol(E tp) 
    {
        EncapsulatedProtocolesSet.put(tp, tp);
    }
    
    public abstract short getStatus(long unix_timestamp);
}
