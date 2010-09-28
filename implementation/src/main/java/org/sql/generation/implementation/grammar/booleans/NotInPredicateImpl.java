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

package org.sql.generation.implementation.grammar.booleans;

import java.util.List;

import org.sql.generation.api.grammar.booleans.NotInPredicate;
import org.sql.generation.api.grammar.common.NonBooleanExpression;

/**
 * 
 * @author Stanislav Muhametsin
 */
public class NotInPredicateImpl extends MultiPredicateImpl<NotInPredicate>
    implements NotInPredicate
{

    public NotInPredicateImpl( NonBooleanExpression left, List<NonBooleanExpression> rights )
    {
        this( NotInPredicate.class, left, rights );
    }

    protected NotInPredicateImpl( Class<? extends NotInPredicate> predicateClass, NonBooleanExpression left,
        List<NonBooleanExpression> rights )
    {
        super( predicateClass, left, rights );
    }

    public NotInPredicateImpl( NonBooleanExpression left, NonBooleanExpression... rights )
    {
        this( NotInPredicate.class, left, rights );
    }

    protected NotInPredicateImpl( Class<? extends NotInPredicate> predicateClass, NonBooleanExpression left,
        NonBooleanExpression... rights )
    {
        super( predicateClass, left, rights );
    }

}
