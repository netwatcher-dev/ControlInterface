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

package exceptionPackage;


public class ControlException extends Exception
{
    public static final int STATE_NO_ERROR = 0;
    public static final int STATE_NO_DEVICE_SELECTED = 1;
    public static final int STATE_UNKNOWN_CAPTURE_DEVICE = 2;
    public static final int STATE_UNKNOWN_FILE = 3;
    public static final int STATE_NOT_ALLOWED_IN_FILE_MODE = 4;
    public static final int STATE_NOT_ALLOWED_IN_DEVICE_MODE = 5;
    public static final int STATE_MUST_STOP_CAPTURE_BEFORE = 6;
    public static final int STATE_CAPTURE_NOT_STARTED = 7;
    public static final int STATE_PCAP_ERROR = 8;
    public static final int STATE_FAILED_TO_RECEIVED_STRING = 9;
    public static final int STATE_SERVER_ERROR = 10;
    public static final int STATE_PARAMETER_ERROR = 11;
    
    public static final int STATE_NO_FILE_SELECTED = 12;
    public static final int STATE_ARG_WRONG_OR_MISSING = 13;
    public static final int STATE_VALUE_POSITIVE_INVALID = 18;
    public static final int STATE_WRONG_BPF = 19;
    public static final int STATE_RECORD_ALREADY_STARTED = 20;
    public static final int STATE_NOTHING_SELECTED = 21;
    public static final int STATE_DATALINK_NOT_MANAGED = 22;
    public static final int STATE_NO_MORE_PORT_AVAILABLE = 23;
    public static final int STATE_NOT_IMPLEMENTED_YET = 24;
    public static final int STATE_NOT_RUNNING = 25;
    public static final int STATE_NOT_IN_PAUSE = 26;
    public static final int STATE_ZERO_VALUE = 27;
    public static final int STATE_ALREADY_RUNNING = 28;
    public static final int STATE_INVALID_IDENTIFIER = 29;
    
    private String message;
    
    private ControlException()
    {}
    
    public ControlException(int code)
    {
        super("Core Control Exception");
        
        switch(code)
        {
            case STATE_NO_ERROR:
                message = "no error";
                break;
            case STATE_NO_DEVICE_SELECTED:
                message = "no device selected";
                break;
            case STATE_UNKNOWN_CAPTURE_DEVICE:
                message = "try to open an unknown device";
                break;
            case STATE_UNKNOWN_FILE:
                message = "try to open an unknown file";
                break;
            case STATE_NOT_ALLOWED_IN_FILE_MODE:
                message = "forbiden action with a file";
                break;
            case STATE_NOT_ALLOWED_IN_DEVICE_MODE:
                message = "forbiden acton with a device";
                break;
            case STATE_MUST_STOP_CAPTURE_BEFORE:
                message = "device or file already selected, must be stop before";
                break;
            case STATE_CAPTURE_NOT_STARTED:
                message = "no capture was started";
                break;
            case STATE_FAILED_TO_RECEIVED_STRING:
                message = "server failed to received string";
                break;
            case STATE_SERVER_ERROR:
                message = "server internal error, see log";
                break;
            case STATE_PARAMETER_ERROR:
                message = "parameter error, check value";
                break;
            case STATE_NO_FILE_SELECTED:
                message = "no file selected";
                break;
            case STATE_ARG_WRONG_OR_MISSING:
                message = "argument is wrong or missing";
                break;
            case STATE_VALUE_POSITIVE_INVALID:
                message = "positive value invalid";
                break;
            case STATE_WRONG_BPF:
                message = "wrong BPF filter";
                break;
            case STATE_RECORD_ALREADY_STARTED:
                message = "a record is already running";
                break;
            case STATE_NOTHING_SELECTED:
                message = "no file or interface is selected";
                break;
            case STATE_DATALINK_NOT_MANAGED:
                message = "the datalink layer is not supported";
                break;
            case STATE_NO_MORE_PORT_AVAILABLE:
                message = "no more port available";
                break;
            case STATE_NOT_IMPLEMENTED_YET:
                message = "not impleted yet";
                break;
            case STATE_NOT_RUNNING:
                message = "the system is not running";
                break;
            case STATE_NOT_IN_PAUSE:
                message = "the system is not in pause";
                break;
            case STATE_ZERO_VALUE:
                message = "zero value is not allowed";
                break;
            case STATE_ALREADY_RUNNING:
                message = "the system is already running";
                break;
            case STATE_INVALID_IDENTIFIER:
                message = "the identifier is unknown or invalid";
            default:
                message = "unknwon error code : "+code;
        }
    }
    
    @Override
    public String getMessage()
    {
        return this.message;
    }
    
    @Override
    public String toString()
    {
        return message;
    }
    
}
