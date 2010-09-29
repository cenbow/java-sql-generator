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

package org.sql.generation.implementation.transformation;

import java.util.Iterator;

import org.sql.generation.api.grammar.common.SQLConstants;
import org.sql.generation.api.grammar.common.ValueExpression;
import org.sql.generation.api.grammar.modification.ColumnSource.Defaults;
import org.sql.generation.api.grammar.modification.ColumnSourceByQuery;
import org.sql.generation.api.grammar.modification.ColumnSourceByValues;
import org.sql.generation.api.grammar.modification.DeleteBySearch;
import org.sql.generation.api.grammar.modification.DynamicColumnSource;
import org.sql.generation.api.grammar.modification.InsertStatement;
import org.sql.generation.api.grammar.modification.SetClause;
import org.sql.generation.api.grammar.modification.TargetTable;
import org.sql.generation.api.grammar.modification.UpdateBySearch;
import org.sql.generation.api.grammar.modification.UpdateSource.Default;
import org.sql.generation.api.grammar.modification.UpdateSource.Null;
import org.sql.generation.api.grammar.modification.UpdateSourceByExpression;
import org.sql.generation.implementation.transformation.spi.SQLProcessorAggregator;

/**
 * 
 * @author Stanislav Muhametsin
 */
public class ModificationProcessing
{

    public static abstract class DynamicColumnSourceProcessor<SourceType extends DynamicColumnSource> extends
        AbstractProcessor<SourceType>
    {
        public DynamicColumnSourceProcessor( Class<? extends SourceType> realType )
        {
            super( realType );
        }

        @Override
        protected void doProcess( SQLProcessorAggregator processor, SourceType object, StringBuilder builder )
        {
            if( object.getColumnNames() != null )
            {
                builder.append( SQLConstants.OPEN_PARENTHESIS );
                processor.process( object.getColumnNames(), builder );
                builder.append( SQLConstants.CLOSE_PARENTHESIS );
            }
            this.doProcessColumnSource( processor, object, builder );
        }

        protected abstract void doProcessColumnSource( SQLProcessorAggregator processor, SourceType object,
            StringBuilder builder );

    }

    public static class ColumnSourceByQueryProcessor extends DynamicColumnSourceProcessor<ColumnSourceByQuery>
    {

        public ColumnSourceByQueryProcessor()
        {
            this( ColumnSourceByQuery.class );
        }

        protected ColumnSourceByQueryProcessor( Class<? extends ColumnSourceByQuery> realType )
        {
            super( realType );
        }

        @Override
        protected void doProcessColumnSource( SQLProcessorAggregator processor, ColumnSourceByQuery object,
            StringBuilder builder )
        {
            builder.append( SQLConstants.NEWLINE );
            processor.process( object.getQuery(), builder );
        }
    }

    public static class ColumnSourceByValuesProcessor extends DynamicColumnSourceProcessor<ColumnSourceByValues>
    {

        public ColumnSourceByValuesProcessor()
        {
            this( ColumnSourceByValues.class );
        }

        public ColumnSourceByValuesProcessor( Class<? extends ColumnSourceByValues> realType )
        {
            super( realType );
        }

        @Override
        protected void doProcessColumnSource( SQLProcessorAggregator processor, ColumnSourceByValues object,
            StringBuilder builder )
        {
            builder.append( SQLConstants.NEWLINE ).append( "VALUES" ).append( SQLConstants.OPEN_PARENTHESIS );
            Iterator<ValueExpression> iter = object.getValues().iterator();
            while( iter.hasNext() )
            {
                processor.process( iter.next(), builder );
                if( iter.hasNext() )
                {
                    builder.append( SQLConstants.COMMA ).append( SQLConstants.TOKEN_SEPARATOR );
                }
            }
            builder.append( SQLConstants.CLOSE_PARENTHESIS );
        }
    }

    public static class ColumnSourceDefaultsProcessor extends AbstractProcessor<Defaults>
    {

        public ColumnSourceDefaultsProcessor()
        {
            this( Defaults.class );
        }

        public ColumnSourceDefaultsProcessor( Class<? extends Defaults> realType )
        {
            super( realType );
        }

        @Override
        protected void doProcess( SQLProcessorAggregator processor, Defaults object, StringBuilder builder )
        {
            builder.append( SQLConstants.TOKEN_SEPARATOR ).append( "DEFAULT VALUES" );
        }

    }

    public static class DeleteBySearchProcessor extends AbstractProcessor<DeleteBySearch>
    {
        public DeleteBySearchProcessor()
        {
            this( DeleteBySearch.class );
        }

        public DeleteBySearchProcessor( Class<? extends DeleteBySearch> realType )
        {
            super( realType );
        }

