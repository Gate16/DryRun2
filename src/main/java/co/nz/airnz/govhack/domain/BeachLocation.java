package co.nz.airnz.govhack.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A BeachLocation.
 */
@Entity
@Table(name = "beach_location")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BeachLocation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "beachname")
    private String beachname;

    @Column(name = "longitude")
    private Float longitude;

    @Column(name = "latitude")
    private Float latitude;

    @Column(name = "zoomlevel")
    private Integer zoomlevel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBeachname() {
        return beachname;
    }

    public void setBeachname(String beachname) {
        this.beachname = beachname;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Integer getZoomlevel() {
        return zoomlevel;
    }

    public void setZoomlevel(Integer zoomlevel) {
        this.zoomlevel = zoomlevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BeachLocation beachLocation = (BeachLocation) o;
        if(beachLocation.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, beachLocation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BeachLocation{" +
            "id=" + id +
            ", beachname='" + beachname + "'" +
            ", longitude='" + longitude + "'" +
            ", latitude='" + latitude + "'" +
            ", zoomlevel='" + zoomlevel + "'" +
            '}';
    }
}
