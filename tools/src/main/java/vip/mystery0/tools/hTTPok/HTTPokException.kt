package vip.mystery0.tools.hTTPok

/**
 * Created by myste.
 */
class HTTPokException : Exception
{
	constructor() : super()
	constructor(message: String?) : super(message)
	constructor(throwable: Throwable?) : super(throwable)
	constructor(message: String?, throwable: Throwable?) : super(message, throwable)
}