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

import org.everit.authnr.permissionchecker.ri.ecm.tests.AuthnrPermissionCheckerTest;
import org.everit.authorization.PermissionChecker;
import org.everit.osgi.ecm.annotation.Component;
import org.everit.osgi.ecm.annotation.ConfigurationPolicy;
import org.everit.osgi.ecm.annotation.Service;
import org.everit.osgi.ecm.extender.ExtendComponent;
import org.junit.Assert;

/**
 * Mock implementation of {@link PermissionChecker}.
 */
@ExtendComponent
@Component(componentId = "MockPermissionCheckerComponent",
    configurationPolicy = ConfigurationPolicy.OPTIONAL)
@Service
public class MockPermissionCheckerComponent implements PermissionChecker {

  public static final boolean HAS_PERMISSION = false;

  public static final long SYSTEM_RESOURCE_ID = 0;

  @Override
  public long[] getAuthorizationScope(final long authorizedResourceId) {
    Assert.assertEquals(MockAuthenticationContextComponent.CURRENT_RESOURCE_ID,
        authorizedResourceId);
    return new long[] { authorizedResourceId };
  }

  @Override
  public long getSystemResourceId() {
    return SYSTEM_RESOURCE_ID;
  }

  @Override
  public boolean hasPermission(final long authorizedResourceId, final long targetResourceId,
      final String... actions) {
    Assert.assertEquals(MockAuthenticationContextComponent.CURRENT_RESOURCE_ID,
        authorizedResourceId);
    Assert.assertEquals(AuthnrPermissionCheckerTest.TARGET_RESOURCE_ID, targetResourceId);
    Assert.assertArrayEquals(AuthnrPermissionCheckerTest.getActions(), actions);
    return HAS_PERMISSION;
  }

}
