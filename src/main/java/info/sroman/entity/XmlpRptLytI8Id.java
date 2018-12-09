package info.sroman.entity;
// Generated Dec 7, 2018 11:22:01 PM by Hibernate Tools 3.2.2.GA


import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * XmlpRptLytI8Id generated by hbm2java
 */
@Embeddable
public class XmlpRptLytI8Id  implements java.io.Serializable {


     private BigDecimal idLytRpt;
     private String lcl;

    public XmlpRptLytI8Id() {
    }

    public XmlpRptLytI8Id(BigDecimal idLytRpt, String lcl) {
       this.idLytRpt = idLytRpt;
       this.lcl = lcl;
    }
   

    @Column(name="ID_LYT_RPT", nullable=false, precision=38, scale=0)
    public BigDecimal getIdLytRpt() {
        return this.idLytRpt;
    }
    
    public void setIdLytRpt(BigDecimal idLytRpt) {
        this.idLytRpt = idLytRpt;
    }

    @Column(name="LCL", nullable=false, length=10)
    public String getLcl() {
        return this.lcl;
    }
    
    public void setLcl(String lcl) {
        this.lcl = lcl;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof XmlpRptLytI8Id) ) return false;
		 XmlpRptLytI8Id castOther = ( XmlpRptLytI8Id ) other; 
         
		 return ( (this.getIdLytRpt()==castOther.getIdLytRpt()) || ( this.getIdLytRpt()!=null && castOther.getIdLytRpt()!=null && this.getIdLytRpt().equals(castOther.getIdLytRpt()) ) )
 && ( (this.getLcl()==castOther.getLcl()) || ( this.getLcl()!=null && castOther.getLcl()!=null && this.getLcl().equals(castOther.getLcl()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getIdLytRpt() == null ? 0 : this.getIdLytRpt().hashCode() );
         result = 37 * result + ( getLcl() == null ? 0 : this.getLcl().hashCode() );
         return result;
   }

    @Override
    public String toString() {
        return "XmlpRptLytI8Id{" +
                "idLytRpt=" + idLytRpt +
                ", lcl='" + lcl + '\'' +
                '}';
    }
}


