package io.github.free.lock.klog

import java.text.SimpleDateFormat
import java.util.Calendar
import java.io.{ PrintWriter, StringWriter }

/**
  * just log important and special information to the standard io
  * grep klog by command, sbt run | grep "klog"
  */
object KLog {
  private val formatter = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss.SSS");

  // listener can get all log information
  private var listener = (message: String) => {}

  // extra format, may have ip and port info
  private var extraInfo = ""

  private val NORMAL_LEVEL = 0
  private val ERROR_LEVEL  = 1
  private val VITAL        = 2

  // some information can be opened to debug or monitor
  private var inspectOpened = false

  var PRINT_LEVEL = sys.env.get("KLOG_PRINT_LEVEL").getOrElse(NORMAL_LEVEL + "").toInt

  def setListener(f: String => Unit) =
    listener = f

  def setExtraInfo(extra: String) = extraInfo = extra

  def toggleInspector() =
    inspectOpened = !inspectOpened

  def logIn(title: String, text: String) =
    if (inspectOpened) {
      info(title, text)
    }

  def info(title: String, text: String, level: Int = NORMAL_LEVEL) = {
    val today   = Calendar.getInstance().getTime();
    val logText = if (extraInfo == "") s"[klog:${formatter.format(today)}] (${title}) ${text}" else s"[klog:${formatter.format(today)}:${extraInfo}] (${title}) ${text}"

    if (level >= PRINT_LEVEL) {
      println(logText)
    }

    listener(logText)
  }

  def logVital(title: String, text: String) =
    info(title, text, VITAL)

  def logErr(title: String, err: Exception) = {
    val sw = new StringWriter
    err.printStackTrace(new PrintWriter(sw))
    info(s"error-response-$title", s"${sw.toString}", ERROR_LEVEL)
  }
}
