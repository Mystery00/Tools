/*
 * Created by Mystery0 on 17-12-26 下午11:29.
 * Copyright (c) 2017. All Rights reserved.
 *
 * Last modified 17-10-12 下午12:39
 */

package vip.mystery0.auto_version

import org.gradle.api.Plugin
import org.gradle.api.Project

class AutoVersionPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.extensions.create("autoVersion", AutoVersionExtension)
    }
}