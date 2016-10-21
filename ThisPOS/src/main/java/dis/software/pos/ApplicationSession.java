/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.software.pos;

import dis.software.pos.entities.User;

/**
 * Clase de sesión que contiene valores del usuario que se encuentra firmado
 * en el sistema ThisPOS
 * @author Milton Cavazos
 */
public class ApplicationSession
{
    
    private static User user = null;
    
    public ApplicationSession() {}

    
    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        ApplicationSession.user = user;
    }
    
}
