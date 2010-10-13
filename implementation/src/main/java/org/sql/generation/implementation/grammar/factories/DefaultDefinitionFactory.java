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

package org.sql.generation.implementation.grammar.factories;

import org.sql.generation.api.common.NullArgumentException;
import org.sql.generation.api.grammar.booleans.BooleanExpression;
import org.sql.generation.api.grammar.builders.definition.ForeignKeyConstraintBuilder;
import org.sql.generation.api.grammar.builders.definition.SchemaDefinitionBuilder;
import org.sql.generation.api.grammar.builders.definition.TableDefinitionBuilder;
import org.sql.generation.api.grammar.builders.definition.TableElementListBuilder;
import org.sql.generation.api.grammar.builders.definition.UniqueConstraintBuilder;
import org.sql.generation.api.grammar.builders.definition.ViewDefinitionBuilder;
import org.sql.generation.api.grammar.common.TableName;
import org.sql.generation.api.grammar.definition.table.CheckConstraint;
import org.sql.generation.api.grammar.definition.table.ColumnDefinition;
import org.sql.generation.api.grammar.definition.table.ConstraintCharacteristics;
import org.sql.generation.api.grammar.definition.table.LikeClause;
import org.sql.generation.api.grammar.definition.table.TableConstraint;
import org.sql.generation.api.grammar.definition.table.TableConstraintDefinition;
import org.sql.generation.api.grammar.definition.view.RegularViewSpecification;
import org.sql.generation.api.vendor.SQLVendor;
import org.sql.generation.implementation.grammar.builders.definition.ForeignKeyConstraintBuilderImpl;
import org.sql.generation.implementation.grammar.builders.definition.SchemaDefinitionBuilderImpl;
import org.sql.generation.implementation.grammar.builders.definition.TableDefinitionBuilderImpl;
import org.sql.generation.implementation.grammar.builders.definition.TableElementListBuilderImpl;
import org.sql.generation.implementation.grammar.builders.definition.UniqueConstraintBuilderImpl;
import org.sql.generation.implementation.grammar.builders.definition.ViewDefinitionBuilderImpl;
import org.sql.generation.implementation.grammar.definition.table.CheckConstraintImpl;
import org.sql.generation.implementation.grammar.definition.table.ColumnDefinitionImpl;
import org.sql.generation.implementation.grammar.definition.table.LikeClauseImpl;
import org.sql.generation.implementation.grammar.definition.table.TableConstraintDefinitionImpl;
import org.sql.generation.implementation.grammar.definition.view.RegularViewSpecificationImpl;

/**
 * 
 * @author Stanislav Muhametsin
 */
public class DefaultDefinitionFactory extends AbstractDefinitionFactory
{

    private final SQLVendor _vendor;

    public DefaultDefinitionFactory( SQLVendor vendor )
    {
        NullArgumentException.validateNotNull( "SQL Vendor", vendor );

        this._vendor = vendor;
    }

    public SchemaDefinitionBuilder createSchemaDefinitionBuilder()
    {
        return new SchemaDefinitionBuilderImpl();
    }

    public TableDefinitionBuilder createTableDefinitionBuilder()
    {
        return new TableDefinitionBuilderImpl();
    }

    public TableElementListBuilder createTableElementListBuilder()
    {
        return new TableElementListBuilderImpl();
    }

    public ColumnDefinition createColumnDefinition( String columnName, String columnDataType, String columnDefault,
        Boolean mayBeNull )
    {
        return new ColumnDefinitionImpl( columnName, columnDataType, columnDefault, mayBeNull );
    }

    public LikeClause createLikeClause( TableName tableName )
    {
        return new LikeClauseImpl( tableName );
    }

    public TableConstraintDefinition createTableConstraintDefinition( String name, TableConstraint constraint,
        ConstraintCharacteristics characteristics )
    {
        return new TableConstraintDefinitionImpl( name, constraint, characteristics );
    }

    public CheckConstraint createCheckConstraint( BooleanExpression check )
    {
        return new CheckConstraintImpl( check );
    }

    public UniqueConstraintBuilder createUniqueConstraintBuilder()
    {
        return new UniqueConstraintBuilderImpl( this._vendor.getColumnsFactory() );
    }

    public ForeignKeyConstraintBuilder createForeignKeyConstraintBuilder()
    {
        return new ForeignKeyConstraintBuilderImpl( this._vendor.getColumnsFactory() );
    }

    public ViewDefinitionBuilder createViewDefinitionBuilder()
    {
        return new ViewDefinitionBuilderImpl();
    }

    public RegularViewSpecification createRegularViewSpecification( String... columnNames )
    {
        return new RegularViewSpecificationImpl( this._vendor.getColumnsFactory().colNames( columnNames ) );
    }

}
