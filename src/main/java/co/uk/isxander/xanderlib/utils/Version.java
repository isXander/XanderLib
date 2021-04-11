/*
 * Copyright (C) isXander [2019 - 2021]
 * This program comes with ABSOLUTELY NO WARRANTY
 * This is free software, and you are welcome to redistribute it under the certain conditions that can be found here
 * https://www.gnu.org/licenses/gpl-3.0.en.html
 */

package co.uk.isxander.xanderlib.utils;

public final class Version {

    private int major, minor, patch;

    public Version(String version) {
        String[] split = version.split("\\.");
        int[] vals = new int[3];
        for (int i = 0; i < split.length; i++) {
            if (i > vals.length - 1) {
                continue;
            }
            try {
                vals[i] = Integer.parseInt(split[i]);
            } catch (NumberFormatException e) {
                vals[i] = 0;
            }
        }
        major = vals[0];
        minor = vals[1];
        patch = vals[2];
    }

    public Version(int major, int minor, int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    public static boolean newerThan(Version a, Version b) {
        if (a.getMajor() < b.getMajor())
            return false;
        if (a.getMinor() < b.getMinor())
            return false;
        if (a.getPatch() < b.getPatch())
            return false;
        return !sameVersion(a, b);
    }

    public boolean newerThan(Version b) {
        if (this.getMajor() < b.getMajor())
            return false;
        if (this.getMinor() < b.getMinor())
            return false;
        if (this.getPatch() < b.getPatch())
            return false;
        return !sameVersion(b);
    }

    public static boolean olderThan(Version a, Version b) {
        if (a.getMajor() > b.getMajor())
            return false;
        if (a.getMinor() > b.getMinor())
            return false;
        if (a.getPatch() > b.getPatch())
            return false;
        return !sameVersion(a, b);
    }

    public boolean olderThan(Version b) {
        if (this.getMajor() > b.getMajor())
            return false;
        if (this.getMinor() > b.getMinor())
            return false;
        if (this.getPatch() > b.getPatch())
            return false;
        return !sameVersion(b);
    }

    public static boolean sameVersion(Version a, Version b) {
        return a.getMajor() == b.getMajor() && a.getMinor() == b.getMinor() && a.getPatch() == b.getPatch();
    }

    public boolean sameVersion(Version b) {
        return this.getMajor() == b.getMajor() && this.getMinor() == b.getMinor() && this.getPatch() == b.getPatch();
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public int getPatch() {
        return patch;
    }

    public void setPatch(int patch) {
        this.patch = patch;
    }

    @Override
    public String toString() {
        return major + "." + minor + "." + patch;
    }


}
