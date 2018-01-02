/*
 * Created by Mystery0 on 17-12-26 下午11:29.
 * Copyright (c) 2017. All Rights reserved.
 *
 * Last modified 17-10-12 下午12:39
 */

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

    static String getBranchName() {
        try {
            return 'git rev-parse --abbrev-ref HEAD'.execute().text.trim()
        } catch (Exception e) {
            e.printStackTrace()
            return "master"
        }
    }
}