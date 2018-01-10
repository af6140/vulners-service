package com.hanwise.vulners.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

public class CVEOS {

        private String osType;
        private String osVersion;

        public CVEOS() {
        }

        public CVEOS(String osType, String osVersion) {
                this.osType = osType;
                this.osVersion = osVersion;
        }

        public String getOsType() {
                return osType;
        }

        public void setOsType(String osType) {
                this.osType = osType;
        }

        public String getOsVersion() {
                return osVersion;
        }

        public void setOsVersion(String osVersion) {
                this.osVersion = osVersion;
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                CVEOS cveos = (CVEOS) o;
                return Objects.equals(osType, cveos.osType) &&
                        Objects.equals(osVersion, cveos.osVersion);
        }

        @Override
        public int hashCode() {

                return Objects.hash(osType, osVersion);
        }
}
