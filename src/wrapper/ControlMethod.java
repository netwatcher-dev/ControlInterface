/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wrapper;

import dataStruct.CoreState;
import dataStruct.NetworkProtocol;
import dataStruct.TransportProtocol;
import exceptionPackage.ControlException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author djo
 */
public class ControlMethod 
{
    public static final int COMMAND_DEVICE_LIST = 1; /*recupere la liste des interfaces reseaux disponible pour l'ecoute*/
    public static final int COMMAND_GET_PROTOCOL_LIST = 2; /*active l'envoi d'information sur les flux que l'on voit passer*/
    public static final int COMMAND_SELECT_CAPTURE_DEVICE = 4; /*selectionne une interface d'ecoute*/
    public static final int COMMAND_DISABLE_CAPTURE_DEVICE = 5; /*deselectionne une interface d'ecoute*/
    public static final int COMMAND_SELECT_CAPTURE_FILE = 6; /*selectionne un fichier de capture*/
    public static final int COMMAND_SET_SPEED = 7; /*defini la vitesse de lecture d'un fichier de capture*/
    public static final int COMMAND_PARSE_FILE = 8; /*parse un fichier pour identifier les protocoles present*/
    public static final int COMMAND_START_CAPTURE = 9; /*demarre la capture pour un protocol et pour l'envoyer en reconstitution*/
    public static final int COMMAND_STOP_CAPTURE = 10; /*desactive une capture*/
    public static final int COMMAND_STOP_ALL_CAPTURE = 11; /*desactive l'ensemble des captures*/
    public static final int COMMAND_LIST_FILE = 12; /*liste les fichiers disponible*/
    public static final int COMMAND_START_RECORD = 13; /*commence l'enregistrement sur un fichier*/
    public static final int COMMAND_STOP_RECORD = 14; /*stop l'enregistrement sur un fichier*/
    public static final int COMMAND_SET_BUFFER_LENGTH_PROTO_LIST = 15; /* Nombre maximum d'entrée pour la liste de protocols */
    public static final int COMMAND_CLEAR_PROTO_LIST = 16; /* Effacer les entrées dans la liste de protocols */
    public static final int COMMAND_SET_MASTER_FILTER = 17; /*Definit le filtre principale sur l'interface*/
    public static final int COMMAND_TEST_MASTER_FILTER = 18; /*Definit le filtre principale sur l'interface*/
    public static final int COMMAND_FLUSH_SEGMENT = 19; /*Definit le filtre principale sur l'interface*/
    public static final int COMMAND_STREAM_PAUSE = 20;
    public static final int COMMAND_STREAM_RESUME = 21;
    public static final int COMMAND_FILE_READ = 22;
    public static final int COMMAND_FILE_GOTO = 23;
    public static final int COMMAND_SELECT_CAPTURE_DEVICE_WITH_MONITORING = 24; /*on tente de forcer le monitoring*/
    public static final int COMMAND_GET_STATE = 25;
    public static final int COMMAND_FILE_STOP = 26;
    public static final int COMMAND_FILE_GOTO_AND_READ = 27;
    
    private DataOutputStream dos;
    private DataInputStream dis;
    
    
    public ControlMethod()
    {
        
    }
    
    public ControlMethod(Socket s) throws IOException 
    {
        setSocket(s);
    }
    
    final public void setSocket(Socket s) throws IOException 
    {
        if(s == null)
        {
            dos = null;
            dis = null;
        }
        else
        {
            dos = new DataOutputStream(s.getOutputStream());
            dis = new DataInputStream(s.getInputStream());
        }
    }
    
    public synchronized List<String> listInterfaces() throws IOException, ControlException
    {
        System.out.println("listInterfaces");
        return receiveStringList(COMMAND_DEVICE_LIST);
    }
    
