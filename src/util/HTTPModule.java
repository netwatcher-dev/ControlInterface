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

package util;

import dataStruct.NetworkProtocol;
import dataStruct.TransportProtocol;
import java.io.IOException;

/**
 *
 * @author djo
 */
public class HTTPModule extends Module
{
    private Process p;
    private String path;
    
    public HTTPModule(String path) 
    {
        this.path = path;
    }
    
    @Override
    public boolean validModule(TransportProtocol tp) 
    {
        return tp.getPort_destination() == 80;
    }

    @Override
    public String toString() 
    {
        return "HTTP Module";
    }

    @Override
    protected void local_launch(int id)  throws IOException
    {
        p = Runtime.getRuntime().exec("java -jar "+path+" -ip 127.0.0.1 -port "+id);
    }

    @Override
    protected void local_halt()
    {
        if(p != null)
        {
            p.destroy();
        }
    }

    @Override
    public boolean validModule(NetworkProtocol np) 
    {
        /*not enough information to make a decision*/
        
        return false;
    }
    
    public void setModulePath(String s) 
    {
        
    }
}
