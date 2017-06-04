package com.aliviatechnology;

/**
 * Waffle (https://github.com/Waffle/waffle)
 *
 * Copyright (c) 2010-2017 Application Security, Inc.
 *
 * All rights reserved. This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors: Application Security, Inc.
 */
import waffle.servlet.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.security.auth.Subject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import waffle.servlet.spi.SecurityFilterProvider;
import waffle.servlet.spi.SecurityFilterProviderCollection;
import waffle.util.AuthorizationHeader;
import waffle.windows.auth.IWindowsAuthProvider;
import waffle.windows.auth.IWindowsIdentity;
import waffle.windows.auth.IWindowsImpersonationContext;
import waffle.windows.auth.PrincipalFormat;
import waffle.windows.auth.impl.WindowsAuthProviderImpl;

/**
 * A Negotiate (NTLM/Kerberos) Security Filter.
 *
 * @author dblock[at]dblock[dot]org
 */
public class NegotiateSecurityFilterAlivia implements Filter {

    /** The Constant LOGGER. */
    private static final Logger              LOGGER              = LoggerFactory
            .getLogger(NegotiateSecurityFilterAlivia.class);

    /** The principal format. */
    private PrincipalFormat                  principalFormat     = PrincipalFormat.FQN;

    /** The role format. */
    private PrincipalFormat                  roleFormat          = PrincipalFormat.FQN;

    /** The providers. */
    private SecurityFilterProviderCollection providers;

    /** The auth. */
    private IWindowsAuthProvider             auth;

    /** The allow guest login. */
    private boolean                          allowGuestLogin     = true;

    /** The impersonate. */
    private boolean                          impersonate;

    /** The Constant PRINCIPALSESSIONKEY. */
    private static final String              PRINCIPALSESSIONKEY = NegotiateSecurityFilterAlivia.class.getName()
            + ".PRINCIPAL";

    /**
     * Instantiates a new negotiate security filter.
     */
    public NegotiateSecurityFilterAlivia() {
        NegotiateSecurityFilterAlivia.LOGGER.debug("[com.aliviatechnology.NegotiateSecurityFilterAlivia] loaded");
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
        NegotiateSecurityFilterAlivia.LOGGER.info("[com.aliviatechnology.NegotiateSecurityFilterAlivia] stopped");
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
     * javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(final ServletRequest sreq, final ServletResponse sres, final FilterChain chain)
            throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) sreq;
        final HttpServletResponse response = (HttpServletResponse) sres;

        NegotiateSecurityFilterAlivia.LOGGER.debug("{} {}, contentlength: {}", request.getMethod(), request.getRequestURI(),
                Integer.valueOf(request.getContentLength()));

        if (this.doFilterPrincipal(request, response, chain)) {
            // previously authenticated user
            return;
        }

        final AuthorizationHeader authorizationHeader = new AuthorizationHeader(request);

        // authenticate user
        if (!authorizationHeader.isNull()) {

            // log the user in using the token
            IWindowsIdentity windowsIdentity;
            try {
                windowsIdentity = this.providers.doFilter(request, response);
                if (windowsIdentity == null) {
                    return;
                }
            } catch (final IOException e) {
                NegotiateSecurityFilterAlivia.LOGGER.warn("error logging in user: {}", e.getMessage());
                NegotiateSecurityFilterAlivia.LOGGER.trace("", e);
                this.sendUnauthorized(response, true);
                return;
            }

            IWindowsImpersonationContext ctx = null;
            try {
                if (!this.allowGuestLogin && windowsIdentity.isGuest()) {
                    NegotiateSecurityFilterAlivia.LOGGER.warn("guest login disabled: {}", windowsIdentity.getFqn());
                    this.sendUnauthorized(response, true);
                    return;
                }

                NegotiateSecurityFilterAlivia.LOGGER.debug("logged in user: {} ({})", windowsIdentity.getFqn(),
                        windowsIdentity.getSidString());

                final HttpSession session = request.getSession(true);
                if (session == null) {
                    throw new ServletException("Expected HttpSession");
                }

                Subject subject = (Subject) session.getAttribute("javax.security.auth.subject");
                if (subject == null) {
                    subject = new Subject();
                }

                WindowsPrincipal windowsPrincipal;
                if (this.impersonate) {
                    windowsPrincipal = new AutoDisposableWindowsPrincipal(windowsIdentity, this.principalFormat,
                            this.roleFormat);
                } else {
                    windowsPrincipal = new WindowsPrincipal(windowsIdentity, this.principalFormat, this.roleFormat);
                }

                NegotiateSecurityFilterAlivia.LOGGER.debug("roles: {}", windowsPrincipal.getRolesString());
                subject.getPrincipals().add(windowsPrincipal);
                request.getSession(false).setAttribute("javax.security.auth.subject", subject);

                NegotiateSecurityFilterAlivia.LOGGER.info("successfully logged in user: {}", windowsIdentity.getFqn());

                request.getSession(false).setAttribute(NegotiateSecurityFilterAlivia.PRINCIPALSESSIONKEY, windowsPrincipal);

                final NegotiateRequestWrapper requestWrapper = new NegotiateRequestWrapper(request, windowsPrincipal);

                if (this.impersonate) {
                    NegotiateSecurityFilterAlivia.LOGGER.debug("impersonating user");
                    ctx = windowsIdentity.impersonate();
                }

                chain.doFilter(requestWrapper, response);
            } finally {
                if (this.impersonate && ctx != null) {
                    NegotiateSecurityFilterAlivia.LOGGER.debug("terminating impersonation");
                    ctx.revertToSelf();
                } else {
                    windowsIdentity.dispose();
                }
            }

            return;
        }

