<?xml version="1.0" encoding="UTF-8"?>
<!--
  ~ Copyright (c) 2011 Alexander Dovzhikov <alexander.dovzhikov@gmail.com>.
  ~ All rights reserved.
  ~
  ~ Redistribution and use in source and binary forms, with or without modification, are
  ~ permitted provided that the following conditions are met:
  ~
  ~    1. Redistributions of source code must retain the above copyright notice, this list of
  ~       conditions and the following disclaimer.
  ~
  ~    2. Redistributions in binary form must reproduce the above copyright notice, this list
  ~       of conditions and the following disclaimer in the documentation and/or other materials
  ~       provided with the distribution.
  ~
  ~ THIS SOFTWARE IS PROVIDED BY ALEXANDER DOVZHIKOV ''AS IS'' AND ANY EXPRESS OR IMPLIED
  ~ WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
  ~ FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL dovzhikov OR
  ~ CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
  ~ CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
  ~ SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
  ~ ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
  ~ NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
  ~ ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
  ~
  ~ cv-default.xsl
  ~
  ~ Created on 06.09.2011 14:39:35
  -->
<!-- noinspection CheckNodeTest -->
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:noNamespaceSchemaLocation="cv-schema.xsd">
    <xsl:output method="xml" indent="yes"/>
    <xsl:variable name="firstName" select="document/personal/first-name"/>
    <xsl:variable name="lastName" select="document/personal/last-name"/>
    <xsl:variable name="applicantName" select="concat($firstName, ' ', $lastName)"/>
    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="A4-portrait"
                                       page-height="29.7cm"
                                       page-width="21.0cm"
                                       margin-top="2cm"
                                       margin-bottom="1cm"
                                       margin-left="2cm"
                                       margin-right="2cm">
                    <fo:region-body margin-top="2.5cm" margin-bottom="1cm"/>
                    <fo:region-before extent="2.5cm"/>
                    <fo:region-after extent="0.8cm"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="A4-portrait">
                <!-- header -->
                <fo:static-content flow-name="xsl-region-before">
                    <fo:table border-bottom-style="solid" border-bottom-width="thin">
                        <fo:table-body>
                            <fo:table-row>
                                <fo:table-cell display-align="center">
                                    <fo:block font-size="18pt" font-weight="bold">
                                        <xsl:value-of select="$applicantName"/>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:table>
                                        <fo:table-body>
                                            <fo:table-row>
                                                <fo:table-cell>
                                                    <fo:block font-size="10pt"
                                                              font-style="italic"
                                                              font-weight="bold"
                                                              text-align="center">
                                                        <xsl:value-of select="document/personal/address/street"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                            <fo:table-row>
                                                <fo:table-cell>
                                                    <fo:block font-size="10pt"
                                                              font-style="italic"
                                                              font-weight="bold"
                                                              text-align="center">
                                                        <xsl:value-of select="document/personal/address/city"/>,
                                                        <xsl:value-of select="document/personal/address/country"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                            <fo:table-row>
                                                <fo:table-cell>
                                                    <fo:block font-size="10pt"
                                                              font-style="italic"
                                                              font-weight="bold"
                                                              text-align="center">
                                                        Phone: <xsl:value-of select="document/personal/phone[@type='home']"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                            <fo:table-row>
                                                <fo:table-cell>
                                                    <fo:block font-size="10pt"
                                                              font-style="italic"
                                                              font-weight="bold"
                                                              text-align="center">
                                                        Cell: <xsl:value-of select="document/personal/phone[@type='mobile']"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                            <fo:table-row>
                                                <fo:table-cell>
                                                    <fo:block font-size="10pt"
                                                              font-style="italic"
                                                              font-weight="bold"
                                                              text-align="center">
                                                        E-Mail: <xsl:value-of select="document/personal/email"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </fo:table-body>
                                    </fo:table>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-body>
                    </fo:table>
                </fo:static-content>
                <!-- footer -->
                <fo:static-content flow-name="xsl-region-after">
                    <fo:block font-size="8pt"
                              border-top-style="solid"
                              border-top-width="thin"
                              padding-top="0.2cm">
                        Generated with Apache FOP
                    </fo:block>
                </fo:static-content>
                <!-- main part -->
                <fo:flow flow-name="xsl-region-body">
                    <xsl:apply-templates/>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
    <xsl:template match="personal">
        <xsl:call-template name="section">
            <xsl:with-param name="section-name">PERSONAL INFORMATION</xsl:with-param>
        </xsl:call-template>
        <fo:table font-family="sans-serif" font-size="11pt">
            <fo:table-column column-width="40mm"/>
            <fo:table-column column-width="150mm"/>
            <fo:table-body>
                <xsl:call-template name="table-row">
                    <xsl:with-param name="key">Date of birth:</xsl:with-param>
                    <xsl:with-param name="value"><xsl:value-of select="birthday"/></xsl:with-param>
                </xsl:call-template>
                <xsl:call-template name="table-row">
                    <xsl:with-param name="key">Marital status:</xsl:with-param>
                    <xsl:with-param name="value"><xsl:value-of select="marital-status"/></xsl:with-param>
                </xsl:call-template>
                <xsl:call-template name="table-row">
                    <xsl:with-param name="key">Languages:</xsl:with-param>
                    <xsl:with-param name="value">
                        <xsl:for-each select="languages/language">
                            <xsl:value-of select="."/> (<xsl:value-of select="@type"/>)<xsl:if test="position() &lt; last()">, </xsl:if>
                        </xsl:for-each>
                    </xsl:with-param>
                </xsl:call-template>
            </fo:table-body>
        </fo:table>
    </xsl:template>
    <xsl:template match="education">
        <xsl:call-template name="section">
            <xsl:with-param name="section-name">EDUCATION</xsl:with-param>
        </xsl:call-template>
        <xsl:apply-templates select="university"/>
    </xsl:template>
    <xsl:template match="university">
        <fo:block font-family="sans-serif" font-size="11pt" font-weight="bold">
            <xsl:value-of select="start-date"/>-<xsl:value-of select="end-date"/>, <xsl:value-of select="university-name"/>
        </fo:block>
        <fo:table font-family="sans-serif" font-size="11pt">
            <fo:table-column column-width="40mm"/>
            <fo:table-column column-width="100mm"/>
            <fo:table-body>
                <xsl:apply-templates select="department"/>
                <xsl:apply-templates select="specialty"/>
                <xsl:apply-templates select="level"/>
                <xsl:apply-templates select="thesis"/>
            </fo:table-body>
        </fo:table>
    </xsl:template>
    <xsl:template match="department">
        <xsl:call-template name="table-row">
            <xsl:with-param name="key">Department:</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of select="."/></xsl:with-param>
        </xsl:call-template>
    </xsl:template>
    <xsl:template match="specialty">
        <xsl:call-template name="table-row">
            <xsl:with-param name="key">Specialty/Course:</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of select="."/></xsl:with-param>
        </xsl:call-template>
    </xsl:template>
    <xsl:template match="level">
        <xsl:call-template name="table-row">
            <xsl:with-param name="key">Gained Level:</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of select="."/></xsl:with-param>
        </xsl:call-template>
    </xsl:template>
    <xsl:template match="thesis">
        <xsl:call-template name="table-row">
            <xsl:with-param name="key">Diploma Thesis:</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of select="."/></xsl:with-param>
        </xsl:call-template>
    </xsl:template>
    <!-- Objective -->
    <xsl:template match="objective">
        <xsl:call-template name="section">
            <xsl:with-param name="section-name">OBJECTIVE</xsl:with-param>
        </xsl:call-template>
        <fo:block font-size="11pt"><xsl:value-of select="."/></fo:block>
    </xsl:template>
    <!-- Summary -->
    <xsl:template match="summary">
        <xsl:call-template name="section">
            <xsl:with-param name="section-name">PROFESSIONAL SUMMARY</xsl:with-param>
        </xsl:call-template>
        <fo:list-block>
            <xsl:for-each select="summary-item">
                <fo:list-item>
                    <fo:list-item-label end-indent="label-end()">
                        <fo:block font-size="9pt"><fo:inline font-family="Symbol">&#x2022;</fo:inline></fo:block>
                    </fo:list-item-label>
                    <fo:list-item-body start-indent="body-start()">
                        <fo:block font-size="11pt"><xsl:value-of select="."/>.</fo:block>
                    </fo:list-item-body>
                </fo:list-item>
            </xsl:for-each>
        </fo:list-block>
    </xsl:template>
    <!-- Skills -->
    <xsl:template match="skills">
        <xsl:call-template name="section">
            <xsl:with-param name="section-name">SKILLS</xsl:with-param>
        </xsl:call-template>
        <fo:table font-family="sans-serif" font-size="11pt">
            <fo:table-column column-width="60mm"/>
            <fo:table-column/>
            <fo:table-body>
                <xsl:for-each select="skill-set">
                    <xsl:call-template name="table-row">
                        <xsl:with-param name="key">
                            <xsl:value-of select="skill-set-name"/>:
                        </xsl:with-param>
                        <xsl:with-param name="value">
                            <xsl:for-each select="skill">
                                <xsl:value-of select="."/><xsl:if test="position() &lt; last()">, </xsl:if>
                            </xsl:for-each>.
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:for-each>
            </fo:table-body>
        </fo:table>
    </xsl:template>
    <!-- Certifications -->
    <xsl:template match="certifications">
        <xsl:call-template name="section">
            <xsl:with-param name="section-name">CERTIFICATIONS</xsl:with-param>
        </xsl:call-template>
        <xsl:for-each select="cert-provider">
            <fo:block font-size="11pt" font-weight="bold">
                <xsl:value-of select="@name"/>
                <xsl:if test="@verify-url">
                    (<fo:basic-link color="blue">
                        <xsl:attribute name="text-decoration">
                            <xsl:text>underline</xsl:text>
                        </xsl:attribute>
                        <xsl:attribute name="external-destination">
                            <xsl:value-of select="@verify-url"/>
                        </xsl:attribute>verify</fo:basic-link>)</xsl:if>:
            </fo:block>
            <xsl:for-each select="cert">
                <fo:block font-size="11pt">
                    <xsl:value-of select="."/>
                    <xsl:if test="@level"> (<xsl:value-of select="@level"/> Level)</xsl:if>
                    on <xsl:value-of select="@date"/>
                    <xsl:if test="@expired='true'"> (expired)</xsl:if>
                </fo:block>
            </xsl:for-each>
            <fo:block margin-bottom="10pt"/>
        </xsl:for-each>
    </xsl:template>
    <!-- Experience -->
    <xsl:template match="experience">
        <xsl:call-template name="section">
            <xsl:with-param name="section-name">PROFESSIONAL EXPERIENCE</xsl:with-param>
        </xsl:call-template>
        <xsl:for-each select="company">
            <fo:block font-size="11pt">
                <fo:inline font-weight="bold"><xsl:value-of select="company-name"/></fo:inline>
                - <xsl:value-of select="company-location"/>
                (<xsl:value-of select="start-date"/> -
                <xsl:choose>
                    <xsl:when test="end-date"><xsl:value-of select="end-date"/></xsl:when>
                    <xsl:otherwise>now</xsl:otherwise>
                </xsl:choose>)
            </fo:block>
            <xsl:for-each select="project">
                <fo:block font-size="11pt">
                    <fo:inline font-weight="bold">Project: </fo:inline>
                    <xsl:value-of select="project-name"/>
                </fo:block>
                <fo:block font-size="11pt">
                    <fo:inline font-weight="bold">Role: </fo:inline>
                    <xsl:value-of select="role"/>
                </fo:block>
                <fo:block font-size="11pt">
                    <fo:inline font-weight="bold">Description: </fo:inline>
                    <xsl:value-of select="description"/>.
                </fo:block>
                <fo:block font-size="11pt" font-weight="bold">Responsibilities:</fo:block>
                <fo:list-block>
                    <xsl:for-each select="responsibilities/responsibility">
                        <fo:list-item>
                            <fo:list-item-label end-indent="label-end()">
                                <fo:block font-size="9pt"><fo:inline font-family="Symbol">&#x2022;</fo:inline></fo:block>
                            </fo:list-item-label>
                            <fo:list-item-body start-indent="body-start()">
                                <fo:block font-size="11pt"><xsl:value-of select="."/>.</fo:block>
                            </fo:list-item-body>
                        </fo:list-item>
                    </xsl:for-each>
                </fo:list-block>
                <fo:block font-size="11pt">
                    <fo:inline font-weight="bold">Environment: </fo:inline>
                    <xsl:for-each select="environment/env-entry">
                        <xsl:value-of select="."/><xsl:if test="position() &lt; last()">, </xsl:if>
                    </xsl:for-each>.
                </fo:block>
                <fo:block margin-bottom="10pt"/>
            </xsl:for-each>
        </xsl:for-each>
    </xsl:template>
    <!-- auxiliary templates -->
    <xsl:template name="section">
        <xsl:param name="section-name"/>
        <fo:block font-family="sans-serif"
                  font-size="12pt"
                  font-weight="bold"
                  background-color="rgb(210,210,210)"
                  margin-top="5pt"
                  margin-bottom="5pt"
                  padding-top="2pt"
                  padding-start="2pt">
            <xsl:value-of select="$section-name"/>
        </fo:block>
    </xsl:template>
    <xsl:template name="table-row">
        <xsl:param name="key"/>
        <xsl:param name="value"/>
        <fo:table-row>
            <fo:table-cell font-weight="bold">
                <fo:block><xsl:value-of select="$key"/></fo:block>
            </fo:table-cell>
            <fo:table-cell>
                <fo:block><xsl:value-of select="$value"/></fo:block>
            </fo:table-cell>
        </fo:table-row>
    </xsl:template>
</xsl:stylesheet>