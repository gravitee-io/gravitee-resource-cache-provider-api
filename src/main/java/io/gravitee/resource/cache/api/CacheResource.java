/*
 * Copyright © 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.resource.cache.api;

import io.gravitee.gateway.reactive.api.context.GenericExecutionContext;
import io.gravitee.gateway.reactive.api.context.base.BaseExecutionContext;
import io.gravitee.resource.api.AbstractConfigurableResource;
import io.gravitee.resource.api.ResourceConfiguration;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public abstract class CacheResource<C extends ResourceConfiguration> extends AbstractConfigurableResource<C> {

    public abstract Cache getCache(io.gravitee.gateway.api.ExecutionContext ctx);

    /**
     * @deprecated use <code>getCache({@link BaseExecutionContext})</code> instead
     */
    @Deprecated(forRemoval = true)
    public abstract Cache getCache(GenericExecutionContext ctx);

    public abstract Cache getCache(BaseExecutionContext ctx);

    public String keySeparator() {
        return "_";
    }
}
