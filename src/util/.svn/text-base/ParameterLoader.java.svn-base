/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 *
 * @author djo
 */
public class ParameterLoader 
{
    private Properties parameter;
    private static ParameterLoader self;
    
    public static final String coreHost = "localhost";
    public static final int corePort = 22222;
    public static final int refreshInterval = 10;
    public static final Integer protocolsSupported[] = new Integer[]{25,80,110};
    public static final int delayBeforeRemoveData = 0;
    
    public ParameterLoader()
    {
        try 
        {
            parameter = new Properties();
            parameter.load(ParameterLoader.class.getResourceAsStream("/util/datas/parameters.properties") );
        } 
        catch (IOException ex) 
        {
            parameter = null;
            System.out.println("(ParameterLoader) failed to load properties : "+ex.getMessage());
        }
    }
    
    public static ParameterLoader getInstance()
    {
        if(self == null)
        {
            self = new ParameterLoader();
        }
        
        return self;
    }
    
    public String getCoreHost()
    {
        if(parameter != null && parameter.containsKey("ipCore"))
        {
            try
            {
                return parameter.getProperty("ipCore");
            }
            catch(NumberFormatException nfe)
            {
                System.out.println("ipCore in properties is not a valid integer");
            }
        }
        
        return coreHost;
    }
    
    public int getCorePort()
    {
        return getIntFromProperties("portCore", corePort);
    }
    
    public int getDelayBeforeRemoveData()
    {
        return getIntFromProperties("delayBeforeRemoveData", delayBeforeRemoveData);
    }
    
    public int getRefreshInterval()
    {
        return getIntFromProperties("refreshInterval", refreshInterval);
    }
    
    private int getIntFromProperties(String key, int defaultValue)
    {
        if(parameter != null && parameter.containsKey(key))
        {
            try
            {
                return Integer.parseInt(parameter.getProperty(key));
            }
            catch(NumberFormatException nfe)
            {
                System.out.println( key+" in properties is not a valid integer");
            }
        }
        
        return defaultValue;
    }
    
    public List<Integer> getProtocolsSupported()
    {
        if(parameter != null && parameter.containsKey("refreshInterval"))
        {
            List<Integer> l = new LinkedList<Integer>();
            StringTokenizer st = new StringTokenizer(parameter.getProperty("refreshInterval")," ");
            while(st.hasMoreTokens())
            {
                try
                {
                    l.add(Integer.parseInt(st.nextToken()));
                }
                catch(NumberFormatException nfe)
                {
                    System.out.println( "a element from the supported protocols in properties is not a valid integer : "+nfe.getMessage());
                }
            }
            return l;
        }
        
        return Arrays.asList(protocolsSupported);
    }

    public void setCoreHost(String addr) 
    {
        parameter.setProperty("ipCore", addr);
    }

    public void setCorePort(String text) 
    {
        parameter.setProperty("portCore", text);
    }

    public void setRefreshInterval(String text) 
    {
        parameter.setProperty("refreshInterval", text);
    }

    public void setDelayBeforeRemoveData(String text) 
    {
        parameter.setProperty("delayBeforeRemoveData", text);
    }
    
}
