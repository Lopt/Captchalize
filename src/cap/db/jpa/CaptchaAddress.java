package cap.db.jpa;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
@Entity
public class CaptchaAddress implements ICaptchalizeEntity {

    @Id
    @GeneratedValue
    private long id = 0;

    @ManyToOne
    @JoinColumn(name = "websiteId")
    private Website website = null;

    private String urlProtocol  = "";
    private String urlPath      = "";
    private String urlParams    = "";
    private int    urlPort      = 0;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date = null;

    public CaptchaAddress() {
    }

    @Override
    public long getId() {
        return this.id;
    }

    public Website getWebsite() {
        return this.website;
    }

    public void setWebsite(final Website website) {
        this.website = website;
    }

    public String getUrlProtocol() {
        return this.urlProtocol;
    }

    public void setUrlProtocol(final String urlProtocol) {
        this.urlProtocol = urlProtocol;
    }

    public String getUrlPath() {
        return this.urlPath;
    }

    public void setUrlPath(final String urlPath) {
        this.urlPath = urlPath;
    }

    public String getUrlParams() {
        return this.urlParams;
    }

    public void setUrlParams(final String urlParams) {
        this.urlParams = urlParams;
    }

    public int getUrlPort() {
        return this.urlPort;
    }

    public void setUrlPort(final int urlPort) {
        this.urlPort = urlPort;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    public URL getUrl() throws MalformedURLException {
        assert this.website != null : "You don't set a website.";

        StringBuilder url = new StringBuilder();
        url.append(this.urlProtocol);
        url.append("://");

        url.append(this.website.getHostname());

        if (this.urlPort != 80) {
            url.append(":");
            url.append(this.urlPort);
        }

        if (!this.urlPath.equals("")) {
            url.append(this.urlPath);
        }

        if (!this.urlParams.equals("")) {
            url.append("?");
            url.append(this.urlParams);
        }

        return new URL(url.toString());
    }

    public void setUrl(final URL url) {
        assert this.website != null : "Set the website attribute first.";
        assert this.website.getHostname().equals(url.getHost()) : "Website hostname and the url hostname mismatched.";

        this.urlProtocol  = url.getProtocol() == null ? "" : url.getProtocol();
        this.urlPath      = url.getPath()     == null ? "" : url.getPath();
        this.urlParams    = url.getQuery()    == null ? "" : url.getQuery();
        this.urlPort      = url.getPort()     == -1   ? 80 : url.getPort();
    }
}
