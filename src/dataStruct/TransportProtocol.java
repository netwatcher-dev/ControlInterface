/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataStruct;

import util.Module;

/**
 *
 * @author djo
 */
public class TransportProtocol extends AbstractProtocolCaptured
{    
    public static final short PROTOCOL_TYPE_TCP = 6;
    public static final short PROTOCOL_TYPE_UDP = 17;
    
    private int type; /* TCP, UDP, ... */
    private int port_source,port_destination;
    private long epoch_time;
    
    private NetworkProtocol parent;
    
    public TransportProtocol(NetworkProtocol parent,short type, int port_source, int port_destination, long epoch_time) 
    {
        this(parent);
        this.type = type;
        this.port_source = port_source;
        this.port_destination = port_destination;
        this.epoch_time = epoch_time;
    }

    public TransportProtocol(NetworkProtocol parent) 
    {
        this.parent = parent;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @return the port_source
     */
    public int getPort_source() {
        return port_source;
    }

    /**
     * @return the port_destination
     */
    public int getPort_destination() {
        return port_destination;
    }
    
    /**
     * @return the epoch_time
     */
    public long getEpoch_time() {
        return epoch_time;
    }

    /**
     * @param type the type to set
     */
    public int setType(int type) {
        return this.type = type;
    }

    /**
     * @param port_source the port_source to set
     */
    public void setPort_source(int port_source) {
        this.port_source = port_source;
    }

    /**
     * @param port_destination the port_destination to set
     */
    public void setPort_destination(int port_destination) {
        this.port_destination = port_destination;
    }

    /**
     * @param epoch_time the epoch_time to set
     */
    public void setEpoch_time(long epoch_time) {
        this.epoch_time = epoch_time;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TransportProtocol other = (TransportProtocol) obj;
        if (this.type != other.type) {
            return false;
        }
        /*if (this.port_source != other.port_source) {
            return false;
        }*/
        if (this.port_destination != other.port_destination) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.type;
        //hash = 89 * hash + this.port_source;
        hash = 89 * hash + this.port_destination;
        return hash;
    }

    @Override
    public String toString() {
        //return "TransportProtocol{" + "type=" + type + ", port_source=" + port_source + ", port_destination=" + port_destination + ", epoch_time=" + epoch_time + '}';
        return port_destination+" ("+getProtocolName(type) +")";
    }

    public short getStatus(long unix_timestamp) 
    {
        return ((unix_timestamp-SECONDS_BEFORE_RECENT_DETECTED) <= this.epoch_time)?STATUS_DETECTED:((unix_timestamp-SECONDS_BEFORE_NOT_DETECTED <= this.epoch_time)?STATUS_RECENT_DETECTED:STATUS_NOT_DETECTED);
    }

    /**
     * @return the parent
     */
    public NetworkProtocol getParent() 
    {
        return parent;
    }
    
    public static String getProtocolName(int num)
    {
        switch(num)
        {
            case 1 : return "ICMP";
            case 2 : return "IGMP";
            case 3 : return "GGP";
            case 4 : return "IPv4";
            case 5 : return "Internet Stream Protocol";
            case 6 : return "TCP";
            case 8 : return "EGP";
            case 9 : return "IGP";
            case 17: return "UDP";
            case 27: return "RDP";
            case 41: return "IPv6";
            case 58: return "ICMP for IPv6";
            case 88: return "EIGRP";
            case 89: return "OSPF";
            default: return "Unknown";     
        }

    }

    @Override
    public void update_local(AbstractProtocolCaptured epc) 
    {
        if(epc instanceof TransportProtocol)
        {
            TransportProtocol tp = (TransportProtocol)epc;
            setEpoch_time(tp.getEpoch_time());
        }
    }
}
