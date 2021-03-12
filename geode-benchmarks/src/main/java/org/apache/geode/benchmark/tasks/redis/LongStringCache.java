/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.geode.benchmark.tasks.redis;

import org.apache.geode.benchmark.LongRange;

public class LongStringCache {
  private final int offset;
  private final String[] cache;

  public LongStringCache(final LongRange longRange) {
    offset = (int) longRange.getMin();
    cache = new String[(int) (longRange.getMax() - offset)];
    longRange.forEach(i -> cache[(int) i] = String.valueOf(i));
  }

  public String valueOf(final long value) {
    return cache[(int) (value - offset)];
  }
}