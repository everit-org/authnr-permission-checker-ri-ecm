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
package org.everit.authnr.permissionchecker.ri.ecm;

/**
 * Constants of the Authnr Permission Checker component.
 */
public final class AuthnrPermissionCheckerConstants {

  public static final String DEFAULT_SERVICE_DESCRIPTION =
      "Default Authenticated Authorization Permission Checker";

  public static final String PROP_AUTHENTICATION_CONTEXT = "authenticationContext.target";

  public static final String PROP_PERMISSION_CHECKER = "permissionChecker.target";

  public static final String SERVICE_FACTORYPID =
      "org.everit.authnr.permissionchecker.ri.ecm.AuthnrPermissionChecker";

  private AuthnrPermissionCheckerConstants() {
  }

}
