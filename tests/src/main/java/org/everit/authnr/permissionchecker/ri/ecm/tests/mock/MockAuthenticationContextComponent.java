/*
 * Copyright (C) 2011 Everit Kft. (http://www.everit.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.everit.authnr.permissionchecker.ri.ecm.tests.mock;

import org.everit.authentication.context.AuthenticationContext;
import org.everit.osgi.ecm.annotation.Component;
import org.everit.osgi.ecm.annotation.ConfigurationPolicy;
import org.everit.osgi.ecm.annotation.Service;
import org.everit.osgi.ecm.extender.ExtendComponent;

/**
 * Mock implementation of {@link AuthenticationContext}.
 */
@ExtendComponent
@Component(componentId = "MockAuthenticationContextComponent",
    configurationPolicy = ConfigurationPolicy.OPTIONAL)
@Service
public class MockAuthenticationContextComponent implements AuthenticationContext {

  public static final long CURRENT_RESOURCE_ID = 1;

  @Override
  public long getCurrentResourceId() {
    return CURRENT_RESOURCE_ID;
  }

  @Override
  public long getDefaultResourceId() {
    throw new UnsupportedOperationException();
  }

}
