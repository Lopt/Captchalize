package cap.db.jpa;

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
public class CaptchaText implements ICaptchalizeEntity {

    @Id
    @GeneratedValue
    private long id = 0;

    private String type  = "";
    private String value = "";

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "captchaText")
    private Set<CaptchaSample> captchaSamples;

    public CaptchaText() {
    }

    @Override
    public long getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(final String value) {
        this.value = value;
    }
}
