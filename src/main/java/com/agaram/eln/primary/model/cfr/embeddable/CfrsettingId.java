package com.agaram.eln.primary.model.cfr.embeddable;

import java.io.Serializable;
import com.agaram.eln.primary.model.usermanagement.LSSiteMaster;
import jakarta.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class CfrsettingId implements Serializable {

    private Integer cfrsettingcode;
    private LSSiteMaster lssitemaster; // Add LSSiteMaster as part of the composite key

    public CfrsettingId() {}

    public CfrsettingId(Integer cfrsettingcode, LSSiteMaster lssitemaster) {
        this.cfrsettingcode = cfrsettingcode;
        this.lssitemaster = lssitemaster;
    }

    public Integer getCfrsettingcode() {
        return cfrsettingcode;
    }

    public void setCfrsettingcode(Integer cfrsettingcode) {
        this.cfrsettingcode = cfrsettingcode;
    }

    public LSSiteMaster getLssitemaster() {
        return lssitemaster;
    }

    public void setLssitemaster(LSSiteMaster lssitemaster) {
        this.lssitemaster = lssitemaster;
    }

    @Override
    public int hashCode() {
        // Compute hash code based on both fields
        int result = cfrsettingcode != null ? cfrsettingcode.hashCode() : 0;
        result = 31 * result + (lssitemaster != null ? lssitemaster.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        CfrsettingId that = (CfrsettingId) obj;

        if (cfrsettingcode != null ? !cfrsettingcode.equals(that.cfrsettingcode) : that.cfrsettingcode != null)
            return false;
        return lssitemaster != null ? lssitemaster.equals(that.lssitemaster) : that.lssitemaster == null;
    }
}
