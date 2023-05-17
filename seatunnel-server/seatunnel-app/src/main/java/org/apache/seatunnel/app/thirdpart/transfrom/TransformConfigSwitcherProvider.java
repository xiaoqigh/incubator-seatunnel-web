/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.seatunnel.app.thirdpart.transfrom;

import org.apache.seatunnel.app.domain.request.job.transform.Transform;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

public enum TransformConfigSwitcherProvider {
    INSTANCE;

    private final Map<Transform, TransformConfigSwitcher> configSwitcherCache;

    TransformConfigSwitcherProvider() {
        ServiceLoader<TransformConfigSwitcher> loader =
                ServiceLoader.load(TransformConfigSwitcher.class);
        configSwitcherCache = new ConcurrentHashMap<>();

        for (TransformConfigSwitcher switcher : loader) {
            configSwitcherCache.put(switcher.getTransform(), switcher);
        }
    }

    public TransformConfigSwitcher getTransformConfigSwitcher(Transform name) {
        return configSwitcherCache.get(name);
    }
}
