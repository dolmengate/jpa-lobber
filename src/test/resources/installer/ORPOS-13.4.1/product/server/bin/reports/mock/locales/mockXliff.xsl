<?xml version="1.0" encoding="UTF-8"?>
<!--
/* ===========================================================================
 * Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/backoffice/reports/mock/locales/mockXliff.xsl /main/1 2009/08/14 12:50:22 ohorne Exp $
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
	
	<xsl:variable name="prefix">哦ãç </xsl:variable>
	<xsl:variable name="suffix"> 簡単£</xsl:variable>
	
	<xsl:param name="target-language"/>	
	
	<!-- Identity transform, passes nodes through untouched -->
	<xsl:template match="@*|node()|text()">
		<xsl:copy>
			<xsl:apply-templates select="@*|*|text()" />
		</xsl:copy>
	</xsl:template>
	
	<xsl:template match="/xliff/file/body/trans-unit/target" >

		<xsl:choose>
			  <xsl:when test="../source/@xml:space='preserve'">
			  	<target xml:space="preserve"><xsl:value-of select="concat($prefix,node() ,$suffix)" /></target>
			  </xsl:when>
			  <xsl:otherwise>
 			  	<target><xsl:value-of select="concat($prefix,node() ,$suffix)" /></target>
			  </xsl:otherwise>
		</xsl:choose>
		
	</xsl:template>
	
	<xsl:template match="/xliff/file/@target-language">
  	  <xsl:attribute name="target-language">
	   <xsl:value-of select="$target-language"/>
	   </xsl:attribute>
	</xsl:template>
	
</xsl:stylesheet>