    public synchronized void updateProtocolList(Map<NetworkProtocol,NetworkProtocol> set) throws IOException, ControlException
    {
        System.out.println("UPDATE PROTOCOL LIST");
        dos.write(COMMAND_GET_PROTOCOL_LIST);
        dos.flush();
        
        /*list protocol*/
        //Map<NetworkProtocol,NetworkProtocol> set = new HashMap<NetworkProtocol,NetworkProtocol>();
        
        long entries_count = dis.readInt()&0xFFFFFFFFL;
        while(entries_count-- > 0)
        {
            NetworkProtocol np = new NetworkProtocol();
            TransportProtocol tp = new TransportProtocol(np);
            
            tp.setEpoch_time(dis.readInt()&0xffffffffL);

            if( (tp.setType(dis.read())) < 0)
            {
                throw new EOFException();
            }

            if( np.setVer(dis.read()) == -1)
            {
                throw new EOFException();
            }

            tp.setPort_source(dis.readShort()&0xffff);              
            tp.setPort_destination(dis.readShort()&0xffff);

            byte ip_s[] = new byte[16]; 
            if(dis.read(ip_s) < 0)
            {
                throw new EOFException();
            }

            byte ip_d[] = new byte[16]; 
            if(dis.read(ip_d) < 0)
            {
                throw new EOFException();
            }

            if(np.getVer() == 4)
            {
                byte ip4_s[] = new byte[4];
                byte ip4_d[] = new byte[4];

                System.arraycopy(ip_s, 0, ip4_s, 0, 4);
                System.arraycopy(ip_d, 0, ip4_d, 0, 4);

                np.setAddr_src(InetAddress.getByAddress(ip4_s));
                np.setAddr_dest(InetAddress.getByAddress(ip4_d));
            }
            else
            {
                np.setAddr_src(InetAddress.getByAddress(ip_s));
                np.setAddr_dest(InetAddress.getByAddress(ip_d));
            }
            
            /*TODO improve filter */
            
            /*si ce n'est pas du tcp ou de l'udp, bye bye*/
            if( tp.getType() != TransportProtocol.PROTOCOL_TYPE_TCP && tp.getType() != TransportProtocol.PROTOCOL_TYPE_UDP)
            {
                continue;
            }
            
            /*recherche du protocole web*/
            if(tp.getPort_destination() != 80 && tp.getPort_destination() != 8080)
            {
                continue;
            }
            
            if(set.containsKey(np))
            {
                np = set.get(np);
            }
            else
            {
                set.put(np, np);
            }
            
            np.update_encapsulated(tp);
        }
        
        int k = dis.readInt();
        
        if(k != ControlException.STATE_NO_ERROR)
            throw new ControlException(k);
        
        System.out.println("UPDATE PROTOCOL LIST DONE");
    }
    
    public synchronized void selectDevice(String device) throws IOException, ControlException
    {   
        sendString(COMMAND_SELECT_CAPTURE_DEVICE,device);
    }
    
    public synchronized void selectDeviceWithMonitoring(String device) throws IOException, ControlException
    {        
        sendString(COMMAND_SELECT_CAPTURE_DEVICE_WITH_MONITORING,device);
    }
    
    public synchronized void disableCurrentDevice() throws IOException, ControlException
    {
        directTransmit(COMMAND_DISABLE_CAPTURE_DEVICE);
    }
    
    public synchronized void selectFile(String file) throws IOException, ControlException
    {
        sendString(COMMAND_SELECT_CAPTURE_FILE,file);
    }
    
    public synchronized void setSpeed(int coeficient) throws IOException, ControlException
    {
        System.out.println("SET SPEED");
        if(coeficient < -127 || coeficient > 127)
        {
            throw new ControlException(ControlException.STATE_PARAMETER_ERROR);
        }
        
        int b = 0;
        
        if( coeficient < 0)
        {
            b |= 0x80;
            coeficient *= -1;
        }
        
        b |= (coeficient&0x7F);
        
        dos.write(COMMAND_SET_SPEED);
        dos.write(b);
        dos.flush();
        
        
        int k = dis.readInt();
        
        if(k != ControlException.STATE_NO_ERROR)
            throw new ControlException(k);
        System.out.println("SET SPEED DONE");
    }
    
    public synchronized void parseFile() throws IOException, ControlException
    {
        directTransmit(COMMAND_PARSE_FILE);
    }
    
    public synchronized int startCapture(String bpf) throws IOException, ControlException
    {        
        sendString(COMMAND_START_CAPTURE,bpf); 
        int ret = dis.readShort()&0xFFFF;
        System.out.println("START CAPTURE GET PORT DONE");
        return ret;
    }
    
    public synchronized void stopCapture(int id) throws IOException, ControlException
    {
        if(id < 0 || id > 65535)
        {
            throw new NumberFormatException("id must be an unsigned short : between 0 and 65535");
        }
        
        sendShort(COMMAND_STOP_CAPTURE,(short)id);
    }
    
    public synchronized void stopAllCapture() throws IOException, ControlException
    {
        directTransmit(COMMAND_STOP_ALL_CAPTURE);
    }
    
    public synchronized List<String> listFiles() throws IOException, ControlException
    {
        return receiveStringList(COMMAND_LIST_FILE);
    }
    
    public synchronized void startRecord(String file) throws IOException, ControlException
    {
        sendString(COMMAND_START_RECORD,file);
    }
    
    public synchronized void stopRecord() throws IOException, ControlException
    {
        directTransmit(COMMAND_STOP_RECORD);
    }
    
