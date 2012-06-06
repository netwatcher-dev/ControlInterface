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

import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;

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
