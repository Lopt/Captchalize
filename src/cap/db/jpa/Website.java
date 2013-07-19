package cap.db.jpa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
@Entity
public class Website implements ICaptchalizeEntity {

    @Id
    @GeneratedValue
    private long   id       = 0;
    private String hostname = "";

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "website")
    private Set<CaptchaSystem> captchaSystems = new HashSet<CaptchaSystem>();

    public Website() {}

    @Override
    public long getId() {
        return this.id;
    }

    public String getHostname() {
        return this.hostname;
    }

    public void setHostname(final String hostname) {
        this.hostname = hostname;
    }

    public Set<CaptchaSystem> getCaptchaSystems() {
        return this.captchaSystems;
    }

    public void setCaptchaSystems(final Set<CaptchaSystem> captchaSystems) {
        this.captchaSystems = captchaSystems;
    }
}
