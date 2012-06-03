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
public abstract class Module 
{    
    public Module()
    {
    }

    public abstract boolean validModule(TransportProtocol tp);
    public abstract boolean validModule(NetworkProtocol np);
    @Override public abstract String toString();
    protected abstract void local_launch(int id) throws IOException;
    protected abstract void local_halt();
    
    public void launch(int id) throws IOException
    {
        local_launch(id);
        
    }
    public void halt()
    {        
        local_halt();
    }
}
