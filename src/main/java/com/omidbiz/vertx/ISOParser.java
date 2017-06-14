package com.omidbiz.vertx;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;

/**
 * @author omidp
 *         <p>
 *         http://jimmod.com/blog/2011/07/26/jimmys-blog-iso-8583-tutorial-build
 *         -and-parse-iso-message-using-jpos-library/
 *         </p>
 *         <p>
 *         https://en.wikipedia.org/wiki/ISO_8583
 *         </p>
 */
public class ISOParser
{

    private final GenericPackager packager;
    private final ISOMsg isoMsg;

    public ISOParser() throws ISOException
    {
        InputStream is = null;
        try
        {
            is = getClass().getResourceAsStream("/basic.xml");
            this.packager = new GenericPackager(is);
            this.isoMsg = new ISOMsg();
            isoMsg.setPackager(packager);
        }
        finally
        {
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                }
            }
        }
    }

    public String pack(String mti, String pc, String amountTransaction, String stan, String terminalCode, String acceptorCode, String mac)
            throws ISOException, UnsupportedEncodingException
    {
        isoMsg.setMTI(mti);
        isoMsg.set(3, "201234");
        isoMsg.set(4, "10000");
        isoMsg.set(7, "110722180");
        isoMsg.set(11, "123456");
        isoMsg.set(41, terminalCode);
        isoMsg.set(44, "A5DFGR");
        isoMsg.set(105, "ABCDEFGHIJ 1234567890");        
        return packAsString();
    }

    public byte[] pack() throws ISOException
    {
        logISOMsg(isoMsg);
        return isoMsg.pack();
    }

    public String packAsString() throws ISOException, UnsupportedEncodingException
    {
        logISOMsg(isoMsg);
        return new String(isoMsg.pack(), "UTF-8");
    }

    public ISOMsg unpack(String isoContent) throws UnsupportedEncodingException, ISOException
    {
        ISOMsg msg = new ISOMsg();
        msg.setPackager(packager);
        msg.unpack(isoContent.getBytes("UTF-8"));
        logISOMsg(msg);
        return msg;
    }

    public ISOMsg getIsoMsg()
    {
        return isoMsg;
    }

    private void logISOMsg(ISOMsg msg)
    {
        System.out.println("----ISO MESSAGE-----");
        try
        {
            System.out.println("  MTI : " + msg.getMTI());
            for (int i = 1; i <= msg.getMaxField(); i++)
            {
                if (msg.hasField(i))
                {
                    System.out.println("    Field-" + i + " : " + msg.getString(i));
                }
            }
        }
        catch (ISOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            System.out.println("--------------------");
        }

    }
}
