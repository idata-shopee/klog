package io.github.free.lock.klog

class KLogTest extends org.scalatest.FunSuite {
  test("base") {
    KLog.info("test", "1234")
  }

  test("setExtraInfo") {
    KLog.info("test", "1234")
    KLog.setExtraInfo("127.0.0.1:9000")
    KLog.info("test", "1234")
  }
}
