package cap.db.jpa;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
@Entity
public class ServerOrder implements ICaptchalizeEntity {

    @Id
    @GeneratedValue
    private long id = 0;

    private int progressReady = 0;
    private int progressComplete = 0;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "serverOrder")
    private Set<CaptchaSample> captchaSamples = new HashSet<CaptchaSample>();

    public ServerOrder() {}

    @Override
    public long getId() {
        return this.id;
    }

    public Set<CaptchaSample> getCaptchaSamples() {
        return this.captchaSamples;
    }

    public void setCaptchaSamples(final Set<CaptchaSample> captchaSamples) {
        this.captchaSamples = captchaSamples;
        this.progressComplete = captchaSamples.size();
    }

    public int getProgressReady() {
        return this.progressReady;
    }

    public int getProgressComplete() {
        return this.progressComplete;
    }

    public void toProgress() {
        assert this.progressReady < this.progressComplete;

        this.progressReady += 1;
    }
}
