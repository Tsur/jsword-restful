/**
 * Distribution License:
 * JSword is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License, version 2.1 as published by
 * the Free Software Foundation. This program is distributed in the hope
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * The License is available on the internet at:
 *       http://www.gnu.org/copyleft/lgpl.html
 * or by writing to:
 *      Free Software Foundation, Inc.
 *      59 Temple Place - Suite 330
 *      Boston, MA 02111-1307, USA
 *
 * Copyright: 2005
 *     The copyright to this program is held by it's authors.
 *
 * ID: $Id: IndexManagerFactory.java 1772 2008-03-08 02:37:36Z dmsmith $
 */
package org.crosswire.jsword.index;

import java.io.IOException;

import org.crosswire.common.util.Logger;
import org.crosswire.common.util.PluginUtil;

/**
 * A Factory class for IndexManagers.
 *
 * @see gnu.lgpl.License for license details.
 *      The copyright to this program is held by it's authors.
 * @author Joe Walker [joe at eireneh dot com]

 */
public final class IndexManagerFactory
{
    /**
     * Prevent instantiation
     */
    private IndexManagerFactory()
    {
    }

    /**
     * Create a new IndexManager.
     */
    public static IndexManager getIndexManager()
    {
        return instance;
    }

    /**
     * The singleton
     */
    private static IndexManager instance;

    /**
     * The log stream
     */
    private static final Logger log = Logger.getLogger(IndexManagerFactory.class);

    /**
     * Setup the instance
     */
    static
    {
        try
        {
            instance = (IndexManager) PluginUtil.getImplementation(IndexManager.class);
        }
        catch (IOException e)
        {
            log.error("createIndexManager failed", e); //$NON-NLS-1$
        }
        catch (ClassCastException e)
        {
            log.error("createIndexManager failed", e); //$NON-NLS-1$
        }
        catch (ClassNotFoundException e)
        {
            log.error("createIndexManager failed", e); //$NON-NLS-1$
        }
        catch (IllegalAccessException e)
        {
            log.error("createIndexManager failed", e); //$NON-NLS-1$
        }
        catch (InstantiationException e)
        {
            log.error("createIndexManager failed", e); //$NON-NLS-1$
        }
    }
}
