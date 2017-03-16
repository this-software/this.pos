/*
 * Copyright (C) 2017 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos;

import javax.print.DocPrintJob;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;

/**
 *
 * @author Milton Cavazos
 */
public class PrintJobWatcher
{
    
    boolean done = false;

    public PrintJobWatcher(DocPrintJob job)
    {
        job.addPrintJobListener(new PrintJobAdapter()
        {
            @Override
            public void printJobCanceled(PrintJobEvent pje) {
                allDone();
            }
            @Override
            public void printJobCompleted(PrintJobEvent pje) {
                allDone();
            }
            @Override
            public void printJobFailed(PrintJobEvent pje) {
                allDone();
            }
            @Override
            public void printJobNoMoreEvents(PrintJobEvent pje) {
                allDone();
            }

            void allDone()
            {
                synchronized (PrintJobWatcher.this)
                {
                    done = true;
                    System.out.println("Printing done");
                    PrintJobWatcher.this.notify();
                }
            }
        });
    }

    public synchronized void waitForDone()
    {
        try
        {
            while (!done)
            {
                wait();
            }
        }
        catch (InterruptedException e)
        {
            
        }
    }
    
}
