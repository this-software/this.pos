/*
 * Copyright (C) 2017 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos;

import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Milton Cavazos
 */
public class ApplicationSound
{
    
    private static final Logger logger = LogManager.getLogger(ApplicationSound.class.getSimpleName());
    
    private static Clip clip = null;
    
    private static void play(String resourcePath)
    {
        
        clip = null;
        AudioListener listener = new AudioListener();
        
        ApplicationSession.getThreadPoolExecutor().execute(() ->
        {
            try
            {
                clip = AudioSystem.getClip();
                try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                    ApplicationSound.class.getResourceAsStream(resourcePath)))
                {
                    clip.addLineListener(listener);
                    clip.open(audioInputStream);
                    clip.start();
                    listener.waitUntilDone();
                    clip.close();
                }
            }
            catch (LineUnavailableException ex)
            {
                logger.error(ex);
            }
            catch (UnsupportedAudioFileException | IOException | InterruptedException ex)
            {
                logger.error(ex);
            }
        });
        
    }
    
    public static void start()
    {
        ApplicationSound.play("/sounds/on_start.wav");
    }
    
    public static void success()
    {
        ApplicationSound.play("/sounds/success.wav");
    }
    
    public static void info()
    {
        ApplicationSound.play("/sounds/message_info.wav");
    }
    
    public static void error()
    {
        ApplicationSound.play("/sounds/message_error.wav");
    }
    
}

class AudioListener implements LineListener
{
    
    private boolean done = false;
    
    @Override
    public synchronized void update(LineEvent event)
    {
        if (event.getType() == Type.STOP
            || event.getType() == Type.CLOSE)
        {
            done = true;
            notifyAll();
        }
    }
    
    public synchronized void waitUntilDone() throws InterruptedException
    {
        while (!done)
        {
            wait();
        }
    }
    
}