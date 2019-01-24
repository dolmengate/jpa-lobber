<?xml version="1.0" encoding="UTF-8"?>
<!--
/* ===========================================================================
* Copyright (c) 2009, 2010, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/backoffice/reports/mock/locales/mockLayouts.xsl /main/2 2010/01/19 17:46:50 sgu Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    sgu       01/13/10 - Fix the stylesheet to not to transform the <Layouts>
 *                         tag
 *    ohorne    08/12/09 - created document
 *
 * ===========================================================================
 */
-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
  <xsl:param name="locale"/>

  <!-- identity rule -->
  <xsl:template match="node()|@*">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()"/>
    </xsl:copy>
  </xsl:template>

  <!-- Layout node processing -->
  <xsl:template match="Layouts/Layout">
      <xsl:message>Mapping Xliff document in report import file: <xsl:value-of select="current()/@name"/>(<xsl:copy-of select="$locale"/>) </xsl:message>
      <xsl:call-template name="addLocaleDocument">
        <xsl:with-param name="category" select="substring-before(current()/@document,'/')"/>
        <xsl:with-param name="reportdoc" select="substring-before(substring-after(current()/@document,'/'),'.rtf')"/>
      </xsl:call-template>
  </xsl:template>

  <!-- add XLIFF mapping  for current element's report -->
  <xsl:template name="addLocaleDocument">
    <xsl:param name="category"/>
    <xsl:param name="reportdoc"/>
    <xsl:choose>
      <xsl:when test="*/@locale=$locale">
         <!-- already exists, don't add element -->
         <xsl:copy-of select="."/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:copy>
          <xsl:apply-templates select="@* | node()"/>
          <LocaleDocument locale="{$locale}" type="XLF" document="{$category}/locales/{$locale}/{$reportdoc}_{$locale}.xlf"/>
        </xsl:copy>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

</xsl:stylesheet>
