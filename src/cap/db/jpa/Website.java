package cap.db.jpa;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
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

    @OneToMany
    private Collection<CaptchaSystem> captchaSystems;

    public Website() {
    }

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

    public Collection<CaptchaSystem> getCaptchaSystems() {
        return this.captchaSystems;
    }

    public void setCaptchaSystems(final Collection<CaptchaSystem> captchaSystems) {
        this.captchaSystems = captchaSystems;
    }
}
