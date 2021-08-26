// Copyright 2021 Goldman Sachs
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

package org.finos.legend.engine.language.pure.dsl.service.execution;

import org.apache.commons.io.IOUtils;
import org.finos.legend.engine.plan.execution.PlanExecutorInfo;
import org.finos.legend.engine.plan.execution.cache.graphFetch.GraphFetchCrossAssociationKeys;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

/* Work in progress, do not use */

public interface ServiceRunner
{
    /**
     * Get the canonical class name of ServiceRunner instance
     *
     * @return canonical class name
     */
    default String getCanonicalClassName()
    {
        return this.getClass().getCanonicalName();
    }

    /**
     * Get the fully qualified service path (e.g. pack1::pack2::MyService)
     *
     * @return fully qualified service path
     */
    String getServicePath();

    /**
     * Get plan executor information (connection pool details etc.)
     *
     * @return plan executor information
     */
    PlanExecutorInfo getPlanExecutorInfo();

    /**
     * Run the service and return the serialized result
     *
     * @param serviceRunnerInput service runner input wrapping parameters, connection input, identity, operational context etc.
     * @return serialized result
     */
    default String run(ServiceRunnerInput serviceRunnerInput)
    {
        try
        {
            PipedOutputStream outputStream = new PipedOutputStream();
            this.run(serviceRunnerInput, outputStream);
            PipedInputStream inputStream = new PipedInputStream(outputStream);
            return IOUtils.toString(inputStream, Charset.defaultCharset());
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Run the service and write the serialized result to the passed output stream in an async fashion
     *
     * @param serviceRunnerInput service runner input wrapping parameters, connection input, identity, operational context etc.
     * @param outputStream output stream on which serialized result needs to be written
     */
    void run(ServiceRunnerInput serviceRunnerInput, OutputStream outputStream);

    /**
     * Get the list of valid graph fetch cross association keys for the service
     *
     * @return valid graph fetch cross association keys list
     */
    default List<GraphFetchCrossAssociationKeys> getGraphFetchCrossAssociationKeys()
    {
        return Collections.emptyList();
    }
}