        @Override
        protected void doProcess( SQLProcessorAggregator processor, DeleteBySearch object, StringBuilder builder )
        {
            builder.append( "DELETE FROM" ).append( SQLConstants.TOKEN_SEPARATOR );
            processor.process( object.getTargetTable(), builder );
            QueryProcessing.processOptionalBooleanExpression( processor, builder, object.getWhere(),
                SQLConstants.NEWLINE, SQLConstants.WHERE );
        }

    }

    public static class InsertStatementProcessor extends AbstractProcessor<InsertStatement>
    {
        public InsertStatementProcessor()
        {
            this( InsertStatement.class );
        }

        public InsertStatementProcessor( Class<? extends InsertStatement> realType )
        {
            super( realType );
        }

        @Override
        protected void doProcess( SQLProcessorAggregator processor, InsertStatement object, StringBuilder builder )
        {
            builder.append( "INSERT INTO" ).append( SQLConstants.TOKEN_SEPARATOR );
            processor.process( object.getTableName(), builder );
            builder.append( SQLConstants.TOKEN_SEPARATOR );
            processor.process( object.getColumnSource(), builder );
        }
    }

    public static class SetClauseProcessor extends AbstractProcessor<SetClause>
    {
        public SetClauseProcessor()
        {
            this( SetClause.class );
        }

        public SetClauseProcessor( Class<? extends SetClause> realType )
        {
            super( realType );
        }

        @Override
        protected void doProcess( SQLProcessorAggregator processor, SetClause object, StringBuilder builder )
        {
            builder.append( object.getUpdateTarget() ).append( SQLConstants.TOKEN_SEPARATOR ).append( "=" )
                .append( SQLConstants.TOKEN_SEPARATOR );
            processor.process( object.getUpdateSource(), builder );
        }
    }

    public static class TargetTableProcessor extends AbstractProcessor<TargetTable>
    {
        public TargetTableProcessor()
        {
            this( TargetTable.class );
        }

        protected TargetTableProcessor( Class<? extends TargetTable> realType )
        {
            super( realType );
        }

        @Override
        protected void doProcess( SQLProcessorAggregator processor, TargetTable object, StringBuilder builder )
        {
            Boolean isOnly = object.isOnly();
            if( isOnly )
            {
                builder.append( "ONLY" ).append( SQLConstants.OPEN_PARENTHESIS );
            }
            processor.process( object.getTableName(), builder );
            if( isOnly )
            {
                builder.append( SQLConstants.CLOSE_PARENTHESIS );
            }
        }
    }

    public static class UpdateBySearchProcessor extends AbstractProcessor<UpdateBySearch>
    {
        public UpdateBySearchProcessor()
        {
            this( UpdateBySearch.class );
        }

        protected UpdateBySearchProcessor( Class<? extends UpdateBySearch> realType )
        {
            super( realType );
        }

        @Override
        protected void doProcess( SQLProcessorAggregator processor, UpdateBySearch object, StringBuilder builder )
        {
            builder.append( "UPDATE" ).append( SQLConstants.TOKEN_SEPARATOR );
            processor.process( object.getTargetTable(), builder );
            builder.append( SQLConstants.NEWLINE ).append( "SET" ).append( SQLConstants.TOKEN_SEPARATOR );
            Iterator<SetClause> iter = object.getSetClauses().iterator();
            while( iter.hasNext() )
            {
                processor.process( iter.next(), builder );
                if( iter.hasNext() )
                {
                    builder.append( SQLConstants.COMMA ).append( SQLConstants.TOKEN_SEPARATOR );
                }
            }
            QueryProcessing.processOptionalBooleanExpression( processor, builder, object.getWhere(),
                SQLConstants.NEWLINE, SQLConstants.WHERE );
        }
    }

    public static class UpdateSourceByExpressionProcessor extends AbstractProcessor<UpdateSourceByExpression>
    {
        public UpdateSourceByExpressionProcessor()
        {
            this( UpdateSourceByExpression.class );
        }

        public UpdateSourceByExpressionProcessor( Class<? extends UpdateSourceByExpression> realType )
        {
            super( realType );
        }

        @Override
        protected void doProcess( SQLProcessorAggregator processor, UpdateSourceByExpression object,
            StringBuilder builder )
        {
            processor.process( object.getValueExpression(), builder );
        }
    }

    public static class UpdateSourceDefaultProcessor extends AbstractProcessor<Default>
    {
        public UpdateSourceDefaultProcessor()
        {
            super( Default.class );
        }

        @Override
        protected void doProcess( SQLProcessorAggregator processor, Default object, StringBuilder builder )
        {
            builder.append( "DEFAULT" );
        }
    }

    public static class UpdateSourceNullProcessor extends AbstractProcessor<Null>
    {
        public UpdateSourceNullProcessor()
        {
            super( Null.class );
        }

        @Override
        protected void doProcess( SQLProcessorAggregator processor, Null object, StringBuilder builder )
        {
            builder.append( "NULL" );
        }
    }
}
