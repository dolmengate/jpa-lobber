package info.sroman.entity;
// Generated Dec 7, 2018 11:22:01 PM by Hibernate Tools 3.2.2.GA


import java.sql.Blob;
import javax.persistence.*;

/**
 * XmlpRptLytI8 generated by hbm2java
 */
@Entity
@Table(name="XMLP_RPT_LYT_I8"
)
public class XmlpRptLytI8  implements java.io.Serializable {


     private XmlpRptLytI8Id id;
     private XmlpRptLyt xmlpRptLyt;
     private Blob doLcl;
     private String tyDo;

    public XmlpRptLytI8() {
    }

    public XmlpRptLytI8(XmlpRptLytI8Id id, XmlpRptLyt xmlpRptLyt, Blob doLcl, String tyDo) {
       this.id = id;
       this.xmlpRptLyt = xmlpRptLyt;
       this.doLcl = doLcl;
       this.tyDo = tyDo;
    }
   
     @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="idLytRpt", column=@Column(name="ID_LYT_RPT", nullable=false, precision=38, scale=0) ), 
        @AttributeOverride(name="lcl", column=@Column(name="LCL", nullable=false, length=10) ) } )
    public XmlpRptLytI8Id getId() {
        return this.id;
    }
    
    public void setId(XmlpRptLytI8Id id) {
        this.id = id;
    }
@ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="ID_LYT_RPT", nullable=false, insertable=false, updatable=false)
    public XmlpRptLyt getXmlpRptLyt() {
        return this.xmlpRptLyt;
    }
    
    public void setXmlpRptLyt(XmlpRptLyt xmlpRptLyt) {
        this.xmlpRptLyt = xmlpRptLyt;
    }
    
    @Column(name="DO_LCL", nullable=false)
    public Blob getDoLcl() {
        return this.doLcl;
    }
    
    public void setDoLcl(Blob doLcl) {
        this.doLcl = doLcl;
    }
    
    @Column(name="TY_DO", nullable=false, length=3)
    public String getTyDo() {
        return this.tyDo;
    }
    
    public void setTyDo(String tyDo) {
        this.tyDo = tyDo;
    }


    @Override
    public String toString() {
        return "XmlpRptLytI8{" +
                "id=" + id +
                ", xmlpRptLyt=" + xmlpRptLyt +
                ", doLcl=" + doLcl +
                ", tyDo='" + tyDo + '\'' +
                '}';
    }
}


