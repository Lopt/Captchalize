package cap.db.jpa;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
@Entity
public class CaptchaAudio implements ICaptchalizeEntity {

    @Id
    @GeneratedValue
    private long id = 0;

    // Abtastrate etc...

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "captchaAudio")
    private Set<CaptchaSample> captchaSamples;

    @Lob
    private byte[] sourceAudio = null;


    public CaptchaAudio() {
    }

    @Override
    public long getId() {
        return this.id;
    }

    public byte[] getSourceAudio() {
        return this.sourceAudio;
    }

    public void setSourceAudio(final byte[] sourceAudio) {
        this.sourceAudio = sourceAudio;
    }
}
