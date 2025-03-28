package de.aittr.bio_marketplace.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String jwtAtSecret;
    private String jwtRtSecret;
    private Integer atExpirationInMs;
    private Integer rtExpirationInMs;
    private String atCookieName;
    private String rtCookieName;
    private Integer resetTokenExpirationInMs;

    public String getJwtAtSecret() {
        return jwtAtSecret;
    }

    public void setJwtAtSecret(String jwtAtSecret) {
        this.jwtAtSecret = jwtAtSecret;
    }

    public String getJwtRtSecret() {
        return jwtRtSecret;
    }

    public void setJwtRtSecret(String jwtRtSecret) {
        this.jwtRtSecret = jwtRtSecret;
    }

    public Integer getAtExpirationInMs() {
        return atExpirationInMs;
    }

    public void setAtExpirationInMs(Integer atExpirationInMs) {
        this.atExpirationInMs = atExpirationInMs;
    }

    public Integer getRtExpirationInMs() {
        return rtExpirationInMs;
    }

    public void setRtExpirationInMs(Integer rtExpirationInMs) {
        this.rtExpirationInMs = rtExpirationInMs;
    }

    public String getAtCookieName() {
        return atCookieName;
    }

    public void setAtCookieName(String atCookieName) {
        this.atCookieName = atCookieName;
    }

    public String getRtCookieName() {
        return rtCookieName;
    }

    public void setRtCookieName(String rtCookieName) {
        this.rtCookieName = rtCookieName;
    }

    public Integer getResetTokenExpirationInMs() {
        return resetTokenExpirationInMs;
    }

    public void setResetTokenExpirationInMs(Integer resetTokenExpirationInMs) {
        this.resetTokenExpirationInMs = resetTokenExpirationInMs;
    }
}
