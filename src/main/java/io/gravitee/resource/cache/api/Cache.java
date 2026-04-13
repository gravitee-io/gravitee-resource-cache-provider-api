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

import io.vertx.core.Future;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface Cache {
    String getName();

    Object getNativeCache();

    Element get(Object key);

    void put(Element element);

    void evict(Object key);

    void clear();

    default Future<Element> getAsync(Object key) {
        try {
            return Future.succeededFuture(get(key));
        } catch (Exception e) {
            return Future.failedFuture(e);
        }
    }

    default Future<Void> putAsync(Element element) {
        try {
            put(element);
            return Future.succeededFuture();
        } catch (Exception e) {
            return Future.failedFuture(e);
        }
    }

    default Future<Void> evictAsync(Object key) {
        try {
            evict(key);
            return Future.succeededFuture();
        } catch (Exception e) {
            return Future.failedFuture(e);
        }
    }

    default Future<Void> clearAsync() {
        try {
            clear();
            return Future.succeededFuture();
        } catch (Exception e) {
            return Future.failedFuture(e);
        }
    }
}