    public synchronized void clearProtoList() throws IOException, ControlException
    {
        directTransmit(COMMAND_CLEAR_PROTO_LIST);
    }
    
    public synchronized void flushSegment() throws IOException, ControlException
    {
        directTransmit(COMMAND_FLUSH_SEGMENT);
    }
    
    public synchronized void streamPause() throws IOException, ControlException
    {
        directTransmit(COMMAND_STREAM_PAUSE);
    }
    
    public synchronized void streamResume() throws IOException, ControlException
    {
        directTransmit(COMMAND_STREAM_RESUME);
    }
    
    public synchronized void fileRead() throws IOException, ControlException
    {
        directTransmit(COMMAND_FILE_READ);
    }
    
    public synchronized void fileStop() throws IOException, ControlException
    {
        directTransmit(COMMAND_FILE_STOP);
    }
    
    public synchronized void setMasterFilter(String filter) throws IOException, ControlException
    {
        sendString(COMMAND_SET_MASTER_FILTER,filter);
    }
    
    public synchronized void testMasterFilter(String filter) throws IOException, ControlException
    {
        sendString(COMMAND_TEST_MASTER_FILTER,filter);
    }
    
    public synchronized void setBufferLength(int buffer_length) throws IOException, ControlException
    {   
        if(buffer_length < 0 || buffer_length > 65535)
        {
            throw new NumberFormatException("buffer_length must be an unsigned short : between 0 and 65535");
        }
        
        sendShort(COMMAND_SET_BUFFER_LENGTH_PROTO_LIST,(short)buffer_length);
    }
    
    public synchronized void fileGotoAndRead(long seconds, int microseconds)throws IOException, ControlException
    {
        fileGotoPrivate(COMMAND_FILE_GOTO_AND_READ,seconds,microseconds);
    }
    
    public synchronized void fileGoto(long seconds, int microseconds)throws IOException, ControlException
    {
        fileGotoPrivate(COMMAND_FILE_GOTO,seconds,microseconds);
    }
    
    public synchronized void fileGotoPrivate(int action, long seconds, int microseconds)throws IOException, ControlException
    {   
        System.out.println("FILE GOTO");
        if(seconds > 4294967295L || seconds < 0)
        {
            throw new NumberFormatException("seconds must be an unsigned integer : between 0 and 4294967295");
        }
                
        dos.write(action);
        dos.writeInt((int)seconds);
        dos.writeInt(microseconds);
        dos.flush();
        
        int k = dis.readInt();
        
        if(k != ControlException.STATE_NO_ERROR)
            throw new ControlException(k);
        
        System.out.println("FILE GOTO DONE");
    }
    
    public synchronized CoreState getInfo() throws IOException, ControlException 
    {
        System.out.println("GET STATE");
        dos.write(COMMAND_GET_STATE);
        dos.flush();
        
        int k = dis.readInt();
        
        if(k != ControlException.STATE_NO_ERROR)
            throw new ControlException(k);
        
        CoreState s = new CoreState(dis.readInt(),dis.readInt()&0xffffffffL, dis.readInt()&0xffffffffL);
        
        byte b[] = new byte[8];
        dis.read(b);
        BigInteger bi1 = new BigInteger(1,b);/*Current_tv_sec*/
        
        dis.read(b);
        BigInteger bi2 = new BigInteger(1,b); /*Current_tv_usec*/

        
        dis.read(b);
        BigInteger bi3 = new BigInteger(1,b); /*Complete_tv_sec*/

        
        dis.read(b);
        BigInteger bi4 = new BigInteger(1,b); /*Complete_tv_usec*/

        if(s.IS_PARSED())
        {
            s.setCurrent_tv(bi1.doubleValue() + (bi2.doubleValue()/1000000) );
            s.setComplete_tv(bi3.doubleValue() + (bi4.doubleValue()/1000000) );
        }
        
        if( (k = dis.readInt()) != 0) /* End with an integer equal to 0 */
        {
            b = new byte[k];
            int received = dis.read(b);
            s.setDevicePath(new String(b, 0, received));
        }
        
        System.out.println("GET STATE DONE");
        
        return s;
    }
    
    /*ABSTRACT COMMAND*/
    
    private void directTransmit(int command) throws IOException, ControlException
    {
        System.out.println("DIRECT TRANSMIT "+getAction(command));
        dos.write(command);
        dos.flush();
        
        int k = dis.readInt();
        
        if(k != ControlException.STATE_NO_ERROR)
            throw new ControlException(k);
        System.out.println("DIRECT TRANSMIT "+getAction(command)+" DONE");
    }
    
