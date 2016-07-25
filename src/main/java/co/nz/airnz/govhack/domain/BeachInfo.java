package co.nz.airnz.govhack.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A user.
 */
@Entity
@Table(name = "beach_info")
public class BeachInfo extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column
    private String region;

    @Column
    private String beach;

    @Column
    private Double excellent;

    @Column
    private Double satisfactory;

    @Column
    private Double unsatisfactory;

    public BeachInfo() {
        this.beach = beach;
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Double getSatisfactory() {
        return satisfactory;
    }

    public void setSatisfactory(Double satisfactory) {
        this.satisfactory = satisfactory;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Double getUnsatisfactory() {
        return unsatisfactory;
    }

    public void setUnsatisfactory(Double unsatisfactory) {
        this.unsatisfactory = unsatisfactory;
    }
}
