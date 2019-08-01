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
package org.everit.authnr.permissionchecker.ri.ecm.tests;

import org.everit.authnr.permissionchecker.AuthnrPermissionChecker;
import org.everit.authnr.permissionchecker.UnauthorizedException;
import org.everit.authnr.permissionchecker.ri.ecm.tests.mock.MockAuthenticationContextComponent;
import org.everit.authnr.permissionchecker.ri.ecm.tests.mock.MockPermissionCheckerComponent;
import org.everit.osgi.dev.testrunner.TestRunnerConstants;
import org.everit.osgi.ecm.annotation.Component;
import org.everit.osgi.ecm.annotation.ConfigurationPolicy;
import org.everit.osgi.ecm.annotation.Service;
import org.everit.osgi.ecm.annotation.ServiceRef;
import org.everit.osgi.ecm.annotation.attribute.StringAttribute;
import org.everit.osgi.ecm.annotation.attribute.StringAttributes;
import org.everit.osgi.ecm.extender.ExtendComponent;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for AuthnrPermissionChecker.
 */
@ExtendComponent
@Component(componentId = "AuthnrPermissionCheckerTest",
    configurationPolicy = ConfigurationPolicy.OPTIONAL)
@StringAttributes({
    @StringAttribute(attributeId = TestRunnerConstants.SERVICE_PROPERTY_TESTRUNNER_ENGINE_TYPE,
        defaultValue = "junit4"),
    @StringAttribute(attributeId = TestRunnerConstants.SERVICE_PROPERTY_TEST_ID,
        defaultValue = "AuthnrPermissionCheckerTest") })
@Service(value = AuthnrPermissionCheckerTest.class)
public class AuthnrPermissionCheckerTest {

  private static final String[] ACTIONS = { "a1", "a2" };

  public static final long TARGET_RESOURCE_ID = 2;

  public static String[] getActions() {
    return ACTIONS.clone();
  }

  private AuthnrPermissionChecker authnrPermissionChecker;

  @ServiceRef(defaultValue = "")
  public void setAuthnrPermissionChecker(final AuthnrPermissionChecker authnrPermissionChecker) {
    this.authnrPermissionChecker = authnrPermissionChecker;
  }

  @Test
  public void testComplex() {

    Assert.assertEquals(MockPermissionCheckerComponent.SYSTEM_RESOURCE_ID,
        this.authnrPermissionChecker.getSystemResourceId());

    Assert.assertArrayEquals(new long[] { MockAuthenticationContextComponent.CURRENT_RESOURCE_ID },
        this.authnrPermissionChecker.getAuthorizationScope());

    Assert.assertEquals(MockPermissionCheckerComponent.HAS_PERMISSION,
        this.authnrPermissionChecker.hasPermission(TARGET_RESOURCE_ID, ACTIONS));

    try {
      this.authnrPermissionChecker.checkPermission(TARGET_RESOURCE_ID, ACTIONS);
      Assert.fail();
    } catch (UnauthorizedException e) {
      Assert.assertArrayEquals(this.authnrPermissionChecker.getAuthorizationScope(),
          e.authorizationScope);
      Assert.assertEquals(TARGET_RESOURCE_ID, e.targetResourceId);
      Assert.assertArrayEquals(AuthnrPermissionCheckerTest.getActions(), e.actions);
    }
  }
}
