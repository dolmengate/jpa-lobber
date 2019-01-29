package info.sroman.entity;
// Generated Dec 7, 2018 11:22:01 PM by Hibernate Tools 3.2.2.GA


import java.math.BigDecimal;
import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * XmlpRptLyt generated by hbm2java
 */
@Entity
@Table(name="XMLP_RPT_LYT"
)
public class XmlpRptLyt  implements java.io.Serializable {


     private BigDecimal idLytRpt;
     private String nmLytRpt;
     private String tyLytRpt;
     private Blob doLytRpt;
     private Character flHtmlRpt;
     private Character flPdfRpt;
     private Character flRtfRpt;
     private Character flExcelRpt;
     private Character flXmlRpt;
     private Set<XmlpRptLytI8> xmlpRptLytI8s = new HashSet<XmlpRptLytI8>(0);
//     private Set<XmlpRpt> xmlpRpts = new HashSet<XmlpRpt>(0);

    public XmlpRptLyt() {
    }

	
    public XmlpRptLyt(BigDecimal idLytRpt) {
        this.idLytRpt = idLytRpt;
    }
    public XmlpRptLyt(BigDecimal idLytRpt, String nmLytRpt, String tyLytRpt, Blob doLytRpt, Character flHtmlRpt, Character flPdfRpt, Character flRtfRpt, Character flExcelRpt, Character flXmlRpt, Set<XmlpRptLytI8> xmlpRptLytI8s
//            , Set<XmlpRpt> xmlpRpts
    ) {
       this.idLytRpt = idLytRpt;
       this.nmLytRpt = nmLytRpt;
       this.tyLytRpt = tyLytRpt;
       this.doLytRpt = doLytRpt;
       this.flHtmlRpt = flHtmlRpt;
       this.flPdfRpt = flPdfRpt;
       this.flRtfRpt = flRtfRpt;
       this.flExcelRpt = flExcelRpt;
       this.flXmlRpt = flXmlRpt;
       this.xmlpRptLytI8s = xmlpRptLytI8s;
//       this.xmlpRpts = xmlpRpts;
    }
   
     @Id 
    
    @Column(name="ID_LYT_RPT", unique=true, nullable=false, precision=38, scale=0)
    public BigDecimal getIdLytRpt() {
        return this.idLytRpt;
    }
    
    public void setIdLytRpt(BigDecimal idLytRpt) {
        this.idLytRpt = idLytRpt;
    }
    
    @Column(name="NM_LYT_RPT", length=80)
    public String getNmLytRpt() {
        return this.nmLytRpt;
    }
    
    public void setNmLytRpt(String nmLytRpt) {
        this.nmLytRpt = nmLytRpt;
    }
    
    @Column(name="TY_LYT_RPT", length=20)
    public String getTyLytRpt() {
        return this.tyLytRpt;
    }
    
    public void setTyLytRpt(String tyLytRpt) {
        this.tyLytRpt = tyLytRpt;
    }
    
    @Column(name="DO_LYT_RPT")
    public Blob getDoLytRpt() {
        return this.doLytRpt;
    }
    
    public void setDoLytRpt(Blob doLytRpt) {
        this.doLytRpt = doLytRpt;
    }
    
    @Column(name="FL_HTML_RPT", length=1)
    public Character getFlHtmlRpt() {
        return this.flHtmlRpt;
    }
    
    public void setFlHtmlRpt(Character flHtmlRpt) {
        this.flHtmlRpt = flHtmlRpt;
    }
    
    @Column(name="FL_PDF_RPT", length=1)
    public Character getFlPdfRpt() {
        return this.flPdfRpt;
    }
    
    public void setFlPdfRpt(Character flPdfRpt) {
        this.flPdfRpt = flPdfRpt;
    }
    
    @Column(name="FL_RTF_RPT", length=1)
    public Character getFlRtfRpt() {
        return this.flRtfRpt;
    }
    
    public void setFlRtfRpt(Character flRtfRpt) {
        this.flRtfRpt = flRtfRpt;
    }
    
    @Column(name="FL_EXCEL_RPT", length=1)
    public Character getFlExcelRpt() {
        return this.flExcelRpt;
    }
    
    public void setFlExcelRpt(Character flExcelRpt) {
        this.flExcelRpt = flExcelRpt;
    }
    
    @Column(name="FL_XML_RPT", length=1)
    public Character getFlXmlRpt() {
        return this.flXmlRpt;
    }
    
    public void setFlXmlRpt(Character flXmlRpt) {
        this.flXmlRpt = flXmlRpt;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="xmlpRptLyt")
    public Set<XmlpRptLytI8> getXmlpRptLytI8s() {
        return this.xmlpRptLytI8s;
    }
    
    public void setXmlpRptLytI8s(Set<XmlpRptLytI8> xmlpRptLytI8s) {
        this.xmlpRptLytI8s = xmlpRptLytI8s;
    }
//@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="xmlpRptLyt")
//    public Set<XmlpRpt> getXmlpRpts() {
//        return this.xmlpRpts;
//    }
//
//    public void setXmlpRpts(Set<XmlpRpt> xmlpRpts) {
//        this.xmlpRpts = xmlpRpts;
//    }

    // todo better toString

    @Override
    public String toString() {
        return "XmlpRptLyt{" +
                "idLytRpt=" + idLytRpt +
                ", nmLytRpt='" + nmLytRpt + '\'' +
                ", tyLytRpt='" + tyLytRpt + '\'' +
                ", doLytRpt=" + doLytRpt +
                ", flHtmlRpt=" + flHtmlRpt +
                ", flPdfRpt=" + flPdfRpt +
                ", flRtfRpt=" + flRtfRpt +
                ", flExcelRpt=" + flExcelRpt +
                ", flXmlRpt=" + flXmlRpt +
//                ", xmlpRptLytI8s=" + xmlpRptLytI8s +
//                ", xmlpRpts=" + xmlpRpts +
                '}';
    }
}


