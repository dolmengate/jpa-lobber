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
 * XmlpRptDtTmpl generated by hbm2java
 */
@Entity
@Table(name="XMLP_RPT_DT_TMPL"
    ,schema="OWNER"
)
public class XmlpRptDtTmpl  implements java.io.Serializable {


     private BigDecimal idDtTmplRpt;
     private String nmMdlRpt;
     private String tyMdlRpt;
     private Blob doMdlRpt;
     private Set<XmlpRpt> xmlpRpts = new HashSet<XmlpRpt>(0);

    public XmlpRptDtTmpl() {
    }

	
    public XmlpRptDtTmpl(BigDecimal idDtTmplRpt) {
        this.idDtTmplRpt = idDtTmplRpt;
    }
    public XmlpRptDtTmpl(BigDecimal idDtTmplRpt, String nmMdlRpt, String tyMdlRpt, Blob doMdlRpt, Set<XmlpRpt> xmlpRpts) {
       this.idDtTmplRpt = idDtTmplRpt;
       this.nmMdlRpt = nmMdlRpt;
       this.tyMdlRpt = tyMdlRpt;
       this.doMdlRpt = doMdlRpt;
       this.xmlpRpts = xmlpRpts;
    }
   
     @Id 
    
    @Column(name="ID_DT_TMPL_RPT", unique=true, nullable=false, precision=38, scale=0)
    public BigDecimal getIdDtTmplRpt() {
        return this.idDtTmplRpt;
    }
    
    public void setIdDtTmplRpt(BigDecimal idDtTmplRpt) {
        this.idDtTmplRpt = idDtTmplRpt;
    }
    
    @Column(name="NM_MDL_RPT", length=80)
    public String getNmMdlRpt() {
        return this.nmMdlRpt;
    }
    
    public void setNmMdlRpt(String nmMdlRpt) {
        this.nmMdlRpt = nmMdlRpt;
    }
    
    @Column(name="TY_MDL_RPT", length=20)
    public String getTyMdlRpt() {
        return this.tyMdlRpt;
    }
    
    public void setTyMdlRpt(String tyMdlRpt) {
        this.tyMdlRpt = tyMdlRpt;
    }
    
    @Column(name="DO_MDL_RPT")
    public Blob getDoMdlRpt() {
        return this.doMdlRpt;
    }
    
    public void setDoMdlRpt(Blob doMdlRpt) {
        this.doMdlRpt = doMdlRpt;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="xmlpRptDtTmpl")
    public Set<XmlpRpt> getXmlpRpts() {
        return this.xmlpRpts;
    }
    
    public void setXmlpRpts(Set<XmlpRpt> xmlpRpts) {
        this.xmlpRpts = xmlpRpts;
    }




}


