package cap.db.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
@Entity
public class CaptchaSystem implements ICaptchalizeEntity {

    @Id
    @GeneratedValue
    private long   id   = 0;
    private String name = "";

    public CaptchaSystem() {
    }

    @Override
    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String hostname) {
        this.name = name;
    }
}
