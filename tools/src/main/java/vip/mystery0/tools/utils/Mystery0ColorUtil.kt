/*
 * Created by Mystery0 on 18-3-10 上午12:52.
 * Copyright (c) 2018. All Rights reserved.
 *
 * Last modified 18-3-10 上午12:51
 */

package vip.mystery0.tools.utils

import android.graphics.Color
import java.util.*

object Mystery0ColorUtil {

	fun parseColor(color: Int, alpha: Int): Int {
		return Color.parseColor(parseColorAsString(color, alpha))
	}

	fun parseColorAsString(color: Int, alpha: Int = 255): String {
		val parseColor = StringBuilder()
		parseColor.append('#')
		var opacityString = Integer.toHexString(alpha)
		if (opacityString.length < 2)
			opacityString = "0$opacityString"
		parseColor.append(opacityString.toUpperCase())
		var red = Integer.toHexString(Color.red(color)).toUpperCase()
		var green = Integer.toHexString(Color.green(color)).toUpperCase()
		var blue = Integer.toHexString(Color.blue(color)).toUpperCase()
		red = if (red.length == 1) "0$red" else red
		green = if (green.length == 1) "0$green" else green
		blue = if (blue.length == 1) "0$blue" else blue
		parseColor.append(red)
				.append(green)
				.append(blue)
		return parseColor.toString()
	}

	fun getRandomColorAsInt(alpha: Int = 255): Int {
		val random = Random()
		//生成红色颜色代码
		val red = random.nextInt(180) + 40
		//生成绿色颜色代码
		val green = random.nextInt(100) + 90
		//生成蓝色颜色代码
		val blue = random.nextInt(120) + 120
		return Color.argb(alpha, red, green, blue)
	}

	fun getRandomColorAsString(alpha: Int = 255): String {
		val color = StringBuilder()
		color.append('#')
		var opacityString = Integer.toHexString(alpha)
		if (opacityString.length < 2)
			opacityString = "0$opacityString"
		color.append(opacityString.toUpperCase())
		val random = Random()
		var red: String = Integer.toHexString(random.nextInt(180) + 40).toUpperCase()
		var green: String = Integer.toHexString(random.nextInt(100) + 90).toUpperCase()
		var blue: String = Integer.toHexString(random.nextInt(120) + 120).toUpperCase()
		red = if (red.length == 1) "0$red" else red
		green = if (green.length == 1) "0$green" else green
		blue = if (blue.length == 1) "0$blue" else blue
		//生成十六进制颜色值
		color.append(red).append(green).append(blue)
		return color.toString()
	}
}