package info.sroman.entity;
// Generated Dec 7, 2018 11:22:01 PM by Hibernate Tools 3.2.2.GA


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * XmlpRptCfgPrmtI8 generated by hbm2java
 */
@Entity
@Table(name="XMLP_RPT_CFG_PRMT_I8"
    ,schema="OWNER"
)
public class XmlpRptCfgPrmtI8  implements java.io.Serializable {


     private XmlpRptCfgPrmtI8Id id;
     private String nmDsplCfgRpt;

    public XmlpRptCfgPrmtI8() {
    }

	
    public XmlpRptCfgPrmtI8(XmlpRptCfgPrmtI8Id id) {
        this.id = id;
    }
    public XmlpRptCfgPrmtI8(XmlpRptCfgPrmtI8Id id, String nmDsplCfgRpt) {
       this.id = id;
       this.nmDsplCfgRpt = nmDsplCfgRpt;
    }
   
     @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="idPrmtCfgRpt", column=@Column(name="ID_PRMT_CFG_RPT", nullable=false, precision=38, scale=0) ), 
        @AttributeOverride(name="idRptXmlp", column=@Column(name="ID_RPT_XMLP", nullable=false, precision=38, scale=0) ), 
        @AttributeOverride(name="lcl", column=@Column(name="LCL", nullable=false, length=10) ) } )
    public XmlpRptCfgPrmtI8Id getId() {
        return this.id;
    }
    
    public void setId(XmlpRptCfgPrmtI8Id id) {
        this.id = id;
    }
    
    @Column(name="NM_DSPL_CFG_RPT", length=120)
    public String getNmDsplCfgRpt() {
        return this.nmDsplCfgRpt;
    }
    
    public void setNmDsplCfgRpt(String nmDsplCfgRpt) {
        this.nmDsplCfgRpt = nmDsplCfgRpt;
    }


    @Override
    public String toString() {
        return "XmlpRptCfgPrmtI8{" +
                "id=" + id +
                ", nmDsplCfgRpt='" + nmDsplCfgRpt + '\'' +
                '}';
    }
}