        NegotiateSecurityFilterAlivia.LOGGER.debug("authorization required");
        this.sendUnauthorized(response, false);
    }

    /**
     * Filter for a previously logged on user.
     *
     * @param request
     *            HTTP request.
     * @param response
     *            HTTP response.
     * @param chain
     *            Filter chain.
     * @return True if a user already authenticated.
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws ServletException
     *             the servlet exception
     */
    private boolean doFilterPrincipal(final HttpServletRequest request, final HttpServletResponse response,
            final FilterChain chain) throws IOException, ServletException {
        Principal principal = request.getUserPrincipal();
        if (principal == null) {
            final HttpSession session = request.getSession(false);
            if (session != null) {
                principal = (Principal) session.getAttribute(NegotiateSecurityFilterAlivia.PRINCIPALSESSIONKEY);
            }
        }

        if (principal == null) {
            // no principal in this request
            return false;
        }

        if (this.providers.isPrincipalException(request)) {
            // the providers signal to authenticate despite an existing principal, eg. NTLM post
            return false;
        }

        // user already authenticated

        if (principal instanceof WindowsPrincipal) {
            NegotiateSecurityFilterAlivia.LOGGER.debug("previously authenticated Windows user: {}", principal.getName());
            final WindowsPrincipal windowsPrincipal = (WindowsPrincipal) principal;

            if (this.impersonate && windowsPrincipal.getIdentity() == null) {
                // This can happen when the session has been serialized then de-serialized
                // and because the IWindowsIdentity field is transient. In this case re-ask an
                // authentication to get a new identity.
                return false;
            }

            final NegotiateRequestWrapper requestWrapper = new NegotiateRequestWrapper(request, windowsPrincipal);

            IWindowsImpersonationContext ctx = null;
            if (this.impersonate) {
                NegotiateSecurityFilterAlivia.LOGGER.debug("re-impersonating user");
                ctx = windowsPrincipal.getIdentity().impersonate();
            }
            try {
                chain.doFilter(requestWrapper, response);
            } finally {
                if (this.impersonate && ctx != null) {
                    NegotiateSecurityFilterAlivia.LOGGER.debug("terminating impersonation");
                    ctx.revertToSelf();
                }
            }
        } else {
            NegotiateSecurityFilterAlivia.LOGGER.debug("previously authenticated user: {}", principal.getName());
            chain.doFilter(request, response);
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        final Map<String, String> implParameters = new HashMap<>();

        String authProvider = null;
        String[] providerNames = null;
        if (filterConfig != null) {
            final Enumeration<String> parameterNames = filterConfig.getInitParameterNames();
            while (parameterNames.hasMoreElements()) {
                final String parameterName = parameterNames.nextElement();
                final String parameterValue = filterConfig.getInitParameter(parameterName);
                NegotiateSecurityFilterAlivia.LOGGER.debug("{}={}", parameterName, parameterValue);
                switch (parameterName) {
                    case "principalFormat":
                        this.principalFormat = PrincipalFormat.valueOf(parameterValue.toUpperCase(Locale.ENGLISH));
                        break;
                    case "roleFormat":
                        this.roleFormat = PrincipalFormat.valueOf(parameterValue.toUpperCase(Locale.ENGLISH));
                        break;
                    case "allowGuestLogin":
                        this.allowGuestLogin = Boolean.parseBoolean(parameterValue);
                        break;
                    case "impersonate":
                        this.impersonate = Boolean.parseBoolean(parameterValue);
                        break;
                    case "securityFilterProviders":
                        providerNames = parameterValue.split("\\s+");
                        break;
                    case "authProvider":
                        authProvider = parameterValue;
                        break;
                    default:
                        implParameters.put(parameterName, parameterValue);
                        break;
                }
            }
        }

        if (authProvider != null) {
            try {
                this.auth = (IWindowsAuthProvider) Class.forName(authProvider).getConstructor().newInstance();
            } catch (final ClassNotFoundException | IllegalArgumentException | SecurityException
                    | InstantiationException | IllegalAccessException | InvocationTargetException
                    | NoSuchMethodException e) {
                NegotiateSecurityFilterAlivia.LOGGER.error("error loading '{}': {}", authProvider, e.getMessage());
                NegotiateSecurityFilterAlivia.LOGGER.trace("", e);
                throw new ServletException(e);
            }
        }

        if (this.auth == null) {
            this.auth = new WindowsAuthProviderImpl();
        }

        if (providerNames != null) {
            this.providers = new SecurityFilterProviderCollection(providerNames, this.auth);
        }

        // create default providers if none specified
        if (this.providers == null) {
            NegotiateSecurityFilterAlivia.LOGGER.debug("initializing default security filter providers");
            this.providers = new SecurityFilterProviderCollection(this.auth);
        }

        // apply provider implementation parameters
        for (final Entry<String, String> implParameter : implParameters.entrySet()) {
            final String[] classAndParameter = implParameter.getKey().split("/", 2);
            if (classAndParameter.length == 2) {
                try {

                    NegotiateSecurityFilterAlivia.LOGGER.debug("setting {}, {}={}", classAndParameter[0],
                            classAndParameter[1], implParameter.getValue());

                    final SecurityFilterProvider provider = this.providers.getByClassName(classAndParameter[0]);
                    provider.initParameter(classAndParameter[1], implParameter.getValue());

                } catch (final ClassNotFoundException e) {
                    NegotiateSecurityFilterAlivia.LOGGER.error("invalid class: {} in {}", classAndParameter[0],
                            implParameter.getKey());
                    throw new ServletException(e);
                } catch (final Exception e) {
                    NegotiateSecurityFilterAlivia.LOGGER.error("{}: error setting '{}': {}", classAndParameter[0],
                            classAndParameter[1], e.getMessage());
                    NegotiateSecurityFilterAlivia.LOGGER.trace("", e);
                    throw new ServletException(e);
                }
            } else {
                NegotiateSecurityFilterAlivia.LOGGER.error("Invalid parameter: {}", implParameter.getKey());
                throw new ServletException("Invalid parameter: " + implParameter.getKey());
            }
        }

        NegotiateSecurityFilterAlivia.LOGGER.info("[waffle.servlet.NegotiateSecurityFilterAlivia] started");
    }

    /**
     * Set the principal format.
     * 
     * @param format
     *            Principal format.
     */
    public void setPrincipalFormat(final String format) {
        this.principalFormat = PrincipalFormat.valueOf(format.toUpperCase(Locale.ENGLISH));
        NegotiateSecurityFilterAlivia.LOGGER.info("principal format: {}", this.principalFormat);
    }

    /**
     * Principal format.
     * 
     * @return Principal format.
     */
    public PrincipalFormat getPrincipalFormat() {
        return this.principalFormat;
    }

    /**
     * Set the principal format.
     * 
     * @param format
     *            Role format.
     */
    public void setRoleFormat(final String format) {
        this.roleFormat = PrincipalFormat.valueOf(format.toUpperCase(Locale.ENGLISH));
        NegotiateSecurityFilterAlivia.LOGGER.info("role format: {}", this.roleFormat);
    }

    /**
     * Principal format.
     * 
     * @return Role format.
     */
    public PrincipalFormat getRoleFormat() {
        return this.roleFormat;
    }

    /**
     * Send a 401 Unauthorized along with protocol authentication headers.
     * 
     * @param response
     *            HTTP Response
     * @param close
     *            Close connection.
     */
    private void sendUnauthorized(final HttpServletResponse response, final boolean close) {
        try {
            this.providers.sendUnauthorized(response);
            if (close) {
                response.setHeader("Connection", "close");
            } else {
                response.setHeader("Connection", "keep-alive");
            }
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            response.flushBuffer();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Windows auth provider.
     * 
     * @return IWindowsAuthProvider.
     */
    public IWindowsAuthProvider getAuth() {
        return this.auth;
    }

    /**
     * Set Windows auth provider.
     * 
     * @param provider
     *            Class implements IWindowsAuthProvider.
     */
    public void setAuth(final IWindowsAuthProvider provider) {
        this.auth = provider;
    }

    /**
     * True if guest login is allowed.
     * 
     * @return True if guest login is allowed, false otherwise.
     */
    public boolean isAllowGuestLogin() {
        return this.allowGuestLogin;
    }

    /**
     * Enable/Disable impersonation.
     *
     * @param value
     *            true to enable impersonation, false otherwise
     */
    public void setImpersonate(final boolean value) {
        this.impersonate = value;
    }

    /**
     * Checks if is impersonate.
     *
     * @return true if impersonation is enabled, false otherwise
     */
    public boolean isImpersonate() {
        return this.impersonate;
    }

    /**
     * Security filter providers.
     * 
     * @return A collection of security filter providers.
     */
    public SecurityFilterProviderCollection getProviders() {
        return this.providers;
    }
}
