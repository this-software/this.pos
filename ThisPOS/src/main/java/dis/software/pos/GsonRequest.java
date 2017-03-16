/*
 * Copyright (C) 2017 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Milton Cavazos
 * @param <T>
 */
public class GsonRequest<T>
{
    
    private static final Logger logger = LogManager.getLogger(GsonRequest.class.getSimpleName());
 
    private Class<T> clazz;
    private String link = null;
    
    /**
     * Método constructor de la clase
     */
    public GsonRequest() {}
    
    /**
     * Método constructor de la clase
     * @param clazz Clase para la petición
     */
    public GsonRequest(Class<T> clazz)
    {
        
        this.clazz = clazz;
        
    }
    
    /**
     * Método constructor de la clase
     * @param clazz Clase para la petición
     * @param link Enlace donde se harán las peticiones
     */
    public GsonRequest(Class<T> clazz, String link)
    {
        
        this.clazz = clazz;
        this.link = link;
        
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    
    /**
     * Método para realizar una petición GET al servidor
     * @return Objeto genérico
     */
    public T get()
    {
        
        T entity = null;
        try
        {
            URL url = new URL(link.concat(Application.getSetting().getStoreCode()));
            
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            
            if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK)
            {
                throw new RuntimeException("HTTP GET request failed with error code : "
                    + httpURLConnection.getResponseCode());
            }
            
            JsonReader jsonReader = new JsonReader(
                new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
            
            GsonBuilder builder = new GsonBuilder();
            builder.serializeNulls();
            
            Gson gson = builder.create();
            entity = gson.fromJson(jsonReader, clazz);
            
            httpURLConnection.disconnect();
        }
        catch (MalformedURLException ex)
        {
            logger.error("Error in url", ex);
        }
        catch (IOException ex)
        {
            logger.error("Error reading json", ex);
        }
        return entity;
        
    }
    
}
