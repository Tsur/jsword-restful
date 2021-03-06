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
 * ID: $Id: TagBuilder.java 1466 2007-07-02 02:48:09Z dmsmith $
 */
package org.crosswire.jsword.book.filter.gbf;

/**
 * A class that packages start and end Tags together.
 *
 * @see gnu.lgpl.License for license details.
 *      The copyright to this program is held by it's authors.
 * @author Joe Walker [joe at eireneh dot com]
 */
public interface TagBuilder
{
    /**
     * Is the <code>name</code> one that we can create a Tag for?.
     * If so then we return a Tag, but if not we return null.
     * @param name The tag name to test
     * @return a new tag or null if the name isn't one we know about
     */
    Tag createTag(String name);
}
