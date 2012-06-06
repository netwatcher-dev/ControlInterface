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
