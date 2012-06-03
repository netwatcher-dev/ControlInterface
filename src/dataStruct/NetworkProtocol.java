/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataStruct;

import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author djo
 */
public class NetworkProtocol extends AbstractProtocolCaptured<TransportProtocol>
{
    private int ver;
    private InetAddress addr_src, addr_dest;
    

    public NetworkProtocol()
    {
        addr_src = addr_dest = null;
        ver = 0;
    }
    
    public NetworkProtocol(short ver, InetAddress addr_src, InetAddress addr_dest) 
    {
        this();
        this.ver = ver;
        this.addr_src = addr_src;
        this.addr_dest = addr_dest;
    }

    /**
     * @return the ver
     */
    public int getVer() {return ver;}

    /**
     * @return the addr_src
     */
    public InetAddress getAddr_src() {return addr_src;}

    /**
     * @return the addr_dest
     */
    public InetAddress getAddr_dest() {return addr_dest;}

    /**
     * @param ver the ver to set
     */
    public int setVer(int ver) {return this.ver = ver;}

    /**
     * @param addr_src the addr_src to set
     */
    public void setAddr_src(InetAddress addr_src) {this.addr_src = addr_src;}

    /**
     * @param addr_dest the addr_dest to set
     */
    public void setAddr_dest(InetAddress addr_dest) {this.addr_dest = addr_dest;}

    @Override
    public boolean equals(Object obj) 
    {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NetworkProtocol other = (NetworkProtocol) obj;
        if (this.ver != other.ver) {
            return false;
        }
        if (this.addr_src != other.addr_src && (this.addr_src == null || !this.addr_src.equals(other.addr_src))) {
            return false;
        }
        /*if (this.addr_dest != other.addr_dest && (this.addr_dest == null || !this.addr_dest.equals(other.addr_dest))) {
            return false;
        }*/
        return true;
    }

    @Override
    public int hashCode() 
    {
        int hash = 3;
        hash = 47 * hash + this.ver;
        hash = 47 * hash + (this.addr_src != null ? this.addr_src.hashCode() : 0);
        //hash = 47 * hash + (this.addr_dest != null ? this.addr_dest.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() 
    {
        return addr_src.getHostAddress();
    }
    
    public boolean removeOld(long olderThan)
    {
        boolean readOnly = this.readonly;
        
        List<TransportProtocol> to_remove = new LinkedList<TransportProtocol>();
        
        for(TransportProtocol tp : super.EncapsulatedProtocolesSet.values())
        {
            if(tp.isReadonly() || tp.isCaptured())
            {
                readOnly = true;
            }
            else
            {
                if(tp.getEpoch_time() < olderThan)
                {
                    to_remove.add(tp);
                }
            }
        }
        
        for(TransportProtocol tp : to_remove)
            super.EncapsulatedProtocolesSet.remove(tp);
        
        return readOnly;
    }
    
    public boolean containADesinationPortUnder(int limit)
    {
        for(TransportProtocol tp : super.EncapsulatedProtocolesSet.values())
        {
            if(tp.getPort_destination() <= limit)
                return true;
        }
        return false;
    }

    @Override
    public void update_local(AbstractProtocolCaptured epc) 
    {
        
    }

    @Override
    public short getStatus(long unix_timestamp) 
    {
        short bestStatus = STATUS_NOT_DETECTED;
        
        for(TransportProtocol tp : super.EncapsulatedProtocolesSet.values())
        {
            short sub_status = tp.getStatus(unix_timestamp);
            if(sub_status == STATUS_DETECTED)
            {
                return STATUS_DETECTED;
            }
            else
            {
                if(sub_status > bestStatus)
                {
                    bestStatus = sub_status;
                }
            }
        }
        
        return bestStatus;
    }
    
}
