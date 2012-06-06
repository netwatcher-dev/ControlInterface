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

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

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
