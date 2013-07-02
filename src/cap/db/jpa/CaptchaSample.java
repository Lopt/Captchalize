package cap.db.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
@Entity
public class CaptchaSample implements ICaptchalizeEntity {

    @Id
    @GeneratedValue
    private long id = 0;

    @ManyToOne
    @JoinColumn(name = "captchaSystemId")
    private CaptchaSystem captchaSystem = null;

    @ManyToOne
    @JoinColumn(name = "captchaAddressId")
    private CaptchaAddress captchaAddress = null;

    @ManyToOne
    @JoinColumn(name = "captchaImageId")
    private CaptchaImage captchaImage = null;

    @ManyToOne
    @JoinColumn(name = "captchaTextId")
    private CaptchaText captchaText = null;

    @ManyToOne
    @JoinColumn(name = "captchaAudioId")
    private CaptchaAudio captchaAudio = null;

    @Lob
    private String solution = "";

    @Lob
    private String solutionAuto = "";

    private float correctness = 0.0f;

    private boolean solved = false;

    public CaptchaSample() {
    }

    @Override
    public long getId() {
        return this.id;
    }

    public CaptchaAddress getCaptchaAddress() {
        return this.captchaAddress;
    }

    public void setCaptchaAddress(final CaptchaAddress captchaAddress) {
        this.captchaAddress = captchaAddress;
    }

    public CaptchaAudio getCaptchaAudio() {
        return this.captchaAudio;
    }

    public void setCaptchaAudio(final CaptchaAudio captchaAudio) {
        this.captchaAudio = captchaAudio;
    }

    public CaptchaImage getCaptchaImage() {
        return this.captchaImage;
    }

    public void setCaptchaImage(final CaptchaImage captchaImage) {
        this.captchaImage = captchaImage;
    }

    public CaptchaSystem getCaptchaSystem() {
        return this.captchaSystem;
    }

    public void setCaptchaSystem(final CaptchaSystem captchaSystem) {
        this.captchaSystem = captchaSystem;
    }

    public CaptchaText getCaptchaText() {
        return this.captchaText;
    }

    public void setCaptchaText(final CaptchaText captchaText) {
        this.captchaText = captchaText;
    }

    public float getCorrectness() {
        return this.correctness;
    }

    public void setCorrectness(final float correctness) {
        this.correctness = correctness;
    }

    public String getSolution() {
        return this.solution;
    }

    public void setSolution(final String solution) {
        this.solution = solution;
    }

    public String getSolutionAuto() {
        return this.solutionAuto;
    }

    public void setSolutionAuto(final String solutionAuto) {
        this.solutionAuto = solutionAuto;
    }

    public boolean isSolved() {
        return this.solved;
    }

    public void setSolved(final boolean solved) {
        this.solved = solved;
    }
}
