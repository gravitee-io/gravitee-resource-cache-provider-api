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

    /**
     * Read a value preserving binary fidelity.
     * <p>
     * Implementations that distinguish between text and binary storage (e.g. Redis) MUST override this
     * method and return the value as a {@code byte[]} on the {@link Element#value()} of the returned
     * element. Backends that do not distinguish (e.g. in-memory) may rely on the default delegation
     * to {@link #getAsync(Object)}.
     * <p>
     * <strong>Backends that store text-typed values and do not override this method will return an
     * {@link Element} whose {@link Element#value()} is the backend's native type</strong> (e.g.
     * {@code String}), not {@code byte[]}. Callers must be prepared to handle this — typically by
     * version-sniffing the payload (see the {@code gravitee-policy-cache} {@code CacheFrame.isLegacyFormat}
     * pattern) rather than blindly casting to {@code byte[]}.
     *
     * @param key the cache key
     * @return a future completing with the element, or {@code null} if absent
     * @since 2.2.0
     */
    default Future<Element> getBinaryAsync(Object key) {
        return getAsync(key);
    }

    /**
     * Write a value preserving binary fidelity.
     * <p>
     * The {@link Element#value()} is expected to be a {@code byte[]}. Implementations that distinguish
     * between text and binary storage (e.g. Redis) MUST override this method and persist the raw bytes
     * without character-encoding transformations. Backends that do not distinguish may rely on the
     * default delegation to {@link #putAsync(Element)}.
     * <p>
     * <strong>If the backend has not overridden this method, the default delegation to
     * {@link #putAsync(Element)} may fail at runtime</strong> (e.g. with {@code ClassCastException})
     * when the underlying implementation expects a {@code String}-typed value. Operators upgrading
     * a cache backend across this API version should ensure the backend implements this method
     * before routing binary writes to it.
     *
     * @param element the element to store; {@code element.value()} must be a {@code byte[]}
     * @return a future completing when the write is done
     * @since 2.2.0
     */
    default Future<Void> putBinaryAsync(Element element) {
        return putAsync(element);
    }
}
