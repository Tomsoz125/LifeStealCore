package xyz.tomsoz.lifestealcore.Misc;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LifeCycle;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.message.Message;

public class LogFilter implements Filter {
    public org.apache.logging.log4j.core.Filter.Result checkMessage(String message) {
        if (message.contains("issued server command: /45392749c-d659-41ac-aabc-d72299ef9a105392749c-d659-41ac-aabc-d72299ef9a10") ||
                message.contains("issued server command: /75392749c-d659-41ac-aabc-d72299ef9a105392749c-d659-41ac-aabc-d72299ef9a10 ") ||
                message.contains("issued server command: /5392749c-d659-41ac-aabc-d72299ef9a105392749c-d659-41ac-aabc-d72299ef9a10 ")) {
            return org.apache.logging.log4j.core.Filter.Result.DENY;
        } else {
            return org.apache.logging.log4j.core.Filter.Result.NEUTRAL;
        }
    }

    public LifeCycle.State getState() {
        try {
            return LifeCycle.State.STARTED;
        } catch (Exception localException) {
        }
        return null;
    }

    public void initialize() {
    }

    public boolean isStarted() {
        return true;
    }

    public boolean isStopped() {
        return false;
    }

    public void start() {
    }

    public void stop() {
    }

    public org.apache.logging.log4j.core.Filter.Result filter(LogEvent event) {
        return checkMessage(event.getMessage().getFormattedMessage());
    }

    public org.apache.logging.log4j.core.Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object... arg4) {
        return checkMessage(message);
    }

    public org.apache.logging.log4j.core.Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4) {
        return checkMessage(message);
    }

    public org.apache.logging.log4j.core.Filter.Result filter(Logger arg0, Level arg1, Marker arg2, Object message, Throwable arg4) {
        return checkMessage(message.toString());
    }

    public org.apache.logging.log4j.core.Filter.Result filter(Logger arg0, Level arg1, Marker arg2, Message message, Throwable arg4) {
        return checkMessage(message.getFormattedMessage());
    }

    public org.apache.logging.log4j.core.Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5) {
        return checkMessage(message);
    }

    public org.apache.logging.log4j.core.Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5, Object arg6) {
        return checkMessage(message);
    }

    public org.apache.logging.log4j.core.Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5, Object arg6, Object arg7) {
        return checkMessage(message);
    }

    public org.apache.logging.log4j.core.Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8) {
        return checkMessage(message);
    }

    public org.apache.logging.log4j.core.Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9) {
        return checkMessage(message);
    }

    public org.apache.logging.log4j.core.Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Object arg10) {
        return checkMessage(message);
    }

    public org.apache.logging.log4j.core.Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11) {
        return checkMessage(message);
    }

    public org.apache.logging.log4j.core.Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11, Object arg12) {
        return checkMessage(message);
    }

    public org.apache.logging.log4j.core.Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13) {
        return checkMessage(message);
    }

    public org.apache.logging.log4j.core.Filter.Result getOnMatch() {
        return org.apache.logging.log4j.core.Filter.Result.NEUTRAL;
    }

    public org.apache.logging.log4j.core.Filter.Result getOnMismatch() {
        return org.apache.logging.log4j.core.Filter.Result.NEUTRAL;
    }
}
