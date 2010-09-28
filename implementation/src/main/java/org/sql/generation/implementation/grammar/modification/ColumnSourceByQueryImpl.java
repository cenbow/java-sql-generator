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

package org.sql.generation.implementation.grammar.modification;

import org.sql.generation.api.common.NullArgumentException;
import org.sql.generation.api.grammar.common.ColumnNameList;
import org.sql.generation.api.grammar.modification.ColumnSourceByQuery;
import org.sql.generation.api.grammar.query.QueryExpression;

/**
 * 
 * @author Stanislav Muhametsin
 */
public class ColumnSourceByQueryImpl extends DynamicColumnSourceImpl<ColumnSourceByQuery>
    implements ColumnSourceByQuery
{

    private final QueryExpression _query;

    public ColumnSourceByQueryImpl( ColumnNameList columnNames, QueryExpression query )
    {
        this( ColumnSourceByQuery.class, columnNames, query );
    }

    protected ColumnSourceByQueryImpl( Class<? extends ColumnSourceByQuery> expressionClass,
        ColumnNameList columnNames, QueryExpression query )
    {
        super( expressionClass, columnNames );
        NullArgumentException.validateNotNull( "query", query );
        this._query = query;
    }

    public QueryExpression getQuery()
    {
        return this._query;
    }

}
