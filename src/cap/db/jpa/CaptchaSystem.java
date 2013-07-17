package cap.db.jpa;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
@Entity
public class CaptchaSystem implements ICaptchalizeEntity {

    @Id
    @GeneratedValue
    private long   id   = 0;
    private String name = "";

    @ManyToOne
    @JoinColumn(name = "websiteId")
    private Website website = null;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "captchaSystem")
    private Set<CaptchaSample> captchaSamples;

    public CaptchaSystem() {}

    @Override
    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
