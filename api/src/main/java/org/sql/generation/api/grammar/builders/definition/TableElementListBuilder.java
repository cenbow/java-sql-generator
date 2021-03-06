/*
 * Copyright (c) 2010, Stanislav Muhametsin. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.sql.generation.api.grammar.builders.definition;

import java.util.List;

import org.sql.generation.api.grammar.builders.AbstractBuilder;
import org.sql.generation.api.grammar.definition.table.TableElement;
import org.sql.generation.api.grammar.definition.table.TableElementList;

/**
 * This is the builder for table element list used in {@code CREATE TABLE} statements.
 * 
 * @author Stanislav Muhametsin
 */
public interface TableElementListBuilder
    extends AbstractBuilder<TableElementList>
{

    /**
     * Adds the table element to this list.
     * 
     * @param element The table element to add to this list.
     * @return This builder.
     */
    public TableElementListBuilder addTableElement( TableElement element );

    /**
     * Returns all the elements that this builder has.
     * 
     * @return All the elements that this builder has.
     */
    public List<TableElement> getTableElements();
}
