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
package org.apache.geode.benchmark.topology;

import static org.apache.geode.benchmark.parameters.Utils.addToTestConfig;
import static org.apache.geode.benchmark.topology.Roles.CLIENT;
import static org.apache.geode.benchmark.topology.Roles.LOCATOR;
import static org.apache.geode.benchmark.topology.Roles.SERVER;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.geode.benchmark.parameters.GcLoggingParameters;
import org.apache.geode.benchmark.parameters.GcParameters;
import org.apache.geode.benchmark.parameters.HeapParameters;
import org.apache.geode.benchmark.parameters.JvmParameters;
import org.apache.geode.benchmark.parameters.ProfilerParameters;
import org.apache.geode.benchmark.tasks.StartClient;
import org.apache.geode.benchmark.tasks.StartLocator;
import org.apache.geode.benchmark.tasks.StartServer;
import org.apache.geode.perftest.TestConfig;

public class ClientServerTopology {
  private static final int NUM_LOCATORS = 1;
  private static final int NUM_SERVERS = 2;
  private static final int NUM_CLIENTS = 1;
  private static final String WITH_SSL_ARGUMENT = "-DwithSsl=true";
  private static final String WITH_SECURITY_MANAGER_ARGUMENT = "-DwithSecurityManager=true";

  public static void configure(TestConfig testConfig) {
    testConfig.role(LOCATOR, NUM_LOCATORS);
    testConfig.role(SERVER, NUM_SERVERS);
    testConfig.role(CLIENT, NUM_CLIENTS);

    JvmParameters.configure(testConfig);
    HeapParameters.configure(testConfig);
    GcLoggingParameters.configure(testConfig);
    GcParameters.configure(testConfig);
    ProfilerParameters.configure(testConfig);

    addToTestConfig(testConfig, "withSsl", WITH_SSL_ARGUMENT);
    addToTestConfig(testConfig, "withSecurityManager", WITH_SECURITY_MANAGER_ARGUMENT);

    testConfig.before(new StartLocator(Ports.LOCATOR_PORT), LOCATOR);
    testConfig.before(new StartServer(Ports.LOCATOR_PORT), SERVER);
    testConfig.before(new StartClient(Ports.LOCATOR_PORT), CLIENT);
  }

}
