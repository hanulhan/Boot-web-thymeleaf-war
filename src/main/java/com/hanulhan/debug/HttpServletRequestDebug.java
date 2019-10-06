/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hanulhan.debug;

import java.io.IOException;

import java.io.ObjectInputStream;

import java.security.Principal;

import java.util.Collection;

import java.util.Enumeration;


import javax.servlet.ServletInputStream;

import javax.servlet.http.Cookie;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

import javax.servlet.http.Part;

 

import org.apache.commons.io.IOUtils;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;

/**
 *
 * @author uli
 */
public class HttpServletRequestDebug {
    


    /**

     * The Constant LOGGER.

     */

    private static final Logger LOGGER = LogManager.getLogger(HttpServletRequestDebug.class);

 

    /**

     * Debug the servle request

     *

     * @param httpServletRequest the http servlet request

     */

    public static void debugRequest(final HttpServletRequest httpServletRequest) {

        try {

            printRequest(httpServletRequest);

        } catch (final Throwable e) {

            LOGGER.error("Could not dump the servlet", e);

        }

    }

 

    /**

     * Prints the request.

     *

     * @param httpServletRequest the http servlet request

     */

    private static void printRequest(final HttpServletRequest httpServletRequest) {

        if (httpServletRequest == null) {

            return;

        }

        LOGGER.debug("----------------------------------------");

        LOGGER.debug("W4 HttpServletRequest");

        LOGGER.debug("\tRequestURL : {}", httpServletRequest.getRequestURL());

        LOGGER.debug("\tRequestURI : {}", httpServletRequest.getRequestURI());

        LOGGER.debug("\tScheme : {}", httpServletRequest.getScheme());

        LOGGER.debug("\tAuthType : {}", httpServletRequest.getAuthType());

        LOGGER.debug("\tEncoding : {}", httpServletRequest.getCharacterEncoding());

        LOGGER.debug("\tContentLength : {}", httpServletRequest.getContentLength());

        LOGGER.debug("\tContentType : {}", httpServletRequest.getContentType());

        LOGGER.debug("\tContextPath : {}", httpServletRequest.getContextPath());

        LOGGER.debug("\tMethod : {}", httpServletRequest.getMethod());

        LOGGER.debug("\tPathInfo : {}", httpServletRequest.getPathInfo());

        LOGGER.debug("\tProtocol : {}", httpServletRequest.getProtocol());

        LOGGER.debug("\tQuery : {}", httpServletRequest.getQueryString());

        LOGGER.debug("\tRemoteAddr : {}", httpServletRequest.getRemoteAddr());

        LOGGER.debug("\tRemoteHost : {}", httpServletRequest.getRemoteHost());

        LOGGER.debug("\tRemotePort : {}", httpServletRequest.getRemotePort());

        LOGGER.debug("\tRemoteUser : {}", httpServletRequest.getRemoteUser());

        LOGGER.debug("\tSessionID : {}", httpServletRequest.getRequestedSessionId());

        LOGGER.debug("\tServerName : {}", httpServletRequest.getServerName());

        LOGGER.debug("\tServerPort : {}", httpServletRequest.getServerPort());

        LOGGER.debug("\tServletPath : {}", httpServletRequest.getServletPath());

 

        LOGGER.debug("---------------------------------------------------------");

        LOGGER.debug("Cookies");

        int i = 0;

        try {

            if (httpServletRequest.getCookies() != null) {

                for (final Cookie cookie : httpServletRequest.getCookies()) {

                    LOGGER.debug("\tCookie[{}].name={}", i, cookie.getName());

                    LOGGER.debug("\tCookie[{}].comment={}", i, cookie.getComment());

                    LOGGER.debug("\tCookie[{}].domain={}", i, cookie.getDomain());

                    LOGGER.debug("\tCookie[{}].maxAge={}", i, cookie.getMaxAge());

                    LOGGER.debug("\tCookie[{}].path={}", i, cookie.getPath());

                    LOGGER.debug("\tCookie[{}].secured={}", i, cookie.getSecure());

                    LOGGER.debug("\tCookie[{}].value={}", i, cookie.getValue());

                    LOGGER.debug("\tCookie[{}].version={}", i, cookie.getVersion());
                    LOGGER.debug("");

                    i++;

                }

            } else {

                LOGGER.error("No Cookies");

            }

        } catch (Exception e) {

            LOGGER.error("ERROR analyzing Cookies");

        }

        LOGGER.debug("\tDispatcherType : {}", httpServletRequest.getDispatcherType());

        LOGGER.debug("---------------------------------------------------------");

 

        LOGGER.debug("Headers");

        int j = 0;

        try {

            final Enumeration<String> headerNames;

            headerNames = httpServletRequest.getHeaderNames();

            if (headerNames != null) {

                while (headerNames.hasMoreElements()) {

                    final String headerName = headerNames.nextElement();

                    final String header = httpServletRequest.getHeader(headerName);

                    LOGGER.debug("\tHeader[{}].name={}", j, headerName);

                    LOGGER.debug("\tHeader[{}].value={}", j, header);
                    LOGGER.debug("");

                    j++;

                }

            } else {

                LOGGER.error("No Headers");

            }

        } catch (Exception e) {

            LOGGER.error("ERROR analyzing Header");

        }

 
        LOGGER.debug("---------------------------------------------------------");
        
        LOGGER.debug("\tLocalAddr : {}", httpServletRequest.getLocalAddr());

        LOGGER.debug("\tLocale : {}", httpServletRequest.getLocale());

        LOGGER.debug("\tLocalPort : {}", httpServletRequest.getLocalPort());

 

        LOGGER.debug("");

        LOGGER.debug("Parameters");

        int k = 0;

        final Enumeration<String> parameterNames = httpServletRequest.getParameterNames();

        try {

            if (parameterNames != null) {

                while (parameterNames.hasMoreElements()) {

                    final String paramName = parameterNames.nextElement();

                    final String paramValue = httpServletRequest.getParameter(paramName);

                    LOGGER.debug("\tParam[{}].name={}", k, paramName);

                    LOGGER.debug("\tParam[{}].value={}", k, paramValue);

                    k++;

                }

            } else {

                LOGGER.error("No Parameters");

            }

        } catch (Exception e) {

            LOGGER.error("ERROR analyzing ParameterName");

        }

 

        LOGGER.debug("");

        LOGGER.debug("Parts");

        int l = 0;

        try {

            Collection<Part> myParts = httpServletRequest.getParts();

 

            if (myParts != null) {

                for (final Object part : httpServletRequest.getParts()) {

                    LOGGER.debug("\tParts[{}].class={}", l, part != null ? part.getClass() : "");

                    LOGGER.debug("\tParts[{}].value={}", l, part != null ? part.toString() : "");

                    l++;

                }

            } else {

                LOGGER.debug("\tParts in ServletRequest is null");

 

            }

        } catch (final Exception e) {

            LOGGER.debug("\tNo Parts in ServletRequest");

        }

        printSession(httpServletRequest.getSession());

        printUser(httpServletRequest.getUserPrincipal());

 

        ServletInputStream myInputStream = null;

 

        try {

            myInputStream = httpServletRequest.getInputStream();

            if (myInputStream == null) {

                LOGGER.error("Input Stream is null");

            }

        } catch (IOException ex) {

            LOGGER.error("getInputStream() ERROR, " + ex);

        }

 

        String myCharEncoding = null;

        try {

            myCharEncoding = httpServletRequest.getCharacterEncoding();

            if (myCharEncoding == null) {

                LOGGER.error("Char Encoding not set");

            }

        } catch (Exception e) {

            LOGGER.error("getCharacterEncoding() ERROR, " + e);

        }

 

        try {

            if (myCharEncoding != null && myInputStream != null) {

                LOGGER.debug("Request Body : {}" + IOUtils.toString(myInputStream, myCharEncoding));

            }

        } catch (final IOException e) {

            LOGGER.debug("Error getting Request Body. " + e);

        }

 

        try {

            if (myCharEncoding != null && myInputStream != null) {

                LOGGER.debug("Request Body : {}" + IOUtils.toString(myInputStream, myCharEncoding));

            }

        } catch (final IOException e) {

            LOGGER.debug("Error getting Request Body. " + e);

        }

 

        ObjectInputStream myObject = null;

        try {

            if (myInputStream != null) {

                myObject = new ObjectInputStream(myInputStream);

                LOGGER.debug("Request Object : {}", myObject.readObject());

            }

        } catch (IOException | ClassNotFoundException e) {

            LOGGER.debug("Error getting Request Object. " + e);

        }

 

        LOGGER.debug("---------------------------------------------------------");

    }

 

