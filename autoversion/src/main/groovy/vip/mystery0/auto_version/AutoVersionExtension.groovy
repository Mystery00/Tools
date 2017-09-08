package vip.mystery0.auto_version

class AutoVersionExtension {
    int major = 0
    int minor = 0
    int patch = 0
    int beta = 0
    int alpha = 0
    int build = -1

    public <T> void setMajor(T number) {
        major = validate(number)
    }

    public <T> void setPatch(T number) {
        patch = validate(number)
    }

    public <T> void setBuild(T number) {
        build = validate(number)
    }

    public def getBuild() {
        if (build == -1)
            build = Utils.commitCount
        return build
    }

    public Integer getCode() {
        return build
    }

    public String getName() {
        def versionName
        if (beta != 0) {
            versionName = "${major}.${minor}.${patch}-beta${beta}"
        } else if (alpha != 0) {
            versionName = "${major}.${minor}.${patch}-alpha${alpha}"
        } else {
            versionName = "${major}.${minor}.${patch}"
        }
        return versionName
    }

    private static <T> int validate(T number) {
        def intNumber
        try {
            intNumber = number.toInteger()
        } catch (Exception e) {
            e.printStackTrace()
            return 0
        }
        if (intNumber < 0) {
            return 0
        }
        return intNumber
    }
}