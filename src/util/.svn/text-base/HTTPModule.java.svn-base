/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
        p = Runtime.getRuntime().exec("java -jar /Volumes/Home/Dropbox/Dropbox/tfe2011_2012/tfe_EPL-2011-058/src/module/ModuleHTTP_v2/pseudoProxy_v2/dist/pseudoProxy_v2.jar -ip 127.0.0.1 -port "+id);
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
}
