<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:cv="cv-schema.xsd">
    <xsl:output method="xml" indent="yes"/>
    <xsl:variable name="firstName" select="cv:document/cv:personal/cv:first-name"/>
    <xsl:variable name="lastName" select="cv:document/cv:personal/cv:last-name"/>
    <xsl:variable name="applicantName" select="concat($firstName, ' ', $lastName)"/>
    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="A4-portrait"
                                       page-height="29.7cm" page-width="21.0cm" margin="2cm">
                    <fo:region-body margin-top="2.5cm"/>
                    <fo:region-before extent="2.5cm"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="A4-portrait">
                <!-- header -->
                <fo:static-content flow-name="xsl-region-before">
                    <fo:table>
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
                                                        <xsl:value-of select="cv:document/cv:personal/cv:address/cv:street"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                            <fo:table-row>
                                                <fo:table-cell>
                                                    <fo:block font-size="10pt"
                                                              font-style="italic"
                                                              font-weight="bold"
                                                              text-align="center">
                                                        <xsl:value-of select="cv:document/cv:personal/cv:address/cv:city"/>,
                                                        <xsl:value-of select="cv:document/cv:personal/cv:address/cv:country"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                            <fo:table-row>
                                                <fo:table-cell>
                                                    <fo:block font-size="10pt"
                                                              font-style="italic"
                                                              font-weight="bold"
                                                              text-align="center">
                                                        Phone: <xsl:value-of select="cv:document/cv:personal/cv:phone[@cv:type='home']"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                            <fo:table-row>
                                                <fo:table-cell>
                                                    <fo:block font-size="10pt"
                                                              font-style="italic"
                                                              font-weight="bold"
                                                              text-align="center">
                                                        Phone: <xsl:value-of select="cv:document/cv:personal/cv:phone[@cv:type='mobile']"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                            <fo:table-row>
                                                <fo:table-cell>
                                                    <fo:block font-size="10pt"
                                                              font-style="italic"
                                                              font-weight="bold"
                                                              text-align="center">
                                                        E-Mail: <xsl:value-of select="cv:document/cv:personal/cv:email"/>
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
                <!-- main part -->
                <fo:flow flow-name="xsl-region-body">
                    <xsl:apply-templates/>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
    <xsl:template match="cv:personal">
        <xsl:call-template name="section">
            <xsl:with-param name="section-name">PERSONAL INFORMATION</xsl:with-param>
        </xsl:call-template>
        <fo:table font-family="sans-serif" font-size="11pt">
            <fo:table-column column-width="40mm"/>
            <fo:table-column column-width="150mm"/>
            <fo:table-body>
                <xsl:call-template name="table-row">
                    <xsl:with-param name="key">Date of birth:</xsl:with-param>
                    <xsl:with-param name="value"><xsl:value-of select="cv:birthday"/></xsl:with-param>
                </xsl:call-template>
                <xsl:call-template name="table-row">
                    <xsl:with-param name="key">Marital status:</xsl:with-param>
                    <xsl:with-param name="value"><xsl:value-of select="cv:marital-status"/></xsl:with-param>
                </xsl:call-template>
                <xsl:call-template name="table-row">
                    <xsl:with-param name="key">Languages:</xsl:with-param>
                    <xsl:with-param name="value">
                        <xsl:for-each select="cv:languages/cv:language">
                            <xsl:value-of select="."/> (<xsl:value-of select="@cv:type"/>)<xsl:if test="position() &lt; last()">, </xsl:if>
                        </xsl:for-each>
                    </xsl:with-param>
                </xsl:call-template>
            </fo:table-body>
        </fo:table>
    </xsl:template>
    <xsl:template match="cv:education">
        <xsl:call-template name="section">
            <xsl:with-param name="section-name">EDUCATION</xsl:with-param>
        </xsl:call-template>
        <xsl:apply-templates select="cv:university"/>
    </xsl:template>
    <xsl:template match="cv:university">
        <fo:block font-family="sans-serif" font-size="11pt" font-weight="bold">
            <xsl:value-of select="cv:start-date"/>-<xsl:value-of select="cv:end-date"/>, <xsl:value-of select="cv:university-name"/>
        </fo:block>
        <fo:table font-family="sans-serif" font-size="11pt">
            <fo:table-column column-width="50mm"/>
            <fo:table-column column-width="100mm"/>
            <fo:table-body>
                <xsl:apply-templates select="cv:department"/>
                <xsl:apply-templates select="cv:specialty"/>
                <xsl:apply-templates select="cv:level"/>
                <xsl:apply-templates select="cv:thesis"/>
            </fo:table-body>
        </fo:table>
    </xsl:template>
    <xsl:template match="cv:department">
        <xsl:call-template name="table-row">
            <xsl:with-param name="key">Department:</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of select="."/></xsl:with-param>
        </xsl:call-template>
    </xsl:template>
    <xsl:template match="cv:specialty">
        <xsl:call-template name="table-row">
            <xsl:with-param name="key">Specialty/Course:</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of select="."/></xsl:with-param>
        </xsl:call-template>
    </xsl:template>
    <xsl:template match="cv:level">
        <xsl:call-template name="table-row">
            <xsl:with-param name="key">Gained Level:</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of select="."/></xsl:with-param>
        </xsl:call-template>
    </xsl:template>
    <xsl:template match="cv:thesis">
        <xsl:call-template name="table-row">
            <xsl:with-param name="key">Diploma Thesis:</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of select="."/></xsl:with-param>
        </xsl:call-template>
    </xsl:template>
    <!-- Objective -->
    <xsl:template match="cv:objective">
        <xsl:call-template name="section">
            <xsl:with-param name="section-name">OBJECTIVE</xsl:with-param>
        </xsl:call-template>
        <fo:block font-size="11pt"><xsl:value-of select="."/></fo:block>
    </xsl:template>
    <!-- Summary -->
    <xsl:template match="cv:summary">
        <xsl:call-template name="section">
            <xsl:with-param name="section-name">PROFESSIONAL SUMMARY</xsl:with-param>
        </xsl:call-template>
        <fo:list-block>
            <xsl:for-each select="cv:summary-item">
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
    <xsl:template match="cv:skills">
        <xsl:call-template name="section">
            <xsl:with-param name="section-name">SKILLS</xsl:with-param>
        </xsl:call-template>
        <fo:table font-family="sans-serif" font-size="11pt">
            <fo:table-column column-width="60mm"/>
            <fo:table-column/>
            <fo:table-body>
                <xsl:for-each select="cv:skill-set">
                    <xsl:call-template name="table-row">
                        <xsl:with-param name="key">
                            <xsl:value-of select="cv:skill-set-name"/>:
                        </xsl:with-param>
                        <xsl:with-param name="value">
                            <xsl:for-each select="cv:skill">
                                <xsl:value-of select="."/><xsl:if test="position() &lt; last()">, </xsl:if>
                            </xsl:for-each>.
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:for-each>
            </fo:table-body>
        </fo:table>
    </xsl:template>
    <!-- Certifications -->
    <xsl:template match="cv:certifications">
        <xsl:call-template name="section">
            <xsl:with-param name="section-name">CERTIFICATIONS</xsl:with-param>
        </xsl:call-template>
        <xsl:for-each select="cv:cert-provider">
            <fo:block font-size="11pt" font-weight="bold">
                <xsl:value-of select="@cv:name"/>
                <xsl:if test="@cv:verify-url">
                    (<fo:basic-link color="blue">
                        <xsl:attribute name="text-decoration">
                            <xsl:text>underline</xsl:text>
                        </xsl:attribute>
                        <xsl:attribute name="external-destination">
                            <xsl:value-of select="@cv:verify-url"/>
                        </xsl:attribute>verify</fo:basic-link>)</xsl:if>:
            </fo:block>
            <xsl:for-each select="cv:cert">
                <fo:block font-size="11pt">
                    <xsl:value-of select="."/>
                    <xsl:if test="@cv:level"> (<xsl:value-of select="@cv:level"/> Level)</xsl:if>
                    on <xsl:value-of select="@cv:date"/>
                    <xsl:if test="@cv:expired='true'"> (expired)</xsl:if>
                </fo:block>
            </xsl:for-each>
            <fo:block margin-bottom="10pt"/>
        </xsl:for-each>
    </xsl:template>
    <!-- Experience -->
    <xsl:template match="cv:experience">
        <xsl:call-template name="section">
            <xsl:with-param name="section-name">PROFESSIONAL EXPERIENCE</xsl:with-param>
        </xsl:call-template>
        <xsl:for-each select="cv:company">
            <fo:block font-size="11pt">
                <fo:inline font-weight="bold"><xsl:value-of select="cv:company-name"/></fo:inline>
                - <xsl:value-of select="cv:company-location"/>
                (<xsl:value-of select="cv:start-date"/> -
                <xsl:choose>
                    <xsl:when test="cv:end-date"><xsl:value-of select="cv:end-date"/></xsl:when>
                    <xsl:otherwise>now</xsl:otherwise>
                </xsl:choose>)
            </fo:block>
            <xsl:for-each select="cv:project">
                <fo:block font-size="11pt">
                    <fo:inline font-weight="bold">Project: </fo:inline>
                    <xsl:value-of select="cv:project-name"/>
                </fo:block>
                <fo:block font-size="11pt">
                    <fo:inline font-weight="bold">Role: </fo:inline>
                    <xsl:value-of select="cv:role"/>
                </fo:block>
                <fo:block font-size="11pt">
                    <fo:inline font-weight="bold">Description: </fo:inline>
                    <xsl:value-of select="cv:description"/>.
                </fo:block>
                <fo:block font-size="11pt" font-weight="bold">Responsibilities:</fo:block>
                <fo:list-block>
                    <xsl:for-each select="cv:responsibilities/cv:responsibility">
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
                    <xsl:for-each select="cv:environment/cv:env-entry">
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