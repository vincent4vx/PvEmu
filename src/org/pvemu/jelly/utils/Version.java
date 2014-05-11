package org.pvemu.jelly.utils;

import java.util.Objects;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class Version implements Comparable<Version>{
    final private int major;
    final private int minor;
    final private int rev;
    final private String stage;

    public Version(int major, int minor, int rev, String stage) {
        this.major = major;
        this.minor = minor;
        this.rev = rev;
        this.stage = stage;
    }

    public Version(int major, int minor, int rev) {
        this.major = major;
        this.minor = minor;
        this.rev = rev;
        this.stage = "";
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getRev() {
        return rev;
    }

    public String getStage() {
        return stage;
    }

    @Override
    public int compareTo(Version o) {
        if(o == null)
            return Integer.MIN_VALUE;
        
        if(major != o.major)
            return major - o.major;
        
        if(minor != o.minor)
            return minor - o.minor;
        
        return rev - o.rev;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.major;
        hash = 53 * hash + this.minor;
        hash = 53 * hash + this.rev;
        hash = 53 * hash + Objects.hashCode(this.stage);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Version other = (Version) obj;
        if (this.major != other.major) {
            return false;
        }
        if (this.minor != other.minor) {
            return false;
        }
        if (this.rev != other.rev) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return major + "." + minor + "." + rev + stage;
    }
}
