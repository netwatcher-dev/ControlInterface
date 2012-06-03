/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dataStruct.TransportProtocol;

/**
 *
 * @author djo
 */
public class ModuleManager 
{
    private static final Module HTTP_MODULE = new HTTPModule();
    
    /*TODO*/
    
    public static Module [] getModules()
    {
        return new Module[]{HTTP_MODULE};
    }
    
    public static Module getBestModule(TransportProtocol tp)
    {
        if(HTTP_MODULE.validModule(tp))
        {
            return HTTP_MODULE;
        }
        else
        {
            return null;
        }
    }
}
