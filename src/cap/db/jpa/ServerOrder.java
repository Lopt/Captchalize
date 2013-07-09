package cap.db.jpa;

import java.util.Collection;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class ServerOrder implements ICaptchalizeEntity {

    @Id
    @GeneratedValue
    private long id = 0;

    private int progressReady = 0;
    private int progressComplete = 0;

    private Set<CaptchaSample> captchaSamples;

    public ServerOrder() {}

    @Override
    public long getId() {
        return this.id;
    }

    /*@OneToMany(mappedBy = "order")
    public Set<CaptchaSample> getCaptchaSamples() {
        return this.captchaSamples;
    }

    public void setCaptchaSamples(final Set<CaptchaSample> captchaSamples) {
        this.captchaSamples = captchaSamples;
        this.progressComplete = captchaSamples.size();
    }*/

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
