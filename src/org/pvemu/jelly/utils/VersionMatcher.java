/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.jelly.utils;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class VersionMatcher {
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
        this.rev = new Interval();
    }

    public VersionMatcher(Interval major) {
        this.major = major;
        this.minor = new Interval();
        this.rev = new Interval();
    }

    public VersionMatcher() {
        this.major = new Interval();
        this.minor = new Interval();
        this.rev = new Interval();
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
