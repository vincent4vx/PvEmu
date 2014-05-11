package org.pvemu.jelly.utils;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class VersionMatcher {
    final static public VersionMatcher ALL = new VersionMatcher(Interval.INFINITE);
    
    final private Interval major;
    final private Interval minor;
    final private Interval rev;

    public VersionMatcher(Interval major, Interval minor, Interval rev) {
        this.major = major;
        this.minor = minor;
        this.rev = rev;
    }

    public VersionMatcher(Interval major, Interval minor) {
        this.major = major;
        this.minor = minor;
        this.rev = Interval.INFINITE;
    }

    public VersionMatcher(Interval major) {
        this.major = major;
        this.minor = Interval.INFINITE;
        this.rev = Interval.INFINITE;
    }

    public Interval getMajor() {
        return major;
    }

    public Interval getMinor() {
        return minor;
    }

    public Interval getRev() {
        return rev;
    }
    
    /**
     * Test if the version belongs to the interval
     * @param version
     * @return true if good
     */
    public boolean match(Version version){
        return major.belongs(version.getMajor())
                && minor.belongs(version.getMinor())
                && rev.belongs(version.getRev());
    }

    @Override
    public String toString() {
        return "Version(" + "major=" + major + ", minor=" + minor + ", rev=" + rev + ")";
    }
}
