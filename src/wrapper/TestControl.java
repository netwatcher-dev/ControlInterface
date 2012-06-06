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

package wrapper;

import dataStruct.NetworkProtocol;
import dataStruct.TransportProtocol;
import exceptionPackage.ControlException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DataFiltering;


public class TestControl 
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        try 
        {
            CommunicationManagerV2 com = CommunicationManagerV2.getInstance();
            com.connect();
            com.setAutoRefresh(true);
            
            shell(com.getCm());
        } 
        catch (UnknownHostException ex) 
        {
            Logger.getLogger(TestControl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(TestControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void shell(ControlMethod cm)
    {
        Scanner sc = new Scanner(System.in);
        while(true)
        {
            System.out.print("M.Y.N control:>");
            String next = sc.nextLine();
            try 
            {
                if(next.startsWith("exit"))
                {
                    System.exit(0);
                }
                else if(next.startsWith("info"))
                {
                    System.out.println(""+cm.getInfo());
                }
                else if(next.startsWith("dlist"))
                {
                    for(String s : cm.listInterfaces())
                    {
                        System.out.println(s);
                    }
                }
                else if(next.startsWith("flist"))
                {
                    for(String s : cm.listFiles())
                    {
                        System.out.println(s);
                    }
                }
                else if(next.startsWith("dset"))
                {
                    StringTokenizer st = new StringTokenizer(next, " ");
                    if(st.countTokens() > 1)
                    {
                        st.nextToken();
                        cm.selectDevice(st.nextToken());
                    }
                    else
                    {
                        System.out.println("not enough args");
                    }
                }
                else if(next.startsWith("fset"))
                {
                    StringTokenizer st = new StringTokenizer(next, " ");
                    if(st.countTokens() > 1)
                    {
                        st.nextToken();
                        cm.selectFile(st.nextToken());
                    }
                    else
                    {
                        System.out.println("not enough args");
                    }
                }
                else if(next.startsWith("rstart"))
                {
                    StringTokenizer st = new StringTokenizer(next, " ");
                    if(st.countTokens() > 1)
                    {
                        st.nextToken();
                        cm.startRecord(st.nextToken());
                    }
                    else
                    {
                        System.out.println("not enough args");
                    }
                }
                else if(next.startsWith("rstop"))
                {
                    cm.stopRecord();
                }
                else if(next.startsWith("astop"))
                {
                    cm.stopAllCapture();
                }
                else if(next.startsWith("fparse"))
                {
                    cm.parseFile();
                }
                else if(next.startsWith("fread"))
                {
                    cm.fileRead();
                }
                else if(next.startsWith("fgoto"))
                {
                    StringTokenizer st = new StringTokenizer(next, " ");
                    if(st.countTokens() > 2)
                    {
                        st.nextToken();
                        cm.fileGoto(Long.parseLong(st.nextToken()), Integer.parseInt(st.nextToken()));
                    }
                    else
                    {
                        System.out.println("not enough args");
                    }
                }
                else if(next.startsWith("pause"))
                {
                    cm.streamPause();
                }
                else if(next.startsWith("resume"))
                {
                    cm.streamResume();
                }
                else if(next.startsWith("dstop"))
                {
                    cm.disableCurrentDevice();
                }
                else if(next.startsWith("mset"))
                {
                    StringTokenizer st = new StringTokenizer(next, " ");
                    if(st.countTokens() > 1)
                    {
                        st.nextToken();
                        cm.setMasterFilter(st.nextToken());
                    }
                    else
                    {
                        System.out.println("not enough args");
                    }
                }
                else if(next.startsWith("mtest"))
                {
                    StringTokenizer st = new StringTokenizer(next, " ");
                    if(st.countTokens() > 1)
                    {
                        st.nextToken();
                        cm.testMasterFilter(st.nextToken());
                    }
                    else
                    {
                        System.out.println("not enough args");
                    }
                }
                else if(next.startsWith("cstart"))
                {
                    StringTokenizer st = new StringTokenizer(next, " ");
                    if(st.countTokens() > 1)
                    {
                        st.nextToken();
                        System.out.println(""+cm.startCapture(st.nextToken()));
                    }
                    else
                    {
                        System.out.println("not enough args");
                    }
                }
                
                else if(next.startsWith("plist"))
                {
                    HashMap<NetworkProtocol,NetworkProtocol> m = new HashMap<NetworkProtocol, NetworkProtocol>();
                    cm.updateProtocolList(m, new LinkedList<DataFiltering>());
                    
                    for(NetworkProtocol pip : m.keySet())
                    {
                        System.out.println(""+pip);
                        for(TransportProtocol tp : pip.getEncapsulatedProtocolesSet().values())
                        {
                            System.out.println("    "+tp);
                        }
                    }
                }
                else if(next.startsWith("pclear"))
                {
                    cm.clearProtoList();
                }
                else if(next.startsWith("flush"))
                {
                    cm.flushSegment();
                }
                else if(next.startsWith("plength"))
                {
                    StringTokenizer st = new StringTokenizer(next, " ");
                    if(st.countTokens() > 1)
                    {
                        st.nextToken();
                        cm.setBufferLength(Integer.parseInt(st.nextToken()));
                    }
                    else
                    {
                        System.out.println("not enough args");
                    }
                }
                else if(next.startsWith("cstop"))
                {
                    StringTokenizer st = new StringTokenizer(next, " ");
                    if(st.countTokens() > 1)
                    {
                        st.nextToken();
                        cm.stopCapture(Integer.parseInt(st.nextToken()));
                    }
                    else
                    {
                        System.out.println("not enough args");
                    }
                }
                else if(next.startsWith("sset"))
                {
                    StringTokenizer st = new StringTokenizer(next, " ");
                    if(st.countTokens() > 1)
                    {
                        st.nextToken();
                        cm.setSpeed(Integer.parseInt(st.nextToken()));
                    }
                    else
                    {
                        System.out.println("not enough args");
                    }
                }
                else
                {
                    System.out.println("Unknwon command");
                }
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(TestControl.class.getName()).log(Level.SEVERE, null, ex);
                return;
            } 
            catch (ControlException ex) 
            {
                System.out.println(""+ex.toString());
            }
            catch(Exception ex)
            {
                System.out.println(""+ex.toString());
            }
        }
    }
}
