/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.software.pos.interfaces;

import dis.software.pos.entities.Profile;

/**
 *
 * @author Milton Cavazos
 */
public interface IProfile extends IGenericHibernate<Profile, Long>
{
    String getNextCode();
}
