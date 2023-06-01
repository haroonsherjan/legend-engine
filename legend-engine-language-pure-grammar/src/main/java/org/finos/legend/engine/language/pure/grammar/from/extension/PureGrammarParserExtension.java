// Copyright 2020 Goldman Sachs
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.finos.legend.engine.language.pure.grammar.from.extension;

import org.eclipse.collections.impl.utility.ListIterate;
import org.finos.legend.engine.language.pure.grammar.from.extension.data.EmbeddedDataParser;
import org.finos.legend.engine.language.pure.grammar.from.extension.test.assertion.TestAssertionParser;
import org.finos.legend.engine.language.pure.grammar.from.mapping.MappingIncludeParser;
import org.finos.legend.engine.protocol.pure.v1.model.context.EngineErrorType;
import org.finos.legend.engine.shared.core.operational.errorManagement.EngineException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public interface PureGrammarParserExtension
{
    default Iterable<? extends SectionParser> getExtraSectionParsers()
    {
        return Collections.emptyList();
    }

    default Iterable<? extends ConnectionValueParser> getExtraConnectionParsers()
    {
        return Collections.emptyList();
    }

    default Iterable<? extends MappingElementParser> getExtraMappingElementParsers()
    {
        return Collections.emptyList();
    }

    default Iterable<? extends MappingTestInputDataParser> getExtraMappingTestInputDataParsers()
    {
        return Collections.emptyList();
    }

    default Iterable<? extends EmbeddedDataParser> getExtraEmbeddedDataParsers()
    {
        return Collections.emptyList();
    }

    default Iterable<? extends EmbeddedPureParser> getExtraEmbeddedPureParsers()
    {
        return Collections.emptyList();
    }

    default Iterable<? extends TestAssertionParser> getExtraTestAssertionParsers()
    {
        return Collections.emptyList();
    }

    default Iterable<? extends MappingIncludeParser> getExtraMappingIncludeParsers()
    {
        return Collections.emptyList();
    }

    static String parseIncludedStoreType(String type, List<Function<String, String>> processors)
    {
        List<String> results = ListIterate.collect(processors, func -> func.apply(type)).select(Objects::nonNull);
        if (results.isEmpty())
        {
            throw new EngineException("Unsupported included store type: " + type, EngineErrorType.PARSER);
        }
        else if (results.size() > 1)
        {
            throw new EngineException("Too many parsers for provided type: " + type, EngineErrorType.PARSER);
        }
        else
        {
            return results.get(0);
        }
    }

    default List<Function<String, String>> getExtraIncludedStoreParsers()
    {
        return Collections.emptyList();
    }
}
