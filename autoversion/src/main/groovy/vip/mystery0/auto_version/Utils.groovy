package vip.mystery0.auto_version

class Utils {
    static int getCommitCount() {
        try {
            def result = new StringBuilder()
            def error = new StringBuilder()
            def process = 'git rev-list HEAD --count'.execute()
            process.waitForProcessOutput(result, error)
            if (result.isInteger()) {
                return result.toInteger()
            } else {
                return 0
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
        return 0
    }

}