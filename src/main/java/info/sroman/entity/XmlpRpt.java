package info.sroman.entity;
// Generated Dec 7, 2018 11:22:01 PM by Hibernate Tools 3.2.2.GA


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * XmlpRpt generated by hbm2java
 */
@Entity
@Table(name="XMLP_RPT"
    ,schema="OWNER"
)
public class XmlpRpt  implements java.io.Serializable {


     private BigDecimal idRptXmlp;
     private XmlpRptDtTmpl xmlpRptDtTmpl;
     private XmlpRptLyt xmlpRptLyt;
     private String nmRptXmlp;
     private String nmDsplRptXmlp;
     private String ctgRptXmlp;
     private String nmDaDftRptXmlp;
     private String nmRlRptXmlp;
     private Set<XmlpRptCfgPrmt> xmlpRptCfgPrmts = new HashSet<XmlpRptCfgPrmt>(0);

    public XmlpRpt() {
    }

	
    public XmlpRpt(BigDecimal idRptXmlp, XmlpRptDtTmpl xmlpRptDtTmpl, XmlpRptLyt xmlpRptLyt) {
        this.idRptXmlp = idRptXmlp;
        this.xmlpRptDtTmpl = xmlpRptDtTmpl;
        this.xmlpRptLyt = xmlpRptLyt;
    }
    public XmlpRpt(BigDecimal idRptXmlp, XmlpRptDtTmpl xmlpRptDtTmpl, XmlpRptLyt xmlpRptLyt, String nmRptXmlp, String nmDsplRptXmlp, String ctgRptXmlp, String nmDaDftRptXmlp, String nmRlRptXmlp, Set<XmlpRptCfgPrmt> xmlpRptCfgPrmts) {
       this.idRptXmlp = idRptXmlp;
       this.xmlpRptDtTmpl = xmlpRptDtTmpl;
       this.xmlpRptLyt = xmlpRptLyt;
       this.nmRptXmlp = nmRptXmlp;
       this.nmDsplRptXmlp = nmDsplRptXmlp;
       this.ctgRptXmlp = ctgRptXmlp;
       this.nmDaDftRptXmlp = nmDaDftRptXmlp;
       this.nmRlRptXmlp = nmRlRptXmlp;
       this.xmlpRptCfgPrmts = xmlpRptCfgPrmts;
    }
   
     @Id 
    
    @Column(name="ID_RPT_XMLP", unique=true, nullable=false, precision=38, scale=0)
    public BigDecimal getIdRptXmlp() {
        return this.idRptXmlp;
    }
    
    public void setIdRptXmlp(BigDecimal idRptXmlp) {
        this.idRptXmlp = idRptXmlp;
    }
@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="ID_DT_TMPL_RPT", nullable=false)
    public XmlpRptDtTmpl getXmlpRptDtTmpl() {
        return this.xmlpRptDtTmpl;
    }
    
    public void setXmlpRptDtTmpl(XmlpRptDtTmpl xmlpRptDtTmpl) {
        this.xmlpRptDtTmpl = xmlpRptDtTmpl;
    }
@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="ID_LYT_RPT", nullable=false)
    public XmlpRptLyt getXmlpRptLyt() {
        return this.xmlpRptLyt;
    }
    
    public void setXmlpRptLyt(XmlpRptLyt xmlpRptLyt) {
        this.xmlpRptLyt = xmlpRptLyt;
    }
    
    @Column(name="NM_RPT_XMLP", length=80)
    public String getNmRptXmlp() {
        return this.nmRptXmlp;
    }
    
    public void setNmRptXmlp(String nmRptXmlp) {
        this.nmRptXmlp = nmRptXmlp;
    }
    
    @Column(name="NM_DSPL_RPT_XMLP", length=120)
    public String getNmDsplRptXmlp() {
        return this.nmDsplRptXmlp;
    }
    
    public void setNmDsplRptXmlp(String nmDsplRptXmlp) {
        this.nmDsplRptXmlp = nmDsplRptXmlp;
    }
    
    @Column(name="CTG_RPT_XMLP", length=50)
    public String getCtgRptXmlp() {
        return this.ctgRptXmlp;
    }
    
    public void setCtgRptXmlp(String ctgRptXmlp) {
        this.ctgRptXmlp = ctgRptXmlp;
    }
    
    @Column(name="NM_DA_DFT_RPT_XMLP", length=80)
    public String getNmDaDftRptXmlp() {
        return this.nmDaDftRptXmlp;
    }
    
    public void setNmDaDftRptXmlp(String nmDaDftRptXmlp) {
        this.nmDaDftRptXmlp = nmDaDftRptXmlp;
    }
    
    @Column(name="NM_RL_RPT_XMLP", length=50)
    public String getNmRlRptXmlp() {
        return this.nmRlRptXmlp;
    }
    
    public void setNmRlRptXmlp(String nmRlRptXmlp) {
        this.nmRlRptXmlp = nmRlRptXmlp;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="xmlpRpt")
    public Set<XmlpRptCfgPrmt> getXmlpRptCfgPrmts() {
        return this.xmlpRptCfgPrmts;
    }
    
    public void setXmlpRptCfgPrmts(Set<XmlpRptCfgPrmt> xmlpRptCfgPrmts) {
        this.xmlpRptCfgPrmts = xmlpRptCfgPrmts;
    }


}

