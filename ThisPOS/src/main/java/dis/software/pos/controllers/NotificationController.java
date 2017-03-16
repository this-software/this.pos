/*
 * Copyright (C) 2017 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.controllers;

import dis.software.pos.entities.Notification;
import dis.software.pos.interfaces.INotification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase controladora de acceso a las notificaciones
 * @author Milton Cavazos
 */
@Repository
@Transactional
public class NotificationController extends GenericHibernateController<Notification, Long>
    implements INotification
{
    
    private static final Logger logger = LogManager.getLogger(NotificationController.class.getSimpleName());
    
}
