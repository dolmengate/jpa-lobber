<?xml version="1.0" encoding="UTF-8"?>
<!--
/* ===========================================================================
 * Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/backoffice/reports/dataModels.xsl /main/1 2009/08/14 12:50:21 ohorne Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    ohorne    08/12/09 - created document
 *
 * ===========================================================================
 */
-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
  <xsl:param name="vat"/>
  
  <!-- identity rule -->
  <xsl:template match="node()|@*">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()"/>
    </xsl:copy>
  </xsl:template>
  
  <!-- set document name according to vat flag -->
  <xsl:template match="/DataModels/DataModel">    
    <xsl:if test="@name='ArgSummary'">
  
      <!-- select xml document -->
      <xsl:variable name="xml">
        <xsl:choose>
          <xsl:when test="$vat='true'">storeops/StoreOps_ArgSummary_DataTemplateVAT.xml</xsl:when>
          <xsl:otherwise>storeops/StoreOps_ArgSummary_DataTemplate.xml</xsl:otherwise>
        </xsl:choose>
      </xsl:variable>
      <xsl:message>ArgSummary report using <xsl:copy-of select="$xml"/> (vat flag = <xsl:copy-of select="$vat"/>) </xsl:message>
      <DataModel name="ArgSummary" type="DataTemplate" document="{$xml}"/>
    </xsl:if>  
    <xsl:if test="@name!='ArgSummary'">
      <xsl:copy-of select="."/>
    </xsl:if>
  </xsl:template>
</xsl:stylesheet>