    /**

     * Prints the session.

     *

     * @param session the session

     */

    private static void printSession(final HttpSession session) {

        LOGGER.debug("-");

        if (session == null) {

            LOGGER.error("No session");

            return;

        }

        LOGGER.debug("\tSession Attributes");

        LOGGER.debug("\tSession.id:  {}", session.getId());

        LOGGER.debug("\tSession.creationTime:  {}", session.getCreationTime());

        LOGGER.debug("\tSession.lastAccessTime:  {}", session.getLastAccessedTime());

        LOGGER.debug("\tSession.maxInactiveInterval:  {}", session.getMaxInactiveInterval());

 

        int k = 0;

        final Enumeration<String> attributeNames = session.getAttributeNames();

        while (attributeNames.hasMoreElements()) {

            final String paramName = attributeNames.nextElement();

            final Object paramValue = session.getAttribute(paramName);

            LOGGER.debug("\tSession Attribute[{}].name={}", k, paramName);

            if (paramValue.getClass() != null) {

                LOGGER.debug("\tSession Attribute[{}].class={}", k, paramValue.getClass());

            }

            LOGGER.debug("\tSession Attribute[{}].value={}", k, paramValue);

            k++;

        }

 

    }

 

    /**

     * Prints the user.

     *

     * @param userPrincipal the user principal

     */

    private static void printUser(final Principal userPrincipal) {

        LOGGER.debug("-");

        if (userPrincipal == null) {

            LOGGER.debug("User Authentication : none");

            return;

        } else {

            LOGGER.debug("User Authentication.name :  {}", userPrincipal.getName());

            LOGGER.debug("User Authentication.class :  {}", userPrincipal.getClass());

            LOGGER.debug("User Authentication.value :  {}", userPrincipal);

        }

 

    }


}

 
