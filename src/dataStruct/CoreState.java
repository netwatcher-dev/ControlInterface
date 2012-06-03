/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataStruct;


/**
 *
 * @author djo
 */
public class CoreState 
{
    private int state;
    private long packet_readed;
    private long packet_in_file;
    private double current_tv, complete_tv;
    private String devicePath;
    private boolean locked;
    
    public CoreState(int state, long packet_readed, long packet_in_file) 
    {
        this.state = state;
        this.packet_in_file = packet_in_file;
        this.packet_readed = packet_readed;
        locked = false;
    }

    /**
     * @return the state
     */
    public int getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public synchronized void setState(int state) {
        System.out.println("setState "+state);
        this.state = state;
    }

    /**
     * @return the packet_readed
     */
    public long getPacket_readed() {
        return packet_readed;
    }

    /**
     * @param packet_readed the packet_readed to set
     */
    public synchronized void setPacket_readed(long packet_readed) {
        this.packet_readed = packet_readed;
    }

    /**
     * @return the packet_in_file
     */
    public long getPacket_in_file() {
        return packet_in_file;
    }

    /**
     * @param packet_in_file the packet_in_file to set
     */
    public synchronized void setPacket_in_file(long packet_in_file) {
        this.packet_in_file = packet_in_file;
    }

    @Override
    public String toString() {
        return "CoreState{" + "state=" + state + ", packet_readed=" + packet_readed + ", packet_in_file=" + packet_in_file + ", current_tv=" + getCurrent_tv() + ", complete_tv=" + getComplete_tv() + ", devicePath=" + devicePath + ", locked=" + locked + '}'
         +"\nRunning : "+IS_RUNNING()+", Recording : "+IS_RECORDING()+", File : "+IS_FILE() +", Stream : "+IS_STREAM()+", Parsing : "+IS_PARSING()+", Reading : "+IS_READING()+", Pause : "+IS_PAUSE() +", Goto : "+IS_GOTO()+", Parsed : "+IS_PARSED()+", Finished : "+IS_FINISHED();
    }

    /**
     * @return the devicePath
     */
    public String getDevicePath() {
        return devicePath;
    }

    /**
     * @param devicePath the devicePath to set
     */
    public synchronized void setDevicePath(String devicePath) {
        this.devicePath = devicePath;
    }
    
    public static int RUNNING = 0x001;
    public boolean IS_RUNNING(){return ( (state&RUNNING) > 0)?true:false;}
    
    public synchronized void setRunning(boolean enable)
    {
        System.out.println("setRunning "+enable);
        if(enable)
        {
            state |= RUNNING;
        }
        else
        {
            if(IS_RUNNING())
            {
                state ^= RUNNING;
            }
        }
    }
    
    public static int RECORDING = 0x002;
    public boolean IS_RECORDING() {return ( (state & RECORDING) > 0)?true:false;}
    
    public synchronized void setRecording(boolean enable)
    {
        System.out.println("setRecording "+enable);
        if(enable)
        {
            state |= RECORDING;
        }
        else
        {
            if(IS_RECORDING())
            {
                state ^= RECORDING;
            }
        }
    }
    
    public static int FILE = 0x004;
    public boolean IS_FILE()      {return ( (state & FILE) > 0)?true:false;}
    
    public synchronized void setFile(boolean enable)
    {
        System.out.println("setFile "+enable);
        if(enable)
        {
            state |= FILE;
        }
        else
        {
            if(IS_FILE())
            {
                state ^= FILE;
            }
        }
    }
    
    public static int STREAM = 0x008;
    public boolean IS_STREAM()    {return ( (state & STREAM) > 0)?true:false;}

    public synchronized void setStream(boolean enable)
    {
        System.out.println("setStream "+enable);
        if(enable)
        {
            state |= STREAM;
        }
        else
        {
            if(IS_STREAM())
            {
                state ^= STREAM;
            }
        }
    }
    
    public static int PARSING = 0x010;
    public boolean IS_PARSING()   {return ( (state & PARSING) > 0)?true:false;}
    
    public synchronized void setParsing(boolean enable)
    {
        System.out.println("setParsing "+enable);
        if(enable)
        {
            state |= PARSING;
        }
        else
        {
            if(IS_PARSING())
            {
                state ^= PARSING;
            }
        }
    }
    
    public static int READING = 0x020;
    public boolean IS_READING()   {return ( (state & READING) > 0)?true:false;}
    
    public synchronized void setReading(boolean enable)
    {
        System.out.println("setReading "+enable);
        if(enable)
        {
            state |= READING;
        }
        else
        {
            if(IS_READING())
            {
                state ^= READING;
            }
        }
    }
    
    public static int PAUSE = 0x040;
    public boolean IS_PAUSE()     {return ( (state & PAUSE) > 0)?true:false;}
    
    public synchronized void setPause(boolean enable)
    {
        System.out.println("setPause "+enable);
        if(enable)
        {
            state |= PAUSE;
        }
        else
        {
            if(IS_PAUSE())
            {
                state ^= PAUSE;
            }
        }
    }
    
    public static int GOTO = 0x080;
    public boolean IS_GOTO()      {return ( (state & GOTO) > 0)?true:false;}

    public synchronized void setGoto(boolean enable)
    {
        System.out.println("setGoto "+enable);
        if(enable)
        {
            state |= GOTO;
        }
        else
        {
            if(IS_GOTO())
            {
                state ^= GOTO;
            }
        }
    }
    
    public static int PARSED = 0x100;
    public boolean IS_PARSED()    {return ( (state & PARSED) > 0)?true:false;}
    
    public synchronized void setParsed(boolean enable)
    {
        System.out.println("setParsed "+enable);
        if(enable)
        {
            state |= PARSED;
        }
        else
        {
            if(IS_PARSED())
            {
                state ^= PARSED;
            }
        }
    }
    
    public static int FINISHED = 0x200;
    public boolean IS_FINISHED()  {return ( (state & FINISHED) > 0)?true:false;}
    
    public synchronized void setFinished(boolean enable)
    {
        System.out.println("setFinished "+enable);
        if(enable)
        {
            state |= FINISHED;
        }
        else
        {
            if(IS_FINISHED())
            {
                state ^= FINISHED;
            }
        }
    }
    
    
    
    public synchronized void copy(CoreState s)
    {
        if(!locked)
        {
            this.state = s.state;

            this.packet_readed = s.packet_readed;
            this.packet_in_file = s.packet_in_file;

            this.setCurrent_tv(s.getCurrent_tv());
            this.setComplete_tv(s.getComplete_tv());

            this.devicePath = s.devicePath;
        }
    }

    /**
     * @return the locked
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * @param locked the locked to set
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    /**
     * @return the current_tv
     */
    public double getCurrent_tv() {
        return current_tv;
    }

    /**
     * @param current_tv the current_tv to set
     */
    public void setCurrent_tv(double current_tv) {
        this.current_tv = current_tv;
    }

    /**
     * @return the complete_tv
     */
    public double getComplete_tv() {
        return complete_tv;
    }

    /**
     * @param complete_tv the complete_tv to set
     */
    public void setComplete_tv(double complete_tv) {
        this.complete_tv = complete_tv;
    }
    
}
