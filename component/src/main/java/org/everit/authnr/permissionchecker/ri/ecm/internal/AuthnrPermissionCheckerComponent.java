/*
 * Copyright (C) 2011 Everit Kft. (http://www.everit.biz)
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
package org.everit.authnr.permissionchecker.ri.ecm.internal;

import java.util.Dictionary;
import java.util.Hashtable;

import org.everit.authentication.context.AuthenticationContext;
import org.everit.authnr.permissionchecker.AuthnrPermissionChecker;
import org.everit.authnr.permissionchecker.ri.AuthnrPermissionCheckerImpl;
import org.everit.authnr.permissionchecker.ri.ecm.AuthnrPermissionCheckerConstants;
import org.everit.authorization.PermissionChecker;
import org.everit.osgi.ecm.annotation.Activate;
import org.everit.osgi.ecm.annotation.Component;
import org.everit.osgi.ecm.annotation.ConfigurationPolicy;
import org.everit.osgi.ecm.annotation.Deactivate;
import org.everit.osgi.ecm.annotation.ManualService;
import org.everit.osgi.ecm.annotation.ManualServices;
import org.everit.osgi.ecm.annotation.ServiceRef;
import org.everit.osgi.ecm.annotation.attribute.StringAttribute;
import org.everit.osgi.ecm.annotation.attribute.StringAttributes;
import org.everit.osgi.ecm.component.ComponentContext;
import org.everit.osgi.ecm.extender.ExtendComponent;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;

/**
 * ECM component for {@link AuthnrPermissionChecker} interface based on
 * {@link AuthnrPermissionCheckerImpl}.
 */
@ExtendComponent
@Component(componentId = AuthnrPermissionCheckerConstants.SERVICE_FACTORYPID,
    configurationPolicy = ConfigurationPolicy.FACTORY,
    label = "Everit Authenticated Authorization Permission Checker RI",
    description = "Component that registers an AuthnrPermissionChecker OSGi service.")
@StringAttributes({
    @StringAttribute(attributeId = Constants.SERVICE_DESCRIPTION,
        defaultValue = AuthnrPermissionCheckerConstants.DEFAULT_SERVICE_DESCRIPTION,
        priority = AuthnrPermissionCheckerComponent.P1_SERVICE_DESCRIPTION,
        label = "Service Description",
        description = "The description of this component configuration. It is used to easily "
            + "identify the service registered by this component.") })
@ManualServices(@ManualService({ AuthnrPermissionChecker.class }))
public class AuthnrPermissionCheckerComponent {

  public static final int P1_SERVICE_DESCRIPTION = 1;

  public static final int P2_AUTHENTICATION_CONTEXT = 2;

  public static final int P3_PERMISSION_CHECKER = 3;

  private AuthenticationContext authenticationContext;

  private PermissionChecker permissionChecker;

  private ServiceRegistration<AuthnrPermissionChecker> serviceRegistration;

  /**
   * Activate method of component.
   */
  @Activate
  public void activate(final ComponentContext<AuthnrPermissionCheckerComponent> componentContext) {
    AuthnrPermissionCheckerImpl authnrPermissionChecker =
        new AuthnrPermissionCheckerImpl(this.authenticationContext, this.permissionChecker);

    Dictionary<String, Object> serviceProperties =
        new Hashtable<>(componentContext.getProperties());
    this.serviceRegistration =
        componentContext.registerService(AuthnrPermissionChecker.class, authnrPermissionChecker,
            serviceProperties);
  }

  /**
   * Component deactivate method.
   */
  @Deactivate
  public void deactivate() {
    if (this.serviceRegistration != null) {
      this.serviceRegistration.unregister();
    }
  }

  @ServiceRef(attributeId = AuthnrPermissionCheckerConstants.PROP_AUTHENTICATION_CONTEXT,
      defaultValue = "", attributePriority = P2_AUTHENTICATION_CONTEXT,
      label = "Authentication Context target",
      description = "OSGi service filter to identify the AuthenticationContext service.")
  public void setAuthenticationContext(final AuthenticationContext authenticationContext) {
    this.authenticationContext = authenticationContext;
  }

  @ServiceRef(attributeId = AuthnrPermissionCheckerConstants.PROP_PERMISSION_CHECKER,
      defaultValue = "", attributePriority = P3_PERMISSION_CHECKER,
      label = "Permission Checker target",
      description = "OSGi service filter to identify the PermissionChecker service.")
  public void setPermissionChecker(final PermissionChecker permissionChecker) {
    this.permissionChecker = permissionChecker;
  }

}
