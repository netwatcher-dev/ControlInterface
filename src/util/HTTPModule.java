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
