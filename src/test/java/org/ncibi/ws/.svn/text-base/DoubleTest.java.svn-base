package org.ncibi.ws;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.junit.Test;


public class DoubleTest
{
    @Test
    public void testDouble()
    {
        Double d = 0.00000000001/0.000003;
        NumberFormat formatter = new DecimalFormat("#0.0E0");
        Double d2 = new Double(formatter.format(d));
        System.out.println("d = " + d);
        System.out.println("d2 = " + d2);
    }
}