    private List<String> receiveStringList(int command) throws IOException, ControlException
    {
        System.out.println("RECEIVE STRING LIST "+getAction(command));
        LinkedList<String> ret = new LinkedList<String>();

        dos.write(command);
        dos.flush();
        
        int k;
        
        k = dis.readInt();
        
        if(k != ControlException.STATE_NO_ERROR)
            throw new ControlException(k);
        
        while( (k = dis.readInt()) != 0) /* End with an integer equal to 0 */
        {
            byte [] b = new byte[k];
            int received = dis.read(b);
            ret.addLast(new String(b, 0, received));
        }
        
        System.out.println("RECEIVE STRING LIST "+getAction(command)+" DONE");
        return ret;
    }

    private void sendString(int command, String string) throws IOException, ControlException
    {
        System.out.println("SEND STRING "+getAction(command));
        dos.write(command);
        dos.writeInt(string.length());
        dos.write(string.getBytes(Charset.forName("ascii")));
        dos.flush();
        
        int k = dis.readInt();
        
        if(k != ControlException.STATE_NO_ERROR)
            throw new ControlException(k);
        
        System.out.println("SEND STRING "+getAction(command)+" DONE");
    }
    
    private void sendShort(int command, short value) throws IOException, ControlException
    {
        System.out.println("SEND SHORT "+getAction(command));
        dos.write(command);
        dos.writeShort(value);
        dos.flush();
        
        int k = dis.readInt();
        
        if(k != ControlException.STATE_NO_ERROR)
            throw new ControlException(k);
        
        System.out.println("SEND SHORT "+getAction(command)+" DONE");
    }

    private String getAction(int command)
    {
        switch(command)
        { 
            case COMMAND_DEVICE_LIST : return "COMMAND_DEVICE_LIST";
            case COMMAND_GET_PROTOCOL_LIST : return "COMMAND_GET_PROTOCOL_LIST";
            case COMMAND_SELECT_CAPTURE_DEVICE : return "COMMAND_SELECT_CAPTURE_DEVICE";
            case COMMAND_DISABLE_CAPTURE_DEVICE : return "COMMAND_DISABLE_CAPTURE_DEVICE";
            case COMMAND_SELECT_CAPTURE_FILE : return "COMMAND_SELECT_CAPTURE_FILE";
            case COMMAND_SET_SPEED : return "COMMAND_SET_SPEED";
            case COMMAND_PARSE_FILE : return "COMMAND_PARSE_FILE";
            case COMMAND_START_CAPTURE : return "COMMAND_START_CAPTURE"; 
            case COMMAND_STOP_CAPTURE : return "COMMAND_STOP_CAPTURE"; 
            case COMMAND_STOP_ALL_CAPTURE : return "COMMAND_STOP_ALL_CAPTURE"; 
            case COMMAND_LIST_FILE : return "COMMAND_LIST_FILE"; 
            case COMMAND_START_RECORD : return "COMMAND_START_RECORD"; 
            case COMMAND_STOP_RECORD : return "COMMAND_STOP_RECORD"; 
            case COMMAND_SET_BUFFER_LENGTH_PROTO_LIST : return "COMMAND_SET_BUFFER_LENGTH_PROTO_LIST"; 
            case COMMAND_CLEAR_PROTO_LIST : return "COMMAND_CLEAR_PROTO_LIST"; 
            case COMMAND_SET_MASTER_FILTER : return "COMMAND_SET_MASTER_FILTER"; 
            case COMMAND_TEST_MASTER_FILTER : return "COMMAND_TEST_MASTER_FILTER"; 
            case COMMAND_FLUSH_SEGMENT : return "COMMAND_FLUSH_SEGMENT"; 
            case COMMAND_STREAM_PAUSE : return "COMMAND_STREAM_PAUSE"; 
            case COMMAND_STREAM_RESUME : return "COMMAND_STREAM_RESUME"; 
            case COMMAND_FILE_READ : return "COMMAND_FILE_READ"; 
            case COMMAND_FILE_GOTO : return "COMMAND_FILE_GOTO"; 
            case COMMAND_SELECT_CAPTURE_DEVICE_WITH_MONITORING : return "COMMAND_SELECT_CAPTURE_DEVICE_WITH_MONITORING"; 
            case COMMAND_GET_STATE : return "COMMAND_GET_STATE"; 
            case COMMAND_FILE_STOP : return "COMMAND_FILE_STOP";
            case COMMAND_FILE_GOTO_AND_READ : return "COMMAND_FILE_GOTO_AND_READ";
            default:
                return "UNKNOWN COMMAND : "+command;
        }
    }
}
