package co.nz.airnz.govhack.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A BeachInfo.
 */
@Entity
@Table(name = "beach_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BeachInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "region")
    private String region;

    @Column(name = "beach")
    private String beach;

    @Column(name = "excellent")
    private Double excellent;

    @Column(name = "satisfactory")
    private Double satisfactory;

    @Column(name = "unsatisfactory")
    private Double unsatisfactory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBeach() {
        return beach;
    }

    public void setBeach(String beach) {
        this.beach = beach;
    }

    public Double getExcellent() {
        return excellent;
    }

    public void setExcellent(Double excellent) {
        this.excellent = excellent;
    }

    public Double getSatisfactory() {
        return satisfactory;
    }

    public void setSatisfactory(Double satisfactory) {
        this.satisfactory = satisfactory;
    }

    public Double getUnsatisfactory() {
        return unsatisfactory;
    }

    public void setUnsatisfactory(Double unsatisfactory) {
        this.unsatisfactory = unsatisfactory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BeachInfo beachInfo = (BeachInfo) o;
        if(beachInfo.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, beachInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BeachInfo{" +
            "id=" + id +
            ", region='" + region + "'" +
            ", beach='" + beach + "'" +
            ", excellent='" + excellent + "'" +
            ", satisfactory='" + satisfactory + "'" +
            ", unsatisfactory='" + unsatisfactory + "'" +
            '}';
    }
}
