/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.apache.geode.benchmark.tasks;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;
import java.util.Map;

import benchmark.geode.data.BenchmarkFunction;
import benchmark.geode.data.Portfolio;
import org.yardstickframework.BenchmarkConfiguration;
import org.yardstickframework.BenchmarkDriverAdapter;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.execute.FunctionService;

public class ExecuteFunction extends BenchmarkDriverAdapter implements Serializable {

  private final boolean isValidationEnabled;

  private Region<Long, Portfolio> region;

  public ExecuteFunction(final boolean isValidationEnabled) {
    this.isValidationEnabled = isValidationEnabled;
  }

  @Override
  public void setUp(BenchmarkConfiguration cfg) throws Exception {
    super.setUp(cfg);
    ClientCache cache = ClientCacheFactory.getAnyInstance();
    region = cache.getRegion("region");
  }

  @Override
  public boolean test(final Map<Object, Object> ctx) {
    final Object result =
        FunctionService.onRegion(region).execute(BenchmarkFunction.class.getName()).getResult();

    if (isValidationEnabled) {
      assertNotNull(result);
      assertTrue(result instanceof Portfolio);
    }

    return true;
  }

}
