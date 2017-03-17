/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */
package dis.software.pos.interfaces;

import dis.software.pos.entities.Sale;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Milton Cavazos
 */
public interface ISale extends IGenericHibernate<Sale, Long>
{
    List<Sale> getDeleted();
    List<Sale> findByDateRange(Calendar initialDate, Calendar finalDate);
    Double getDailyCashOutAmount();
}
